package sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.dao;

import java.sql.*;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.conexion.ConexionBD;
import sistemadepracticasprofesionalesdeingenieriadesoftware.modelo.pojo.Evaluador;

public class EvaluadorDAO {
    public static Evaluador obtenerEvaluadorPorIdUsuario(int idUsuario) throws SQLException {
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
                evaluador.setApellidoPaterno(resultado.getString("apellido_paterno"));
                evaluador.setApellidoMaterno(resultado.getString("apellido_materno"));
                evaluador.setIdUsuario(resultado.getInt("id_usuario"));
            }

            resultado.close();
            sentencia.close();
            conexion.close();
        }

        return evaluador;
    }
}
