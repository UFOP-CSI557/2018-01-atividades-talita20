package problemadamochila;

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
    Integer precisao;
    Double minimo;
    Double maximo;
    Integer nVariaveis;

    ArrayList<Integer> pesos;//Peso Kg de cada item
    int pesoMaximo;//Peso maximo da mochila
    int pesoMinimo;//Peso minimo da mochila
    ArrayList<Integer> pesoItens = null;//array de pesos
    ArrayList<Integer> cromossomos = null;//array de itens -> representacao binaria

    private ArrayList<Integer> valores;//valores de cada item
    int valorMaximo;//Valor maximo da mochila
    int valorMinimo;//Valor minimo da mochila
    private ArrayList<Integer> valorItens;

    public AlgoritmoGenetico(Integer tamanho, Double pCrossover, Double pMutacao, Integer geracoes, Problema problema, Integer precisao, Double minimo, Double maximo, Integer nVariaveis, ArrayList<Integer> pesos, int pesoMinimo, int pesoMaximo, ArrayList<Integer> pesoItens, ArrayList<Integer> valores, int valorMinimo, int valorMaximo, ArrayList<Integer> valorItens) {
        this.tamanho = tamanho;
        this.pCrossover = pCrossover;
        this.pMutacao = pMutacao;
        this.geracoes = geracoes;
        this.problema = problema;
        this.precisao = precisao;
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVariaveis = nVariaveis;
        this.pesos = pesos;
        this.pesoMinimo = pesoMinimo;
        this.pesoMaximo = pesoMaximo;
        this.pesoItens = new ArrayList<>();
        this.valores = valores;
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
        this.valorItens = new ArrayList<>();
    }

    Populacao populacao;
    Populacao novaPopulacao;
    Individuo melhorSolucao;

    public Individuo getMelhorSolucao() {
        return melhorSolucao;
    }

    public Populacao executar(ArrayList<Integer> pesoItens, ArrayList<Integer> valorItens) {
        populacao = new Populacao(precisao, minimo, maximo, nVariaveis, tamanho, problema);
        novaPopulacao = new Populacao(precisao, minimo, maximo, nVariaveis, tamanho, problema);

        populacao.criar();
        populacao.avaliar(pesoItens, valorItens);

        // Recuperar o melhor indivíduo
        Random rnd = new Random();
        int ind1, ind2;

        // Enquanto o critério de parada não for satisfeito - Gerações
        for (int g = 1; g <= geracoes; g++) {
            for (int i = 0; i < this.tamanho; i++) {
                // Crossover
                if (rnd.nextDouble() <= this.pCrossover) {
                    // Realizar a operação
                    ind1 = rnd.nextInt(this.tamanho);
                    do {
                        ind2 = rnd.nextInt(this.tamanho);
                    } while (ind1 == ind2);
                    Individuo desc1 = new Individuo(precisao, minimo, maximo, nVariaveis);
                    Individuo desc2 = new Individuo(precisao, minimo, maximo, nVariaveis);

                    // Progenitores
                    Individuo p1 = populacao.getIndividuos().get(ind1);
                    Individuo p2 = populacao.getIndividuos().get(ind2);

                    int corte = rnd.nextInt(p1.getCromossomos().size());

                    crossoverUmPonto(p1, p2, desc1, corte);

                    crossoverUmPonto(p2, p1, desc2, corte);

                    // Mutação
                    mutacaoPorBit(desc1);
                    mutacaoPorBit(desc2);

                    // Avaliar as novas soluções
                    problema.calcularFuncaoObjetivo(desc1, pesoItens, valorItens);
                    problema.calcularFuncaoObjetivo(desc2, pesoItens, valorItens);

                    // Inserir na nova população
                    novaPopulacao.getIndividuos().add(desc1);
                    novaPopulacao.getIndividuos().add(desc2);
                }
            }

            // Definir sobreviventes -> populacao + descendentes
            // Merge: combinar pop+desc
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());
            Collections.sort(populacao.getIndividuos());

            // Eliminar os demais individuos - criterio: tamanho da população
            populacao.getIndividuos()
                    .subList(this.tamanho,
                            populacao.getIndividuos().size())
                    .clear();

            // Limpa a nova população para a geração seguinte
            novaPopulacao.getIndividuos().clear();

            // Imprimir a situacao atual
            //System.out.println("Gen = " + g
            //  + "\tCusto = "
            //    + populacao.getIndividuos().get(0).getFuncaoObjetivo());
        }

        //exibir o melhor resultado -> itens e pesos
        cromossomos = populacao.getIndividuos().get(0).getCromossomos();
        imprimeItens(cromossomos, pesoItens, valorItens);

        return populacao;
    }

    private void crossoverUmPonto(Individuo ind1, Individuo ind2, Individuo descendente, int corte) {
        descendente.getCromossomos()
                .addAll(ind1.getCromossomos().subList(0, corte));

        descendente.getCromossomos()
                .addAll(ind2.getCromossomos().subList(corte, ind2.getCromossomos().size()));
    }

    private void mutacaoPorBit(Individuo individuo) {
        Random rnd = new Random();
        for (int i = 0; i < individuo.getCromossomos().size(); i++) {
            if (rnd.nextDouble() <= this.pMutacao) {
                int bit = individuo.getCromossomos().get(i);
                if (bit == 0) {
                    bit = 1;
                } else {
                    bit = 0;
                }
                individuo.getCromossomos().set(i, bit);
            }
        }
    }

    //Imprime os arrays de itens e pesos
    private void imprimeItens(ArrayList<Integer> cromossomos, ArrayList<Integer> pesos, ArrayList<Integer> valores) {
        System.out.println("Itens: " + cromossomos);
        System.out.println("Pesos: " + pesos);
        System.out.println("Valores: " + valores);
        calculaPesoMochila(cromossomos, pesos, valores);
        cromossomos.clear();
        pesos.clear();
        valores.clear();
    }

    //Calcula o peso inserido na mochila
    private void calculaPesoMochila(ArrayList<Integer> cromossomos, ArrayList<Integer> pesoItens, ArrayList<Integer> valorItens) {
        int pesoRestante = 50;
        int pesoMochila = 0;
        ArrayList<Integer> itensInseridos = (ArrayList<Integer>) cromossomos.clone();
        Integer inserido = 1;
        for (int i = 0; i < cromossomos.size(); i++) {
            if (cromossomos.get(i) == 1) {
                if (pesoItens.get(i) <= pesoRestante && valorItens.get(i) >= 3) {
                    pesoMochila += pesoItens.get(i);
                    pesoRestante -= pesoItens.get(i);
                    itensInseridos.set(i, inserido);
                } else {
                    itensInseridos.set(i, 0);
                }
            } else {
                itensInseridos.set(i, 0);
            }
        }
        if (pesoMochila < 50) {
            for (int j = 0; j < cromossomos.size(); j++) {
                if (cromossomos.get(j) == 1 && itensInseridos.get(j) == 0) {
                    if (pesoItens.get(j) <= pesoRestante && valorItens.get(j) < 3) {
                        pesoMochila += pesoItens.get(j);
                        pesoRestante -= pesoItens.get(j);
                        itensInseridos.set(j, inserido);
                    } else {
                        itensInseridos.set(j, 0);
                    }
                } else {
                    itensInseridos.set(j, 0);
                }
            }
        }

        System.out.println("Inseridos: " + itensInseridos);
        System.out.println("Peso da mochila: " + pesoMochila + "kg\n");
        itensInseridos.clear();
    }

    public void resultados(int execucao, Double custo, long tempo, int cabecalho) {
        try {
            FileWriter saida = new FileWriter("Resultado2.csv", true);
            BufferedWriter sai = new BufferedWriter(saida);

            if (execucao == 1 && cabecalho == 1) {
                saida.write("EXECUCAO;CUSTO;TEMPO;\n");
                saida.write(execucao + ";\t" + custo + ";\t" + tempo + "ms\n");

            } else {
                saida.write(execucao + ";\t" + custo + ";\t" + tempo + "ms\n");
            }

            saida.flush();
            saida.close();

        } catch (IOException ex) {
            System.out.println("Erro ao escrever no arquivo Resultado2.csv");
        }
    }

}
