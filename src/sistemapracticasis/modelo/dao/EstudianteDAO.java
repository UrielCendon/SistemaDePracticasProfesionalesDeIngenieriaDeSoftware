package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.util.Utilidad;

/**
 * Clase DAO para gestionar las operaciones relacionadas con estudiantes en la 
 * base de datos.
 * Autor: Uriel Cendón
 * Fecha de creación: 15/06/2025
 * Descripción: Proporciona métodos para obtener, buscar y actualizar información 
 * de estudiantes, así como verificar su estado en el sistema.
 */
public class EstudianteDAO {

    /**
     * Obtiene un estudiante por su ID de usuario asociado.
     * @param idUsuario ID del usuario asociado al estudiante.
     * @return Objeto Estudiante con los datos encontrados.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public static Estudiante obtenerEstudiantePorIdUsuario(int idUsuario) throws SQLException {
        Estudiante estudiante = null;
        Connection conexion = ConexionBD.abrirConexion();

        if (conexion != null) {
            String consulta = "SELECT id_estudiante, matricula, nombre, correo, telefono, "
                    + "apellido_paterno, apellido_materno, id_usuario "
                    + "FROM estudiante WHERE id_usuario = ?";
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
                estudiante.setApellidoPaterno(resultado.getString("apellido_paterno"));
                estudiante.setApellidoMaterno(resultado.getString("apellido_materno"));
                estudiante.setIdUsuario(resultado.getInt("id_usuario"));
            }

            resultado.close();
            sentencia.close();
            conexion.close();
        }

        return estudiante;
    }
    
    /**
     * Obtiene todos los estudiantes que se encuentran cursando en el periodo académico actual,
     * junto con la información de su proyecto asignado (si lo tienen).
     * 
     * @return Una lista de objetos Estudiante del periodo actual.
     */
    public static List<Estudiante> obtenerEstudiantesConProyectoDelPeriodoActual() {
        List<Estudiante> estudiantes = new ArrayList<>();

        String consulta = "SELECT e.id_estudiante, e.matricula, e.nombre, e.apellido_paterno, "
            + "e.apellido_materno, e.correo, e.telefono, e.id_proyecto, p.nombre AS "
            + "nombre_proyecto, per.id_periodo "
            + "FROM estudiante e "
            + "JOIN periodo_cursante pc ON e.id_estudiante = pc.id_estudiante "
            + "JOIN periodo per ON pc.id_periodo = per.id_periodo "
            + "LEFT JOIN proyecto p ON e.id_proyecto = p.id_proyecto "
            + "WHERE CURDATE() BETWEEN per.fecha_inicio AND per.fecha_fin "
            + "AND e.id_proyecto IS NULL";

        try (Connection conexion = ConexionBD.abrirConexion();
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Estudiante estudiante = new Estudiante();

                estudiante.setIdEstudiante(resultado.getInt("id_estudiante"));
                estudiante.setMatricula(resultado.getString("matricula"));
                estudiante.setNombre(resultado.getString("nombre"));
                estudiante.setApellidoPaterno(resultado.getString("apellido_paterno"));
                estudiante.setApellidoMaterno(resultado.getString("apellido_materno"));
                estudiante.setCorreo(resultado.getString("correo"));
                estudiante.setTelefono(resultado.getString("telefono"));
                estudiante.setIdProyecto(resultado.getInt("id_proyecto"));
                estudiante.setNombreProyecto(resultado.getString("nombre_proyecto"));
                estudiante.setIdPeriodo(resultado.getInt("id_periodo"));

                estudiantes.add(estudiante);
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la Base de Datos", 
                "No se pudo establecer conexión para obtener los estudiantes.");
        }

        return estudiantes;
    }
    
    /**
     * Obtiene los estudiantes de un profesor en el periodo actual que tienen al menos
    * un documento o reporte pendiente de validación (validado = 0).
    * 
    * @param idProfesor El ID del profesor para filtrar a los estudiantes.
    * @return Una lista de Estudiantes que tienen entregas sin validar.
    */
    public static List<Estudiante> obtenerEstudiantesDeProfesorConDocumentoSinValidar
            (int idProfesor) {
        List<Estudiante> estudiantes = new ArrayList<>();

        String consulta = "SELECT e.id_estudiante, e.matricula, e.nombre, e.apellido_paterno, "
            + "e.apellido_materno, e.correo, e.telefono, e.id_proyecto, p.nombre AS "
            + "nombre_proyecto, per.id_periodo "
            + "FROM estudiante e "
            + "JOIN periodo_cursante pc ON e.id_estudiante = pc.id_estudiante "
            + "JOIN periodo per ON pc.id_periodo = per.id_periodo "
            + "LEFT JOIN proyecto p ON e.id_proyecto = p.id_proyecto "
            + "JOIN expediente exp ON e.id_estudiante = exp.id_estudiante "
            + "WHERE (CURDATE() BETWEEN per.fecha_inicio AND per.fecha_fin) "
            + "AND e.id_experiencia_educativa = "
            + "(SELECT id_experiencia_educativa FROM profesor WHERE id_profesor = ?) "
            + "AND EXISTS ("
            + "  SELECT 1 FROM entrega_documento ed "
            + "  WHERE ed.id_expediente = exp.id_expediente "
            + "  AND ed.validado = 0 AND (ed.calificacion = 0 OR ed.calificacion IS NULL) "
            + "  AND EXISTS (SELECT 1 FROM documento d WHERE d.id_entrega_documento = "
            + "ed.id_entrega_documento))";

        try (Connection conexion = ConexionBD.abrirConexion();
            PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idProfesor);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    Estudiante estudiante = new Estudiante();

                    estudiante.setIdEstudiante(resultado.getInt("id_estudiante"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setApellidoPaterno(resultado.getString("apellido_paterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellido_materno"));
                    estudiante.setCorreo(resultado.getString("correo"));
                    estudiante.setTelefono(resultado.getString("telefono"));
                    estudiante.setIdProyecto(resultado.getInt("id_proyecto"));
                    estudiante.setNombreProyecto(resultado.getString("nombre_proyecto"));
                    estudiante.setIdPeriodo(resultado.getInt("id_periodo"));

                    estudiantes.add(estudiante);
                }
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorBD", 
                "No hay conexión con la base de datos.");
        }

        return estudiantes;
    }

    /**
     * Asigna un proyecto a un estudiante mediante su matrícula.
     * @param matricula Matrícula del estudiante a actualizar.
     * @param idProyecto ID del proyecto a asignar.
     * @return true si la asignación fue exitosa, false en caso contrario.
     */
    public boolean asignarProyecto(String matricula, int idProyecto) {
        boolean exito = false;
        String consulta = "UPDATE estudiante SET id_proyecto = ? WHERE matricula = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idProyecto);
            sentencia.setString(2, matricula);

            int filasAfectadas = sentencia.executeUpdate();
            exito = (filasAfectadas > 0);

        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }

        return exito;
    }

    /**
     * Verifica si un estudiante tiene evaluación AOV (A Organización Vinculada).
     * @param matriculaEstudiante Matrícula del estudiante a verificar.
     * @return true si el estudiante tiene evaluación AOV, false en caso 
     *         contrario.
     */
    public boolean tieneEvaluacionAOV(String matriculaEstudiante) {
        String consulta = "SELECT eval.id_evaluacion_a_organizacion_vinculada "
                + "FROM estudiante est "
                + "JOIN expediente exp ON est.id_estudiante = exp.id_estudiante "
                + "JOIN periodo per ON exp.id_periodo = per.id_periodo "
                + "JOIN evaluacion_a_organizacion_vinculada eval ON exp.id_expediente = eval.id_expediente "
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
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }
        return false;
    }

    /**
     * Verifica si un estudiante está en periodo actual.
     * @param matricula Matrícula del estudiante a verificar.
     * @return true si el estudiante está en periodo actual, false en caso 
     *         contrario.
     */
    public static boolean estaEnPeriodoActual(String matricula) {
        String consulta = "SELECT COUNT(*) > 0 "
            + "FROM periodo p "
            + "INNER JOIN periodo_cursante pc ON p.id_periodo = pc.id_periodo "
            + "INNER JOIN estudiante e ON pc.id_estudiante = e.id_estudiante "
            + "WHERE e.matricula = ? "
            + "AND CURDATE() BETWEEN p.fecha_inicio AND p.fecha_fin";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {

            sentencia.setString(1, matricula);

            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() && resultado.getBoolean(1);
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }
        return false;
    }
}
