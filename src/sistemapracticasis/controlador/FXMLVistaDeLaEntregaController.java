package sistemapracticasis.controlador;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import sistemapracticasis.modelo.dao.DocumentoDAO;
import sistemapracticasis.modelo.dao.EntregaDocumentoDAO;
import sistemapracticasis.modelo.dao.EntregaReporteDAO;
import sistemapracticasis.modelo.dao.ReporteDAO;
import sistemapracticasis.modelo.pojo.EntregaVisual;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.PDFGenerador;
import sistemapracticasis.util.Utilidad;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLVistaDeLaEntregaController implements Initializable {

    private EntregaVisual entregaActual;
    
    @FXML
    private TextField txtNombreDocumento;
    @FXML
    private TextField txtFechaEntregado;
    @FXML
    private TextField txtCalificacion;
    @FXML
    private ImageView imvPDF;
    @FXML
    private Button btnAgregarObservacion;
    @FXML
    private Button btnValidar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnValidar.setDisable(true);
        agregarListenersValidacion();
    }    
    
    public void inicializarDatos(EntregaVisual entregaVisual) {
        this.entregaActual = entregaVisual;
        
        verificarObservacionExistente();

        txtNombreDocumento.setText(entregaVisual.getNombreEntrega());
        txtFechaEntregado.setText("Entregado el: " 
            + entregaVisual.getFechaEntregado());
        if (entregaVisual.getTipo().equals("documento")) {
            byte[] datos = DocumentoDAO.obtenerArchivoPorId
                (entregaVisual.getId());
            mostrarArchivo(datos);
        } else {
            byte[] datos = ReporteDAO.obtenerArchivoPorId
                (entregaVisual.getId());
            mostrarArchivo(datos);
        }
    }
    
    private void verificarObservacionExistente() {
        boolean tieneObservacion = false;

        if (entregaActual.getTipo().equals("documento")) {
            tieneObservacion = DocumentoDAO.tieneObservacion(entregaActual.getId());
        } else {
            tieneObservacion = ReporteDAO.tieneObservacion(entregaActual.getId());
        }

        if (tieneObservacion) {
            btnAgregarObservacion.setDisable(true);
        }
    }
    
    private void mostrarArchivo(byte[] datos) {
        try (PDDocument documento = Loader.loadPDF(datos)) {
            PDFRenderer renderizador = new PDFRenderer(documento);
            BufferedImage imagenPDF = renderizador.renderImageWithDPI(0, 150);
            Image imagenFX = SwingFXUtils.toFXImage(imagenPDF, null);
            imvPDF.setImage(imagenFX);
        } catch (IOException e) {
            e.printStackTrace();
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Error",
                "No se pudo cargar el archivo PDF: " + e.getMessage()
            );
        }
    }


    @FXML
    private void clicAgregarObservacion(ActionEvent event) {
        if (entregaActual != null) {
            Navegador.abrirVentanaModalParametrizada(
                Utilidad.getEscenarioComponente(txtCalificacion),
                "/sistemapracticasis/vista/FXMLObservaciones.fxml",
                FXMLObservacionesController.class,
                "inicializarDatos",
                entregaActual
            );
            
            verificarObservacion();
        }
    }
    
    private void verificarObservacion() {
        boolean tieneObservacion = false;

        if (entregaActual.getTipo().equals("documento")) {
            tieneObservacion = DocumentoDAO.tieneObservacion
                (entregaActual.getId());
        } else if (entregaActual.getTipo().equals("reporte")) {
            tieneObservacion = ReporteDAO.tieneObservacion
                (entregaActual.getId());
        }
        
        btnAgregarObservacion.setDisable(tieneObservacion);
    }

    @FXML
    private void clicValidar(ActionEvent event) {
        if (validarCampos()) {
            try {
                float calificacion = Float.parseFloat(txtCalificacion.
                    getText());
                boolean exito;

                if (entregaActual.getTipo().equals("documento")) {
                    exito = EntregaDocumentoDAO.validarEntregaDocumento
                        (entregaActual.getId(), calificacion);
                } else {
                    exito = EntregaReporteDAO.validarEntregaReporte
                        (entregaActual.getId(), calificacion);
                }
                if (exito) {
                    Utilidad.mostrarAlertaSimple(
                        Alert.AlertType.INFORMATION,
                        "Éxito en la validación",
                        "Se ha guardado la validación."
                    );
                    Navegador.cerrarVentana(btnValidar);
                } else {
                    Utilidad.mostrarAlertaSimple(
                        Alert.AlertType.ERROR,
                        "Error",
                        "No se pudo guardar la evaluación"
                    );
                }
            } catch (NumberFormatException e) {
                Utilidad.mostrarAlertaSimple(
                    Alert.AlertType.ERROR,
                    "Error",
                    "La calificación debe ser un número válido"
                );
            }            
        } else {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.INFORMATION,
                "Datos Inválidos", 
                "Existen campos inválidos, por favor corrija la evaluación"
            );
        }
    }
    
    private boolean validarCampos() {
        boolean camposLlenados = true;

        camposLlenados &= validarCampo(txtCalificacion);
        return camposLlenados;
    }
    
    private boolean validarCampo(TextField campo) {
        try {
            String texto = campo.getText().trim();
            double valor = Double.parseDouble(texto);

            if (valor < 1 || valor > 10) {
                campo.setStyle("-fx-border-color: red;");
                return false;
            } else {
                campo.setStyle("");
                return true;
            }
        } catch (NumberFormatException e) {
            campo.setStyle("-fx-border-color: red;");
            return false;
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
            "Cancelar",
            "Cancelar",
            "¿Está seguro de que quiere cancelar?")) {
                Navegador.cerrarVentana(txtCalificacion);
        }
    }

    @FXML
    private void clicDescargar(ActionEvent event) {
        String directorioDescargas = System.getProperty("user.home") + 
            File.separator + "Downloads";
        
        boolean exito = PDFGenerador.descargarPDF
            (entregaActual, directorioDescargas);
        
        if (exito) {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.INFORMATION,
                "Éxito",
                "Archivo descargado correctamente en: " + directorioDescargas
            );
        } else {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Error",
                "No se pudo descargar el archivo."
            );
        }
    }
    
    private void agregarListenersValidacion() {
        configurarCampoNumerico(txtCalificacion);
        txtCalificacion.textProperty().addListener
            ((obs, oldVal, newVal) -> verificarCamposLlenos());
    }
    
    private void verificarCamposLlenos() {
        boolean todosLlenos =
            !txtCalificacion.getText().trim().isEmpty();

        btnValidar.setDisable(!todosLlenos);
    }
    
    private void configurarCampoNumerico(TextField campo) {
        String regex = "^\\d*\\.?\\d{0,2}$";
    
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            String newText = change.getControlNewText();

            if (newText.isEmpty() || newText.equals(".")) {
                return change;
            }

            if (newText.matches(regex)) {
                return change;
            }
            return null;
        };

        campo.setTextFormatter(new TextFormatter<>(filtro));
    }
}