package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.OrganizacionVinculada;
import sistemapracticasis.modelo.pojo.ResultadoOperacion;

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
    public static OrganizacionVinculada obtenerOrganizacionVinculadaPorEstudiante(int idEstudiante) {
        OrganizacionVinculada organizacionVinculada = null;
        String consulta = "SELECT ov.razon_social "
            + "FROM organizacion_vinculada ov "
            + "JOIN proyecto p ON ov.id_organizacion_vinculada = p.id_organizacion_vinculada "
            + "JOIN estudiante e ON p.id_proyecto = e.id_proyecto "
            + "WHERE e.id_estudiante = ?";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {

            sentencia.setInt(1, idEstudiante);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    organizacionVinculada = new OrganizacionVinculada();
                    organizacionVinculada.setRazonSocial(resultado.getString("razon_social"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return organizacionVinculada;
    }

    /**
     * Obtiene todas las organizaciones vinculadas registradas.
     * @return Lista de organizaciones vinculadas
     */
    public static List<OrganizacionVinculada> obtenerOrganizaciones() {
        List<OrganizacionVinculada> organizaciones = new ArrayList<>();
        String consulta = "SELECT * FROM organizacion_vinculada";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {
            
            while (resultado.next()) {
                OrganizacionVinculada org = new OrganizacionVinculada();
                org.setIdOrganizacionVinculada(resultado.getInt("id_organizacion_vinculada"));
                org.setRazonSocial(resultado.getString("razon_social"));
                org.setTelefono(resultado.getString("telefono"));
                org.setDireccion(resultado.getString("direccion"));
                org.setCorreo(resultado.getString("correo"));
                organizaciones.add(org);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        String consulta = "INSERT INTO organizacion_vinculada "
            + "(razon_social, telefono, direccion, correo) "
            + "VALUES (?, ?, ?, ?)";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            
            sentencia.setString(1, organizacion.getRazonSocial());
            sentencia.setString(2, organizacion.getTelefono());
            sentencia.setString(3, organizacion.getDireccion());
            sentencia.setString(4, organizacion.getCorreo());
            
            int filasAfectadas = sentencia.executeUpdate();
            
            if (filasAfectadas > 0) {
                resultado.setError(false);
                resultado.setMensaje("Organización registrada con éxito");
            } else {
                resultado.setError(true);
                resultado.setMensaje("No se pudo registrar la organización");
            }
        } catch (SQLException e) {
            resultado.setError(true);
            resultado.setMensaje("Error en la base de datos: " + e.getMessage());
        }
        
        return resultado;
    }

    /**
     * Verifica si existe una organización con el nombre especificado.
     * @param nombre Nombre de la organización a verificar
     * @return true si existe, false en caso contrario
     */
    public static boolean existeOrganizacionConNombre(String nombre) {
        boolean existe = false;
        String consulta = "SELECT COUNT(*) FROM organizacion_vinculada WHERE razon_social = ?";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement stmt = conexion.prepareStatement(consulta)) {
            
            stmt.setString(1, nombre);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    existe = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    /**
     * Obtiene una organización vinculada por su ID.
     * @param idOrganizacion ID de la organización a buscar
     * @return Organización encontrada o null si no existe
     */
    public static OrganizacionVinculada obtenerOrganizacionPorId(int idOrganizacion) {
        String consulta = "SELECT * FROM organizacion_vinculada WHERE id_organizacion_vinculada = ?";
        OrganizacionVinculada organizacion = null;

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement stmt = conexion.prepareStatement(consulta)) {

            stmt.setInt(1, idOrganizacion);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    organizacion = new OrganizacionVinculada();
                    organizacion.setIdOrganizacionVinculada(rs.getInt("id_organizacion_vinculada"));
                    organizacion.setRazonSocial(rs.getString("razon_social"));
                    organizacion.setTelefono(rs.getString("telefono"));
                    organizacion.setDireccion(rs.getString("direccion"));
                    organizacion.setCorreo(rs.getString("correo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return organizacion;
    }
}