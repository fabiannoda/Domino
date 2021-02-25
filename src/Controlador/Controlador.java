/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Dom;
import Modelo.Ficha;
import Modelo.Jugador;
import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author
 */
public class Controlador implements ActionListener {

    //vistas
    private Ventana ventana;
    private Dom dom;
    private int turno, h1, h2;
    private Ficha[][] tablero;

    public Controlador() {
        dom = new Dom();
        tablero = new Ficha[15][15];
        this.ventana = new Ventana(this, dom.getJugadores());
        this.ventana.setFichas(tablero);
        inicial();
        turno = 0;
        this.ventana.paint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.ventana.setVisible(false);
        movimiento(e.getActionCommand());
        this.ventana = new Ventana(this, dom.getJugadores());
        this.ventana.setFichas(tablero);
        this.ventana.setTurno(this.turno + 1);
        
        int h = validarGanador();
        if (h != -1) {

            if (h == 2) {
                this.ventana.finalizo(false, h);
            } else {
                this.ventana.finalizo(true, h);
            }
        } else {
            this.ventana.paint();
        }
        int cont=0;
        for (int j = 0; j < this.dom.getJugadores()[1].getFichas().length; j++) {
            if (this.dom.getJugadores()[1].getFichas()[j] != null) {
                cont++;
            }
        }
        if (cont==0) {
            this.ventana.finalizo(false, 1);
        }else{
            this.ventana.paint();
        }
        this.ventana.setVisible(true);
        posibilidades();
        this.dom.ver();
    }

    public void movimiento(String n) {
        posibilidades();
        Ficha p = null;
        int f = Integer.parseInt(n);
        int jug = -1;
        if (turno == 1) {
            jug = 0;
        } else {
            jug = 1;
        }
        Jugador[] j = this.dom.getJugadores();
        for (int i = 0; i < j[jug].getFichas().length; i++) {
            if (j[jug].getFichas()[i] != null) {
                if (f == j[jug].getFichas()[i].getId()) {
                    p = j[jug].getFichas()[i];
                }
            }
        }
        if (p != null) {
            if (p.getValor1() == h1 || p.getValor2() == h1 || p.getValor1() == h2 || p.getValor2() == h2) {
                for (int i = 0; i < j[jug].getFichas().length; i++) {
                    if (j[jug].getFichas()[i] != null) {
                        if (f == j[jug].getFichas()[i].getId()) {
                            j[jug].getFichas()[i] = null;
                        }
                    }
                }
                if (p.getValor1() == h1 || p.getValor2() == h1) {
                    ponerficha(p, h1);
                } else if (p.getValor1() == h2 || p.getValor2() == h2) {
                    ponerficha(p, h2);
                }
            } else {
                this.dom.addficha(jug);
            }

        } else {
            JOptionPane.showMessageDialog(ventana, "el turno es de el jugador " + (turno + 1));
        }

        mandar();
    }

    public void mandar() {
        cambiarturno();
        cambiarturno();
        juegaMaquina();
    }

    public void juegaMaquina() {
        posibilidades();
        int jug = 0;
        Ficha p = null;
        Jugador[] j = this.dom.getJugadores();
        for (int i = 0; i < j[jug].getFichas().length; i++) {
            if (j[jug].getFichas()[i] != null) {
                p = j[jug].getFichas()[i];
            }
            if (p != null) {
                if (p.getValor1() == h1 || p.getValor2() == h1 || p.getValor1() == h2 || p.getValor2() == h2) {
                    j[jug].getFichas()[i] = null;
                    if (p.getValor1() == h1 || p.getValor2() == h1) {
                        ponerficha(p, h1);
                        break;
                    } else if (p.getValor1() == h2 || p.getValor2() == h2) {
                        ponerficha(p, h2);
                        break;
                    }
                } else {
                    if (i == j[jug].getFichas().length - 1) {
                        this.dom.addficha(jug);
                        break;
                    }
                }
            }

        }

    }

    //poner una ficha en el tablero
    public void ponerficha(Ficha f, int val) {
        Ficha[][] tablero = this.ventana.getFichas();
        int x = -1, y = -1, x1 = -1, y1 = -1;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (tablero[i][j] != null) {
                    if (!tablero[i][j].isUsevalor1()) {
                        if (tablero[i][j].getValor1() == val) {
                            x1 = j;
                            y1 = i;
                            x = j - 1;
                            y = i;
                            break;
                        }
                    }
                    if (!tablero[i][j].isUsevalor2()) {
                        if (tablero[i][j].getValor2() == val) {
                            x1 = j;
                            y1 = i;
                            x = j + 1;
                            y = i;
                            break;
                        }
                    }
                }
            }
        }
        if (x < x1) {
            if (f.getValor2() != val) {
                int a = f.getValor2();
                f.setValor2(f.getValor1());
                f.setValor1(a);
            }
            f.setUsevalor2(true);
        } else {
            if (f.getValor2() == val) {
                int a = f.getValor1();
                f.setValor1(f.getValor2());
                f.setValor2(a);
            }
            f.setUsevalor1(true);
        }

        if (x < 0) {
            x = 0;
            y++;
        } else if (x >= tablero.length) {
            x = tablero.length - 1;
            y++;
        }

        if (x != -1 && y != -1) {
            tablero[y][x] = f;
        }

        if (tablero[y1][x1].getValor1() == val && !tablero[y1][x1].isUsevalor1()) {
            tablero[y1][x1].setUsevalor1(true);
        } else {
            tablero[y1][x1].setUsevalor2(true);
        }

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (tablero[i][j] != null) {
                    this.dom.ocupar(tablero[i][j].getId());
                }
            }
        }
        this.ventana.setFichas(tablero);
    }

    //valida cuales son los numeros de las esquinas 
    public void posibilidades() {
        Ficha[][] tablero = this.ventana.getFichas();
        h1 = -1;
        h2 = -1;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (tablero[i][j] != null) {
                    if (!tablero[i][j].isUsevalor1()) {
                        if (h1 == -1) {
                            h1 = tablero[i][j].getValor1();
                        } else {
                            h2 = tablero[i][j].getValor1();
                        }
                    }
                    if (!tablero[i][j].isUsevalor2()) {
                        if (h1 == -1) {
                            h1 = tablero[i][j].getValor2();
                        } else {
                            h2 = tablero[i][j].getValor2();
                        }
                    }
                }
                if (h1 != -1 && h2 != -1) {
                    break;
                }
            }
        }

        System.out.println(" h1-" + h1 + "      h2-" + h2);
    }

    public void inicial() {
        tablero[0][5] = dom.getFicha();
        this.ventana.setFichas(tablero);
    }

    //valida si hay ganadores o fin del juego 
    public int validarGanador() {
        int res = -1;
        if (sinopciones()) {
            System.out.println("con opciones");
            for (int i = 0; i < this.dom.getJugadores().length; i++) {
                int cont = 0;
                for (int j = 0; j < this.dom.getJugadores()[i].getFichas().length; j++) {
                    if (this.dom.getJugadores()[i].getFichas()[j] != null) {
                        cont++;
                    }
                }
                if (cont == 0) {
                    res = i;
                    break;
                }
            }
        } else {
            System.out.println("sin opciones");
            int jug1 = 0, jug2 = 0;
            for (int i = 0; i < this.dom.getJugadores().length; i++) {
                for (int j = 0; j < this.dom.getJugadores()[i].getFichas().length; j++) {
                    if (this.dom.getJugadores()[i].getFichas()[j] != null) {
                        if (i == 0) {
                            jug1++;
                        } else {
                            jug2++;
                        }
                    }
                }
            }

            if (jug1 == jug2) {
                res = 2;
            } else if (jug1 < jug2) {
                res = jug1;
            } else {
                res = jug2;
            }
        }
        System.out.println(res);
        return res;
    }

    //valida el fin del juego donde ya no hay mas posibilidades
    public boolean sinopciones() {
        boolean res = true;
        if (this.dom.getFicha() == null) {
            for (int i = 0; i < this.dom.getJugadores().length; i++) {
                for (int j = 0; j < this.dom.getJugadores()[i].getFichas().length; j++) {
                    Ficha p = this.dom.getJugadores()[i].getFichas()[j];
                    if (p != null) {
                        if (p.getValor1() == h1 || p.getValor2() == h1 || p.getValor1() == h2 || p.getValor2() == h2) {
                            res = false;
                            System.out.println("falseeeeeeeee");
                        }
                    }
                }
            }
        }
        return res;
    }

    public void cambiarturno() {
        if (this.turno == 1) {
            this.turno = 0;
            this.ventana.setTurno(this.turno + 1);
        } else {
            this.turno = 1;
            this.ventana.setTurno(this.turno + 1);
        }
    }

    public void startaplication() {
        this.ventana.setVisible(true);
    }

}
