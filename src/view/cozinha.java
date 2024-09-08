package view;

import java.util.concurrent.Semaphore;
import controller.ThreadPrato;

public class cozinha {

	public static void main(String[] args) {

		int permissoes = 5;
		Semaphore semaforo = new Semaphore(permissoes);

		for(int idPrato = 1; idPrato < 6 ; idPrato++) {
			Thread tPrato = new ThreadPrato(idPrato, semaforo);
			tPrato.start(); 
		}
		
	}

}
