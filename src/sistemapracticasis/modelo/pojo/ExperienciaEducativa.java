/**
 * Autor: Uriel Cendón  
 * Fecha de creación: 13/06/2025  
 * Descripción: Clase POJO que representa una experiencia educativa en el 
 * sistema de prácticas profesionales. Contiene información como el NRC, nombre,
 * bloque y sección de la EE.
 */
package sistemapracticasis.modelo.pojo;

/**
 * Representa una experiencia educativa (EE) relacionada con las prácticas 
 * profesionales.
 */
public class ExperienciaEducativa {
    /**
     * Identificador único de la experiencia educativa.
     */
    private int idEE;

    /**
     * Número de Registro de la experiencia educativa.
     */
    private String nrc;

    /**
     * Nombre de la experiencia educativa.
     */
    private String nombre;

    /**
     * Bloque al que pertenece la experiencia educativa.
     */
    private String bloque;

    /**
     * Sección correspondiente de la experiencia educativa.
     */
    private String seccion;

    /**
     * Obtiene el identificador de la experiencia educativa.
     * @return El ID de la EE.
     */
    public int getIdEE() {
        return idEE;
    }

    /**
     * Establece el identificador de la experiencia educativa.
     * @param idEE El ID a asignar.
     */
    public void setIdEE(int idEE) {
        this.idEE = idEE;
    }

    /**
     * Obtiene el NRC (Número de Registro de Curso).
     * @return El NRC de la EE.
     */
    public String getNrc() {
        return nrc;
    }

    /**
     * Establece el NRC de la experiencia educativa.
     * @param nrc El NRC a asignar.
     */
    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    /**
     * Obtiene el nombre de la experiencia educativa.
     * @return El nombre de la EE.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la experiencia educativa.
     * @param nombre El nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el bloque al que pertenece la experiencia educativa.
     * @return El bloque.
     */
    public String getBloque() {
        return bloque;
    }

    /**
     * Establece el bloque de la experiencia educativa.
     * @param bloque El bloque a asignar.
     */
    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    /**
     * Obtiene la sección de la experiencia educativa.
     * @return La sección.
     */
    public String getSeccion() {
        return seccion;
    }

    /**
     * Establece la sección de la experiencia educativa.
     * @param seccion La sección a asignar.
     */
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
}
