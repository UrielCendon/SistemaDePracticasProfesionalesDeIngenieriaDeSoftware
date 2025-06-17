package sistemapracticasis.util;

/**
 * Autor: Raziel Filobello
 * Fecha de creación: 15/06/2025
 * Descripción: Utilidad que proporciona métodos relacionados con la evaluación,
 * incluyendo la conversión de texto a calificación numérica para diferentes 
 * niveles de desempeño.
 */
public class EvaluacionUtils {

    /**
     * Convierte un valor textual de evaluación a una calificación numérica.
     * 
     * @param valor El texto de evaluación que representa el desempeño.
     * @return La calificación numérica correspondiente al texto de evaluación,
     *         o 0 si el texto no coincide con los valores esperados.
     */
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