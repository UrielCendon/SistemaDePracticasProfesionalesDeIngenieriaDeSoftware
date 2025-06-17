package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.Usuario;

/**
 * Clase DAO para gestionar el inicio de sesión de usuarios.
 * Autor: Uriel Cendón
 * Fecha de creación: 10/06/2025
 * Descripción: Proporciona métodos para verificar credenciales de usuario 
 * utilizando autenticación segura con BCrypt.
 */
public class InicioSesionDAO {

    /**
     * Verifica las credenciales de un usuario contra la base de datos.
     * @param username Nombre de usuario a verificar
     * @param password Contraseña a verificar
     * @return Objeto Usuario si las credenciales son válidas, null en caso contrario
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static Usuario verificarCredenciales(String username, String password) 
            throws SQLException {
        Usuario usuarioSesion = null;
        
        try (Connection conexionBD = ConexionBD.abrirConexion()) {
            if (conexionBD == null) {
                throw new SQLException("Error: Sin conexión a la Base de datos.");
            }

            String consulta = "SELECT id_usuario, usuario, password, tipo "
                + "FROM usuario WHERE usuario = ?";
            
            try (PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {
                sentencia.setString(1, username);
                
                try (ResultSet resultado = sentencia.executeQuery()) {
                    if (resultado.next()) {
                        String hashAlmacenado = resultado.getString("password");

                        if (BCrypt.checkpw(password, hashAlmacenado)) {
                            usuarioSesion = convertirRegistroUsuario(resultado);
                        }
                    }
                }
            }
        }
        
        return usuarioSesion;
    }
        
    /**
     * Convierte un registro de ResultSet a objeto Usuario.
     * @param resultado ResultSet con los datos del usuario
     * @return Objeto Usuario con los datos del resultado
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private static Usuario convertirRegistroUsuario(ResultSet resultado) 
            throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(resultado.getInt("id_usuario"));
        usuario.setUsername(resultado.getString("usuario"));
        usuario.setTipo(resultado.getString("tipo"));
        return usuario;
    }
}