package problemadamochila;

import com.sun.org.apache.xml.internal.serialize.IndentPrinter;
import java.util.ArrayList;

public class Populacao {

    Integer precisao;
    Double minimo;
    Double maximo;
    Integer nVar;
    Integer tamanho;

    ArrayList<Individuo> individuos;// Conjunto de indivíduos
    ArrayList<Individuo> itens;//array de itens
    Problema problema;
    ArrayList<Integer> pesoItens;

    public Populacao(Integer precisao, Double minimo, Double maximo, Integer nVar, Integer tamanho, Problema problema) {
        this.precisao = precisao;
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVar = nVar;
        this.tamanho = tamanho;
        this.problema = problema;
        this.individuos = new ArrayList<>();
        this.itens = new ArrayList<>();
        this.pesoItens = new ArrayList<>();
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

    public ArrayList<Integer> getPesoItens() {
        return pesoItens;
    }

    public void setPesoItens(ArrayList<Integer> pesoItens) {
        this.pesoItens = pesoItens;
    }

    public ArrayList<Individuo> getItens() {
        return itens;
    }

    public void setItens(ArrayList<Individuo> itens) {
        this.itens = itens;
    }

    // Criar a população
    public void criar() {
        this.individuos = new ArrayList<>();
        for (int i = 0; i < this.getTamanho(); i++) {
            Individuo individuo = new Individuo(precisao, minimo, maximo, nVar);
            individuo.criar();
            this.getIndividuos().add(individuo);
        }
    }

    // Avaliar a população
    public void avaliar(ArrayList<Integer> pesoItens, ArrayList<Integer> valorItens) {
        for (Individuo individuo : this.getIndividuos()) {
            problema.calcularFuncaoObjetivo(individuo, pesoItens, valorItens);
        }
    }

}
