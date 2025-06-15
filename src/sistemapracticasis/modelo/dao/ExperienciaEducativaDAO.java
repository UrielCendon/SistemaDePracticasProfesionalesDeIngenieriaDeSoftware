package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.ExperienciaEducativa;

/**
 *
 * @author uriel
 */
public class ExperienciaEducativaDAO {
    public static ExperienciaEducativa obtenerEEPorIdEstudiante
            (int idEstudiante) {
        ExperienciaEducativa experiencia = null;
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            String consulta = "SELECT ee.id_experiencia_educativa, ee.nrc, "
                + "ee.nombre, ee.bloque, ee.seccion "
                + "FROM experiencia_educativa ee "
                + "JOIN estudiante e ON ee.id_experiencia_educativa = "
                + "e.id_experiencia_educativa "
                + "WHERE e.id_estudiante = ?";

            try {
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setInt(1, idEstudiante);
                ResultSet resultado = sentencia.executeQuery();

                if (resultado.next()) {
                    experiencia = new ExperienciaEducativa();
                    experiencia.setIdEE(resultado.getInt
                        ("id_experiencia_educativa"));
                    experiencia.setNombre(resultado.getString("nombre"));
                    experiencia.setNrc(resultado.getString("nrc"));
                    experiencia.setBloque(resultado.getString("bloque"));
                    experiencia.setSeccion(resultado.getString("seccion"));
                }

                resultado.close();
                sentencia.close();
                conexion.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return experiencia;
    }
}
