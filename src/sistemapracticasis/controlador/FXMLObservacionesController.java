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
 * Autor: Uriel Cendón
 * Fecha de creación: 15/06/2025
 * Descripción: Controlador de la vista FXMLObservaciones,
 * que permite al profesor asignar una observación en una entrega de un 
 * estudiante.
 */
public class FXMLObservacionesController implements Initializable {

    /* Sección: Declaración de variables
    * Contiene todas las variables de instancia y componentes FXML
    * utilizados en el controlador.
    /** Entrega (documento o reporte) actualmente en selección */
    private EntregaVisual entregaVisual;
    
    /** Área de texto para ingresar o mostrar observaciones */
    @FXML private TextArea txaObservacion; 

    /* Sección: Inicialización del controlador
    * Contiene los métodos relacionados con la configuración inicial
    * del controlador y carga de datos básicos.
    */
    /**
     * Inicializa el controlador después de que su elemento raíz haya sido 
     * procesado.
     * @param url Ubicación utilizada para resolver rutas relativas para el objeto raíz.
     * @param rb Recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    /**
     * Inicializa los datos de la entrega que recibirá la observación.
     * @param entrega Objeto EntregaVisual que contiene los datos de la entrega 
     * a observar.
     */
    public void inicializarDatos(EntregaVisual entrega) {
        this.entregaVisual = entrega;
    }

    /* Sección: Manejo de eventos de UI
    * Contiene los métodos que gestionan las interacciones del usuario
    * con la interfaz gráfica.
    */
    /**
     * Maneja el evento de clic en el botón para enviar la observación.
     * Valida y registra la observación en la base de datos.
     * @param event Evento de acción generado por el clic.
     */
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

    /**
     * Maneja el evento de clic en el botón para regresar.
     * Cierra la ventana actual sin guardar cambios.
     * @param event Evento de acción generado por el clic.
     */
    @FXML
    private void clicRegresar(ActionEvent event) {
        Navegador.cerrarVentana(txaObservacion);
    }
    
}