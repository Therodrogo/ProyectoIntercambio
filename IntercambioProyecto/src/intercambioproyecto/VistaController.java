/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intercambioproyecto;

import java.awt.Panel;
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
    private ArrayList<Proceso> listaMemoria = new ArrayList();
    private ArrayList<Proceso> listaDisco = new ArrayList();
    
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
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
            //Extreamos el primer elemento de nuestra cola
            if (listaMemoria.size()<13 && listaMemoria.size()+listaProcesos.get(0).getCantBloques()<13) {
                
                avazarTiempoGrid();
                
                
                
                Proceso primerProceso = listaProcesos.remove(0);

                System.out.println(primerProceso.getIdentificador());
                
                //Se agrean la cantidad conrrespondientes de bloques del proceso en la memoria
                for (int i = 0; i < primerProceso.getCantBloques(); i++) {
                    Proceso aux = new Proceso();
                    aux.setIdentificador(primerProceso.getIdentificador());
                    aux.setCantBloques(primerProceso.getCantBloques());
                    aux.setTiempo(primerProceso.getTiempo());
                    aux.setPrioridad(primerProceso.getPrioridad());
                    
                    listaMemoria.add(aux);
                    
                }
                //Se actualiza el grid de procesos
                actualizarGrid(procesos,listaProcesos);
                //Se actualiza el grid de memoria
                actualizarGrid(memoria,listaMemoria);
            }
            else{
                avazarTiempoGrid();
                
                System.out.println("Limete de memoria superado");
            }
            
        
        } catch (Exception e) {
 
            avazarTiempoGrid();

            
        }

    }
    public void avazarTiempoGrid(){
        if (listaMemoria.size()>=0) {

                ArrayList<Proceso> borrar = new ArrayList();

                for (Proceso proceso: listaMemoria) {
                    if (proceso.getTiempo()==1) {
                        borrar.add(proceso);
                    }
                }

                for (int i = 0; i < borrar.size(); i++) {
                    listaMemoria.remove(borrar.get(i));
                }

                for (Proceso proceso: listaMemoria) {
                    proceso.avanzarTiempo();
                }
                actualizarGrid(memoria,listaMemoria);
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
                
                actualizarGrid(procesos,listaProcesos);
 
            }
            else{
                System.out.println("Limite de procesos superado.");
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
 
    }
    
    public void actualizarGrid(GridPane gridPane, ArrayList<Proceso> listaProcesos){
        int index = 0;
        gridPane.setGridLinesVisible(true);
        gridPane.getChildren().clear();
        
        for (Proceso proceso: listaProcesos) {
           
            gridPane.add(proceso.getItem(), 0, index);
            
            index++;

        }

    }
    
}
