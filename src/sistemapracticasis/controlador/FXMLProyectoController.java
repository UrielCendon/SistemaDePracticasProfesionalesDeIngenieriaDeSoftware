package sistemapracticasis.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import sistemapracticasis.modelo.dao.ProyectoDAO;
import sistemapracticasis.modelo.pojo.Coordinador;
import sistemapracticasis.modelo.pojo.Proyecto;
import sistemapracticasis.modelo.pojo.ResultadoOperacion;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * Autor: Miguel Escobar
 * Fecha de creación: 15/06/2025
 * Descripción: Controlador para la gestión de proyectos académicos,
 * permite visualizar, buscar, actualizar y eliminar proyectos.
 */
public class FXMLProyectoController implements Initializable {

    /* Sección: Componentes de la interfaz
     * Contiene los elementos FXML de la vista de gestión de proyectos.
     */

    /** Tabla que muestra los proyectos disponibles. */
    @FXML private TableView<Proyecto> tblEntregas;

    /** Columna que muestra el nombre del proyecto. */
    @FXML private TableColumn<Proyecto, String> tbcNombre;

    /** Columna que indica el estado actual del proyecto. */
    @FXML private TableColumn<Proyecto, String> tbcEstado;

    /** Columna que muestra el cupo máximo del proyecto. */
    @FXML private TableColumn<Proyecto, Integer> tbcCupo;

    /** Columna que muestra la fecha de inicio del proyecto. */
    @FXML private TableColumn<Proyecto, String> tbcFechaInicio;

    /** Columna que muestra la fecha de fin del proyecto. */
    @FXML private TableColumn<Proyecto, String> tbcFechaFin;

    /** Campo de texto para buscar proyectos por nombre o palabra clave. */
    @FXML private TextField txtBuscar;

    /** Etiqueta que muestra el nombre del coordinador en sesión. */
    @FXML private Label lblNombreUsuario;

    /** Botón para regresar a la vista anterior. */
    @FXML private Button btnRegresar;

    /** Botón para eliminar el proyecto seleccionado. */
    @FXML private Button btnEliminar;

    /** Botón para actualizar la información del proyecto seleccionado. */
    @FXML private Button btnActualizar;

    /* Sección: Variables de instancia
     * Almacena los datos del coordinador en sesión.
     */

    /** Coordinador actualmente autenticado en la aplicación. */
    private Coordinador coordinadorSesion;


    /**
     * Inicializa el controlador después de que su elemento raíz haya sido procesado.
     * @param url Ubicación utilizada para resolver rutas relativas.
     * @param rb Recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarProyectos();
    }

    /* Sección: Métodos públicos */
    /**
     * Inicializa la información del coordinador en sesión.
     * @param coordinadorSesion Objeto Coordinador con datos de sesión.
     */
    public void inicializarInformacion(Coordinador coordinadorSesion) {
        this.coordinadorSesion = coordinadorSesion;
        cargarInformacionUsuario();
    }

    /* Sección: Configuración de la interfaz */
    private void configurarColumnas() {
        tbcNombre.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getNombre()));

        tbcEstado.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getEstado().toString()));

        tbcCupo.setCellValueFactory(cellData ->
            new SimpleIntegerProperty(cellData.getValue().getCupo()).asObject());

        tbcFechaInicio.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getFecha_inicio()));

        tbcFechaFin.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getFecha_fin()));
    }

    /* Sección: Carga de datos */
    private void cargarInformacionUsuario() {
        if (coordinadorSesion != null) {
            lblNombreUsuario.setText(coordinadorSesion.toString());
        }
    }

    /**
     * Carga todos los proyectos desde la base de datos.
     */
    private void cargarProyectos() {
        try {
            ArrayList<Proyecto> listaProyectos = ProyectoDAO.obtenerTodosLosProyectos();
            tblEntregas.getItems().clear();
            tblEntregas.getItems().addAll(listaProyectos);
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                "Error de conexión",
                "No hay conexión con la base de datos."); 
        }
    }

    /* Sección: Manejo de eventos */
    @FXML
    private void clicBtnBuscar(ActionEvent event) {
        String textoBusqueda = txtBuscar.getText().trim();

        if (!validarCampoBusqueda(textoBusqueda)) return;

        ArrayList<Proyecto> proyectosFiltrados = buscarProyectosPorNombre(textoBusqueda);

        if (!proyectosFiltrados.isEmpty()) {
            tblEntregas.getItems().setAll(proyectosFiltrados);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                "Sin coincidencias",
                "No se encontró ningún proyecto con el nombre ingresado.");
        }
    }

    @FXML
    private void clicBtnActualizar(ActionEvent event) {
        Proyecto proyectoSeleccionado = tblEntregas.getSelectionModel().getSelectedItem();

        if (proyectoSeleccionado == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                "Proyecto no seleccionado",
                "Por favor, selecciona un proyecto para actualizar.");
            return;
        }

        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(btnEliminar),
            "/sistemapracticasis/vista/FXMLRegistrarProyecto.fxml",
            FXMLRegistrarProyectoController.class,
            "cargarDatosProyectoParaEditar",
            proyectoSeleccionado,
            coordinadorSesion
        );
    }

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Navegador.cambiarEscenaParametrizada(
            Utilidad.getEscenarioComponente(lblNombreUsuario),
            "/sistemapracticasis/vista/FXMLPrincipalCoordinador.fxml",
            FXMLPrincipalCoordinadorController.class,
            "inicializarInformacion",
            coordinadorSesion
        );
    }

    @FXML
    private void clicBtnEliminar(ActionEvent event) {
        Proyecto proyectoSeleccionado = tblEntregas.getSelectionModel().getSelectedItem();

        if (proyectoSeleccionado == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                "Proyecto no seleccionado",
                "Por favor, selecciona un proyecto para eliminar.");
            return;
        }

        boolean confirmar = Utilidad.mostrarConfirmacion(
            "Confirmar eliminación",
            "¿Está seguro que desea eliminar el proyecto?",
            "Esta acción no se puede deshacer."
        );

        if (confirmar) {
            ResultadoOperacion resultado = ProyectoDAO.eliminarProyectoPorId(
                    proyectoSeleccionado.getIdProyecto());

            if (!resultado.isError()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                    "Proyecto eliminado",
                    resultado.getMensaje());
                cargarProyectos(); 
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error al eliminar",
                    resultado.getMensaje());
            }
        }
    }

    /* Sección: Métodos auxiliares */
    private boolean validarCampoBusqueda(String texto) {
        if (texto.isEmpty()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                "Campo vacío",
                "Ingrese un nombre para buscar un proyecto.");
            return false;
        }
        return true;
    }

    private ArrayList<Proyecto> buscarProyectosPorNombre(String textoBusqueda) {
        ArrayList<Proyecto> proyectosCoincidentes = new ArrayList<>();
        try {
            ArrayList<Proyecto> todosLosProyectos = ProyectoDAO.obtenerTodosLosProyectos();
            String textoLower = textoBusqueda.toLowerCase();

            for (Proyecto proyecto : todosLosProyectos) {
                if (proyecto.getNombre().toLowerCase().contains(textoLower)) {
                    proyectosCoincidentes.add(proyecto);
                }
            }
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                "Error de conexión",
                "No hay conexión con la base de datos.");
        }
        return proyectosCoincidentes;
    }
}