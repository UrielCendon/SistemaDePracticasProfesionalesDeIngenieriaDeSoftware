package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.Coordinador;

public class CoordinadorDAO {

    public static Coordinador obtenerCoordinadorPorIdUsuario(int idUsuario) throws SQLException {
        Coordinador coordinador = null;
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            String consulta = "SELECT id_coordinador, num_personal, nombre, correo, " +
                              "apellido_paterno, apellido_materno, id_usuario " +
                              "FROM coordinador WHERE id_usuario = ?";
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            sentenciaSQL.setInt(1, idUsuario);
            ResultSet resultadoConsulta = sentenciaSQL.executeQuery();

            if (resultadoConsulta.next()) {
                coordinador = new Coordinador();
                coordinador.setIdCoordinador(resultadoConsulta.getInt("id_coordinador"));
                coordinador.setNumPersonal(resultadoConsulta.getInt("num_personal"));
                coordinador.setNombre(resultadoConsulta.getString("nombre"));
                coordinador.setCorreo(resultadoConsulta.getString("correo"));
                coordinador.setApellidoPaterno(resultadoConsulta.getString("apellido_paterno"));
                coordinador.setApellidoMaterno(resultadoConsulta.getString("apellido_materno"));
                coordinador.setIdUsuario(resultadoConsulta.getInt("id_usuario"));
            }

            resultadoConsulta.close();
            sentenciaSQL.close();
            conexion.close();
        }

        return coordinador;
    }
}
