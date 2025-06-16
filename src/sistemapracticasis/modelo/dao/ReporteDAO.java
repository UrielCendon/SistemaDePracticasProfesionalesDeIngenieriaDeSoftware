package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.EntregaVisual;

/**
 *
 * @author uriel
 */
public class ReporteDAO {
    public static List<EntregaVisual> obtenerReportesPorIdExpediente(int idExpediente) {
        List<EntregaVisual> lista = new ArrayList<>();

        String consulta = "SELECT r.idreporte, r.nombre_reporte, "
            + "r.fecha_entregado FROM reporte r " 
            + "JOIN entrega_reporte er ON r.id_entrega_reporte = "
            + "er.id_entrega_reporte "
            + "WHERE er.id_expediente = ? AND r.fecha_entregado IS NOT NULL";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)){

            sentencia.setInt(1, idExpediente);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    int id = resultado.getInt("idreporte");
                    String nombre = resultado.getString("nombre_reporte");
                    String fecha = resultado.getString("fecha_entregado");

                    lista.add(new EntregaVisual(id, nombre, fecha, "reporte"));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }
    
    public static byte[] obtenerArchivoPorId(int idReporte) {
        byte[] datos = null;

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(
                "SELECT documento FROM reporte WHERE idreporte = ?")) {

            sentencia.setInt(1, idReporte);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    datos = resultado.getBytes("documento");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return datos;
    }
    
    public static Double obtenerCalificacionPorId(int idDocumento) {
        Double calificacion = null;
        String consulta = "SELECT er.calificacion FROM reporte r "
            + "JOIN entrega_reporte er ON r.id_entrega_reporte = "
            + "er.id_entrega_reporte WHERE r.idreporte = ?";
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)){
            sentencia.setInt(1, idDocumento);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                calificacion = resultado.getDouble("calificacion");
                if (resultado.wasNull()) {
                    calificacion = null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return false;
    }

}
