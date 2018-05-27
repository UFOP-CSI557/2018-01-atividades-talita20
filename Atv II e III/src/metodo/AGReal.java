package metodo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import problema.ProblemaAGReal;
import solucao.IndividuoAGReal;
import solucao.PopulacaoAGReal;

public class AGReal {

    Integer tamanho;
    Double pCrossover;
    Double pMutacao;
    Integer geracoes;
    ProblemaAGReal problema;
    Double minimo;
    Double maximo;
    Integer nVariaveis;

    public AGReal(Integer tamanho, Double pCrossover, Double pMutacao, Integer geracoes, ProblemaAGReal problema, Double minimo, Double maximo, Integer nVariaveis) {
        this.tamanho = tamanho;
        this.pCrossover = pCrossover;
        this.pMutacao = pMutacao;
        this.geracoes = geracoes;
        this.problema = problema;
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVariaveis = nVariaveis;
    }

    PopulacaoAGReal populacao;
    PopulacaoAGReal novaPopulacao;

    public PopulacaoAGReal executar() {
        populacao = new PopulacaoAGReal(minimo, maximo, nVariaveis, tamanho, problema);
        novaPopulacao = new PopulacaoAGReal(minimo, maximo, nVariaveis, tamanho, problema);

        populacao.criar();
        populacao.avaliar();

        // Recuperar o melhor indivíduo
        Random rnd = new Random();
        int ind1, ind2;

        for (int g = 1; g <= geracoes; g++) {
            for (int i = 0; i < this.tamanho; i++) {
                // Crossover
                if (rnd.nextDouble() <= this.pCrossover) {
                    ind1 = rnd.nextInt(this.tamanho);
                    do {
                        ind2 = rnd.nextInt(this.tamanho);
                    } while (ind1 == ind2);
                    IndividuoAGReal desc1 = new IndividuoAGReal(minimo, maximo, nVariaveis);
                    IndividuoAGReal desc2 = new IndividuoAGReal(minimo, maximo, nVariaveis);

                    // Progenitores
                    IndividuoAGReal p1 = populacao.getIndividuos().get(ind1);
                    IndividuoAGReal p2 = populacao.getIndividuos().get(ind2);

                    int corte = rnd.nextInt(p1.getVariaveis().size());

                    crossoverUmPonto(p1, p2, desc1, corte);
                    crossoverUmPonto(p2, p1, desc2, corte);

                    mutacaoPorVariavel(desc1);
                    mutacaoPorVariavel(desc2);

                    // Avaliar as novas soluções
                    problema.calcularFuncaoObjetivo(desc1);
                    problema.calcularFuncaoObjetivo(desc2);

                    // Inserir na nova população
                    novaPopulacao.getIndividuos().add(desc1);
                    novaPopulacao.getIndividuos().add(desc2);
                }
            }

            // Definir sobreviventes -> populacao + descendentes
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());

            // Ordenar populacao;
            Collections.sort(populacao.getIndividuos());

            // Eliminar os demais individuos - criterio: tamanho da população
            populacao.getIndividuos().subList(this.tamanho, populacao.getIndividuos().size()).clear();

            // Limpa a nova população para a geração seguinte
            novaPopulacao.getIndividuos().clear();
        }

        //System.out.println(populacao.getIndividuos().get(0).getVariaveis());
        return populacao;
    }

    private void crossoverUmPonto(IndividuoAGReal ind1, IndividuoAGReal ind2, IndividuoAGReal descendente, int corte) {

        Random rnd = new Random();
        Double alpha = rnd.nextDouble();

        // Ind1_1
        // alpha * P1
        for (int i = 0; i < corte; i++) {
            Double valor = alpha * ind1.getVariaveis().get(i);
            descendente.getVariaveis().add(valor);
        }

        // Ind2_2
        // (1 - alpha) * P2
        for (int i = corte; i < this.nVariaveis; i++) {
            Double valor = (1.0 - alpha) * ind2.getVariaveis().get(i);
            descendente.getVariaveis().add(valor);
        }
    }

    private void mutacaoPorVariavel(IndividuoAGReal individuo) {
        Random rnd = new Random();

        for (int i = 0; i < individuo.getVariaveis().size(); i++) {
            if (rnd.nextDouble() <= this.pMutacao) {

                // Mutacao aritmetica
                Double valor = individuo.getVariaveis().get(i);
                valor *= rnd.nextDouble();

                // Inverter o sinal
                if (!rnd.nextBoolean()) {
                    valor = -valor;
                }

                if (valor >= this.minimo && valor <= this.maximo) {
                    individuo.getVariaveis().set(i, valor);
                }
            }
        }
    }

    public void resultados(String nomeTeste, int execucao, Double custo, long tempo, int cabecalho) {
        try {
            FileWriter saida = new FileWriter("AGReal.csv", true);
            BufferedWriter sai = new BufferedWriter(saida);

            if (execucao == 1 && cabecalho == 1) {
                saida.write("TESTE;EXECUCAO;CUSTO;TEMPO;\n");
                saida.write(nomeTeste + ";\t" + execucao + ";\t" + custo + ";\t" + tempo + "ms\n");

            } else {
                saida.write(nomeTeste + ";\t" + execucao + ";\t" + custo + ";\t" + tempo + "ms\n");
            }

            saida.flush();
            saida.close();

        } catch (IOException ex) {
            System.out.println("Erro ao escrever no arquivo AGReal.csv");
        }
    }

}
