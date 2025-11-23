/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Automatas;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author rinco
 */
public class AFND {
    private LinkedHashSet<String> estados;
    private String estInicial;
    private Set<Character> alfabeto;
    private Set<String> estFinales;
    private LinkedHashMap<String, Map<Character, Set<String>>> trans;

    public AFND(){
        trans = new LinkedHashMap<>();
    }

    public LinkedHashSet<String> getEstados() {
        return estados;
    }

    public void setEstados(LinkedHashSet<String> estados) {
        this.estados = estados;
    }

    public String getEstInicial() {
        return estInicial;
    }

    public void setEstInicial(String estInicial) {
        this.estInicial = estInicial;
    }

    public Set<Character> getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(Set<Character> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public Set<String> getEstFinales() {
        return estFinales;
    }

    public void setEstFinales(Set<String> estFinales) {
        this.estFinales = estFinales;
    }

    public LinkedHashMap<String, Map<Character, Set<String>>> getTrans() {
        return trans;
    }

    public void setTrans(LinkedHashMap<String, Map<Character, Set<String>>> trans) {
        this.trans = trans;
    }

    //metodos 
    public void aggTransicion(String estadoOrigen, char simbolo, String estadoDestino){
        trans.putIfAbsent(estadoOrigen, new HashMap<>());
        trans.get(estadoOrigen).putIfAbsent(simbolo, new HashSet<>());
        trans.get(estadoOrigen).get(simbolo).add(estadoDestino); 
    }
    
    public boolean eliminarTrans(String estadoOrigen, char simbolo, String estadoDestino){        
        if(trans.containsKey(estadoOrigen)){
            Map<Character, Set<String>> transEst = trans.get(estadoOrigen);
            
            if(transEst.containsKey(simbolo)){
                Set<String> estsDes = transEst.get(simbolo);
                boolean elim = estsDes.remove(estadoDestino);
                
                if(estsDes.isEmpty()) transEst.remove(simbolo);
                if(transEst.isEmpty()) trans.remove(estadoOrigen);
                return elim;
            }
        }
        return false;
    }
}
