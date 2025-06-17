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

/**Clase DAO para gestionar las operaciones relacionadas con entregas de 
 * documentos en la base de datos.2
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
     * @param entregas Lista de entregas a guardar
     * @param fechaInicioPeriodo Fecha de inicio del periodo
     * @param fechaFinPeriodo Fecha de fin del periodo
     * @return true si se guardaron correctamente, false en caso contrario
     * @throws SQLException Si ocurre un error en la base de datos
     */
    public static boolean guardarEntregasIniciales(List<EntregaDocumento> entregas, 
                                                String fechaInicioPeriodo, 
                                                String fechaFinPeriodo) throws SQLException {
        Connection conexion = null;
        try {
            conexion = ConexionBD.abrirConexion();
            conexion.setAutoCommit(false);

            List<Integer> expedientes = obtenerExpedientesParaPeriodo(conexion, 
                    fechaInicioPeriodo, fechaFinPeriodo);
            validarExpedientes(expedientes);

            int totalEntregasInsertadas = procesarEntregas(conexion, entregas, expedientes);
            return manejarResultadoTransaccion(conexion, totalEntregasInsertadas);

        } catch (SQLException e) {
            manejarErrorTransaccion(conexion, e);
            throw e;
        } finally {
            cerrarConexion(conexion);
        }
    }

    /**
     * Obtiene los expedientes asociados a un periodo específico.
     */
    private static List<Integer> obtenerExpedientesParaPeriodo(Connection conexion, 
                                                             String fechaInicio, 
                                                             String fechaFin) throws SQLException {
        final String sql = "SELECT e.id_expediente FROM expediente e "
                         + "JOIN periodo p ON e.id_periodo = p.id_periodo "
                         + "WHERE p.fecha_inicio = ? AND p.fecha_fin = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, fechaInicio);
            stmt.setString(2, fechaFin);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Integer> expedientes = new ArrayList<>();
                while (rs.next()) {
                    expedientes.add(rs.getInt("id_expediente"));
                }
                return expedientes;
            }
        }
    }

    /**
     * Valida que existan expedientes para procesar.
     */
    private static void validarExpedientes(List<Integer> expedientes) throws SQLException {
        if (expedientes.isEmpty()) {
            throw new SQLException("No se encontraron expedientes para el periodo actual");
        }
    }

    /**
     * Procesa todas las entregas para los expedientes encontrados.
     */
    private static int procesarEntregas(Connection conexion, 
                                      List<EntregaDocumento> entregas, 
                                      List<Integer> expedientes) throws SQLException {
        int totalInsertadas = 0;

        try (PreparedStatement stmtInsert = crearStatementInsert(conexion)) {
            for (int idExpediente : expedientes) {
                if (!existenEntregasInicialesParaExpediente(conexion, idExpediente)) {
                    totalInsertadas += insertarEntregasParaExpediente(entregas, idExpediente, stmtInsert);
                }
            }

            if (totalInsertadas > 0) {
                stmtInsert.executeBatch();
            }
        }

        return totalInsertadas;
    }

    /**
     * Inserta las entregas para los expedientes
     * @param entregas
     * @param idExpediente
     * @param stmtInsert
     * @return
     * @throws SQLException 
     */
    private static int insertarEntregasParaExpediente(List<EntregaDocumento> entregas, 
                                                    int idExpediente,
                                                    PreparedStatement stmtInsert) throws SQLException {
        int insertadas = 0;
        for (EntregaDocumento entrega : entregas) {
            configurarInsertEntrega(stmtInsert, entrega, idExpediente);
            stmtInsert.addBatch();
            insertadas++;
        }
        return insertadas;
    }
    /**
     * Crea el PreparedStatement para insertar entregas.
     */
    private static PreparedStatement crearStatementInsert(Connection conexion) throws SQLException {
        final String sql = "INSERT INTO entrega_documento("
                         + "nombre, fecha_inicio, fecha_fin, tipo_entrega, validado, calificacion, "
                         + "id_expediente, tipo_descripcion) "
                         + "VALUES (?, ?, ?, 'inicial', 0, ?, ?, ?)";
        return conexion.prepareStatement(sql);
    }

    /**
     * Crea el PreparedStatement para verificar existencia de entregas.
     */
    private static PreparedStatement crearStatementExiste(Connection conexion) throws SQLException {
        final String sql = "SELECT 1 FROM entrega_documento WHERE nombre = ? AND "
                         + "id_expediente = ? AND tipo_entrega = 'inicial'";
        return conexion.prepareStatement(sql);
    }

    /**
     * Procesa todas las entregas en batch.
     */
    private static int procesarTodasEntregas(List<EntregaDocumento> entregas, 
                                           List<Integer> expedientes,
                                           PreparedStatement stmtInsert,
                                           PreparedStatement stmtExiste) throws SQLException {
        int totalInsertadas = 0;

        for (EntregaDocumento entrega : entregas) {
            totalInsertadas += procesarEntregaParaExpedientes(entrega, expedientes, 
                    stmtInsert, stmtExiste);
        }

        if (totalInsertadas > 0) {
            stmtInsert.executeBatch();
        }

        return totalInsertadas;
    }

    /**
     * Procesa una entrega para todos los expedientes.
     */
    private static int procesarEntregaParaExpedientes(EntregaDocumento entrega, 
                                                    List<Integer> expedientes,
                                                    PreparedStatement stmtInsert,
                                                    PreparedStatement stmtExiste)
            throws SQLException {
        int insertadasPorEntrega = 0;

        for (int idExpediente : expedientes) {
            if (!existeEntrega(entrega.getNombre(), idExpediente, stmtExiste)) {
                configurarInsertEntrega(stmtInsert, entrega, idExpediente);
                stmtInsert.addBatch();
                insertadasPorEntrega++;
            }
        }

        return insertadasPorEntrega;
    }

    /**
     * Verifica si ya existe una entrega para un expediente.
     */
    private static boolean existeEntrega(String nombreEntrega, 
                                       int idExpediente,
                                       PreparedStatement stmtExiste) throws SQLException {
        stmtExiste.setString(1, nombreEntrega);
        stmtExiste.setInt(2, idExpediente);
        try (ResultSet rs = stmtExiste.executeQuery()) {
            return rs.next();
        }
    }

    /**
     * Configura los parámetros para insertar una entrega.
     */
    private static void configurarInsertEntrega(PreparedStatement stmt, 
                                             EntregaDocumento entrega, 
                                             int idExpediente) throws SQLException {
        stmt.setString(1, entrega.getNombre());
        stmt.setString(2, entrega.getFechaInicio());
        stmt.setString(3, entrega.getFechaFin());
        stmt.setDouble(4, entrega.getCalificacion());
        stmt.setInt(5, idExpediente);
        stmt.setString(6, mapearNombreATipoDescripcion(entrega.getNombre()));
        stmt.clearParameters();
    }

    /**
     * Maneja el resultado de la transacción.
     */
    private static boolean manejarResultadoTransaccion(Connection conexion, 
                                                     int totalInsertadas) throws SQLException {
        if (totalInsertadas > 0) {
            conexion.commit();
            return true;
        }
        conexion.rollback();
        return false;
    }

    /**
     * Maneja errores durante la transacción.
     */
    private static void manejarErrorTransaccion(Connection conexion, SQLException e) throws SQLException {
        if (conexion != null) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                throw new SQLException("Error al hacer rollback: " + ex.getMessage(), ex);
            }
        }
    }

    /**
     * Cierra la conexión de forma segura.
     */
    private static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                if (!conexion.getAutoCommit()) {
                    conexion.setAutoCommit(true);
                }
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    /**
     * Verifica si existen entregas por periodo
     * @param conexion
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws SQLException 
     */
    private static boolean existenEntregasInicialesParaExpediente(Connection conexion, int idExpediente) throws SQLException {
        String sql = "SELECT 1 FROM entrega_documento WHERE id_expediente = ? AND tipo_entrega = 'inicial' LIMIT 1";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idExpediente);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    /**
     * Verifica si ya hay entrega
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