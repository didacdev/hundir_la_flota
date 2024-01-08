package database;

import common.database.Data;

import java.util.HashMap;
import java.util.Scanner;

public class Interfaz {

    private ServicioDatosImpl servidorDatos;

    public Interfaz(ServicioDatosImpl servidorDatos) {
        this.servidorDatos = servidorDatos;
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 3) {
            System.out.println();
            System.out.println("Por favor, seleccione una opción:");
            System.out.println("1.- Información de la base de datos");
            System.out.println("2.- Listar jugadores registrados");
            System.out.println("3.- Salir");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println();
                    mostrarInformacionBaseDatos();
                    break;
                case 2:
                    System.out.println();
                    listarJugadoresRegistrados();
                    break;
                case 3:
                    System.out.println();
                    System.out.println("Saliendo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println();
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
                    break;
            }
        }

        scanner.close();
    }

    private void mostrarInformacionBaseDatos() {
        try {
            Data data = servidorDatos.getData();

            System.out.println("Información de la base de datos:");
            System.out.println("Jugadores registrados: " + data.getRegistredUsers());
            System.out.println("Jugadores conectados: " + data.getOnlineUsers());
            System.out.println("Partidas en espera: " + data.getWaitingGames());
            System.out.println("Partidas en curso: " + data.getStartedGames());
            System.out.println("Partidas creadas: " + data.getCreatedGames());

        } catch (Exception e) {
            System.out.println("Error al obtener la información de la base de datos.");
        }
    }

    private void listarJugadoresRegistrados() {
        try {
            HashMap<String, HashMap<Integer, Integer>> usersList = servidorDatos.getUsersList();

            System.out.println("Jugadores registrados:");

            for (String username : usersList.keySet()) {
                System.out.println("Jugador: " + username);

                for (Integer gameID : usersList.get(username).keySet()) {
                    System.out.println("Partida: " + gameID + " - Puntuación: " + usersList.get(username).get(gameID));
                }
            }

        } catch (Exception e) {
            System.out.println("Error al obtener la lista de jugadores registrados.");
        }
    }
}
