/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author 
 */
public class Ficha {
    private int id;
    private int valor1;
    private int valor2;
    private boolean usevalor1;
    private boolean usevalor2;
    private boolean used;
    
    public Ficha(int id, int valor1, int valor2) {
        this.id = id;
        this.valor1 = valor1;
        this.valor2 = valor2;
        this.usevalor1=false;
        this.usevalor2=false;
        this.used=false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValor1() {
        return valor1;
    }

    public void setValor1(int valor1) {
        this.valor1 = valor1;
    }

    public int getValor2() {
        return valor2;
    }

    public void setValor2(int valor2) {
        this.valor2 = valor2;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isUsevalor1() {
        return usevalor1;
    }

    public void setUsevalor1(boolean usevalor1) {
        this.usevalor1 = usevalor1;
    }

    public boolean isUsevalor2() {
        return usevalor2;
    }

    public void setUsevalor2(boolean usevalor2) {
        this.usevalor2 = usevalor2;
    }

    @Override
    public String toString() {
        return "Ficha{" + "id=" + id + ", usevalor1=" + usevalor1 + ", usevalor2=" + usevalor2 + '}';
    }
    
    
    
}
