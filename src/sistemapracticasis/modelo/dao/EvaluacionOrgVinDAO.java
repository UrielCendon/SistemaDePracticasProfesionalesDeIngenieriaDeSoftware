package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.EvaluacionOrgVin;
import sistemapracticasis.util.Utilidad;

public class EvaluacionOrgVinDAO {

    public static boolean guardarEvaluacionOrganizacionVinculada(EvaluacionOrgVin evaluacion) {
        boolean exito = false;
        String consulta = "INSERT INTO evaluacion_a_organizacion_vinculada "
            + "(fecha_realizado, puntaje_total, ambiente_laboral, supervision, "
            + "cumplimiento_actividades, carga_laboral, recomendaciones, id_expediente) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setDate(1, java.sql.Date.valueOf(evaluacion.getFechaRealizado()));
            sentencia.setDouble(2, evaluacion.getPuntajeTotal());
            sentencia.setInt(3, evaluacion.getAmbienteLaboral());
            sentencia.setInt(4, evaluacion.getSupervision());
            sentencia.setInt(5, evaluacion.getCumplimientoActividades());
            sentencia.setInt(6, evaluacion.getCargaLaboral());
            sentencia.setString(7, evaluacion.getRecomendaciones());
            sentencia.setInt(8, evaluacion.getIdExpediente());

            exito = sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
        }

        return exito;
    }
}
