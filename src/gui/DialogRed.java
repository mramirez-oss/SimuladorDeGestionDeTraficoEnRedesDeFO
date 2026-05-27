/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package gui;

import java.awt.BorderLayout;
import javax.swing.*;
import modelo.OLT;

public class DialogRed extends JDialog {

    private PanelRed panelRed;
    private OLT olt;

    public DialogRed(JFrame parent, OLT olt) {
        super(parent, "Visualización de la Red GPON", false); // false = no modal
        this.olt = olt;
        
        initComponents();
        
        setSize(700, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        panelRed = new PanelRed(olt);
        add(panelRed, BorderLayout.CENTER);
    }

    public void actualizarRed(OLT olt) {
        this.olt = olt;
        panelRed.setOlt(olt);
    }
}
