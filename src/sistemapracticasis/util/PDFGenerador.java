/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemapracticasis.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

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
}