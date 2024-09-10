package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class ThreadAtleta extends Thread {

    private static final Semaphore armas = new Semaphore(5); // Máximo de 5 armas disponíveis
    private static final Random random = new Random();
    private static final int DISTANCIA_CORRIDA = 3000; // 3 km em metros
    private static final int DISTANCIA_CICLISMO = 5000; // 5 km em metros
    private static final int NUM_TIROS = 3;
    
    private final int idAtleta;
    private int pontuacaoTiro;

    public ThreadAtleta(int idAtleta) {
        this.idAtleta = idAtleta;
    }

    @Override
    public void run() {
        try {
            // Fase de corrida
            correr();
            
            // Fase de tiro
            fazerTiros();
            
            // Fase de ciclismo
            pedalar();
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void correr() throws InterruptedException {
        int velocidade = 20 + random.nextInt(6); // Entre 20 e 25 m/30ms
        long tempoCorrida = (DISTANCIA_CORRIDA / velocidade) * 30; // Tempo em milissegundos
        System.out.println("Atleta #" + idAtleta + " está correndo por " + tempoCorrida + " ms.");
        Thread.sleep(tempoCorrida);
    }

    private void fazerTiros() throws InterruptedException {
        armas.acquire(); // Espera pela disponibilidade da arma
        System.out.println("Atleta #" + idAtleta + " pegou uma arma.");
        
        int totalTiros = 0;
        for (int i = 0; i < NUM_TIROS; i++) {
            int tempoTiro = 500 + random.nextInt(2500); // Entre 0,5 e 3 segundos
            int pontuacao = random.nextInt(11); // Pontuação de 0 a 10
            totalTiros += pontuacao;
            System.out.println("Atleta #" + idAtleta + " disparou tiro " + (i + 1) + " e obteve " + pontuacao + " pontos.");
            Thread.sleep(tempoTiro);
        }
        
        pontuacaoTiro = totalTiros;
        System.out.println("Atleta #" + idAtleta + " terminou os tiros com um total de " + pontuacaoTiro + " pontos.");
        armas.release(); // Libera a arma para outro atleta
    }

    private void pedalar() throws InterruptedException {
        int velocidade = 30 + random.nextInt(11); // Entre 30 e 40 m/40ms
        long tempoCiclismo = (DISTANCIA_CICLISMO / velocidade) * 40; // Tempo em milissegundos
        System.out.println("Atleta #" + idAtleta + " está pedalando por " + tempoCiclismo + " ms.");
        Thread.sleep(tempoCiclismo);
    }

    public int getPontuacaoTotal() {
        int pontosCorrida = 250; // Pontuação inicial de corrida
        pontosCorrida -= (idAtleta - 1) * 10; // Reduz 10 pontos para cada atleta que chega depois
        if (pontosCorrida < 10) pontosCorrida = 10;
        return pontosCorrida + pontuacaoTiro;
    }

    public int getIdAtleta() {
        return idAtleta;
    }
}
