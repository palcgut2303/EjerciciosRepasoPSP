/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EjercicioMedia;

class HiloMedia extends Thread {

    int[] numeros;
    float resultado;
    int suma;
    int inicio;
    int fin;

    public HiloMedia(int[] numeros, int inicio, int fin) {
        this.numeros = numeros;
        this.resultado = 0;
        this.inicio = inicio;
        this.fin = fin;
    }

    @Override
    public void run() {
        for (int i = inicio; i < fin; i++) {
            suma += numeros[i];
        }

        resultado = suma / (fin - inicio);

    }

    public float getResultado() {
        return resultado;
    }

}

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int[] numeros = new int[200];
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = i;
        }

        HiloMedia hilo1 = new HiloMedia(numeros, 0, 50);
        HiloMedia hilo2 = new HiloMedia(numeros, 50, 100);
        HiloMedia hilo3 = new HiloMedia(numeros, 100, 150);
        HiloMedia hilo4 = new HiloMedia(numeros, 150, 200);

        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();

        hilo1.join();
        hilo2.join();
        hilo3.join();
        hilo4.join();
        
        float h1 = hilo1.getResultado();
        float h2 = hilo2.getResultado();
        float h3 = hilo3.getResultado();
        float h4 = hilo4.getResultado();

        float result = (h1+h2+h3+h4) / 4;
        System.out.println("Media: " + result);
        
    }

}
