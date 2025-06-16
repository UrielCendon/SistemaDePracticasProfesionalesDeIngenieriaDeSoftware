package sistemapracticasis.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import sistemapracticasis.modelo.dao.ObservacionDAO;
import sistemapracticasis.modelo.pojo.EntregaVisual;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.Utilidad;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLObservacionesController implements Initializable {

    private EntregaVisual entregaVisual;
    
    @FXML
    private TextArea txaObservacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void inicializarDatos(EntregaVisual entrega) {
        this.entregaVisual = entrega;
    }

    @FXML
    private void clicEnviar(ActionEvent event) {
        String texto = txaObservacion.getText().trim();
        if (texto.isEmpty()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                "Campo vacío", 
                "Debe escribir una observación");
            return;
        }

        int idObservacion = ObservacionDAO.insertarObservacion(texto);

        if (idObservacion > 0) {
            boolean exito = false;

            
            if (entregaVisual.getTipo().equals("documento")) {
                exito = ObservacionDAO.actualizarEntregaDocumento(entregaVisual.
                    getId(), idObservacion);
            } else {
                exito = ObservacionDAO.actualizarEntregaReporte(entregaVisual.
                    getId(), idObservacion);
            }

            if (exito) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                    "Operación exitosa", 
                    "Observación agregada con éxito");
                Navegador.cerrarVentana(txaObservacion);
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                    "Error", 
                    "No se pudo asignar la observación a la entrega.");
            }
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                "Error", 
                "No se pudo guardar la observación.");
        }
    }

    @FXML
    private void clicRegresar(ActionEvent event) {
        Navegador.cerrarVentana(txaObservacion);
    }
    
}