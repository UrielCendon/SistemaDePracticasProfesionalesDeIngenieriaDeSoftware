package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.EntregaVisual;
import sistemapracticasis.util.Utilidad;
import javafx.scene.control.Alert;

public class ReporteDAO {

    public static List<EntregaVisual> obtenerReportesPorIdExpediente(int idExpediente) {
        List<EntregaVisual> lista = new ArrayList<>();
        String consulta = "SELECT r.idreporte, r.nombre_reporte, r.fecha_entregado "
                + "FROM reporte r JOIN entrega_reporte er ON r.id_entrega_reporte = "
                + "er.id_entrega_reporte WHERE er.id_expediente = ? "
                + "AND r.fecha_entregado IS NOT NULL";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idExpediente);
            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    lista.add(new EntregaVisual(
                            resultado.getInt("idreporte"),
                            resultado.getString("nombre_reporte"),
                            resultado.getString("fecha_entregado"),
                            "reporte"
                    ));
                }
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
        }
        return lista;
    }

    public static byte[] obtenerArchivoPorId(int idReporte) {
        byte[] datos = null;
        String consulta = "SELECT documento FROM reporte WHERE idreporte = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idReporte);
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

    public static Double obtenerCalificacionPorId(int idReporte) {
        Double calificacion = null;
        String consulta = "SELECT er.calificacion FROM reporte r "
                + "JOIN entrega_reporte er ON r.id_entrega_reporte = "
                + "er.id_entrega_reporte WHERE r.idreporte = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idReporte);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    calificacion = resultado.getDouble("calificacion");
                    if (resultado.wasNull()) {
                        calificacion = null;
                    }
                }
            }
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
        }
        return calificacion;
    }

    public static boolean tieneObservacion(int idReporte) {
        String consulta = "SELECT er.id_observacion FROM reporte r "
                + "JOIN entrega_reporte er ON r.id_entrega_reporte = "
                + "er.id_entrega_reporte WHERE r.idreporte = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idReporte);
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
