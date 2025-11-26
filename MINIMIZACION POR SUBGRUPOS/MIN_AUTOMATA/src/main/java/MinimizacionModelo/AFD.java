/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MinimizacionModelo;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author rinco
 */
public class AFD {

    private Set<String> estados;
    private String estInicial;
    private Set<Character> alfabeto;
    private Set<String> estFinales;
    private Map<String, Map<Character, String>> trans;

    public AFD(Set<String> estados, String estInicial, Set<Character> alfabeto, Set<String> estFinales, Map<String, Map<Character, String>> trans) {
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

    public Map<String, Map<Character, String>> getTrans() {
        return trans;
    }

    public void setTrans(Map<String, Map<Character, String>> trans) {
        this.trans = trans;
    }
}
