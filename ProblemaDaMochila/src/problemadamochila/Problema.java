package problemadamochila;

import java.util.ArrayList;

public class Problema {

    public void calcularFuncaoObjetivo(Individuo individuo, ArrayList<Integer> pesoItens, ArrayList<Integer> valorItens) {
        Double value = 0.0;
        int pesoMaximo = 50;
        int pesoRestante = pesoMaximo;
        ArrayList<Integer> itensInseridos = (ArrayList<Integer>) individuo.getCromossomos().clone();
        Integer inserido = 1;

        for (int i = 0; i < individuo.getCromossomos().size(); i++) {
            if (individuo.getCromossomos().get(i) == 1) {
                if (pesoItens.get(i) <= pesoRestante && valorItens.get(i) >= 3) {
                    pesoRestante -= pesoItens.get(i);
                    itensInseridos.set(i, inserido);
                    value += individuo.getCromossomos().get(i);
                } else {
                    itensInseridos.set(i, 0);
                }
            } else {
                itensInseridos.set(i, 0);
            }
        }
        //Caso ainda tenha espaco na mochila -> insere item com valor 1 ou 2
        if (pesoRestante < 50) {
            for (int j = 0; j < individuo.getCromossomos().size(); j++) {
                if (individuo.getCromossomos().get(j) == 1 && itensInseridos.get(j) == 0) {
                    if (pesoItens.get(j) <= pesoRestante && valorItens.get(j) < 3) {
                        pesoRestante -= pesoItens.get(j);
                        itensInseridos.set(j, inserido);
                        value += individuo.getCromossomos().get(j);
                    } else {
                        itensInseridos.set(j, 0);
                    }
                } else {
                    itensInseridos.set(j, 0);
                }
            }
        }

        individuo.setFuncaoObjetivo(value);
        itensInseridos.clear();
    }
}
