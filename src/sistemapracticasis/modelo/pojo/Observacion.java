/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemapracticasis.modelo.pojo;

/**
 *
 * @author uriel
 */
public class Observacion {
    private int idObservacion;
    private String descripcion;
    private String fechaObservacion;

    public Observacion(int idObservacion, String descripcion, String fechaObservacion) {
        this.idObservacion = idObservacion;
        this.descripcion = descripcion;
        this.fechaObservacion = fechaObservacion;
    }

    public int getIdObservacion() {
        return idObservacion;
    }

    public void setIdObservacion(int idObservacion) {
        this.idObservacion = idObservacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaObservacion() {
        return fechaObservacion;
    }

    public void setFechaObservacion(String fechaObservacion) {
        this.fechaObservacion = fechaObservacion;
    }
}
