import service.ArvoreService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ArvoreService arvoreService = new ArvoreService();
        List<Integer> valoresArvore = new ArrayList<>(List.of(12,-6,32,16,0,-80,90,5,3,50,42,44,46, 14));

        arvoreService.criarArvore(valoresArvore);

        arvoreService.exibirArvore();

        // Nó com dois filhos
        System.out.println("Removendo o nó de valor: 32");
        arvoreService.remover(32);
        arvoreService.exibirArvore();

        // Nó sem filhos
        System.out.println("Removendo o nó de valor: 46");
        arvoreService.remover(46);
        arvoreService.exibirArvore();

        // Nó apenas com filho a direita
        System.out.println("Removendo o nó de valor: 50");
        arvoreService.remover(50);
        arvoreService.exibirArvore();

        // Nó apenas com filho a esquerda
        System.out.println("Removendo o nó de valor: 16");
        arvoreService.remover(16);
        arvoreService.exibirArvore();

    }
}