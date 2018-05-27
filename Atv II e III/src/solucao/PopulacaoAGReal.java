package solucao;

import java.util.ArrayList;
import problema.ProblemaAGReal;

public class PopulacaoAGReal {

    Double minimo;
    Double maximo;
    Integer nVar;
    Integer tamanho;
    ArrayList<IndividuoAGReal> individuos;
    ProblemaAGReal problema;

    public PopulacaoAGReal(Double minimo, Double maximo, Integer nVar, Integer tamanho, ProblemaAGReal problema) {
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

    public ArrayList<IndividuoAGReal> getIndividuos() {
        return individuos;
    }

    public void setIndividuos(ArrayList<IndividuoAGReal> individuos) {
        this.individuos = individuos;
    }

    public void criar() {
        this.individuos = new ArrayList<>();
        for (int i = 0; i < this.getTamanho(); i++) {
            IndividuoAGReal individuo = new IndividuoAGReal(minimo, maximo, nVar);
            individuo.criar();
            this.getIndividuos().add(individuo);
        }
    }

    public void avaliar() {
        for (IndividuoAGReal individuo : this.getIndividuos()) {
            problema.calcularFuncaoObjetivo(individuo);
        }
    }

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