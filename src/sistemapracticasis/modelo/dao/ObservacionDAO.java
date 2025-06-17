package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sistemapracticasis.modelo.conexion.ConexionBD;

/**
 * Clase DAO para gestionar operaciones relacionadas con observaciones en la base de datos.
 * Autor: Uriel Cendón
 * Fecha de creación: 10/06/2025
 * Descripción: Proporciona métodos para insertar observaciones y actualizar su asociación
 * con entregas de documentos y reportes.
 */
public class ObservacionDAO {

    /**
     * Inserta una nueva observación en la base de datos.
     * @param descripcion Descripción de la observación a insertar
     * @return ID generado de la observación insertada, o -1 si falla la operación
     */
    public static int insertarObservacion(String descripcion) {
        int idGenerado = -1;
        String consulta = "INSERT INTO observacion (descripcion, fecha_observacion) "
                       + "VALUES (?, CURDATE())";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(
                 consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            sentencia.setString(1, descripcion);
            int filas = sentencia.executeUpdate();

            if (filas > 0) {
                try (ResultSet resultado = sentencia.getGeneratedKeys()) {
                    if (resultado.next()) {
                        idGenerado = resultado.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return idGenerado;
    }

    /**
     * Actualiza la asociación de una observación con una entrega de documento.
     * @param idDocumento ID del documento a actualizar
     * @param idObservacion ID de la observación a asociar
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public static boolean actualizarEntregaDocumento(int idDocumento, int idObservacion) {
        String consulta = "UPDATE entrega_documento SET id_observacion = ? "
                       + "WHERE id_entrega_documento = ("
                       + "SELECT id_entrega_documento FROM documento "
                       + "WHERE id_documento = ?)";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idObservacion);
            sentencia.setInt(2, idDocumento);

            return sentencia.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
    
    /**
     * Actualiza la asociación de una observación con una entrega de reporte.
     * @param idReporte ID del reporte a actualizar
     * @param idObservacion ID de la observación a asociar
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public static boolean actualizarEntregaReporte(int idReporte, int idObservacion) {
        String consulta = "UPDATE entrega_reporte SET id_observacion = ? "
                       + "WHERE id_entrega_reporte = ("
                       + "SELECT id_entrega_reporte FROM reporte "
                       + "WHERE idreporte = ?)";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idObservacion);
            sentencia.setInt(2, idReporte);

            return sentencia.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}