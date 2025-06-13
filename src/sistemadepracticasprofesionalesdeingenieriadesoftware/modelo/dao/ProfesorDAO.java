package sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.dao;

import java.sql.*;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.conexion.ConexionBD;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.pojo.Profesor;

public class ProfesorDAO {
    public static Profesor obtenerProfesorPorIdUsuario(int idUsuario) throws SQLException {
        Profesor profesor = null;
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            String consulta = "SELECT * FROM profesor WHERE id_usuario = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, idUsuario);
            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {
                profesor = new Profesor();
                profesor.setIdProfesor(resultado.getInt("id_profesor"));
                profesor.setNumPersonal(resultado.getInt("num_personal"));
                profesor.setNombre(resultado.getString("nombre"));
                profesor.setCorreo(resultado.getString("correo"));
                profesor.setApellidoPaterno(resultado.getString("apellido_paterno"));
                profesor.setApellidoMaterno(resultado.getString("apellido_materno"));
                profesor.setIdUsuario(resultado.getInt("id_usuario"));
                // Agrega más campos si los necesitas
            }

            resultado.close();
            sentencia.close();
            conexion.close();
        }

        return profesor;
    }
}
