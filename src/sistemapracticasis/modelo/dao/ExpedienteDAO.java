package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.Alert;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.EstadoExpediente;
import sistemapracticasis.modelo.pojo.Expediente;
import sistemapracticasis.util.Utilidad;

/**
 * Clase DAO para gestionar las operaciones relacionadas con expedientes en la 
 * base de datos.
 * Autor: Uriel Cendón
 * Fecha de creación: 15/06/2025
 * Descripción: Proporciona métodos para verificar, actualizar, obtener e 
 * insertar expedientes de estudiantes.
 */
public class ExpedienteDAO {

    /**
     * Verifica si un estudiante tiene un expediente en curso.
     * @param matriculaEstudiante Matrícula del estudiante a verificar.
     * @return true si el estudiante tiene un expediente en curso, false en caso 
     *         contrario.
     */
    public boolean tieneExpedienteEnCurso(String matriculaEstudiante) {
        String consulta = "SELECT exp.id_expediente "
            + "FROM expediente exp "
            + "INNER JOIN estudiante est ON exp.id_estudiante = est.id_estudiante "
            + "INNER JOIN periodo per ON exp.id_periodo = per.id_periodo "
            + "INNER JOIN periodo_cursante pc ON per.id_periodo = pc.id_periodo "
            + "AND est.id_estudiante = pc.id_estudiante "
            + "WHERE est.matricula = ? "
            + "AND exp.estado = 'en curso' "
            + "AND CURDATE() BETWEEN per.fecha_inicio AND per.fecha_fin";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setString(1, matriculaEstudiante);

            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next();
            }

        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }

        return false;
    }

    /**
     * Actualiza la calificación por evaluación de organización vinculada en un 
     * expediente.
     * @param calificacion Calificación a actualizar.
     * @param idExpediente ID del expediente a actualizar.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public static boolean actualizarCalifPorEvaluacionOrgVin(double calificacion, 
            int idExpediente) {
        boolean actualizado = false;
        String consulta = "UPDATE expediente SET calif_eval_org_vinc = ? WHERE id_expediente = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setDouble(1, calificacion);
            sentencia.setInt(2, idExpediente);

            actualizado = sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }

        return actualizado;
    }

    /**
     * Obtiene el expediente más reciente de un estudiante.
     * @param idEstudiante ID del estudiante del cual obtener el expediente.
     * @return Objeto Expediente con los datos encontrados, o null si no se 
     *         encuentra.
     */
    public static Expediente obtenerExpedientePorIdEstudiante(int idEstudiante) {
        String consulta = "SELECT e.id_expediente, e.horas_acumuladas, e.estado " +
            "FROM expediente e " +
            "INNER JOIN periodo p ON e.id_periodo = p.id_periodo " +
            "WHERE e.id_estudiante = ? " +
            "AND CURDATE() BETWEEN p.fecha_inicio AND p.fecha_fin " +
            "ORDER BY p.fecha_inicio DESC " +
            "LIMIT 1";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idEstudiante);
            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {
                String estadoTexto = resultado.getString("estado");
                return new Expediente(
                    resultado.getInt("id_expediente"),
                    resultado.getInt("horas_acumuladas"),
                    EstadoExpediente.fromValor(estadoTexto)
                );
            } else {
                System.out.println("No se encontró expediente activo para el "
                    + "estudiante con ID " + idEstudiante);
            }

        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        } catch (IllegalArgumentException e) {
            System.out.println("Error en estado del expediente");
        }

        return null;
    }


    /**
     * Inserta un nuevo expediente vacío en la base de datos.
     * @return ID del expediente creado, o -1 si ocurrió un error.
     */
    public static int insertarExpedienteVacio() {
        String consulta = "INSERT INTO expediente (horas_acumuladas, estado) VALUES "
            + "(0, 'en curso')";

        try (Connection conexion = ConexionBD.abrirConexion();
            PreparedStatement sentencia = conexion.prepareStatement(consulta, Statement.
                RETURN_GENERATED_KEYS)) {

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = sentencia.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }

        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }

        return -1;
    }
    
    /**
     * Actualiza el expediente asociado a un estudiante en el periodo actual.
     * @param idEstudiante ID del estudiante
     * @param idExpediente ID del expediente a asociar
     * @return true si la actualización fue exitosa, false en caso contrario
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static boolean insertarEstudianteEnExpediente(int idEstudiante, int idExpediente) 
            throws SQLException {
        String consultaPeriodoCursante = "SELECT p.id_periodo FROM periodo_cursante pc "
            + "JOIN periodo p ON p.id_periodo = pc.id_periodo "
            + "WHERE pc.id_estudiante = ? AND CURDATE() BETWEEN p.fecha_inicio AND p.fecha_fin";

        String consultaUpdateExpediente = "UPDATE expediente SET id_estudiante = ?, "
                + "id_periodo = ?, horas_acumuladas = 0, estado = 'en curso' "
                + "WHERE id_expediente = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentenciaPeriodo = conexion.prepareStatement
                (consultaPeriodoCursante)) {

            sentenciaPeriodo.setInt(1, idEstudiante);
            try (ResultSet resultado = sentenciaPeriodo.executeQuery()) {
                if (resultado.next()) {
                    int idPeriodo = resultado.getInt("id_periodo");

                    try (PreparedStatement sentenciaActualizar = conexion.prepareStatement
                            (consultaUpdateExpediente)) {
                        sentenciaActualizar.setInt(1, idEstudiante);
                        sentenciaActualizar.setInt(2, idPeriodo);
                        sentenciaActualizar.setInt(3, idExpediente);

                        return sentenciaActualizar.executeUpdate() > 0;
                    }
                } else {
                    return false;
                }
            }
        }
    }

    /**
     * Obtiene el ID de expediente asociado a un estudiante.
     * @param idEstudiante ID del estudiante
     * @return ID del expediente asociado o null si no existe
     */
    public static Integer obtenerIdExpedientePorEstudiante(int idEstudiante) {
        Integer idExpediente = null;
        String consulta = "SELECT id_expediente FROM expediente "
        + "INNER JOIN periodo ON expediente.id_periodo = periodo.id_periodo "
        + "WHERE expediente.id_estudiante = ? AND periodo.fecha_fin > CURDATE()";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idEstudiante);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    idExpediente = resultado.getInt("id_expediente");
                }
            }
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }
        return idExpediente;
    }
}
