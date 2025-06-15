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
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLActualizarExpedienteDocumentoInicialController implements 
        Initializable {

    private Estudiante estudianteSesion;
    
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private TextField txtNombreEstudiante;
    @FXML
    private TextField txtPeriodo;
    @FXML
    private TextField txtInicioPeriodo;
    @FXML
    private TextField txtMatriculaEstudiante;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtFinPeriodo;
    @FXML
    private TextField txtHorasAcumuladas;
    @FXML
    private TextField txtEstado;
    @FXML
    private TextField txtNombreProyecto;
    @FXML
    private TextField txtNombreEE;
    @FXML
    private TextField txtNombreProfesor;
    @FXML
    private TableView<EntregaDocumento> tblEntregas;
    @FXML
    private TableColumn<EntregaDocumento, String> tbcNombreDocEntrega;
    @FXML
    private TableColumn<EntregaDocumento, String> tbcFechaInicioEntrega;
    @FXML
    private TableColumn<EntregaDocumento, String> tbcFechaFinEntrega;
    @FXML
    private Button btnSubirDocumento;

    private int idExpedienteActual;

    /**
     * Initializes the controller class.
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
            Periodo periodo = PeriodoDAO.obtenerPeriodoActualPorEstudiante
                (estudianteSesion.getIdEstudiante());
            if (periodo != null) {
                txtPeriodo.setText("Periodo: " + periodo.getNombrePeriodo());
                txtInicioPeriodo.setText(periodo.getFechaInicio());
                txtFinPeriodo.setText(periodo.getFechaFin());
            }
        }
    }
    
    private void cargarInformacionExpediente() {
        if (estudianteSesion != null) {
            Expediente expediente = 
                ExpedienteDAO.obtenerExpedientePorIdEstudiante(
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
            Proyecto proyecto = ProyectoDAO.obtenerProyectoPorIdEstudiante
                (estudianteSesion.getIdEstudiante());

            if (proyecto != null) {
                txtNombreProyecto.setText(proyecto.getNombre());
            } else {
                txtNombreProyecto.setText("No asignado");
                System.out.println("El estudiante no tiene proyecto asignado");
            }
        }
    }
    
    private void cargarInformacionExperienciaEducativa() {
        if (estudianteSesion != null) {
            ExperienciaEducativa experiencia = ExperienciaEducativaDAO.
                obtenerEEPorIdEstudiante(estudianteSesion.getIdEstudiante());

            if (experiencia != null) {
                txtNombreEE.setText(experiencia.getNombre());
            } else {
                txtNombreEE.setText("No asignada");
            }
        }
    }
    
    private void cargarInformacionProfesor(){
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
    
    private void cargarInformacionEntregaDocumento(){
        if (idExpedienteActual > 0) {
            List<EntregaDocumento> entregas = EntregaDocumentoDAO
                .obtenerEntregasInicialesPorExpediente(idExpedienteActual);
            tblEntregas.getItems().setAll(entregas);
        }
    }
    
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
}