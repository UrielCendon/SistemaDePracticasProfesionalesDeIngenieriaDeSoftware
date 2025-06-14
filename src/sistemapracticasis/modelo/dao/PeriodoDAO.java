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
import sistemapracticasis.modelo.pojo.Periodo;

/**
 *
 * @author uriel
 */
public class PeriodoDAO {
    public Periodo obtenerPeriodoActual() {
        String consulta = "SELECT id_periodo, nombre_periodo, fecha_inicio, "
            + "fecha_fin FROM periodo WHERE CURDATE() "
            + "BETWEEN fecha_inicio AND fecha_fin LIMIT 1";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            if (resultado.next()) {
                Periodo periodo = new Periodo();
                periodo.setIdPeriodo(resultado.getInt("id_periodo"));
                periodo.setNombrePeriodo(resultado.getString("nombre_periodo"));
                periodo.setFechaInicio(resultado.getDate("fecha_inicio").
                    toString());
                periodo.setFechaFin(resultado.getDate("fecha_fin").toString());

                return periodo;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
