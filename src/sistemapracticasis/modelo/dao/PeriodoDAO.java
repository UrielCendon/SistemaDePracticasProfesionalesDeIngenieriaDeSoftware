package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.Periodo;

/**
 * Clase DAO para gestionar operaciones relacionadas con periodos académicos.
 * Autor: Uriel Cendón
 * Fecha de creación: 14/06/2025
 * Descripción: Proporciona métodos para obtener y actualizar información 
 * sobre periodos académicos de estudiantes.
 */
public class PeriodoDAO {

    /**
     * Obtiene el periodo actual de un estudiante.
     * @param idEstudiante ID del estudiante
     * @return Objeto Periodo con los datos encontrados o null si no existe
     */
    public static Periodo obtenerPeriodoActualPorEstudiante(int idEstudiante) {
        String consulta = "SELECT nombre_periodo, fecha_inicio, fecha_fin, "
            + "id_estudiante, id_expediente "
            + "FROM periodo "
            + "WHERE id_estudiante = ? "
            + "AND id_expediente IN (SELECT id_expediente FROM expediente) "
            + "ORDER BY fecha_inicio DESC LIMIT 1";
            
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            sentencia.setInt(1, idEstudiante);
            
            if (resultado.next()) {
                return new Periodo(
                    resultado.getString("nombre_periodo"),
                    resultado.getString("fecha_inicio"),
                    resultado.getString("fecha_fin"),
                    resultado.getInt("id_estudiante"),
                    resultado.getInt("id_expediente")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Obtiene el periodo académico actual.
     * @return Objeto Periodo con los datos del periodo actual o null si no existe
     */
    public static Periodo obtenerPeriodoActual() {
        Periodo periodoActual = null;
        String consulta = "SELECT p.id_expediente, p.nombre_periodo, "
            + "p.fecha_inicio, p.fecha_fin, "
            + "ee.nombre AS nombreEE, ee.nrc "
            + "FROM periodo p "
            + "JOIN expediente exp ON p.id_expediente = exp.id_expediente "
            + "JOIN estudiante est ON est.id_estudiante = p.id_estudiante "
            + "JOIN experiencia_educativa ee ON est.id_experiencia_educativa = "
            + "ee.id_experiencia_educativa "
            + "WHERE CURDATE() BETWEEN p.fecha_inicio AND p.fecha_fin "
            + "LIMIT 1";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement ps = conexion.prepareStatement(consulta);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                periodoActual = new Periodo(
                    rs.getString("nombre_periodo"),
                    rs.getString("fecha_inicio"),
                    rs.getString("fecha_fin"),
                    rs.getInt("id_expediente"),
                    0
                );
                periodoActual.setNombreEE(rs.getString("nombreEE"));
                periodoActual.setNrc(rs.getString("nrc"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return periodoActual;
    }

    /**
     * Actualiza el expediente asociado a un estudiante en el periodo actual.
     * @param idEstudiante ID del estudiante
     * @param idExpediente ID del expediente a asociar
     * @return true si la actualización fue exitosa, false en caso contrario
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static boolean actualizarExpedienteEstudiante(int idEstudiante, 
            int idExpediente) throws SQLException {
        String consulta = "UPDATE periodo SET id_expediente = ? WHERE "
            + "id_estudiante = ? AND fecha_fin > CURDATE()";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idExpediente);
            sentencia.setInt(2, idEstudiante);
            return sentencia.executeUpdate() > 0;
        }
    }

    /**
     * Obtiene el ID de expediente asociado a un estudiante.
     * @param idEstudiante ID del estudiante
     * @return ID del expediente asociado o null si no existe
     */
    public static Integer obtenerIdExpedientePorEstudiante(int idEstudiante) {
        Integer idExpediente = null;
        String query = "SELECT id_expediente FROM periodo WHERE id_estudiante = ?";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            stmt.setInt(1, idEstudiante);
            if (rs.next()) {
                idExpediente = rs.getInt("id_expediente");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return idExpediente;
    }
}