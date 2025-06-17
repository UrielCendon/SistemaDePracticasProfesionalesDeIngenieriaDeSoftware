package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.Coordinador;

/**
 * Clase que proporciona métodos para interactuar con la tabla de coordinadores 
 * en la base de datos.
 * Autor: Uriel Cendón
 * Fecha de creación: 15/06/2025
 * Descripción: Contiene operaciones de obtención para la entidad Coordinador.
 */
public class CoordinadorDAO {

    /**
     * Obtiene un coordinador de la base de datos según su ID de usuario asociado.
     * @param idUsuario El ID del usuario asociado al coordinador que se desea 
     *        recuperar.
     * @return Objeto Coordinador con los datos encontrados, o null si no se 
     *         encontró coincidencia.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public static Coordinador obtenerCoordinadorPorIdUsuario(int idUsuario) 
            throws SQLException {
        Coordinador coordinador = null;
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            String consulta = "SELECT * FROM coordinador WHERE id_usuario = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, idUsuario);
            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {
                coordinador = new Coordinador();
                coordinador.setIdCoordinador(resultado.getInt("id_coordinador"));
                coordinador.setNumPersonal(resultado.getInt("num_personal"));
                coordinador.setNombre(resultado.getString("nombre"));
                coordinador.setCorreo(resultado.getString("correo"));
                coordinador.setApellidoPaterno(
                    resultado.getString("apellido_paterno"));
                coordinador.setApellidoMaterno(
                    resultado.getString("apellido_materno"));
                coordinador.setIdUsuario(resultado.getInt("id_usuario"));
            }

            resultado.close();
            sentencia.close();
            conexion.close();
        }

        return coordinador;
    }
}