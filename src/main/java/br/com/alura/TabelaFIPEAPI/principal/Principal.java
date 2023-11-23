package br.com.alura.TabelaFIPEAPI.principal;

import br.com.alura.TabelaFIPEAPI.model.DadosMarcas;
import br.com.alura.TabelaFIPEAPI.model.DadosModelos;
import br.com.alura.TabelaFIPEAPI.model.Modelos;
import br.com.alura.TabelaFIPEAPI.service.ConsumoAPI;
import br.com.alura.TabelaFIPEAPI.service.ConverteDados;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

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
        getModelos(endereco);
    }

    public String getCodigoFabricante(String endereco){

        var json = request.obterDados(endereco);

        List<DadosMarcas> marcas = converteDados.obterListaDeDados(json, DadosMarcas.class);
        marcas.stream()
                .sorted(Comparator.comparing(DadosMarcas::codigo))
                .forEach(System.out::println);

        System.out.println("\nQual código do fabricante deseja verificar?");
        var codigo = leitura.next();
        return codigo;
    }

    public void getModelos(String endereco){

        var json = request.obterDados(endereco);
        //System.out.println(json);
        var listaModelos = converteDados.obterDados(json, Modelos.class);
        listaModelos.modelos().stream()
                .sorted(Comparator.comparing(DadosModelos::modelo))
                .forEach(System.out::println);
    }


}
