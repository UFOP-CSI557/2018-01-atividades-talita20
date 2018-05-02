package atvi;

import java.util.ArrayList;

public class Populacao {

    Double minimo;
    Double maximo;
    Integer nVar;
    Integer tamanho;
    ArrayList<Individuo> individuos;
    Problema problema;

    public Populacao(Double minimo, Double maximo, Integer nVar, Integer tamanho, Problema problema) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVar = nVar;
        this.tamanho = tamanho;
        this.problema = problema;
        this.individuos = new ArrayList<>();
    }

    public Double getMinimo() {
        return minimo;
    }

    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }

    public Double getMaximo() {
        return maximo;
    }

    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }

    public Integer getnVar() {
        return nVar;
    }

    public void setnVar(Integer nVar) {
        this.nVar = nVar;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public ArrayList<Individuo> getIndividuos() {
        return individuos;
    }

    public void setIndividuos(ArrayList<Individuo> individuos) {
        this.individuos = individuos;
    }

    public void criar() {
        this.individuos = new ArrayList<>();
        for (int i = 0; i < this.getTamanho(); i++) {
            Individuo individuo = new Individuo(minimo, maximo, nVar);
            individuo.criar();
            this.getIndividuos().add(individuo);
        }

    }

    public void avaliar() {
        for (Individuo individuo : this.getIndividuos()) {
            problema.calcularFuncaoObjetivo(individuo);
        }
    }
}
