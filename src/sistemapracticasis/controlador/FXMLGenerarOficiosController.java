package sistemapracticasis.controlador;

import java.io.File;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

import sistemapracticasis.modelo.dao.GenerarOficiosDAO;
import sistemapracticasis.modelo.pojo.Coordinador;
import sistemapracticasis.modelo.pojo.EstudianteAsignado;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.PDFGenerador;
import sistemapracticasis.util.Utilidad;

/** 
 * Autor: Raziel Filobello
 * Fecha de creación: 15/06/2025
 * Descripción: Controlador para la generación de oficios de asignación,
 * permite seleccionar estudiantes y generar los documentos oficiales
 * de asignación a organizaciones vinculadas.
 */
public class FXMLGenerarOficiosController implements Initializable {

    /* Sección: Componentes de la interfaz
     * Contiene los elementos FXML de la vista.
     */
    @FXML private Button btnCancelar;
    @FXML private Label lblNombreUsuario;
    @FXML private Button btnGenerarDocumentos;
    @FXML private TableView<EstudianteAsignado> tvOficiosAsignacion;
    @FXML private TableColumn<EstudianteAsignado, String> colMatricula;
    @FXML private TableColumn<EstudianteAsignado, String> colEstudiante;
    @FXML private TableColumn<EstudianteAsignado, String> colProyectoAsignado;
    @FXML private TableColumn<EstudianteAsignado, String> colOrganizacionVinculada;
    @FXML private TableColumn<EstudianteAsignado, Boolean> colSeleccionEstudiante;
    @FXML private Button btnSeleccionarTodos;

    /* Sección: Variables de instancia
     * Almacena datos de la sesión y lista de estudiantes.
     */
    private Coordinador coordinadorSesion;
    private ObservableList<EstudianteAsignado> listaEstudiantes = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     * @param url Ubicación utilizada para resolver rutas relativas.
     * @param rb Recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarEstudiantes();
    }

    /* Sección: Configuración de interfaz
     * Métodos para configurar componentes visuales.
     */
    private void configurarTabla() {
        tvOficiosAsignacion.setEditable(true);
        colSeleccionEstudiante.setEditable(true);
        
        colMatricula.setCellValueFactory(cellData -> cellData.getValue().matriculaProperty());
        colEstudiante.setCellValueFactory(cellData -> cellData.getValue().nombreEstudianteProperty());
        colProyectoAsignado.setCellValueFactory(cellData -> cellData.getValue().nombreProyectoProperty());
        colOrganizacionVinculada.setCellValueFactory(cellData -> cellData.getValue().razonSocialOrganizacionProperty());
        colSeleccionEstudiante.setCellValueFactory(cellData -> cellData.getValue().seleccionadoProperty());
        colSeleccionEstudiante.setCellFactory(CheckBoxTableCell.forTableColumn(colSeleccionEstudiante));
    }

    /* Sección: Carga de datos
     * Métodos para obtener y mostrar información.
     */
    private void cargarEstudiantes() {
        try {
            List<EstudianteAsignado> estudiantes = GenerarOficiosDAO.obtenerEstudiantesAsignadosPeriodoActual();
            listaEstudiantes.setAll(estudiantes);
            tvOficiosAsignacion.setItems(listaEstudiantes);
        } catch (Exception e) {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Sin conexión",
                "No hay conexión con la base de datos."
            );
        }
    }

    /**
     * Inicializa la información del coordinador en sesión.
     * @param coordinadorSesion Objeto Coordinador con los datos de la sesión.
     */
    public void inicializarInformacion(Coordinador coordinadorSesion) {
        this.coordinadorSesion = coordinadorSesion;
        cargarInformacionUsuario();
    }

    private void cargarInformacionUsuario() {
        if (coordinadorSesion != null) {
            lblNombreUsuario.setText(coordinadorSesion.toString());
        }
    }

    /* Sección: Manejo de eventos
     * Métodos para acciones del usuario.
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

    @FXML
    private void clicSeleccionarTodos(ActionEvent event) {
        for (EstudianteAsignado est : listaEstudiantes) {
            est.setSeleccionado(true);
        }
        tvOficiosAsignacion.refresh();
    }

    @FXML
    private void clicGenerarDocumentos(ActionEvent event) {
        List<EstudianteAsignado> seleccionados = listaEstudiantes.stream()
            .filter(EstudianteAsignado::isSeleccionado)
            .collect(Collectors.toList());

        if (seleccionados.isEmpty()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                "Estudiantes no seleccionados",
                "Se debe seleccionar al menos un estudiante antes de generar los oficios.");
            return;
        }

        File carpeta = new File("oficios_generados");
        if (!carpeta.exists()) carpeta.mkdirs();

        boolean todosGenerados = true;

        for (EstudianteAsignado est : seleccionados) {
            boolean ok = PDFGenerador.generarOficio(est, carpeta.getAbsolutePath());
            if (!ok) todosGenerados = false;
        }

        if (todosGenerados) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                "Éxito", "Oficios generados correctamente.");
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                "Error", "No fue posible generar los OFICIOS DE ASIGNACIÓN.");
        }
    }
}