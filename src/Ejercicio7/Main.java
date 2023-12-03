/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio7;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

class RecursoCompartido{
    
    char[] cola = new char[6];
    int contador = 0;
    
    
    synchronized void get() throws InterruptedException{
        while(contador == 0){
            wait();
        }
        
        char valor = cola[--contador];
        System.out.println("Recogiendo el caracter: " + valor + " del buffer.");
        notify();
    }
    
    synchronized void put() throws InterruptedException{
        while(contador == cola.length){
            wait();
        }
        Random random = new Random();
        char caracter = (char) (random.nextInt(26) + 'a');
        cola[contador++] = caracter;
        System.out.println("Depositando el caracter: " + caracter + " del buffer");
        notify();
        
    }
}

class HiloProductor implements Runnable{

    RecursoCompartido rc;

    public HiloProductor(RecursoCompartido rc) {
        this.rc = rc;
    }
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                rc.put();
                Thread.sleep(100);
            }
            
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloProductor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}

class HiloConsumidor implements Runnable{

    RecursoCompartido rc;

    public HiloConsumidor(RecursoCompartido rc) {
        this.rc = rc;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                rc.get();
                Thread.sleep(200);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloConsumidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}


public class Main {
    public static void main(String[] args) throws InterruptedException {
        RecursoCompartido almacen = new RecursoCompartido();
        Thread hprod = new Thread(new HiloProductor(almacen));
        Thread hcons1 = new Thread(new HiloConsumidor(almacen));
        hprod.start();
        hcons1.start();
        
        hprod.join();
        hcons1.join();
        System.out.println("Termina el programa");
        
        
    }
}
