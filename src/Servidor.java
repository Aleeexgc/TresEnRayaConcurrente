import java.net.*;
import java.io.*;
import java.lang.*;

public class Servidor {

	public static void main ( String [] args) {

		ServerSocket serverAddr = null;

		Socket sc = null;
		Socket sc2 = null;

		Socket[] jugadores = new Socket[2];
		int numJugadores = 0;

		try {

			serverAddr = new ServerSocket(2500);

		} catch (Exception e){

			System.err.println("Error creando socket");

		}


		while (true){

			try {

				sc = serverAddr.accept(); // esperando conexion
				System.out.println("Acepto conexion 1");

				InputStream istream = sc.getInputStream();

				ObjectInputStream in = new ObjectInputStream(istream);


				int opcion = in.readInt(); // Lee el tipo de partida

				OutputStream ostream = sc.getOutputStream();

				ObjectOutputStream s = new ObjectOutputStream(ostream);

				if (opcion == 1) {  // comprueba si el modo de juego es de uno o dos jugadores

					sc2 = serverAddr.accept();
					System.out.println("Acepto conexion 2");

					InputStream istream2 = sc2.getInputStream();

					ObjectInputStream in2 = new ObjectInputStream(istream2);
					 
					opcion = in2.readInt(); // Confirma que quiere jugar contra otro jugador
					
					OutputStream ostream2 = sc2.getOutputStream();

					ObjectOutputStream s2 = new ObjectOutputStream(ostream2);

					new TratarPeticion(in, s,in2, s2, sc, sc2).start(); // Inicia un nuevo hilo para dos jugadores

				} else {

					new TratarPeticion(in, s, sc ).start(); // Inicia un nuevo hilo para un jugador
				}

			

			} catch(Exception e) {

				System.err.println("excepcion " + e.toString() );

				e.printStackTrace() ;

			}

		}

	}

}
