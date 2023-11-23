package br.com.alura.TabelaFIPEAPI.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosMarcas(
        String codigo,
        @JsonAlias("nome") String fabricante

) {

    @Override
    public String toString() {
        return "codigo = " + codigo +", fabricante = " + fabricante;
    }
}
