package br.com.alura.TabelaFIPEAPI.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record DadosModelos(
        String codigo,
        @JsonAlias("nome") String modelo
)



{
    @Override
    public String toString() {
        return "codigo = " + codigo + ", modelo = " + modelo ;
    }
}
