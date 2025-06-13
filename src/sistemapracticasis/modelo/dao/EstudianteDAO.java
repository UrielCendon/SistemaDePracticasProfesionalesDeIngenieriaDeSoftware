package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.Estudiante;

public class EstudianteDAO {
    public static Estudiante obtenerEstudiantePorIdUsuario(int idUsuario) throws SQLException {
        Estudiante estudiante = null;
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            String consulta = "SELECT * FROM estudiante WHERE id_usuario = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, idUsuario);
            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {
                estudiante = new Estudiante();
                estudiante.setIdEstudiante(resultado.getInt("id_estudiante"));
                estudiante.setMatricula(resultado.getString("matricula"));
                estudiante.setNombre(resultado.getString("nombre"));
                estudiante.setCorreo(resultado.getString("correo"));
                estudiante.setTelefono(resultado.getString("telefono"));
                estudiante.setApellidoPaterno(resultado.getString("apellido_paterno"));
                estudiante.setApellidoMaterno(resultado.getString("apellido_materno"));
                estudiante.setIdUsuario(resultado.getInt("id_usuario"));
            }

            resultado.close();
            sentencia.close();
            conexion.close();
        }

        return estudiante;
    }
}
