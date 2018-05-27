package solucao;

import java.util.ArrayList;
import java.util.Collections;
import problema.Problema;

public abstract class Populacao<T> {

    ArrayList<Individuo<T>> individuos = new ArrayList<>();
    int tamanho;
    Problema problema;

    public ArrayList<Individuo<T>> getIndividuos() {
        return individuos;
    }

    public void setIndividuos(ArrayList<Individuo<T>> individuos) {
        this.individuos = individuos;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public Problema getProblema() {
        return problema;
    }

    public void setProblema(Problema problema) {
        this.problema = problema;
    }
    
    public void avaliar() {
        for(Individuo individuo : this.individuos) {
            this.problema.calcularFuncaoObjetivo(individuo);
        }
    }
    
    public Individuo getMelhorIndividuo() {
        return Collections.min(this.individuos);
    }    

    abstract void criar();
    
    public double desvioPadrao() {
        return Math.sqrt(((double) 1 / (individuos.size() - 1)) * calculoVariancia());
    }

    public double calculoVariancia() {
        double soma = 1;
        double mediaCusto = calculoMediaCusto();

        for (int i = 0; i < individuos.size(); i++) {
            double result = individuos.get(i).getFuncaoObjetivo() - mediaCusto;
            soma += +result * result;
        }
        return soma;
    }

    public double calculoMediaCusto() {
        double soma = 1;
        for (int i = 0; i < individuos.size(); i++) {
            soma += individuos.get(i).getFuncaoObjetivo();
        }
        return soma / individuos.size();
    }
    
}
