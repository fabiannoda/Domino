/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Random;

/**
 *
 * @author 
 */
public class Dom {

    private Jugador[] jugadores;
    private Ficha[] fichas;

    public Dom() {
        iniciar();
    }

    public void iniciar() {
        fichas = new Ficha[28];
        jugadores = new Jugador[2];
        int pos = 0;
        //poner valores a las fichas
        for (int i = 0; i < 7; i++) {
            for (int j = i; j < 7; j++) {
                fichas[pos] = new Ficha(pos, i, j);
                pos++;
            }
        }
        //repartir mano a jugadores
        for (int i = 0; i < jugadores.length; i++) {
            jugadores[i] = new Jugador("jugador" + (i + 1), mano());
        }

        for (int i = 0; i < fichas.length; i++) {
            System.out.println(fichas[i].getId() + " , " + fichas[i].getValor1() + " , " + fichas[i].getValor2());
        }
        System.out.println(" jugadores--------");
        for (int i = 0; i < jugadores.length; i++) {
            for (int j = 0; j < jugadores[i].getFichas().length; j++) {
                System.out.println(jugadores[i].getFichas()[j].getId() + " , " + jugadores[i].getFichas()[j].getValor1() + " , " + jugadores[i].getFichas()[j].getValor2());
            }
        }
    }

    //asignar fichas a los jugadores
    public Ficha[] mano() {
        Ficha[] n = new Ficha[8];
        Random rdm = new Random();
        for (int i = 0; i < n.length; i++) {
            int pos = rdm.nextInt(fichas.length);
            if (!repetido(pos) && !repetido2(pos, n)) {
                n[i] = fichas[pos];
            } else {
                i--;
            }
        }
        return n;
    }

    public boolean repetido(int id) {
        boolean res = false;
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i] != null) {
                for (int j = 0; j < jugadores[i].getFichas().length; j++) {
                    if (jugadores[i].getFichas()[j] != null) {
                        if (id == jugadores[i].getFichas()[j].getId()) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public boolean repetido2(int id, Ficha[] n) {
        boolean res = false;
        for (int i = 0; i < n.length; i++) {
            if (n[i] != null) {
                if (id == n[i].getId()) {
                    res = true;
                }
            }
        }
        return res;
    }

    //busca si hay fichas desocupadas
    public Ficha posFicha() {
        Ficha n = null;
        for (int i = 0; i < this.fichas.length; i++) {
            if (!fichas[i].isUsed()) {
                boolean rep = false;
                for (int k = 0; k < jugadores.length; k++) {
                    if (jugadores[k] != null) {
                        for (int j = 0; j < jugadores[k].getFichas().length; j++) {
                            if (jugadores[k].getFichas()[j] != null) {
                                if (fichas[i].getId() == jugadores[k].getFichas()[j].getId()) {
                                    rep = true;
                                }
                            }
                        }
                    }
                }
                if (rep) {
                    n = fichas[i];
                }
            }
        }
        return n;
    }

    //genera una ficha al azar de las que estan sin usar y no la tiene ningun jugador 
    public Ficha getFicha() {
        Ficha n = null;
        if (posFicha() != null) {
            Random rdm = new Random();
            boolean rep = true;
            while (rep) {
                int pos = rdm.nextInt(fichas.length);
                if (!repetido(pos) && !fichas[pos].isUsed()) {
                    n = fichas[pos];
                    rep = false;
                }
            }
        }
        return n;
    }

    //el jugador come una ficha, se añade a su mano
    public boolean addficha(int jug) {
        boolean res = false;
        Ficha n = getFicha();
        if (n != null) {
            for (int i = 0; i < this.jugadores[jug].getFichas().length; i++) {
                if (this.jugadores[jug].getFichas()[i] == null) {
                    this.jugadores[jug].getFichas()[i] = n;
                    res = true;
                    break;
                }
            }
            if (!res) {
                Ficha[] n2 = new Ficha[this.jugadores[jug].getFichas().length + 1];
                for (int i = 0; i < this.jugadores[jug].getFichas().length; i++) {
                    n2[i] = this.jugadores[jug].getFichas()[i];
                }
                n2[this.jugadores[jug].getFichas().length] = n;
                this.jugadores[jug].setFichas(n2);
                res = true;
            }
        }
        return res;
    }

    //la ficha se pone en el tablero y cambia al estado de usada 
    public void ocupar(int id) {
        for (int i = 0; i < this.fichas.length; i++) {
            if (id == this.fichas[i].getId()) {
                this.fichas[i].setUsed(true);
            }
        }
    }
    
    
    public void ver() {
        System.out.println(" --------------------------");
        for (int i = 0; i < this.fichas.length; i++) {
            System.out.println(" "+this.fichas[i].getId()+" ("+this.fichas[i].getValor1()+","+this.fichas[i].getValor2()+
                    ") -- "+this.fichas[i].isUsed());
        }
        System.out.println(" --------------------------");
    }

    public Jugador[] getJugadores() {
        return jugadores;
    }

    public void setJugadores(Jugador[] jugadores) {
        this.jugadores = jugadores;
    }

    public Ficha[] getFichas() {
        return fichas;
    }

    public void setFichas(Ficha[] fichas) {
        this.fichas = fichas;
    }

}
