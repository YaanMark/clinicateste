package br.edu.imepac.clinica.medica.entidades;

import java.time.LocalDate;

public class Paciente {
    private int id;
    private static String usuario;
    private String nome;
    private String idade;
    private String sexo;
    private static String cpf;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String contato;
    private String email;
    private LocalDate dataNascimento;

    public int getId() {return id;}
    public static String getUsuario() {return usuario;}
    public String getNome() {return nome;}
    public int getIdade() {return Integer.parseInt(idade);}
    public String getSexo() {return sexo;}
    public static String getCpf() {return cpf;}
    public String getRua() {return rua;}
    public String getNumero() {return numero;}
    public String getComplemento() {return complemento;}
    public String getBairro() {return bairro;}
    public String getCidade() {return cidade;}
    public String getEstado() {return estado;}
    public String getContato() {return contato;}
    public String getEmail() {return email;}
    public LocalDate getDataNascimento() {return dataNascimento;}


    public void setId(int id) {
        this.id = id;
    }
    public void setUsuario(String usuario) {this.usuario = usuario;}
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setIdade(String idade) {this.idade = idade;}
    public void setSexo(String sexo) {this.sexo = sexo;}
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setRua(String rua) {this.rua = rua;}
    public void setNumero(String numero) {this.numero = numero;}
    public void setComplemento(String complemento) {this.complemento = complemento;}
    public void setBairro(String bairro) {this.bairro = bairro;}
    public void setCidade(String cidade) {this.cidade = cidade;}
    public void setEstado(String estado) {this.estado = estado;}
    public void setContato(String contato) {this.contato = contato;}
    public void setEmail(String email) {this.email = email;}
    public void setDataNascimento(LocalDate dataNascimento) {}

}
