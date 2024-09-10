package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class threadAeroporto extends Thread {

    private String nome;
    private static final Semaphore areaDecolagem = new Semaphore(2); // Máximo de 2 aviões na área de decolagem
    private static final Random random = new Random();

    public threadAeroporto(String nome) {
        this.nome = nome;
    }

    @Override
    public void run() {
        tentarDecolagem();
    }

    private void tentarDecolagem() {
        // Simula o processo de decolagem
        System.out.println(nome + " está se preparando para decolar.");

        try {
            areaDecolagem.acquire(); // Tenta entrar na área de decolagem
            System.out.println(nome + " entrou na área de decolagem.");

            realizarFase("taxiar", 500, 1000);
            realizarFase("decolagem", 600, 800);
            realizarFase("afastamento", 300, 800);

            System.out.println(nome + " completou a decolagem e está saindo da área de decolagem.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            areaDecolagem.release(); // Libera a área de decolagem
        }
    }

    private void realizarFase(String fase, int minMs, int maxMs) throws InterruptedException {
        int duracao = minMs + random.nextInt(maxMs - minMs + 1);
        System.out.println(nome + " está na fase de " + fase + " por " + duracao + " ms.");
        Thread.sleep(duracao);
    }
}
