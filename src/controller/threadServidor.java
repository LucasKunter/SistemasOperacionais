package controller;

import java.util.concurrent.Semaphore;

public class threadServidor extends Thread {

    private int idThread;
    private  Semaphore semaforoBD;

    public threadServidor(int idThread, Semaphore semaforoBD) {
        this.idThread = idThread;
        this.semaforoBD = semaforoBD;
    }

    @Override
    public void run() {
        try {
            if (idThread % 3 == 1) {
                processarTipo1();
            } else if (idThread % 3 == 2) {
                processarTipo2();
            } else {
                processarTipo0();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processarTipo1() throws InterruptedException {
        realizarCalculo(200, 1000); 
        realizarTransacaoBD(1000);
        realizarCalculo(200, 1000);
        realizarTransacaoBD(1000);
    }

    private void processarTipo2() throws InterruptedException {
        realizarCalculo(500, 1500);
        realizarTransacaoBD(1500);
        realizarCalculo(500, 1500);
        realizarTransacaoBD(1500);
        realizarCalculo(500, 1500);
        realizarTransacaoBD(1500);
    }
  
    private void processarTipo0() throws InterruptedException {
     
        realizarCalculo(1000, 2000);
        realizarTransacaoBD(1500);
        realizarCalculo(1000, 2000);
        realizarTransacaoBD(1500);   
        realizarCalculo(1000, 2000);
        realizarTransacaoBD(1500);
    }

    private void realizarCalculo(int minMs, int maxMs) throws InterruptedException {
        int tempo = minMs + (int) (Math.random() * (maxMs - minMs + 1));
        System.out.println("Thread #" + idThread + " realizando cálculo por " + tempo + " ms.");
        sleep(tempo);
    }

    private void realizarTransacaoBD(int tempoMs) throws InterruptedException {
        try {
        	semaforoBD.acquire();
        	sleep(tempoMs);
        	System.out.println("Thread #" + idThread + " realizando transação com BD por " + tempoMs + " ms.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaforoBD.release();
        }
    }}


