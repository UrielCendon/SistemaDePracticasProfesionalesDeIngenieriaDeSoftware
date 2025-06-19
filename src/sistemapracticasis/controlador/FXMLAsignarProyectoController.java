package sistemapracticasis.controlador;

import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;

import sistemapracticasis.modelo.dao.EstudianteDAO;
import sistemapracticasis.modelo.dao.ProyectoDAO;
import sistemapracticasis.modelo.dto.ParametrosProyectosDisponibles;
import sistemapracticasis.modelo.pojo.Coordinador;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.Proyecto;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;


/**
 * Autor: Uriel Cendón
 * Fecha de creación: 14/06/2025
 * Descripción: Controlador de la vista FXMLAsignarProyecto,
 * que permite al coordinador asignarle un proyecto al estudiante que decida
 * buscar.
 */
public class FXMLAsignarProyectoController implements Initializable {

    /* Sección: Declaración de variables
     * Contiene todas las variables de instancia y componentes FXML
     * utilizados en el controlador.
     */
    /** Coordinador actualmente en sesión */
    private Coordinador coordinadorSesion;
    
    /** Botón para asignar proyecto al estudiante */
    @FXML private Button btnAsignarProyecto;
    
    /** Botón para cancelar la operación */
    @FXML private Button btnCancelar;
    
    /** Campo de texto para buscar estudiantes por su nombre */
    @FXML private TextField txtBuscar;
    
    /** Campo de texto que muestra la matrícula del estudiante */
    @FXML private TextField txtMatricula;
    
    /** Campo de texto que muestra el nombre del estudiante */
    @FXML private TextField txtNombre;
    
    /** Campo de texto que muestra el correo del estudiante */
    @FXML private TextField txtCorreo;
    
    /** Campo de texto que muestra el teléfono del estudiante */
    @FXML private TextField txtTelefono;
    
    /** Campo de texto que muestra el proyecto asignado */
    @FXML private TextField txtProyecto;
    
    /** Etiqueta que muestra el nombre del coordinador en sesión */
    @FXML private Label lblNombreUsuario;
    
    /** Lista desplegable con los estudiantes disponibles. */
    @FXML private ComboBox<Estudiante> cbEstudiantes;
    
    /** Lista observable de estudiantes disponibles. */
    private ObservableList<Estudiante> listaEstudiantes;

    /**
     * Inicializa el controlador después de que su raíz ha sido procesada.
     * Configura el estado inicial de los componentes.
     * 
     * @param url Ubicación relativa del objeto raíz
     * @param rb Recursos localizados para este objeto
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEstudiantesDelPeriodoActual();

        if (listaEstudiantes.isEmpty()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Sin Estudiantes", 
                "No hay estudiantes registrados en el periodo actual. No se puede continuar.");
            txtBuscar.setDisable(true);
            cbEstudiantes.setDisable(true);
        } else {
            txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> 
                filtrarEstudiantes(newVal));

            cbEstudiantes.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        seleccionarEstudiante(newSelection);
                    } else {
                        limpiarCampos();
                    }
                }
            );
        }

        btnAsignarProyecto.setDisable(true);
    }
    
    /**
     * Inicializa la información del coordinador en sesión.
     * 
     * @param coordinadorSesion Objeto Coordinador con los datos de la sesión 
     * activa
     */
    public void inicializarInformacion(Coordinador coordinadorSesion) {
        this.coordinadorSesion = coordinadorSesion;
        cargarInformacionUsuario();
    }

    /* Sección: Manejo de eventos de la interfaz
     * Contiene los métodos que responden a las acciones del usuario
     */
    /**
     * Maneja el evento de clic en el botón de asignar proyecto.
     * Abre una ventana modal con los proyectos disponibles para asignar.
     * 
     * @param event Evento de acción generado por el clic
     */
    @FXML
    private void clicAsignarProyecto(ActionEvent event) {
        Estudiante estudianteSeleccionado = cbEstudiantes.getSelectionModel().getSelectedItem();

        if (estudianteSeleccionado == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Sin selección",
                "Por favor, seleccione un estudiante de la lista antes de asignar un proyecto.");
            return;
        }

        try {
            List<Proyecto> proyectos = ProyectoDAO.obtenerProyectosDisponibles();

            ParametrosProyectosDisponibles parametros = new 
                    ParametrosProyectosDisponibles(
                proyectos,
                (proyectoAsignado) -> {
                    actualizarProyectoAsignado(proyectoAsignado);
                },
                estudianteSeleccionado
            );

            Navegador.abrirVentanaModalParametrizada(
                Utilidad.getEscenarioComponente(lblNombreUsuario),
                "/sistemapracticasis/vista/FXMLProyectosDisponibles.fxml",
                FXMLProyectosDisponiblesController.class,
                "inicializarInformacion",
                parametros 
            );

        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "ErrorBD", 
                "No hay conexión a la Base de Datos.");
        }
    }

    /**
     * Maneja el evento de clic en el botón de cancelar.
     * Regresa a la vista principal del coordinador.
     * 
     * @param event Evento de acción generado por el clic
     */
    @FXML
    private void clicCancelar(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
            "Cancelar",
            "Cancelar",
            "¿Está seguro de que quiere cancelar?")) {
                Navegador.cambiarEscenaParametrizada(
                    Utilidad.getEscenarioComponente(lblNombreUsuario),
                    "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
                    FXMLPrincipalCoordinadorController.class,
                    "inicializarInformacion",
                    coordinadorSesion
                );
        }
    }

    /* Sección: Métodos auxiliares
     * Contiene los métodos que ayudan a relalizar las operaciones de los métodos principales
     */
    /**
     * Actualiza la interfaz con el nombre del proyecto asignado.
     * 
     * @param proyectoAsignado Proyecto que se asignó al estudiante.
     */
    public void actualizarProyectoAsignado(Proyecto proyectoAsignado) {
        Estudiante estudianteEnUI = cbEstudiantes.getSelectionModel().getSelectedItem();
    
        if (estudianteEnUI != null && proyectoAsignado != null) {
            estudianteEnUI.setIdProyecto(proyectoAsignado.getIdProyecto());
            estudianteEnUI.setNombreProyecto(proyectoAsignado.getNombre());

            txtProyecto.setText(proyectoAsignado.getNombre());
            btnAsignarProyecto.setDisable(true);

            cbEstudiantes.getItems().set(cbEstudiantes.getSelectionModel().getSelectedIndex(), 
                estudianteEnUI);
            cbEstudiantes.getSelectionModel().select(estudianteEnUI);

            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Proyecto asignado "
                + "correctamente.");
        }
    }
    
    private void seleccionarEstudiante(Estudiante estudiante) {
        txtMatricula.setText(estudiante.getMatricula());
        txtNombre.setText(estudiante.toString());
        txtCorreo.setText(estudiante.getCorreo());
        txtTelefono.setText(estudiante.getTelefono());

       if (estudiante.getIdProyecto() == 0) {
            txtProyecto.setText("Aún no cuenta con un proyecto asignado");
            btnAsignarProyecto.setDisable(false);
        } else {
            txtProyecto.setText(estudiante.getNombreProyecto());
            btnAsignarProyecto.setDisable(true);
        }
   }

    /* Sección: Métodos auxiliares privados
     * Contiene la lógica de apoyo para las operaciones principales
     */
    private void cargarInformacionUsuario() {
        if (coordinadorSesion != null) {
            lblNombreUsuario.setText(coordinadorSesion.toString());
        }
    }
    
    private void cargarEstudiantesDelPeriodoActual() {
        listaEstudiantes = FXCollections.observableArrayList(
            EstudianteDAO.obtenerEstudiantesConProyectoDelPeriodoActual()
        );
        cbEstudiantes.setItems(listaEstudiantes);
    }
    
    private void filtrarEstudiantes(String filtro) {
        ObservableList<Estudiante> estudiantesFiltrados;

        if (filtro == null || filtro.trim().isEmpty()) {
            estudiantesFiltrados = listaEstudiantes;
        } else {
            String textoBusqueda = filtro.toLowerCase().trim();

            estudiantesFiltrados = listaEstudiantes.stream()
                .filter(estudiante -> estudiante.toString()
                                                .toLowerCase()
                                                .contains(textoBusqueda))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

        cbEstudiantes.getSelectionModel().clearSelection();

        cbEstudiantes.setItems(estudiantesFiltrados);
    }

    private void limpiarCampos() {
        txtMatricula.clear();
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtProyecto.clear();
    }
}