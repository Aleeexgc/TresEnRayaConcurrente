import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TratarPeticion extends Thread {

	private static String[][] tablero = null;
	private String[][] tablero2 = null;


	private static boolean[][] ocupadas = null;		

	private static boolean ganado = false;
	private static int fila;
	private static int columna;

	private static boolean ocupada = false;

	ServerSocket serverAddr = null;

	Socket sc = null;
	ObjectInputStream in;
	ObjectOutputStream s;
	ObjectInputStream in2;
	ObjectOutputStream s2;

	Socket sc2 = null;

	static Tablero t = new Tablero();
	public TratarPeticion(ObjectInputStream in, ObjectOutputStream s, ObjectInputStream in2, ObjectOutputStream s2, Socket sc,Socket sc2) {

		this.in = in;
		this.s = s;

		this.in2 = in2;
		this.s2 = s2;

		this.sc = sc;

		this.sc2 = sc2;

	}
	
	public TratarPeticion(ObjectInputStream in, ObjectOutputStream s, Socket sc) {

		this.in = in;
		this.s = s;
		this.sc = sc;

	}
	public void run() {

		try {

			ocupadas = new boolean[3][3];

			if (sc2 != null) { // Comprueba si hay dos jugadores o uno 

				tablero = (String[][]) in.readObject();

				tablero2 = (String[][]) in2.readObject();

				s.writeInt(1);
				s.flush();
				
				s2.writeInt(2);
				s.flush();
				
				jugarDos(in, s, in2, s2);

			} else {
				
				tablero = (String[][]) in.readObject();
				
				s.writeInt(1);
				s.flush();
				
				jugarMaquina(in, s);

			}

			sc.close();

			if (sc2 != null) {
				sc2.close();

			}
		} catch(Exception e) {

			System.err.println("excepcion " + e.toString() );

			e.printStackTrace() ;

		}
	}

	// Metodo para dos jugadores
	private static void jugarDos(ObjectInputStream in, ObjectOutputStream s, ObjectInputStream in2, ObjectOutputStream s2) {
		int contador = 0;
		t.setTurno(1); // Inicio el turno en 1 que son las X

		try {


			// Coprubeba que nadie ha ganado el juego o se hay empate
			while (!Coordenada.comprobarFilas(tablero) && !Coordenada.comprobarColumnas(tablero) 

					&& !Coordenada.comprobarDiagonal(tablero) && !Coordenada.comprobarDiagonalInversa(tablero) && contador < 9 ) {

				if (t.getTurno() == 1) { // Comprueba qen que turno esta actualmente

					ganado = false;

					s.writeBoolean(ganado); // Envia la condicion que para el juego
					s.flush();

					for (int i = 0; i < tablero.length; i++) { // Envia el tablero de la jugada anterior

						for (int j = 0; j < tablero[i].length; j++) {

							s.writeUTF(tablero[i][j]);
							s.flush();
						}
					}

					// Lee las coordenadas
					
					fila = in.readInt(); 

					columna = in.readInt();

					if (!ocupadas[fila][columna]) { // Comprueba si la coordenada eesta ocupada o no 

						tablero[fila][columna] = t.colocarFicha() + "";

						ocupadas[fila][columna] = true;

						ocupada = false;
						s.writeBoolean(ocupada); // Envia si la coordenada esta ocupada o no
						s.flush();

						for (int i = 0; i < tablero.length; i++) { // Envia el tablero de la jugada actual

							for (int j = 0; j < tablero[i].length; j++) {

								s.writeUTF(tablero[i][j]);
								s.flush();

							}
						}

						contador++; // Incrementa en uno el valor que cuenta las jugada 
						t.cambiarTurno(); // Cambia el turno

					} else { // Si la coordena esta ocupada envia true

						ocupada = true;
						s.writeBoolean(ocupada);

						s.flush();

					}


				} else { // Si el turno no es uno

					// Envio el valor de ganado que para el juego si este es cierto
					
					ganado = false;

					s2.writeBoolean(ganado);
					s2.flush();

					// Envio tablero de la jugada anterior
					for (int i = 0; i < tablero.length; i++) { 

						for (int j = 0; j < tablero[i].length; j++) {

							s2.writeUTF(tablero[i][j]);
							s2.flush();
						}
					}

					// Leee las coordenadas
					
					fila = in2.readInt();

					columna = in2.readInt();

					if (!ocupadas[fila][columna]) { // comprueba si la coordenada esta ocupada o no 

						tablero[fila][columna] = t.colocarFicha() + "";

						ocupadas[fila][columna] = true;

						ocupada = false;

						s2.writeBoolean(ocupada);
						s2.flush();

						for (int i = 0; i < tablero.length; i++) { // Envia el tablero de la jugada actual

							for (int j = 0; j < tablero[i].length; j++) {


								s2.writeUTF(tablero[i][j]);
								s2.flush();


							}
						}

						contador++;
						t.cambiarTurno(); // Cambia el turno

					} else {

						ocupada = true;

						s2.writeBoolean(ocupada);

						s2.flush();

					}

				}


			}
			
			// Sale del while y pone la condicion ganado a true Y la envia a los dos Jugadores

			ganado = true;
			t.cambiarTurno();

			s.writeBoolean(ganado);
			s.flush();

			s2.writeBoolean(ganado);
			s2.flush();

			for (int i = 0; i < tablero.length; i++) { // Envia el tablero del ultima jugada (Ganadora) 

				for (int j = 0; j < tablero[i].length; j++) {
					s.writeUTF(tablero[i][j]);
					s.flush();

					s2.writeUTF(tablero[i][j]);
					s2.flush();
				}
			}

			if (ganado && contador == 9) { // Comprueba Si se ha ganado o hay empate

				System.out.println();
				s.writeUTF("EMPATE");
				s.flush();

				s2.writeUTF("EMPATE");
				s2.flush();
				
			} else {  // No hay empate
				
				System.out.println();
				System.out.println();
				
				if (t.getTurno() == 1) { // Indica quien ha ganado y quien ha perdido

					s.writeUTF("Ha ganado el jugador " + t.getTurno());
					s.flush();
					t.cambiarTurno();
					s2.writeUTF("Ha perdido el jugador " + t.getTurno());
					s2.flush();
				} else {

					s.writeUTF("Ha perdido el jugador " + t.getTurno());
					s.flush();
					t.cambiarTurno();
					s2.writeUTF("Ha ganado el jugador " + t.getTurno());
					s2.flush();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	private static void jugarMaquina(ObjectInputStream in, ObjectOutputStream s) throws IOException {
		
		int contador = 0;

		t.setTurno(1);
		try {


			while (!Coordenada.comprobarFilas(tablero) && !Coordenada.comprobarColumnas(tablero) 

					&& !Coordenada.comprobarDiagonal(tablero) && !Coordenada.comprobarDiagonalInversa(tablero) && contador < 9 ) { // Comrpueba si se ha ganado 

				s.writeBoolean(ganado);
				s.flush();
				
				// Lee las coordenadas

				fila = in.readInt();

				columna = in.readInt();

				if (!ocupadas[fila][columna]) { // Comprueba si la coordenada esta ocupada o no

					tablero[fila][columna] = t.colocarFicha() + "";

					ocupadas[fila][columna] = true;

					ocupada = false;

					s.writeBoolean(ocupada);

					for (int i = 0; i < tablero.length; i++) { // Envia el tablero de la jugada actual

						for (int j = 0; j < tablero[i].length; j++) {

							s.writeUTF(tablero[i][j]);

							s.flush();
						}
					}

					s.flush();
					t.cambiarTurno(); // Cambia de turno

					contador++;

				} else {

					ocupada = true;

					s.writeBoolean(ocupada);

					s.flush();
				}

			}

			ganado = true;

			s.writeBoolean(ganado);
			s.flush();

			if (ganado && contador == 9) { // Comprueba ganador o empate

				s.writeUTF("EMPATE");
				s.flush();
			} else {

				s.writeUTF("Ha ganado el jugador " + t.getTurno());
				s.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();			}
	}							



}
