package br.com.alura.TabelaFIPEAPI.service;

import java.util.List;

public interface IConverteDados {

    <T> T obterDados(String json, Class<T> classe);
    <T> List<T> obterListaDeDados(String json, Class<T> classe);
}
