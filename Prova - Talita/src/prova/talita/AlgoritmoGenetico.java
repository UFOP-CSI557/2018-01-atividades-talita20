package prova.talita;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AlgoritmoGenetico {

    Integer tamanho;
    Double pCrossover;
    Double pMutacao;
    Integer geracoes;
    Problema problema;
    Double minimo;
    Double maximo;
    Integer nVariaveis;
    private Integer mu;
    private Integer lambda;

    public AlgoritmoGenetico(Double minimo, Double maximo, Integer nVariaveis, Problema problema, Integer mu, Integer lambda, Integer geracoes, Double pMutacao) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVariaveis = nVariaveis;
        this.problema = problema;
        this.mu = mu;
        this.lambda = lambda;
        this.geracoes = geracoes;
        this.pMutacao = pMutacao;
    }

    Populacao populacao;
    Populacao novaPopulacao;

    public Populacao executar() {
        populacao = new Populacao(problema, minimo, maximo, nVariaveis, tamanho);
        novaPopulacao = new Populacao(problema, minimo, maximo, nVariaveis, mu);

        populacao.criar();
        populacao.avaliar();

        // Populacao - descendentes
        Populacao descendentes = new Populacao();

        // Recuperar o melhor indivíduo
        Random rnd = new Random();
        int ind1, ind2;

        for (int g = 1; g <= geracoes; g++) {
            for (int i = 0; i < this.nVariaveis; i++) {
                // Gerar lambda/mu descendentes
                for (int d = 0; d < lambda / mu; d++) {
                    // Clonar individuo
                    Individuo filho = (Individuo) populacao.getIndividuos().get(i).clone();
                    // Aplicar mutacao
                    mutacaoSWAP(filho);
                    // Avaliar
                    problema.calcularFuncaoObjetivo(filho);
                    // Inserir na populacao de descendentes
                    descendentes.getIndividuos().add(filho);
                }
            }
            // Definir sobreviventes -> populacao + descendentes
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());

            // Ordenar populacao;
            Collections.sort(populacao.getIndividuos());

            // Eliminar os demais individuos - criterio: tamanho da população
            populacao.getIndividuos().subList(this.nVariaveis, populacao.getIndividuos().size()).clear();

            // Limpa a nova população para a geração seguinte
            novaPopulacao.getIndividuos().clear();
        }
        return populacao;
    }

    private void mutacaoSWAP(Individuo individuo) {

        Random rnd = new Random();

        for (int i = 0; i < individuo.getVariaveis().size(); i++) {
            if (rnd.nextDouble() <= this.pMutacao) {

                int j;
                do {
                    j = rnd.nextInt(this.nVariaveis);
                } while (i == j);

                Collections.swap(individuo.getVariaveis(), i, j);

            }
        }
    }
    
    public void resultados(int execucao, Double custo, long tempo, int cabecalho) {
        try {
            FileWriter saida = new FileWriter("resultados.csv", true);
            BufferedWriter sai = new BufferedWriter(saida);

            if (execucao == 1 && cabecalho == 1) {
                saida.write("NumeroDaExecucao;ResultadoDaF0;TempoDeExecucaoEmMilissegundos\n");
                saida.write(execucao + ";" + custo + ";" + tempo);
            } else {
                saida.write(execucao + ";" + custo + ";" + tempo+"\n");
            }

            saida.flush();
            saida.close();

        } catch (IOException ex) {
            System.out.println("Erro ao escrever no arquivo resultados.csv");
        }
    }
}
