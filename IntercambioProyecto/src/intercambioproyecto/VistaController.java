/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intercambioproyecto;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Rodrigo
 */
public class VistaController implements Initializable {
    @FXML
    private Pane panelMemoria;
    @FXML
    private TextField tfIdentificador;
    @FXML
    private ComboBox<Integer> comboBoxCantBloques;
    @FXML
    private ComboBox<String> comboBoxPrioridad;
    @FXML
    private ComboBox<Integer> comboBoxTiempo;
    @FXML
    private Pane panelProcesos;
    
    
    private GridPane memoria = new GridPane();
    private GridPane procesos = new GridPane();
    
    private MemoriaPrincipal memoriaPrincipal;
    int indexProcesosGrid = 0;
    int contadorProcesosGrid = 0;
    
    private ArrayList<Proceso> listaProcesos = new ArrayList();
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        memoria.setPrefSize(183, 279);
        
        panelProcesos.getChildren().add(procesos);
        
        panelMemoria.getChildren().add(memoria);
        
        memoriaPrincipal = new MemoriaPrincipal(memoria);
        
        
        
        comboBoxCantBloques.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
        comboBoxTiempo.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
        comboBoxPrioridad.getItems().addAll("Alta","Baja");
        
        
        
        
        
        
        
        
        
        
    }    

    @FXML
    private void ejecutar(ActionEvent event) {
        
        try {
//            memoriaPrincipal.setProceso(proceso);
//            memoriaPrincipal.setProceso(proceso2);
        
        } catch (Exception e) {
        }

    }

    @FXML
    private void crearProceso(ActionEvent event) {
        try {
            if (listaProcesos.size() <19) {

                String id = tfIdentificador.getText();
                String prioridad = comboBoxPrioridad.getSelectionModel().getSelectedItem();
                int cantBloques = comboBoxCantBloques.getSelectionModel().getSelectedItem();
                int tiempo = comboBoxTiempo.getSelectionModel().getSelectedItem();

                Proceso proceso = new Proceso();

                proceso.setIdentificador(id);
                proceso.setPrioridad(prioridad);
                proceso.setCantBloques(cantBloques);
                proceso.setTiempo(tiempo);
                
                listaProcesos.add(proceso);
                
                actualizarGrid();
 
            }
            else{
                System.out.println("Limite de procesos superado.");
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
 
    }
    
    public void actualizarGrid(){
        indexProcesosGrid=0;
        procesos.getChildren().clear();
        for (Proceso proceso: listaProcesos) {
           
            procesos.add(proceso.getItem(), 0, indexProcesosGrid);
            indexProcesosGrid++;

        }
    
       
    }
    
}
