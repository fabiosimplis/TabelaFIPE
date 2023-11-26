package br.com.alura.TabelaFIPEAPI.model;

public record Dados(String codigo, String nome) {

    @Override
    public String toString() {
        return codigo + ", nome ='" + nome;
    }
}
