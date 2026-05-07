/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;


public class ONTEmpresarial extends ONT {
        private double slaGarantizado;
        private int numIpsFijas;

    public ONTEmpresarial(double slaGarantizado, int numIpsFijas, String perfil, String id, String nombre, double distanciaFibra) {
        super("Empresarial", id, nombre, distanciaFibra);
        this.slaGarantizado = slaGarantizado;
        this.numIpsFijas = numIpsFijas;
    }

    public double getSlaGarantizado() {
        return slaGarantizado;
    }

    public void setSlaGarantizado(double slaGarantizado) {
        this.slaGarantizado = slaGarantizado;
    }

    public int getNumIpsFijas() {
        return numIpsFijas;
    }

    public void setNumIpsFijas(int numIpsFijas) {
        this.numIpsFijas = numIpsFijas;
    }

    @Override
    public int getPrioridad() {
        return 2;
    }

    @Override
    public double getBandaMinima() {
        return slaGarantizado;
    }

    @Override
    public double calcularAtenuacion() {
        return (getDistanciaFibra()* 0.35);
    }

    @Override
    public String getEstado() {
        return "ID: " + getId()
         + " | Perfil: Residencial"
         + " | Mbps de SLA minimo: " + slaGarantizado
         + " | Numeros de Ip's fisicas: " + numIpsFijas
         + " | Bajada: " + getAnchoBajada() + " Mbps"
         + " | Subida: " + getAnchoSubida() + " Mbps"
         + " | Atenuación: " + calcularAtenuacion() + " dB";
    }
      
    
        
}
