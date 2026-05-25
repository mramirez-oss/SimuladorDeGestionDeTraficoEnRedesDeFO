/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package gui;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import modelo.ONT;
import modelo.ONTResidencial;
import modelo.ONTEmpresarial;
import modelo.OLT;

public class PanelRed extends JPanel {

    private OLT olt;

    public PanelRed(OLT olt) {
        this.olt = olt;
        setBackground(new Color(245, 245, 245));
        setPreferredSize(new Dimension(600, 400));
    }

    public void setOlt(OLT olt) {
        this.olt = olt;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (olt == null) {
            return;
        }
        
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
        
        g2d.setColor(new Color(238, 237, 254));
        g2d.fillRoundRect(x0, y0, anchoOLT, altoOLT, 15, 15);
        g2d.setColor(new Color(60, 52, 137));
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
        g2d.setColor(new Color(211, 209, 199));
        g2d.fillRect(barraX, barraY, barraAncho, barraAlto);
        
        int barraLlena = (int) (barraAncho * pctUso / 100);
        g2d.setColor(new Color(127, 119, 221));
        g2d.fillRect(barraX, barraY, barraLlena, barraAlto);
        
        // Dibujar ONTs alrededor
        List<ONT> onts = olt.getListaONTs();
        if (onts != null && !onts.isEmpty()) {
            
            int numONTs = onts.size();
            double radio = Math.min(ancho, alto) * 0.35;
            
            for (int i = 0; i < numONTs; i++) {
                ONT ont = onts.get(i);
                
                double angulo = (2 * Math.PI * i) / numONTs;
                
                int ontX = (int) (centrox + radio * Math.cos(angulo));
                int ontY = (int) (centroy + radio * Math.sin(angulo));
                
                // Dibujar línea de fibra
                g2d.setColor(new Color(180, 178, 169));
                g2d.setStroke(new BasicStroke(1f));
                g2d.drawLine(centrox, centroy, ontX, ontY);
                
                // Etiqueta de distancia
                int midX = (centrox + ontX) / 2;
                int midY = (centroy + ontY) / 2;
                g2d.setColor(new Color(150, 140, 130));
                g2d.setFont(new Font("SansSerif", Font.PLAIN, 9));
                String distLabel = String.format("%.1f km", ont.getDistanciaFibra());
                g2d.drawString(distLabel, midX + 5, midY - 5);
                
                // Obtener color según tipo de ONT
                Color colorONT = getColorONT(ont);
                Color colorBorde = getColorBorde(ont);
                Color colorTexto = getColorTexto(ont);
                Color colorBarra = getColorBarra(ont);
                
                // Dibujar rectángulo de ONT
                int anchoONT = 85;
                int altoONT = 50;
                int xONT = ontX - anchoONT / 2;
                int yONT = ontY - altoONT / 2;
                
                g2d.setColor(colorONT);
                g2d.fillRoundRect(xONT, yONT, anchoONT, altoONT, 8, 8);
                
                // Borde
                g2d.setColor(colorBorde);
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(xONT, yONT, anchoONT, altoONT, 8, 8);
                
                // Texto: ID
                g2d.setColor(colorTexto);
                g2d.setFont(new Font("SansSerif", Font.BOLD, 11));
                String id = ont.getId();
                int xIdText = ontX - g2d.getFontMetrics().stringWidth(id) / 2;
                g2d.drawString(id, xIdText, yONT + 15);
                
                // Texto: Banda asignada
                g2d.setFont(new Font("SansSerif", Font.PLAIN, 9));
                String banda = String.format("%.0f Mbps", ont.getAnchoBajada());
                int xBandaText = ontX - g2d.getFontMetrics().stringWidth(banda) / 2;
                g2d.drawString(banda, xBandaText, yONT + 28);
                
                // Barra de progreso de la ONT
                int barraONTAncho = 70;
                int barraONTAlto = 3;
                int barraONTX = ontX + (anchoONT - barraONTAncho) / 2;
                int barraONTY = yONT + altoONT - 10;
                
                g2d.setColor(new Color(211, 209, 199));
                g2d.fillRect(barraONTX, barraONTY, barraONTAncho, barraONTAlto);
                
                double pctONT = (ont.getAnchoBajada() / olt.getAnchoTotalBajada()) * 100;
                int barraONTLlena = (int) (barraONTAncho * pctONT / 100);
                g2d.setColor(colorBarra);
                g2d.fillRect(barraONTX, barraONTY, barraONTLlena, barraONTAlto);
            }
        }
    }

    private Color getColorONT(ONT ont) {
        if (ont instanceof ONTResidencial) {
            return new Color(234, 243, 222);
        } else if (ont instanceof ONTEmpresarial) {
            return new Color(230, 241, 251);
        }
        return new Color(240, 240, 240);
    }

    private Color getColorBorde(ONT ont) {
        if (ont instanceof ONTResidencial) {
            return new Color(151, 196, 89);
        } else if (ont instanceof ONTEmpresarial) {
            return new Color(133, 183, 235);
        }
        return Color.GRAY;
    }

    private Color getColorTexto(ONT ont) {
        if (ont instanceof ONTResidencial) {
            return new Color(39, 80, 10);
        } else if (ont instanceof ONTEmpresarial) {
            return new Color(12, 68, 124);
        }
        return Color.BLACK;
    }

    private Color getColorBarra(ONT ont) {
        if (ont instanceof ONTResidencial) {
            return new Color(99, 153, 34);
        } else if (ont instanceof ONTEmpresarial) {
            return new Color(24, 95, 165);
        }
        return Color.GRAY;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
