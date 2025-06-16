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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class FXMLSeleccionarOVYResponsableController implements Initializable {

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnRegresar;
    @FXML
    private ComboBox<?> cbOrganizaciones;
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private TextField txtBuscar;
    @FXML
    private ComboBox<?> cbResponsables;

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
    private void clicBtnRegresar(ActionEvent event) {
    }
    
}
