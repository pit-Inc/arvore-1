package service;

import dto.NoRefDTO;
import model.Arvore;
import model.No;
import utils.NoUtils;

import java.util.List;

public class ArvoreService {

    private final Arvore arvore = new Arvore();

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

    private NoRefDTO getNoRef(int valor) {
        No noAtual = this.arvore.getNoRaiz();
        No noPai = null;
        while (noAtual.getValor() != valor) {
            noPai = noAtual;
            if (valor > noAtual.getValor()) {
                noAtual = noAtual.getDireita();
            } else {
                noAtual = noAtual.getEsquerda();
            }
            if (noAtual == null) {
                throw new IllegalArgumentException("Não existe um nó com esse valor na árvore.");
            }
        }
        return new NoRefDTO(noAtual, noPai);
    }

    public void remover(int valor) {
        NoRefDTO noRef =  getNoRef(valor);

        No noParaRemover = noRef.no();
        No noPai = noRef.pai();

        if (!NoUtils.noPossuiFilhos(noParaRemover)) {
            removerNoSemFilhos(noParaRemover, noPai);
        } else {
            if (NoUtils.noPossuiApenasFilhoADireita(noParaRemover)) {
                removerNoPossuiApenasFilhoADireita(noParaRemover, noPai);
            } else if (NoUtils.noPossuiApenasFilhoAEsquerda(noParaRemover)) {
                removerNoPossuiApenasFilhoAEsquerda(noParaRemover, noPai);
            } else {
                // Nó possui dois filhos
                removerNoComDoisFilhos(noParaRemover);
            }
        }

    }

    private void removerNoSemFilhos(No noParaRemover, No noPai) {
        if(noParaRemover.equals(arvore.getNoRaiz())) {
            arvore.setNoRaiz(null);
        } else {
            if (NoUtils.noEhFilhoEsquerdo(noParaRemover, noPai)) {
                noPai.setEsquerda(null);
            } else {
                noPai.setDireita(null);
            }
        }
    }

    private void removerNoPossuiApenasFilhoADireita(No noParaRemover, No noPai) {
        if (NoUtils.noEhFilhoEsquerdo(noParaRemover, noPai)) {
            noPai.setEsquerda(noParaRemover.getDireita());
        } else {
            noPai.setDireita(noParaRemover.getDireita());
        }
    }

    private void removerNoPossuiApenasFilhoAEsquerda(No noParaRemover, No noPai) {
        if (NoUtils.noEhFilhoEsquerdo(noParaRemover, noPai)) {
            noPai.setEsquerda(noParaRemover.getEsquerda());
        } else {
            noPai.setDireita(noParaRemover.getEsquerda());
        }
    }

    private void removerNoComDoisFilhos(No noParaRemover) {
        No noSucessor = getSucessor(noParaRemover);

        remover(noSucessor.getValor());

        noParaRemover.setValor(noSucessor.getValor());

    }

    private No getSucessor(No no) {
        no = no.getDireita();

        while(no.getEsquerda() != null) {
            no = no.getEsquerda();
        }

        return no;

    }

    public void exibirArvore() {
        if (arvore.getNoRaiz() == null) {
            System.out.println("Árvore vazia.");
            return;
        }

        exibirRecursivo(arvore.getNoRaiz(), "", true, true);

        System.out.println();
        System.out.println("-------------------------------------------------------------------------");
        System.out.println();
    }

    private void exibirRecursivo(No no, String prefixo, boolean ehCauda, boolean ehRaiz) {
        if (no == null) {
            return;
        }
        System.out.print(prefixo);

        if (!ehRaiz) {
            System.out.print(ehCauda ? "└── " : "├── ");
        }

        System.out.println(no.getValor());
        String prefixoFilhos = prefixo + (ehRaiz ? "" : (ehCauda ? "    " : "│   "));

        No filhoEsquerdo = no.getEsquerda();
        No filhoDireito = no.getDireita();

        if (filhoEsquerdo != null) {
            exibirRecursivo(filhoEsquerdo, prefixoFilhos, (filhoDireito == null), false);
        }

        if (filhoDireito != null) {
            exibirRecursivo(filhoDireito, prefixoFilhos, true, false);
        }
    }


    public Arvore getArvore() {
        return arvore;
    }
}
