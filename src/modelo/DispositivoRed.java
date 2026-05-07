/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;

import java.io.Serializable;


public abstract class DispositivoRed implements Serializable{
    private String id;
    private String nombre;
    private double distanciaFibra; //en km

    public DispositivoRed(String id, String nombre, double distanciaFibra) {
        this.id = id;
        this.nombre = nombre;
        this.distanciaFibra = distanciaFibra;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getDistanciaFibra() {
        return distanciaFibra;
    }

    public void setDistanciaFibra(double distanciaFibra) {
        this.distanciaFibra = distanciaFibra;
    }
    
    public abstract double calcularAtenuacion();
    
    public abstract String getEstado();
    
    
}
