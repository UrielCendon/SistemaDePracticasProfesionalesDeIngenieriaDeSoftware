package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import sistemapracticasis.modelo.dao.DocumentoDAO;
import sistemapracticasis.modelo.dao.EstudianteDAO;
import sistemapracticasis.modelo.dao.ExpedienteDAO;
import sistemapracticasis.modelo.dao.ReporteDAO;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.Profesor;
import sistemapracticasis.modelo.pojo.EntregaVisual;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * Autor: Uriel Cendón
 * Fecha de creación: 15/06/2025
 * Descripción: Controlador de la vista FXMLValidarEntregaController,
 * que permite al profesor buscar a un estudiante y seleccionar una de sus 
 * entregas para validarla según sea el caso.
 */
public class FXMLValidarEntregaController implements Initializable {

    /* Sección: Declaración de variables
    * Contiene todas las variables de instancia y componentes FXML
    * utilizados en el controlador.
    /** Profesor actualmente en sesión */
    private Profesor profesorSesion;
    
    /** Campo de texto para buscar un estudiante*/
    @FXML private TextField txtBuscar;

    /** Tabla que muestra el listado de entregas visuales */
    @FXML private TableView<EntregaVisual> tblEntregas;

    /** Columna que muestra el nombre del documento en la tabla */
    @FXML private TableColumn<EntregaVisual, String> tbcNombreDocumento;

    /** Columna que muestra la fecha de entrega del documento */
    @FXML private TableColumn<EntregaVisual, String> tbcFechaEntregadoDoc;

    /** Etiqueta que muestra el nombre del usuario en sesión */
    @FXML private Label lblNombreUsuario;

    /** Botón para mostrar las opciones de validar y agregar una observación*/
    @FXML private Button btnOpciones;

    /* Sección: Inicialización del controlador
    * Contiene los métodos relacionados con la configuración inicial
    * del controlador y carga de información básica.
    */
    /**
     * Inicializa el controlador después de que su elemento raíz haya sido 
     * procesado.
     * Configura las columnas de la tabla y los listeners de selección.
     * @param url Ubicación utilizada para resolver rutas relativas para el 
     * objeto raíz.
     * @param rb Recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcNombreDocumento.setCellValueFactory(new PropertyValueFactory<>
            ("nombreEntrega"));
        tbcFechaEntregadoDoc.setCellValueFactory(new PropertyValueFactory<>
            ("fechaEntregado"));
        btnOpciones.setDisable(true);
        
        agregarListenersSeleccion();
    }
    
    /**
     * Inicializa la información del profesor en sesión.
     * @param profesorSesion Objeto Profesor que contiene los datos del usuario 
     * en sesión.
     */
    public void inicializarInformacion(Profesor profesorSesion) {
        this.profesorSesion = profesorSesion;
        cargarInformacionUsuario();
    }

    /* Sección: Manejo de eventos de UI
    * Contiene los métodos que gestionan las interacciones del usuario
    * con los componentes de la interfaz.
    */
    /**
     * Maneja el evento de clic en el botón de opciones.
     * Abre la ventana de visualización de entrega seleccionada.
     * @param event Evento de acción generado por el clic.
     */
    @FXML
    private void clicOpciones(ActionEvent event) {
        EntregaVisual seleccion = tblEntregas.getSelectionModel().
            getSelectedItem();
    
        if (seleccion != null) {
            Navegador.abrirVentanaModalParametrizada(
                Utilidad.getEscenarioComponente(lblNombreUsuario),
                "/sistemapracticasis/vista/FXMLVistaDeLaEntrega.fxml",
                FXMLVistaDeLaEntregaController.class,
                "inicializarDatos",
                seleccion
            );
            
            verificarEstadoBoton(seleccion);
        }
    }

    /**
     * Maneja el evento de clic en el botón de cancelar.
     * Regresa a la vista principal del profesor previa confirmación.
     * @param event Evento de acción generado por el clic.
     */
    @FXML
    private void clicCancelar(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
            "Cancelar",
            "Cancelar",
            "¿Está seguro de que quiere cancelar?")) {
                Navegador.cambiarEscenaParametrizada(
                    Utilidad.getEscenarioComponente(lblNombreUsuario),
                    "/sistemapracticasis/vista/FXMLPrincipalProfesor.fxml",
                    FXMLPrincipalProfesorController.class,
                    "inicializarInformacion",
                    profesorSesion
                );
        }
    }

    /**
     * Maneja el evento de clic en el botón de buscar.
     * Busca un estudiante por matrícula y muestra sus entregas.
     * @param event Evento de acción generado por el clic.
     */
    @FXML
    private void clicBuscar(ActionEvent event) {
        String textoBusqueda = txtBuscar.getText().trim();

        if (!validarCampos(textoBusqueda)) return;

        Estudiante estudianteEncontrado = obtenerEstudiante(profesorSesion.getIdProfesor(), 
            textoBusqueda);

        if (estudianteEncontrado != null) {
            boolean estaEnPeriodo = EstudianteDAO.estaEnPeriodoActual
                (textoBusqueda);

            if (estaEnPeriodo) {
                llenarCamposEstudiante(estudianteEncontrado);
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    "Estudiante fuera de periodo",
                    "El estudiante no pertenece al periodo actual. "
                    + "Por favor, intente con otro.");
                tblEntregas.getItems().clear();
            }
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                "No encontrado", "No se encontró ningún estudiante con la "
                + "matrícula ingresada. Verifique que sea correcta o que pertenezca"
                + " a su experiencia educativa.");
        }
    }
    
    /* Sección: Lógica de negocio
    * Contiene los métodos que implementan la funcionalidad principal
    * de la aplicación.
    */
    private void cargarInformacionUsuario() {
        if (profesorSesion != null) {
            lblNombreUsuario.setText(
                profesorSesion.toString()
            );
        }
    }
    
    private boolean validarCampos(String matricula) {
        if (matricula.isEmpty()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                "Campo vacío", "Ingrese una matrícula para buscar al "
                + "estudiante.");
            return false;
        }

        if (!matricula.matches("S\\d{8}")) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                "Matrícula inválida", "La matrícula debe iniciar con 'S' "
                    + "seguido de 8 dígitos (Ej. S12345678).");
            return false;
        }

        return true;
    }
    
    private void llenarCamposEstudiante(Estudiante estudiante) {
        int idExpediente = ExpedienteDAO.obtenerIdExpedientePorEstudiante
            (estudiante.getIdEstudiante());

        if (idExpediente > 0) {
            List<EntregaVisual> entregas = new ArrayList<>();
            entregas.addAll(DocumentoDAO.obtenerDocumentosPorIdExpediente
                (idExpediente));
            entregas.addAll(ReporteDAO.obtenerReportesPorIdExpediente
                (idExpediente));

            tblEntregas.getItems().setAll(entregas);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                "Sin expediente", "El estudiante no tiene expediente "
                    + "asignado.");
        }
    }
    
    private Estudiante obtenerEstudiante(int idProfesor, String matricula) {
        Estudiante estudiante = new Estudiante();
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        boolean encontrado = estudianteDAO.buscarMatriculaPorIdProfesor
            (idProfesor, matricula, estudiante);
        return (encontrado) ? estudiante : null;
    }
    
    /* Sección: Configuración de UI
    * Contiene los métodos para configurar el comportamiento
    * de los componentes de la interfaz.
    */
    private void agregarListenersSeleccion() {
        tblEntregas.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> {
                if (newVal != null) {
                    boolean tieneCalificacion = false;

                    if (newVal.getTipo().equals("documento")) {
                        Double calificacion = DocumentoDAO.
                            obtenerCalificacionPorId(newVal.getId());
                        tieneCalificacion = (calificacion != 0.0);
                    } else if (newVal.getTipo().equals("reporte")) {
                        Double calificacion = ReporteDAO.
                            obtenerCalificacionPorId(newVal.getId());
                        tieneCalificacion = (calificacion != 0.0);
                    }

                    btnOpciones.setDisable(tieneCalificacion);
                } else {
                    btnOpciones.setDisable(true);
                }
            }
        );
    }
    
    private void verificarEstadoBoton(EntregaVisual entrega) {
        boolean tieneCalificacion = false;

        if (entrega.getTipo().equals("documento")) {
            Double calificacion = DocumentoDAO.obtenerCalificacionPorId
                (entrega.getId());
            tieneCalificacion = (calificacion != null);
        } else if (entrega.getTipo().equals("reporte")) {
            Double calificacion = ReporteDAO.obtenerCalificacionPorId
                (entrega.getId());
            tieneCalificacion = (calificacion != null);
        }

        btnOpciones.setDisable(tieneCalificacion);
    }
}
