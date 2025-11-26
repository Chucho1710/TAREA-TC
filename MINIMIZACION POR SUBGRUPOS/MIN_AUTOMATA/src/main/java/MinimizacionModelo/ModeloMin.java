/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MinimizacionModelo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author rinco
 */
public class ModeloMin {
    private AFND auto;

    public ModeloMin() {
    }

    public ModeloMin(AFND auto) {
        this.auto = auto;
    }

    public AFD minimizar() {
        AFD afd = convertir(auto);

        Set<Set<String>> grupos = new HashSet<>();
        Set<Set<String>> estFinales = new HashSet<>();
        Map<Set<String>, Map<Character, Set<String>>> transF = new HashMap<>();

        Set<String> estNF = new HashSet<>();
        Set<String> estF = afd.getEstFinales();
        Map<String, Map<Character, String>> trans = afd.getTrans();

        for (String s : afd.getEstados()) {
            if (!estF.contains(s)) {
                estNF.add(s);
            }
        }

        if (!estNF.isEmpty()) {
            grupos.add(estNF);
        }
        if (!estF.isEmpty()) {
            grupos.add(estF);
        }

        boolean para = true;

        while (para) {
            para = false;
            Set<Set<String>> nuevos = new HashSet<>();
            for (Set<String> grupo : grupos) {
                Set<String> sub = new HashSet<>();
                Map<Character, Set<String>> mapTemp1 = null;
                for (String estado : grupo) {
                    if (mapTemp1 == null) {
                        mapTemp1 = getTransPorGrupo(trans.get(estado), grupos);
                    } else {
                        Map<Character, Set<String>> mapTemp2 = getTransPorGrupo(trans.get(estado), grupos);
                        if (!Objects.equals(mapTemp1, mapTemp2)) {
                            sub.add(estado);
                        }
                    }
                }
                if (sub.size() >= 1) {
                    Set<String> grupoTemp = new HashSet<>(grupo);
                    grupoTemp.removeAll(sub);
                    nuevos.add(grupoTemp);
                    nuevos.add(sub);
                    para = true;
                } else {
                    nuevos.add(grupo);
                }
            }
            grupos = nuevos;
        }

        Set<String> grupoInicial = buscarGrupoPorEstado(afd.getEstInicial(), grupos);
        String estInicial = nombreEstado(grupoInicial);

        for (Set<String> s : grupos) {
            for (String ss : s) {
                if (estF.contains(ss)) {
                    estFinales.add(s);
                    break;
                }
            }
        }

        for (Set<String> s : grupos) {
            for (String ss : s) {
                Map<Character, Set<String>> map = getTransPorGrupo(trans.get(ss), grupos);
                transF.put(s, map);
                break;
            }
        }

        return transformar(grupos, estInicial, afd.getAlfabeto(), estFinales, transF);
    }

    public Set<String> buscarGrupoPorEstado(String estado, Set<Set<String>> grupos) {
        for (Set<String> s : grupos) {
            if (s.contains(estado)) {
                return s;
            }

        }

        return null;
    }

    public Map<Character, Set<String>> getTransPorGrupo(Map<Character, String> trans, Set<Set<String>> grupos) {
        Map<Character, Set<String>> map = new HashMap<>();

        if (trans == null) {
            return map;
        }

        for (Character c : trans.keySet()) {
            if (!(buscarGrupoPorEstado(trans.get(c), grupos) == null)) {
                map.put(c, buscarGrupoPorEstado(trans.get(c), grupos));
            }
        }

        return map;
    }

    public AFD convertir(AFND afnd) {
        Set<Set<String>> est = new HashSet<>();
        Set<Set<String>> estFinales = new HashSet<>();
        Map<Set<String>, Map<Character, Set<String>>> trans = new HashMap<>();
        Queue<Set<String>> cola = new LinkedList<>();
        Set<String> inicio = new HashSet<>();

        inicio.add(afnd.getEstInicial());
        cola.add(inicio);
        Map<String, Map<Character, Set<String>>> transOrg = afnd.getTrans();

        while (!cola.isEmpty()) {
            Set<String> estActual = cola.remove();
            est.add(estActual);
            Map<Character, Set<String>> map = new HashMap<>();

            for (String n : estActual) {
                if (transOrg.containsKey(n)) {
                    Map<Character, Set<String>> mapTemp = transOrg.get(n);
                    for (Character c : mapTemp.keySet()) {
                        map.putIfAbsent(c, new HashSet<>());
                        map.get(c).addAll(mapTemp.get(c));
                    }
                }
            }

            for (Set<String> k : map.values()) {
                if (!est.contains(k)) {
                    cola.add(k);
                }
            }

            trans.put(estActual, map);
        }

        for (Set<String> s : est) {
            for (String s2 : afnd.getEstFinales()) {
                if (s.contains(s2)) {
                    estFinales.add(s);
                    break;
                }
            }
        }

        return transformar(est, afnd.getEstInicial(), afnd.getAlfabeto(), estFinales, trans);
    }

    public AFD transformar(Set<Set<String>> est, String estInicial, Set<Character> alfabeto, Set<Set<String>> estFinales, Map<Set<String>, Map<Character, Set<String>>> trans) {
        Set<String> est2 = new HashSet<>();
        Set<String> estFinales2 = new HashSet<>();
        Map<String, Map<Character, String>> trans2 = new HashMap<>();

        for (Set<String> s : est) {
            if (s == null || s.isEmpty()) {
                continue;
            }
            String estTemp = nombreEstado(s);
            est2.add(estTemp);
        }

        for (Set<String> s : estFinales) {
            String estTemp = nombreEstado(s);
            estFinales2.add(estTemp);
        }

        for (Set<String> s : trans.keySet()) {
            String estTemp = nombreEstado(s);
            Map<Character, String> mapTemp = new HashMap<>();
            for (Character c : trans.get(s).keySet()) {
                String estTemp2 = nombreEstado(trans.get(s).get(c));
                mapTemp.put(c, estTemp2);
            }

            trans2.put(estTemp, mapTemp);
        }

        AFD afd = new AFD(est2, estInicial, alfabeto, estFinales2, trans2);
        return afd;
    }

    private String nombreEstado(Set<String> conjunto) {
        Set<Character> chars = new HashSet<>();
        for (String s : conjunto) {
            for (char c : s.toCharArray()) {
                chars.add(c);
            }
        }
        return chars.stream().sorted().map(String::valueOf).collect(Collectors.joining(""));
    }

    public AFND getAuto() {
        return auto;
    }

    public void setAuto(AFND auto) {
        this.auto = auto;
    }
}
