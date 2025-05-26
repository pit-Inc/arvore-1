import java.sql.ClientInfoStatus;
import java.util.List;

public class ArvoreService {

    private final Arvore arvore = new Arvore();

    private No noEncontrado;

    // 15,-6,32,16,0,-80,105,-3
    public Arvore criarArvore(List<Integer> valoresArvore) {
        if (valoresArvore.isEmpty()) {
            throw new IllegalArgumentException("A lista de valores da árvore não pode ser vazia.");
        }

        arvore.setNoRaiz(inserirNaArvore(null, valoresArvore.getFirst()));

        valoresArvore.removeFirst();

        valoresArvore.forEach(valor -> inserirNaArvore(arvore.getNoRaiz(), valor));

        return arvore;
    }

    public No inserirNaArvore(No noRef, int valor) {
        if (noRef == null) {
            return new No(valor, null, null);
        }

        if (valor < noRef.getValor()) {
            noRef.setEsquerda(inserirNaArvore(noRef.getEsquerda(), valor));
        }

        if (valor > noRef.getValor()) {
            noRef.setDireita(inserirNaArvore(noRef.getDireita(), valor));
        }

        return noRef;

    }

    public No getNo(int valor) {
        No noAtual = this.arvore.getNoRaiz();
        while (noAtual.getValor() != valor) {
            if (valor > noAtual.getValor()) {
                noAtual = noAtual.getDireita();
            } else {
                noAtual = noAtual.getEsquerda();
            }
            if (noAtual == null) {
                throw new IllegalArgumentException("Não existe um nó nesse valor");
            }
        }
        return noAtual;
    }

    public No removerDaArvore(No no, No noAnterior) {
        if (no.getDireita() == null && no.getEsquerda() == null) {
            return no = null;
        } else {
            if (noAnterior.getDireita() == null) {
                no = noAnterior.getEsquerda();
            } else {
                no = noAnterior.getDireita();
            }

            removerDaArvore(no, no.getEsquerda());

        }

    }


    public Arvore getArvore() {
        return arvore;
    }
}
