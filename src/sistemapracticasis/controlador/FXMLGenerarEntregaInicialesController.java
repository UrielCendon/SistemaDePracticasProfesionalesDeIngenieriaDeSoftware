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

    private int idPeriodoActual;
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

        colNombreEntrega.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombreEntrega.setCellFactory(TextFieldTableCell.forTableColumn());
        colNombreEntrega.setOnEditCommit(
            event -> event.getRowValue().setNombre(event.getNewValue())
        );

        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colDescripcion.setCellFactory(TextFieldTableCell.forTableColumn());
        colDescripcion.setOnEditCommit(
            event -> event.getRowValue().setDescripcion(event.getNewValue())
        );

        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colFechaInicio.setCellFactory(TextFieldTableCell.forTableColumn());
        colFechaInicio.setOnEditCommit(
            event -> event.getRowValue().setFechaInicio(event.getNewValue())
        );

        colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        colFechaFin.setCellFactory(TextFieldTableCell.forTableColumn());
        colFechaFin.setOnEditCommit(
            event -> event.getRowValue().setFechaFin(event.getNewValue())
        );

        colCalificacion.setCellValueFactory(new PropertyValueFactory<>("calificacion"));
        colCalificacion.setCellFactory(TextFieldTableCell.<EntregaDocumento, Double>forTableColumn(new javafx.util.converter.DoubleStringConverter()));
        colCalificacion.setOnEditCommit(
            event -> event.getRowValue().setCalificacion(event.getNewValue())
        );
    }
    
    public void inicializarInformacion(String fechaInicio, String fechaFin, Integer idExpediente) {
        this.idPeriodoActual = idExpediente; // ← nombre correcto
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
            // Validación ANTES de guardar
            if (EntregaDocumentoDAO.existenEntregasInicialesParaPeriodo(idPeriodoActual)) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Ya existen entregas",
                        "No se puede generar porque ya existen entregas iniciales para este periodo.");
                return;
            }

            EntregaDocumentoDAO.guardarEntregasIniciales(new ArrayList<>(tvEntregasIniciales.getItems()), idPeriodoActual);
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Operación exitosa.");
            Navegador.cerrarVentana(btnGenerar);
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        Navegador.cerrarVentana(btnCancelar);
    }
}