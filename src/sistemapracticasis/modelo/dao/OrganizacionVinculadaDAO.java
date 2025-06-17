package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.OrganizacionVinculada;
import sistemapracticasis.modelo.pojo.ResultadoOperacion;
import sistemapracticasis.util.Utilidad;

/**
 * Clase DAO para gestionar operaciones relacionadas con organizaciones vinculadas.
 * Autor: Uriel Cendón
 * Fecha de creación: 14/06/2025
 * Descripción: Proporciona métodos para obtener, registrar y verificar 
 * organizaciones vinculadas en el sistema.
 */
public class OrganizacionVinculadaDAO {

    /**
     * Obtiene la organización vinculada asociada a un estudiante.
     * @param idEstudiante ID del estudiante
     * @return Organización vinculada o null si no se encuentra
     */
    public static OrganizacionVinculada obtenerOrganizacionVinculadaPorEstudiante
            (int idEstudiante) {
        OrganizacionVinculada organizacion = null;
        String consulta = "SELECT ov.razon_social "
            + "FROM organizacion_vinculada ov "
            + "JOIN proyecto p ON ov.id_organizacion_vinculada = p.id_organizacion_vinculada "
            + "JOIN estudiante e ON p.id_proyecto = e.id_proyecto "
            + "WHERE e.id_estudiante = ?";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentenciaSQL = conn.prepareStatement(consulta)) {

            sentenciaSQL.setInt(1, idEstudiante);

            try (ResultSet resultado = sentenciaSQL.executeQuery()) {
                if (resultado.next()) {
                    organizacion = new OrganizacionVinculada();
                    organizacion.setRazonSocial(resultado.getString("razon_social"));
                }
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorBD", 
                "No hay conexión con la base de datos.");
        }

        return organizacion;
    }

    /**
     * Obtiene todas las organizaciones vinculadas registradas.
     * @return Lista de organizaciones vinculadas
     */
    public static List<OrganizacionVinculada> obtenerOrganizaciones() {
        List<OrganizacionVinculada> organizaciones = new ArrayList<>();
        String consulta = "SELECT id_organizacion_vinculada, razon_social, telefono, direccion,"
            + " correo FROM organizacion_vinculada";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
             ResultSet resultado = sentenciaSQL.executeQuery()) {

            while (resultado.next()) {
                OrganizacionVinculada organizacion = new OrganizacionVinculada();
                organizacion.setIdOrganizacionVinculada(resultado.getInt
                    ("id_organizacion_vinculada"));
                organizacion.setRazonSocial(resultado.getString("razon_social"));
                organizacion.setTelefono(resultado.getString("telefono"));
                organizacion.setDireccion(resultado.getString("direccion"));
                organizacion.setCorreo(resultado.getString("correo"));
                organizaciones.add(organizacion);
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorBD", 
                "No hay conexión con la base de datos.");
        }

        return organizaciones;
    }

    /**
     * Registra una nueva organización vinculada.
     * @param organizacion Datos de la organización a registrar
     * @return Resultado de la operación
     */
    public static ResultadoOperacion registrarOrganizacion(OrganizacionVinculada organizacion) {
        ResultadoOperacion resultado = new ResultadoOperacion();
        String consulta = "INSERT INTO organizacion_vinculada (razon_social, telefono, "
            + "direccion, correo) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta)) {

            sentenciaSQL.setString(1, organizacion.getRazonSocial());
            sentenciaSQL.setString(2, organizacion.getTelefono());
            sentenciaSQL.setString(3, organizacion.getDireccion());
            sentenciaSQL.setString(4, organizacion.getCorreo());

            int filasAfectadas = sentenciaSQL.executeUpdate();

            if (filasAfectadas > 0) {
                resultado.setError(false);
                resultado.setMensaje("Organización registrada con éxito");
            } else {
                resultado.setError(true);
                resultado.setMensaje("No se pudo registrar la organización");
            }
        } catch (SQLException ex) {
            resultado.setError(true);
            resultado.setMensaje("No hay conexion con la base de datos");
        }

        return resultado;
    }

    /**
     * Verifica si existe una organización con el nombre especificado.
     * @param nombre Nombre de la organización a verificar
     * @return true si existe, false en caso contrario
     */
    public static boolean existeOrganizacionConNombre(String nombre) {
        String consulta = "SELECT COUNT(*) FROM organizacion_vinculada WHERE razon_social = ?";
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta)) {

            sentenciaSQL.setString(1, nombre);

            try (ResultSet resultado = sentenciaSQL.executeQuery()) {
                return resultado.next() && resultado.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorBD", 
                "No hay conexión con la base de datos.");
        }
        return false;
    }

    /**
     * Obtiene una organización vinculada por su ID.
     * @param idOrganizacion ID de la organización a buscar
     * @return Organización encontrada o null si no existe
     */
    public static OrganizacionVinculada obtenerOrganizacionPorId(int idOrganizacion) {
        OrganizacionVinculada organizacion = null;
        String consulta = "SELECT id_organizacion_vinculada, razon_social, telefono, "
            + "direccion, correo FROM organizacion_vinculada WHERE id_organizacion_vinculada = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta)) {

            sentenciaSQL.setInt(1, idOrganizacion);

            try (ResultSet resultado = sentenciaSQL.executeQuery()) {
                if (resultado.next()) {
                    organizacion = new OrganizacionVinculada();
                    organizacion.setIdOrganizacionVinculada(resultado.getInt
                        ("id_organizacion_vinculada"));
                    organizacion.setRazonSocial(resultado.getString("razon_social"));
                    organizacion.setTelefono(resultado.getString("telefono"));
                    organizacion.setDireccion(resultado.getString("direccion"));
                    organizacion.setCorreo(resultado.getString("correo"));
                }
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorBD", 
                "No hay conexión con la base de datos.");
        }

        return organizacion;
    }
}
