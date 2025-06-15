package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.Periodo;

/**
 *
 * @author uriel
 */
public class PeriodoDAO {
    public static Periodo obtenerPeriodoActualPorEstudiante(int idEstudiante) {
        String consulta = "SELECT nombre_periodo, fecha_inicio, fecha_fin, "
            + "id_estudiante, id_expediente "
            + "FROM periodo "
            + "WHERE id_estudiante = ? "
            + "AND id_expediente IN (SELECT id_expediente FROM expediente) "
            + "ORDER BY fecha_inicio DESC LIMIT 1";
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {

            sentencia.setInt(1, idEstudiante);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                return new Periodo(
                    resultado.getString("nombre_periodo"),
                    resultado.getString("fecha_inicio"),
                    resultado.getString("fecha_fin"),
                    resultado.getInt("id_estudiante"),
                    resultado.getInt("id_expediente")
                );
            }
            
            resultado.close();
            sentencia.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
