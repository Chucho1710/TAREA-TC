/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MinimizacionModelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author rinco
 */
public class AFND implements Automata{
    private Set<String> estados;
    private String estInicial;
    private Set<Character> alfabeto;
    private Set<String> estFinales;
    private Map<String, Map<Character, Set<String>>> trans;

    public AFND(Set<String> estados, String estInicial, Set<Character> alfabeto, Set<String> estFinales) {
        this.estados = estados;
        this.estInicial = estInicial;
        this.alfabeto = alfabeto;
        this.estFinales = estFinales;
        this.trans = new HashMap<>();
    }

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

    @Override
    public void agregarTrans(String est1, String est2, char alf) {
        if(trans.containsKey(est1)){
            Map<Character, Set<String>> map = trans.get(est1);
            if(map.containsKey(alf)){
                map.get(alf).add(est2);
            }else{
                Set<String> set = new HashSet<>();
                set.add(est2);
                map.put(alf, set);
            }
            trans.put(est1, map);
            return;
        }
        Map<Character, Set<String>> map = new HashMap<>();
        Set<String> set = new HashSet<>();
        set.add(est2);
        map.put(alf, set);
        trans.put(est1, map);
    }

    @Override
    public void borrarTrans(String est, char alf) {
        trans.get(est).remove(alf);
    }
    
    @Override
    public List<String> listaDeTrans() {
        List<String> lista = new ArrayList<>();
        
        for(String s : trans.keySet()){
            for(Character c : trans.get(s).keySet()){
                String temp = s + "---" + c + "--->";
                for(String s2 : trans.get(s).get(c))
                    temp += s2;
                lista.add(temp);
            }
        }
        
        Collections.sort(lista);
        
        return lista;
    }
}