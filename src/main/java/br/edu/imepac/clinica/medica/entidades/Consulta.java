package br.edu.imepac.clinica.medica.entidades;

import java.time.LocalDateTime;

public class Consulta {

    private int id;
    private LocalDateTime data;
    private String sintomas;
    private boolean eRetorno;
    private boolean estaAtiva;
    private int idPaciente;
    private int idMedico;

    public int getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getSintomas() {
        return sintomas;
    }

    public boolean iseRetorno() {
        return eRetorno;
    }

    public boolean isEstaAtiva() {
        return estaAtiva;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public void seteRetorno(boolean eRetorno) {
        this.eRetorno = eRetorno;
    }

    public void setEstaAtiva(boolean estaAtiva) {
        this.estaAtiva = estaAtiva;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }
}