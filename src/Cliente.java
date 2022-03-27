import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	private static Tablero t = new Tablero();
	private static String[][] tablero = null;

	private static int numJudador;

	private static Scanner sc1 = new Scanner(System.in);

	private static Socket sc;
	public static void main(String[] args) {

		try {

			// se crea la conexión

			String host = "localhost";

			sc = new Socket(host, 2500); // conexión

			System.out.println(" 		 Elija el modo de juego");

			System.out.println();

			System.out.println("Opcion 1:  Contra otro jugador");

			System.out.println();

			System.out.println("Opcion 2:  Contra la maquina");

			//			System.out.println();
			//
			//			System.out.println("Opcion 3:  Salir ");

			int opcion = Integer.parseInt(sc1.nextLine());
			t.iniciarTablero();

			OutputStream ostream = sc.getOutputStream();

			ObjectOutputStream s = new ObjectOutputStream(ostream);

			if (opcion == 1) {

				System.out.println("Esperando a otro jugador");
			}

			s.writeInt(opcion);
			s.flush();
			
			InputStream istream = sc.getInputStream();

			ObjectInputStream in = new ObjectInputStream(istream);

			tablero = t.getTablero();
			s.writeObject(t.getTablero());

			s.flush();

			t.setTurno(in.readInt());

			System.out.println("Usted es el Jugador " + t.getTurno());

			numJudador = t.getTurno();

			switch (opcion) {
			case 1:

				jugarDos(in, s);
				break;

			case 2:
				JugarMaquina(in, s);
				break;
			}


		} catch (Exception e) {

			System.err.println("excepcion " + e.toString() );

			e.printStackTrace() ;

		}


	}


	private static void jugarDos(ObjectInputStream in, ObjectOutputStream s) {
		boolean ganado = false;
		int fila;
		int columna;
		boolean ocupada = false;

		boolean jugando = true;
		try {

			while (jugando) { // Ejecucion continua mientra no se haya ganado

				ganado = in.readBoolean();
				
				if(!ganado){ // Comprueba que no se ha ganado
	
					for (int i = 0; i < tablero.length; i++) { // Lee el tablero de la jugada anterior

						for (int j = 0; j < tablero[i].length; j++) {

							tablero[i][j] = in.readUTF();
						
						}
					}

					System.out.println();
					System.out.println("Tablero del Jugador " + t.getTurno());
					System.out.println();
					t.imprimirTablero(tablero);
					
					// Lee por teclado y envia al servidor las coordenadas
					
					System.out.println("Introduzca la fila");
					fila = Integer.parseInt(sc1.nextLine());

					System.out.println("Introduzca la columna");
					columna = Integer.parseInt(sc1.nextLine());

					s.writeInt(fila);

					s.writeInt(columna);

					s.flush();

					ocupada = in.readBoolean();

					if (!ocupada) { // Comprueba si la coordenada esta ocupada 
					
			
						for (int i = 0; i < tablero.length; i++) { // Lee el tablero de la jugada actual

							for (int j = 0; j < tablero[i].length; j++) {

								tablero[i][j] = in.readUTF();
							
							}

						}

						t.imprimirTablero(tablero);
						t.cambiarTurno();

 
					} else { // Si esta ocuapda pide otra coordenada repitiendo la ejecucion sin guardar el movimiento

						System.out.println("La posicion (" + fila + ", " + columna + ") ya esta ocupada");
					}


				} else {

					jugando = false;
				}
			}

			for (int i = 0; i < tablero.length; i++) { // Lee el ultimo tablero con el que se ha finalizado el juego

				for (int j = 0; j < tablero[i].length; j++) {

					tablero[i][j] = in.readUTF();
				
				}

			}
			
			System.out.println(); 
			System.out.println();
			
			t.imprimirTablero(tablero);

			System.out.println(); 
			System.out.println();
			System.err.println(in.readUTF());
			System.out.println();
			
			sc.close();

			System.out.println();

			System.out.println("�Desea volver a jugar?. Pulse 1 para confirmar, 0 para salir");

			System.out.println();

			int v = Integer.parseInt(sc1.nextLine());


			if (v == 1) {

								main(null);

			} else {

				System.err.println("Usted ha salido");
			}

		} catch (Exception e) {
			System.err.println("excepcion " + e.toString() );

			e.printStackTrace() ;
		}
	}

	private static void JugarMaquina(ObjectInputStream in, ObjectOutputStream s) {

		boolean ganado = false;
		int fila;
		int columna;
		boolean ocupada = false;

		boolean jugando = true;
		try {

			while (jugando) { // Ejecucion continua hasta que se gane

				ganado = in.readBoolean();

				if(!ganado){ // Comprueba si se ha ganado

					if (t.getTurno() != 2) { // Detecta que turno es si es el jugador pide por teclado las coordenadas 

						System.out.println("Introduzca la fila");
						fila = Integer.parseInt(sc1.nextLine());

						System.out.println("Introduzca la columna");
						columna = Integer.parseInt(sc1.nextLine());

						s.writeInt(fila);

						s.writeInt(columna);

						s.flush();

					} else { // Sino las genera aleatoriamente

						System.out.println();

						System.out.println("Turno de la Maquina");

						System.out.println();

						fila = (int)((Math.random()*3));

						columna = (int)((Math.random()*3));

						s.writeInt(columna);

						s.writeInt(fila);

						s.flush();
					} 

					ocupada = in.readBoolean();

					if (!ocupada) {
						
						for (int i = 0; i < tablero.length; i++) { // Lee el tablero de la jugad actual

							for (int j = 0; j < tablero[i].length; j++) {

								tablero[i][j] = in.readUTF();
							}

						}

						t.imprimirTablero(tablero);
						t.cambiarTurno();

					} else {

						System.out.println("La posicion (" + fila + ", " + columna + ") ya esta ocupada");
					}

				} else {

					jugando = false;
				}
			}

			System.out.println();

			System.err.println(in.readUTF());
			
			sc.close();

			System.out.println();

			System.out.println("�Desea volver a jugar?. Pulse 1 para confirmar, 0 para salir");

			System.out.println();

			int v = Integer.parseInt(sc1.nextLine());


			if (v == 1) {

								main(null);

			} else {

				System.err.println("Usted ha salido");
			}

		} catch (Exception e) {

			e.printStackTrace();
			
		}

	}

}
