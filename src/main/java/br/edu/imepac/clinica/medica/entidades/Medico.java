package br.edu.imepac.clinica.medica.entidades;

public class Medico {

    private int id;
    private String nome;
    private String crm;
    private Especialidade especialidade;
    private int especialidadeId;

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getCrm() {
        return crm;
    }
    public Especialidade getEspecialidade() {
        return especialidade;
    }
    public int getEspecialidadeId() {
        return especialidadeId;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCrm(String crm) {
        this.crm = crm;
    }
    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
    public void setEspecialidadeId(int especialidadeId) {
        this.especialidadeId = especialidadeId;
    }

}
