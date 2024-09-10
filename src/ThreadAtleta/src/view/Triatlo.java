package view;

import controller.ThreadAtleta;

import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;

public class Triatlo {

    public static void main(String[] args) {
        final int NUM_ATLETAS = 25;
        List<ThreadAtleta> atletas = new ArrayList<>();
        
        // Cria e inicia as threads de atletas
        for (int i = 0; i < NUM_ATLETAS; i++) {
            ThreadAtleta atleta = new ThreadAtleta(i + 1);
            atletas.add(atleta);
            atleta.start();
        }

        // Espera todas as threads terminarem
        for (ThreadAtleta atleta : atletas) {
            try {
                atleta.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Ordena atletas por pontuação total (do maior para o menor)
        atletas.sort(Comparator.comparingInt(ThreadAtleta::getPontuacaoTotal).reversed());

        // Exibe o resultado final
        System.out.println("Ranking final:");
        for (ThreadAtleta atleta : atletas) {
            System.out.println("Atleta #" + atleta.getIdAtleta() + " - Pontuação Total: " + atleta.getPontuacaoTotal());
        }
    }
}
