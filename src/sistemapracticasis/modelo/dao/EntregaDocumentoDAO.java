package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;

import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.EntregaDocumento;
import sistemapracticasis.modelo.pojo.EntregaDocumentoTipo;
import sistemapracticasis.modelo.pojo.TipoDocumento;
import sistemapracticasis.util.Utilidad;

/**Add commentMore actions
 * Clase DAO para gestionar las operaciones relacionadas con entregas de 
 * documentos en la base de datos.
 * Autor: Raziel Filobello
 * Fecha de creación: 15/06/2025
 * Descripción: Proporciona métodos para verificar, crear y gestionar entregas 
 * de documentos iniciales y sus validaciones.
 */
public class EntregaDocumentoDAO {

    /**
     * Verifica si existe una entrega inicial vigente para un estudiante.
     * @param idEstudiante ID del estudiante a verificar.
     * @return true si existe una entrega inicial vigente, false en caso 
     *         contrario.
     */
    public static boolean existeEntregaInicialVigente(int idEstudiante) {
        String consulta = "SELECT 1 FROM entrega_documento ed "
            + "JOIN expediente e ON ed.id_expediente = e.id_expediente "
            + "JOIN periodo p ON e.id_periodo = p.id_periodo "
            + "JOIN periodo_cursante pc ON p.id_periodo = pc.id_periodo "
            + "WHERE pc.id_estudiante = ? AND ed.tipo_entrega = 'inicial' "
            + "AND CURDATE() BETWEEN p.fecha_inicio AND p.fecha_fin "
            + "LIMIT 1";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {

            sentencia.setInt(1, idEstudiante);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next();
            }

        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }

        return false;
    }

    /**
     * Verifica si existen entregas iniciales para un periodo específico.
     * @param idPeriodo ID del periodo a verificar.
     * @return true si existen entregas iniciales, false en caso contrario.
     */
    public static boolean existenEntregasInicialesParaPeriodo(int idPeriodo) {
        String consulta = "SELECT 1 FROM entrega_documento ed "
            + "JOIN expediente e ON ed.id_expediente = e.id_expediente "
            + "WHERE ed.tipo_entrega = 'inicial' AND e.id_periodo = ?";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {

            sentencia.setInt(1, idPeriodo);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next();
            }

        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }

        return false;
    }

    /**
     * Verifica si existen entregas iniciales para un rango de fechas.
     * @param fechaInicio Fecha de inicio del periodo.
     * @param fechaFin Fecha de fin del periodo.
     * @return true si existen entregas iniciales, false en caso contrario.
     */
    public static boolean existenEntregasInicialesParaPeriodo(String fechaInicio, 
            String fechaFin) {
        String consulta = "SELECT COUNT(*) FROM entrega_documento ed "
            + "JOIN periodo p ON ed.id_expediente = p.id_expediente "
            + "WHERE p.fecha_inicio = ? AND p.fecha_fin = ?";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(consulta)) {

            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con la "
                + "base de datos");
        }
        return false;
    }

    /**
     * Obtiene las entregas iniciales asociadas a un expediente.
     * @param idExpediente ID del expediente del cual obtener las entregas.
     * @return Lista de objetos EntregaDocumento con la información de las 
     *         entregas.
     */
    public static List<EntregaDocumento> obtenerEntregasInicialesPorExpediente(int idExpediente) {
        List<EntregaDocumento> entregas = new ArrayList<>();
        String consulta = "SELECT id_entrega_documento, nombre, fecha_inicio, fecha_fin, "
            + "validado, calificacion, id_expediente, id_observacion FROM entrega_documento "
            + "WHERE id_expediente = ? AND tipo_entrega = 'inicial'";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {

            sentencia.setInt(1, idExpediente);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                entregas.add(new EntregaDocumento(
                    resultado.getInt("id_entrega_documento"),
                    resultado.getString("nombre"),
                    resultado.getString("fecha_inicio"),
                    resultado.getString("fecha_fin")
                ));
            }
            resultado.close();
            sentencia.close();
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }
        return entregas;
    }

    /**
     * Genera entregas iniciales por defecto basadas en los tipos de documento.
     * @param fechaInicio Fecha de inicio para las entregas.
     * @param fechaFin Fecha de fin para las entregas.
     * @return Lista de entregas de documentos iniciales generadas.
     */
    public static ArrayList<EntregaDocumento> generarEntregasInicialesPorDefecto
            (String fechaInicio, String fechaFin) {
        ArrayList<EntregaDocumento> entregas = new ArrayList<>();
        for (TipoDocumento tipo : TipoDocumento.values()) {
            EntregaDocumento entrega = new EntregaDocumento(
                0, tipo.getValorEnDB(), fechaInicio, fechaFin);
            entrega.setCalificacion(0.0);
            entrega.setTipoEntrega(EntregaDocumentoTipo.INICIAL);
            entregas.add(entrega);
        }
        return entregas;
    }

    /**
     * Guarda las entregas iniciales en la base de datos para todos los expedientes de un periodo.
     * @param entregas Lista de entregas a guardar.
     * @param fechaInicioPeriodo Fecha de inicio del periodo.
     * @param fechaFinPeriodo Fecha de fin del periodo.
     * @return true si se insertaron entregas, false si ya existían entregas para este periodo.
     * @throws SQLException Si ocurre un error de base de datos o no hay expedientes para el 
     *         periodo.
     */
    public static boolean guardarEntregasIniciales(ArrayList<EntregaDocumento> entregas, 
                                                String fechaInicioPeriodo, 
                                                String fechaFinPeriodo) throws SQLException {
        Connection conexion = null;
        try {
            conexion = ConexionBD.abrirConexion();
            conexion.setAutoCommit(false);

            if (existenEntregasInicialesParaPeriodo(conexion, fechaInicioPeriodo, fechaFinPeriodo)) {
                return false;
            }

            String obtenerExpedientesSQL = "SELECT e.id_expediente FROM expediente e "
                + "JOIN periodo p ON e.id_periodo = p.id_periodo "
                + "WHERE p.fecha_inicio = ? AND p.fecha_fin = ?";

            PreparedStatement stmtExpedientes = conexion.prepareStatement(obtenerExpedientesSQL);
            stmtExpedientes.setString(1, fechaInicioPeriodo);
            stmtExpedientes.setString(2, fechaFinPeriodo);
            ResultSet rs = stmtExpedientes.executeQuery();

            ArrayList<Integer> expedientes = new ArrayList<>();
            while (rs.next()) {
                expedientes.add(rs.getInt("id_expediente"));
            }

            if (expedientes.isEmpty()) {
                throw new SQLException("No se encontraron expedientes para el periodo actual");
            }

            String insertarEntregaSQL = "INSERT INTO entrega_documento("
                + "nombre, fecha_inicio, fecha_fin, tipo_entrega, validado, calificacion, "
                + "id_expediente, tipo_descripcion) "
                + "VALUES (?, ?, ?, 'inicial', 0, ?, ?, ?)";

            String existeEntregaSQL = "SELECT 1 FROM entrega_documento WHERE nombre = ? AND "
                + "id_expediente = ? AND tipo_entrega = 'inicial'";

            PreparedStatement stmtInsert = conexion.prepareStatement(insertarEntregaSQL);
            PreparedStatement stmtExiste = conexion.prepareStatement(existeEntregaSQL);

            int totalEntregasInsertadas = 0;

            for (EntregaDocumento entrega : entregas) {
                for (int idExpediente : expedientes) {
                    if (!existeEntrega(conexion, entrega.getNombre(), idExpediente, stmtExiste)) {
                        stmtInsert.setString(1, entrega.getNombre());
                        stmtInsert.setString(2, entrega.getFechaInicio());
                        stmtInsert.setString(3, entrega.getFechaFin());
                        stmtInsert.setDouble(4, entrega.getCalificacion());
                        stmtInsert.setInt(5, idExpediente);
                        stmtInsert.setString(6, mapearNombreATipoDescripcion(entrega.getNombre()));
                        stmtInsert.addBatch();
                        totalEntregasInsertadas++;
                    }
                }
            }

            if (totalEntregasInsertadas > 0) {
                stmtInsert.executeBatch();
                conexion.commit();
                return true;
            } else {
                conexion.rollback();
                return false;
            }

        } catch (SQLException e) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException ex) {
                    throw new SQLException("Error al hacer rollback: " + ex.getMessage(), ex);
                }
            }
            throw e;
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar conexión: " + e.getMessage());
                }
            }
        }
    }

    /**
     * 
     * @param conexion
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws SQLException 
     */
    private static boolean existenEntregasInicialesParaPeriodo(Connection conexion, 
            String fechaInicio, String fechaFin) throws SQLException {
        String sql = "SELECT 1 FROM entrega_documento ed "
            + "JOIN expediente e ON ed.id_expediente = e.id_expediente "
            + "JOIN periodo p ON e.id_periodo = p.id_periodo "
            + "WHERE p.fecha_inicio = ? AND p.fecha_fin = ? AND ed.tipo_entrega = 'inicial' "
            + "LIMIT 1";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    /**
     * Para obtener el id de la observacion
     * @param conexion
     * @param descripcion
     * @param stmtGetObs
     * @param stmtInsertObs
     * @return
     * @throws SQLException 
     */
    private static int obtenerIdObservacion(Connection conexion, String descripcion, 
                                            PreparedStatement stmtGetObs, PreparedStatement 
                                            stmtInsertObs) throws SQLException {
        stmtGetObs.setString(1, descripcion);
        ResultSet rsObs = stmtGetObs.executeQuery();

        if (rsObs.next()) {
            return rsObs.getInt("id_observacion");
        } else {
            stmtInsertObs.setString(1, descripcion);
            stmtInsertObs.executeUpdate();
            ResultSet generatedKeys = stmtInsertObs.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            throw new SQLException("No se pudo obtener el ID de la observación insertada.");
        }
    }
    /**
     * 
     * @param conexion
     * @param nombre
     * @param idExpediente
     * @param stmtExiste
     * @return
     * @throws SQLException 
     */
    private static boolean existeEntrega(Connection conexion, String nombre, int idExpediente, 
                                       PreparedStatement stmtExiste) throws SQLException {
        stmtExiste.setString(1, nombre);
        stmtExiste.setInt(2, idExpediente);
        try (ResultSet rs = stmtExiste.executeQuery()) {
            return rs.next();
        }
    }

    /**
     * Mapea el nombre del documento a un valor válido para el campo tipo_descripcion ENUM
     */
    private static String mapearNombreATipoDescripcion(String nombreDocumento) {
        if (nombreDocumento.toLowerCase().contains("carta")) {
            return "carta de aceptacion";
        } else if (nombreDocumento.toLowerCase().contains("seguro")) {
            return "constancia seguro";
        } else if (nombreDocumento.toLowerCase().contains("cronograma")) {
            return "cronograma actividades";
        } else if (nombreDocumento.toLowerCase().contains("horario")) {
            return "horario uv";
        } else if (nombreDocumento.toLowerCase().contains("oficio")) {
            return "oficio asignacion";
        }

        return "carta de aceptacion"; // Valor por defecto
    }

    /**
     * Valida una entrega de documento y asigna una calificación.
     * @param idDocumento ID del documento a validar.
     * @param calificacion Calificación a asignar.
     * @return true si la validación fue exitosa, false en caso contrario.
     */
    public static boolean validarEntregaDocumento(int idDocumento, float calificacion) {
        String consulta = "UPDATE entrega_documento ed "
            + "JOIN documento d ON ed.id_entrega_documento = d.id_entrega_documento "
            + "SET ed.validado = 1, ed.calificacion = ? "
            + "WHERE d.id_documento = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setFloat(1, calificacion);
            sentencia.setInt(2, idDocumento);

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", "Error con "
                + "la base de datos");
            return false;
        }
    }
}