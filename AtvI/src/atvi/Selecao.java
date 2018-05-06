package atvi;

import java.util.ArrayList;
import java.util.Collections;

public class Selecao {

    Populacao populacao;
    ArrayList<Populacao> populacoes = new ArrayList<>();

    public Selecao(Populacao populacao) {
        this.populacao = populacao;
    }

    public void addPopulacao(Populacao populacao) {
        populacoes.add(populacao);
    }
}
