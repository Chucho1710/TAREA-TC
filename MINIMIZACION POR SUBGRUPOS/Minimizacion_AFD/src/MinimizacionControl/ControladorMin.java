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

/**
 *
 * @author rinco
 */
public class ControladorMin {
    private final VistaMin vista;
    private final ModeloMin modelo;
    
    public ControladorMin(VistaMin vista){
        this.vista = vista;
        modelo = new ModeloMin();
        DefaultListModel<String> modelo = new DefaultListModel<>();
        vista.getLstTransOg().setModel(modelo);
    }
    
    public void crearAutomata(){
        Set<String> est = new HashSet<>();
        Set<String> estF = new HashSet<>();
        Set<Character> alfabeto = new HashSet<>();
        String estInicial = vista.getCmbEstadosCreacion().getItemAt(0);
        
        DefaultComboBoxModel<String> modeloEstados = (DefaultComboBoxModel<String>) vista.getCmbEstadosCreacion().getModel();
        DefaultComboBoxModel<String> modeloAlfabeto = (DefaultComboBoxModel<String>) vista.getCmbAlfabetoCreacion().getModel();
        DefaultComboBoxModel<String> modeloEstFinales = (DefaultComboBoxModel<String>) vista.getCmbEstFinales().getModel();
        
        for(int i=0; i<modeloEstados.getSize(); i++){
            est.add((String)modeloEstados.getElementAt(i));
            vista.getCmbEstTrans1().addItem((String)modeloEstados.getElementAt(i));
            vista.getCmbEstTrans2().addItem((String)modeloEstados.getElementAt(i));
        }
        
        for(int i=0; i<modeloAlfabeto.getSize(); i++){
            alfabeto.add(modeloAlfabeto.getElementAt(i).charAt(0));
            vista.getCmbAlfTrans().addItem((String)modeloAlfabeto.getElementAt(i));
        }
        
        for(int i=0; i<modeloEstFinales.getSize(); i++)
            estF.add((String)modeloEstFinales.getElementAt(i));
        
        if(vista.getRbnAFND().isSelected()){
            AFND afnd = new AFND(est, estInicial, alfabeto, estF);
            modelo.setAuto(afnd);
        }else{
            AFD afd = new AFD(est, estInicial, alfabeto, estF);
            modelo.setAuto(afd);
        }
    }
    
    public void agregarEstado(){
        String est = vista.getTxtEstados().getText();
        String[] ests = est.split("-");
        
        for(String s : ests)
            vista.getCmbEstadosCreacion().addItem(s);
        
        vista.getTxtEstados().setText("");
    }
    
    public void agregarAlfabeto(){
        String est = vista.getTxtAlfabeto().getText();
        String[] ests = est.split("-");
        
        for(String s : ests)
            vista.getCmbAlfabetoCreacion().addItem(s);
        
        vista.getTxtAlfabeto().setText("");
    }
    
    public void agregarEstadoFinal(){
        vista.getCmbEstFinales().addItem(vista.getCmbEstadosCreacion().getSelectedItem().toString());
    }
    
    public void borrarEstado(){
        String est = vista.getCmbEstadosCreacion().getSelectedItem().toString();
        
        DefaultComboBoxModel<String> modeloEstados = (DefaultComboBoxModel<String>) vista.getCmbEstadosCreacion().getModel();
        DefaultComboBoxModel<String> modeloEstFinales = (DefaultComboBoxModel<String>) vista.getCmbEstFinales().getModel();
        
        modeloEstados.removeElement(est);
        modeloEstFinales.removeElement(est);
    }
    
    public void borrarAlfabeto(){
        String alf = vista.getCmbAlfabetoCreacion().getSelectedItem().toString();
        
        DefaultComboBoxModel<String> modeloAlfabeto = (DefaultComboBoxModel<String>) vista.getCmbAlfabetoCreacion().getModel();
        
        modeloAlfabeto.removeElement(alf);
    }
    
    public void agregarTrans(){
        String est1 = vista.getCmbEstTrans1().getSelectedItem().toString();
        String est2 = vista.getCmbEstTrans2().getSelectedItem().toString();
        char alf = vista.getCmbAlfTrans().getSelectedItem().toString().charAt(0);
        
        modelo.getAuto().agregarTrans(est1, est2, alf);
        List<String> lista = modelo.getAuto().listaDeTrans();
        
        DefaultListModel<String> modeloLista = (DefaultListModel<String>) vista.getLstTransOg().getModel();
        modeloLista.removeAllElements();
        for(String s : lista)
            modeloLista.addElement(s);
    }       
    
    public void borrarTrans(){
        String trans = vista.getLstTransOg().getSelectedValue();
        
        String[] obj = trans.split("---");
        
        modelo.getAuto().borrarTrans(obj[0], obj[1].charAt(0));
        DefaultListModel<String> modeloLista = (DefaultListModel<String>) vista.getLstTransOg().getModel();
        modeloLista.removeElementAt(vista.getLstTransOg().getSelectedIndex());
    }
    
    public void minimizar(){
        AFD afd = modelo.minimizar();
        
        Set<String> est = afd.getEstados();
        Set<Character> alf = afd.getAlfabeto();
        Map<String, Map<Character, String>> trans = afd.getTrans();
        
        for(String s : est)
            vista.getTxaEstadosMin().append(s+"\n");
        
        for(Character c : alf)
            vista.getTxaAlfabetoMin().append(c+"\n");
        
        for(String s : trans.keySet()){
            for(Character c : trans.get(s).keySet()){
                vista.getLstTransMin().append(s + "---" + c + "--->" + trans.get(s).get(c) + "\n");
            }
        }
//        
//        GraficaAFD graf = new GraficaAFD(afd);
//              
//        try {
//            graf.exportToImage("afd.png");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        vista.getCanva().setImagen("afd.png");
    }
}