/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package main;

import gui.VentanaInicialOLT;
import gui.VentanaPrincipal;
import modelo.OLT;
import javax.swing.SwingUtilities;

public class SimuladorMain {

   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            // Primero muestra la ventana inicial para crear/cargar OLT
            VentanaInicialOLT ventanaInicial = new VentanaInicialOLT(null);
            ventanaInicial.setVisible(true);
            
            // Después de cerrar el diálogo, obtiene la OLT seleccionada
            OLT olt = ventanaInicial.getOLTseleccionada();
            
            // Si el usuario cerró sin crear/cargar, no abre la ventana principal
            if (olt != null) {
                VentanaPrincipal ventana = new VentanaPrincipal(olt);
                ventana.setVisible(true);
            } else {
                System.out.println("No se seleccionó ninguna OLT. Cerrando aplicación.");
                System.exit(0);
            }
        });
    }

}
