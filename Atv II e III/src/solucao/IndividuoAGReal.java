package solucao;

import java.util.ArrayList;
import java.util.Random;

public class IndividuoAGReal implements Comparable<IndividuoAGReal> {

    // Genótipo+Fenotipo
    private ArrayList<Double> variaveis;
    // Custo da função objetivo
    Double funcaoObjetivo;
    Double minimo;
    Double maximo;
    Integer nVar;

    public IndividuoAGReal(Double minimo, Double maximo, Integer nVar) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVar = nVar;
        this.variaveis = new ArrayList<>();
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

    public int compareTo(IndividuoAGReal o) {
        return this.getFuncaoObjetivo().compareTo(o.getFuncaoObjetivo());
    }

}