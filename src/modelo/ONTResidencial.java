/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;


public class ONTResidencial extends ONT {
    private int numUsuarios;
    private boolean tieneTv; //por si incluya servicio de TV

    public ONTResidencial(int numUsuarios, boolean tieneTv, String id, String nombre, double distanciaFibra) {
        super("Residencial", id, nombre, distanciaFibra);
        this.numUsuarios = numUsuarios;
        this.tieneTv = tieneTv;
    }

    public int getNumUsuarios() {
        return numUsuarios;
    }

    public void setNumUsuarios(int numUsuarios) {
        this.numUsuarios = numUsuarios;
    }

    public boolean isTieneTv() {
        return tieneTv;
    }

    public void setTieneTv(boolean tieneTv) {
        this.tieneTv = tieneTv;
    }

    @Override
    public int getPrioridad() {
        return 1;
    }

    @Override
    public double getBandaMinima() {
        return 50.0;
    }

    @Override
    public double calcularAtenuacion() {
        return (this.getDistanciaFibra() * 0.35);
    }

    @Override
    public String getEstado() {
        return "ID: " + getId()
         + " | Perfil: Residencial"
         + " | Usuarios: " + numUsuarios
         + " | Bajada: " + getAnchoBajada() + " Mbps"
         + " | Subida: " + getAnchoSubida() + " Mbps"
         + " | Atenuación: " + calcularAtenuacion() + " dB";
    }
  
    
}
