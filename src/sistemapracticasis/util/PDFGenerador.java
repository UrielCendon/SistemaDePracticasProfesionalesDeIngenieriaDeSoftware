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
 *
 * @author Kaiser
 */
public class PDFGenerador {

    public static boolean generarOficio(EstudianteAsignado est, String rutaDirectorio) {
        PDDocument documento = new PDDocument();
        PDPage pagina = new PDPage(PDRectangle.LETTER);
        documento.addPage(pagina);

        try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
            contenido.setLeading(18f);
            contenido.beginText();

            contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
            contenido.newLineAtOffset(50, 700);
            contenido.showText("OFICIO DE ASIGNACIÓN DE PROYECTO");
            contenido.newLine();
            contenido.newLine();

            contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contenido.showText("Por medio del presente se hace constar que el/la estudiante:");
            contenido.newLine();
            contenido.showText("Nombre: " + est.getNombreEstudiante());
            contenido.newLine();
            contenido.showText("Matrícula: " + est.getMatricula());
            contenido.newLine();
            contenido.newLine();
            contenido.showText("Ha sido asignado(a) al proyecto: " + est.getNombreProyecto());
            contenido.newLine();
            contenido.showText("Organización Vinculada: " + est.getRazonSocialOrganizacion());
            contenido.newLine();
            contenido.newLine();
            contenido.showText("Fecha de generación: " + LocalDate.now());

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
    
    private static String generarNombreArchivo(EntregaVisual entrega) {
        String tipo = entrega.getTipo().equals("documento") ? "Documento" : 
            "Reporte";
        return String.format("%s_%s_%d.pdf", 
            tipo, 
            entrega.getNombreEntrega().replaceAll("[^a-zA-Z0-9]", "_"),
            System.currentTimeMillis());
    }
}