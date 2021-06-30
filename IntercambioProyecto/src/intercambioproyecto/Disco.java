/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intercambioproyecto;

import java.util.ArrayList;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Rodrigo
 */
public class Disco {
    
    private ArrayList<Proceso> procesos = new ArrayList();
    private GridPane disco;
    
    public Disco(GridPane disco){
        this.disco = disco;
    }
    
    public void setProceso(Proceso proceso){
        procesos.add(proceso);
    }
    
    public void popProceso(Proceso proceso){
        procesos.remove(proceso);
    }
    
    
}
