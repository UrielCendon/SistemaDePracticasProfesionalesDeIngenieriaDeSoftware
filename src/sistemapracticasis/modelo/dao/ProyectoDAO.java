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
 *
 * @author uriel
 */
public class ProyectoDAO {
    public static ArrayList<Proyecto> obtenerProyectosDisponibles() throws 
            SQLException {
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            String consulta = "SELECT p.id_proyecto, p.nombre, p.descripcion, "
                + "p.estado, p.cupo, p.fecha_inicio, p.fecha_fin, " 
                + "p.id_organizacion_vinculada " 
                + "FROM proyecto p " 
                + "WHERE p.cupo > 0 " 
                + "AND CURDATE() BETWEEN p.fecha_inicio AND p.fecha_fin;";

            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Proyecto proyecto = new Proyecto();
                proyecto.setIdProyecto(resultado.getInt("id_proyecto"));
                proyecto.setNombre(resultado.getString("nombre"));
                proyecto.setDescripcion(resultado.getString("descripcion"));
                proyecto.setEstado(EstadoProyecto.fromValor(resultado.
                    getString("estado")));
                proyecto.setCupo(resultado.getInt("cupo"));
                proyecto.setFecha_inicio(resultado.getDate("fecha_inicio").
                    toString());
                proyecto.setFecha_fin(resultado.getDate("fecha_fin").
                    toString());
                proyecto.setIdOrganizacionVinculada(resultado.getInt
                    ("id_organizacion_vinculada"));

                proyectos.add(proyecto);
            }

            resultado.close();
            sentencia.close();
            conexion.close();
        }

        return proyectos;
    }

    public static boolean decrementarCupoProyecto(int idProyecto) throws 
            SQLException {
        boolean actualizado = false;
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            String consulta = "UPDATE proyecto SET cupo = cupo - 1 WHERE "
                + "id_proyecto = ? AND cupo > 0;";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, idProyecto);

            int filasAfectadas = sentencia.executeUpdate();
            actualizado = filasAfectadas > 0;

            sentencia.close();
            conexion.close();
        }

        return actualizado;
        }

    
    public static Proyecto obtenerProyectoPorIdEstudiante(int idEstudiante) {
        Proyecto proyecto = null;
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            String consulta = "SELECT p.id_proyecto, p.nombre, p.descripcion, "
                + "p.estado, p.cupo, p.fecha_inicio, p.fecha_fin, "
                + "p.id_organizacion_vinculada "
                + "FROM proyecto p "
                + "JOIN estudiante e ON p.id_proyecto = e.id_proyecto "
                + "WHERE e.id_estudiante = ?";

            try {
                PreparedStatement sentencia = conexion.prepareStatement
                    (consulta);
                sentencia.setInt(1, idEstudiante);
                ResultSet resultado = sentencia.executeQuery();

                if (resultado.next()) {
                    proyecto = new Proyecto();
                    proyecto.setIdProyecto(resultado.getInt("id_proyecto"));
                    proyecto.setNombre(resultado.getString("nombre"));
                    proyecto.setDescripcion(resultado.getString("descripcion"));
                    proyecto.setEstado(EstadoProyecto.fromValor(resultado.
                        getString("estado")));
                    proyecto.setCupo(resultado.getInt("cupo"));
                    proyecto.setFecha_inicio(resultado.getDate("fecha_inicio").
                        toString());
                    proyecto.setFecha_fin(resultado.getDate("fecha_fin").
                        toString());
                    proyecto.setIdOrganizacionVinculada(resultado.getInt
                        ("id_organizacion_vinculada"));
                }

                resultado.close();
                sentencia.close();
                conexion.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return proyecto;
    }
    
    public static ResultadoOperacion registrarProyecto(Proyecto proyecto) {
        ResultadoOperacion resultado = new ResultadoOperacion();

        String sql = "INSERT INTO proyecto (nombre, descripcion, estado, cupo, fecha_inicio, fecha_fin, id_organizacion_vinculada, id_responsable_proyecto) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, proyecto.getNombre());
            stmt.setString(2, proyecto.getDescripcion());
            stmt.setString(3, proyecto.getEstado().toString().toLowerCase()); // activo, concluido, etc.
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
            resultado.setMensaje("Error en la base de datos.");
        }

        return resultado;
    }
    
    public static ArrayList<Proyecto> obtenerTodosLosProyectos() throws SQLException {
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            String consulta = "SELECT id_proyecto, nombre, descripcion, estado, cupo, "
                    + "fecha_inicio, fecha_fin, id_organizacion_vinculada, id_responsable_proyecto "
                    + "FROM proyecto";

            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery();

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

            resultado.close();
            sentencia.close();
            conexion.close();
        }

        return proyectos;
    }

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
            resultado.setMensaje("Error: no se pudo eliminar el proyecto o ya tiene alguna relacion.");
        }

        return resultado;
    }

    public static ResultadoOperacion actualizarProyecto(Proyecto proyecto) {
        ResultadoOperacion resultado = new ResultadoOperacion();

        String sql = "UPDATE proyecto SET nombre = ?, descripcion = ?, estado = ?, cupo = ?, "
                   + "fecha_inicio = ?, fecha_fin = ?, id_organizacion_vinculada = ?, id_responsable_proyecto = ? "
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
