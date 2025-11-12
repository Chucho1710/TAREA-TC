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
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author rinco
 */
public class Conversion {
    public static void main(String[] args){
        Set<String> estados = Set.of("X","Y","Z","W","P");
        String estInicial = "X";
        Set<Character> alfabeto = Set.of('a', 'b');
        Set<String> estFinales = Set.of("W");

        // Mapa de transiciones
        Map<String, Map<Character, Set<String>>> trans = new HashMap<>();

        trans.put("X", new HashMap<>());
        trans.put("Y", new HashMap<>());
        trans.put("Z", new HashMap<>());
        trans.put("W", new HashMap<>());
        trans.put("P", new HashMap<>());

        // A --a--> {A, B}
        trans.get("X").put('a', new HashSet<>(List.of("Y")));
        // A --b--> {A}
        trans.get("X").put('b', new HashSet<>(List.of("Z")));
        // B --b--> {C}
        trans.get("Y").put('a', new HashSet<>(List.of("W")));
        // A --b--> {A}
        trans.get("Y").put('b', new HashSet<>(List.of("P")));
        
        trans.get("Z").put('a', new HashSet<>(List.of("Y" , "P")));
        // A --b--> {A}
        trans.get("Z").put('b', new HashSet<>(List.of("Y")));
        
        trans.get("W").put('a', new HashSet<>(List.of("Y")));
        // A --b--> {A}
        trans.get("W").put('b', new HashSet<>(List.of("W")));
        
        trans.get("P").put('a', new HashSet<>(List.of("W")));
        // A --b--> {A}
        trans.get("P").put('b', new HashSet<>(List.of("P")));

        // Crear el AFND con tu constructor
        AFND afnd = new AFND(estados, estInicial, alfabeto, estFinales, trans);

        // --- Convertir a AFD ---
        AFD afd = convertir(afnd);

        // --- Imprimir resultado ---
        System.out.println("=== AFD RESULTANTE ===");
        System.out.println("Estados:");
        for (Set<String> estado : afd.getEstados()) {
            System.out.println("  " + estado);
        }

        System.out.println("\nEstado inicial: " + afd.getEstInicial());
//        System.out.println("Estados finales: " + afd.estadosFinales);

        System.out.println("\nTransiciones:");
        for (var entry : afd.getTrans().entrySet()) {
            Set<String> origen = entry.getKey();
            for (var t : entry.getValue().entrySet()) {
                System.out.println("  " + origen + " --" + t.getKey() + "--> " + t.getValue());
            }
        }
    }
    
    public static AFD convertir(AFND afnd){
        Set<Set<String>> est = new HashSet<>();
        Set<Set<String>> estFinales = new HashSet<>();
        Map<Set<String>, Map<Character, Set<String>>> trans = new HashMap<>();
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
        
        AFD afd = new AFD(est, afnd.getEstInicial(), afnd.getAlfabeto(), estFinales, trans);
        return afd;
    }
}