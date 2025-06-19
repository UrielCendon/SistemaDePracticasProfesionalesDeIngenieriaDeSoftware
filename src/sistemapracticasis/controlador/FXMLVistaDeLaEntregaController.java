package sistemapracticasis.controlador;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import sistemapracticasis.modelo.dao.DocumentoDAO;
import sistemapracticasis.modelo.dao.EntregaDocumentoDAO;
import sistemapracticasis.modelo.dao.EntregaReporteDAO;
import sistemapracticasis.modelo.dao.ReporteDAO;
import sistemapracticasis.modelo.pojo.EntregaVisual;
import sistemapracticasis.util.Navegador;
import sistemapracticasis.util.PDFGenerador;
import sistemapracticasis.util.Utilidad;

/**
 * Autor: Uriel Cendón
 * Fecha de creación: 15/06/2025
 * Descripción: Controlador de la vista FXMLVistaDeLaEntrega,
 * que permite al profesor visualizar la entrega que el estudiante ha mandado, 
 * y darle las opciones de validarla o agregar una observación.
 */
public class FXMLVistaDeLaEntregaController implements Initializable {

    /* Sección: Declaración de variables
     * Contiene todas las variables de instancia y componentes FXML
     * utilizados en el controlador.
     */
    /** Entrega visual (documentos y reportes) que está 
     * siendo gestionada actualmente */
    private EntregaVisual entregaActual;

    /** Callback para notificar a la ventana anterior de una validación exitosa. */
    private Runnable callbackValidacionExitosa;
    
    /** Campo de texto que muestra el nombre del documento */
    @FXML private TextField txtNombreDocumento;

    /** Campo de texto que muestra la fecha de entrega del documento */
    @FXML private TextField txtFechaEntregado;

    /** Campo de texto para ingresar la calificación del documento */
    @FXML private TextField txtCalificacion;

    /** Visor de imágenes para mostrar una vista previa del PDF */
    @FXML private ImageView imvPDF;

    /** Botón para agregar una nueva observación al documento */
    @FXML private Button btnAgregarObservacion;

    /** Botón para validar y guardar la calificación del documento */
    @FXML private Button btnValidar;
    
    /* Sección: Inicialización
     * Métodos relacionados con la inicialización del controlador
     * y carga de datos iniciales.
     */
    /**
     * Inicializa el controlador después de que su elemento raíz haya sido 
     * procesado.
     * @param url Ubicación utilizada para resolver rutas relativas para el 
     * objeto raíz.
     * @param rb Recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnValidar.setDisable(true);
        agregarListenersValidacion();
    }    
    
    /**
     * Inicializa los datos de la entrega y el callback.
     * Acepta un array de objetos para recibir tanto la entrega como la acción
     * a ejecutar después de validar.
     * @param entrega La entrega visual seleccionada.
     * @param callback La acción a ejecutar tras una validación exitosa.
     */
    public void inicializarDatos(EntregaVisual entrega, Runnable callback) {
        this.entregaActual = entrega;
        this.callbackValidacionExitosa = callback;
    
        cargarDatosEnUI();
    }
    
    /* Sección: Manejo de eventos
     * Métodos que gestionan las acciones del usuario en la interfaz.
     */
    /**
     * Maneja el evento de clic en el botón para agregar observaciones.
     * @param event Evento de acción generado por el clic.
     */
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
    
    /**
     * Maneja el evento de clic en el botón para validar la entrega.
     * @param event Evento de acción generado por el clic.
     */
    @FXML
    private void clicValidar(ActionEvent event) {
        if (!validarCampos()) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Datos Inválidos", 
                "Existen campos inválidos, por favor corrija la evaluación.");
            return;
        }

        try {
            float calificacion = Float.parseFloat(txtCalificacion.getText());
            boolean exito;

            if (entregaActual.getTipo().equals("documento")) {
                exito = EntregaDocumentoDAO.validarEntregaDocumento(entregaActual.getId(), 
                    calificacion);
            } else {
                exito = EntregaReporteDAO.validarEntregaReporte(entregaActual.getId(), 
                    calificacion);
            }

            if (exito) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", 
                    "Se ha guardado la validación.");
                
                if (callbackValidacionExitosa != null) {
                    callbackValidacionExitosa.run();
                }
                
                Navegador.cerrarVentana(btnValidar);
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de Guardado", 
                    "No se pudo guardar la evaluación en la base de datos.");
            }
        } catch (NumberFormatException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de Formato", 
                "La calificación debe ser un número válido.");
        }
    }
    
    /**
     * Maneja el evento de clic en el botón para cancelar la acción.
     * @param event Evento de acción generado por el clic.
     */
    @FXML
    private void clicCancelar(ActionEvent event) {
        if (Utilidad.mostrarConfirmacion(
            "Cancelar",
            "Cancelar",
            "¿Está seguro de que quiere cancelar?")) {
                Navegador.cerrarVentana(txtCalificacion);
        }
    }

    /**
     * Maneja el evento de clic en el botón para descargar el pdf.
     * @param event Evento de acción generado por el clic.
     */
    @FXML
    private void clicDescargar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Archivo PDF");
        
        String nombreSugerido = entregaActual.getNombreEntrega().replaceAll("[^a-zA-Z0-9.-]", "_")
            + ".pdf";
        fileChooser.setInitialFileName(nombreSugerido);
        
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos PDF "
            + "(*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage escena = Utilidad.getEscenarioComponente(btnValidar);
        File archivoDestino = fileChooser.showSaveDialog(escena);

        if (archivoDestino != null) {
            boolean exito = PDFGenerador.descargarPDFA(entregaActual, archivoDestino);
            
            if (exito) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", 
                    "Archivo guardado correctamente en: " + archivoDestino.getAbsolutePath());
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", 
                    "No se pudo guardar el archivo.");
            }
        }
    }
    
    /* Sección: Lógica de observaciones
     * Métodos que gestionan las observaciones de las entregas.
     */
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
    
    /* Sección: Manejo de datos en la interfaz
     * Métodos relacionados con la visualización y manejo de archivos PDF.
     */
    private void cargarDatosEnUI() {
        verificarObservacion();
        txtNombreDocumento.setText(entregaActual.getNombreEntrega());
        txtFechaEntregado.setText("Entregado el: " + entregaActual.getFechaEntregado());
        
        byte[] datosPDF = null;
        if (entregaActual.getTipo().equals("documento")) {
            datosPDF = DocumentoDAO.obtenerArchivoPorId(entregaActual.getId());
        } else {
            datosPDF = ReporteDAO.obtenerArchivoPorId(entregaActual.getId());
        }
        
        if (datosPDF != null) {
            mostrarArchivo(datosPDF);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Sin Archivo", 
                "No se encontró el archivo PDF para esta entrega.");
        }
    }
    
    /* Sección: Manejo de archivos
     * Métodos relacionados con la visualización y manejo de archivos PDF.
     */
    private void mostrarArchivo(byte[] datos) {
        try (PDDocument documento = Loader.loadPDF(datos)) {
            PDFRenderer renderizador = new PDFRenderer(documento);
            BufferedImage imagenPDF = renderizador.renderImageWithDPI(0, 150);
            Image imagenFX = SwingFXUtils.toFXImage(imagenPDF, null);
            imvPDF.setImage(imagenFX);
        } catch (IOException e) {
            Utilidad.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Error",
                "No se pudo cargar el archivo PDF"
            );
        }
    }
    
    /* Sección: Lógica de validación
     * Métodos que gestionan la validación de campos y datos.
     */
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
    
    /* Sección: Configuración de UI
     * Métodos para configurar componentes de la interfaz de usuario.
     */
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