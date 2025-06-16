package sistemapracticasis.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    
    /** DAO para operaciones relacionadas con estudiantes */
    private final EstudianteDAO ESTUDIANTE_DAO = new EstudianteDAO();
    
    /** Botón para asignar proyecto al estudiante */
    @FXML private Button btnAsignarProyecto;
    
    /** Botón para cancelar la operación */
    @FXML private Button btnCancelar;
    
    /** Campo de texto para buscar estudiantes por matrícula */
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

    /**
     * Inicializa el controlador después de que su raíz ha sido procesada.
     * Configura el estado inicial de los componentes.
     * 
     * @param url Ubicación relativa del objeto raíz
     * @param rb Recursos localizados para este objeto
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        try {
            String textoBusqueda = txtBuscar.getText().trim();
            List<Proyecto> proyectos = ProyectoDAO.
                obtenerProyectosDisponibles();
            Estudiante estudiante = obtenerEstudiante(textoBusqueda);

            if (estudiante.getIdProyecto() > 0) {
                btnAsignarProyecto.setDisable(true);
            }
            
            ParametrosProyectosDisponibles parametros = new 
                    ParametrosProyectosDisponibles(
                proyectos,
                (nombreProyecto) -> {
                    actualizarProyectoAsignado(nombreProyecto);
                },
                estudiante
            );

            Navegador.abrirVentanaModalParametrizada(
                Utilidad.getEscenarioComponente(lblNombreUsuario),
                "/sistemapracticasis/vista/FXMLProyectosDisponibles.fxml",
                FXMLProyectosDisponiblesController.class,
                "inicializarInformacion",
                estudiante,
                parametros
            );

        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Error de base de datos", 
                "No se pudieron cargar los proyectos.");
            ex.printStackTrace();
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

    /**
     * Maneja el evento de clic en el botón de buscar.
     * Busca un estudiante por matrícula y muestra su información.
     * 
     * @param event Evento de acción generado por el clic
     */
    @FXML
    private void clicBuscar(ActionEvent event) {
        String textoBusqueda = txtBuscar.getText().trim();

        if (!validarCampos(textoBusqueda)) return;

        Estudiante estudianteEncontrado = obtenerEstudiante(textoBusqueda);

        if (estudianteEncontrado != null) {
            boolean estaEnPeriodo = EstudianteDAO.estaEnPeriodoActual
                (textoBusqueda);

            if (estaEnPeriodo) {
                llenarCamposEstudiante(estudianteEncontrado);
            } else {
                Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.WARNING,
                    "Estudiante fuera de periodo",
                    "El estudiante no pertenece al periodo actual. "
                        + "Por favor, intente con otro."
                );
                limpiarCampos();
            }

        } else {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.INFORMATION,
                "No encontrado",
                "No se encontró ningún estudiante con la matrícula ingresada."
            );
            limpiarCampos();
        }
    }

    /**
     * Actualiza la interfaz con el nombre del proyecto asignado.
     * 
     * @param nombreProyecto Nombre del proyecto que fue asignado al estudiante
     */
    public void actualizarProyectoAsignado(String nombreProyecto) {
        txtProyecto.setText(nombreProyecto);
        btnAsignarProyecto.setDisable(true);
    }

    /* Sección: Métodos auxiliares privados
     * Contiene la lógica de apoyo para las operaciones principales
     */
    private void cargarInformacionUsuario() {
        if (coordinadorSesion != null) {
            lblNombreUsuario.setText(coordinadorSesion.toString());
        }
    }

    private boolean validarCampos(String matricula) {
        if (matricula.isEmpty()) {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.WARNING,
                "Campo vacío", 
                "Ingrese una matrícula para buscar al estudiante."
            );
            return false;
        }

        if (!matricula.matches("S\\d{8}")) {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.WARNING,
                "Matrícula inválida", 
                "La matrícula debe iniciar con 'S' seguido de 8 dígitos "
                    + "(Ej. S12345678)."
            );
            return false;
        }

        return true;
    }

    private void llenarCamposEstudiante(Estudiante estudiante) {
        txtMatricula.setText(estudiante.getMatricula());
        txtNombre.setText(estudiante.toString());
        txtCorreo.setText(estudiante.getCorreo());
        txtTelefono.setText(estudiante.getTelefono());

        if (estudiante.getIdProyecto() == 0) {
            txtProyecto.setText("Aún no cuenta con un proyecto asignado");
            btnAsignarProyecto.setDisable(false);
        } else {
            txtProyecto.setText(estudiante.getNombreProyecto());
        }
    }

    private void limpiarCampos() {
        txtMatricula.clear();
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtProyecto.clear();
    }
    
    private Estudiante obtenerEstudiante(String matricula) {
        Estudiante estudiante = new Estudiante();
        boolean encontrado = ESTUDIANTE_DAO.buscarPorMatricula(matricula, 
            estudiante);
        return encontrado ? estudiante : null;
    }
}