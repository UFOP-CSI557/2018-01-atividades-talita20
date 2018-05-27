package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import metodo.DEReal;
import problema.ProblemaRastrigin;
import solucao.Individuo;
import solucao.Populacao;

public class DERealMain {

    public static void main(String[] args) {
        
        Double minimo = -5.12;
        Double maximo = 5.12;
        
        int D = 100;
        ProblemaRastrigin problema = new ProblemaRastrigin(D);
        
        int gmax = 300;
        int Np = 20;
        double F = 0.015;
        double Cr = 0.9;

        Populacao pop = null;
        ArrayList<Populacao> populacoes = new ArrayList();
        ArrayList<Individuo> melhorInd = new ArrayList();
        ArrayList<Individuo> piorInd = new ArrayList();
        ArrayList<Double> de1 = new ArrayList();

        Populacao pop2 = null;
        ArrayList<Populacao> populacoes2 = new ArrayList();
        ArrayList<Individuo> melhorInd2 = new ArrayList();
        ArrayList<Individuo> piorInd2 = new ArrayList();
        ArrayList<Double> de2 = new ArrayList();

        int repeticoes = 30;
        long tempos = 0;//tempo medio de execucao
        int cabecalho = 1;
        
        ArrayList<String> nomes = new ArrayList<>(Arrays.asList("DE1", "DE2"));

        for (int i = 1; i <= repeticoes; i++) {
            ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(1,2));
            Collections.shuffle(casos);

            Double mediaDE1 = 0.0;
            Double mediaDE2 = 0.0;
            Double desvioPadrao = 0.0;

            for (int c = 1; c <= casos.size(); c++) {
                Double result = 0.0;
                long inicio = System.currentTimeMillis();
                DEReal deReal = new DEReal(minimo, maximo, problema, gmax, D, Np, F, Cr);
                int teste = casos.get(c - 1);

                switch (teste) {
                    case 1:
                        Np = 10;
                        F = 0.005;
                        Cr = 0.7;
                        
                        pop = deReal.executar();
                        populacoes.add(pop);
                        result = pop.getMelhorIndividuo().getFuncaoObjetivo();
                        mediaDE1 = result;
                        desvioPadrao = pop.desvioPadrao();
                        melhorInd.add((Individuo) pop.getIndividuos().get(0));
                        piorInd.add((Individuo) pop.getIndividuos().get(pop.getIndividuos().size() - 1));

                        de1.add(result);
                        break;

                    case 2:
                        Np = 20;
                        F = 0.009;
                        Cr = 1.0;
                        
                        pop2 = deReal.executar();
                        populacoes2.add(pop2);
                        result = pop2.getMelhorIndividuo().getFuncaoObjetivo();
                        mediaDE2 = result;
                        desvioPadrao = pop2.desvioPadrao();
                        melhorInd2.add((Individuo) pop2.getIndividuos().get(0));
                        piorInd2.add((Individuo) pop2.getIndividuos().get(pop2.getIndividuos().size() - 1));

                        de2.add(result);
                        break;
                }
                if (i != 1) {
                    mediaDE1 += mediaDE1;
                    mediaDE2 += mediaDE2;
                }

                long fim = System.currentTimeMillis();
                long tempoTotal = System.currentTimeMillis();
                tempoTotal = fim - inicio;
                tempos += tempoTotal;

                //System.out.println(nomes.get(teste - 1) + " -> " + i + "ª Execução" + "\tCusto:" + result + "\tTempo: " + tempoTotal + "ms");
                //System.out.flush();
                deReal.resultados(nomes.get(teste - 1), i, result, tempoTotal, cabecalho);
                cabecalho = -1;
            }

            if (i == repeticoes) {
                Double melhorCusto1 = de1.get(0);
                int melhor1 = 0;
                for (int cont = 0; cont < de1.size(); cont++) {
                    if (de1.get(cont) < melhorCusto1) {
                        melhorCusto1 = de1.get(cont);
                        melhor1 = cont;
                    }
                }
                
                Double piorCusto1 = de1.get(de1.size() - 1);
                int pior1 = 0;
                for (int cont = 0; cont < de1.size(); cont++) {
                    if (de1.get(cont) > piorCusto1) {
                        piorCusto1 = de1.get(cont);
                        pior1 = cont;
                    }
                }

                Double melhorCusto2 = de2.get(0);
                int melhor2 = 0;
                for (int cont = 0; cont < de2.size(); cont++) {
                    if (de2.get(cont) < melhorCusto2) {
                        melhorCusto2 = de2.get(cont);
                        melhor2 = cont;
                    }
                }
                
                Double piorCusto2 = de2.get(de2.size() - 1);
                int pior2 = 0;
                for (int cont = 0; cont < de2.size(); cont++) {
                    if (de2.get(cont) > piorCusto2) {
                        piorCusto2 = de2.get(cont);
                        pior2 = cont;
                    }
                }

                System.out.println("\nDE1");
                System.out.println("Melhor custo: " + melhorCusto1);
                System.out.println("Melhor indivíduo: " + melhorInd.get(melhor1).getCromossomos().toString());
                System.out.println("Pior custo: " + piorCusto1);
                System.out.println("Pior indivíduo: " + piorInd.get(pior1).getCromossomos().toString());
                System.out.println("Média: " + mediaDE1 / repeticoes);

                System.out.println("\nDE2");
                System.out.println("Melhor custo: " + melhorCusto2);
                System.out.println("Melhor indivíduo: " + melhorInd2.get(melhor2).getCromossomos().toString());
                System.out.println("Pior custo: " + piorCusto2);
                System.out.println("Pior indivíduo: " + piorInd2.get(pior2).getCromossomos().toString());
                System.out.println("Média: " + mediaDE2 / repeticoes);

                tempos = tempos / (repeticoes * 2);
                System.out.println("\nTempo médio de execução: " + tempos + "ms");
                System.out.println("Desvio padrão: " + desvioPadrao);
            }
        }

        
    }
    
}
