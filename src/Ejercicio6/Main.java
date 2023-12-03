/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio6;

import java.util.logging.Level;
import java.util.logging.Logger;

class RecursoCompartido {

    String palabra;
    boolean esDisponible;

    synchronized String get() throws InterruptedException {
        while(!esDisponible) {
            wait();
        }
        
        esDisponible = false;
        notify();
        return palabra;
    }

    synchronized void put(String valor) throws InterruptedException {
        while(esDisponible){
            wait();
        }
        esDisponible = true;
        palabra = valor;
        notify();
    }
    

}

class Productor extends Thread {

    int contador = 2;
    RecursoCompartido rc;

    public Productor(RecursoCompartido rc) {
        this.rc = rc;
    }

    @Override
    public void run() {
        for (int i = 2; i < 10; i++) {
            if ((i % 2) == 0) {
                try {
                    rc.put("PING");
                } catch (InterruptedException ex) {
                    Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                try {
                    rc.put("PONG");
                } catch (InterruptedException ex) {
                    Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

}

class Consumidor extends Thread {

    RecursoCompartido rc;

    public Consumidor(RecursoCompartido rc) {
        this.rc = rc;
    }

    @Override
    public void run() {
        for (int i = 2; i < 10; i++) {
            String palabra = "";
            try {
                palabra = rc.get();
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(palabra);
        }

    }

}

public class Main {

    public static void main(String[] args) throws InterruptedException {
        RecursoCompartido rc = new RecursoCompartido();
        Productor pr1 = new Productor(rc);
        Consumidor cons1 = new Consumidor(rc);
        Thread pr = new Thread(pr1);
        Thread cons = new Thread(cons1);

        pr.start();
        cons.start();

    }
}
