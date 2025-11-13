/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Automatas;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author rinco
 */
public class AFND {
    private Set<String> estados;
    private String estInicial;
    private Set<Character> alfabeto;
    private Set<String> estFinales;
    private Map<String, Map<Character, Set<String>>> trans;

    public AFND(Set<String> estados, String estInicial, Set<Character> alfabeto, Set<String> estFinales, Map<String, Map<Character, Set<String>>> trans) {
        this.estados = estados;
        this.estInicial = estInicial;
        this.alfabeto = alfabeto;
        this.estFinales = estFinales;
        this.trans = trans;
    }

    public Set<String> getEstados() {
        return estados;
    }

    public void setEstados(Set<String> estados) {
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

    public Map<String, Map<Character, Set<String>>> getTrans() {
        return trans;
    }

    public void setTrans(Map<String, Map<Character, Set<String>>> trans) {
        this.trans = trans;
    }

    //metodos 
    public void aggTransicion(String estadoOrigen, char simbolo, String estadoDestino){
        trans.putIfAbsent(estadoOrigen, new HashMap<>());
        trans.get(estadoOrigen).putIfAbsent(simbolo, new HashSet<>());
        trans.get(estadoOrigen).get(simbolo).add(estadoDestino); 
    }

    public void mostrarAFND() {
        System.out.println("=== AFND ===");
        System.out.println("Estados: " + estados);
        System.out.println("Alfabeto: " + alfabeto);
        System.out.println("Estado inicial: " + estInicial);
        System.out.println("Estados finales: " + estFinales);
        System.out.println("Transiciones:");
        
        for (String estado : trans.keySet()) {
            Map<Character, Set<String>> transiciones = trans.get(estado);
            for (char simbolo : transiciones.keySet()) {
                System.out.println("  " + estado + " --" + simbolo + "--> " + transiciones.get(simbolo));
            }
        }
    }
}

