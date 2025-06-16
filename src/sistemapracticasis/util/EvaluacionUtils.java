/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemapracticasis.util;

/**
 *
 * @author Kaiser
 */
public class EvaluacionUtils {

    public static int convertirTextoACalificacion(String valor) {
        switch (valor) {
            case "Excelente": return 10;
            case "Muy bien": return 9;
            case "Bien": return 8;
            case "Regular": return 7;
            case "Insuficiente": return 5;
            default: return 0;
        }
    }
}