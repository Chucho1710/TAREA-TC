/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Automatas;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author rinco
 */
public class AFD{
    private LinkedHashSet<Set<String>> estados;
    private String estInicial;
    private Set<Character> alfabeto;
    private Set<Set<String>> estFinales;
    private LinkedHashMap<Set<String>, Map<Character, Set<String>>> trans;

    public AFD(){
        estados = new LinkedHashSet<>();
        alfabeto = new HashSet<>();
        trans = new LinkedHashMap<>();
        estFinales = new HashSet<>();
    }
    
    public AFD(LinkedHashSet<Set<String>> estados, String estInicial, Set<Character> alfabeto, LinkedHashMap<Set<String>, Map<Character, Set<String>>> trans) {
        this.estados = estados;
        this.estInicial = estInicial;
        this.alfabeto = alfabeto;
        this.trans = trans;
        estFinales = new HashSet<>();
    }

    public LinkedHashSet<Set<String>> getEstados() {
        return estados;
    }

    public void setEstados(LinkedHashSet<Set<String>> estados) {
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

    public LinkedHashMap<Set<String>, Map<Character, Set<String>>> getTrans() {
        return trans;
    }

    public void setTrans(LinkedHashMap<Set<String>, Map<Character, Set<String>>> trans) {
        this.trans = trans;
    }
    //Metodos
    //OPCION 1
    public void estadosFinales3(Set<String> estFinalesAFND1){
        estFinales = new LinkedHashSet<>();  // faltaba inicializarlo AQUIIIII!!!!!!
        //System.out.println(estFinalesAFND1);
        for(Set<String> estAFD : estados){
            //System.out.println("Verificando estado: "+estAFD);
            for(String estI : estAFD){
                if(estFinalesAFND1.contains(estI)){
                    estFinales.add(estAFD);
                }
            }
            
        }
    }
}