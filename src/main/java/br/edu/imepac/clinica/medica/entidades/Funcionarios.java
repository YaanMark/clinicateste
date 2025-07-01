package br.edu.imepac.clinica.medica.entidades;

import java.time.LocalDate;

public class Funcionarios {
    private int id;
    private String usuario;
    private String nome;
    private String idade;
    private String sexo;
    private String cpf;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String contato;
    private String email;
    private LocalDate dataNascimento;
    private String senha;
    private int idPerfil;

    public int getId() {
        return id;
    }
    public String getUsuario() {return usuario;}
    public String getNome() {
        return nome;
    }
    public String getIdade() {return idade;}
    public String getSexo() {return sexo;}
    public String getCpf() {return cpf;}
    public String getRua() {return rua;}
    public String getBairro() {return bairro;}
    public String getCidade() {return cidade;}
    public String getEstado() {return estado;}
    public String getContato() {return contato;}
    public String getEmail() {return email;}
    public LocalDate getDataNascimento() {return dataNascimento;}
    public String getSenha() {return senha;}

    public int getIdPerfil() {return idPerfil;}

    public void setId(int id) {
        this.id = id;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setIdade(String idade) {this.idade = idade;}
    public void setSexo(String sexo) {this.sexo = sexo;}
    public void setCpf(String cpf) {this.cpf = cpf;}
    public void setRua(String rua) {this.rua = rua;}
    public void setBairro(String bairro) {this.bairro = bairro;}
    public void setCidade(String cidade) {this.cidade = cidade;}
    public void setEstado(String estado) {this.estado = estado;}
    public void setContato(String contato) {this.contato = contato;}
    public void setEmail(String email) {this.email = email;}
    public void setDataNascimento(LocalDate dataNascimento) {this.dataNascimento = dataNascimento;}
    public void setSenha(char[] password) {
        this.senha = new String(password);
    }
    public void setIdPerfil(int idPerfil) {this.idPerfil = idPerfil;}
    public void setSenha(String senha) {this.senha = senha;}
}