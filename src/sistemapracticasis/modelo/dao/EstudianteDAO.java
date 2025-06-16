package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.Estudiante;

public class EstudianteDAO {
    public static Estudiante obtenerEstudiantePorIdUsuario(int idUsuario) 
            throws SQLException {
        Estudiante estudiante = null;
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            String consulta = "SELECT * FROM estudiante WHERE id_usuario = ?";
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, idUsuario);
            ResultSet resultado = sentencia.executeQuery();

            if (resultado.next()) {
                estudiante = new Estudiante();
                estudiante.setIdEstudiante(resultado.getInt("id_estudiante"));
                estudiante.setMatricula(resultado.getString("matricula"));
                estudiante.setNombre(resultado.getString("nombre"));
                estudiante.setCorreo(resultado.getString("correo"));
                estudiante.setTelefono(resultado.getString("telefono"));
                estudiante.setApellidoPaterno(resultado.
                    getString("apellido_paterno"));
                estudiante.setApellidoMaterno(resultado.
                    getString("apellido_materno"));
                estudiante.setIdUsuario(resultado.getInt("id_usuario"));
            }

            resultado.close();
            sentencia.close();
            conexion.close();
        }

        return estudiante;
    }
    
    public boolean buscarPorMatricula(String matricula, Estudiante estudiante) {
        boolean encontrado = false;
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            try {
                String consulta =
                    "SELECT estudiante.id_estudiante, estudiante.matricula, " +
                    "estudiante.nombre, estudiante.correo, estudiante." + 
                    "telefono, estudiante.apellido_paterno, " +
                    "estudiante.apellido_materno, estudiante.id_proyecto, " + 
                    "estudiante.id_experiencia_educativa, " +
                    "proyecto.nombre AS nombre_proyecto " +
                    "FROM estudiante " +
                    "LEFT JOIN proyecto ON estudiante.id_proyecto = " +
                    "proyecto.id_proyecto WHERE estudiante.matricula = ?";

                PreparedStatement sentencia = conexion.
                    prepareStatement(consulta);
                sentencia.setString(1, matricula);
                ResultSet resultado = sentencia.executeQuery();

                if (resultado.next()) {
                    estudiante.setIdEstudiante(resultado.
                        getInt("estudiante.id_estudiante"));
                    estudiante.setMatricula(resultado.
                        getString("estudiante.matricula"));
                    estudiante.setNombre(resultado.
                        getString("estudiante.nombre"));
                    estudiante.setCorreo(resultado.
                        getString("estudiante.correo"));
                    estudiante.setTelefono(resultado.
                        getString("estudiante.telefono"));
                    estudiante.setApellidoPaterno(resultado.
                        getString("estudiante.apellido_paterno"));
                    estudiante.setApellidoMaterno(resultado.
                        getString("estudiante.apellido_materno"));
                    estudiante.setIdProyecto(resultado.
                        getInt("estudiante.id_proyecto"));
                    estudiante.setIdExperienciaEducativa(resultado.
                        getInt("estudiante.id_experiencia_educativa"));
                    estudiante.setNombreProyecto(resultado.
                        getString("nombre_proyecto"));
                    
                    encontrado = true;
                }

                resultado.close();
                sentencia.close();
                conexion.close();

            } catch (SQLException e) {
                System.err.println("Error al buscar estudiante por matrícula: "
                    + e.getMessage());
                e.printStackTrace();
            }
        }

        return encontrado;
    }
    
    public boolean asignarProyecto(String matricula, int idProyecto) {
        boolean exito = false;
        String consulta = "UPDATE estudiante SET id_proyecto = ? "
                + "WHERE matricula = ?";
        try (Connection conexion = ConexionBD.abrirConexion();
            PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, idProyecto);
            sentencia.setString(2, matricula);

            int filasAfectadas = sentencia.executeUpdate();
            exito = (filasAfectadas > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }
    
    public boolean tieneEvaluacionAOV(String matriculaEstudiante) {  
        String consulta = "SELECT eval.id_evaluacion_a_organizacion_vinculada "
            + "FROM estudiante est "
            + "JOIN periodo per ON est.id_estudiante = per.id_estudiante "
            + "JOIN expediente exp ON per.id_expediente = exp.id_expediente "
            + "JOIN evaluacion_a_organizacion_vinculada eval ON "
            + "exp.id_expediente = eval.id_expediente "
            + "WHERE est.matricula = ? "
            + "AND exp.estado = 'en curso' "
            + "AND CURDATE() BETWEEN per.fecha_inicio AND per.fecha_fin";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {

            sentencia.setString(1, matriculaEstudiante);

            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    
    public static boolean estaEnPeriodoActual(String matricula) {
        String consulta = "SELECT COUNT(*) > 0 "
            + "FROM periodo p "
            + "INNER JOIN estudiante e ON e.id_estudiante = p.id_estudiante "
            + "WHERE e.matricula = ? "
            + "AND CURDATE() BETWEEN p.fecha_inicio AND p.fecha_fin";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {

            sentencia.setString(1, matricula);

            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() && resultado.getBoolean(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public static int obtenerIdExpedientePorEstudiante(int idEstudiante) {
        int idExpediente = -1;

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(
                 "SELECT id_expediente FROM periodo WHERE id_estudiante = ?")) {

            sentencia.setInt(1, idEstudiante);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    idExpediente = resultado.getInt("id_expediente");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return idExpediente;
    }

}
