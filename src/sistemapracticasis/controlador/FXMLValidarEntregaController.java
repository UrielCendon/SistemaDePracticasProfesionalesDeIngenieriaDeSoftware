package sistemapracticasis.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sistemapracticasis.SistemaPracticasIS;
import sistemapracticasis.modelo.pojo.Profesor;
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
    private TableView<?> tblEntregas;
    @FXML
    private TableColumn<?, ?> tbcNombreDocumento;
    @FXML
    private TableColumn<?, ?> tbcFechaEntregadoDoc;
    @FXML
    private Label lblNombreUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        boolean confirmado = Utilidad.mostrarConfirmacion(
            "Cancelar",
            "¿Está seguro de que quiere cancelar?",
            ""
        );

        if (confirmado) {
            try {
                Stage escenarioBase = (Stage) lblNombreUsuario.getScene().getWindow();
                FXMLLoader cargador = new FXMLLoader
                    (SistemaPracticasIS.
                            class.getResource("vista/FXMLPrincipalProfesor.fxml"));
                Parent vista = cargador.load();
                FXMLPrincipalProfesorController controlador = cargador
                    .getController();
                controlador.inicializarInformacion(profesorSesion);
                Scene escenaPrincipal = new Scene(vista);
                escenarioBase.setScene(escenaPrincipal);
                escenarioBase.setTitle("Sistema de gestión de prácticas "
                    + "profesionales");
                escenarioBase.centerOnScreen();
                escenarioBase.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void clicBuscar(ActionEvent event) {
    }
    
}
