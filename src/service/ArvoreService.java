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

    /**
     * Método auxiliar para obter o nó e seu pai pelo valor
     */
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

    /**
     * Método principal para remover um nó da árvore ou de uma sub-árvore
     */
    public void remover(int valor) {
        NoRefDTO noRef =  getNoRef(valor);

        // Utilizar o NoRefDTO para acessar o nó a ser removido e seu pai
        No noParaRemover = noRef.no();
        No noPai = noRef.pai();

        if (!NoUtils.noPossuiFilhos(noParaRemover)) {
            // Caso 1: Nó folha
            removerNoSemFilhos(noParaRemover, noPai);
        } else {
            if (NoUtils.noPossuiApenasFilhoADireita(noParaRemover)) {
                // Caso 2: Nó possui apenas filho à direita
                removerNoPossuiApenasFilhoADireita(noParaRemover, noPai);
            } else if (NoUtils.noPossuiApenasFilhoAEsquerda(noParaRemover)) {
                // Caso 3: Nó possui apenas filho à esquerda
                removerNoPossuiApenasFilhoAEsquerda(noParaRemover, noPai);
            } else {
                // Caso 4: Nó possui dois filhos
                removerNoComDoisFilhos(noParaRemover);
            }
        }

    }

    private void removerNoSemFilhos(No noParaRemover, No noPai) {
        // O nó a ser removido é a raiz da árvore?
        if(noParaRemover.equals(arvore.getNoRaiz())) {
            // Se sim, a raiz da árvore se torna nula
            arvore.setNoRaiz(null);
        } else {
            // O nó a ser removido está sendo referenciado à esquerda de seu pai?
            if (NoUtils.noEhFilhoEsquerdo(noParaRemover, noPai)) {
                // Se sim, o apontamento a esquerda do pai vai apontar para nulo
                noPai.setEsquerda(null);
            } else {
                // Se não, o apontamento à direita do pai vai apontar para nulo
                noPai.setDireita(null);
            }
        }
    }

    private void removerNoPossuiApenasFilhoADireita(No noParaRemover, No noPai) {
        // O nó a ser removido está sendo referenciado à esquerda de seu pai?
        if (NoUtils.noEhFilhoEsquerdo(noParaRemover, noPai)) {
            // Se sim, o apontamento a esquerda do pai vai apontar para o filho à direita do nó a ser removido
            noPai.setEsquerda(noParaRemover.getDireita());
        } else {
            // Se não, o apontamento à direita do pai vai apontar para o filho à direita do nó a ser removido
            noPai.setDireita(noParaRemover.getDireita());
        }
    }

    private void removerNoPossuiApenasFilhoAEsquerda(No noParaRemover, No noPai) {
        // O nó a ser removido está sendo referenciado à esquerda de seu pai?
        if (NoUtils.noEhFilhoEsquerdo(noParaRemover, noPai)) {
            // Se sim, o apontamento a esquerda do pai vai apontar para o filho à esquerda do nó a ser removido
            noPai.setEsquerda(noParaRemover.getEsquerda());
        } else {
            // Se não, o apontamento a direita do pai vai apontar para o filho à esquerda do nó a ser removido
            noPai.setDireita(noParaRemover.getEsquerda());
        }
    }

    private void removerNoComDoisFilhos(No noParaRemover) {
        // Guardamos uma cópia da referência do nó sucessor em uma variável
        No noSucessor = getSucessor(noParaRemover);

        /* Removemos o nó sucessor real da árvore, porque já alocamos uma cópia de sua referência em uma variável
           Nessa iteração, o nó sucessor não terá filhos à esquerda, pois ele é o menor valor na sub-árvore direita */
        remover(noSucessor.getValor());

        // Substituímos o valor do nó a ser removido pelo valor do nó sucessor
        noParaRemover.setValor(noSucessor.getValor());

    }

    /**
     * Método auxiliar para retornar o sucessor de um nó (menor valor na sub-árvore direita)
     */
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
