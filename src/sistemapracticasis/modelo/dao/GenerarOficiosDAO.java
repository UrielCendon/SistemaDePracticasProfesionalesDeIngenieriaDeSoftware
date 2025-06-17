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

public class GenerarOficiosDAO {

    public static List<EstudianteAsignado> obtenerEstudiantesAsignadosPeriodoActual() {
        List<EstudianteAsignado> lista = new ArrayList<>();

        String consulta = "SELECT e.matricula, "
            + "CONCAT(e.nombre, ' ', e.apellido_paterno, ' ', e.apellido_materno) AS nombre_estudiante, "
            + "p.nombre AS nombre_proyecto, "
            + "ov.razon_social AS razon_social_organizacion "
            + "FROM estudiante e "
            + "JOIN proyecto p ON e.id_proyecto = p.id_proyecto "
            + "JOIN organizacion_vinculada ov ON p.id_organizacion_vinculada = ov.id_organizacion_vinculada "
            + "JOIN periodo pe ON e.id_estudiante = pe.id_estudiante "
            + "JOIN expediente exp ON pe.id_expediente = exp.id_expediente "
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
