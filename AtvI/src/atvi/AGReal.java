package atvi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AGReal {

    public static void main(String[] args) {

        Problema problema = new Problema();

        Integer tamanho = 100;
        Double minimo = -5.12;
        Double maximo = 5.12;
        Integer nVariaveis = 100;
        Double pCrossover = 0.0;
        Double pMutacao = 0.0;
        Integer geracoes = 300;

        Double mediaTeste1 = 0.0;
        Double mediaTeste2 = 0.0;

        Populacao pop = null;
        ArrayList<Populacao> populacoes = new ArrayList();
        ArrayList<Individuo> melhorInd = new ArrayList();
        ArrayList<Individuo> piorInd = new ArrayList();

        int repeticoes = 30;

        long tempos = 0;

        ArrayList<String> nomes = new ArrayList<>(Arrays.asList("1º teste", "2º teste"));

        for (int i = 1; i <= repeticoes; i++) {
            ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(1,2));
            Collections.shuffle(casos);
            AlgoritmoGenetico algoritmo = new AlgoritmoGenetico(tamanho, pCrossover, pMutacao, geracoes, problema, minimo, maximo, nVariaveis);

            for (int c = 1; c <= casos.size(); c++) {
                Double result = 0.0;
                long inicio = System.currentTimeMillis();
                int teste = casos.get(c - 1);

                switch (teste) {
                    case 1:
                        pCrossover = 0.01;
                        pMutacao = 0.05;

                        pop = algoritmo.executar();
                        populacoes.add(pop);
                        result = pop.getIndividuos().get(0).getFuncaoObjetivo();
                        mediaTeste1 += result;
                        melhorInd.add(pop.getIndividuos().get(0));
                        piorInd.add(pop.getIndividuos().get(pop.getIndividuos().size() - 1));
                        break;

                    case 2:
                        pCrossover = 0.009;
                        pMutacao = 0.03;

                        pop = algoritmo.executar();
                        populacoes.add(pop);
                        result = pop.getIndividuos().get(0).getFuncaoObjetivo();
                        mediaTeste2 += result;
                        melhorInd.add(pop.getIndividuos().get(0));
                        piorInd.add(pop.getIndividuos().get(pop.getIndividuos().size() - 1));
                        break;
                }
                long fim = System.currentTimeMillis();
                long tempoTotal = System.currentTimeMillis();
                tempoTotal = fim - inicio;
                tempos += tempoTotal;

                System.out.println(nomes.get(teste - 1) + " -> " + i + "ª Execução" + "\t\tCusto:" + result + "\tTempo: " + tempoTotal + "ms\n");
                System.out.flush();

            }
            if (i == repeticoes) {
                System.out.println("Melhor indivíduo: " + melhorInd.get(0).getVariaveis().toString());
                System.out.println("Pior indivíduo: " + piorInd.get(piorInd.size() - 1).getVariaveis().toString());
                System.out.println("Média 1º Teste: " + mediaTeste1 / repeticoes);
                System.out.println("Média 2º Teste: " + mediaTeste2 / repeticoes);
                System.out.println("Desvio Padrão: ");
            }
        }
        tempos = tempos / (repeticoes * 2);
        System.out.println("Tempo médio de execução: " + tempos + "ms");
    }

}
