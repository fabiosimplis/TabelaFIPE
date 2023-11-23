package br.com.alura.TabelaFIPEAPI.principal;

import br.com.alura.TabelaFIPEAPI.service.ConsumoAPI;

import java.util.Scanner;

public class Principal {

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI request = new ConsumoAPI();

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
        var json = request.obterDados(endereco);
        System.out.println(json);
    }


}
