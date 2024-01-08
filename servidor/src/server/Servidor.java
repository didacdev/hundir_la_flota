package server;

import common.database.Partida;
import common.database.ServicioDatosInterfaz;
import common.rmi.IniciarRMI;
import common.server.ServicioAutenticacionInterfaz;
import common.server.ServicioGestorInterfaz;

import database.ServicioDatosImpl;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Servidor extends IniciarRMI {

    private static final Date fechaInicio = new Date();
    private static ServicioDatosInterfaz servicioDatos;

    public Servidor() {
        super(Servidor.class, 1099);
        fechaInicio.setTime(System.currentTimeMillis());
    }

    @Override
    public void operacionRMI() {
        try {

            // Obtener la dirección IP
            String ip = InetAddress.getLocalHost().getHostAddress();

            // Crear instancias de los objetos remotos
            ServicioAutenticacionImpl servicioAutenticacion = new ServicioAutenticacionImpl();
            ServicioGestorImpl servicioGestor = new ServicioGestorImpl();

            // Exportar el objeto remoto
            ServicioAutenticacionInterfaz serAutStub = (ServicioAutenticacionInterfaz) UnicastRemoteObject.exportObject(servicioAutenticacion, 0);
            ServicioGestorInterfaz serGesStub = (ServicioGestorInterfaz) UnicastRemoteObject.exportObject(servicioGestor, 0);

            // Crear identificadores únicos
            String uniqueIdAut = "002";
            String uniqueIdGes = "003";

            // Crear las URL
            String URL_nombre_aut = "rmi://" + ip + ":" + this.registryPort + "/" + ServicioAutenticacionInterfaz.NOMBRE_SERVICIO + "/" + uniqueIdAut;
            String URL_nombre_ges = "rmi://" + ip + ":" + this.registryPort + "/" + ServicioGestorInterfaz.NOMBRE_SERVICIO + "/" + uniqueIdGes;

            // Registrar el objeto remoto
            Naming.rebind(URL_nombre_aut, serAutStub);
            Naming.rebind(URL_nombre_ges, serGesStub);

            servicioDatos = (ServicioDatosInterfaz) Naming.lookup("rmi://" + ip + ":" + this.registryPort + "/" + ServicioAutenticacionInterfaz.NOMBRE_SERVICIO + "/" + "001");

            System.out.println("Servidor iniciado...");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static Date getFechaInicio() {
        return fechaInicio;
    }

    public HashMap<Integer, Partida> getPartidasEnEjecucion() {
        try {
            return servicioDatos.getStartedGames();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {

        Servidor servidor = new Servidor();
        Interfaz interfaz = new Interfaz(servidor);
        interfaz.iniciar();
    }
}
