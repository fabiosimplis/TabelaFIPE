package br.com.alura.TabelaFIPEAPI.service;

public interface IConverteDados {

    <T> T obterDados(String json, Class<T> classe);
}
