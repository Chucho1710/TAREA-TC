/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MinimizacionControl;

import MinimizacionModelo.AFD;
import MinimizacionModelo.AFND;
import MinimizacionModelo.GraficaAFD;
import MinimizacionModelo.ModeloMin;
import MinimizacionVista.VistaMin;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author rinco
 */
public class ControladorMin {
    
    private final VistaMin vista;
    private final ModeloMin modelo;
    
    public ControladorMin(VistaMin vista) {
        this.vista = vista;
        modelo = new ModeloMin();
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        vista.getLstTransOg().setModel(modeloLista);
    }
    
    public void crearAutomata() {
        try {
            Set<String> est = new HashSet<>();
            Set<String> estF = new HashSet<>();
            Set<Character> alfabeto = new HashSet<>();
            String estInicial = vista.getCmbEstInicial().getSelectedItem().toString();
            
            DefaultComboBoxModel<String> modeloEstados = (DefaultComboBoxModel<String>) vista.getCmbEstadosCreacion().getModel();
            DefaultComboBoxModel<String> modeloAlfabeto = (DefaultComboBoxModel<String>) vista.getCmbAlfabetoCreacion().getModel();
            DefaultComboBoxModel<String> modeloEstFinales = (DefaultComboBoxModel<String>) vista.getCmbEstFinales().getModel();
            
            for (int i = 0; i < modeloEstados.getSize(); i++) {
                est.add((String) modeloEstados.getElementAt(i));
            }
            
            for (int i = 0; i < modeloAlfabeto.getSize(); i++) {
                alfabeto.add(modeloAlfabeto.getElementAt(i).charAt(0));
            }
            
            for (int i = 0; i < modeloEstFinales.getSize(); i++) {
                estF.add((String) modeloEstFinales.getElementAt(i));
            }
            
            AFND afnd = new AFND(est, estInicial, alfabeto, estF);
            modelo.setAuto(afnd);
            
            vista.getCmbEstTrans1().removeAllItems();
            vista.getCmbEstTrans2().removeAllItems();
            vista.getCmbAlfTrans().removeAllItems();
            DefaultListModel<String> modeloLista = (DefaultListModel<String>) vista.getLstTransOg().getModel();
            modeloLista.removeAllElements();
            
            for (int i = 0; i < modeloEstados.getSize(); i++) {
                vista.getCmbEstTrans1().addItem((String) modeloEstados.getElementAt(i));
                vista.getCmbEstTrans2().addItem((String) modeloEstados.getElementAt(i));
            }
            
            for (int i = 0; i < modeloAlfabeto.getSize(); i++) {
                vista.getCmbAlfTrans().addItem((String) modeloAlfabeto.getElementAt(i));
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Rellene todos los datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void agregarEstado() {
        String est = vista.getTxtEstados().getText();
        String[] ests = est.split("-");
        
        for (String s : ests) {
            s = s.trim();
            if (s.isEmpty()) {
                continue;
            }
            boolean q = true;
            for (int i = 0; i < vista.getCmbEstadosCreacion().getItemCount(); i++) {
                if (vista.getCmbEstadosCreacion().getItemAt(i).equals(s)) {
                    q = false;
                    break;
                }
            }
            if (q) {
                vista.getCmbEstadosCreacion().addItem(s);
                vista.getCmbEstInicial().addItem(s);
            }
        }
        
        vista.getTxtEstados().setText("");
    }
    
    public void agregarAlfabeto() {
        String est = vista.getTxtAlfabeto().getText();
        String[] ests = est.split("-");
        
        for (String s : ests) {
            s = s.trim();
            if (s.isEmpty()) {
                continue;
            }
            boolean q = true;
            for (int i = 0; i < vista.getCmbAlfabetoCreacion().getItemCount(); i++) {
                if (vista.getCmbAlfabetoCreacion().getItemAt(i).equals(s)) {
                    q = false;
                    break;
                }
            }
            if (q) {
                vista.getCmbAlfabetoCreacion().addItem(s);
            }
        }
        
        vista.getTxtAlfabeto().setText("");
    }
    
    public void agregarEstadoFinal() {
        try {
            String s = vista.getCmbEstadosCreacion().getSelectedItem().toString();
            boolean q = true;
            
            for (int i = 0; i < vista.getCmbEstFinales().getItemCount(); i++) {
                if (vista.getCmbEstFinales().getItemAt(i).equals(s)) {
                    q = false;
                    break;
                }
            }
            if (q) {
                vista.getCmbEstFinales().addItem(s);
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No hay estados creados", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void quitarEstadoFinal() {
        try {
            String est = vista.getCmbEstFinales().getSelectedItem().toString();
            
            DefaultComboBoxModel<String> modeloEstFinales = (DefaultComboBoxModel<String>) vista.getCmbEstFinales().getModel();
            
            modeloEstFinales.removeElement(est);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay estados finales para quitar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void borrarEstado() {
        try {
            String est = vista.getCmbEstadosCreacion().getSelectedItem().toString();
            
            DefaultComboBoxModel<String> modeloEstados = (DefaultComboBoxModel<String>) vista.getCmbEstadosCreacion().getModel();
            DefaultComboBoxModel<String> modeloEstFinales = (DefaultComboBoxModel<String>) vista.getCmbEstFinales().getModel();
            DefaultComboBoxModel<String> modeloEstInicial = (DefaultComboBoxModel<String>) vista.getCmbEstInicial().getModel();
            
            modeloEstados.removeElement(est);
            modeloEstFinales.removeElement(est);
            modeloEstInicial.removeElement(est);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay estados para borrar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void borrarAlfabeto() {
        try {
            String alf = vista.getCmbAlfabetoCreacion().getSelectedItem().toString();
            
            DefaultComboBoxModel<String> modeloAlfabeto = (DefaultComboBoxModel<String>) vista.getCmbAlfabetoCreacion().getModel();
            
            modeloAlfabeto.removeElement(alf);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay simbolos para borrar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void agregarTrans() {
        try {
            String est1 = vista.getCmbEstTrans1().getSelectedItem().toString();
            String est2 = vista.getCmbEstTrans2().getSelectedItem().toString();
            char alf = vista.getCmbAlfTrans().getSelectedItem().toString().charAt(0);
            
            modelo.getAuto().agregarTrans(est1, est2, alf);
            List<String> lista = modelo.getAuto().listaDeTrans();
            
            DefaultListModel<String> modeloLista = (DefaultListModel<String>) vista.getLstTransOg().getModel();
            modeloLista.removeAllElements();
            
            for (String s : lista) {
                modeloLista.addElement(s);
            }
        } catch (java.lang.NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No hay estados y/o simbolo seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void borrarTrans() {
        try {
            String trans = vista.getLstTransOg().getSelectedValue();
            
            String[] obj = trans.split("---");
            
            modelo.getAuto().borrarTrans(obj[0], obj[1].charAt(0));
            DefaultListModel<String> modeloLista = (DefaultListModel<String>) vista.getLstTransOg().getModel();
            modeloLista.removeElementAt(vista.getLstTransOg().getSelectedIndex());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Seleccione alguna transicion", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void minimizar() {
        try {
            AFD afd = modelo.minimizar();
            
            actualizarTextAreas(afd);
            
            GraficaAFD graf = new GraficaAFD(afd);
            
            try {
                graf.exportToImage("afd.png");
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            vista.getCanva().setImagen("afd.png");
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No hay ningun automata creado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void actualizarTextAreas(AFD afd) {
        Set<String> est = afd.getEstados();
        Set<String> estF = afd.getEstFinales();
        Set<Character> alf = afd.getAlfabeto();
        Map<String, Map<Character, String>> trans = afd.getTrans();
        
        vista.getTxaEstadosMin().setText("");
        vista.getTxaAlfabetoMin().setText("");
        vista.getLstTransMin().setText("");
        
        vista.getTxaEstadosMin().append("ESTADOS:" + "\n");
        for (String s : est) {
            vista.getTxaEstadosMin().append(s + "\n");
        }
        vista.getTxaEstadosMin().append("\n" + "ESTADO INICIAL:" + "\n" + afd.getEstInicial() + "\n");
        vista.getTxaEstadosMin().append("\n" + "ESTADOS FINALES:" + "\n");
        for (String s : estF) {
            vista.getTxaEstadosMin().append(s + "\n");
        }
        
        for (Character c : alf) {
            vista.getTxaAlfabetoMin().append(c + "\n");
        }
        
        for (String s : trans.keySet()) {
            for (Character c : trans.get(s).keySet()) {
                vista.getLstTransMin().append(s + "---" + c + "--->" + trans.get(s).get(c) + "\n");
            }
        }
    }
}