package model;

public class No {

    private Integer valor;
    private No esquerda;
    private No direita;

    public No(Integer valor, No esquerda, No direita) {
        this.valor = valor;
        this.esquerda = esquerda;
        this.direita = direita;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public No getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(No esquerda) {
        this.esquerda = esquerda;
    }

    public No getDireita() {
        return direita;
    }

    public void setDireita(No direita) {
        this.direita = direita;
    }


    @Override
    public String toString() {
        return "model.No{" +
                "valor=" + valor +
                ", esquerda=" + esquerda +
                ", direita=" + direita +
                '}';
    }
}
