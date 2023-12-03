/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio5;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Source;

class RecursoCompartido {
    
    int numero = 0;
    boolean esDisponible = false;
    
    synchronized int get() throws InterruptedException {
        while (!esDisponible) {
            wait();
        }
        
        esDisponible = false;
        notifyAll();
        return numero;
    }
    
    synchronized void put(int valor) throws InterruptedException {
        
        while (esDisponible) {
            wait();
        }
        numero = valor;
        esDisponible = true;
        notifyAll();
        
    }
    
}

class Productor implements Runnable {
    
    RecursoCompartido rc;
    String nombre;
    
    public Productor(RecursoCompartido rc, String nombre) {
        this.rc = rc;
        this.nombre = nombre;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                rc.put(i);
                System.out.println(i + "=>Productor : " + nombre + ", produce : " + i);
            } catch (InterruptedException ex) {
                Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Fin productor ...");
    }
}

class Consumidor implements Runnable {
    
    int nombre;
    RecursoCompartido rc;
    
    public Consumidor(int nombre, RecursoCompartido rc) {
        this.nombre = nombre;
        this.rc = rc;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                int valor = rc.get();
                System.out.println(i + "=>Consumidor: " + nombre + ", consume: " + valor);
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
}

public class Main {
    
    public static void main(String[] args) throws InterruptedException {
        RecursoCompartido rc = new RecursoCompartido();
        Thread prod1 = new Thread(new Productor(rc, "1"));
        Thread cons1 = new Thread(new Consumidor(1, rc));
        Thread cons2 = new Thread(new Consumidor(2, rc));
        
        prod1.start();
        Thread.sleep(500);
        cons1.start();
        cons2.start();
    }
    
}
