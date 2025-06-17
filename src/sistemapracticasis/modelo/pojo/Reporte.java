/**
 * Autor: Uriel Cendón
 * Fecha de creación: 14/06/2025
 * Descripción: Clase POJO que representa un reporte dentro del sistema de 
 * prácticas, incluyendo información como el nombre, fecha de entrega, 
 * el documento asociado y el identificador de la entrega relacionada.
 */
package sistemapracticasis.modelo.pojo;

public class Reporte {

    /**
     * Identificador único del reporte.
     */
    private int idReporte;

    /**
     * Nombre del reporte.
     */
    private String nombreReporte;

    /**
     * Fecha de entrega del reporte.
     */
    private String fechaEntregado;

    /**
     * Documento asociado al reporte en formato binario.
     */
    private byte[] documento;

    /**
     * Identificador de la entrega a la que está asociado este reporte.
     */
    private int idEntregaReporte;

    /**
     * Obtiene el identificador del reporte.
     * @return El id del reporte.
     */
    public int getIdReporte() {
        return idReporte;
    }

    /**
     * Establece el identificador del reporte.
     * @param idReporte El id del reporte.
     */
    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    /**
     * Obtiene el nombre del reporte.
     * @return El nombre del reporte.
     */
    public String getNombreReporte() {
        return nombreReporte;
    }

    /**
     * Establece el nombre del reporte.
     * @param nombreReporte El nombre del reporte.
     */
    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }

    /**
     * Obtiene la fecha de entrega del reporte.
     * @return La fecha de entrega del reporte.
     */
    public String getFechaEntregado() {
        return fechaEntregado;
    }

    /**
     * Establece la fecha de entrega del reporte.
     * @param fechaEntregado La fecha de entrega del reporte.
     */
    public void setFechaEntregado(String fechaEntregado) {
        this.fechaEntregado = fechaEntregado;
    }

    /**
     * Obtiene el documento asociado al reporte en formato binario.
     * @return El documento del reporte.
     */
    public byte[] getDocumento() {
        return documento;
    }

    /**
     * Establece el documento asociado al reporte.
     * @param documento El documento del reporte.
     */
    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    /**
     * Obtiene el identificador de la entrega a la que está asociado este 
     * reporte.
     * @return El id de la entrega asociada al reporte.
     */
    public int getIdEntregaReporte() {
        return idEntregaReporte;
    }

    /**
     * Establece el identificador de la entrega asociada al reporte.
     * @param idEntregaReporte El id de la entrega.
     */
    public void setIdEntregaReporte(int idEntregaReporte) {
        this.idEntregaReporte = idEntregaReporte;
    }
}
