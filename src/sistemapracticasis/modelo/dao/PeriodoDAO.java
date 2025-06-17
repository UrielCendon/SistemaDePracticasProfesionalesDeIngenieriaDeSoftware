package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.Periodo;
import sistemapracticasis.util.Utilidad;

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
        String consulta = "SELECT p.id_periodo, p.nombre_periodo, p.fecha_inicio, p.fecha_fin, "
            + "e.id_estudiante, exp.id_expediente "
            + "FROM periodo p "
            + "INNER JOIN periodo_cursante pc ON p.id_periodo = pc.id_periodo "
            + "INNER JOIN estudiante e ON pc.id_estudiante = e.id_estudiante "
            + "INNER JOIN expediente exp ON exp.id_estudiante = e.id_estudiante AND "
            + "exp.id_periodo = p.id_periodo "
            + "WHERE e.id_estudiante = ? "
            + "ORDER BY p.fecha_inicio DESC LIMIT 1";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idEstudiante);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return new Periodo(
                            resultado.getInt("id_periodo"),
                            resultado.getString("nombre_periodo"),
                            resultado.getString("fecha_inicio"),
                            resultado.getString("fecha_fin"),
                            resultado.getInt("id_estudiante"),
                            resultado.getInt("id_expediente")
                    );
                }
            }
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }
        return null;
    }

    /**
     * Obtiene el periodo académico actual.
     * @return Objeto Periodo con los datos del periodo actual o null si no existe
     */
    public static Periodo obtenerPeriodoActual() {
        Periodo periodoActual = null;
        String consulta = "SELECT p.id_periodo, p.nombre_periodo, "
            + "p.fecha_inicio, p.fecha_fin, ee.nombre AS nombreEE, ee.nrc, "
            + "exp.id_expediente FROM periodo p "
            + "JOIN periodo_cursante pc ON p.id_periodo = pc.id_periodo "
            + "JOIN estudiante est ON pc.id_estudiante = est.id_estudiante "
            + "JOIN expediente exp ON exp.id_estudiante = est.id_estudiante AND "
            + "exp.id_periodo = p.id_periodo "
            + "JOIN experiencia_educativa ee ON est.id_experiencia_educativa = "
            + "ee.id_experiencia_educativa "
            + "WHERE CURDATE() BETWEEN p.fecha_inicio AND p.fecha_fin "
            + "LIMIT 1";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            if (resultado.next()) {
                periodoActual = new Periodo(
                        resultado.getString("nombre_periodo"),
                        resultado.getString("fecha_inicio"),
                        resultado.getString("fecha_fin"),
                        resultado.getInt("id_expediente"),
                        0
                );
                periodoActual.setNombreEE(resultado.getString("nombreEE"));
                periodoActual.setNrc(resultado.getString("nrc"));
            }
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }
        return periodoActual;
    }
}
