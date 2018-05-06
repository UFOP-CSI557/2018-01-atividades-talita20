package atvi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AGReal {

    public static void main(String[] args) {

        Problema problema = new Problema();

        Double minimo = -5.12;
        Double maximo = 5.12;
        Integer nVariaveis = 100;
        Integer tamanho = 100;
        Integer geracoes = 300;
        Double pCrossover = 0.0;
        Double pMutacao = 0.0;

        Double mediaTeste1 = 0.0;
        Double mediaTeste2 = 0.0;
        Double desvioPadrao = 0.0;

        Populacao pop = null;
        ArrayList<Populacao> populacoes = new ArrayList();
        ArrayList<Individuo> melhorInd = new ArrayList();
        ArrayList<Individuo> piorInd = new ArrayList();
        ArrayList<Double> teste1 = new ArrayList();

        Populacao pop2 = null;
        ArrayList<Populacao> populacoes2 = new ArrayList();
        ArrayList<Individuo> melhorInd2 = new ArrayList();
        ArrayList<Individuo> piorInd2 = new ArrayList();
        ArrayList<Double> teste2 = new ArrayList();

        int repeticoes = 30;
        long tempos = 0;//tempo medio de execucao
        int cabecalho = 1;

        ArrayList<String> nomes = new ArrayList<>(Arrays.asList("Teste1", "Teste2"));
        for (int i = 1; i <= repeticoes; i++) {
            ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(1, 2));
            Collections.shuffle(casos);

            for (int c = 1; c <= casos.size(); c++) {
                AlgoritmoGenetico algoritmo = new AlgoritmoGenetico(tamanho, pCrossover, pMutacao, geracoes, problema, minimo, maximo, nVariaveis);

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
                        mediaTeste1 += result;//media
                        desvioPadrao = pop.desvioPadrao();//desvio padrao
                        melhorInd.add(pop.getIndividuos().get(0));//array com os melhores individuos
                        piorInd.add(pop.getIndividuos().get(pop.getIndividuos().size() - 1));//array com os piores individuos                      

                        teste1.add(result);//array de custos
                        break;

                    case 2:
                        pCrossover = 0.01;
                        pMutacao = 0.03;

                        pop2 = algoritmo.executar();
                        populacoes.add(pop2);
                        result = pop2.getIndividuos().get(0).getFuncaoObjetivo();
                        mediaTeste2 += result;
                        desvioPadrao = pop2.desvioPadrao();
                        melhorInd2.add(pop2.getIndividuos().get(0));
                        piorInd2.add(pop2.getIndividuos().get(pop2.getIndividuos().size() - 1));

                        teste2.add(result);
                        break;

                }

                long fim = System.currentTimeMillis();
                long tempoTotal = System.currentTimeMillis();
                tempoTotal = fim - inicio;
                tempos += tempoTotal;

                /*if (teste == 1) {
                    System.out.println(nomes.get(teste - 1) + " -> " + i + "ª Execução" + "\tCusto:" + result + "\tTempo: " + tempoTotal + "\n");
                } else {
                    System.out.println(nomes.get(teste - 1) + " -> " + i + "ª Execução" + "\tCusto:" + result + "\tTempo: " + tempoTotal + "\n");
                }*/
                algoritmo.resultados(nomes.get(teste - 1), i, result, tempoTotal, cabecalho);//passa os resultados pro CSV

                System.out.flush();

                cabecalho = -1;
            }

            if (i == repeticoes) {
                //Seleciona o melhor custo e exibe o melhor individuo da populacao
                Double melhorCusto1 = teste1.get(0);
                int melhor1 = 0;
                for (int cont = 0; cont < teste1.size(); cont++) {
                    if (teste1.get(cont) < melhorCusto1) {
                        melhorCusto1 = teste1.get(cont);
                        melhor1 = cont;
                    }
                }

                //Seleciona o pior custo e exibe o pior individuo da populacao
                Double piorCusto1 = teste1.get(teste1.size() - 1);
                int pior1 = 0;
                for (int cont = 0; cont < teste1.size(); cont++) {
                    if (teste1.get(cont) > piorCusto1) {
                        piorCusto1 = teste1.get(cont);
                        pior1 = cont;
                    }
                }

                //Seleciona o melhor custo e exibe o melhor individuo da populacao
                Double melhorCusto2 = teste2.get(0);
                int melhor2 = 0;
                for (int cont = 0; cont < teste1.size(); cont++) {
                    if (teste2.get(cont) < melhorCusto2) {
                        melhorCusto2 = teste1.get(cont);
                        melhor2 = cont;
                    }
                }

                //Seleciona o pior custo e exibe o pior individuo da populacao
                Double piorCusto2 = teste2.get(teste2.size() - 1);
                int pior2 = 0;
                for (int cont = 0; cont < teste1.size(); cont++) {
                    if (teste2.get(cont) > piorCusto2) {
                        piorCusto2 = teste1.get(cont);
                        pior2 = cont;
                    }
                }

                System.out.println("Teste1");
                System.out.println("Melhor indivíduo: " + melhorInd.get(melhor1).getVariaveis().toString());
                System.out.println("Pior indivíduo: " + piorInd.get(pior1).getVariaveis().toString());
                System.out.println("Média: " + mediaTeste1 / repeticoes);

                System.out.println("\nTeste2");
                System.out.println("Melhor indivíduo: " + melhorInd2.get(melhor2).getVariaveis().toString());
                System.out.println("Pior indivíduo: " + piorInd2.get(pior2).getVariaveis().toString());
                System.out.println("Média: " + mediaTeste2 / repeticoes);

                tempos = tempos / (repeticoes * 2);
                System.out.println("\nTempo médio de execução: " + tempos + "ms");
                System.out.println("Desvio padrão: " + desvioPadrao);
            }

        }

    }

}
