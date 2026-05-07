/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;

import java.util.ArrayList;
import java.util.List;

public class OLT extends DispositivoRed {
    private double anchoTotalBajada;
    private double anchoTotalSubida;
    private List<ONT> listaONTs;

    public OLT(double anchoTotalBajada, double anchoTotalSubida, String id, String nombre, double distanciaFibra) {
        super(id, nombre, distanciaFibra);
        this.anchoTotalBajada = anchoTotalBajada;
        this.anchoTotalSubida = anchoTotalSubida;
        this.listaONTs = new ArrayList<>();
    }

    public double getAnchoTotalBajada() {
        return anchoTotalBajada;
    }

    public double getAnchoTotalSubida() {
        return anchoTotalSubida;
    }

    public List<ONT> getListaONTs() {
        return listaONTs;
    }
    
    public void agregarONT(ONT ont) {
        if(ont.getDistanciaFibra()>20){
            throw new IllegalArgumentException("Distancia " + ont.getDistanciaFibra() +
                    " km supera el  limite de 20 km.");
        }
        if(listaONTs.size()>64){
            throw new IllegalArgumentException("La OLT " + getId() + "alcanzo el limite de 64 ONT's.");
        }
        for(ONT o : listaONTs){
            if(o.getId().equals(ont.getId())){
                throw new IllegalArgumentException("Ya existe una ONT con esa Id");
            }
        }
        double bandaComprometida = 0;
        for(ONT o : listaONTs){
            bandaComprometida += o.getBandaMinima();
        }
        if(bandaComprometida + ont.getBandaMinima()> anchoTotalBajada){
            throw new IllegalStateException("Capacidad insuficiente. Banda Comprometida: "
            + bandaComprometida +" Mbps. Banda Solicitada " + ont.getBandaMinima() + " Mbs.");
        }
        listaONTs.add(ont);
        distribuirBanda();
    }
    
    public void eliminarONT(ONT ont){
        for(ONT o : listaONTs){
            if(o.equals(ont)){
                listaONTs.remove(o);
                distribuirBanda();
                break;
            }
        }
        throw new IllegalArgumentException("Esa ONT no esta conectada a esta OLT");
    }
    
    public void distribuirBanda(){
        //tengo que pensarle un fla
    }
    
    public double getPorcentajeUso(){
        double sumAnchoBajada = 0;
        for(ONT o : listaONTs){
            sumAnchoBajada += o.getAnchoBajada();
        }
        return (sumAnchoBajada / anchoTotalBajada);
    }

    @Override
    public double calcularAtenuacion() {
       double sumaAtenuacion = 0;
        for(ONT o : listaONTs){
            sumaAtenuacion += o.calcularAtenuacion();
        }
        return (sumaAtenuacion / listaONTs.size()); 
    }

    @Override
    public String getEstado() {
        return "ID: " + getId()
         + " | Capacidad total de Bajada: " + anchoTotalBajada + " Mbps"
         + " | Capacidad total de Subida: " + anchoTotalSubida + " Mbps"
         + " | Porcentaje de uso" + getPorcentajeUso()
         + " | ONT's conectadas: " + listaONTs.size() ;
    }
    
    
}
