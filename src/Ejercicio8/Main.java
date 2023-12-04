/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio8;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class RecursoCompartido {

    ArrayList<Productos> lista = new ArrayList<>();
    int tamañoInventario = 20;

    synchronized void get() throws InterruptedException {
        while (lista.isEmpty()) {
            wait();
        }

        Productos miProducto = lista.get(0);
        System.out.println("Consumidor,ha comprado el producto con id: " + miProducto.getId());
        lista.remove(0);
        notify();
    }

    synchronized void put(Productos miProducto) throws InterruptedException {
        while (lista.size() == tamañoInventario) {
            wait();
        }

        lista.add(miProducto);
        System.out.println("Se ha producido el producto con id: " + miProducto.getId());
        notify();

    }

}

class Productor implements Runnable {

    RecursoCompartido rc;

    public Productor(RecursoCompartido rc) {
        this.rc = rc;
    }

    @Override
    public void run() {
        while (true) {
            Productos nuevoProducto = new Productos();
            try {
                rc.put(nuevoProducto);
               // Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

class Consumidor implements Runnable {

    RecursoCompartido rc;

    public Consumidor(RecursoCompartido rc) {
        this.rc = rc;
    }

    @Override
    public void run() {
        while (true) {
            try {
                rc.get();
               // Thread.sleep(150);

            } catch (InterruptedException ex) {
                Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

public class Main {

    public static void main(String[] args) {

        RecursoCompartido almacen = new RecursoCompartido();
        Thread hprod = new Thread(new Productor(almacen));
        Thread hcons1 = new Thread(new Consumidor(almacen));

        hprod.start();
        hcons1.start();

    }
}
