/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MinimizacionModelo;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author rinco
 */
public class GraficaAFD {
    AFD afd;
    
    public GraficaAFD(AFD afd){
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
        for (String estFinal : afd.getEstFinales()) {
             sb.append("  \"" + estFinal + "\" [shape=doublecircle, style=filled, fillcolor=\"lightgreen\"];\n");
        }
        sb.append("\n");

        // Estados normales (no finales)
        for (String estado : afd.getEstados()) {
            if (!afd.getEstFinales().contains(estado)) {
                sb.append("    \"" + estado + "\" [shape=circle];\n");
            }
        }
        sb.append("\n");

        // Transiciones
        for (var entry : afd.getTrans().entrySet()) {
            String origen = entry.getKey();

            for (var tr : entry.getValue().entrySet()) {
                Character simbolo = tr.getKey();
                String destino = tr.getValue();

                sb.append("    \"" + origen + "\" -> \"" +
                          destino + "\" [label=\"" + simbolo + "\"];\n");
            }
        }

        sb.append("}\n");

        return sb.toString();
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