/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejercicio11;

import java.util.Random;

class Arbitro {

    Random rnd = new Random();
    int numeroAadivinar = rnd.nextInt(11);
    int turno = 1;
    boolean esAcabado = false;

    public synchronized int getTurno() {
        if (turno < 4) {
            return turno;
        } else {
            return turno = 1;
        }

    }

    public int getNumeroAadivinar() {
        return numeroAadivinar;
    }

    public synchronized void pregunta(int turnoJugador, int numeroJugador) {
        if (!esAcabado) {
            System.out.println("Jugador " + turno + ", dice: " + numeroJugador);
            if (numeroJugador == numeroAadivinar) {

                System.out.println("Jugador " + turno + " gana, adivinó el numero!!!!");
                esAcabado = true;
            }
            this.turno++;
        }
    }

}

class Jugador implements Runnable {

    int turnoJugador;
    Arbitro arb;

    public Jugador(int turnoJugador, Arbitro arb) {
        this.turnoJugador = turnoJugador;
        this.arb = arb;
    }

    @Override
    public void run() {
        while (!arb.esAcabado) {
            if (turnoJugador == arb.getTurno()) {
                Random rnd = new Random();
                int numeroJugador = rnd.nextInt(11);
                arb.pregunta(turnoJugador, numeroJugador);
            }
        }

    }

}

public class Main {

    public static void main(String[] args) {
        Arbitro arb = new Arbitro();
        System.out.println("Arbitro dice: " + arb.getNumeroAadivinar());
        Thread jugador1 = new Thread(new Jugador(1, arb));
        Thread jugador2 = new Thread(new Jugador(2, arb));
        Thread jugador3 = new Thread(new Jugador(3, arb));

        jugador1.start();
        jugador2.start();
        jugador3.start();

    }

}
