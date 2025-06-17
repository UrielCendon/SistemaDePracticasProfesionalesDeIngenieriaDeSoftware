package sistemapracticasis.controlador;

import java.net.URL;
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
    @FXML private TableView<EntregaDocumento> tvEntregasIniciales;
    @FXML private TableColumn<EntregaDocumento, String> colNombreEntrega;
    @FXML private TableColumn<EntregaDocumento, String> colDescripcion;
    @FXML private TableColumn<EntregaDocumento, String> colFechaInicio;
    @FXML private TableColumn<EntregaDocumento, String> colFechaFin;
    @FXML private TableColumn<EntregaDocumento, Double> colCalificacion;
    @FXML private Button btnGenerar;
    @FXML private Button btnCancelar;

    /* Sección: Variables de instancia
     * Almacena los datos de las entregas y las fechas del periodo.
     */
    private ObservableList<EntregaDocumento> entregas;
    private String fechaInicio;
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
        configurarColumnaDescripcion();
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
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Campo vacío", "El nombre no puede estar vacío");
                tvEntregasIniciales.refresh();
            } else {
                event.getRowValue().setNombre(nuevo);
            }
        });
    }

    /**
     * Configura la columna de descripción con validación de campo vacío.
     */
    private void configurarColumnaDescripcion() {
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colDescripcion.setCellFactory(TextFieldTableCell.forTableColumn());
        colDescripcion.setOnEditCommit(event -> {
            String nueva = event.getNewValue();
            if (nueva == null || nueva.trim().isEmpty()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Campo vacío", "La descripción no puede estar vacía");
                tvEntregasIniciales.refresh();
            } else {
                event.getRowValue().setDescripcion(nueva);
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
        colCalificacion.setCellFactory(TextFieldTableCell.<EntregaDocumento, Double>forTableColumn(new DoubleStringConverter()));
        colCalificacion.setOnEditCommit(event -> {
            try {
                Double cal = event.getNewValue();
                if (cal == null || cal < 0.0 || cal > 10.0) {
                    throw new IllegalArgumentException("Debe estar entre 0.0 y 10.0");
                }
                event.getRowValue().setCalificacion(cal);
            } catch (Exception e) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Valor inválido", e.getMessage());
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
    public void inicializarInformacion(String fechaInicio, String fechaFin, Integer idExpedienteIgnorado) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;

        ArrayList<EntregaDocumento> lista = EntregaDocumentoDAO.generarEntregasInicialesPorDefecto(fechaInicio, fechaFin);
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
            if (EntregaDocumentoDAO.existenEntregasInicialesParaPeriodo(fechaInicio, fechaFin)) {
                Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.WARNING,
                    "Ya existen entregas",
                    "Ya hay entregas iniciales generadas para este periodo."
                );
                return;
            }

            EntregaDocumentoDAO.guardarEntregasIniciales(new ArrayList<>(entregas), fechaInicio, fechaFin);
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Entregas generadas correctamente.");
            Navegador.cerrarVentana(btnGenerar);

        } catch (RuntimeException e) {
            // Ya se muestra error desde el DAO
        }
    }

    /**
     * Valida que todos los datos de las entregas sean correctos.
     * @return true si todos los datos son válidos, false en caso contrario.
     */
    private boolean validarDatos() {
        for (EntregaDocumento entrega : entregas) {
            if (entrega.getNombre() == null || entrega.getNombre().trim().isEmpty()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Campo requerido", "El nombre no puede estar vacío.");
                return false;
            }
            if (entrega.getDescripcion() == null || entrega.getDescripcion().trim().isEmpty()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Campo requerido", "La descripción no puede estar vacía.");
                return false;
            }
            if (entrega.getCalificacion() < 0.0 || entrega.getCalificacion() > 10.0) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Calificación inválida", "Debe estar entre 0.0 y 10.0.");
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