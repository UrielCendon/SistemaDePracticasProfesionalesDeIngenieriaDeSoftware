package sistemapracticasis.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import sistemapracticasis.modelo.dao.DocumentoDAO;
import sistemapracticasis.modelo.dao.ReporteDAO;
import sistemapracticasis.modelo.pojo.EntregaVisual;
import sistemapracticasis.modelo.pojo.EstudianteAsignado;

/** 
 * Autor: Raziel Filobello
 * Fecha de creación: 15/06/2025
 * Descripción: Clase encargada de la generación y descarga de archivos PDF,
 * que incluye la creación de un oficio de asignación de proyecto para un 
 * estudiante y la descarga de un PDF relacionado con una entrega visual.
 */
public class PDFGenerador {

    /*
     * Sección: Generación del oficio de asignación
     * Esta sección del código se encarga de generar un oficio en formato PDF
     * para un estudiante, indicando el proyecto al cual ha sido asignado.
     */
    /**
     * Genera un oficio de asignación de proyecto para un estudiante y lo guarda
     * en un archivo PDF en la ruta especificada.
     *
     * @param est El objeto EstudianteAsignado que contiene los datos del 
     *            estudiante para el cual se generará el oficio.
     * @param rutaDirectorio La ruta del directorio donde se guardará el archivo
     *                       PDF.
     * @return true si el oficio fue generado y guardado correctamente, false 
     *         en caso contrario.
     */
    public static boolean generarOficio(EstudianteAsignado est, String 
            rutaDirectorio) {
        PDDocument documento = new PDDocument();
        PDPage pagina = new PDPage(PDRectangle.LETTER);
        documento.addPage(pagina);

        try (PDPageContentStream contenido = new PDPageContentStream(documento, 
                pagina)) {
            contenido.setLeading(18f);
            contenido.beginText();

            contenido.setFont(new PDType1Font(Standard14Fonts.FontName.
                HELVETICA_BOLD), 14);
            contenido.newLineAtOffset(50, 700);
            contenido.showText("OFICIO DE ASIGNACIÓN DE PROYECTO");
            contenido.newLine();
            contenido.newLine();

            contenido.setFont(new PDType1Font(Standard14Fonts.FontName.
                HELVETICA), 12);
            contenido.showText("Por medio del presente se hace constar que "
                + "el/la estudiante:");
            contenido.newLine();
            contenido.showText("Nombre: " + est.getNombreEstudiante());
            contenido.newLine();
            contenido.showText("Matrícula: " + est.getMatricula());
            contenido.newLine();
            contenido.newLine();
            contenido.showText("Ha sido asignado(a) al proyecto: " + est.
                getNombreProyecto());
            contenido.newLine();
            contenido.showText("Organización Vinculada: " + est.
                getRazonSocialOrganizacion());
            contenido.newLine();
            contenido.newLine();
            contenido.showText("Fecha de generación: " + LocalDate.now());

            contenido.endText();
        } catch (IOException e) {
            System.err.println("Error al escribir contenido en PDF: " + e.
                getMessage());
            e.printStackTrace();
            return false;
        }

        try {
            String nombreArchivo = rutaDirectorio + File.separator + est.
                getMatricula() + "_Oficio.pdf";
            documento.save(nombreArchivo);
            documento.close();
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar el PDF: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /*
     * Sección: Descarga de archivo PDF
     * Esta sección del código maneja la descarga de un archivo PDF relacionado 
     * con una entrega visual, ya sea un documento o un reporte, y lo guarda 
     * en una ruta de destino.
     */
    /**
     * Descarga un archivo PDF relacionado con una entrega visual y lo guarda
     * en la ruta de destino especificada.
     *
     * @param entrega El objeto EntregaVisual que contiene los datos de la 
     *                entrega.
     * @param rutaDestino La ruta de destino donde se guardará el archivo PDF.
     * @return true si el PDF fue descargado correctamente, false en caso 
     *         contrario.
     */
    public static boolean descargarPDF(EntregaVisual entrega, 
            String rutaDestino) {
        try {
            byte[] datosPDF = entrega.getTipo().equals("documento") 
                ? DocumentoDAO.obtenerArchivoPorId(entrega.getId())
                : ReporteDAO.obtenerArchivoPorId(entrega.getId());
            
            if (datosPDF == null || datosPDF.length == 0) {
                return false;
            }
            
            File directorio = new File(rutaDestino);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            
            String nombreArchivo = generarNombreArchivo(entrega);
            File archivoDestino = new File(directorio, nombreArchivo);
            
            try (PDDocument documento = Loader.loadPDF(datosPDF);
               FileOutputStream fos = new FileOutputStream(archivoDestino)) {

               documento.save(fos);
               return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /*
     * Sección: Generación del nombre del archivo PDF
     * Esta sección se encarga de generar un nombre único para el archivo PDF
     * basado en los datos de la entrega visual.
     */
    /**
     * Genera un nombre de archivo único para un archivo PDF basado en los 
     * datos de la entrega visual.
     *
     * @param entrega El objeto EntregaVisual que contiene los datos para la 
     *                generación del nombre.
     * @return El nombre del archivo generado, con formato 
     *         "Tipo_NombreEntrega_Timestamp.pdf".
     */
    private static String generarNombreArchivo(EntregaVisual entrega) {
        String tipo = entrega.getTipo().equals("documento") ? "Documento" : 
            "Reporte";
        return String.format("%s_%s_%d.pdf", 
            tipo, 
            entrega.getNombreEntrega().replaceAll("[^a-zA-Z0-9]", "_"),
            System.currentTimeMillis());
    }
}