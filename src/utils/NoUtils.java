package utils;

import model.No;

public class NoUtils {

    public static boolean noPossuiFilhos(No no) {
        return no.getDireita() != null || no.getEsquerda() != null;
    }

    public static boolean noPossuiApenasFilhoAEsquerda(No no) {
        return no.getEsquerda() != null && no.getDireita() == null;
    }

    public static boolean noPossuiApenasFilhoADireita(No no) {
        return no.getDireita() != null && no.getEsquerda() == null;
    }

    public static boolean noPossuiDoisFilhos(No no) {
        return no.getDireita() != null && no.getEsquerda() != null;
    }

    public static boolean noEhFilhoEsquerdo(No no, No pai) {
        return pai.getEsquerda() == no;
    }

}
