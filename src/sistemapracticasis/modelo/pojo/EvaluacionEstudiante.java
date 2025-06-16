/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemapracticasis.modelo.pojo;

/**
 *
 * @author Kaiser
 */
public class EvaluacionEstudiante {
    private int usoTecnicas;
    private int requisitos;
    private int seguridad;
    private int contenido;
    private int ortografia;
    private double puntajeTotal;
    private int idExpediente;
    private int idObservacion;

    public int getUsoTecnicas() { return usoTecnicas; }
    public void setUsoTecnicas(int usoTecnicas) { this.usoTecnicas = usoTecnicas; }

    public int getRequisitos() { return requisitos; }
    public void setRequisitos(int requisitos) { this.requisitos = requisitos; }

    public int getSeguridad() { return seguridad; }
    public void setSeguridad(int seguridad) { this.seguridad = seguridad; }

    public int getContenido() { return contenido; }
    public void setContenido(int contenido) { this.contenido = contenido; }

    public int getOrtografia() { return ortografia; }
    public void setOrtografia(int ortografia) { this.ortografia = ortografia; }

    public double getPuntajeTotal() { return puntajeTotal; }
    public void setPuntajeTotal(double puntajeTotal) { this.puntajeTotal = puntajeTotal; }

    public int getIdExpediente() { return idExpediente; }
    public void setIdExpediente(int idExpediente) { this.idExpediente = idExpediente; }

    public int getIdObservacion() { return idObservacion; }
    public void setIdObservacion(int idObservacion) { this.idObservacion = idObservacion; }
}