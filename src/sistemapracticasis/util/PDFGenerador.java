package sistemapracticasis.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public static boolean generarOficio(EstudianteAsignado est, String rutaDirectorio) {
        PDDocument documento = new PDDocument();
        PDPage pagina = new PDPage(PDRectangle.LETTER);
        documento.addPage(pagina);

        try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
            contenido.setLeading(16f);
            contenido.beginText();

            float marginX = 50;
            float yStart = 700;
            contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contenido.newLineAtOffset(marginX, yStart);

            contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
            contenido.showText("UNIVERSIDAD VERACRUZANA");
            contenido.newLine();
            contenido.showText("FACULTAD DE ESTADÍSTICA E INFORMÁTICA");
            contenido.newLine();
            contenido.showText("LIC. EN INGENIERÍA DE SOFTWARE");
            contenido.newLine();
            contenido.showText("PRÁCTICAS PROFESIONALES");
            contenido.newLine();
            contenido.newLine();
            contenido.newLine();
            contenido.newLine();

            contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            contenido.showText("ASUNTO: ASIGNACIÓN DE PROYECTO");
            contenido.newLine();
            contenido.newLine();

            contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            contenido.showText("C. ESTUDIANTE: " + est.getNombreEstudiante().toUpperCase());
            contenido.newLine();
            contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contenido.showText("PRESENTE:");
            contenido.newLine();
            contenido.newLine();

            contenido.setLeading(18f);
            contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            agregarTextoEnParrafos(contenido, "EL QUE SUSCRIBE, RESPONSABLE DE LA GESTIÓN DE PROYECTOS DE PRÁCTICAS PROFESIONALES DE LA LICENCIATURA EN INGENIERÍA DE SOFTWARE, HACE CONSTAR QUE EL ESTUDIANTE " 
                + est.getNombreEstudiante().toUpperCase() + ", CON MATRÍCULA " + est.getMatricula() + ",", 
                new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12, 500);
            agregarTextoEnParrafos(contenido, "HA SIDO ASIGNADO(A) AL PROYECTO TITULADO \"" + est.getNombreProyecto() + "\", QUE SE DESARROLLARÁ EN LA ORGANIZACIÓN \"" + est.getRazonSocialOrganizacion().toUpperCase() + "\".", 
                new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12, 500);
            contenido.newLine();
            contenido.newLine();
            contenido.showText("SE EXPIDE EL PRESENTE OFICIO PARA LOS FINES QUE CONVENGAN AL INTERESADO.");
            contenido.newLine();
            contenido.newLine();
            contenido.showText("FECHA DE EMISIÓN: " + LocalDate.now());
            contenido.newLine();
            contenido.newLine();
            contenido.newLine();
            contenido.newLine();

            contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            contenido.setLeading(14f);
            contenido.newLine();
            contenido.showText("A T E N T A M E N T E");
            contenido.newLine();
            contenido.newLine();
            contenido.showText("EL COORDINADOR DE PRÁCTICAS PROFESIONALES");
            contenido.newLine();
            contenido.newLine();
            contenido.newLine();
            contenido.showText("____________________________________");
            contenido.newLine();
            contenido.showText("NOMBRE Y FIRMA DEL RESPONSABLE");

            contenido.endText();

        } catch (IOException e) {
            System.err.println("Error al escribir contenido en PDF: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        try {
            String nombreArchivo = rutaDirectorio + File.separator + est.getMatricula() + "_Oficio.pdf";
            documento.save(nombreArchivo);
            documento.close();
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar el PDF: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Ayuda a separar el texto en parrafos para que no se corte en la hoja
     * @param contenido
     * @param texto
     * @param fuente
     * @param tamFuente
     * @param anchoMax
     * @throws IOException 
     */
    private static void agregarTextoEnParrafos(PDPageContentStream contenido, String texto, PDType1Font fuente, float tamFuente, float anchoMax) throws IOException {
        List<String> lineas = new ArrayList<>();
        String[] palabras = texto.split(" ");
        StringBuilder lineaActual = new StringBuilder();

        for (String palabra : palabras) {
            String lineaTentativa = lineaActual.length() == 0 ? palabra : lineaActual + " " + palabra;
            float ancho = fuente.getStringWidth(lineaTentativa) / 1000 * tamFuente;

            if (ancho < anchoMax) {
                lineaActual.append(lineaActual.length() == 0 ? palabra : " " + palabra);
            } else {
                lineas.add(lineaActual.toString());
                lineaActual = new StringBuilder(palabra);
            }
        }

        if (lineaActual.length() > 0) {
            lineas.add(lineaActual.toString());
        }

        for (String linea : lineas) {
            contenido.showText(linea);
            contenido.newLine();
        }
    }
    
    /**
     * Guarda el PDF de una entrega en un archivo específico elegido por el usuario.
     * @param entrega La entrega visual que contiene el tipo y el ID.
     * @param archivoDestino El objeto File donde se guardará el PDF.
     * @return true si se guardó con éxito, false en caso contrario.
     */
    public static boolean descargarPDFA(EntregaVisual entrega, File archivoDestino) {
        byte[] datosPDF = null;
        
        if (entrega.getTipo().equals("documento")) {
            datosPDF = DocumentoDAO.obtenerArchivoPorId(entrega.getId());
        } else if (entrega.getTipo().equals("reporte")) {
            datosPDF = ReporteDAO.obtenerArchivoPorId(entrega.getId());
        }

        if (datosPDF == null) {
            return false;
        }
        
        try (FileOutputStream fos = new FileOutputStream(archivoDestino)) {
            fos.write(datosPDF);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}