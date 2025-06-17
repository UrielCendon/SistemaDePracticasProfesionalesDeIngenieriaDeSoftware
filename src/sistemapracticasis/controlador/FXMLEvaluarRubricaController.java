package sistemapracticasis.controlador;

import java.net.URL;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableRow;

import sistemapracticasis.modelo.dao.EvaluacionEstudianteDAO;
import sistemapracticasis.modelo.dao.ObservacionDAO;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.EvaluacionEstudiante;
import sistemapracticasis.util.EvaluacionUtils;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/** 
 * Autor: Uriel Cendón
 * Fecha de creación: 15/06/2025
 * Descripción: Controlador de la vista FXMLEvaluarRubricaController,
 * que permite agregar las calificaciones y observaciones a las presentaciones
 * se los estudiantes.
 */
public class FXMLEvaluarRubricaController implements Initializable {

    /* Sección: Componentes de la interfaz
     * Contiene los elementos FXML que componen la interfaz gráfica.
     */
    @FXML private Label lblNombreEstudiante;
    @FXML private Label lblExperienciaEducativa;
    @FXML private Label lblProyecto;
    @FXML private TableView<Map.Entry<String, String>> tvRubrica;
    @FXML private TableColumn<Map.Entry<String, String>, String> colCriterioEvaluacion;
    @FXML private TableColumn<Map.Entry<String, String>, String> colExcelente;
    @FXML private TableColumn<Map.Entry<String, String>, String> colMuyBien;
    @FXML private TableColumn<Map.Entry<String, String>, String> colBien;
    @FXML private TableColumn<Map.Entry<String, String>, String> colRegular;
    @FXML private TableColumn<Map.Entry<String, String>, String> colInsuficiente;
    @FXML private TableView<Map.Entry<String, Integer>> tvCalificacion;
    @FXML private TableColumn<Map.Entry<String, Integer>, String> colCriterio;
    @FXML private TableColumn<Map.Entry<String, Integer>, Integer> colCalificacion;
    @FXML private TextArea txtObservacion;
    @FXML private Button btnAceptar;
    @FXML private Button btnCancelar;
    @FXML private Button btnAgregarObservacion;

    /* Sección: Variables de instancia
     * Almacena los datos del estudiante y las calificaciones asignadas.
     */
    private Estudiante estudiante;
    private final Map<String, Integer> calificaciones = new LinkedHashMap<>();

    /* Sección: Constantes
     * Define los criterios de evaluación utilizados en la rúbrica.
     */
    private final String[] criterios = {
        "Uso de métodos y técnicas de la IS",
        "Requisitos",
        "Seguridad y dominio",
        "Contenido",
        "Ortografía y redacción"
    };

    /**
     * Inicializa el controlador después de que su elemento raíz haya sido procesado.
     * @param url Ubicación utilizada para resolver rutas relativas para el objeto raíz.
     * @param rb Recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablas();
    }

    /**
     * Inicializa los datos del estudiante que será evaluado.
     * @param estudiante El estudiante que será evaluado.
     */
    public void inicializarEvaluacion(Estudiante estudiante) {
        this.estudiante = estudiante;
        lblNombreEstudiante.setText(estudiante.toString());
        lblProyecto.setText(estudiante.getNombreProyecto());
        cargarRubrica();
    }

    /* Sección: Configuración de tablas
     * Configura las propiedades y celdas de las tablas de la interfaz.
     */
    private void configurarTablas() {
        colCriterioEvaluacion.setCellValueFactory(data -> 
                new SimpleStringProperty(data.getValue().getKey()));

        colExcelente.setCellFactory(col -> crearCeldaEvaluacion("Excelente"));
        colMuyBien.setCellFactory(col -> crearCeldaEvaluacion("Muy bien"));
        colBien.setCellFactory(col -> crearCeldaEvaluacion("Bien"));
        colRegular.setCellFactory(col -> crearCeldaEvaluacion("Regular"));
        colInsuficiente.setCellFactory(col -> crearCeldaEvaluacion("Insuficiente"));

        colCriterio.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));
        colCalificacion.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getValue()).asObject());
    }

    /**
     * Crea una celda de tabla con un botón para asignar calificaciones.
     * @param nivel El nivel de calificación que representa el botón.
     * @return TableCell configurada con el botón correspondiente.
     */
    private TableCell<Map.Entry<String, String>, String> crearCeldaEvaluacion(String nivel) {
        return new TableCell<Map.Entry<String, String>, String>() {
            private final Button btn = new Button(nivel);

            {
                btn.setOnAction(e -> {
                    TableRow<Map.Entry<String, String>> row = getTableRow();
                    if (row != null && row.getItem() != null) {
                        String criterio = row.getItem().getKey();
                        int calif;

                        if (nivel.equals("Regular")) {
                            List<Integer> opciones = Arrays.asList(7, 6);
                            ChoiceDialog<Integer> dialogo = new ChoiceDialog<>(7, opciones);
                            dialogo.setTitle("Seleccionar calificación");
                            dialogo.setHeaderText("Calificación para \"" + criterio + "\"");
                            dialogo.setContentText("Selecciona la calificación:");

                            Optional<Integer> resultado = dialogo.showAndWait();
                            if (resultado.isPresent()) {
                                calif = resultado.get();
                            } else {
                                return; 
                            }
                        } else {
                            calif = EvaluacionUtils.convertirTextoACalificacion(nivel);
                        }

                        calificaciones.put(criterio, calif);
                        actualizarTablaCalificacion();
                        tvCalificacion.refresh();
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        };
    }

    /* Sección: Carga de datos
     * Métodos para cargar y actualizar datos en la interfaz.
     */
    private void cargarRubrica() {
        ObservableList<Map.Entry<String, String>> filas = FXCollections.observableArrayList();
        for (String criterio : criterios) {
            filas.add(new AbstractMap.SimpleEntry<>(criterio, ""));
        }
        tvRubrica.setItems(filas);
    }

    /**
     * Actualiza la tabla de calificaciones con los valores actuales.
     */
    private void actualizarTablaCalificacion() {
        tvCalificacion.setItems(FXCollections.observableArrayList(calificaciones.entrySet()));
    }

    /* Sección: Manejo de eventos
     * Métodos para manejar las acciones del usuario.
     */
    /**
     * Maneja el evento de clic en el botón Aceptar.
     * @param event El evento de acción generado.
     */
    @FXML
    private void clicAceptar(ActionEvent event) {
        if (txtObservacion.getText().trim().isEmpty()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Campo requerido", "La observación no puede quedar vacía");
            return;
        }

        if (calificaciones.size() < criterios.length) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Faltan criterios", "Debes calificar todos los criterios.");
            return;
        }

        int idObs = ObservacionDAO.insertarObservacion(txtObservacion.getText().trim());
        if (idObs == -1) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo guardar la observación.");
            return;
        }

        EvaluacionEstudiante eval = new EvaluacionEstudiante();
        eval.setIdExpediente(estudiante.getIdPeriodo());
        eval.setIdObservacion(idObs);
        eval.setUsoTecnicas(calificaciones.get(criterios[0]));
        eval.setRequisitos(calificaciones.get(criterios[1]));
        eval.setSeguridad(calificaciones.get(criterios[2]));
        eval.setContenido(calificaciones.get(criterios[3]));
        eval.setOrtografia(calificaciones.get(criterios[4]));

        double total = calificaciones.values().stream().mapToDouble(i -> i).sum();
        eval.setPuntajeTotal(total);

        if (EvaluacionEstudianteDAO.guardarEvaluacion(eval)) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Operación exitosa.");
            Navegador.cerrarVentana(btnAceptar);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo guardar la evaluación.");
        }
    }

    /**
     * Maneja el evento de clic en el botón Cancelar.
     * @param event El evento de acción generado.
     */
    @FXML
    private void clicCancelar(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion("Cancelar", "¿Está seguro de que deseas cancelar?", "Los cambios no se guardarán.")) {
            Navegador.cerrarVentana(btnCancelar);
        }
    }

    /**
     * Maneja el evento de clic en el botón Agregar Observación.
     * @param event El evento de acción generado.
     */
    @FXML
    private void clicAgregarObservacion(ActionEvent event) {
        txtObservacion.requestFocus();
    }
}