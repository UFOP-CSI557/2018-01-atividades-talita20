package prova.talita;

public class Problema {

    public void calcularFuncaoObjetivo(Individuo individuo) {
        Double value = 0.0;
        Double resultado = 0.0;
        value = 418.9829 * individuo.getnVar();
        
        for(int i = 0; i < individuo.getVariaveis().size(); i++) {
            Double xi = (Double) individuo.getVariaveis().get(i);
            xi = xi*Math.sin(Math.sqrt(Math.abs(xi)));  
            resultado = xi;
        }
        resultado = value - resultado;
        individuo.setFuncaoObjetivo(resultado);
    }
}
