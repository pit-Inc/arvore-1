package model;

public class Arvore {

    public Arvore() {
    }

    public Arvore(No noRaiz) {
        this.noRaiz = noRaiz;
    }

    private No noRaiz;

    public No getNoRaiz() {
        return noRaiz;
    }

    public void setNoRaiz(No noRaiz) {
        this.noRaiz = noRaiz;
    }

}
