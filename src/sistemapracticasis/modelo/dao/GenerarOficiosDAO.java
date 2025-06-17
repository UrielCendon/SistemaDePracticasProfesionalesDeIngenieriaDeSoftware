package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.EstudianteAsignado;
import sistemapracticasis.util.Utilidad;

/**
 * Clase DAO para gestionar la generación de oficios.
 * Autor: Raziel Filobello
 * Fecha de creación: 14/06/2025
 * Descripción: Proporciona métodos para obtener información de estudiantes 
 * asignados a proyectos en el periodo actual.
 */
public class GenerarOficiosDAO {

    /**
     * Obtiene la lista de estudiantes asignados a proyectos en el periodo actual.
     * @return Lista de objetos EstudianteAsignado con información de estudiantes, 
     *         proyectos y organizaciones vinculadas.
     */
    public static List<EstudianteAsignado> obtenerEstudiantesAsignadosPeriodoActual() {
        List<EstudianteAsignado> lista = new ArrayList<>();

        String consulta = "SELECT e.matricula, "
            + "CONCAT(e.nombre, ' ', e.apellido_paterno, ' ', e.apellido_materno) AS nombre_estudiante, "
            + "p.nombre AS nombre_proyecto, "
            + "ov.razon_social AS razon_social_organizacion "
            + "FROM estudiante e "
            + "JOIN expediente exp ON e.id_estudiante = exp.id_estudiante "
            + "JOIN periodo pe ON exp.id_periodo = pe.id_periodo "
            + "JOIN proyecto p ON e.id_proyecto = p.id_proyecto "
            + "JOIN organizacion_vinculada ov ON p.id_organizacion_vinculada = ov.id_organizacion_vinculada "
            + "WHERE CURDATE() BETWEEN pe.fecha_inicio AND pe.fecha_fin "
            + "AND exp.estado = 'en curso'";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
             ResultSet resultadoConsulta = sentenciaSQL.executeQuery()) {

            while (resultadoConsulta.next()) {
                EstudianteAsignado estudianteAsignado = new EstudianteAsignado(
                    resultadoConsulta.getString("matricula"),
                    resultadoConsulta.getString("nombre_estudiante"),
                    resultadoConsulta.getString("nombre_proyecto"),
                    resultadoConsulta.getString("razon_social_organizacion")
                );
                lista.add(estudianteAsignado);
            }

        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
        }

        return lista;
    }
}