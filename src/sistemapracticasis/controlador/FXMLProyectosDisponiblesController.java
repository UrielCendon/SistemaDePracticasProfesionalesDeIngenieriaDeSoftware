package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLProyectosDisponiblesController implements Initializable {

    @FXML
    private TableView<?> tblProyectos;
    @FXML
    private TableColumn<?, ?> tbcNombre;
    @FXML
    private TextField txtProyectoSeleccionado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicAsignarProyecto(ActionEvent event) {
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
    }
    
}
