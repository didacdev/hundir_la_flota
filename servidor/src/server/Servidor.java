package server;

import common.rmi.IniciarRMI;
import common.server.ServicioAutenticacionInterfaz;
import common.server.ServicioGestorInterfaz;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class Servidor extends IniciarRMI {

    public Servidor() {
        super(Servidor.class, 2003);
    }

    @Override
    public void operacionRMI() {
        try {
            Registry registry = LocateRegistry.createRegistry(registryPort);

            ServicioAutenticacionImpl servicioAutenticacion = new ServicioAutenticacionImpl();
            ServicioAutenticacionInterfaz serAutStub = (ServicioAutenticacionInterfaz) UnicastRemoteObject.exportObject(servicioAutenticacion, ServicioAutenticacionInterfaz.port);

            ServicioGestorImpl servicioGestor = new ServicioGestorImpl();
            ServicioGestorInterfaz serGesStub = (ServicioGestorInterfaz) UnicastRemoteObject.exportObject(servicioGestor, ServicioGestorInterfaz.port);

            registry.rebind(ServicioAutenticacionInterfaz.NOMBRE_SERVICIO, serAutStub);
            registry.rebind(ServicioGestorInterfaz.NOMBRE_SERVICIO, serGesStub);

            System.out.println(Arrays.toString(registry.list()));

            System.out.println("Servidor iniciado...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        new Servidor();
    }
}
