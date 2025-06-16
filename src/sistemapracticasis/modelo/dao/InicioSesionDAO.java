package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.Usuario;

/**
 *
 * @author uriel
 */
public class InicioSesionDAO {
    public static Usuario verificarCredenciales(String username, String password) throws SQLException {
        Usuario usuarioSesion = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        
        if (conexionBD != null) {
            String consulta = "SELECT id_usuario, usuario, password, tipo FROM usuario WHERE usuario = ?";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, username);
            ResultSet resultado = sentencia.executeQuery();
            
            if (resultado.next()) {
                String hashAlmacenado = resultado.getString("password");

                if (BCrypt.checkpw(password, hashAlmacenado)) {
                    usuarioSesion = new Usuario();
                    usuarioSesion.setIdUsuario(resultado.getInt("id_usuario"));
                    usuarioSesion.setUsername(resultado.getString("usuario"));
                    usuarioSesion.setTipo(resultado.getString("tipo"));
                }
            }
            
            resultado.close();
            sentencia.close();
            conexionBD.close();
        } else {
            throw new SQLException("Error: Sin conexión a la Base de datos.");
        }
        
        return usuarioSesion;
    }
        
    private static Usuario convertirRegistroUsuario(ResultSet resultado) 
                throws SQLException {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(resultado.getInt("id_usuario"));
            usuario.setUsername(resultado.getString("usuario"));
            usuario.setTipo(resultado.getString("tipo"));
            return usuario;
        }
}
