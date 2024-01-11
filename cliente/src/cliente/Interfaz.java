package cliente;

import common.database.Coordinate;
import common.database.Partida;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Interfaz {
    private Boolean isLogged = false;
    private String username;

    public Interfaz() {
    }

    public void iniciar() {

        initialMenu();
        mainMenu();

    }

    // -------------- Menús --------------
    private void initialMenu() {

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (Boolean.FALSE.equals(isLogged)) {
            opcion = 0;
            System.out.println();
            System.out.println("1.- Registrar un nuevo jugador");
            System.out.println("2.- Hacer login");
            System.out.println("3.- Salir");

            try {
                opcion = scanner.nextInt();
            } catch (Exception e) {
                System.out.println();
            }

            switch (opcion) {
                case 1:
                    if (Boolean.TRUE.equals(register())) {
                        System.out.println();
                        System.out.println("Usuario registrado correctamente");
                    } else {
                        System.out.println();
                        System.out.println("Error al registrar el usuario");
                    }
                    break;
                case 2:
                    if (Boolean.TRUE.equals(login())) {
                        System.out.println();
                        System.out.println("Login correcto");
                        isLogged = true;
                    } else {
                        System.out.println();
                        System.out.println("Error al hacer login");
                    }
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println();
                    System.out.println("Opción no válida");
            }
        }
    }

    private void mainMenu() {

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (true) {
            System.out.println();
            System.out.println("1.- Información del jugador");
            System.out.println("2.- Iniciar una partida");
            System.out.println("3.- Listar partidas iniciadas a la espera de contrincante");
            System.out.println("4.- Unirse a una partida ya iniciada");
            System.out.println("5.- Salir");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    mostrarInformacion();
                    break;
                case 2:
                    iniciarPartida();
                    esperarEvento();
                    break;
                case 3:
                    listarPartidas();
                    break;
                case 4:
                    unirsePartida();
                    esperarEvento();
                    break;
                case 5:
                    if (Boolean.TRUE.equals(logeout())) {
                        System.out.println();
                        System.out.println("Logout correcto");
                        isLogged = false;
                        initialMenu();
                    } else {
                        System.out.println();
                        System.out.println("Error al hacer logout");
                    }
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    // -------------- Métodos de login y registro --------------
    private Boolean login() {

        boolean isLogged = false;

        try {
            System.out.println();
            System.out.println("Introduce el nombre de usuario: ");
            Scanner scanner = new Scanner(System.in);
            String nombre = scanner.nextLine();
            System.out.println("Introduce la contraseña: ");
            String password = scanner.nextLine();
            isLogged = Jugador.getServicioAutenticacion().iniciarSesion(nombre, password);

            if (isLogged) username = nombre;

        } catch (Exception e) {
            System.out.println();
            System.out.println("Error: " + e.getMessage());
        }

        return isLogged;
    }

    private Boolean register() {
        boolean isRegistered = false;

        try {
            System.out.println();
            System.out.println("Introduce el nombre de usuario: ");
            Scanner scanner = new Scanner(System.in);
            String nombre = scanner.nextLine();
            System.out.println("Introduce la contraseña: ");
            String password = scanner.nextLine();
            isRegistered = Jugador.getServicioAutenticacion().registrarUsuario(nombre, password, Jugador.getClientId());

        } catch (Exception e) {
            System.out.println();
            System.out.println("Error: " + e.getMessage());
        }

        return isRegistered;
    }

    private Boolean logeout() {
        boolean isUnlogged = false;

        try {
            Jugador.getServicioAutenticacion().cerrarSesion(username);
            isUnlogged = true;
        } catch (Exception e) {
            System.out.println();
            System.out.println("Error: " + e.getMessage());
        }

        return isUnlogged;
    }

    // -------------- Esperamos a que se produzca un evento --------------
    private void esperarEvento() {
        boolean finished = false;

        while (!finished) {
            try {
                if (Jugador.getListaSincronizada().datosPendientes() != 0) {
                    String evento = Jugador.getListaSincronizada().getEvento();
                    leerEvento(evento);
                } else {
                    Thread.sleep(2000);
                    System.out.println();
                    System.out.println("Esperando evento...");
                }
            } catch (Exception e) {
                System.out.println();
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void leerEvento(String evento) {
        String[] parts = evento.split("-");
        int gameID = Integer.parseInt(parts[0]);
        String event = parts[1];

        switch (event) {
            case "coordenadas":
                getCoordinates(gameID);
                break;
        }
    }

    // -------------- Obtenemos las coordenadas de los barcos --------------
    private void getCoordinates(int gameID) {
        Coordinate barcoUno = new Coordinate();
        Coordinate barcoDos = new Coordinate();
        boolean isSet = false;

        while (!isSet) {

            // Coordenadas del barco uno
            barcoUno = getSingleShipCoordinates("uno");

            // Coordenadas del barco dos
            barcoDos = getSingleShipCoordinates("dos");

            // Comprobamos si las coordenadas son válidas y no se solapan
            List<Coordinate> barcoUnoCoordinates = barcoUno.getOccupiedCoordinates();
            List<Coordinate> barcoDosCoordinates = barcoDos.getOccupiedCoordinates();

            if (barcoUnoCoordinates.isEmpty() || barcoDosCoordinates.isEmpty() || barcoUnoCoordinates.stream().anyMatch(barcoDosCoordinates::contains)) {
                System.out.println();
                System.out.println("Los barcos no pueden ocupar el mismo espacio o salirse del tablero. Por favor, inténtalo de nuevo.");
            } else {
                isSet = true;
            }
        }

        try {
            Jugador.getServicioGestor().setCoordinates(gameID, Jugador.getClientId(), barcoUno, barcoDos);
            System.out.println();
            System.out.println("Coordenadas enviadas");

            // Obtener los datos del jugador desde las partidas en curso del servidor
            Partida partida = Jugador.getServicioGestor().getPartidaEnCurso(gameID);
            common.database.Jugador jugador;
            if (partida.getPlayerOne().getClienteID().equals(Jugador.getClientId())) {
                jugador = partida.getPlayerOne();
            } else {
                jugador = partida.getPlayerTwo();
            }
            jugador.printBoard();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Coordinate getSingleShipCoordinates(String shipNumber) {
        System.out.println();
        System.out.println("Introduce las coordenadas del barco " + shipNumber + ": ");
        System.out.println("Fila de la proa (A - J): ");
        Scanner scanner = new Scanner(System.in);
        String proaRow = scanner.nextLine();
        System.out.println("Columna de la proa (1 - 10): ");
        int proaColumn = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Horientación (h - horizontal, v - vertical): ");
        String orientation = scanner.nextLine();

        // Comprobamos si las coordenadas son válidas
        if (isValidCoordinate(proaRow, proaColumn, orientation)) {
            return new Coordinate(proaRow, proaColumn, orientation);
        } else {
            System.out.println("Las coordenadas introducidas no son válidas. Por favor, inténtalo de nuevo.");
            return getSingleShipCoordinates(shipNumber);
        }
    }

    private boolean isValidCoordinate(String row, int column, String orientation) {
        String validRows = "ABCDEFGHIJ";
        String validOrientations = "hv";

        return validRows.contains(row.toUpperCase()) && column >= 1 && column <= 10 && validOrientations.contains(orientation.toLowerCase());
    }

    // -------------- Métodos del menú --------------
    private void mostrarInformacion() {
        try {
            common.database.Jugador jugador = Jugador.getServicioGestor().getJugador(username);
            HashMap<Integer, Integer> points = jugador.getGamePoints();

            System.out.println();
            System.out.println("Nombre: " + jugador.getUsername());
            System.out.println("Puntos totales: " + jugador.getTotalPoints());
            System.out.println("Puntos por partida: ");
            for (Integer gameId : points.keySet()) {
                System.out.println("Partida " + gameId + ": " + points.get(gameId));
            }

        } catch (Exception e) {
            System.out.println();
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void iniciarPartida() {
        boolean started = false;
        boolean isWaiting = true;
        try {
            started = Jugador.getServicioGestor().startGame(username);
        } catch (Exception e) {
            System.out.println();
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();

        if (started) {
            System.out.println("Esperando a que se una un contrincante...");
            while (isWaiting) {
                try {

                    if (Jugador.getListaSincronizada().datosPendientes() == 0) {
                        System.out.println("No se ha unido ningún contrincante");
                        Thread.sleep(2000);
                    } else if (Jugador.getListaSincronizada().getEvento().equals("partida_iniciada")) {
                        System.out.println("Se ha unido un contrincante");
                        isWaiting = false;
                    }
                } catch (Exception e) {
                    System.out.println();
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Error al iniciar la partida");
        }
    }

    private void listarPartidas() {

        try {
            List<String> waitingGames = Jugador.getServicioGestor().getWaitingGamesWithCreators();
            System.out.println();
            System.out.println("Partidas a la espera de contrincante y contrincante: ");
            for (String game : waitingGames) {
                System.out.println(game);
            }
        } catch (Exception e) {
            System.out.println();
            System.out.println("Error: " + e.getMessage());
        }

    }

    private void unirsePartida() {
        boolean joined = false;
        try {
            System.out.println();
            System.out.println("Introduce el id de la partida a la que quieres unirte: ");
            Scanner scanner = new Scanner(System.in);
            int gameId = scanner.nextInt();
            joined = Jugador.getServicioGestor().joinGame(username, gameId);
        } catch (Exception e) {
            System.out.println();
            System.out.println("Error: " + e.getMessage());
        }

        if (joined) {
            System.out.println();

            try {
                if (Jugador.getListaSincronizada().datosPendientes() == 0) {

                    System.out.println("Error al unirse a la partida");

                } else if (Jugador.getListaSincronizada().getEvento().equals("partida_iniciada")) {
                    System.out.println("Te has unido a la partida");
                }
            } catch (Exception e) {
                System.out.println();
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println();
            System.out.println("Error al unirse a la partida");
        }
    }
}
