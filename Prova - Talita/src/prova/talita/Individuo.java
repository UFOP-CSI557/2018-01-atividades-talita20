package prova.talita;

import java.util.ArrayList;
import java.util.Random;

public class Individuo implements Comparable<Individuo> {

    // Genótipo+Fenotipo
    private ArrayList<Double> variaveis;
    // Custo da função objetivo
    Double funcaoObjetivo;
    Double minimo;
    Double maximo;
    Integer nVar;
    // Genótipo+Fenotipo
    private ArrayList<Double> cromossomos;

    public Individuo(Double minimo, Double maximo, Integer nVar) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVar = nVar;
        this.variaveis = new ArrayList<>();
        this.cromossomos = new ArrayList<>();
    }

    public ArrayList<Double> getCromossomos() {
        return cromossomos;
    }

    public void setCromossomos(ArrayList<Double> cromossomos) {
        this.cromossomos = cromossomos;
    }

    public ArrayList<Double> getVariaveis() {
        return variaveis;
    }

    public void setVariaveis(ArrayList<Double> variaveis) {
        this.variaveis = variaveis;
    }

    public Double getFuncaoObjetivo() {
        return funcaoObjetivo;
    }

    public void setFuncaoObjetivo(Double funcaoObjetivo) {
        this.funcaoObjetivo = funcaoObjetivo;
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

    // Gerar o genótipo
    public void criar() {
        this.variaveis = new ArrayList<>();

        Random rnd = new Random();
        Double valor;

        for (int i = 0; i < this.getnVar(); i++) {
            valor = this.minimo + (this.maximo - this.minimo) * rnd.nextDouble();
            this.variaveis.add(valor);
        }
    }

    @Override
    public int compareTo(Individuo o) {
        return this.getFuncaoObjetivo().compareTo(o.getFuncaoObjetivo());
    }

    public Individuo clone() {
        Individuo individuo = null;
        individuo
                = new Individuo(this.getMinimo(),
                        this.getMaximo(),
                        this.getnVar());
        individuo.setCromossomos(new ArrayList<>(this.getCromossomos()));
        individuo.setFuncaoObjetivo(this.getFuncaoObjetivo());
        return individuo;
    }

}
