package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import sistemapracticasis.modelo.conexion.ConexionBD;

/**
 *
 * @author uriel
 */
public class EntregaReporteDAO {
    public static boolean validarEntregaReporte(int idReporte, 
            float calificacion) {
        String consulta = "UPDATE entrega_reporte er "
            + "JOIN reporte r ON er.id_entrega_reporte = "
            + "r.id_entrega_reporte "
            + "SET er.validado = 1, er.calificacion = ? "
            + "WHERE r.idreporte = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)){

            sentencia.setFloat(1, calificacion);
            sentencia.setInt(2, idReporte);

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
