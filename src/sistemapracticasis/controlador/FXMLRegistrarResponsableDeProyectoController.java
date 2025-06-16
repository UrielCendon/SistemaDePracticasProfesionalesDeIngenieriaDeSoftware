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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class FXMLRegistrarResponsableDeProyectoController implements Initializable {

    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtAPaterno;
    @FXML
    private TextField txtDepartamento;
    @FXML
    private TextField txtCorreo;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private TextField txtPuesto;
    @FXML
    private TextField txtAMaterno;
    @FXML
    private TextField txtTelefono;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
    }
    
}
