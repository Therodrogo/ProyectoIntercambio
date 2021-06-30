/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intercambioproyecto;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Rodrigo
 */
public class MemoriaPrincipal {
    
    private ArrayList<Proceso> procesos = new ArrayList();
    private GridPane memoria;
    
    private int index;
    
    private int ramMemoria=10;
    
    public MemoriaPrincipal(GridPane memoria){
        this.memoria = memoria;
        index=0;
    }
    
    
    public void setProceso(Proceso proceso){
        procesos.add(proceso);
        inToMemory(proceso);
    }
    
    public void popProceso(Proceso proceso){
        procesos.remove(proceso);
    }
    
    public void inToMemory(Proceso proceso){
            
            if (ramMemoria >= proceso.getCantBloques()) {
                if (proceso.getTiempo()==0) {
                    popProceso(proceso);
                }
                for (int i = 0; i < proceso.getCantBloques(); i++) {
                    String id = proceso.getIdentificador();
                    Label item = new Label(id);
                    item.setPrefSize(183, 30);
                    
                    proceso.avanzarTiempo(); 
                    
                    memoria.add(item, 0, index);
                    
                    ramMemoria--;
                    index++;
                }
            }
            else{
                //Va la cola de espera
                System.out.println("No hay espacio para este proceso.");
            }
                

        
        
    }

    public GridPane getMemoria() {
        return memoria;
    }
    
    
    
}
