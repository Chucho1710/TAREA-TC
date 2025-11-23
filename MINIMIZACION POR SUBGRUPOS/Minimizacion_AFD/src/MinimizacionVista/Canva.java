/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MinimizacionVista;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author rinco
 */
public class Canva extends Canvas{
    private Image imagen;

    public void setImagen(String ruta) {
        try {
            imagen = ImageIO.read(new File(ruta));
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limpiar() {
        imagen = null;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (imagen != null) {
            // La dibuja ajustada al tamaño del canvas
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}