package cliente;

import common.client.CallbackJugadorIterfaz;
import common.dataStructures.ListaSincronizada;

public class CallbackJugadorImpl implements CallbackJugadorIterfaz {

    private ListaSincronizada listaSincronizada;

    public CallbackJugadorImpl(ListaSincronizada lista) {
        listaSincronizada = lista;
    }

    @Override
    public void notificar(String mensaje) throws java.rmi.RemoteException {
        listaSincronizada.addEvento(mensaje);
    }

}
