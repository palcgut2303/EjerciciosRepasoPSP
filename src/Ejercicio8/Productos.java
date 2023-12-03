/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercicio8;


public class Productos {

    private static int contador = 0;
    private int id;

    public Productos() {
        this.id = contador++;
    }
    public int getId() {
        return id;
    }

    
}
