package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.EvaluacionOrgVin;

/**
 * Clase DAO para gestionar las operaciones relacionadas con evaluaciones a 
 * organizaciones vinculadas en la base de datos.
 * Autor: Uriel Cendón
 * Fecha de creación: 15/06/2025
 * Descripción: Proporciona métodos para guardar evaluaciones realizadas a 
 * organizaciones vinculadas donde los estudiantes realizan sus prácticas.
 */
public class EvaluacionOrgVinDAO {

    /**
     * Guarda una evaluación de organización vinculada en la base de datos.
     * @param evaluacion Objeto EvaluacionOrgVin con los datos a guardar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean guardarEvaluacionOrganizacionVinculada(
            EvaluacionOrgVin evaluacion) {
        boolean resultado = false;
        String consulta = "INSERT INTO evaluacion_a_organizacion_vinculada "
            + "(fecha_realizado, puntaje_total, ambiente_laboral, supervision, "
            + "cumplimiento_actividades, carga_laboral, recomendaciones, "
            + "id_expediente) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {

            sentencia.setDate(1, java.sql.Date.valueOf(
                evaluacion.getFechaRealizado()));
            sentencia.setDouble(2, evaluacion.getPuntajeTotal());
            sentencia.setInt(3, evaluacion.getAmbienteLaboral());
            sentencia.setInt(4, evaluacion.getSupervision());
            sentencia.setInt(5, evaluacion.getCumplimientoActividades());
            sentencia.setInt(6, evaluacion.getCargaLaboral());
            sentencia.setString(7, evaluacion.getRecomendaciones());
            sentencia.setInt(8, evaluacion.getIdExpediente());

            resultado = sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }
}