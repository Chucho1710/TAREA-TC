/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Automatas.AFD;
import Automatas.AFND;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author rinco
 */

public class Conversion {
    AFND afnd;
    AFD afd;
    
    public Conversion(){
        afnd = new AFND();
        afd = new AFD();
    }
    
    public void limpiarAutomatas(){
        afnd = null;
        afd = null;
    }
    
    public void aggEstadosAFND(LinkedHashSet<String> est){
        afnd.setEstados(est);
    }
    
    public boolean aggEstInicialAFND(String estI){
        if(!afnd.getEstados().contains(estI)) return false;
        afnd.setEstInicial(estI);
        return true;
    }
    
    public boolean aggEstFinales(Set<String> estFinales){
        for(String e : estFinales){
            if(!afnd.getEstados().contains(e)){
                return false;
            }
        }
        afnd.setEstFinales(estFinales);
        return true;
    }
    
    public void aggAlfabeto(Set<Character> alfabeto){
        afnd.setAlfabeto(alfabeto);
    }
    
    public void aggTransiciones(String estadoOrigen, char simbolo, String estadoDestino){
        afnd.aggTransicion(estadoOrigen, simbolo, estadoDestino);
    }
    
    public ArrayList<String> transicionesAFND(){
        ArrayList<String> trans = new ArrayList<>();
        for (String estado : afnd.getTrans().keySet()) {
            Map<Character, Set<String>> transiciones = afnd.getTrans().get(estado);
            for (char simbolo : transiciones.keySet()) {
                trans.add("  " + estado + " --- " + simbolo + " ---> " + transiciones.get(simbolo));
            }
        }
        return trans;
    }
    
    public void eliminarTransAFND(int indice){
        ArrayList<String> trans = transicionesAFND();
        String transElim = "";
        if(indice > 0 || indice <= trans.size()){
            transElim = trans.get(indice);
        }
        String[] parte = transElim.split("---|---> ");
        
        if(parte.length >= 3){
            String estO = parte[0].trim();
            char sim = parte[1].trim().charAt(0);
            String estD = parte[2].substring(1).trim();
            System.out.println(estD);
            
            if(estD.startsWith("[") && estD.endsWith("]")){
                String estDL = estD.substring(1, estD.length()-1);
                String[] estadosD = estDL.split(",");
                
                for(String d : estadosD){
                    System.out.println(afnd.eliminarTrans(estO, sim, d.trim()));
                }
            }
        }
    }
    
    
    public String transicionesAFD(){
        String transAFD = "";
        for (var entry : afd.getTrans().entrySet()) {
            Set<String> origen = entry.getKey();
            String resultado1 = String.join("", origen);
            for (var t : entry.getValue().entrySet()) {
                Set<String> r = t.getValue();
                String resultado = String.join("", r);
                transAFD += ("  " + resultado1 + " --- " + t.getKey() + " ---> " + resultado +"\n");
            }
        }
        return transAFD;
    }
    
    public AFD convertir(){
        LinkedHashSet<Set<String>> est = new LinkedHashSet<>();
        LinkedHashMap<Set<String>, Map<Character, Set<String>>> trans = new LinkedHashMap<>();
        
        Queue<Set<String>> cola = new LinkedList<>();
        Set<String> inicio = new HashSet<>();
        
        inicio.add(afnd.getEstInicial());
        cola.add(inicio);
        
        Map<String, Map<Character, Set<String>>> transOrg = afnd.getTrans();
        
        while(!cola.isEmpty()){
            Set<String> estActual = cola.remove();
            est.add(estActual);
            Map<Character, Set<String>> map = new HashMap<>();
            
            for(String n : estActual){
                if(transOrg.containsKey(n)){
                    Map<Character, Set<String>> mapTemp = transOrg.get(n);
                    for(Character c : mapTemp.keySet()){
                        map.putIfAbsent(c, new HashSet<>());
                        map.get(c).addAll(mapTemp.get(c));
                    }
                }
            }
            
            for(Set<String> k : map.values()) {
                if(!est.contains(k))
                    cola.add(k);
            }
            
            trans.put(estActual, map);
        }
        afd.setEstados(est);
        afd.setEstInicial(afnd.getEstInicial());
        afd.setAlfabeto(afnd.getAlfabeto());
        afd.setTrans(trans);
        
        AFD afd1 = new AFD(est, afnd.getEstInicial(), afnd.getAlfabeto(), trans);
        afd.estadosFinales3(afnd.getEstFinales());
        afd1.estadosFinales3(afnd.getEstFinales());
        return afd1;
    }
    
    public String infoAFD(){
        String infoAFD = "";
        infoAFD += "=== AFD RESULTANTE ===";
        infoAFD = "Estados:";
        for (Set<String> estado : afd.getEstados()) {
            String r = String.join("", estado);
            infoAFD += ("  " + r);
        }

        infoAFD += "\nEstado inicial: " + afd.getEstInicial();
        infoAFD += "\nEstados finales: ";
        for(Set<String> estadoF : afd.getEstFinales()){
            String r = String.join(" ", estadoF);
            infoAFD += r;
        }
        return infoAFD;
    }
}