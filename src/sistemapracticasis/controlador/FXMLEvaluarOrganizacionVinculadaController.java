package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sistemapracticasis.modelo.pojo.Estudiante;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLEvaluarOrganizacionVinculadaController implements Initializable {
    
    private Estudiante estudianteSesion;

    @FXML
    private TextField txtRazonSocial;
    @FXML
    private TextField txtAmbienteLaboral;
    @FXML
    private TextField txtSupervisionEncargado;
    @FXML
    private TextField txtCumplimientoActividades;
    @FXML
    private TextField txtCargaLaboral;
    @FXML
    private TextField txtFecha;
    @FXML
    private TextField txtPuntajeTotal;
    @FXML
    private TextArea txaRecomendaciones;
    @FXML
    private Label lblNombreUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void inicializarInformacion(Estudiante estudianteSesion) {
        this.estudianteSesion = estudianteSesion;
        cargarInformacionUsuario();
    }

    private void cargarInformacionUsuario() {
        if (estudianteSesion != null) {
            lblNombreUsuario.setText(
                estudianteSesion.toString()
            );
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
                    "/sistemapracticasis/vista/FXMLPrincipalEstudiante.fxml",
                    FXMLPrincipalEstudianteController.class,
                    "inicializarInformacion",
                    estudianteSesion
                );
        }
    }

    @FXML
    private void clicSubir(ActionEvent event) {
    }
    
}
