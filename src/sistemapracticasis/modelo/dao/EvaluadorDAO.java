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
    public static Evaluador obtenerEvaluadorPorIdUsuario(int idUsuario) throws SQLException {
        Evaluador evaluador = null;
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            String consulta = "SELECT id_evaluador, num_personal, nombre, correo, "
                    + "apellido_paterno, apellido_materno, id_usuario "
                    + "FROM evaluador WHERE id_usuario = ?";
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            sentenciaSQL.setInt(1, idUsuario);
            ResultSet resultadoConsulta = sentenciaSQL.executeQuery();

            if (resultadoConsulta.next()) {
                evaluador = new Evaluador();
                evaluador.setIdEvaluador(resultadoConsulta.getInt("id_evaluador"));
                evaluador.setNumPersonal(resultadoConsulta.getInt("num_personal"));
                evaluador.setNombre(resultadoConsulta.getString("nombre"));
                evaluador.setCorreo(resultadoConsulta.getString("correo"));
                evaluador.setApellidoPaterno(resultadoConsulta.getString("apellido_paterno"));
                evaluador.setApellidoMaterno(resultadoConsulta.getString("apellido_materno"));
                evaluador.setIdUsuario(resultadoConsulta.getInt("id_usuario"));
            }

            resultadoConsulta.close();
            sentenciaSQL.close();
            conexion.close();
        }

        return evaluador;
    }
}
