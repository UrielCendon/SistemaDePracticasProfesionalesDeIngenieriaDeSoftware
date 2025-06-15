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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Kaiser
 */
public class FXMLEvaluarRubricaController implements Initializable {

    @FXML
    private Label lblNombreEstudiante;
    @FXML
    private Label lblExperienciaEducativa;
    @FXML
    private Label lblProyecto;
    @FXML
    private TableView<?> tvRubrica;
    @FXML
    private TableColumn<?, ?> colCriterioEvaluacion;
    @FXML
    private TableColumn<?, ?> colExcelente;
    @FXML
    private TableColumn<?, ?> colMuyBien;
    @FXML
    private TableColumn<?, ?> colBien;
    @FXML
    private TableColumn<?, ?> colRegular;
    @FXML
    private TableColumn<?, ?> colInsuficiente;
    @FXML
    private TableView<?> tvCalificacion;
    @FXML
    private TableColumn<?, ?> colCriterio;
    @FXML
    private TableColumn<?, ?> colCalificacion;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregarObservacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicAceptar(ActionEvent event) {
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
    }

    @FXML
    private void clicAgregarObservacion(ActionEvent event) {
    }
    
}
