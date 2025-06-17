package sistemapracticasis.modelo.pojo;

/**
 * Autor: Uriel Cendón
 * Fecha de creación: 13/06/2025
 * Descripción: Clase que representa un documento asociado a una entrega de 
 * documento, con detalles sobre su nombre, fecha de entrega, tipo y el 
 * contenido binario del documento.
 */
public class Documento {
    
    /**
     * Identificador único del documento.
     */
    private int idDocumento;
    
    /**
     * Nombre del documento.
     */
    private String nombreDocumento;
    
    /**
     * Fecha en que el documento fue entregado.
     */
    private String fechaEntregado;
    
    /**
     * Contenido binario del documento (en formato byte[]).
     */
    private byte[] documento;
    
    /**
     * Identificador de la entrega de documento asociada a este documento.
     */
    private int idEntregaDocumento;
    
    /**
     * Tipo de documento (relacionado a la enumeración TipoDocumento).
     */
    private TipoDocumento tipo;

    /**
     * Obtiene el identificador único del documento.
     * 
     * @return El identificador del documento.
     */
    public int getIdDocumento() {
        return idDocumento;
    }

    /**
     * Establece el identificador único del documento.
     * 
     * @param idDocumento El identificador del documento.
     */
    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    /**
     * Obtiene el nombre del documento.
     * 
     * @return El nombre del documento.
     */
    public String getNombreDocumento() {
        return nombreDocumento;
    }

    /**
     * Establece el nombre del documento.
     * 
     * @param nombreDocumento El nombre del documento.
     */
    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    /**
     * Obtiene la fecha en que el documento fue entregado.
     * 
     * @return La fecha de entrega del documento.
     */
    public String getFechaEntregado() {
        return fechaEntregado;
    }

    /**
     * Establece la fecha en que el documento fue entregado.
     * 
     * @param fechaEntregado La fecha de entrega del documento.
     */
    public void setFechaEntregado(String fechaEntregado) {
        this.fechaEntregado = fechaEntregado;
    }

    /**
     * Obtiene el contenido binario del documento.
     * 
     * @return El contenido binario del documento.
     */
    public byte[] getDocumento() {
        return documento;
    }

    /**
     * Establece el contenido binario del documento.
     * 
     * @param documento El contenido binario del documento.
     */
    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    /**
     * Obtiene el identificador de la entrega de documento asociada a este 
     * documento.
     * 
     * @return El identificador de la entrega de documento.
     */
    public int getIdEntregaDocumento() {
        return idEntregaDocumento;
    }

    /**
     * Establece el identificador de la entrega de documento asociada a este 
     * documento.
     * 
     * @param idEntregaDocumento El identificador de la entrega de documento.
     */
    public void setIdEntregaDocumento(int idEntregaDocumento) {
        this.idEntregaDocumento = idEntregaDocumento;
    }

    /**
     * Obtiene el tipo de documento.
     * 
     * @return El tipo de documento.
     */
    public TipoDocumento getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de documento.
     * 
     * @param tipo El tipo de documento.
     */
    public void setTipo(TipoDocumento tipo) {
        this.tipo = tipo;
    }
}
