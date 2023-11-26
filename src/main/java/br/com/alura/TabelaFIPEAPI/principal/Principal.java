package br.com.alura.TabelaFIPEAPI.principal;

import br.com.alura.TabelaFIPEAPI.model.Dados;
import br.com.alura.TabelaFIPEAPI.model.Modelos;
import br.com.alura.TabelaFIPEAPI.model.Veiculo;
import br.com.alura.TabelaFIPEAPI.service.ConsumoAPI;
import br.com.alura.TabelaFIPEAPI.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI request = new ConsumoAPI();
    private ConverteDados converteDados = new ConverteDados();

    public void exibeMenu(){
        String endereco;
        var veiculo = "";
        var menu = """
                \n*** OPÇÕES ***
                                
                1 - Carro
                2 - Moto
                3 - Caminhão
                
                Digite uma das opções de veículo que deseja consultar?
                """;
        System.out.println(menu);

        var opcao = leitura.nextInt();

        switch (opcao){
            case 1:
                veiculo = "carros/marcas";
                break;
            case 2:
                veiculo = "motos/marcas";
                break;
            case 3:
                veiculo = "caminhoes/marcas";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + opcao);
        }

        endereco = URL_BASE + veiculo;
        var codigo = getCodigoFabricante(endereco);

        endereco = endereco + "/" + codigo + "/modelos";

        var listaModelos = getModelos(endereco);

        System.out.println("\nDigite um trecho do nome do carro a ser buscado:");
        var nomeVeiculo = leitura.next();

        List<Dados> modelosFiltrados = listaModelos.modelos().stream()
                .filter(modelo -> modelo.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("Modelos Filtrados");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("\nDigite por favor o código do modelo, para fazer a busca por ano");
        var codigoModelo = leitura.next();

        endereco = endereco + "/" + codigoModelo + "/anos";
        getCarroPorAno(endereco);
    }

    public void getCarroPorAno(String endereco){

        var json = request.obterDados(endereco);

        List<Dados> anos = converteDados.obterListaDeDados(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++){
            json = request.obterDados(endereco +"/"+ anos.get(i).codigo());
            Veiculo veiculo = converteDados.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veículos filtrados");
        veiculos.forEach(System.out::println);


    }

    public String getCodigoFabricante(String endereco){

        var json = request.obterDados(endereco);

        List<Dados> marcas = converteDados.obterListaDeDados(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);

        System.out.println("\nQual código do fabricante deseja verificar?");
        var codigo = leitura.next();
        return codigo;
    }

    public Modelos getModelos(String endereco){

        var json = request.obterDados(endereco);
        //System.out.println(json);
        var listaModelos = converteDados.obterDados(json, Modelos.class);
        listaModelos.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);


        return listaModelos;
    }


}
