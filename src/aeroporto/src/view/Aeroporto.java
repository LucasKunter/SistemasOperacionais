package view;

import controller.threadAeroporto;

public class Aeroporto {

    public static void main(String[] args) {
        // Cria e inicia 12 threads de aviões
        for (int i = 0; i < 12; i++) {
        	threadAeroporto aviao = new threadAeroporto("Avião " + (i + 1));
            aviao.start();
        }
    }
}
