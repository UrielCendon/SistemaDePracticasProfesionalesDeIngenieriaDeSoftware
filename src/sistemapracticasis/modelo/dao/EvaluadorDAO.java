package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.Evaluador;

/**
 * Clase DAO para gestionar las operaciones relacionadas con evaluadores en la 
 * base de datos.
 * Autor: Raziel Filobello
 * Fecha de creación: 15/06/2025
 * Descripción: Proporciona métodos para obtener información de evaluadores.
 */
public class EvaluadorDAO {

    /**
     * Obtiene un evaluador por su ID de usuario asociado.
     * @param idUsuario ID del usuario asociado al evaluador.
     * @return Objeto Evaluador con los datos encontrados.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public static Evaluador obtenerEvaluadorPorIdUsuario(int idUsuario) 
            throws SQLException {
        Evaluador evaluador = null;
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            String consulta = "SELECT * FROM evaluador WHERE id_usuario = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, idUsuario);
            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {
                evaluador = new Evaluador();
                evaluador.setIdEvaluador(resultado.getInt("id_evaluador"));
                evaluador.setNumPersonal(resultado.getInt("num_personal"));
                evaluador.setNombre(resultado.getString("nombre"));
                evaluador.setCorreo(resultado.getString("correo"));
                evaluador.setApellidoPaterno(
                    resultado.getString("apellido_paterno"));
                evaluador.setApellidoMaterno(
                    resultado.getString("apellido_materno"));
                evaluador.setIdUsuario(resultado.getInt("id_usuario"));
            }

            resultado.close();
            sentencia.close();
            conexion.close();
        }

        return evaluador;
    }
}