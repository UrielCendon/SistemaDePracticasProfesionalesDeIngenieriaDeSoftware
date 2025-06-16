/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import sistemapracticasis.modelo.dao.EntregaDocumentoDAO;
import sistemapracticasis.modelo.pojo.EntregaDocumento;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * FXML Controller class
 *
 * @author Kaiser
 */
public class FXMLGenerarEntregaInicialesController implements Initializable {

    @FXML
    private TableView<EntregaDocumento> tvEntregasIniciales;
    @FXML
    private TableColumn<EntregaDocumento, String> colNombreEntrega;
    @FXML
    private TableColumn<EntregaDocumento, String> colDescripcion;
    @FXML
    private TableColumn<EntregaDocumento, String> colFechaInicio;
    @FXML
    private TableColumn<EntregaDocumento, String> colFechaFin;
    @FXML
    private TableColumn<EntregaDocumento, Double> colCalificacion;
    @FXML
    private Button btnGenerar;
    @FXML
    private Button btnCancelar;

    private int idExpediente;
    private ObservableList<EntregaDocumento> entregas;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        tvEntregasIniciales.setEditable(true);
        colFechaInicio.setEditable(true);
        colFechaFin.setEditable(true);
        colCalificacion.setEditable(true);
    }
    
    private void configurarColumnas() {
        tvEntregasIniciales.setEditable(true);

        // Columna Nombre (editable con validación)
        colNombreEntrega.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombreEntrega.setCellFactory(TextFieldTableCell.forTableColumn());
        colNombreEntrega.setOnEditCommit(event -> {
            if (event.getNewValue() == null || event.getNewValue().trim().isEmpty()) {
                Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.WARNING,
                    "Campo vacío",
                    "El nombre no puede estar vacío"
                );
                tvEntregasIniciales.refresh();
            } else {
                event.getRowValue().setNombre(event.getNewValue());
            }
        });

        // Columna Descripción (editable con validación)
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colDescripcion.setCellFactory(TextFieldTableCell.forTableColumn());
        colDescripcion.setOnEditCommit(event -> {
            if (event.getNewValue() == null || event.getNewValue().trim().isEmpty()) {
                Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.WARNING,
                    "Campo vacío",
                    "La descripción no puede estar vacía"
                );
                tvEntregasIniciales.refresh();
            } else {
                event.getRowValue().setDescripcion(event.getNewValue());
            }
        });

        // Columna Fecha Inicio (NO editable)
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        
        // Columna Fecha Fin (NO editable)
        colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        
        // Columna Calificación con validación (editable)
        colCalificacion.setCellValueFactory(new PropertyValueFactory<>("calificacion"));
        colCalificacion.setCellFactory(TextFieldTableCell.<EntregaDocumento, Double>forTableColumn(new javafx.util.converter.DoubleStringConverter()));
        colCalificacion.setOnEditCommit(event -> {
            try {
                Double nuevaCalificacion = event.getNewValue();
                if (nuevaCalificacion == null) {
                    throw new IllegalArgumentException("La calificación no puede estar vacía");
                }
                
                if (nuevaCalificacion < 0.0 || nuevaCalificacion > 10.0) {
                    Utilidad.mostrarAlertaSimple(
                        Alert.AlertType.WARNING, 
                        "Calificación inválida", 
                        "La calificación debe estar entre 0.0 y 10.0"
                    );
                    tvEntregasIniciales.refresh();
                } else {
                    event.getRowValue().setCalificacion(nuevaCalificacion);
                }
            } catch (Exception e) {
                Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.WARNING, 
                    "Valor inválido", 
                    e.getMessage() != null ? e.getMessage() : "Ingrese un valor numérico entre 0.0 y 10.0"
                );
                tvEntregasIniciales.refresh();
            }
        });
    }
    
    public void inicializarInformacion(String fechaInicio, String fechaFin, Integer idExpediente) {
        this.idExpediente = idExpediente; // ← nombre correcto
        ArrayList<EntregaDocumento> entregasList =
            EntregaDocumentoDAO.generarEntregasInicialesPorDefecto(fechaInicio, fechaFin);
        this.entregas = FXCollections.observableArrayList(entregasList);
        tvEntregasIniciales.setItems(this.entregas);
    }

    @FXML
    private void clicGenerar(ActionEvent event) {
        boolean confirmar = Utilidad.mostrarConfirmacion(
            "Confirmación",
            "¿Está seguro de generar las entregas?",
            "Estas entregas se asignarán a todos los expedientes del periodo actual."
        );

        if (confirmar) {
            try {
                // Validación completa antes de guardar
                if (!validarDatosCompletos()) {
                    return;
                }

                if (EntregaDocumentoDAO.existenEntregasInicialesParaPeriodo(idExpediente)) {
                    Utilidad.mostrarAlertaSimple(
                        Alert.AlertType.WARNING, 
                        "Ya existen entregas",
                        "No se puede generar porque ya existen entregas iniciales para este periodo."
                    );
                    return;
                }

                EntregaDocumentoDAO.guardarEntregasIniciales(
                    new ArrayList<>(tvEntregasIniciales.getItems()), 
                    idExpediente
                );
                Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.INFORMATION, 
                    "Éxito", 
                    "Operación exitosa."
                );
                Navegador.cerrarVentana(btnGenerar);
            } catch (RuntimeException e) {
                // El DAO ya mostró el mensaje de error
            }
        }
    }
    
    private boolean validarDatosCompletos() {
        for (EntregaDocumento entrega : entregas) {
            // 1. Validar nombre (no vacío)
            if (entrega.getNombre() == null || entrega.getNombre().trim().isEmpty()) {
                Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.WARNING,
                    "Campo requerido",
                    "El nombre no puede estar vacío"
                );
                return false;
            }

            // 2. Validar descripción (no vacía)
            if (entrega.getDescripcion() == null || entrega.getDescripcion().trim().isEmpty()) {
                Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.WARNING,
                    "Campo requerido",
                    "La descripción no puede estar vacía"
                );
                return false;
            }

            // 3. Validar calificación (rango 0.0 a 10.0)
            // Nota: Usamos Double.isNaN() para evitar el error con '== null' en primitivos
            if (Double.isNaN(entrega.getCalificacion()) || entrega.getCalificacion() < 0.0 || entrega.getCalificacion() > 10.0) {
                Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.WARNING,
                    "Calificación inválida",
                    "La calificación debe estar entre 0.0 y 10.0"
                );
                return false;
            }
        }
        return true; // Si pasa todas las validaciones
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        Navegador.cerrarVentana(btnCancelar);
    }
}