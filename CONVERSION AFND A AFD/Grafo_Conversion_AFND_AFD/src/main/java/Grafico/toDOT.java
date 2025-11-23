/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grafico;

import Automatas.AFD;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import java.io.File;
import java.io.FileWriter;
import java.util.Set;

/**
 *
 * @author Yelitz flórez
 */
public class toDOT {
    AFD afd;
    
    public toDOT(AFD afd){
        this.afd = afd;
    }
    public String toDOT() {
        StringBuilder sb = new StringBuilder();

        sb.append("digraph AFD {\n");
        sb.append("    rankdir=LR;\n");
        sb.append("    node [shape=circle, style=filled, fillcolor=white];\n\n");

        // Estado inicial (flecha fantasma)
        sb.append("    invisible_start [shape=point, style=invis];\n");
        sb.append("    invisible_start -> \"" + afd.getEstInicial() + "\";\n\n");

        // Estados finales
        for (Set<String> estFinal : afd.getEstFinales()) {
            String nombre = String.join("", estFinal);
             sb.append("  \"" + nombre + "\" [shape=doublecircle, style=filled, fillcolor=\"lightgreen\"];\n");
        }
        sb.append("\n");

        // Estados normales (no finales)
        for (Set<String> estado : afd.getEstados()) {
            String nombre = String.join("", estado);
            if (!esFinal(estado)) {
                sb.append("    \"" + nombre + "\" [shape=circle];\n");
            }
        }
        sb.append("\n");

        // Transiciones
        for (var entry : afd.getTrans().entrySet()) {
            Set<String> origen = entry.getKey();
            String origenNombre = String.join("", origen);

            for (var tr : entry.getValue().entrySet()) {
                Character simbolo = tr.getKey();
                Set<String> destino = tr.getValue();
                String destinoNombre = String.join("", destino);

                sb.append("    \"" + origenNombre + "\" -> \"" +
                          destinoNombre + "\" [label=\"" + simbolo + "\"];\n");
            }
        }

        sb.append("}\n");

        return sb.toString();
    }

    // Método auxiliar para saber si un estado es final
    private boolean esFinal(Set<String> estado) {
        for (Set<String> f : afd.getEstFinales()) {
            if (f.equals(estado)) return true;
        }
        return false;
    }
    
    
    public void exportToImage(String rutaDOT, String rutaPNG) throws Exception {
        // 1. Crear el archivo .dot
        FileWriter fw = new FileWriter(rutaDOT);
        fw.write(this.toDOT());
        fw.close();

        // 2. Ejecutar GraphViz para generar la imagen
        ProcessBuilder pb = new ProcessBuilder(
                "dot", "-Tpng", rutaDOT, "-o", rutaPNG
        );
        pb.redirectErrorStream(true);
        Process p = pb.start();

        // Esperar a que termine
        p.waitFor();
    }

    public void exportToImage(String rutaPNG) throws Exception {
        String dot = this.toDOT(); // usa tu método toDOT()

        Graphviz.fromString(dot)
        .width(1200)       // ← aumenta la resolución
        .render(Format.PNG)
        .toFile(new File(rutaPNG));
    }
}
