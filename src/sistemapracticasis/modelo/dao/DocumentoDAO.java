package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.EntregaVisual;
import sistemapracticasis.util.Utilidad;

/**
 * Clase DAO para manejar las operaciones relacionadas con documentos en la base
 * de datos.
 * Autor: Uriel Cendón
 * Fecha de creación: 15/06/2025
 * Descripción: Proporciona métodos para gestionar documentos, incluyendo 
 * verificación de entregas, almacenamiento y recuperación de documentos.
 */
public class DocumentoDAO {

    /**Add commentMore actions
     * Verifica si un documento ya ha sido entregado por un estudiante.
     * @param idEntregaDocumento ID de la entrega de documento a verificar.
     * @param idEstudiante ID del estudiante a verificar.
     * @return true si el documento ya fue entregado, false en caso contrario.
     */
    public static boolean documentoYaEntregado(int idEntregaDocumento, 
            int idEstudiante) {
        String consulta = "SELECT d.fecha_entregado "
            + "FROM documento d "
            + "JOIN entrega_documento ed ON d.id_entrega_documento = ed."
            + "id_entrega_documento "
            + "JOIN expediente e ON ed.id_expediente = e.id_expediente "
            + "JOIN periodo_cursante pc ON e.id_estudiante = pc.id_estudiante "
            + "AND e.id_periodo = pc.id_periodo "
            + "WHERE d.id_entrega_documento = ? AND p.id_estudiante = ?";
        
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {
            
            sentencia.setInt(1, idEntregaDocumento);
            sentencia.setInt(2, idEstudiante);
            ResultSet resultado = sentencia.executeQuery();
            
            if (resultado.next()) {
                return resultado.getDate("fecha_entregado") != null;
            }
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }
        return false;
    }

    /**
     * Guarda un documento inicial en la base de datos.
     * @param idEntregaDocumento ID de la entrega asociada al documento.
     * @param nombreDocumento Nombre del documento a guardar.
     * @param documento Contenido del documento en bytes.
     * @return true si el documento se guardó correctamente, false en caso
     *         contrario.
     */
    public static boolean guardarDocumentoInicial(int idEntregaDocumento, 
        String nombreDocumento, byte[] documento) {
        String consulta = "INSERT INTO documento (nombre_documento, "
            + "fecha_entregado, documento, id_entrega_documento) "
            + "VALUES (?, NOW(), ?, ?)";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {

            sentencia.setString(1, nombreDocumento);
            sentencia.setBytes(2, documento);
            sentencia.setInt(3, idEntregaDocumento);

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
            "Error con la base de datos");
            return false;
        }
    }

    /**
     * Obtiene todos los documentos asociados a un expediente.
     * @param idExpediente ID del expediente del cual obtener los documentos.
     * @return Lista de objetos EntregaVisual con la información de los 
     *         documentos.
     */
    public static List<EntregaVisual> obtenerDocumentosPorIdExpediente
            (int idExpediente) {
        List<EntregaVisual> lista = new ArrayList<>();

        String consulta = "SELECT d.id_documento, d.nombre_documento, "
            + "d.fecha_entregado FROM documento d "
            + "JOIN entrega_documento ed ON d.id_entrega_documento = ed. id_entrega_documento "
            + "WHERE ed.id_expediente = ? AND d.fecha_entregado IS NOT NULL";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idExpediente);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    int id = resultado.getInt("id_documento");
                    String nombre = resultado.getString("nombre_documento");
                    String fecha = resultado.getString("fecha_entregado");

                    lista.add(new EntregaVisual(id, nombre, fecha, 
                        "documento"));
                }
            }

        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }

        return lista;
    }

    /**
     * Obtiene el archivo de un documento por su ID.
     * @param idDocumento ID del documento a recuperar.
     * @return Contenido del documento en bytes, o null si no se encuentra.
     */
    public static byte[] obtenerArchivoPorId(int idDocumento) {
        byte[] datos = null;

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(
                "SELECT documento FROM documento WHERE id_documento = ?")) {

            sentencia.setInt(1, idDocumento);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    datos = resultado.getBytes("documento");
                }
            }

        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }

        return datos;
    }

    /**
     * Obtiene la calificación asociada a un documento.
     * @param idDocumento ID del documento a consultar.
     * @return Calificación del documento, o null si no tiene calificación.
     */
    public static Double obtenerCalificacionPorId(int idDocumento) {
        Double calificacion = null;
        String consulta = "SELECT ed.calificacion FROM documento d "
            + "JOIN entrega_documento ed ON d.id_entrega_documento = ed."
            + "id_entrega_documento "
            + "WHERE d.id_documento = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idDocumento);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                calificacion = resultado.getDouble("calificacion");
                if (resultado.wasNull()) {
                    calificacion = null;
                }
            }

        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }

        return calificacion;
    }

    /**
     * Verifica si un documento tiene observaciones asociadas.
     * @param idDocumento ID del documento a verificar.
     * @return true si el documento tiene observaciones, false en caso contrario.
     */
    public static boolean tieneObservacion(int idDocumento) {
        String consulta = "SELECT ed.id_observacion FROM documento d "
            + "JOIN entrega_documento ed ON d.id_entrega_documento = ed."
            + "id_entrega_documento "
            + "WHERE d.id_documento = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idDocumento);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getObject("id_observacion") != null;
                }
            }

        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }

        return false;
    }
}
