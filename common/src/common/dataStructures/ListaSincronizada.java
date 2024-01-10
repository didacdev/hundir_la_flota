package common.dataStructures;

import java.util.ArrayList;
import java.util.List;
public class ListaSincronizada {
    private List <String> lista;
    public ListaSincronizada ()
    {
        lista= new ArrayList<String>();
    }
    public synchronized int datosPendientes()
    {
        return (lista.size());
    }
    public synchronized void addEvento(String dato) {
        lista.add(dato);
        notifyAll();
    }
    public synchronized String getEvento() throws InterruptedException {
        if (lista.size()==0)
            wait();
        String dato = lista.get(0);
        lista.remove(0);
        return dato;
    }
}
