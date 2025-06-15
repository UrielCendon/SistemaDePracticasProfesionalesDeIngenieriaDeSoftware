package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.Profesor;

public class ProfesorDAO {
    public static Profesor obtenerProfesorPorIdUsuario(int idUsuario) 
            throws SQLException {
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
                profesor.setApellidoPaterno(resultado.
                    getString("apellido_paterno"));
                profesor.setApellidoMaterno(resultado.
                    getString("apellido_materno"));
                profesor.setIdUsuario(resultado.getInt("id_usuario"));    
            }

            resultado.close();
            sentencia.close();
            conexion.close();
        }

        return profesor;
    }
    
    public static Profesor obtenerProfesorPorIdEstudiante(int idEstudiante) {
        Profesor profesor = null;
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            String consulta = "SELECT p.id_profesor, p.num_personal, p.nombre, "
                + "p.correo, p.apellido_paterno, p.apellido_materno, "
                + "p.id_experiencia_educativa, p.id_usuario "
                + "FROM profesor p "
                + "JOIN experiencia_educativa ee ON p.id_experiencia_educativa "
                + "= ee.id_experiencia_educativa "
                + "JOIN estudiante e ON ee.id_experiencia_educativa "
                + "= e.id_experiencia_educativa "
                + "WHERE e.id_estudiante = ?";

            try {
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setInt(1, idEstudiante);
                ResultSet resultado = sentencia.executeQuery();

                if (resultado.next()) {
                    profesor = new Profesor();
                    profesor.setIdProfesor(resultado.getInt("id_profesor"));
                    profesor.setNombre(resultado.getString("nombre"));
                    profesor.setApellidoPaterno(resultado.getString
                        ("apellido_paterno"));
                    profesor.setApellidoMaterno(resultado.getString
                        ("apellido_materno"));
                    profesor.setNumPersonal(resultado.getInt("num_personal"));
                    profesor.setCorreo(resultado.getString("correo"));
                }

                resultado.close();
                sentencia.close();
                conexion.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return profesor;
    }
}
