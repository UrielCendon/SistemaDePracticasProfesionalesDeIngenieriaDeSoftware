package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.EntregaVisual;

/**
 * Clase DAO para gestionar operaciones relacionadas con reportes.
 * Autor: Uriel Cendón
 * Fecha de creación: 15/06/2025
 * Descripción: Proporciona métodos para obtener reportes, archivos asociados,
 * calificaciones y verificar observaciones.
 */
public class ReporteDAO {

    /**
     * Obtiene los reportes asociados a un expediente.
     * @param idExpediente ID del expediente
     * @return Lista de objetos EntregaVisual con la información de los reportes
     */
    public static List<EntregaVisual> obtenerReportesPorIdExpediente(int idExpediente) {
        List<EntregaVisual> lista = new ArrayList<>();
        String consulta = "SELECT r.idreporte, r.nombre_reporte, r.fecha_entregado "
            + "FROM reporte r JOIN entrega_reporte er ON r.id_entrega_reporte = "
            + "er.id_entrega_reporte WHERE er.id_expediente = ? "
            + "AND r.fecha_entregado IS NOT NULL";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            sentencia.setInt(1, idExpediente);

            while (resultado.next()) {
                lista.add(new EntregaVisual(
                    resultado.getInt("idreporte"),
                    resultado.getString("nombre_reporte"),
                    resultado.getString("fecha_entregado"),
                    "reporte"
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    /**
     * Obtiene el archivo de un reporte por su ID.
     * @param idReporte ID del reporte
     * @return Contenido del reporte en bytes o null si no se encuentra
     */
    public static byte[] obtenerArchivoPorId(int idReporte) {
        byte[] datos = null;
        String consulta = "SELECT documento FROM reporte WHERE idreporte = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            sentencia.setInt(1, idReporte);
            if (resultado.next()) {
                datos = resultado.getBytes("documento");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datos;
    }

    /**
     * Obtiene la calificación de un reporte por su ID.
     * @param idReporte ID del reporte
     * @return Calificación del reporte o null si no tiene calificación
     */
    public static Double obtenerCalificacionPorId(int idReporte) {
        Double calificacion = null;
        String consulta = "SELECT er.calificacion FROM reporte r "
            + "JOIN entrega_reporte er ON r.id_entrega_reporte = "
            + "er.id_entrega_reporte WHERE r.idreporte = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            sentencia.setInt(1, idReporte);
            if (resultado.next()) {
                calificacion = resultado.getDouble("calificacion");
                if (resultado.wasNull()) {
                    calificacion = null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return calificacion;
    }

    /**
     * Verifica si un reporte tiene observaciones asociadas.
     * @param idReporte ID del reporte a verificar
     * @return true si tiene observaciones, false en caso contrario
     */
    public static boolean tieneObservacion(int idReporte) {
        String consulta = "SELECT er.id_observacion FROM reporte r "
            + "JOIN entrega_reporte er ON r.id_entrega_reporte = "
            + "er.id_entrega_reporte WHERE r.idreporte = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            sentencia.setInt(1, idReporte);
            if (resultado.next()) {
                return resultado.getObject("id_observacion") != null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}