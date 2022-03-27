
public class Tablero {

	private String[][] tablero;

	private int filas;

	private int columnas;

	private int turno;

	private char x = 'X';

	private char o = 'O';

	public Tablero() {

		this.filas = 3;

		this.columnas = 3;

		tablero = new String[filas][columnas];

		turno = 1;

//		elegirModo();



	}

	public String[][] getTablero() {
		return tablero;
	}

	public int getColumnas() {
		return columnas;
	}
	
	public int getFilas() {
		return filas;
	}
	public void iniciarTablero() {

		for (int i = 0; i < tablero.length; i++) {

			for (int j = 0; j < tablero[i].length; j++) {

				tablero[i][j] = "_";

				System.out.print(tablero[i][j] + " ");

			}

			System.out.println();

		}

	}

	public int getTurno() {
		return turno;
	}
	
	public void setTurno(int turno) {
		this.turno = turno;
	}
	
	public int cambiarTurno() {

		if (turno == 1) {

			turno = 2;

		} else {

			turno = 1;
		}

		return turno;
	}

	public char colocarFicha() {

		if (turno == 1) {

			return x;

		} else {

			return o;
		}

	}

	public void elegirModo(int opcion) {

		switch (opcion) {

		case 1: 

//			System.out.println("Van a jugar dos jugadores: 1er 'X' , 2� 'O'");

//			iniciarTablero();
//
//			turno = 0;
//			jugar();

			break;

		case 2:

			System.out.println("Va a jugar contra la maquina");

			iniciarTablero();

			turno = 0;

			jugarMaquina();

			break;
			
		case 3:

			System.out.println("Usted ha salido");


			break;

		}



	}

	private void jugar() {

		int contador = 0;

		boolean[][] ocupada = new boolean[filas][columnas];		

		do {

			if (contador < 9) {

				if (!Coordenada.comprobarFilas(tablero)) {

					if (!Coordenada.comprobarColumnas(tablero)) {

						if (!Coordenada.comprobarDiagonal(tablero)) {

							if (!Coordenada.comprobarDiagonalInversa(tablero)) {

								System.out.println();

								Coordenada.leerCoordenada();

								if (!ocupada[Coordenada.getFila()][Coordenada.getColumna()]) {

									tablero[Coordenada.getFila()][Coordenada.getColumna()] = colocarFicha() + "";

									ocupada[Coordenada.getFila()][Coordenada.getColumna()] = true;

									for (int j2 = 0; j2 < tablero.length; j2++) {

										for (int k = 0; k < tablero[j2].length; k++) {

											System.out.print(tablero[j2][k] + " ");
										}

										System.out.println();
									}

									cambiarTurno();

									contador++;

								} else {

									System.out.println("La posicion (" + Coordenada.getFila() + ", " + Coordenada.getColumna() + ") ya esta ocupada");

								}

							} 

						}

					}

				} 

			} else {

				System.err.println("  EMPATE " );

				System.out.println();

//				volverAJugar();

			}

		} while (!Coordenada.comprobarFilas(tablero) && !Coordenada.comprobarColumnas(tablero) 

				&& !Coordenada.comprobarDiagonal(tablero) && !Coordenada.comprobarDiagonalInversa(tablero));

		System.err.println("Ha ganado el jugador " + this.turno);

		System.out.println();

//		volverAJugar();


	}

	private void jugarMaquina() {

		int contador = 0;

		boolean[][] ocupada = new boolean[filas][columnas];			



		do {
			if (contador < 9) {

				if (!Coordenada.comprobarFilas(tablero)) {

					if (!Coordenada.comprobarColumnas(tablero)) {

						if (!Coordenada.comprobarDiagonal(tablero)) {

							if (!Coordenada.comprobarDiagonalInversa(tablero)) {

								System.out.println();

								if (turno != 1) {

									Coordenada.leerCoordenada();

								} else {

									System.out.println("Turno de la maquina");

									Coordenada.leerCoordenada2();
								}



								if (!ocupada[Coordenada.getFila()][Coordenada.getColumna()]) {

									tablero[Coordenada.getFila()][Coordenada.getColumna()] = colocarFicha() + "";

									ocupada[Coordenada.getFila()][Coordenada.getColumna()] = true;

									for (int j2 = 0; j2 < tablero.length; j2++) {

										for (int k = 0; k < tablero[j2].length; k++) {

											System.out.print(tablero[j2][k] + " ");
										}

										System.out.println();
									}

									cambiarTurno();

									contador++;

								} else {

									System.out.println("La posicion (" + Coordenada.getFila() + ", " + Coordenada.getColumna() + ") ya esta ocupada");

								}

							} 

						}

					}

				} 

			} else {

				System.err.println("  EMPATE " );

				System.out.println();

//				volverAJugar();
			}

		} while (!Coordenada.comprobarFilas(tablero) && !Coordenada.comprobarColumnas(tablero) 

				&& !Coordenada.comprobarDiagonal(tablero) && !Coordenada.comprobarDiagonalInversa(tablero));

		System.err.println("Ha ganado el jugador " + this.turno);

		System.out.println();

//		volverAJugar();

	}

//	private void volverAJugar() {
//
//		Scanner sc2 = new Scanner(System.in);
//
//		System.out.println("Si quiere volver a jugar pulse 1, en caso contrario pulse 0");
//
//		int opcion = sc2.nextInt();
//
//		//		while (opcion != 2) {
//
//		switch (opcion) {
//
//		case 1:
//
//			System.out.println("Usted quiere volver a jugar");
//
//			elegirModo();
//
//			System.out.println();
//
//			break;
//
//		case 0:				
//
//			imprimirTablero();
//			break;
//		}
//		//		}
//
//		sc2.close();
//	}
	public void imprimirTablero(String[][] tablero) {

		for (int i = 0; i < tablero.length; i++) {

			for (int j = 0; j < tablero[i].length; j++) {

				System.out.print(tablero[i][j] + " ");
			}

			System.out.println();
		}

	}
}
