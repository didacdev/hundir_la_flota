package server;

import common.database.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Interfaz {

    private Servidor servidor;

    public Interfaz(Servidor servidorDatos) {
        this.servidor = servidorDatos;
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 3) {
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

        scanner.close();
    }

    private void mostrarInformacionServidor() {
        try {
            Date fechaInicio = servidor.getFechaInicio();

            System.out.println("Información del servidor:");
            System.out.println("Fecha de inicio del servidor: " + fechaInicio);
            System.out.println("Estado: en ejecución");

        } catch (Exception e) {
            System.out.println("Error al obtener la información del servidor.");
        }
    }

    private void listarPartidas() {
        try {
            List<Integer> partidasEnEjecucion = servidor.getPartidasEnEjecucion();

            System.out.println("Partidas en ejecución:");

            for (Integer partidaId : partidasEnEjecucion) {
                System.out.println("Partida ID: " + partidaId);
            }

        } catch (Exception e) {
            System.out.println("Error al obtener la lista de partidas en ejecución.");
        }
    }
}

