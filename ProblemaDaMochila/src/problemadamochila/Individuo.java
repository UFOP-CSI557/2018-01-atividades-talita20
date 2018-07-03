package problemadamochila;

import java.util.ArrayList;
import java.util.Random;

public class Individuo implements Comparable<Individuo> {

    private ArrayList<Integer> cromossomos;// Genotipo
    private ArrayList<Double> variaveis;// Fenotipo
    Double funcaoObjetivo;
    Integer precisao;
    Double minimo;
    Double maximo;
    Integer nVar;

    int geracoes;
    private ArrayList<Integer> pesos;//peso de cada item
    int pesoMaximo;//Peso maximo da mochila
    int pesoMinimo;//Peso minimo da mochila
    private ArrayList<Integer> pesoItens;

    private ArrayList<Integer> valores;//valores de cada item
    int valorMaximo;//Valor maximo da mochila
    int valorMinimo;//Valor minimo da mochila
    private ArrayList<Integer> valorItens;

    public Individuo(Integer precisao, Double minimo, Double maximo, Integer nVar) {
        this.precisao = precisao;
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVar = nVar;
        this.cromossomos = new ArrayList<>();
    }

    public Individuo(int geracoes, ArrayList<Integer> pesos, int pesoMinimo, int pesoMaximo, ArrayList<Integer> valores, int valorMinimo, int valorMaximo) {
        this.geracoes = geracoes;
        this.pesos = pesos;
        this.pesoMinimo = pesoMinimo;
        this.pesoMaximo = pesoMaximo;
        this.valores = valores;
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
    }

    public ArrayList<Integer> getCromossomos() {
        return cromossomos;
    }

    public void setCromossomos(ArrayList<Integer> cromossomos) {
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

    public Integer getPrecisao() {
        return precisao;
    }

    public void setPrecisao(Integer precisao) {
        this.precisao = precisao;
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
        this.cromossomos = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < this.getnVar(); i++) {
            for (int j = 0; j < this.getPrecisao(); j++) {
                this.getCromossomos().add(rnd.nextInt(2));
            }
        }
    }

    @Override
    public int compareTo(Individuo o) {
        return this.getFuncaoObjetivo()
                .compareTo(o.getFuncaoObjetivo());
    }

    public ArrayList<Integer> preeenchePesos(ArrayList<Integer> pesos, int pesoMinimo, int pesoMaximo, int geracoes) {
        Random rnd = new Random();
        for (int i = 0; i < geracoes; i++) {
            pesos.add(rnd.nextInt((pesoMaximo - pesoMinimo) + 1) + pesoMinimo);
        }
        return pesos;
    }

    public ArrayList<Integer> preeencheValores(ArrayList<Integer> valores, int valorMinimo, int valorMaximo, int geracoes) {
        Random rnd = new Random();
        for (int i = 0; i < geracoes; i++) {
            valores.add(rnd.nextInt((valorMaximo - valorMinimo) + 1) + valorMinimo);
        }
        return valores;
    }

    @Override
    public String toString() {
        return "Itens{" + "pesoItens=" + pesoItens + '}';
    }

}
