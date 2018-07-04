package problemadamochila;

import java.util.ArrayList;

public class ProblemaDaMochila {

    public static void main(String[] args) {

        Problema problema = new Problema();

        Integer tamanho = 100;
        Double pCrossover = 0.9;
        Double pMutacao = 0.05;
        Integer geracoes = 100;
        Integer precisao = 1;
        Double minimo = -100.00;
        Double maximo = 100.00;
        Integer nVariaveis = 100;

        ArrayList<Integer> pesos = new ArrayList<>();
        ArrayList<Integer> pesoItens = null;//peso de cada item
        ArrayList<Integer> valores = new ArrayList<>();
        ArrayList<Integer> valorItens = new ArrayList<>();//valor de cada item
        int pesoMinimo = 1;//peso minimo do item
        int pesoMaximo = 15;//peso maximo do item
        int valorMinimo = 1;//valor minimo do item
        int valorMaximo = 5;//valor maximo do item

        int repeticoes = 30;
        long tempos = 0;//tempo medio de execucao
        int cabecalho = 1;

        Populacao pop = null;
        ArrayList<Populacao> populacoes = new ArrayList();
        ArrayList<Double> alg = new ArrayList();
        Double mediaCusto = 0.0;

        for (int i = 1; i <= repeticoes; i++) {

            Individuo ind = new Individuo(geracoes, pesos, pesoMinimo, pesoMaximo, valores, valorMinimo, valorMaximo);
            pesoItens = ind.preeenchePesos(pesos, pesoMinimo, pesoMaximo, geracoes);
            valorItens = ind.preeencheValores(valores, valorMinimo, valorMaximo, geracoes);

            AlgoritmoGenetico ag = new AlgoritmoGenetico(tamanho, pCrossover, pMutacao, geracoes, problema, precisao, minimo, maximo, nVariaveis, pesos, pesoMinimo, pesoMaximo, pesoItens, valores, valorMinimo, valorMaximo, valorItens);

            Double result = 0.0;
            long inicio = System.currentTimeMillis();

            pop = ag.executar(pesoItens, valorItens);
            result = pop.getIndividuos().get(0).getFuncaoObjetivo();
            mediaCusto += result;
            populacoes.add(pop);
            alg.add(result);

            long fim = System.currentTimeMillis();
            long tempoTotal = System.currentTimeMillis();
            tempoTotal = fim - inicio;
            tempos += tempoTotal;

            //System.out.println(i + "ª Execução" + "\tCusto:" + result + "\tTempo: " + tempoTotal + "ms\n");
            //System.out.flush();
            ag.resultados(i, result, tempoTotal, cabecalho);
            cabecalho = -1;

            if (i == repeticoes) {
                Double melhorCusto = alg.get(0);
                int melhor = 0;
                for (int cont = 0; cont < alg.size(); cont++) {
                    if (alg.get(cont) < melhorCusto) {
                        melhorCusto = alg.get(cont);
                        melhor = cont;
                    }
                }
                Double piorCusto = alg.get(alg.size() - 1);
                int pior = 0;
                for (int cont = 0; cont < alg.size(); cont++) {
                    if (alg.get(cont) > piorCusto) {
                        piorCusto = alg.get(cont);
                        pior = cont;
                    }
                }

                System.out.println("Custo mais baixo: " + melhorCusto);
                System.out.println("Custo mais alto: " + piorCusto);
                System.out.println("Média dos custos: " + mediaCusto / repeticoes);
                tempos = tempos / repeticoes;
                System.out.println("Tempo médio de execução: " + tempos + "ms");

            }
        }
    }
}
