package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import metodo.ESReal;
import problema.ProblemaRastrigin;
import solucao.Individuo;
import solucao.Populacao;


public class ESRealMain {

    public static void main(String[] args) {

        Double minimo = -5.12;
        Double maximo = 5.12;
        Integer nVariaveis = 100;
        ProblemaRastrigin problema = new ProblemaRastrigin(nVariaveis);

        Integer mu = 20; // Tamanho da populacao
        Integer lambda = 80; // numero de descendentes
        Integer geracoes = 300; // criterio de parada
        Double pMutacao = 0.0;// mutacao - aplicacao ao descendente - variacao/perturbacao

        Populacao pop = null;
        ArrayList<Populacao> populacoes = new ArrayList();
        ArrayList<Individuo> melhorInd = new ArrayList();
        ArrayList<Individuo> piorInd = new ArrayList();
        ArrayList<Double> es1 = new ArrayList();

        Populacao pop2 = null;
        ArrayList<Populacao> populacoes2 = new ArrayList();
        ArrayList<Individuo> melhorInd2 = new ArrayList();
        ArrayList<Individuo> piorInd2 = new ArrayList();
        ArrayList<Double> es2 = new ArrayList();

        int repeticoes = 30;
        long tempos = 0;//tempo medio de execucao
        int cabecalho = 1;
        
        ArrayList<String> nomes = new ArrayList<>(Arrays.asList("ES1", "ES2"));

        for (int i = 1; i <= repeticoes; i++) {
            ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(1,2));
            Collections.shuffle(casos);

            Double mediaES1 = 0.0;
            Double mediaES2 = 0.0;
            Double desvioPadrao = 0.0;

            for (int c = 1; c <= casos.size(); c++) {
                Double result = 0.0;
                long inicio = System.currentTimeMillis();
                ESReal esReal = new ESReal(minimo, maximo, nVariaveis, problema, mu, lambda, geracoes, pMutacao);
                int teste = casos.get(c - 1);

                switch (teste) {
                    case 1:
                        pMutacao = 0.05;
                        
                        pop = esReal.executar();
                        populacoes.add(pop);
                        result = pop.getMelhorIndividuo().getFuncaoObjetivo();
                        mediaES1 = result;
                        desvioPadrao = pop.desvioPadrao();
                        melhorInd.add((Individuo) pop.getIndividuos().get(0));
                        piorInd.add((Individuo) pop.getIndividuos().get(pop.getIndividuos().size() - 1));  
                        
                        es1.add(result);
                        break;

                    case 2:
                        pMutacao = 0.03;
                        
                        pop2 = esReal.executar();
                        populacoes2.add(pop2);
                        result = pop2.getMelhorIndividuo().getFuncaoObjetivo();
                        mediaES2 = result;
                        desvioPadrao = pop2.desvioPadrao();
                        melhorInd2.add((Individuo) pop2.getIndividuos().get(0));
                        piorInd2.add((Individuo) pop2.getIndividuos().get(pop2.getIndividuos().size() - 1));

                        es2.add(result);
                        break;
                }
                if (i != 1) {
                    mediaES1 += mediaES1;
                    mediaES2 += mediaES2;
                }
                
                long fim = System.currentTimeMillis();
                long tempoTotal = System.currentTimeMillis();
                tempoTotal = fim - inicio;
                tempos += tempoTotal;

                //System.out.println(nomes.get(teste - 1) + " -> " + i + "ª Execução" + "\tCusto:" + result + "\tTempo: " + tempoTotal + "ms");
                //System.out.flush();
                esReal.resultados(nomes.get(teste - 1), i, result, tempoTotal, cabecalho);
                cabecalho = -1;
            }

            if (i == repeticoes) {
                Double melhorCusto1 = es1.get(0);
                int melhor1 = 0;
                for (int cont = 0; cont < es1.size(); cont++) {
                    if (es1.get(cont) < melhorCusto1) {
                        melhorCusto1 = es1.get(cont);
                        melhor1 = cont;
                    }
                }
                
                Double piorCusto1 = es1.get(es1.size() - 1);
                int pior1 = 0;
                for (int cont = 0; cont < es1.size(); cont++) {
                    if (es1.get(cont) > piorCusto1) {
                        piorCusto1 = es1.get(cont);
                        pior1 = cont;
                    }
                }
                
                Double melhorCusto2 = es2.get(0);
                int melhor2 = 0;
                for (int cont = 0; cont < es2.size(); cont++) {
                    if (es2.get(cont) < melhorCusto2) {
                        melhorCusto2 = es2.get(cont);
                        melhor2 = cont;
                    }
                }
                
                Double piorCusto2 = es2.get(es2.size() - 1);
                int pior2 = 0;
                for (int cont = 0; cont < es2.size(); cont++) {
                    if (es2.get(cont) > piorCusto2) {
                        piorCusto2 = es2.get(cont);
                        pior2 = cont;
                    }
                }

                System.out.println("\nES1");
                System.out.println("Melhor custo: " + melhorCusto1);
                System.out.println("Melhor indivíduo: " + melhorInd.get(melhor1).getCromossomos().toString());
                System.out.println("Pior custo: " + piorCusto1);
                System.out.println("Pior indivíduo: " + piorInd.get(pior1).getCromossomos().toString());
                System.out.println("Média: " + mediaES1 / repeticoes);
                
                System.out.println("\nES2");
                System.out.println("Melhor custo: " + melhorCusto2);
                System.out.println("Melhor indivíduo: " + melhorInd2.get(melhor2).getCromossomos().toString());
                System.out.println("Pior custo: " + piorCusto2);
                System.out.println("Pior indivíduo: " + piorInd2.get(pior2).getCromossomos().toString());
                System.out.println("Média: " + mediaES2 / repeticoes);
                
                tempos = tempos / (repeticoes * 2);
                System.out.println("\nTempo médio de execução: " + tempos + "ms");
                System.out.println("Desvio padrão: " + desvioPadrao);
            }
        }
        
        
    }

}
