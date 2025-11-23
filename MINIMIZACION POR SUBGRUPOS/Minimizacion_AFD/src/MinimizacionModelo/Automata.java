/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MinimizacionModelo;

import java.util.List;

/**
 *
 * @author rinco
 */
public interface Automata {
    public void agregarTrans(String est1, String est2, char alf);
    public void borrarTrans(String est, char alf);
    public List<String> listaDeTrans();
}
