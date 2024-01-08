package server;

import common.database.Data;
import common.database.Partida;

import java.util.*;

public class Interfaz {

    public Interfaz() {}

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (true) {
            System.out.println();
            System.out.println("Por favor, seleccione una opción:");
            System.out.println("1.- Información del servidor");
            System.out.println("2.- Estado de las partidas en curso");
            System.out.println("3.- Salir");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println();
                    mostrarInformacionServidor();
                    break;
                case 2:
                    System.out.println();
                    listarPartidas();
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
    }

    private void mostrarInformacionServidor() {
        try {
            Date fechaInicio = Servidor.getFechaInicio();

            System.out.println("Información del servidor:");
            System.out.println("Fecha de inicio del servidor: " + fechaInicio);
            System.out.println("URL del servicio autenticación: " + Servidor.getURL_nombre_aut());
            System.out.println("URL del servicio gestor: " + Servidor.getURL_nombre_ges());
            System.out.println("Estado: en ejecución");

        } catch (Exception e) {
            System.out.println("Error al obtener la información del servidor.");
        }
    }

    private void listarPartidas() {
        try {
            HashMap<Integer, Partida> partidasEnEjecucion = Servidor.getPartidasEnEjecucion();

            System.out.println("Partidas en ejecución:");

            assert partidasEnEjecucion != null;
            if (partidasEnEjecucion.isEmpty()) {
                System.out.println("No hay partidas en ejecución.");
            } else {
                for (Map.Entry<Integer, Partida> partida : partidasEnEjecucion.entrySet()) {
                    System.out.println("Partida " + partida.getKey() + ": " + partida.getValue().getPlayerOne().getUsername() + " vs " + partida.getValue().getPlayerTwo().getUsername());
                }
            }

        } catch (Exception e) {
            System.out.println("Error al obtener la lista de partidas en ejecución.");
        }
    }
}

