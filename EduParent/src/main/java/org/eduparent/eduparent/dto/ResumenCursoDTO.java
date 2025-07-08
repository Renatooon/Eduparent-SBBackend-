package org.eduparent.eduparent.dto;

public class ResumenCursoDTO {

    private String nombreCurso;
    private String emailProfesor;
    private String nombreProfesor;
    private double promedioNotas;
    private double porcentajeAsistencia;
    private long totalPresentes;
    private long totalFaltas;

    public ResumenCursoDTO() {
    }

    public ResumenCursoDTO(String nombreCurso, String emailProfesor, String nombreProfesor, double promedioNotas,
                           double porcentajeAsistencia, long totalPresentes, long totalFaltas) {
        this.nombreCurso = nombreCurso;
        this.emailProfesor = emailProfesor;
        this.nombreProfesor = nombreProfesor;
        this.promedioNotas = promedioNotas;
        this.porcentajeAsistencia = porcentajeAsistencia;
        this.totalPresentes = totalPresentes;
        this.totalFaltas = totalFaltas;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getEmailProfesor() {
        return emailProfesor;
    }

    public void setEmailProfesor(String emailProfesor) {
        this.emailProfesor = emailProfesor;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public double getPromedioNotas() {
        return promedioNotas;
    }

    public void setPromedioNotas(double promedioNotas) {
        this.promedioNotas = promedioNotas;
    }

    public double getPorcentajeAsistencia() {
        return porcentajeAsistencia;
    }

    public void setPorcentajeAsistencia(double porcentajeAsistencia) {
        this.porcentajeAsistencia = porcentajeAsistencia;
    }

    public long getTotalPresentes() {
        return totalPresentes;
    }

    public void setTotalPresentes(long totalPresentes) {
        this.totalPresentes = totalPresentes;
    }

    public long getTotalFaltas() {
        return totalFaltas;
    }

    public void setTotalFaltas(long totalFaltas) {
        this.totalFaltas = totalFaltas;
    }
}
