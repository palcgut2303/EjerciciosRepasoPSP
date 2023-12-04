package EjercicioCochera;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


class Coche{
    int contador = 0;
    int id;

    public Coche() {
        contador++;
        this.id = contador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}



class RecursoCompartido {

    ArrayList<Coche> cochera = new ArrayList<>();
    int tamañoArray = 10;

    public int getTamañoArray() {
        return tamañoArray;
    }
    
    synchronized void getDejarPlaza(int i) throws InterruptedException {
        while (cochera.isEmpty()) {
            wait();
        }

        Coche miCoche = cochera.get(0);
        System.out.println("Dejando la plaza "+ i +" que tiene el coche: " + miCoche.getId());
        cochera.remove(0);
        notifyAll();
    }

    synchronized void putOcuparPlaza(Coche coche,int i) throws InterruptedException {
        while (cochera.size() >= tamañoArray) {
            wait();
        }

        cochera.add(coche);
        System.out.println("Ocupado la plaza: "+i+" con el coche: " + coche.getId());
        notifyAll();
    }
}

class Productor implements Runnable {

    RecursoCompartido rec;

    public Productor(RecursoCompartido rec) {
        this.rec = rec;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i < rec.getTamañoArray(); i++) {
                Coche nuevoCoche = new Coche();
                nuevoCoche.setId(i);
                rec.putOcuparPlaza(nuevoCoche,i);
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

class Consumidor implements Runnable {

    RecursoCompartido rec;

    public Consumidor(RecursoCompartido rec) {
        this.rec = rec;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 1; i < rec.getTamañoArray(); i++) {
                rec.getDejarPlaza(i);
                Thread.sleep(150);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

public class Main {

    public static void main(String[] args) throws InterruptedException {
        RecursoCompartido rc = new RecursoCompartido();
        Thread prod = new Thread(new Productor(rc));
        Thread cons = new Thread(new Consumidor(rc));
        
        prod.start();
        cons.start();
        
        prod.join();
        cons.join();
        
        System.out.println("FINALIZADO");
    }
    
}
