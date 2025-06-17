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

public class DocumentoDAO {

    public static boolean documentoYaEntregado(int idEntregaDocumento, int idEstudiante) {
        String consulta = "SELECT d.fecha_entregado "
            + "FROM documento d "
            + "JOIN entrega_documento ed ON d.id_entrega_documento = ed.id_entrega_documento "
            + "JOIN expediente e ON ed.id_expediente = e.id_expediente "
            + "JOIN periodo p ON e.id_expediente = p.id_expediente "
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
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
        }
        return false;
    }

    public static boolean guardarDocumentoInicial(int idEntregaDocumento, String nombreDocumento, byte[] documento) {
        String consulta = "INSERT INTO documento (nombre_documento, fecha_entregado, documento, id_entrega_documento) "
                + "VALUES (?, NOW(), ?, ?)";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {

            sentencia.setString(1, nombreDocumento);
            sentencia.setBytes(2, documento);
            sentencia.setInt(3, idEntregaDocumento);

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
            return false;
        }
    }

    public static List<EntregaVisual> obtenerDocumentosPorIdExpediente(int idExpediente) {
        List<EntregaVisual> lista = new ArrayList<>();

        String consulta = "SELECT d.id_documento, d.nombre_documento, d.fecha_entregado "
            + "FROM documento d "
            + "JOIN entrega_documento ed ON d.id_entrega_documento = ed.id_entrega_documento "
            + "WHERE ed.id_expediente = ? AND d.fecha_entregado IS NOT NULL";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idExpediente);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    int id = resultado.getInt("id_documento");
                    String nombre = resultado.getString("nombre_documento");
                    String fecha = resultado.getString("fecha_entregado");

                    lista.add(new EntregaVisual(id, nombre, fecha, "documento"));
                }
            }

        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
        }

        return lista;
    }

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
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
        }

        return datos;
    }

    public static Double obtenerCalificacionPorId(int idDocumento) {
        Double calificacion = null;
        String consulta = "SELECT ed.calificacion FROM documento d "
            + "JOIN entrega_documento ed ON d.id_entrega_documento = ed.id_entrega_documento "
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
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
        }

        return calificacion;
    }

    public static boolean tieneObservacion(int idDocumento) {
        String consulta = "SELECT ed.id_observacion FROM documento d "
            + "JOIN entrega_documento ed ON d.id_entrega_documento = ed.id_entrega_documento "
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
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
        }

        return false;
    }
}
