package problema;

import solucao.Individuo;

public interface Problema {
 
    void calcularFuncaoObjetivo(Individuo individuo);
    int getDimensao();
    
}
