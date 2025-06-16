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
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.modelo.pojo.Profesor;
import sistemapracticasis.modelo.dao.EstudianteDAO;
import sistemapracticasis.modelo.dao.ReporteDAO;
import sistemapracticasis.modelo.pojo.EntregaVisual;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLValidarEntregaController implements Initializable {

    private Profesor profesorSesion;
    
    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<EntregaVisual> tblEntregas;
    @FXML
    private TableColumn<EntregaVisual, String> tbcNombreDocumento;
    @FXML
    private TableColumn<EntregaVisual, String> tbcFechaEntregadoDoc;
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Button btnOpciones;

    /**
     * Initializes the controller class.
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
    
    public void inicializarInformacion(Profesor profesorSesion) {
        this.profesorSesion = profesorSesion;
        cargarInformacionUsuario();
    }

    private void cargarInformacionUsuario() {
        if (profesorSesion != null) {
            lblNombreUsuario.setText(
                profesorSesion.toString()
            );
        }
    }

    @FXML
    private void clicOpciones(ActionEvent event) {
        EntregaVisual seleccion = tblEntregas.getSelectionModel().getSelectedItem();
    
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

    @FXML
    private void clicBuscar(ActionEvent event) {
        String textoBusqueda = txtBuscar.getText().trim();

        if (!validarCampos(textoBusqueda)) return;

        Estudiante estudianteEncontrado = obtenerEstudiante(textoBusqueda);

        if (estudianteEncontrado != null) {
            llenarCamposEstudiante(estudianteEncontrado);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                "No encontrado", "No se encontró ningún estudiante con la "
                    + "matrícula ingresada.");
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
        int idExpediente = EstudianteDAO.obtenerIdExpedientePorEstudiante
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
    
    private Estudiante obtenerEstudiante(String matricula) {
        Estudiante estudiante = new Estudiante();
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        boolean encontrado = estudianteDAO.buscarPorMatricula
            (matricula, estudiante);
        return (encontrado) ? estudiante : null;
    }
    
    private void agregarListenersSeleccion() {
        tblEntregas.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> {
                if (newVal != null) {
                    boolean tieneCalificacion = false;

                    if (newVal.getTipo().equals("documento")) {
                        Double calificacion = DocumentoDAO.
                            obtenerCalificacionPorId(newVal.getId());
                        tieneCalificacion = (calificacion != null);
                    } else if (newVal.getTipo().equals("reporte")) {
                        Double calificacion = ReporteDAO.
                            obtenerCalificacionPorId(newVal.getId());
                        tieneCalificacion = (calificacion != null);
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
