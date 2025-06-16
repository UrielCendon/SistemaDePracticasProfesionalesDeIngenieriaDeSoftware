/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemapracticasis.modelo.dao;

import java.sql.*;
import java.util.ArrayList;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.EvaluacionEstudiante;
/**
 *
 * @author Kaiser
 */
public class EvaluacionEstudianteDAO {

    public static boolean guardarEvaluacion(EvaluacionEstudiante evaluacion) {
        String sql = "INSERT INTO evaluacion_estudiante(fecha_entregado, puntaje_total, uso_tecnicas, seguridad, contenido, ortografia, requisitos, id_observacion, id_expediente) " +
                     "VALUES (CURDATE(), ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, evaluacion.getPuntajeTotal());
            ps.setInt(2, evaluacion.getUsoTecnicas());
            ps.setInt(3, evaluacion.getSeguridad());
            ps.setInt(4, evaluacion.getContenido());
            ps.setInt(5, evaluacion.getOrtografia());
            ps.setInt(6, evaluacion.getRequisitos());
            ps.setInt(7, evaluacion.getIdObservacion());
            ps.setInt(8, evaluacion.getIdExpediente());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static ArrayList<Estudiante> obtenerEstudiantesNoEvaluados() {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();

        String sql = "SELECT e.*, p.nombre AS nombre_proyecto, per.id_expediente " +
                     "FROM estudiante e " +
                     "JOIN proyecto p ON e.id_proyecto = p.id_proyecto " +
                     "JOIN periodo per ON e.id_estudiante = per.id_estudiante " +
                     "LEFT JOIN evaluacion_estudiante ev ON per.id_expediente = ev.id_expediente " +
                     "WHERE ev.id_evaluacion_estudiante IS NULL " +
                     "AND CURDATE() BETWEEN per.fecha_inicio AND per.fecha_fin";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Estudiante est = new Estudiante();
                est.setIdEstudiante(rs.getInt("id_estudiante"));
                est.setMatricula(rs.getString("matricula"));
                est.setNombre(rs.getString("nombre"));
                est.setApellidoPaterno(rs.getString("apellido_paterno"));
                est.setApellidoMaterno(rs.getString("apellido_materno"));
                est.setNombreProyecto(rs.getString("nombre_proyecto"));
                est.setIdPeriodo(rs.getInt("id_expediente"));
                estudiantes.add(est);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return estudiantes;
    }
}