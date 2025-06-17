package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.util.Utilidad;

public class ObservacionDAO {

    public static int insertarObservacion(String descripcion) {
        int idGenerado = -1;
        String consulta = "INSERT INTO observacion (descripcion, fecha_observacion) "
                        + "VALUES (?, CURDATE())";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentenciaSQL = conexion.prepareStatement(
                 consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {

            sentenciaSQL.setString(1, descripcion);
            int filas = sentenciaSQL.executeUpdate();

            if (filas > 0) {
                try (ResultSet resultadoClave = sentenciaSQL.getGeneratedKeys()) {
                    if (resultadoClave.next()) {
                        idGenerado = resultadoClave.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
        }

        return idGenerado;
    }

    public static boolean actualizarEntregaDocumento(int idDocumento, int idObservacion) {
        String consulta = "UPDATE entrega_documento SET id_observacion = ? "
                        + "WHERE id_entrega_documento = ("
                        + "SELECT id_entrega_documento FROM documento "
                        + "WHERE id_documento = ?)";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta)) {

            sentenciaSQL.setInt(1, idObservacion);
            sentenciaSQL.setInt(2, idDocumento);

            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
        }

        return false;
    }

    public static boolean actualizarEntregaReporte(int idReporte, int idObservacion) {
        String consulta = "UPDATE entrega_reporte SET id_observacion = ? "
                        + "WHERE id_entrega_reporte = ("
                        + "SELECT id_entrega_reporte FROM reporte "
                        + "WHERE idreporte = ?)";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta)) {

            sentenciaSQL.setInt(1, idObservacion);
            sentenciaSQL.setInt(2, idReporte);

            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
        }

        return false;
    }
}
