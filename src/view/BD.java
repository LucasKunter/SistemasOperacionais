package view;

import java.util.concurrent.Semaphore;
import controller.*;

public class BD {

	public static void main(String[] args) {

		int permissoes = 5;
		Semaphore semaforo = new Semaphore(permissoes);

		for(int idProcesso = 1; idProcesso < 22 ; idProcesso++) {
			Thread tPrato = new threadServidor(idProcesso, semaforo);
			tPrato.start(); 
		}
		
	}

}
