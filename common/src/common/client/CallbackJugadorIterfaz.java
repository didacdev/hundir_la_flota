package common.client;

import java.rmi.Remote;

public interface CallbackJugadorIterfaz extends Remote {
    public static final String NOMBRE_SERVICIO = "CallbackJugador";

    // Añadir métodos remotos

    /**
     * Notifica al jugador un mensaje
     * @param mensaje mensaje a notificar
     * @throws java.rmi.RemoteException
     */
    public void notificar(String mensaje) throws java.rmi.RemoteException;

}
