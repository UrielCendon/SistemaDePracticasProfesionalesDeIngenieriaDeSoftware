package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.EstadoProyecto;
import sistemapracticasis.modelo.pojo.Proyecto;
import sistemapracticasis.modelo.pojo.ResultadoOperacion;

/**
 * Clase DAO para gestionar operaciones relacionadas con proyectos.
 * Autor: Uriel Cendón
 * Fecha de creación: 10/06/2025
 * Descripción: Proporciona métodos para gestionar proyectos (CRUD) y operaciones relacionadas.
 */
public class ProyectoDAO {

    /**
     * Obtiene los proyectos disponibles con cupo y vigentes.
     * @return Lista de proyectos disponibles
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static ArrayList<Proyecto> obtenerProyectosDisponibles() throws SQLException {
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        String consulta = "SELECT p.id_proyecto, p.nombre, p.descripcion, "
            + "p.estado, p.cupo, p.fecha_inicio, p.fecha_fin, " 
            + "p.id_organizacion_vinculada " 
            + "FROM proyecto p " 
            + "WHERE p.cupo > 0 " 
            + "AND CURDATE() BETWEEN p.fecha_inicio AND p.fecha_fin";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Proyecto proyecto = new Proyecto();
                proyecto.setIdProyecto(resultado.getInt("id_proyecto"));
                proyecto.setNombre(resultado.getString("nombre"));
                proyecto.setDescripcion(resultado.getString("descripcion"));
                proyecto.setEstado(EstadoProyecto.fromValor(resultado.getString("estado")));
                proyecto.setCupo(resultado.getInt("cupo"));
                proyecto.setFecha_inicio(resultado.getDate("fecha_inicio").toString());
                proyecto.setFecha_fin(resultado.getDate("fecha_fin").toString());
                proyecto.setIdOrganizacionVinculada(resultado.getInt("id_organizacion_vinculada"));

                proyectos.add(proyecto);
            }
        }
        return proyectos;
    }

    /**
     * Decrementa el cupo disponible de un proyecto.
     * @param idProyecto ID del proyecto a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static boolean decrementarCupoProyecto(int idProyecto) throws SQLException {
        String consulta = "UPDATE proyecto SET cupo = cupo - 1 WHERE "
            + "id_proyecto = ? AND cupo > 0";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idProyecto);
            return sentencia.executeUpdate() > 0;
        }
    }

    /**
     * Obtiene el proyecto asociado a un estudiante.
     * @param idEstudiante ID del estudiante
     * @return Proyecto asociado o null si no existe
     */
    public static Proyecto obtenerProyectoPorIdEstudiante(int idEstudiante) {
        Proyecto proyecto = null;
        String consulta = "SELECT p.id_proyecto, p.nombre, p.descripcion, "
            + "p.estado, p.cupo, p.fecha_inicio, p.fecha_fin, "
            + "p.id_organizacion_vinculada "
            + "FROM proyecto p "
            + "JOIN estudiante e ON p.id_proyecto = e.id_proyecto "
            + "WHERE e.id_estudiante = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            sentencia.setInt(1, idEstudiante);

            if (resultado.next()) {
                proyecto = new Proyecto();
                proyecto.setIdProyecto(resultado.getInt("id_proyecto"));
                proyecto.setNombre(resultado.getString("nombre"));
                proyecto.setDescripcion(resultado.getString("descripcion"));
                proyecto.setEstado(EstadoProyecto.fromValor(resultado.getString("estado")));
                proyecto.setCupo(resultado.getInt("cupo"));
                proyecto.setFecha_inicio(resultado.getDate("fecha_inicio").toString());
                proyecto.setFecha_fin(resultado.getDate("fecha_fin").toString());
                proyecto.setIdOrganizacionVinculada(resultado.getInt("id_organizacion_vinculada"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proyecto;
    }

    /**
     * Registra un nuevo proyecto en la base de datos.
     * @param proyecto Datos del proyecto a registrar
     * @return Resultado de la operación
     */
    public static ResultadoOperacion registrarProyecto(Proyecto proyecto) {
        ResultadoOperacion resultado = new ResultadoOperacion();
        String sql = "INSERT INTO proyecto (nombre, descripcion, estado, cupo, "
            + "fecha_inicio, fecha_fin, id_organizacion_vinculada, "
            + "id_responsable_proyecto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, proyecto.getNombre());
            stmt.setString(2, proyecto.getDescripcion());
            stmt.setString(3, proyecto.getEstado().toString().toLowerCase());
            stmt.setInt(4, proyecto.getCupo());
            stmt.setDate(5, java.sql.Date.valueOf(proyecto.getFecha_inicio()));
            stmt.setDate(6, java.sql.Date.valueOf(proyecto.getFecha_fin()));
            stmt.setInt(7, proyecto.getIdOrganizacionVinculada());
            stmt.setInt(8, proyecto.getIdResponsableProyecto());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                resultado.setError(false);
                resultado.setMensaje("Proyecto registrado exitosamente.");
            } else {
                resultado.setError(true);
                resultado.setMensaje("No se pudo registrar el proyecto.");
            }
        } catch (SQLException e) {
            resultado.setError(true);
            resultado.setMensaje("Error en la base de datos: " + e.getMessage());
        }
        return resultado;
    }

    /**
     * Obtiene todos los proyectos registrados.
     * @return Lista de todos los proyectos
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     */
    public static ArrayList<Proyecto> obtenerTodosLosProyectos() throws SQLException {
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        String consulta = "SELECT id_proyecto, nombre, descripcion, estado, cupo, "
            + "fecha_inicio, fecha_fin, id_organizacion_vinculada, "
            + "id_responsable_proyecto FROM proyecto";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Proyecto proyecto = new Proyecto();
                proyecto.setIdProyecto(resultado.getInt("id_proyecto"));
                proyecto.setNombre(resultado.getString("nombre"));
                proyecto.setDescripcion(resultado.getString("descripcion"));
                proyecto.setEstado(EstadoProyecto.fromValor(resultado.getString("estado")));
                proyecto.setCupo(resultado.getInt("cupo"));
                proyecto.setFecha_inicio(resultado.getDate("fecha_inicio").toString());
                proyecto.setFecha_fin(resultado.getDate("fecha_fin").toString());
                proyecto.setIdOrganizacionVinculada(resultado.getInt("id_organizacion_vinculada"));
                proyecto.setIdResponsableProyecto(resultado.getInt("id_responsable_proyecto"));

                proyectos.add(proyecto);
            }
        }
        return proyectos;
    }

    /**
     * Elimina un proyecto por su ID.
     * @param idProyecto ID del proyecto a eliminar
     * @return Resultado de la operación
     */
    public static ResultadoOperacion eliminarProyectoPorId(int idProyecto) {
        ResultadoOperacion resultado = new ResultadoOperacion();
        String sql = "DELETE FROM proyecto WHERE id_proyecto = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idProyecto);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                resultado.setError(false);
                resultado.setMensaje("Proyecto eliminado con éxito.");
            } else {
                resultado.setError(true);
                resultado.setMensaje("No se encontró el proyecto a eliminar.");
            }
        } catch (SQLException e) {
            resultado.setError(true);
            resultado.setMensaje("Error: no se pudo eliminar el proyecto o ya tiene alguna relación.");
        }
        return resultado;
    }

    /**
     * Actualiza los datos de un proyecto existente.
     * @param proyecto Datos actualizados del proyecto
     * @return Resultado de la operación
     */
    public static ResultadoOperacion actualizarProyecto(Proyecto proyecto) {
        ResultadoOperacion resultado = new ResultadoOperacion();
        String sql = "UPDATE proyecto SET nombre = ?, descripcion = ?, estado = ?, "
            + "cupo = ?, fecha_inicio = ?, fecha_fin = ?, "
            + "id_organizacion_vinculada = ?, id_responsable_proyecto = ? "
            + "WHERE id_proyecto = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, proyecto.getNombre());
            stmt.setString(2, proyecto.getDescripcion());
            stmt.setString(3, proyecto.getEstado().toString().toLowerCase());
            stmt.setInt(4, proyecto.getCupo());
            stmt.setDate(5, java.sql.Date.valueOf(proyecto.getFecha_inicio()));
            stmt.setDate(6, java.sql.Date.valueOf(proyecto.getFecha_fin()));
            stmt.setInt(7, proyecto.getIdOrganizacionVinculada());
            stmt.setInt(8, proyecto.getIdResponsableProyecto());
            stmt.setInt(9, proyecto.getIdProyecto());

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                resultado.setError(false);
                resultado.setMensaje("Proyecto actualizado correctamente.");
            } else {
                resultado.setError(true);
                resultado.setMensaje("No se pudo actualizar el proyecto.");
            }
        } catch (SQLException e) {
            resultado.setError(true);
            resultado.setMensaje("Error: no se pudo actualizar el proyecto.");
        }
        return resultado;
    }
}