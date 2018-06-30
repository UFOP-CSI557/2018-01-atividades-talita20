package prova.talita;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AGReal {

    public static void main(String[] args) {

        Problema problema = new Problema();

        Double minimo = -500.00;
        Double maximo = 500.00;
        Integer nVariaveis = 50;
        Integer tamanho = 100;
        Integer geracoes = 300;
        Double pCrossover = 0.009;
        Double pMutacao = 0.05;
        Integer mu = 100;
        Integer lambda = 1000; 

        Populacao pop = null;

        int repeticoes = 30;
        int cabecalho = 1;

        System.out.println("NumeroDaExecucao;ResultadoDaF0;TempoDeExecucaoEmMilissegundos");
        for (int i = 1; i <= repeticoes; i++) {
            
            AlgoritmoGenetico algoritmo = new AlgoritmoGenetico(minimo, maximo, nVariaveis, problema, mu, lambda, geracoes, pMutacao);

            Double result = 0.0;
            long inicio = System.currentTimeMillis();

            pop = algoritmo.executar();
            result = pop.getIndividuos().get(0).getFuncaoObjetivo();//custo     

            long fim = System.currentTimeMillis();
            long tempoTotal = System.currentTimeMillis();
            tempoTotal = fim - inicio;

            System.out.println(i + ";" + result + ";" + tempoTotal);
            System.out.flush();
            algoritmo.resultados(i, result, tempoTotal, cabecalho);                
            cabecalho = -1;

            cabecalho = -1;
        }
    }

}
