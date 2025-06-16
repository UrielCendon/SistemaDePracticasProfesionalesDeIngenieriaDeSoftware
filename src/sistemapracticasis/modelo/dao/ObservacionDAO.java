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
public class ObservacionDAO {
    public static int insertarObservacion(String descripcion) {
        int idGenerado = -1;
        String consulta = "INSERT INTO observacion (descripcion, "
            + "fecha_observacion) VALUES (?, CURDATE())";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            sentencia.setString(1, descripcion);
            int filas = sentencia.executeUpdate();

            if (filas > 0) {
                try (ResultSet resultado = sentencia.getGeneratedKeys()) {
                    if (resultado.next()) {
                        idGenerado = resultado.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return idGenerado;
    }

    public static boolean actualizarEntregaDocumento(int idDocumento, 
                int idObservacion) {
        String consulta = "UPDATE entrega_documento SET id_observacion = ? "
            + "WHERE id_entrega_documento = (SELECT id_entrega_documento "
            + "FROM documento WHERE id_documento = ?)";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)){

            sentencia.setInt(1, idObservacion);
            sentencia.setInt(2, idDocumento);

            return sentencia.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
    
    public static boolean actualizarEntregaReporte(int idReporte, 
                int idObservacion) {
        String consulta = "UPDATE entrega_reporte SET id_observacion = ? "
            + "WHERE id_entrega_reporte = (SELECT id_entrega_reporte "
            + "FROM reporte WHERE idreporte = ?)";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)){

            sentencia.setInt(1, idObservacion);
            sentencia.setInt(2, idReporte);

            return sentencia.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

}
