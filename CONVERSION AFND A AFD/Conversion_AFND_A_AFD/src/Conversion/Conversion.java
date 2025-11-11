/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conversion;

import Automatas.AFD;
import Automatas.AFND;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author rinco
 */
public class Conversion {
    public AFD convertir(AFND afnd){
        Set<Set<String>> est = new HashSet<>();
        Set<Set<String>> estFinales = new HashSet<>();
        Map<Set<String>, Map<Character, Set<String>>> trans = new HashMap<>();
        Queue<Set<String>> cola = new LinkedList<>();
        Set<String> inicio = new HashSet<>();
        inicio.add(afnd.getEstInicial());
        cola.add(inicio);
        
        Map<String, Map<Character, Set<String>>> transOrg = afnd.getTrans();
        Set<String> estOrg = afnd.getEstados();
        Set<String> estFinalesOrg = afnd.getEstFinales();
        
        while(!cola.isEmpty()){
            Set<String> estActual = cola.remove();
            est.add(estActual);
            for(String n : estActual){
                if(transOrg.containsKey(n)){
                    Map<Character, Set<String>> mapTemp = transOrg.get(n);
                    
                }
            }
        }
        
        
        
        
        AFD afd = new AFD(est, afnd.getEstInicial(), afnd.getAlfabeto(), estFinales, trans);
        return afd;
    }
}
