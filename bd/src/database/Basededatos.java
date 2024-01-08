package database;

import common.database.Jugador;
import common.database.Partida;
import common.database.ServicioDatosInterfaz;
import common.rmi.IniciarRMI;

import common.server.ServicioAutenticacionInterfaz;
import server.Servidor;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;


public class Basededatos extends IniciarRMI {

    public Basededatos() {

        super(Basededatos.class, 1099);

    }

    @Override
    public void operacionRMI() {
        try {

            Registry registry = LocateRegistry.createRegistry(this.registryPort);

            // Obtener la dirección IP
            String ip = InetAddress.getLocalHost().getHostAddress();

            // Crear jugadores y partidas de prueba
            Jugador jugador1 = new Jugador("password1");
            Jugador jugador2 = new Jugador("password2");

            Partida partida1 = new Partida(jugador1);
            partida1.setPlayerTwo(jugador2);

            // Crear listas y mapas para almacenar los jugadores y partidas
            HashMap<String, Jugador> registredUsers = new HashMap<>();
            registredUsers.put("jugador1", jugador1);
            registredUsers.put("jugador2", jugador2);

            List<String> onlineUsers = new ArrayList<>();
            onlineUsers.add("jugador1");
            onlineUsers.add("jugador2");

            List<Integer> waitingGames = new ArrayList<>();
            waitingGames.add(1);

            List<Integer> startedGames = new ArrayList<>();
            startedGames.add(2);

            HashMap<Integer, Partida> createdGames = new HashMap<>();
            createdGames.put(1, partida1);

            // Crear una instancia de ServidorDatosImpl con los datos de prueba
            ServicioDatosImpl servicioDatos = new ServicioDatosImpl(registredUsers, onlineUsers, waitingGames, startedGames, createdGames);

//            ServicioDatosImpl servicioDatos = new ServicioDatosImpl();
            ServicioDatosInterfaz serDatStub = (ServicioDatosInterfaz) UnicastRemoteObject.exportObject(servicioDatos, 0);

            String uniqueIdDat = "001";
            String URL_nombre_dat = "rmi://" + ip + ":" + this.registryPort + "/" + ServicioAutenticacionInterfaz.NOMBRE_SERVICIO + "/" + uniqueIdDat;

            Naming.rebind(URL_nombre_dat, serDatStub);

            System.out.println("Base de datos ok...");

            // Ejecución de la interfaz
            Interfaz interfaz = new Interfaz(servicioDatos);
            interfaz.iniciar();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        Basededatos basededatos = new Basededatos();

        // Añadir algunos datos de prueba
        try {
            ServicioDatosImpl servicioDatos = new ServicioDatosImpl();


        } catch (Exception e) {
            System.out.println("Error al añadir datos de prueba: " + e.getMessage());
        }

    }
}
