/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.conexion.ConexionBD;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.pojo.Usuario;

/**
 *
 * @author uriel
 */
public class InicioSesionDAO {
    public static Usuario verificarCredenciales(String username, 
            String password) throws SQLException{
        Usuario usuarioSesion = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if(conexionBD != null){
            String consulta = "SELECT id_usuario, usuario, tipo " +
                              "FROM usuario " +
                              "WHERE usuario = ? AND password = ?";

            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, username);
            sentencia.setString(2, password);
            ResultSet resultado = sentencia.executeQuery();
            if(resultado.next()){
                usuarioSesion = convertirRegistroUsuario(resultado);
            }
            resultado.close();
            sentencia.close();
            conexionBD.close();
        }else{
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
