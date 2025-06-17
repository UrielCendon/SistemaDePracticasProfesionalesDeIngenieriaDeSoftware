package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.EvaluacionEstudiante;
import sistemapracticasis.util.Utilidad;

/**
 * Clase DAO para gestionar las operaciones relacionadas con evaluaciones de
 * estudiantes en la base de datos.
 * Autor: Raziel Filobello
 * Fecha de creación: 15/06/2025
 * Descripción: Proporciona métodos para guardar evaluaciones de estudiantes y 
 * obtener listados de estudiantes no evaluados.
 */
public class EvaluacionEstudianteDAO {

    /**
     * Guarda una evaluación de estudiante en la base de datos.
     * @param evaluacion Objeto EvaluacionEstudiante con los datos a guardar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean guardarEvaluacion(EvaluacionEstudiante evaluacion) {
        String consulta = "INSERT INTO evaluacion_estudiante("
            + "fecha_entregado, puntaje_total, uso_tecnicas, seguridad, "
            + "contenido, ortografia, requisitos, id_observacion, id_expediente) "
            + "VALUES (CURDATE(), ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setDouble(1, evaluacion.getPuntajeTotal());
            sentencia.setInt(2, evaluacion.getUsoTecnicas());
            sentencia.setInt(3, evaluacion.getSeguridad());
            sentencia.setInt(4, evaluacion.getContenido());
            sentencia.setInt(5, evaluacion.getOrtografia());
            sentencia.setInt(6, evaluacion.getRequisitos());
            sentencia.setInt(7, evaluacion.getIdObservacion());
            sentencia.setInt(8, evaluacion.getIdExpediente());

            return sentencia.executeUpdate() > 0;

        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }

        return false;
    }

    /**
     * Obtiene una lista de estudiantes que no han sido evaluados en el periodo actual.
     * @return ArrayList de objetos Estudiante que no han sido evaluados.
     */
    public static ArrayList<Estudiante> obtenerEstudiantesNoEvaluados() {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();

        String consulta = "SELECT e.*, p.nombre AS nombre_proyecto, per.id_expediente "
            + "FROM estudiante e "
            + "JOIN proyecto p ON e.id_proyecto = p.id_proyecto "
            + "JOIN periodo per ON e.id_estudiante = per.id_estudiante "
            + "LEFT JOIN evaluacion_estudiante ev ON per.id_expediente = ev.id_expediente "
            + "WHERE ev.id_evaluacion_estudiante IS NULL "
            + "AND CURDATE() BETWEEN per.fecha_inicio AND per.fecha_fin";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Estudiante estudiante = new Estudiante();
                estudiante.setIdEstudiante(resultado.getInt("id_estudiante"));
                estudiante.setMatricula(resultado.getString("matricula"));
                estudiante.setNombre(resultado.getString("nombre"));
                estudiante.setApellidoPaterno(resultado.getString("apellido_paterno"));
                estudiante.setApellidoMaterno(resultado.getString("apellido_materno"));
                estudiante.setNombreProyecto(resultado.getString("nombre_proyecto"));
                estudiante.setIdPeriodo(resultado.getInt("id_expediente"));
                estudiantes.add(estudiante);
            }

        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }

        return estudiantes;
    }
}
