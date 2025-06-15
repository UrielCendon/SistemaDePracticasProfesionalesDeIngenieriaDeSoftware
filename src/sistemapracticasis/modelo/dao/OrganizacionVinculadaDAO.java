package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.OrganizacionVinculada;

/**
 *
 * @author uriel
 */
public class OrganizacionVinculadaDAO {
    public static OrganizacionVinculada 
        obtenerOrganizacionVinculadaPorEstudiante(int idEstudiante) {
            OrganizacionVinculada organizacionVinculada = null;
            String consulta = "SELECT ov.razon_social "
                + "FROM organizacion_vinculada ov "
                + "JOIN proyecto p ON ov.id_organizacion_vinculada = "
                + "p.id_organizacion_vinculada "
                + "JOIN estudiante e ON p.id_proyecto = e.id_proyecto "
                + "WHERE e.id_estudiante = ?";

            try (Connection conn = ConexionBD.abrirConexion();
                 PreparedStatement sentencia = conn.prepareStatement(consulta)){

                sentencia.setInt(1, idEstudiante);

                try (ResultSet resultado = sentencia.executeQuery()) {
                    if (resultado.next()) {
                        organizacionVinculada = new OrganizacionVinculada();
                        organizacionVinculada.setRazonSocial(resultado.getString
                            ("razon_social"));
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            return organizacionVinculada;
    }
        
    public static Integer obtenerIdExpedientePorEstudiante(int idEstudiante) {
        Integer idExpediente = null;
        String query = "SELECT id_expediente FROM periodo WHERE id_estudiante "
            + "= ?";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idExpediente = rs.getInt("id_expediente");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return idExpediente;
    }

}
