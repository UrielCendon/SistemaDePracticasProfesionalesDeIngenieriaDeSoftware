package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
 * Descripción: Controlador de la vista FXMLValidarEntrega. Permite al profesor
 * buscar y seleccionar a un estudiante con entregas pendientes para
 * poder validarlas.
 */
public class FXMLValidarEntregaController implements Initializable {

    /* Sección: Declaración de variables
    * Contiene todas las variables de instancia y componentes FXML
    * utilizados en el controlador.
    */
    /** Profesor actualmente en sesión */
    private Profesor profesorSesion;
    
    /** Campo de texto para buscar un estudiante*/
    @FXML private TextField txtBuscar;
    
    /** Campo de texto que muestra el proyecto de un estudiante*/
    @FXML private TextField txtProyecto;

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
    
    /** Lista desplegable con los estudiantes disponibles. */
    @FXML private ComboBox<Estudiante> cbEstudiantes;
    
    /** Lista observable de estudiantes disponibles. */
    private ObservableList<Estudiante> listaEstudiantes;

    /* Sección: Inicialización del controlador
    * Contiene los métodos relacionados con la configuración inicial
    * del controlador y carga de información básica.
    */
    /**
     * Inicializa el controlador después de que su elemento raíz haya sido 
     * procesado. Configura las columnas de la tabla y los listeners para una
     * interfaz reactiva.
     * @param url Ubicación utilizada para resolver rutas relativas para el 
     * objeto raíz.
     * @param rb Recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcNombreDocumento.setCellValueFactory(new PropertyValueFactory<>("nombreEntrega"));
        tbcFechaEntregadoDoc.setCellValueFactory(new PropertyValueFactory<>("fechaEntregado"));
        btnOpciones.setDisable(true);
        
        configurarListeners();
    }
    
    /**
     * Inicializa el controlador con los datos del profesor en sesión.
     * Desencadena la carga de la información del usuario y la lista de 
     * estudiantes con entregas pendientes.
     * @param profesorSesion Objeto Profesor que contiene los datos del usuario 
     * en sesión.
     */
    public void inicializarInformacion(Profesor profesorSesion) {
        this.profesorSesion = profesorSesion;
        cargarInformacionUsuario();
        cargarEstudiantesConEntregasPendientes();
    }

    /* Sección: Manejo de eventos de UI
    * Contiene los métodos que gestionan las interacciones del usuario
    * con los componentes de la interfaz.
    */
    /**
     * Maneja el evento de clic en el botón de opciones.
     * Abre la ventana modal para visualizar la entrega seleccionada.
     * @param event Evento de acción generado por el clic.
     */
    @FXML
    private void clicOpciones(ActionEvent event) {
        EntregaVisual seleccion = tblEntregas.getSelectionModel().getSelectedItem();
        int indiceSeleccionado = tblEntregas.getSelectionModel().getSelectedIndex();
    
        if (seleccion != null) {
            Runnable accionPostValidacion = () -> {
                seleccion.setValidado(true);
                tblEntregas.getItems().set(indiceSeleccionado, seleccion);
                tblEntregas.getSelectionModel().select(indiceSeleccionado);
            };

            Navegador.abrirVentanaModalParametrizada(
                Utilidad.getEscenarioComponente(lblNombreUsuario),
                "/sistemapracticasis/vista/FXMLVistaDeLaEntrega.fxml",
                FXMLVistaDeLaEntregaController.class,
                "inicializarDatos",
                seleccion,
                accionPostValidacion
            );
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
            "Cancelar", "Cancelar", "¿Está seguro de que quiere cancelar?")) {
                Navegador.cambiarEscenaParametrizada(
                    Utilidad.getEscenarioComponente(lblNombreUsuario),
                    "/sistemapracticasis/vista/FXMLPrincipalProfesor.fxml",
                    FXMLPrincipalProfesorController.class,
                    "inicializarInformacion",
                    profesorSesion
                );
        }
    }
    
    /* Sección: Lógica de la Interfaz y Negocio
    * Contiene los métodos que implementan la funcionalidad principal y
    * configuran el comportamiento dinámico de la vista.
    */
    /**
     * Configura todos los listeners necesarios para que la interfaz sea
     * reactiva: la selección en el ComboBox de estudiantes, el filtro de 
     * búsqueda y la selección en la tabla de entregas.
     */
    private void configurarListeners() {
        cbEstudiantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, 
                newSelection) -> {
            if (newSelection != null) {
                manejarSeleccionEstudiante(newSelection);
            } else {
                limpiarDetallesEstudiante();
            }
        });

        txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> filtrarEstudiantes(newVal));

        tblEntregas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, 
                newVal) -> {
            if (newVal != null) {
                btnOpciones.setDisable(newVal.isValidado());
            } else {
                btnOpciones.setDisable(true);
            }
        });
    }
    
    /**
     * Carga la información del profesor en sesión en la etiqueta de la interfaz.
     */
    private void cargarInformacionUsuario() {
        if (profesorSesion != null) {
            lblNombreUsuario.setText(profesorSesion.toString());
        }
    }
    
    /**
     * Obtiene y carga la lista de estudiantes del profesor que tienen entregas
     * sin validar en el ComboBox.
     */
    private void cargarEstudiantesConEntregasPendientes() {
        listaEstudiantes = FXCollections.observableArrayList(
            EstudianteDAO.obtenerEstudiantesDeProfesorConDocumentoSinValidar
                (profesorSesion.getIdProfesor())
        );
        cbEstudiantes.setItems(listaEstudiantes);

        if (listaEstudiantes.isEmpty()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Sin entregas "
                + "pendientes de validar",
                "No tiene estudiantes con entregas pendientes de validación en este momento.");
            txtBuscar.setDisable(true);
            cbEstudiantes.setDisable(true);
        }
    }

    /**
     * Gestiona la lógica a ejecutar cuando un estudiante es seleccionado del 
     * ComboBox. Muestra el proyecto y carga sus entregas en la tabla.
     * @param estudiante El estudiante que fue seleccionado.
     */
    private void manejarSeleccionEstudiante(Estudiante estudiante) {
        txtProyecto.setText(estudiante.getNombreProyecto() != null && !estudiante.
                getNombreProyecto().isEmpty() 
            ? estudiante.getNombreProyecto() 
            : "Sin proyecto asignado");
        
        llenarTablaEntregas(estudiante);
    }

    /**
     * Puebla la tabla de entregas con los documentos y reportes asociados al 
     * expediente de un estudiante específico.
     * @param estudiante El estudiante del cual se mostrarán las entregas.
     */
    private void llenarTablaEntregas(Estudiante estudiante) {
        int idExpediente = ExpedienteDAO.obtenerIdExpedientePorEstudiante
            (estudiante.getIdEstudiante());

        if (idExpediente > 0) {
            List<EntregaVisual> entregas = new ArrayList<>();
            entregas.addAll(DocumentoDAO.obtenerDocumentosPorIdExpediente(idExpediente));
            entregas.addAll(ReporteDAO.obtenerReportesPorIdExpediente(idExpediente));

            tblEntregas.getItems().setAll(entregas);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Sin Expediente",
                "El estudiante seleccionado no tiene un expediente asociado.");
            tblEntregas.getItems().clear();
        }
    }

    /**
     * Limpia los campos de detalle del estudiante (proyecto y tabla de entregas)
     * cuando no hay ninguna selección en el ComboBox.
     */
    private void limpiarDetallesEstudiante() {
        txtProyecto.clear();
        tblEntregas.getItems().clear();
        btnOpciones.setDisable(true);
    }
    
    /**
     * Filtra la lista de estudiantes en el ComboBox según el texto ingresado
     * en el campo de búsqueda. La búsqueda es insensible a mayúsculas.
     * @param filtro El texto utilizado para filtrar la lista.
     */
    private void filtrarEstudiantes(String filtro) {
        ObservableList<Estudiante> estudiantesFiltrados;

        if (filtro == null || filtro.trim().isEmpty()) {
            estudiantesFiltrados = listaEstudiantes;
        } else {
            String textoBusqueda = filtro.toLowerCase().trim();
            estudiantesFiltrados = listaEstudiantes.stream()
                .filter(estudiante -> estudiante.toString().toLowerCase().contains(textoBusqueda))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
        cbEstudiantes.setItems(estudiantesFiltrados);
        
        if (estudiantesFiltrados.isEmpty()) {
            cbEstudiantes.getSelectionModel().clearSelection();
        }
    }
}