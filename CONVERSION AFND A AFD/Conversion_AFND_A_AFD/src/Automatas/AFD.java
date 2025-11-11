/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Automatas;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author rinco
 */
public class AFD {
    private Set<Set<String>> estados;
    private String estInicial;
    private Set<Character> alfabeto;
    private Set<Set<String>> estFinales;
    private Map<Set<String>, Map<Character, Set<String>>> trans;

    public AFD(Set<Set<String>> estados, String estInicial, Set<Character> alfabeto, Set<Set<String>> estFinales, Map<Set<String>, Map<Character, Set<String>>> trans) {
        this.estados = estados;
        this.estInicial = estInicial;
        this.alfabeto = alfabeto;
        this.estFinales = estFinales;
        this.trans = trans;
    }

    public Set<Set<String>> getEstados() {
        return estados;
    }

    public void setEstados(Set<Set<String>> estados) {
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

    public Set<Set<String>> getEstFinales() {
        return estFinales;
    }

    public void setEstFinales(Set<Set<String>> estFinales) {
        this.estFinales = estFinales;
    }

    public Map<Set<String>, Map<Character, Set<String>>> getTrans() {
        return trans;
    }

    public void setTrans(Map<Set<String>, Map<Character, Set<String>>> trans) {
        this.trans = trans;
    }
}