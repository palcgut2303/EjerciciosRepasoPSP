/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EjercicioChatGPT;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class Producto {

    private int contador = 0;
    private int id;

    public Producto() {
        this.id = contador++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
}

class Almacen {

    ArrayList<Producto> almacen = new ArrayList<>();
    private int tamañoArra = 30;

    public int getTamañoArra() {
        return tamañoArra;
    }

    synchronized void get() throws InterruptedException {
        while (almacen.size() == 0) {
            wait();
        }
        
        System.out.println("Comprando el producto con id: " + almacen.get(0).getId());
        almacen.remove(0);
        notifyAll();
    }

    synchronized void put(Producto producto) throws InterruptedException {
        while (almacen.size() >= tamañoArra) {
            wait();
        }
        
        almacen.add(producto);
        System.out.println("Produciendo el producto con id: " + producto.getId());
        notifyAll();
    }

}

class Productor extends Thread {

    Almacen alm;

    public Productor(Almacen alm) {
        this.alm = alm;
    }

    @Override
    public void run() {
        for (int i = 0; i < alm.getTamañoArra(); i++) {
            try {
                Producto miProducto = new Producto();
                miProducto.setId(i);
                alm.put(miProducto);
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}

class Consumidor extends Thread {

    Almacen alm;

    public Consumidor(Almacen alm) {
        this.alm = alm;
    }

    @Override
    public void run() {
        for (int i = 0; i < alm.getTamañoArra(); i++) {
            try {
                alm.get();
                Thread.sleep(150);
            } catch (Exception e) {
            }
        }
    }

}

public class Main {
    public static void main(String[] args) {
        
        Almacen almacen = new Almacen();
        Productor productor = new Productor(almacen);
        Consumidor consumidor = new Consumidor(almacen);
        
        productor.start();
        consumidor.start();
               
        
    }
}
