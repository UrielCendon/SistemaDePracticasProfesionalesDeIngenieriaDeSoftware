package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.util.Utilidad;

/**
 * Clase DAO para gestionar operaciones relacionadas con observaciones en la base de datos.
 * Autor: Uriel Cendón
 * Fecha de creación: 10/06/2025
 * Descripción: Proporciona métodos para insertar observaciones y actualizar su asociación
 * con entregas de documentos y reportes.
 */
public class ObservacionDAO {

    /**
     * Inserta una nueva observación en la base de datos.
     * @param descripcion Descripción de la observación a insertar
     * @return ID generado de la observación insertada, o -1 si falla la operación
     */
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
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }

        return idGenerado;
    }

    /**
     * Actualiza la asociación de una observación con una entrega de documento.
     * @param idDocumento ID del documento a actualizar
     * @param idObservacion ID de la observación a asociar
     * @return true si la actualización fue exitosa, false en caso contrario
     */
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
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }

        return false;
    }

    /**
     * Actualiza la asociación de una observación con una entrega de reporte.
     * @param idReporte ID del reporte a actualizar
     * @param idObservacion ID de la observación a asociar
     * @return true si la actualización fue exitosa, false en caso contrario
     */
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
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }

        return false;
    }
}
