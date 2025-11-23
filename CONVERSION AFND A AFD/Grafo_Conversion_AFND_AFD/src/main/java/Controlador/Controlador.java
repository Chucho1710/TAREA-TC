/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Automatas.AFD;
import Grafico.toDOT;
import Modelo.Conversion;
import Vista.Vista;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Yelitz flórez
 */
public class Controlador {
    private Vista vista;
    private Conversion modelo;
    
    public Controlador(Vista vista) {
        this.vista = vista;
        modelo = new Conversion();
    }
   
    
    public void mostrarAFDGrafo() {
        AFD afd = modelo.convertir(); 
        toDOT to = new toDOT(afd);
              
        try {
            to.exportToImage("afd.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        vista.getCanvas1().setImagen("afd.png");
    }
   
    
    public void agregarAFND(){
        
        if(vista.getCmbEstOrigen() !=null || vista.getCmbEstDestino() !=null || vista.getCmbSimbolo() !=null){
            vista.getCmbEstOrigen().removeAllItems();
            vista.getCmbEstDestino().removeAllItems();
            vista.getCmbSimbolo().removeAllItems();
        }
        
        //agrega estados del afnd
        String est = vista.getTxtEstadosAFND().getText();
        LinkedHashSet<String> estM = new LinkedHashSet<>();
        String[] datos = est.split("-");
        for (String dato : datos) {
            estM.add(dato.trim());
        }
        modelo.aggEstadosAFND(estM);
        
        //agrega alfabeto
        String alfb = vista.getTxtAlfabetoAFND().getText();
        Set<Character> alfbM = new HashSet<>();
        String[] datos1 = alfb.split("-");
        for (String dato : datos1) {
            char datoC = dato.charAt(0);
            alfbM.add(datoC);
        }
        modelo.aggAlfabeto(alfbM);
        
        //agrega estado inicial
        String estI = vista.getTxtEstIncialAFND().getText();
        
        //agrega estados finales
        String estF = vista.getTxtEstFinalesAFND().getText();
        Set<String> estFM = new HashSet<>();
        String[] datos2 = estF.split("-");
        for (String dato : datos2) {
            estFM.add(dato.trim());
        }
        
        //comprobaciones
        if(!modelo.aggEstFinales(estFM) || !modelo.aggEstInicialAFND(estI)){
            JOptionPane.showMessageDialog(null, "No se puede definir ese estado como final o inicial, porque no esta entre los estados ingresados.");
        }else{
            llenarCombos();
            modelo.aggEstFinales(estFM);
            modelo.aggEstInicialAFND(estI);
        }
    }
    
    public void llenarCombos(){
        String est = vista.getTxtEstadosAFND().getText();
        String[] datos = est.split("-");
        for (String dato : datos) {
            vista.getCmbEstOrigen().addItem(dato.trim());  // .trim() para quitar espacios
            vista.getCmbEstDestino().addItem(dato.trim());
        }
        
        String alfb = vista.getTxtAlfabetoAFND().getText();
        String[] datos1 = alfb.split("-");
        for (String dato : datos1) {
            vista.getCmbSimbolo().addItem(dato.trim());
        }
    }
    
    public void agregarTransiciones(){
        String estO = vista.getCmbEstOrigen().getSelectedItem().toString();
        String simbolo = vista.getCmbSimbolo().getSelectedItem().toString();
        char simboloC = simbolo.charAt(0);
        String estD = vista.getCmbEstDestino().getSelectedItem().toString();
        
        modelo.aggTransiciones(estO, simboloC, estD);
        mostrarTransiciones();
        
    }
    
    public void mostrarTransiciones(){
        ArrayList<String> trans = modelo.transicionesAFND();
        
        DefaultListModel model = (DefaultListModel<String>)vista.getjListTransiciones().getModel();
        model.removeAllElements();
        for (String c : trans) {
            model.addElement(c);
        }
    }
    
    public void eliminarTransicion(){
        DefaultListModel<String> model = (DefaultListModel<String>)vista.getjListTransiciones().getModel();
        int indiceSelec = vista.getjListTransiciones().getSelectedIndex();
        
        if(indiceSelec != -1){
            model.remove(indiceSelec);
        }
        System.out.println("++");
        modelo.eliminarTransAFND(indiceSelec);
        JOptionPane.showMessageDialog(null, "Transición eliminada con exito.");
    }
    
    public void convertir(){
        modelo.convertir();
    }
    
    public void mostrarAFD(){
        vista.getTxtADatosAFD().setText("");
        vista.getTxtATransicionesAFD().setText("");
        vista.getTxtADatosAFD().append(modelo.infoAFD());
        vista.getTxtATransicionesAFD().append(modelo.transicionesAFD());
    }
}
