package controller;

import java.util.concurrent.Semaphore;

public class ThreadPrato extends Thread {

    private int idPrato;
    private static int pratoPronto;
    private Semaphore semaforo;
    private static Semaphore semaforoEntrega = new Semaphore(1);

    public ThreadPrato(int idPrato, Semaphore semaforo){
        this.idPrato = idPrato;
        this.semaforo = semaforo;
    }        

    @Override
    public void run() {         
        pratoCozinhando(idPrato);

        try {
            semaforo.acquire();
            pratoEntregar();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaforo.release();
        }
    }    

    private void pratoCozinhando(int idPrato) {
        int tempoTotal;
        int tempoCozimento = 100; 
        String nomePrato;

      
        if (idPrato % 2 != 0) { 
            nomePrato = "Sopa de Cebola";
            tempoTotal = (int) (Math.random() * (800 - 500) + 500); 
        } else { 
            nomePrato = "Lasanha a Bolonhesa";
            tempoTotal = (int) (Math.random() * (1200 - 600) + 600); 
        }

        System.out.println("O prato #" + idPrato + " (" + nomePrato + ") iniciou o cozimento!");

        int tempoDecorrido = 0;
        while (tempoDecorrido < tempoTotal) {
            try {
                sleep(tempoCozimento);
                tempoDecorrido += tempoCozimento;
                int percentagem = (int) ((tempoDecorrido / (float) tempoTotal) * 100);
                System.out.println("O percentual de cozimento do " + nomePrato + " #" + idPrato + " está em " + percentagem + "% !");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("O " + nomePrato + " #" + idPrato + " ficou pronto!");
    }

    private void pratoEntregar() {
        try {
            semaforoEntrega.acquire();
            Thread.sleep(500); 
            pratoPronto++;
            System.out.println("O prato #" + idPrato + " foi entregue!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaforoEntrega.release();
        }
    }
}
