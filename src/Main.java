import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrdenacaoService ordenacaoService = new OrdenacaoService();
        ArvoreService arvoreService = new ArvoreService();
        List<Integer> valoresArvore = new ArrayList<>(List.of(15,-6,32,16,0,-80,105,-3));

        arvoreService.criarArvore(valoresArvore);

        No no = arvoreService.getNo(32);



    }
}