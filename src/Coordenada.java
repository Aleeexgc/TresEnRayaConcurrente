import java.util.Scanner;

public class Coordenada {

	private static int fila;

	private static int columna;


	public static void leerCoordenada() {

		Scanner sc = new Scanner(System.in);

		System.out.println("Pulse 4 en cualquier momento para guardar la partida");
		
		System.out.println();
		
		System.out.println("Introduzca la fila");

		fila = sc.nextInt();

		System.out.println("Introsuzca la columna");

		columna = sc.nextInt();

	}

	public static void leerCoordenada2() {


		fila = (int)((Math.random()*3));

		columna = (int)((Math.random()*3));

		System.out.println(fila + " " + columna);

	}


	public static int getFila() {

		return fila;
	}

	public static int getColumna() {

		return columna;
	}

	public static boolean comprobarFilas(String[][] aux) {

		boolean contador = false;

		boolean contador1 = false;

		boolean max = false;

		for (int i = 0; i < aux.length; i++) {

			for (int j = 0; j < aux[i].length; j++) {

				if (aux[0][j].equals("X")) {

					if (aux[1][j].equals("X")) {

						if (aux[2][j].equals("X")) {

							contador = true;

							max = contador;
						}
					}

				} else if (aux[0][j].equals("O")) { 

					if (aux[1][j].equals("O")) {

						if (aux[2][j].equals("O")) {

							contador1 = true;

							max = contador1;

						}
					}

				}


			}

		}

		return max;
	}

	public static boolean comprobarColumnas(String[][] aux) {

		boolean contador = false;

		boolean contador1 = false;

		boolean max = false;

		for (int i = 0; i < aux.length; i++) {

			for (int j = 0; j < aux[i].length; j++) {

				if (aux[i][0].equals("X")) { 

					if (aux[i][1].equals("X")) {

						if (aux[i][2].equals("X")) {

							contador = true;

							max = contador;
						}
					}

				} else if (aux[i][0].equals("O")) {

					if (aux[i][1].equals("O")) {

						if (aux[i][2].equals("O")) {

							contador1 = true;

							max = contador1;
						}

					}

				}


			}

		}

		return max;
	}

	public static boolean comprobarDiagonal(String[][] aux) {

		boolean contador = false;

		boolean contador1 = false;

		boolean max = false;

		for (int i = 0; i < aux.length; i++) {

			for (int j = 0; j < aux[i].length; j++) {

				if (aux[0][0].equals("X")) {

					if (aux[1][1].equals("X")) {

						if (aux[2][2].equals("X")) {

							contador = true;

							max = contador;
						}

					} 

				} else if (aux[0][0].equals("O")) {

					if (aux[1][1].equals("O")) {

						if (aux[2][2].equals("O")) {

							contador1 = true;

							max = contador1;

						}
					}
				}

			}

		}

		return max;
	}

	public static boolean comprobarDiagonalInversa(String[][] aux) {

		boolean contador = false;

		boolean contador1 = false;

		boolean max = false;

		for (int i = 0; i < aux.length; i++) {

			for (int j = 0; j < aux[i].length; j++) {

				if (aux[0][2].equals("X")) {

					if (aux[1][1].equals("X")) {

						if (aux[2][0].equals("X")) {

							contador = true;

							max = contador;
						}

					} 

				} else if (aux[0][2].equals("O")) {

					if (aux[1][1].equals("O")) {

						if (aux[2][0].equals("O")) {

							contador1 = true;

							max = contador1;

						}
					}
				}

			}

		}

		return max;
	}
}
