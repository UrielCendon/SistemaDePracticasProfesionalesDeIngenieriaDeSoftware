/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistemapracticasis.modelo.conexion.ConexionBD;

/**
 *
 * @author uriel
 */
public class ExpedienteDAO {
    public boolean tieneExpedienteEnCurso(String matriculaEstudiante) {
        String consulta = "SELECT exp.id_expediente "
            + "FROM expediente exp "
            + "JOIN periodo per ON exp.id_expediente = per.id_expediente "
            + "JOIN estudiante est ON per.id_estudiante = est.id_estudiante "
            + "WHERE est.matricula = ? "
            + "AND exp.estado = 'en curso' "
            + "AND CURDATE() BETWEEN per.fecha_inicio AND per.fecha_fin";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {

            sentencia.setString(1, matriculaEstudiante);

            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public static boolean actualizarCalifPorEvaluacionOrgVin
        (double calificacion, int idExpediente) {
            boolean actualizado = false;
            String consulta = "UPDATE expediente SET calif_eval_org_vinc = ? "
                + "WHERE id_expediente = ?";

            try (Connection conn = ConexionBD.abrirConexion();
                 PreparedStatement sentencia = conn.prepareStatement
                    (consulta)) {

                sentencia.setDouble(1, calificacion);
                sentencia.setInt(2, idExpediente);

                actualizado = sentencia.executeUpdate() > 0;

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return actualizado;
            }
}
