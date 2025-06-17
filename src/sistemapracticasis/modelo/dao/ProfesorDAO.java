package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.Profesor;

/**
 * Clase DAO para gestionar operaciones relacionadas con profesores.
 * Autor: Miguel Escobar
 * Fecha de creación: 16/06/2025
 * Descripción: Proporciona métodos para obtener información de profesores
 * asociados a usuarios o estudiantes.
 */
public class ProfesorDAO {

    /**
     * Obtiene un profesor por su ID de usuario asociado.
     * @param idUsuario ID del usuario asociado al profesor
     * @return Objeto Profesor con los datos encontrados
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static Profesor obtenerProfesorPorIdUsuario(int idUsuario) throws SQLException {
        Profesor profesor = null;
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(
                 "SELECT * FROM profesor WHERE id_usuario = ?");
             ResultSet resultado = sentencia.executeQuery()) {
            
            sentencia.setInt(1, idUsuario);
            
            if (resultado.next()) {
                profesor = new Profesor();
                profesor.setIdProfesor(resultado.getInt("id_profesor"));
                profesor.setNumPersonal(resultado.getInt("num_personal"));
                profesor.setNombre(resultado.getString("nombre"));
                profesor.setCorreo(resultado.getString("correo"));
                profesor.setApellidoPaterno(resultado.getString("apellido_paterno"));
                profesor.setApellidoMaterno(resultado.getString("apellido_materno"));
                profesor.setIdUsuario(resultado.getInt("id_usuario"));    
            }
        }
        
        return profesor;
    }
    
    /**
     * Obtiene el profesor asociado a un estudiante.
     * @param idEstudiante ID del estudiante
     * @return Objeto Profesor con los datos encontrados o null si no existe
     */
    public static Profesor obtenerProfesorPorIdEstudiante(int idEstudiante) {
        Profesor profesor = null;
        String consulta = "SELECT p.id_profesor, p.num_personal, p.nombre, "
            + "p.correo, p.apellido_paterno, p.apellido_materno, "
            + "p.id_experiencia_educativa, p.id_usuario "
            + "FROM profesor p "
            + "JOIN experiencia_educativa ee ON p.id_experiencia_educativa = "
            + "ee.id_experiencia_educativa "
            + "JOIN estudiante e ON ee.id_experiencia_educativa = "
            + "e.id_experiencia_educativa "
            + "WHERE e.id_estudiante = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {
            
            sentencia.setInt(1, idEstudiante);
            
            if (resultado.next()) {
                profesor = new Profesor();
                profesor.setIdProfesor(resultado.getInt("id_profesor"));
                profesor.setNombre(resultado.getString("nombre"));
                profesor.setApellidoPaterno(resultado.getString("apellido_paterno"));
                profesor.setApellidoMaterno(resultado.getString("apellido_materno"));
                profesor.setNumPersonal(resultado.getInt("num_personal"));
                profesor.setCorreo(resultado.getString("correo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return profesor;
    }
}