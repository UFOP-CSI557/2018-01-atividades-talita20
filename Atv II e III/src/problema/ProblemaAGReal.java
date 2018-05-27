package problema;

import solucao.IndividuoAGReal;

public class ProblemaAGReal {

    //Funcao de avaliacao - funcao de rastringin
    public void calcularFuncaoObjetivo(IndividuoAGReal individuo) {
        Double soma = 10.0 * individuo.getnVar();

        for (Double var : individuo.getVariaveis()) {
            soma += Math.pow(var, 2) - (10 * Math.cos(2 * Math.PI * var));
        }
        individuo.setFuncaoObjetivo(soma);
    }
}