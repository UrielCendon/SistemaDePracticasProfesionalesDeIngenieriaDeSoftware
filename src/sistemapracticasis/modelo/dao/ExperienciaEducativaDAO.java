package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.ExperienciaEducativa;

/**
 * Clase DAO para gestionar las operaciones relacionadas con experiencias 
 * educativas en la base de datos.
 * Autor: Uriel Cendón
 * Fecha de creación: 15/06/2025
 * Descripción: Proporciona métodos para obtener información sobre experiencias 
 * educativas asociadas a estudiantes.
 */
public class ExperienciaEducativaDAO {

    /**
     * Obtiene la experiencia educativa asociada a un estudiante.
     * @param idEstudiante ID del estudiante del cual obtener la experiencia educativa.
     * @return Objeto ExperienciaEducativa con los datos encontrados, o null si no se encuentra.
     */
    public static ExperienciaEducativa obtenerEEPorIdEstudiante(int idEstudiante) {
        ExperienciaEducativa experiencia = null;
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            String consulta = "SELECT ee.id_experiencia_educativa, ee.nrc, "
                + "ee.nombre, ee.bloque, ee.seccion "
                + "FROM experiencia_educativa ee "
                + "JOIN estudiante e ON ee.id_experiencia_educativa = "
                + "e.id_experiencia_educativa "
                + "WHERE e.id_estudiante = ?";

            try (PreparedStatement sentencia = conexion.prepareStatement(consulta);
                 ResultSet resultado = sentencia.executeQuery()) {

                sentencia.setInt(1, idEstudiante);

                if (resultado.next()) {
                    experiencia = new ExperienciaEducativa();
                    experiencia.setIdEE(resultado.getInt("id_experiencia_educativa"));
                    experiencia.setNombre(resultado.getString("nombre"));
                    experiencia.setNrc(resultado.getString("nrc"));
                    experiencia.setBloque(resultado.getString("bloque"));
                    experiencia.setSeccion(resultado.getString("seccion"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conexion != null) {
                        conexion.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return experiencia;
    }
}