/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;


public abstract class ONT extends DispositivoRed {
        private String perfil;
        private double anchoBajada;
        private double anchoSubida;

    public ONT(String perfil, String id, String nombre, double distanciaFibra) {
        super(id, nombre, distanciaFibra);
        this.perfil = perfil;
    }

    public double getAnchoBajada() {
        return anchoBajada;
    }

    public void setAnchoBajada(double anchoBajada) {
        this.anchoBajada = anchoBajada;
    }

    public double getAnchoSubida() {
        return anchoSubida;
    }

    public void setAnchoSubida(double anchoSubida) {
        this.anchoSubida = anchoSubida;
    }
        
        
   public abstract int getPrioridad();
   
   public abstract double getBandaMinima();
   
   public abstract double calcularAtenuacion();
}
