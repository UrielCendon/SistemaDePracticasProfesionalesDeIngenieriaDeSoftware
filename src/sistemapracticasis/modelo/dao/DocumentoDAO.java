package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistemapracticasis.modelo.conexion.ConexionBD;

/**
 *
 * @author uriel
 */
public class DocumentoDAO {
    public static boolean documentoYaEntregado
            (int idEntregaDocumento, int idEstudiante) {
        String consulta = "SELECT d.fecha_entregado "
            + "FROM documento d "
            + "JOIN entrega_documento ed ON d.id_entrega_documento = "
            + "ed.id_entrega_documento "
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
            e.printStackTrace();
        }
        return false;
    }
    
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
            e.printStackTrace();
            return false;
        }
    }
}
