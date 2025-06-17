package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;

import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.EntregaReporte;
import sistemapracticasis.util.Utilidad;

public class EntregaReporteDAO {

    public static boolean validarEntregaReporte(int idReporte, float calificacion) {
        String consulta = "UPDATE entrega_reporte er "
            + "JOIN reporte r ON er.id_entrega_reporte = r.id_entrega_reporte "
            + "SET er.validado = 1, er.calificacion = ? "
            + "WHERE r.idreporte = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setFloat(1, calificacion);
            sentencia.setInt(2, idReporte);

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
            return false;
        }
    }

    public static ArrayList<EntregaReporte> generarEntregasReportesPorDefecto(String fechaInicio, String fechaFin) {
        ArrayList<EntregaReporte> entregas = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            EntregaReporte entrega = new EntregaReporte(
                0, 
                "Entrega " + i, 
                fechaInicio, 
                fechaFin, 
                0, 
                0.0, 
                0, 
                0
            );
            entrega.setObservacion("Observación para entrega " + i);
            entregas.add(entrega);
        }

        return entregas;
    }

    public static void guardarEntregasReportes(
            ArrayList<EntregaReporte> entregas, 
            String fechaInicioPeriodo, 
            String fechaFinPeriodo) {

        String obtenerExpedientesSQL = "SELECT DISTINCT p.id_expediente "
            + "FROM periodo p WHERE p.fecha_inicio = ? AND p.fecha_fin = ?";
        String insertarObservacionSQL = "INSERT INTO observacion "
            + "(descripcion, fecha_observacion) VALUES (?, CURDATE())";
        String insertarEntregaSQL = "INSERT INTO entrega_reporte("
            + "nombre, fecha_inicio, fecha_fin, validado, calificacion, "
            + "id_expediente, id_observacion) VALUES (?, ?, ?, 0, ?, ?, ?)";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement stmtExpedientes = conexion.prepareStatement(obtenerExpedientesSQL);
             PreparedStatement stmtInsertObs = conexion.prepareStatement(insertarObservacionSQL, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtInsertEntrega = conexion.prepareStatement(insertarEntregaSQL)) {

            stmtExpedientes.setString(1, fechaInicioPeriodo);
            stmtExpedientes.setString(2, fechaFinPeriodo);
            ResultSet rs = stmtExpedientes.executeQuery();

            ArrayList<Integer> expedientes = new ArrayList<>();
            while (rs.next()) {
                expedientes.add(rs.getInt("id_expediente"));
            }

            if (expedientes.isEmpty()) {
                Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.WARNING, 
                    "Sin expedientes", 
                    "No se encontraron expedientes para el periodo actual.");
                return;
            }

            for (EntregaReporte entrega : entregas) {
                stmtInsertObs.setString(1, entrega.getObservacion());
                stmtInsertObs.executeUpdate();
                ResultSet generatedKeys = stmtInsertObs.getGeneratedKeys();
                int idObservacion = 0;
                if (generatedKeys.next()) {
                    idObservacion = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID de la observación insertada.");
                }

                for (int idExpediente : expedientes) {
                    stmtInsertEntrega.setString(1, entrega.getNombre());
                    stmtInsertEntrega.setString(2, entrega.getFechaInicio());
                    stmtInsertEntrega.setString(3, entrega.getFechaFin());
                    stmtInsertEntrega.setDouble(4, entrega.getCalificacion());
                    stmtInsertEntrega.setInt(5, idExpediente);
                    stmtInsertEntrega.setInt(6, idObservacion);
                    stmtInsertEntrega.addBatch();
                }
            }

            stmtInsertEntrega.executeBatch();

        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Error al guardar",
                "No se pudieron guardar las entregas de reportes: " + e.getMessage()
            );
            throw new RuntimeException("Error al guardar entregas de reportes", e);
        }
    }

    public static boolean existenEntregasDeReportesParaPeriodo(String fechaInicio, String fechaFin) {
        String sql = "SELECT COUNT(*) FROM entrega_reporte er "
            + "JOIN periodo p ON er.id_expediente = p.id_expediente "
            + "WHERE p.fecha_inicio = ? AND p.fecha_fin = ?";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
        }

        return false;
    }
}
