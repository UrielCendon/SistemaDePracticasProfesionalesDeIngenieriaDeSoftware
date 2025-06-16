package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.EvaluacionOrgVin;

/**
 *
 * @author uriel
 */
public class EvaluacionOrgVinDAO {
    public static boolean guardarEvaluacionOrganizacionVinculada
            (EvaluacionOrgVin evaluacion) {
        boolean resultado = false;
        String consulta = "INSERT INTO evaluacion_a_organizacion_vinculada " +
            "(fecha_realizado, puntaje_total, ambiente_laboral, supervision, " +
            "cumplimiento_actividades, carga_laboral, recomendaciones, " +
            "id_expediente) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {

            sentencia.setDate(1, java.sql.Date.valueOf(evaluacion.
                getFechaRealizado()));
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
