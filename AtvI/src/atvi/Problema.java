package atvi;

public class Problema {

    //Funcao de avaliacao - funcao de rastringin
    public void calcularFuncaoObjetivo(Individuo individuo) {
        Double soma = 10.0 * individuo.getnVar();

        for (Double var : individuo.getVariaveis()) {
            soma += Math.pow(var, 2) - (10 * Math.cos(2 * Math.PI * var));
        }
        individuo.setFuncaoObjetivo(soma);
    }
}
