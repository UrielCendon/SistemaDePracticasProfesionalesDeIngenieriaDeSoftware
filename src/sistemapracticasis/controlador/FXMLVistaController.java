package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLVistaController implements Initializable {

    @FXML
    private TextField txtNombreDocumento;
    @FXML
    private TextField txtFechaEntregado;
    @FXML
    private TextField txtCalificacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicAgregarObservacion(ActionEvent event) {
    }

    @FXML
    private void clicValidar(ActionEvent event) {
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
    }
    
}
