/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class FXMLRegistrarProyectoController implements Initializable {

    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField txtCupo;
    @FXML
    private TextField txtNombre;
    @FXML
    private Button btnCancelar;
    @FXML
    private ComboBox<?> cbEstado;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private Label lblTituloVentana;
    @FXML
    private ComboBox<?> cbOrganizacionVinculada;
    @FXML
    private ComboBox<?> cbResponsable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
    }
    
}
