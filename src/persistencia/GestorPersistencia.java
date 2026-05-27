/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import modelo.OLT;

public class GestorPersistencia {
    private String nombreArchivo;//Archivo.dat

    public GestorPersistencia(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    public void guardar(OLT olt) throws IOException{
        try(ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo))){
            salida.writeObject(olt);
            salida.close();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getLocalizedMessage());
            throw ex;
        }
    }
    
    public OLT cargar() {
        try(ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            OLT olt = (OLT) entrada.readObject();
            entrada.close();
            return olt;
        }catch(IOException|ClassNotFoundException e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return null;
    }
    
    
}
