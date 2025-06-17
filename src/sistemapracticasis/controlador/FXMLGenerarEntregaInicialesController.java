package sistemapracticasis.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

import sistemapracticasis.modelo.dao.EntregaDocumentoDAO;
import sistemapracticasis.modelo.pojo.EntregaDocumento;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/** 
 * Autor: Raziel Filobello
 * Fecha de creación: 15/06/2025
 * Descripción: Controlador de la vista FXMLGenerarEntregaIniciales,
 * que permite al coordinador generar las entregas iniciales que los
 * estudiantes deberán completar durante el periodo de prácticas.
 */
public class FXMLGenerarEntregaInicialesController implements Initializable {

    /* Sección: Componentes de la interfaz
     * Contiene los elementos FXML que componen la interfaz gráfica.
     */

    /** Tabla que muestra las entregas iniciales del expediente. */
    @FXML private TableView<EntregaDocumento> tvEntregasIniciales;

    /** Columna que muestra el nombre del documento a entregar. */
    @FXML private TableColumn<EntregaDocumento, String> colNombreEntrega;

    /** Columna que muestra la fecha de inicio de la entrega. */
    @FXML private TableColumn<EntregaDocumento, String> colFechaInicio;

    /** Columna que muestra la fecha límite de la entrega. */
    @FXML private TableColumn<EntregaDocumento, String> colFechaFin;

    /** Columna que muestra la calificación obtenida en la entrega. */
    @FXML private TableColumn<EntregaDocumento, Double> colCalificacion;

    /** Botón para generar el archivo consolidado de entregas. */
    @FXML private Button btnGenerar;

    /** Botón para cancelar la operación y cerrar la ventana. */
    @FXML private Button btnCancelar;

    /* Sección: Variables de instancia
     * Almacena los datos de las entregas y las fechas del periodo.
     */

    /** Lista observable de entregas iniciales. */
    private ObservableList<EntregaDocumento> entregas;

    /** Fecha de inicio del periodo académico (formato AAAA-MM-DD). */
    private String fechaInicio;

    /** Fecha de fin del periodo académico (formato AAAA-MM-DD). */
    private String fechaFin;


    /**
     * Inicializa el controlador después de que su elemento raíz haya sido procesado.
     * @param url Ubicación utilizada para resolver rutas relativas para el objeto raíz.
     * @param rb Recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        tvEntregasIniciales.setEditable(true);
    }

    /* Sección: Configuración de tablas
     * Configura las propiedades y celdas de las tablas de la interfaz.
     */
    private void configurarColumnas() {
        configurarColumnaNombre();
        configurarColumnasFechas();
        configurarColumnaCalificacion();
    }

    /**
     * Configura la columna de nombre con validación de campo vacío.
     */
    private void configurarColumnaNombre() {
        colNombreEntrega.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombreEntrega.setCellFactory(TextFieldTableCell.forTableColumn());
        colNombreEntrega.setOnEditCommit(event -> {
            String nuevo = event.getNewValue();
            if (nuevo == null || nuevo.trim().isEmpty()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Campo vacío", 
                        "El nombre no puede estar vacío");
                tvEntregasIniciales.refresh();
            } else {
                event.getRowValue().setNombre(nuevo);
            }
        });
    }

    /**
     * Configura las columnas de fechas (solo lectura).
     */
    private void configurarColumnasFechas() {
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
    }

    /**
     * Configura la columna de calificación con validación de rango (0-10).
     */
    private void configurarColumnaCalificacion() {
        colCalificacion.setCellValueFactory(new PropertyValueFactory<>("calificacion"));
        colCalificacion.setCellFactory(TextFieldTableCell.<EntregaDocumento, 
                Double>forTableColumn(new DoubleStringConverter()));
        colCalificacion.setOnEditCommit(event -> {
            try {
                Double cal = event.getNewValue();
                if (cal == null || cal < 0.0 || cal > 10.0) {
                    throw new IllegalArgumentException("Debe estar entre 0.0 y 10.0");
                }
                event.getRowValue().setCalificacion(cal);
            } catch (Exception e) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                        "Valor inválido", e.getMessage());
                tvEntregasIniciales.refresh();
            }
        });
    }

    /* Sección: Métodos públicos */
    /**
     * Inicializa la información de las entregas iniciales.
     * @param fechaInicio Fecha de inicio del periodo.
     * @param fechaFin Fecha de fin del periodo.
     * @param idExpedienteIgnorado Identificador de expediente a ignorar (no utilizado actualmente).
     */
    public void inicializarInformacion(String fechaInicio, String fechaFin, 
            Integer idExpedienteIgnorado) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;

        ArrayList<EntregaDocumento> lista = EntregaDocumentoDAO.generarEntregasInicialesPorDefecto(
                fechaInicio, fechaFin);
        entregas = FXCollections.observableArrayList(lista);
        tvEntregasIniciales.setItems(entregas);
    }

    /* Sección: Manejo de eventos */
    /**
     * Maneja el evento de clic en el botón Generar.
     * @param event Evento de acción generado al hacer clic.
     */
    @FXML
    private void clicGenerar(ActionEvent event) {
        boolean confirmar = Utilidad.mostrarConfirmacion(
            "Confirmación",
            "¿Está seguro de generar las entregas?",
            "Estas entregas se asignarán a todos los expedientes del periodo actual."
        );

        if (!confirmar || !validarDatos()) return;

        try {
            boolean guardadoExitoso = EntregaDocumentoDAO.guardarEntregasIniciales(
                new ArrayList<>(entregas), fechaInicio, fechaFin);

            if (guardadoExitoso) {
                Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.INFORMATION, 
                    "Éxito", 
                    "Entregas generadas correctamente.");
                Navegador.cerrarVentana(btnGenerar);
            } else {
                Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.WARNING,
                    "Entregas existentes",
                    "Ya existen entregas iniciales para este período. No se generaron nuevas entregas."
                );
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("No se encontraron expedientes")) {
                Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.WARNING, 
                    "No hay expedientes", 
                    e.getMessage());
            } else {
                Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.ERROR,
                    "Error en base de datos",
                    "Ocurrió un error al guardar las entregas: " + e.getMessage()
                );
            }
        } catch (Exception e) {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Error inesperado",
                "Ocurrió un error inesperado: " + e.getMessage()
            );
        }
    }

    /**
     * Valida que todos los datos de las entregas sean correctos.
     * @return true si todos los datos son válidos, false en caso contrario.
     */
    private boolean validarDatos() {
        for (EntregaDocumento entrega : entregas) {
            if (entrega.getNombre() == null || entrega.getNombre().trim().isEmpty()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Campo requerido", 
                        "El nombre no puede estar vacío.");
                return false;
            }
            if (entrega.getCalificacion() < 0.0 || entrega.getCalificacion() > 10.0) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Calificación inválida", 
                        "Debe estar entre 0.0 y 10.0.");
                return false;
            }
        }
        return true;
    }

    /**
     * Maneja el evento de clic en el botón Cancelar.
     * @param event Evento de acción generado al hacer clic.
     */
    @FXML
    private void clicCancelar(ActionEvent event) {
        Navegador.cerrarVentana(btnCancelar);
    }
}