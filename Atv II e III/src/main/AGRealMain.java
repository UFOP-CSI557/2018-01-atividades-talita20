package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import metodo.AGReal;
import problema.ProblemaAGReal;
import solucao.IndividuoAGReal;
import solucao.PopulacaoAGReal;

public class AGRealMain {

    public static void main(String[] args) {

        Double minimo = -5.12;
        Double maximo = 5.12;
        Integer nVariaveis = 100;
        
        ProblemaAGReal problema = new ProblemaAGReal();
        
        Integer tamanho = 100;
        Integer geracoes = 300;
        Double pCrossover = 0.0;
        Double pMutacao = 0.0;

        PopulacaoAGReal pop = null;
        ArrayList<PopulacaoAGReal> populacoes = new ArrayList();
        ArrayList<IndividuoAGReal> melhorInd = new ArrayList();
        ArrayList<IndividuoAGReal> piorInd = new ArrayList();
        ArrayList<Double> ag1 = new ArrayList();

        PopulacaoAGReal pop2 = null;
        ArrayList<PopulacaoAGReal> populacoes2 = new ArrayList();
        ArrayList<IndividuoAGReal> melhorInd2 = new ArrayList();
        ArrayList<IndividuoAGReal> piorInd2 = new ArrayList();
        ArrayList<Double> ag2 = new ArrayList();

        int repeticoes = 30;
        long tempos = 0;//tempo medio de execucao
        int cabecalho = 1;

        ArrayList<String> nomes = new ArrayList<>(Arrays.asList("AG1", "AG2"));
        for (int i = 1; i <= repeticoes; i++) {
            ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(1, 2));
            Collections.shuffle(casos);

            Double mediaTeste1 = 0.0;
            Double mediaTeste2 = 0.0;
            Double desvioPadrao = 0.0;

            for (int c = 1; c <= casos.size(); c++) {
                AGReal algoritmo = new AGReal(tamanho, pCrossover, pMutacao, geracoes, problema, minimo, maximo, nVariaveis);

                Double result = 0.0;
                long inicio = System.currentTimeMillis();

                int teste = casos.get(c - 1);

                switch (teste) {

                    case 1:
                        pCrossover = 0.009;
                        pMutacao = 0.05;

                        pop = algoritmo.executar();
                        populacoes.add(pop);//array de individuos
                        result = pop.getIndividuos().get(0).getFuncaoObjetivo();//custo                        
                        mediaTeste1 = result;//media
                        desvioPadrao = pop.desvioPadrao();//desvio padrao
                        melhorInd.add(pop.getIndividuos().get(0));//array com os melhores individuos
                        piorInd.add(pop.getIndividuos().get(pop.getIndividuos().size() - 1));                     

                        ag1.add(result);//array de custos
                        break;

                    case 2:
                        pCrossover = 0.01;
                        pMutacao = 0.03;

                        pop2 = algoritmo.executar();
                        populacoes.add(pop2);
                        result = pop2.getIndividuos().get(0).getFuncaoObjetivo();
                        mediaTeste2 = result;
                        desvioPadrao = pop2.desvioPadrao();
                        melhorInd2.add(pop2.getIndividuos().get(0));
                        piorInd2.add(pop2.getIndividuos().get(pop2.getIndividuos().size() - 1));                      

                        ag2.add(result);
                        break;

                }
                if (i != 1) {
                    mediaTeste1 += mediaTeste1;
                    mediaTeste2 += mediaTeste2;
                }

                long fim = System.currentTimeMillis();
                long tempoTotal = System.currentTimeMillis();
                tempoTotal = fim - inicio;
                tempos += tempoTotal;

                //System.out.println(nomes.get(teste - 1) + " -> " + i + "ª Execução" + "\tCusto:" + result + "\tTempo: " + tempoTotal + "ms");
                //System.out.flush();
                algoritmo.resultados(nomes.get(teste - 1), i, result, tempoTotal, cabecalho);                
                cabecalho = -1;
            }

            if (i == repeticoes) {
                Double melhorCusto1 = ag1.get(0);
                int melhor1 = 0;
                for (int cont = 0; cont < ag1.size(); cont++) {
                    if (ag1.get(cont) < melhorCusto1) {
                        melhorCusto1 = ag1.get(cont);
                        melhor1 = cont;
                    }
                }
                
                Double piorCusto1 = ag1.get(ag1.size() - 1);
                int pior1 = 0;
                for (int cont = 0; cont < ag1.size(); cont++) {
                    if (ag1.get(cont) > piorCusto1) {
                        piorCusto1 = ag1.get(cont);
                        pior1 = cont;
                    }
                }

                Double melhorCusto2 = ag2.get(0);
                int melhor2 = 0;
                for (int cont = 0; cont < ag2.size(); cont++) {
                    if (ag2.get(cont) < melhorCusto2) {
                        melhorCusto2 = ag2.get(cont);
                        melhor2 = cont;
                    }
                }
                
                Double piorCusto2 = ag2.get(ag2.size() - 1);
                int pior2 = 0;
                for (int cont = 0; cont < ag2.size(); cont++) {
                    if (ag2.get(cont) > piorCusto2) {
                        piorCusto2 = ag2.get(cont);
                        pior2 = cont;
                    }
                }

                System.out.println("\nTeste1");
                System.out.println("Melhor custo: " + melhorCusto1);
                System.out.println("Melhor indivíduo: " + melhorInd.get(melhor1).getVariaveis().toString());
                System.out.println("Pior custo: " + piorCusto1);
                System.out.println("Pior indivíduo: " + piorInd.get(pior1).getVariaveis().toString());
                System.out.println("Média: " + mediaTeste1 / repeticoes);

                System.out.println("\nTeste2");
                System.out.println("Melhor custo: " + melhorCusto2);
                System.out.println("Melhor indivíduo: " + melhorInd2.get(melhor2).getVariaveis().toString());
                System.out.println("Pior custo: " + piorCusto2);
                System.out.println("Pior indivíduo: " + piorInd2.get(pior2).getVariaveis().toString());
                System.out.println("Média: " + mediaTeste2 / repeticoes);

                tempos = tempos / (repeticoes * 2);
                System.out.println("\nTempo médio de execução: " + tempos + "ms");
                System.out.println("Desvio padrão: " + desvioPadrao);

            }

        }

    }
    
}
