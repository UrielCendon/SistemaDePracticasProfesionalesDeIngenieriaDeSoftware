/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemapracticasis.modelo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.EstudianteAsignado;
/**
 *
 * @author Kaiser
 */
public class GenerarOficiosDAO {

    public static List<EstudianteAsignado> obtenerEstudiantesAsignadosPeriodoActual() {
        List<EstudianteAsignado> lista = new ArrayList<>();

    String query = 
        "SELECT " +
        "    e.matricula, " +
        "    CONCAT(e.nombre, ' ', e.apellido_paterno, ' ', e.apellido_materno) AS nombre_estudiante, " +
        "    p.nombre AS nombre_proyecto, " +
        "    ov.razon_social AS razon_social_organizacion " +
        "FROM estudiante e " +
        "JOIN proyecto p ON e.id_proyecto = p.id_proyecto " +
        "JOIN organizacion_vinculada ov ON p.id_organizacion_vinculada = ov.id_organizacion_vinculada " +
        "JOIN periodo pe ON e.id_estudiante = pe.id_estudiante " +
        "JOIN expediente exp ON pe.id_expediente = exp.id_expediente " +
        "WHERE CURDATE() BETWEEN pe.fecha_inicio AND pe.fecha_fin " +
        "  AND exp.estado = 'en curso'";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EstudianteAsignado est = new EstudianteAsignado(
                        rs.getString("matricula"),
                        rs.getString("nombre_estudiante"),
                        rs.getString("nombre_proyecto"),
                        rs.getString("razon_social_organizacion")
                );
                lista.add(est);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }
}