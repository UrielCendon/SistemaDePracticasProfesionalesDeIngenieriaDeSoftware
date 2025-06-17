package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.Evaluador;

public class EvaluadorDAO {

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
