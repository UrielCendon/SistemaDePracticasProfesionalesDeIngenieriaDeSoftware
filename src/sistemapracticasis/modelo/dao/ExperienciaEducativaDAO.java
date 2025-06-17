package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.ExperienciaEducativa;
import sistemapracticasis.util.Utilidad;

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
                + "JOIN estudiante e ON ee.id_experiencia_educativa = e.id_experiencia_educativa "
                + "WHERE e.id_estudiante = ?";

            try (PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta)) {

                sentenciaSQL.setInt(1, idEstudiante);

                try (ResultSet resultadoConsulta = sentenciaSQL.executeQuery()) {
                    if (resultadoConsulta.next()) {
                        experiencia = new ExperienciaEducativa();
                        experiencia.setIdEE(resultadoConsulta.getInt("id_experiencia_educativa"));
                        experiencia.setNombre(resultadoConsulta.getString("nombre"));
                        experiencia.setNrc(resultadoConsulta.getString("nrc"));
                        experiencia.setBloque(resultadoConsulta.getString("bloque"));
                        experiencia.setSeccion(resultadoConsulta.getString("seccion"));
                    }
                }

            } catch (SQLException e) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                    "Error con la base de datos");
            } finally {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                        "Error con la base de datos");
                }
            }
        }

        return experiencia;
    }
}
