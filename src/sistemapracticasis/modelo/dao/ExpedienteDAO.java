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
            + "JOIN periodo per ON exp.id_expediente = per.id_expediente "
            + "JOIN estudiante est ON per.id_estudiante = est.id_estudiante "
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
        String consulta = "SELECT e.id_expediente, e.horas_acumuladas, e.estado "
            + "FROM expediente e "
            + "JOIN periodo p ON e.id_expediente = p.id_expediente "
            + "WHERE p.id_estudiante = ? "
            + "ORDER BY p.fecha_inicio DESC LIMIT 1";

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
                System.out.println("No se encontró expediente para el estudiante con "
                    + "ID " + idEstudiante);
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
}
