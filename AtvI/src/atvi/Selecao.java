package atvi;

import java.util.ArrayList;
import java.util.Collections;

public class Selecao {

    Populacao populacao;
    Individuo melhorIndividuo;
    Individuo piorIndividuo;
    ArrayList<Individuo> melhorInd = new ArrayList<>();
    ArrayList<Individuo> piorInd = new ArrayList<>();
    ArrayList<Populacao> populacoes = new ArrayList<>();

    public Selecao(Populacao populacao) {
        this.populacao = populacao;
    }

    public Populacao getPopulacao() {
        return populacao;
    }

    public void setPopulacao(Populacao populacao) {
        this.populacao = populacao;
    }

    public Individuo getMelhorIndividuo() {
        return melhorIndividuo;
    }

    public void setMelhorIndividuo(Individuo melhor) {
        this.melhorIndividuo = melhor;
    }

    public Individuo getPiorIndividuo() {
        return piorIndividuo;
    }

    public void setPiorIndividuo(Individuo pior) {
        this.piorIndividuo = pior;
    }

    public Individuo melhorIndividuo() {
        return this.populacao.getIndividuos().get(0);
    }

    public Individuo piorIndividuo() {
        return this.populacao.getIndividuos().get(this.populacao.getIndividuos().size());
    }

    public void addPopulacao(Populacao populacao) {
        populacoes.add(populacao);
    }
    
}
