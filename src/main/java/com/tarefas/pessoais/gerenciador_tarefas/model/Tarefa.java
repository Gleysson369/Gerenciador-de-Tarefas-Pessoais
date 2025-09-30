package com.tarefas.pessoais.gerenciador_tarefas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título da tarefa não pode ser vazio.")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória e não pode ser vazia.")
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres.")
    private String descricao;

    private boolean concluida = false;
    private String categoria;

    public Tarefa() {
    }

    public Tarefa(String titulo, String descricao, String categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.concluida = false;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}