package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.EntregaDocumento;
import sistemapracticasis.modelo.pojo.EntregaDocumentoTipo;
import sistemapracticasis.modelo.pojo.TipoDocumento;
import sistemapracticasis.util.Utilidad;

public class EntregaDocumentoDAO {

    public static boolean existeEntregaInicialVigente(int idEstudiante) {
        String consulta = "SELECT 1 FROM entrega_documento ed JOIN expediente e"
            + " ON ed.id_expediente = e.id_expediente JOIN periodo p "
            + "ON p.id_expediente = e.id_expediente JOIN estudiante est "
            + "ON est.id_estudiante = p.id_estudiante "
            + "WHERE est.id_estudiante = ? AND ed.tipo_entrega = 'inicial' "
            + "AND CURDATE() BETWEEN ed.fecha_inicio AND ed.fecha_fin "
            + "LIMIT 1";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {

            sentencia.setInt(1, idEstudiante);

            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean existenEntregasInicialesParaPeriodo(int idPeriodo) {
        String consulta = "SELECT 1 FROM entrega_documento WHERE tipo_entrega = 'inicial' AND id_expediente = ?";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {

            sentencia.setInt(1, idPeriodo);

            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<EntregaDocumento> obtenerEntregasInicialesPorExpediente(int idExpediente) {
        List<EntregaDocumento> entregas = new ArrayList<>();
        String consulta = "SELECT id_entrega_documento, nombre, "
            + "fecha_inicio, fecha_fin, validado, calificacion, "
            + "id_expediente, id_observacion FROM entrega_documento WHERE "
            + "id_expediente = ? AND tipo_entrega = 'inicial'";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {

            sentencia.setInt(1, idExpediente);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                entregas.add(new EntregaDocumento(
                    resultado.getInt("id_entrega_documento"),
                    resultado.getString("nombre"),
                    resultado.getString("fecha_inicio"),
                    resultado.getString("fecha_fin")
                ));
            }
            resultado.close();
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entregas;
    }

    public static ArrayList<EntregaDocumento> generarEntregasInicialesPorDefecto(String fechaInicio, String fechaFin) {
        ArrayList<EntregaDocumento> entregas = new ArrayList<>();
        for (TipoDocumento tipo : TipoDocumento.values()) {
            EntregaDocumento entrega = new EntregaDocumento(0, tipo.getValorEnDB(), fechaInicio, fechaFin);
            entrega.setCalificacion(0.0);
            entrega.setTipoEntrega(EntregaDocumentoTipo.INICIAL);
            entregas.add(entrega);
        }
        return entregas;
    }

    public static void guardarEntregasIniciales(ArrayList<EntregaDocumento> entregas, int idExpediente) {
        String consulta = "INSERT INTO entrega_documento(nombre, fecha_inicio, fecha_fin, tipo_entrega, calificacion, id_expediente) " +
                         "VALUES (?, ?, ?, 'inicial', ?, ?)";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement ps = conexion.prepareStatement(consulta)) {

            for (EntregaDocumento entrega : entregas) {
                ps.setString(1, entrega.getNombre());
                ps.setString(2, entrega.getFechaInicio());
                ps.setString(3, entrega.getFechaFin());
                ps.setDouble(4, entrega.getCalificacion());
                ps.setInt(5, idExpediente);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Error de base de datos",
                "No se pudieron guardar las entregas: " + e.getMessage()
            );
            throw new RuntimeException("Error al guardar las entregas iniciales", e);
        }
    }

    public static boolean validarEntregaDocumento(int idDocumento, float calificacion) {
        String consulta = "UPDATE entrega_documento ed "
            + "JOIN documento d ON ed.id_entrega_documento = d.id_entrega_documento "
            + "SET ed.validado = 1, ed.calificacion = ? "
            + "WHERE d.id_documento = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)){

            sentencia.setFloat(1, calificacion);
            sentencia.setInt(2, idDocumento);

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
