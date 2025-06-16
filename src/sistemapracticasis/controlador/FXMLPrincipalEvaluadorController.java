package sistemapracticasis.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sistemapracticasis.modelo.dao.EvaluacionEstudianteDAO;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.Evaluador;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

public class FXMLPrincipalEvaluadorController implements Initializable {

    private Evaluador evaluadorSesion;
    private ObservableList<Estudiante> estudiantes;
    private Estudiante seleccionado;

    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnAceptar;
    @FXML
    private TableView<Estudiante> tvEvaluarEstudiante;
    @FXML
    private TableColumn<Estudiante, String> colMatricula;
    @FXML
    private TableColumn<Estudiante, String> colEstudiante;
    @FXML
    private TableColumn<Estudiante, String> colProyectoAsignado;
    @FXML
    private TableColumn<Estudiante, Void> colSeleccionEstudiante;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }

    public void inicializarInformacion(Evaluador evaluadorSesion) {
        this.evaluadorSesion = evaluadorSesion;
        lblNombreUsuario.setText(evaluadorSesion.toString());
        cargarEstudiantes();
    }

    private void configurarTabla() {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colEstudiante.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));
        colProyectoAsignado.setCellValueFactory(new PropertyValueFactory<>("nombreProyecto"));

        colSeleccionEstudiante.setCellFactory(new Callback<TableColumn<Estudiante, Void>, TableCell<Estudiante, Void>>() {
            @Override
            public TableCell<Estudiante, Void> call(TableColumn<Estudiante, Void> param) {
                return new TableCell<Estudiante, Void>() {
                    private final RadioButton rb = new RadioButton();
                    private final ToggleGroup toggleGroup = new ToggleGroup();

                    {
                        rb.setToggleGroup(toggleGroup);
                        rb.setOnAction(e -> {
                            TableRow<Estudiante> row = getTableRow();
                            if (row != null && row.getItem() != null) {
                                seleccionado = row.getItem();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                            setGraphic(null);
                        } else {
                            setGraphic(rb);
                            rb.setSelected(getTableRow().getItem().equals(seleccionado));
                        }
                    }
                };
            }
        });
    }

    private void cargarEstudiantes() {
        ArrayList<Estudiante> lista = EvaluacionEstudianteDAO.obtenerEstudiantesNoEvaluados();
        estudiantes = FXCollections.observableArrayList(lista);
        tvEvaluarEstudiante.setItems(estudiantes);
    }

    @FXML
    private void clicAceptar(ActionEvent event) {
        if (seleccionado == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Sin selección", "Seleccione un estudiante para evaluar.");
            return;
        }

        Stage parentStage = Utilidad.getEscenarioComponente(btnAceptar);

        // Cargar FXML manualmente
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemapracticasis/vista/FXMLEvaluarRubrica.fxml"));
            Parent root = loader.load();

            FXMLEvaluarRubricaController controller = loader.getController();
            controller.inicializarEvaluacion(seleccionado);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(parentStage);
            stage.setScene(new Scene(root));

            // 🔁 Refrescar la tabla cuando se cierre la ventana
            stage.setOnHidden(e -> {
                cargarEstudiantes(); // Refresca la lista
                seleccionado = null;
            });

            stage.showAndWait();

        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de evaluación.");
        }
    }

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion("Cerrar sesión", "¿Seguro que desea cerrar sesión?", null)) {
            Navegador.cerrarSesion(Utilidad.getEscenarioComponente(lblNombreUsuario));
        }
    }
}