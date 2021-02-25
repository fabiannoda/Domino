/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.Controlador;
import Modelo.Ficha;
import Modelo.Jugador;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author
 */
public class Ventana extends JFrame {

    private Controlador cont;
    private Ficha[][] fichas;
    private Jugador[] jugadores;
    private int turno;

    public Ventana(Controlador cont, Jugador[] j) {
        this.cont = cont;
        this.jugadores = j;
        this.turno = 1;
    }

    public void paint() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 700));
        setLayout(new BorderLayout());
        JPanel n = new JPanel();
        n.setLayout(new BorderLayout());
        JLabel t = new JLabel(" " + this.jugadores[0].getNombre());
        n.add(t, BorderLayout.WEST);
        t = new JLabel("                                                     DOMINO  ");
        n.add(t, BorderLayout.CENTER);
        t = new JLabel(" " + this.jugadores[1].getNombre());
        n.add(t, BorderLayout.EAST);
        this.add(n, BorderLayout.NORTH);
        this.add(tabla(), BorderLayout.CENTER);
        this.add(paintjug(this.jugadores[1]), BorderLayout.WEST);
        this.add(paintjug(this.jugadores[0]), BorderLayout.EAST);
        this.add(new Label("   Turno: jugador " + this.turno), BorderLayout.SOUTH);
    }

    public JPanel tabla() {
        JPanel n = new JPanel();
        n.setLayout(new GridLayout(fichas.length, fichas.length));
        for (int i = 0; i < fichas.length; i++) {
            for (int j = 0; j < fichas.length; j++) {
                if (fichas[i][j] != null) {
                    if (j == 0) {
                        n.add(paintficha2(fichas[i][j].getValor2(), fichas[i][j].getValor1()));
                    } else if (j == (fichas.length - 1)) {
                        n.add(paintficha2(fichas[i][j].getValor1(), fichas[i][j].getValor2()));
                    } else {
                        n.add(paintficha1(fichas[i][j]));
                    }
                } else {
                    n.add(new JLabel("  "));
                }
            }
        }
        return n;
    }

    public JPanel paintjug(Jugador j) {
        JPanel n = new JPanel();
        n.setLayout(new GridLayout(j.getFichas().length, 1));
        for (int i = 0; i < j.getFichas().length; i++) {
            if (j.getFichas()[i] != null) {
                Button b = new Button(" " + j.getFichas()[i].getValor1() + ", " + j.getFichas()[i].getValor2());
                b.setActionCommand("" + j.getFichas()[i].getId());
                b.addActionListener(cont);
                n.add(b);
            } else {
                n.add(new JLabel("  "));
            }
        }
        return n;
    }

    public JPanel paintficha1(Ficha f) {
        JPanel n = new JPanel();
        n.setLayout(new GridLayout(3, 5));
        n.add(new Label(" "));
        n.add(new Label("--"));
        n.add(new Label(" "));
        n.add(new Label("--"));
        n.add(new Label(" "));
        n.add(new Label("|"));
        n.add(new Label("" + f.getValor1()));
        n.add(new Label("|"));
        n.add(new Label("" + f.getValor2()));
        n.add(new Label("|"));
        n.add(new Label(" "));
        n.add(new Label("--"));
        n.add(new Label(" "));
        n.add(new Label("--"));
        n.add(new Label(" "));
        return n;
    }

    public JPanel paintficha2(int val1, int val2) {
        JPanel n = new JPanel();
        n.setLayout(new GridLayout(5, 3));
        n.add(new Label(" "));
        n.add(new Label("--"));
        n.add(new Label(" "));
        n.add(new Label("|"));
        n.add(new Label("" + val1));
        n.add(new Label("|"));
        n.add(new Label(" "));
        n.add(new Label("--"));
        n.add(new Label(" "));
        n.add(new Label("|"));
        n.add(new Label("" + val2));
        n.add(new Label("|"));
        n.add(new Label(" "));
        n.add(new Label("--"));
        n.add(new Label(" "));
        return n;
    }

    public boolean validarAcierto(char a) {
        boolean res = false;

        return res;
    }

    public void finalizo(boolean r, int i) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(500, 400));
        setLayout(new BorderLayout());
        JPanel n = new JPanel();
        JLabel t = new JLabel(" Domino");
        n.add(t);
        this.add(n, BorderLayout.NORTH);
        String g = "";
        if (r) {
            g = " ganador jugador "+(i+1);
        } else {
            g = "    empate ";
        }
        JLabel res = new JLabel(g);
        this.add(res, BorderLayout.CENTER);
    }

    public Ficha[][] getFichas() {
        return fichas;
    }

    public void setFichas(Ficha[][] fichas) {
        this.fichas = fichas;
    }

    public Jugador[] getJugadores() {
        return jugadores;
    }

    public void setJugadores(Jugador[] jugadores) {
        this.jugadores = jugadores;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

}
