package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.ResponsableProyecto;
import sistemapracticasis.modelo.pojo.ResultadoOperacion;
import sistemapracticasis.util.Utilidad;

public class ResponsableProyectoDAO {

    public static ResultadoOperacion registrarResponsable(ResponsableProyecto responsable) {
        ResultadoOperacion resultado = new ResultadoOperacion();
        String consulta = "INSERT INTO responsable_proyecto (nombre, telefono, "
            + "correo, puesto, departamento, apellido_paterno, apellido_materno, "
            + "id_organizacion_vinculada) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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
                resultado.setMensaje("No se pudo registrar el responsable");
            }
        } catch (SQLException e) {
            resultado.setError(true);
            resultado.setMensaje("No hay conexion con la base de datos");
        }
        return resultado;
    }

    public static List<ResponsableProyecto> obtenerResponsablesPorIdOrganizacion(int idOrganizacion) {
        List<ResponsableProyecto> responsables = new ArrayList<>();
        String consulta = "SELECT id_encargado, nombre, apellido_paterno, apellido_materno, "
                        + "telefono, correo, puesto, departamento, id_organizacion_vinculada "
                        + "FROM responsable_proyecto WHERE id_organizacion_vinculada = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idOrganizacion);
            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    ResponsableProyecto responsable = new ResponsableProyecto();
                    responsable.setIdEncargado(resultado.getInt("id_encargado"));
                    responsable.setNombre(resultado.getString("nombre"));
                    responsable.setApellidoPaterno(resultado.getString("apellido_paterno"));
                    responsable.setApellidoMaterno(resultado.getString("apellido_materno"));
                    responsable.setTelefono(resultado.getString("telefono"));
                    responsable.setCorreo(resultado.getString("correo"));
                    responsable.setPuesto(resultado.getString("puesto"));
                    responsable.setDepartamento(resultado.getString("departamento"));
                    responsable.setIdOrganizacionVinculada(resultado.getInt("id_organizacion_vinculada"));
                    responsables.add(responsable);
                }
            }
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
        }
        return responsables;
    }

    public static ResponsableProyecto obtenerResponsablePorId(int idResponsable) {
        ResponsableProyecto responsable = null;
        String consulta = "SELECT id_encargado, nombre, apellido_paterno, apellido_materno, "
                        + "telefono, correo, puesto, departamento, id_organizacion_vinculada "
                        + "FROM responsable_proyecto WHERE id_encargado = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idResponsable);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    responsable = new ResponsableProyecto();
                    responsable.setIdEncargado(resultado.getInt("id_encargado"));
                    responsable.setNombre(resultado.getString("nombre"));
                    responsable.setApellidoPaterno(resultado.getString("apellido_paterno"));
                    responsable.setApellidoMaterno(resultado.getString("apellido_materno"));
                    responsable.setTelefono(resultado.getString("telefono"));
                    responsable.setCorreo(resultado.getString("correo"));
                    responsable.setPuesto(resultado.getString("puesto"));
                    responsable.setDepartamento(resultado.getString("departamento"));
                    responsable.setIdOrganizacionVinculada(resultado.getInt("id_organizacion_vinculada"));
                }
            }
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la base de datos");
        }
        return responsable;
    }
}
