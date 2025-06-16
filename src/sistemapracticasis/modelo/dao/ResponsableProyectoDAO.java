package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.ResponsableProyecto;
import sistemapracticasis.modelo.pojo.ResultadoOperacion;

public class ResponsableProyectoDAO {

    public static ResultadoOperacion registrarResponsable(ResponsableProyecto responsable) {
        ResultadoOperacion resultado = new ResultadoOperacion();

        String consulta = "INSERT INTO responsable_proyecto "
                + "(nombre, telefono, correo, puesto, departamento, apellido_paterno, apellido_materno, id_organizacion_vinculada) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setString(1, responsable.getNombre());
            sentencia.setString(2, responsable.getTelefono());
            sentencia.setString(3, responsable.getCorreo());
            sentencia.setString(4, responsable.getPuesto());
            sentencia.setString(5, responsable.getDepartamento());
            sentencia.setString(6, responsable.getApellidoPaterno());
            sentencia.setString(7, responsable.getApellidoMaterno());
            sentencia.setInt(8, responsable.getIdOrganizacionVinculada());

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas > 0) {
                resultado.setError(false);
                resultado.setMensaje("Responsable del proyecto registrado con éxito");
            } else {
                resultado.setError(true);
                resultado.setMensaje("No se pudo registrar el responsable.");
            }

        } catch (SQLException e) {
            resultado.setError(true);
            resultado.setMensaje("No hay conexión con la base de datos");
        }

        return resultado;
    }

    public static boolean existeCorreoResponsable(String correo) {
        boolean existe = false;

        String consulta = "SELECT COUNT(*) FROM responsable_proyecto WHERE correo = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setString(1, correo);
            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next() && resultado.getInt(1) > 0) {
                existe = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return existe;
    }
    
    public static List<ResponsableProyecto> obtenerResponsablesPorIdOrganizacion(int idOrganizacion) {
        List<ResponsableProyecto> responsables = new ArrayList<>();
        String query = "SELECT id_encargado, nombre, apellido_paterno, apellido_materno, "
                     + "telefono, correo, puesto, departamento, id_organizacion_vinculada "
                     + "FROM responsable_proyecto "
                     + "WHERE id_organizacion_vinculada = ?";
        
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idOrganizacion);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ResponsableProyecto responsable = new ResponsableProyecto();
                    responsable.setIdEncargado(rs.getInt("id_encargado"));
                    responsable.setNombre(rs.getString("nombre"));
                    responsable.setApellidoPaterno(rs.getString("apellido_paterno"));
                    responsable.setApellidoMaterno(rs.getString("apellido_materno"));
                    responsable.setTelefono(rs.getString("telefono"));
                    responsable.setCorreo(rs.getString("correo"));
                    responsable.setPuesto(rs.getString("puesto"));
                    responsable.setDepartamento(rs.getString("departamento"));
                    responsable.setIdOrganizacionVinculada(rs.getInt("id_organizacion_vinculada"));
                    
                    responsables.add(responsable);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responsables;
    }

    public static ResponsableProyecto obtenerResponsablePorId(int idResponsable) {
        String consulta = "SELECT * FROM responsable_proyecto WHERE id_encargado = ?";
        ResponsableProyecto responsable = null;

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement stmt = conexion.prepareStatement(consulta)) {

            stmt.setInt(1, idResponsable);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                responsable = new ResponsableProyecto();
                responsable.setIdEncargado(rs.getInt("id_encargado"));
                responsable.setNombre(rs.getString("nombre"));
                responsable.setApellidoPaterno(rs.getString("apellido_paterno"));
                responsable.setApellidoMaterno(rs.getString("apellido_materno"));
                responsable.setTelefono(rs.getString("telefono"));
                responsable.setCorreo(rs.getString("correo"));
                responsable.setPuesto(rs.getString("puesto"));
                responsable.setDepartamento(rs.getString("departamento"));
                responsable.setIdOrganizacionVinculada(rs.getInt("id_organizacion_vinculada"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return responsable;
    }

}
