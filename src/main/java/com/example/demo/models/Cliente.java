package com.example.demo.models;

public class Cliente {
    private int id;
    private String documento;
    private String nome;
    private String rg;
    private String email;
    private String telefone;



    public Cliente() {
    }

    public Cliente(int id, String documento, String nome, String rg, String email, String telefone) {
        this.id = id;
        this.documento = documento;
        this.nome = nome;
        this.rg = rg;
        this.email = email;
        this.telefone = telefone;

    }



    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
