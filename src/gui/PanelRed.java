/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import modelo.ONT;
import modelo.ONTResidencial;
import modelo.ONTEmpresarial;
import modelo.OLT;

/**
 *
 * @author USER
 */
public class PanelRed extends javax.swing.JPanel {

    /**
     * Creates new form PanelRed
     */
    public PanelRed(OLT olt) {
        initComponents();
        this.olt=olt;
    }
    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
    int ancho = getWidth();
    int alto = getHeight();
    int centrox = ancho / 2;
    int centroy = alto / 2;
    
    // Dibujar OLT en el centro
    int anchoOLT = 120;
    int altoOLT = 60;
    int x0 = centrox - anchoOLT / 2;
    int y0 = centroy - altoOLT / 2;
    
    g2d.setColor(new Color(238, 237, 254)); // fondo muy claro
    g2d.fillRoundRect(x0, y0, anchoOLT, altoOLT, 15, 15);
    g2d.setColor(new Color(60, 52, 137)); // borde morado
    g2d.setStroke(new BasicStroke(2f));
    g2d.drawRoundRect(x0, y0, anchoOLT, altoOLT, 15, 15);
    
    // Texto OLT
    g2d.setColor(new Color(60, 52, 137));
    g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
    String txtOLT = olt.getId();
    FontMetrics fm = g2d.getFontMetrics();
    int xText = centrox - fm.stringWidth(txtOLT) / 2;
    int yText = centroy - 10;
    g2d.drawString(txtOLT, xText, yText);
    
    // Porcentaje de uso
    g2d.setFont(new Font("SansSerif", Font.PLAIN, 10));
    double pctUso = olt.getPorcentajeUso();
    String txtPct = String.format("%.1f%% uso", pctUso);
    int xPct = centrox - g2d.getFontMetrics().stringWidth(txtPct) / 2;
    g2d.drawString(txtPct, xPct, centroy + 18);
    
    // Barra de progreso dentro de la OLT
    int barraAncho = 100;
    int barraAlto = 4;
    int barraX = centrox - barraAncho / 2;
    int barraY = centroy + 8;
    g2d.setColor(new Color(211, 209, 199)); // fondo gris
    g2d.fillRect(barraX, barraY, barraAncho, barraAlto);
    
    int barraLlena = (int) (barraAncho * pctUso / 100);
    g2d.setColor(new Color(127, 119, 221)); // morado
    g2d.fillRect(barraX, barraY, barraLlena, barraAlto);
    
    // Dibujar ONTs alrededor
    List<ONT> onts = olt.getListaONTs();
    if (onts != null && !onts.isEmpty()) {
        
        int numONTs = onts.size();
        double radio = Math.min(ancho, alto) * 0.35; // distancia del centro
        
        for (int i = 0; i < numONTs; i++) {
            ONT ont = onts.get(i);
            
            // Ángulo en radianes: distribuir equidistante alrededor del círculo
            double angulo = (2 * Math.PI * i) / numONTs;
            
            // Coordenadas de la ONT
            int ontX = (int) (centrox + radio * Math.cos(angulo));
            int ontY = (int) (centroy + radio * Math.sin(angulo));
            
            // Dibujar línea de fibra desde OLT hasta ONT
            g2d.setColor(new Color(180, 178, 169)); // gris neutro
            g2d.setStroke(new BasicStroke(1f));
            g2d.drawLine(centrox, centroy, ontX, ontY);
            
            // Etiqueta de distancia en la mitad de la línea
            int midX = (centrox + ontX) / 2;
            int midY = (centroy + ontY) / 2;
            g2d.setColor(new Color(150, 140, 130));
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 9));
            String distLabel = String.format("%.1f km", ont.getDistanciaFibra());
            g2d.drawString(distLabel, midX + 5, midY - 5);
            
            // Obtener color según tipo de ONT
            Color colorONT = getColorONT(ont);
            
            // Dibujar rectángulo de ONT
            int anchoONT = 85;
            int altoONT = 50;
            int xONT = ontX - anchoONT / 2;
            int yONT = ontY - altoONT / 2;
            
            g2d.setColor(colorONT);
            g2d.fillRoundRect(xONT, yONT, anchoONT, altoONT, 8, 8);
            
            // Borde
            g2d.setColor(getColorBorde(ont));
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawRoundRect(xONT, yONT, anchoONT, altoONT, 8, 8);
            
            // Texto: ID
            g2d.setColor(getColorTexto(ont));
            g2d.setFont(new Font("SansSerif", Font.BOLD, 11));
            String id = ont.getId();
            int xIdText = ontX - g2d.getFontMetrics().stringWidth(id) / 2;
            g2d.drawString(id, xIdText, yONT + 15);
            
            // Texto: Banda asignada
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 9));
            String banda = String.format("%.0f Mbps", ont.getAnchoBajada());
            int xBandaText = ontX - g2d.getFontMetrics().stringWidth(banda) / 2;
            g2d.drawString(banda, xBandaText, yONT + 28);
            
            // Pequeña barra de progreso dentro de la ONT
            int barraONTAncho = 70;
            int barraONTAlto = 3;
            int barraONTX = ontX + (anchoONT - barraONTAncho) / 2;
            int barraONTY = yONT + altoONT - 10;
            
            g2d.setColor(new Color(211, 209, 199));
            g2d.fillRect(barraONTX, barraONTY, barraONTAncho, barraONTAlto);
            
            double pctONT = (ont.getAnchoBajada() / olt.getAnchoTotalBajada()) * 100;
            int barraONTLlena = (int) (barraONTAncho * pctONT / 100);
            g2d.setColor(getColorBarra(ont));
            g2d.fillRect(barraONTX, barraONTY, barraONTLlena, barraONTAlto);
        }
    }
}
    private Color getColorONT(ONT ont) {
    if (ont instanceof ONTResidencial) {
        return new Color(234, 243, 222); // verde muy claro
    } else if (ont instanceof ONTEmpresarial) {
        return new Color(230, 241, 251); // azul muy claro
    }
    return new Color(240, 240, 240);
}

    private Color getColorBorde(ONT ont) {
    if (ont instanceof ONTResidencial) {
        return new Color(151, 196, 89); // verde
    } else if (ont instanceof ONTEmpresarial) {
        return new Color(133, 183, 235); // azul
    }
    return Color.GRAY;
}

    private Color getColorTexto(ONT ont) {
    if (ont instanceof ONTResidencial) {
        return new Color(39, 80, 10); // verde oscuro
    } else if (ont instanceof ONTEmpresarial) {
        return new Color(12, 68, 124); // azul oscuro
    }
    return Color.BLACK;
}

    private Color getColorBarra(ONT ont) {
    if (ont instanceof ONTResidencial) {
        return new Color(99, 153, 34); // verde saturado
    } else if (ont instanceof ONTEmpresarial) {
        return new Color(24, 95, 165); // azul saturado
    }
    return Color.GRAY;
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private OLT olt;
}
