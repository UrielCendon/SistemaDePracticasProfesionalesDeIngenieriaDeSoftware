package sistemapracticasis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;

import sistemapracticasis.modelo.conexion.ConexionBD;
import sistemapracticasis.modelo.pojo.EntregaReporte;
import sistemapracticasis.util.Utilidad;

/**
 * Clase DAO para gestionar las operaciones relacionadas con entregas de reportes
 * en la base de datos.
 * Autor: Raziel Filobello
 * Fecha de creación: 15/06/2025
 * Descripción: Proporciona métodos para validar, generar y guardar entregas de 
 * reportes, así como verificar su existencia.
 */
public class EntregaReporteDAO {

    /**
     * Valida una entrega de reporte y asigna una calificación.
     * @param idReporte ID del reporte a validar.
     * @param calificacion Calificación a asignar.
     * @return true si la validación fue exitosa, false en caso contrario.
     */
    public static boolean validarEntregaReporte(int idReporte, float calificacion) {
        String consulta = "UPDATE entrega_reporte er "
            + "JOIN reporte r ON er.id_entrega_reporte = r.id_entrega_reporte "
            + "SET er.validado = 1, er.calificacion = ? "
            + "WHERE r.idreporte = ?";

        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setFloat(1, calificacion);
            sentencia.setInt(2, idReporte);

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
            return false;
        }
    }

    /**
     * Genera entregas de reportes por defecto para un periodo.
     * @param fechaInicio Fecha de inicio del periodo.
     * @param fechaFin Fecha de fin del periodo.
     * @return Lista de entregas de reportes generadas.
     */
    public static ArrayList<EntregaReporte> generarEntregasReportesPorDefecto(String fechaInicio, 
            String fechaFin) {
        ArrayList<EntregaReporte> entregas = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            EntregaReporte entrega = new EntregaReporte(
                0, 
                "Entrega " + i, 
                fechaInicio, 
                fechaFin, 
                0, 
                0.0, 
                0, 
                0
            );
            entrega.setObservacion("Observación para entrega " + i);
            entregas.add(entrega);
        }

        return entregas;
    }

    /**
     * Guarda las entregas de reportes en la base de datos para todos los 
     * expedientes de un periodo.
     */
    public static boolean guardarEntregasReportes(ArrayList<EntregaReporte> entregas, 
                                               String fechaInicioPeriodo, 
                                               String fechaFinPeriodo) {
        try (Connection conexion = ConexionBD.abrirConexion()) {
            ArrayList<Integer> expedientes = obtenerExpedientesParaPeriodo(conexion, fechaInicioPeriodo, fechaFinPeriodo);

            if (expedientes.isEmpty()) {
                mostrarAdvertenciaSinExpedientes();
                return false;
            }

            int totalInsertadas = procesarEntregasReportes(conexion, entregas, expedientes);
            return totalInsertadas > 0;

        } catch (SQLException e) {
            manejarErrorGuardado(e);
            throw new RuntimeException("Error al guardar entregas de reportes", e);
        }
    }

    /**
     * Obtiene los expedientes asociados a un periodo específico.
     * @param conexion Conexión a la base de datos.
     * @param fechaInicio Fecha de inicio del periodo.
     * @param fechaFin Fecha de fin del periodo.
     * @return Lista de IDs de expedientes para el periodo.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private static ArrayList<Integer> obtenerExpedientesParaPeriodo(Connection conexion, 
                                                                 String fechaInicio, 
                                                                 String fechaFin) throws SQLException {
        String sql = "SELECT e.id_expediente FROM expediente e "
                   + "JOIN periodo p ON e.id_periodo = p.id_periodo "
                   + "WHERE p.fecha_inicio = ? AND p.fecha_fin = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, fechaInicio);
            stmt.setString(2, fechaFin);

            ResultSet rs = stmt.executeQuery();
            ArrayList<Integer> expedientes = new ArrayList<>();

            while (rs.next()) {
                expedientes.add(rs.getInt("id_expediente"));
            }

            return expedientes;
        }
    }

    /**
     * Muestra una advertencia cuando no se encuentran expedientes para el periodo.
     */
    private static void mostrarAdvertenciaSinExpedientes() {
        Utilidad.mostrarAlertaSimple(
            Alert.AlertType.WARNING, 
            "Sin expedientes", 
            "No se encontraron expedientes para el periodo actual."
        );
    }

    /**
     * Procesa el guardado de las entregas de reportes en la base de datos.
     * @param conexion Conexión a la base de datos.
     * @param entregas Lista de entregas de reportes a guardar.
     * @param expedientes Lista de IDs de expedientes asociados.
     * @return Número total de entregas insertadas.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private static int procesarEntregasReportes(Connection conexion, 
                                             ArrayList<EntregaReporte> entregas, 
                                             ArrayList<Integer> expedientes) throws SQLException {
        try (PreparedStatement stmtInsert = crearStatementInsertReporte(conexion);
             PreparedStatement stmtExiste = crearStatementExisteReporte(conexion)) {

            int totalInsertadas = 0;

            for (EntregaReporte entrega : entregas) {
                totalInsertadas += procesarEntregaReporte(entrega, expedientes, stmtInsert, stmtExiste);
            }

            if (totalInsertadas > 0) {
                stmtInsert.executeBatch();
            }

            return totalInsertadas;
        }
    }

    /**
     * Crea el PreparedStatement para insertar una entrega de reporte.
     * @param conexion Conexión a la base de datos.
     * @return PreparedStatement configurado para la inserción.
     * @throws SQLException Si ocurre un error al crear el statement.
     */
    private static PreparedStatement crearStatementInsertReporte(Connection conexion) throws SQLException {
        String sql = "INSERT INTO entrega_reporte("
                   + "nombre, fecha_inicio, fecha_fin, validado, calificacion, "
                   + "id_expediente) VALUES (?, ?, ?, 0, ?, ?)";
        return conexion.prepareStatement(sql);
    }

    /**
     * Crea el PreparedStatement para verificar si existe una entrega de reporte.
     * @param conexion Conexión a la base de datos.
     * @return PreparedStatement configurado para la verificación.
     * @throws SQLException Si ocurre un error al crear el statement.
     */
    private static PreparedStatement crearStatementExisteReporte(Connection conexion) throws SQLException {
        String sql = "SELECT 1 FROM entrega_reporte WHERE nombre = ? AND id_expediente = ?";
        return conexion.prepareStatement(sql);
    }

    /**
     * Procesa una entrega de reporte individual para todos los expedientes.
     * @param entrega Entrega de reporte a procesar.
     * @param expedientes Lista de IDs de expedientes.
     * @param stmtInsert Statement para insertar entregas.
     * @param stmtExiste Statement para verificar existencia.
     * @return Número de entregas insertadas para esta entrega.
     * @throws SQLException Si ocurre un error al procesar la entrega.
     */
    private static int procesarEntregaReporte(EntregaReporte entrega, 
                                           ArrayList<Integer> expedientes,
                                           PreparedStatement stmtInsert,
                                           PreparedStatement stmtExiste) throws SQLException {
        int insertadasPorEntrega = 0;

        for (int idExpediente : expedientes) {
            if (!existeEntregaReporte(entrega, idExpediente, stmtExiste)) {
                configurarInsertReporte(stmtInsert, entrega, idExpediente);
                stmtInsert.addBatch();
                insertadasPorEntrega++;
            }
        }

        return insertadasPorEntrega;
    }

    /**
     * Verifica si ya existe una entrega de reporte con el mismo nombre para un expediente.
     * @param entrega Entrega de reporte a verificar.
     * @param idExpediente ID del expediente a verificar.
     * @param stmtExiste Statement para verificar existencia.
     * @return true si ya existe, false en caso contrario.
     * @throws SQLException Si ocurre un error al verificar.
     */
    private static boolean existeEntregaReporte(EntregaReporte entrega, 
                                             int idExpediente,
                                             PreparedStatement stmtExiste) throws SQLException {
        stmtExiste.setString(1, entrega.getNombre());
        stmtExiste.setInt(2, idExpediente);
        try (ResultSet rs = stmtExiste.executeQuery()) {
            return rs.next();
        }
    }

    /**
     * Configura los parámetros para insertar una entrega de reporte.
     * @param stmt Statement a configurar.
     * @param entrega Entrega de reporte con los datos.
     * @param idExpediente ID del expediente asociado.
     * @throws SQLException Si ocurre un error al configurar.
     */
    private static void configurarInsertReporte(PreparedStatement stmt, 
                                             EntregaReporte entrega, 
                                             int idExpediente) throws SQLException {
        stmt.setString(1, entrega.getNombre());
        stmt.setString(2, entrega.getFechaInicio());
        stmt.setString(3, entrega.getFechaFin());
        stmt.setDouble(4, entrega.getCalificacion());
        stmt.setInt(5, idExpediente);
    }

    /**
     * Maneja y muestra un error ocurrido durante el guardado de entregas.
     * @param e Excepción SQL ocurrida.
     */
    private static void manejarErrorGuardado(SQLException e) {
        Utilidad.mostrarAlertaSimple(
            Alert.AlertType.ERROR,
            "Error al guardar",
            "No se pudieron guardar las entregas de reportes: " + e.getMessage()
        );
    }

    /**
     * Verifica si existen entregas de reportes para un periodo específico.
     * @param fechaInicio Fecha de inicio del periodo.
     * @param fechaFin Fecha de fin del periodo.
     * @return true si existen entregas de reportes, false en caso contrario.
     */
    public static boolean existenEntregasDeReportesParaPeriodo(String fechaInicio, 
            String fechaFin) {
        String sql = "SELECT COUNT(*) FROM entrega_reporte er "
            + "JOIN expediente e ON er.id_expediente = e.id_expediente "
            + "JOIN periodo p ON e.id_periodo = p.id_periodo "
            + "JOIN periodo_cursante pc ON p.id_periodo = pc.id_periodo "
            + "WHERE p.fecha_inicio = ? AND p.fecha_fin = ?";

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "ErrorDB", 
                "Error con la base de datos");
        }

        return false;
    }
}