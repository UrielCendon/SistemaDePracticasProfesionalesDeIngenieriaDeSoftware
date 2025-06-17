package sistemapracticasis.controlador;

// Java estándar
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

// JavaFX
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

// Proyecto propio
import sistemapracticasis.modelo.dao.EvaluacionEstudianteDAO;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.Evaluador;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/** 
 * Autor: Uriel Cendon
 * Fecha de creación: 15/06/2025
 * Descripción: Controlador para la vista principal del evaluador,
 * permite evaluar estudiantes mediante una rúbrica y gestionar
 * las evaluaciones pendientes.
 */
public class FXMLPrincipalEvaluadorController implements Initializable {

    /* Sección: Componentes de la interfaz
     * Contiene los elementos FXML de la vista principal del evaluador.
     */

    /** Etiqueta que muestra el nombre del evaluador en sesión. */
    @FXML private Label lblNombreUsuario;

    /** Botón para cerrar la sesión del evaluador. */
    @FXML private Button btnCerrarSesion;

    /** Botón para confirmar la selección y continuar con la evaluación. */
    @FXML private Button btnAceptar;

    /** Tabla que lista a los estudiantes pendientes de evaluación. */
    @FXML private TableView<Estudiante> tvEvaluarEstudiante;

    /** Columna que muestra la matrícula del estudiante. */
    @FXML private TableColumn<Estudiante, String> colMatricula;

    /** Columna que muestra el nombre completo del estudiante. */
    @FXML private TableColumn<Estudiante, String> colEstudiante;

    /** Columna que muestra el proyecto asignado al estudiante. */
    @FXML private TableColumn<Estudiante, String> colProyectoAsignado;

    /** Columna con el botón para seleccionar al estudiante a evaluar. */
    @FXML private TableColumn<Estudiante, Void> colSeleccionEstudiante;

    /* Sección: Variables de instancia
     * Almacena los datos del evaluador en sesión y estudiantes a evaluar.
     */

    /** Evaluador actualmente autenticado en la aplicación. */
    private Evaluador evaluadorSesion;

    /** Lista observable de estudiantes que serán evaluados. */
    private ObservableList<Estudiante> estudiantes;

    /** Estudiante seleccionado actualmente para su evaluación. */
    private Estudiante seleccionado;


    /**
     * Inicializa el controlador después de que su elemento raíz haya sido procesado.
     * @param url Ubicación utilizada para resolver rutas relativas.
     * @param rb Recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }

    /* Sección: Métodos públicos */
    /**
     * Inicializa la información del evaluador en sesión.
     * @param evaluadorSesion Objeto Evaluador con los datos de sesión.
     */
    public void inicializarInformacion(Evaluador evaluadorSesion) {
        this.evaluadorSesion = evaluadorSesion;
        lblNombreUsuario.setText(evaluadorSesion.toString());
        cargarEstudiantes();
    }

    /* Sección: Configuración de tabla */
    /**
     * Configura las columnas y comportamiento de la tabla de estudiantes.
     */
    private void configurarTabla() {
        configurarColumnasTexto();
        configurarColumnaSeleccion();
    }

    private void configurarColumnasTexto() {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colEstudiante.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().toString()));
        colProyectoAsignado.setCellValueFactory(new PropertyValueFactory<>("nombreProyecto"));
    }

    private void configurarColumnaSeleccion() {
        colSeleccionEstudiante.setCellFactory(new Callback<TableColumn<Estudiante, Void>, 
                TableCell<Estudiante, Void>>() {
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

    /* Sección: Carga de datos */
    /**
     * Carga la lista de estudiantes pendientes de evaluación.
     */
    private void cargarEstudiantes() {
        ArrayList<Estudiante> lista = EvaluacionEstudianteDAO.obtenerEstudiantesNoEvaluados();
        estudiantes = FXCollections.observableArrayList(lista);
        tvEvaluarEstudiante.setItems(estudiantes);
    }

    /* Sección: Manejo de eventos */
    /**
     * Maneja el evento para evaluar al estudiante seleccionado.
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicAceptar(ActionEvent event) {
        if (seleccionado == null) {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.WARNING, 
                "Sin selección", 
                "Seleccione un estudiante para evaluar."
            );
            return;
        }

        abrirVentanaEvaluacion();
    }

    /**
     * Abre la ventana modal para evaluar al estudiante seleccionado.
     */
    private void abrirVentanaEvaluacion() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/sistemapracticasis/vista/FXMLEvaluarRubrica.fxml")
            );
            Parent root = loader.load();

            FXMLEvaluarRubricaController controller = loader.getController();
            controller.inicializarEvaluacion(seleccionado);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(Utilidad.getEscenarioComponente(btnAceptar));
            stage.setScene(new Scene(root));

            // Refrescar la tabla cuando se cierre la ventana
            stage.setOnHidden(e -> {
                cargarEstudiantes();
                seleccionado = null;
            });

            stage.showAndWait();

        } catch (IOException ex) {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.ERROR, 
                "Error", 
                "No se pudo abrir la ventana de evaluación."
            );
            ex.printStackTrace();
        }
    }

    /**
     * Maneja el evento para cerrar la sesión actual.
     * @param event Evento de acción generado.
     */
    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
            "Cerrar sesión", 
            "¿Seguro que desea cerrar sesión?", 
            null
        )) {
            Navegador.cerrarSesion(Utilidad.getEscenarioComponente(lblNombreUsuario));
        }
    }
}