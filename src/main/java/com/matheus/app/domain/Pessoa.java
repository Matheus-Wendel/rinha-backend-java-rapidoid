package com.matheus.app.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.validator.GenericValidator;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.matheus.app.ForceStringDeserializer;

@Entity
public class Pessoa {

    /**
     *
     */
    private static final String FORMATO_DATA = "YYYY-MM-DD";

    @Id
    public UUID id;

    @NotNull
    @Column(nullable = false, unique = true, length = 32)
    @Size(max = 32)
    @JsonDeserialize(using = ForceStringDeserializer.class)
    public String apelido;
    @NotNull
    @Column(nullable = false, length = 100)
    @Size(max = 100)
    @JsonDeserialize(using = ForceStringDeserializer.class)
    public String nome;
    @NotNull
    @JsonDeserialize(using = ForceStringDeserializer.class)
    public String nascimento;
    @NotNull
    @Column(nullable = true, length = 100)
    private String stack;

    public Set<String> getStack() {
        if (stack == null) {
            return null;
        }
        return new HashSet<String>(Arrays.asList(stack.split(",")));

    }

    public void setStack(Set<String> stack) {

        if (stack != null) {
            this.stack = String.join(",", stack);
        }

    }

    public boolean validate() {

        if (!GenericValidator.isDate(nascimento, FORMATO_DATA, true)) {
            return false;
        }
        return true;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getApelido() {
        return this.apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNascimento() {
        return this.nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

}
