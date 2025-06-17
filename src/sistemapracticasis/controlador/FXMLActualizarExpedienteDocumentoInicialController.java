package sistemapracticasis.controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.stage.FileChooser;

import sistemapracticasis.modelo.dao.DocumentoDAO;
import sistemapracticasis.modelo.dao.EntregaDocumentoDAO;
import sistemapracticasis.modelo.dao.ExpedienteDAO;
import sistemapracticasis.modelo.dao.ExperienciaEducativaDAO;
import sistemapracticasis.modelo.dao.PeriodoDAO;
import sistemapracticasis.modelo.dao.ProfesorDAO;
import sistemapracticasis.modelo.dao.ProyectoDAO;
import sistemapracticasis.modelo.pojo.EntregaDocumento;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.Expediente;
import sistemapracticasis.modelo.pojo.ExperienciaEducativa;
import sistemapracticasis.modelo.pojo.Periodo;
import sistemapracticasis.modelo.pojo.Profesor;
import sistemapracticasis.modelo.pojo.Proyecto;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * Autor: Uriel Cendón
 * Fecha de creación: 13/06/2025
 * Descripción: Controlador de la vista FXMLActualizarExpedienteDocumento
 * Inicial, que permite a los estudiantes subir documentos iniciales 
 * según las entregas programadas.
 */
public class FXMLActualizarExpedienteDocumentoInicialController implements 
        Initializable {

    /* Sección: Declaración de variables
    * Contiene todas las variables de instancia y componentes FXML
    * utilizados en el controlador.
    /** Estudiante actualmente en sesión */
    private Estudiante estudianteSesion;
    
    /** Etiqueta que muestra el nombre del usuario */
    @FXML private Label lblNombreUsuario;
    
    /** Campo de texto que muestra el nombre del estudiante */
    @FXML private TextField txtNombreEstudiante;
    
    /** Campo de texto que muestra el periodo académico */
    @FXML private TextField txtPeriodo;
    
    /** Campo de texto que muestra la fecha de inicio del periodo */
    @FXML private TextField txtInicioPeriodo;
    
    /** Campo de texto que muestra la matrícula del estudiante */
    @FXML private TextField txtMatriculaEstudiante;
    
    /** Campo de texto que muestra el correo del estudiante */
    @FXML private TextField txtCorreo;
    
    /** Campo de texto que muestra la fecha de fin del periodo */
    @FXML private TextField txtFinPeriodo;
    
    /** Campo de texto que muestra las horas acumuladas */
    @FXML private TextField txtHorasAcumuladas;
    
    /** Campo de texto que muestra el estado del expediente */
    @FXML private TextField txtEstado;
    
    /** Campo de texto que muestra el nombre del proyecto */
    @FXML private TextField txtNombreProyecto;
    
    /** Campo de texto que muestra el nombre de la experiencia educativa */
    @FXML private TextField txtNombreEE;
    
    /** Campo de texto que muestra el nombre del profesor */
    @FXML private TextField txtNombreProfesor;
    
    /** Tabla que muestra las entregas de documentos pendientes */
    @FXML private TableView<EntregaDocumento> tblEntregas;
    
    /** Columna de la tabla que muestra el nombre del documento */
    @FXML private TableColumn<EntregaDocumento, String> tbcNombreDocEntrega;
    
    /** Columna de la tabla que muestra la fecha de inicio de entrega */
    @FXML private TableColumn<EntregaDocumento, String> tbcFechaInicioEntrega;
    
    /** Columna de la tabla que muestra la fecha de fin de entrega */
    @FXML private TableColumn<EntregaDocumento, String> tbcFechaFinEntrega;
    
    /** Botón para subir documentos */
    @FXML private Button btnSubirDocumento;

    /** ID del expediente actual */
    private int idExpedienteActual;

    /**
     * Inicializa el controlador después de que su raíz ha sido procesada.
     * Configura los componentes iniciales y prepara la tabla de entregas.
     * 
     * @param url Ubicación relativa del objeto raíz
     * @param rb Recursos localizados para este objeto
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnSubirDocumento.setDisable(true);
        
        tbcNombreDocEntrega.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcFechaInicioEntrega.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getFechaInicio()));
        tbcFechaFinEntrega.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getFechaFin()));
        
        agregarListenersSeleccion();
    }
    
    /* Sección: Métodos públicos
    * Contiene los métodos accesibles desde otras clases, principalmente
    * para inicializar información y manejar eventos de la interfaz.
    /**
     * Inicializa la información del estudiante y carga todos los datos 
     * relacionados.
     * 
     * @param estudianteSesion El estudiante autenticado en la sesión actual
     */
    public void inicializarInformacion(Estudiante estudianteSesion) {
        this.estudianteSesion = estudianteSesion;
        cargarInformacionUsuario();
        cargarInformacionPeriodo();
        cargarInformacionExpediente();
        cargarInformacionProyecto();
        cargarInformacionExperienciaEducativa();
        cargarInformacionEntregaDocumento();
        cargarInformacionProfesor();
    }

    /**
     * Maneja el evento de clic en el botón de salir.
     * Muestra una confirmación y regresa a la vista principal del estudiante.
     * 
     * @param event Evento de acción generado por el clic
     */
    @FXML
    private void clicSalir(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
            "Salir",
            "Salir",
            "¿Está seguro de que quiere salir?")) {
                Navegador.cambiarEscenaParametrizada(
                    Utilidad.getEscenarioComponente(lblNombreUsuario),
                    "/sistemapracticasis/vista/FXMLPrincipalEstudiante.fxml",
                    FXMLPrincipalEstudianteController.class,
                    "inicializarInformacion",
                    estudianteSesion
                );
        }
    }

    /**
     * Maneja el evento de clic en el botón de subir documento.
     * Permite seleccionar un archivo PDF y subirlo al sistema.
     * 
     * @param event Evento de acción generado por el clic
     */
    @FXML
    private void clicSubirDocumento(ActionEvent event) {
        EntregaDocumento entregaSeleccionada = tblEntregas.getSelectionModel()
            .getSelectedItem();
    
        if (entregaSeleccionada != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Abrir");

            FileChooser.ExtensionFilter extFilter = new FileChooser.
                ExtensionFilter(
                    "Archivos PDF (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);

            File archivoSeleccionado = fileChooser.showOpenDialog(
                btnSubirDocumento.getScene().getWindow());

            if (archivoSeleccionado != null) {
                if (Utilidad.mostrarConfirmacion(
                    "Confirmación",
                    "Confirmación",
                    "¿Está seguro de que quiere subir el documento "
                    + "seleccionado?")) {

                    try {
                        byte[] contenidoDocumento = Files.readAllBytes
                            (archivoSeleccionado.toPath());

                        boolean exito = DocumentoDAO.guardarDocumentoInicial(
                            entregaSeleccionada.getIdEntregaDocumento(),
                            archivoSeleccionado.getName(),
                            contenidoDocumento);

                        if (exito) {
                            Utilidad.mostrarAlertaSimple(
                                Alert.AlertType.INFORMATION,
                                "Operación exitosa",
                                "Operación realizada exitosamente.");

                            cargarInformacionEntregaDocumento();
                        } else {
                            Utilidad.mostrarAlertaSimple(
                                Alert.AlertType.ERROR,
                                "Error",
                                "No se pudo subir el documento");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        Utilidad.mostrarAlertaSimple(
                            Alert.AlertType.ERROR,
                            "Error",
                            "Ocurrió un error al leer el archivo");
                    }
                }
            }
        }
    }

    /* Sección: Métodos de carga de información
     * Contiene los métodos responsables de cargar y mostrar
     * la información del estudiante y sus documentos.
     */
    private void cargarInformacionUsuario() {
        if (estudianteSesion != null) {
            lblNombreUsuario.setText(estudianteSesion.toString());
            txtNombreEstudiante.setText(estudianteSesion.toString());
            txtMatriculaEstudiante.setText(estudianteSesion.getMatricula());
            txtCorreo.setText(estudianteSesion.getCorreo());
        }
    }
    
    private void cargarInformacionPeriodo() {
        if (estudianteSesion != null) {
            Periodo periodo = PeriodoDAO.obtenerPeriodoActualPorEstudiante(
                estudianteSesion.getIdEstudiante());
            if (periodo != null) {
                txtPeriodo.setText("Periodo: " + periodo.getNombrePeriodo());
                txtInicioPeriodo.setText(periodo.getFechaInicio());
                txtFinPeriodo.setText(periodo.getFechaFin());
            }
        }
    }
    
    private void cargarInformacionExpediente() {
        if (estudianteSesion != null) {
            Expediente expediente = ExpedienteDAO.
                obtenerExpedientePorIdEstudiante(
                estudianteSesion.getIdEstudiante());

            if (expediente != null) {
                txtHorasAcumuladas.setText("Horas acumuladas: " + 
                    String.valueOf(expediente.getHorasAcumuladas()));
                txtEstado.setText("Estado: " + expediente.getEstado().
                    getValorEnDB());
                idExpedienteActual = expediente.getIdExpediente();
            } else {
                txtHorasAcumuladas.setText("Horas acumuladas: 0");
                txtEstado.setText("Estado: No registrado");
                idExpedienteActual = 0;
            }
        }
    }
    
    private void cargarInformacionProyecto() {
        if (estudianteSesion != null) {
            Proyecto proyecto = ProyectoDAO.obtenerProyectoPorIdEstudiante(
                estudianteSesion.getIdEstudiante());

            if (proyecto != null) {
                txtNombreProyecto.setText(proyecto.getNombre());
            } else {
                txtNombreProyecto.setText("No asignado");
            }
        }
    }
    
    private void cargarInformacionExperienciaEducativa() {
        if (estudianteSesion != null) {
            ExperienciaEducativa experiencia = ExperienciaEducativaDAO
                .obtenerEEPorIdEstudiante(estudianteSesion.getIdEstudiante());

            if (experiencia != null) {
                txtNombreEE.setText(experiencia.getNombre());
            } else {
                txtNombreEE.setText("No asignada");
            }
        }
    }
    
    private void cargarInformacionProfesor() {
        if (estudianteSesion != null) {
            Profesor profesor = ProfesorDAO.obtenerProfesorPorIdEstudiante(
                estudianteSesion.getIdEstudiante());

            if (profesor != null) {
                txtNombreProfesor.setText("Profesor: " + profesor.toString());
            } else {
                txtNombreProfesor.setText("Profesor: No asignado");
            }
        }
    }
    
    private void cargarInformacionEntregaDocumento() {
        if (idExpedienteActual > 0) {
            List<EntregaDocumento> entregas = EntregaDocumentoDAO
                .obtenerEntregasInicialesPorExpediente(idExpedienteActual);
            tblEntregas.getItems().setAll(entregas);
        }
    }
    
    /* Sección: Listener
     * Contiene un método de apoyo para poder identificar el elemento de la
     * tabla y manejar acciones en base a ello.
     */
    private void agregarListenersSeleccion() {
        tblEntregas.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> {
                if (newVal != null && estudianteSesion != null) {
                    boolean yaEntregado = DocumentoDAO.documentoYaEntregado(
                        newVal.getIdEntregaDocumento(), 
                        estudianteSesion.getIdEstudiante()
                    );

                    btnSubirDocumento.setDisable(yaEntregado);
                } else {
                    btnSubirDocumento.setDisable(true);
                }
            }
        );
    }
}