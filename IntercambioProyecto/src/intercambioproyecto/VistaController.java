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
import javafx.scene.control.ComboBox;
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
    private GridPane disco = new GridPane();
    
    private ArrayList<Proceso> listaProcesos = new ArrayList();
    private ArrayList<Proceso> listaMemoria = new ArrayList();
    private ArrayList<Proceso> listaMemoriaUnica = new ArrayList();
    
    private ArrayList<Proceso> listaDisco = new ArrayList();
    
    
    private ArrayList<Proceso> nuevo;
    
    @FXML
    private Pane panelDisco;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        panelProcesos.getChildren().add(procesos);
        
        panelMemoria.getChildren().add(memoria);
        
        panelDisco.getChildren().add(disco);

        comboBoxCantBloques.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
        comboBoxTiempo.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
        comboBoxPrioridad.getItems().addAll("Alta","Baja");
        
        
    }    

    @FXML
    private void ejecutar(ActionEvent event) {
        
        try {
            Proceso validar;
            if (!listaProcesos.isEmpty()) {
                validar = listaProcesos.get(0);
            }
            else{
                validar=null;
            }
            
            if (validar==null || isPrioridadAlta(validar)==false  ) {
                
                if (listaDisco.size()>0) {
                    
                    if (listaMemoria.size()<13 && listaMemoria.size()+listaDisco.get(0).getCantBloques()<13) {
                        
                        swapingDiscoToMemoria();

                    }
                    else{
                        System.out.println("Limite de memoria");
                    }
                    
                }
                
            }
                //Extreamos el primer elemento de nuestra cola
            if (listaMemoria.size()<13 && listaMemoria.size()+listaProcesos.get(0).getCantBloques()<13) {

                avazarTiempoGrid();

                Proceso primerProceso = listaProcesos.remove(0);

                System.out.println(primerProceso.getIdentificador());

                listaMemoriaUnica.add(primerProceso);

                //Se agrean la cantidad conrrespondientes de bloques del proceso en la memoria
                for (int i = 0; i < primerProceso.getCantBloques(); i++) {
                    Proceso aux = new Proceso();
                    aux.setIdentificador(primerProceso.getIdentificador());
                    aux.setCantBloques(primerProceso.getCantBloques());
                    aux.setTiempo(primerProceso.getTiempo());
                    aux.setPrioridad(primerProceso.getPrioridad());

                    listaMemoria.add(aux);

                }

                soloPrioridadBaja();
                ordenarArraylistaPrioridadBaja();

                //Se actualiza el grid de procesos
                actualizarGrid(procesos,listaProcesos);
                //Se actualiza el grid de memoria
                actualizarGrid(memoria,listaMemoria);
            }
            else{

                System.out.println("Limite de memoria superado"); 
                //Preguntamos si el proceso que esta en la lista procesos es de prioridad alta
                swapingToDisco();

                avazarTiempoGrid();

            }

        
        } catch (Exception e) {
           
            avazarTiempoGrid();

            
        }

    }
    
    public boolean isPrioridadAlta(Proceso proceso){
        if (proceso.getPrioridad().equals("Alta")) {
            return true;
        }
        else{
            return false;
        }
        
    }
    public boolean isPrioridadBajaEnMemoria(){
        
        for (Proceso proceso: listaMemoriaUnica) {
            if (proceso.getPrioridad().equals("Baja")) {
                return true;
            }
        }
        return false;
    }
    
   
    public void soloPrioridadBaja(){
        nuevo = new ArrayList();

        for (Proceso proceso: listaMemoriaUnica) {
            
            if (proceso.getPrioridad().equals("Baja")) {
                nuevo.add(proceso);
            }
        }

        
    }
    
    public void ordenarArraylistaPrioridadBaja(){
        
        if (nuevo.size()>1) {
            ArrayList<Proceso> listaAux= new ArrayList();
            for (int i = 0; i < nuevo.size()-1; i++) {
                for (int j = i; j < nuevo.size(); j++) {
                    if(nuevo.get(i).getCantBloques()>nuevo.get(j).getCantBloques()){
                        
                        listaAux.add(nuevo.get(i)); 
                        nuevo.set(i, nuevo.get(j)); 
                        nuevo.set(j, listaAux.get(0));
                        listaAux = new ArrayList();
                    }
                }
            }
            for (int i = 0; i < nuevo.size(); i++) {
                System.out.print(nuevo.get(i).getCantBloques());

            } 

        }
    
    }
    
    public ArrayList<Proceso> verificarEspacio(Proceso primerProceso){
        
        ArrayList<Proceso> cambiarDisco = new ArrayList();
        
        //Caso1
        for (Proceso proceso: nuevo) {
            
            int total =  proceso.getCantBloques();
            
            if (primerProceso.getCantBloques()<=total) {
                cambiarDisco.add(proceso);
                return cambiarDisco;
            }
            
        }
        
        //Caso2
        int total2 = 0;
        for (Proceso proceso: nuevo) {
            
            total2 =  total2 + proceso.getCantBloques();
            
            if (primerProceso.getCantBloques()<=total2) {
                cambiarDisco.add(proceso);
                return cambiarDisco;
            }
            else{
                cambiarDisco.add(proceso);

            }
        }
        
        //Caso 3
//        cambiarDisco.add(nuevo.get(0));
//        return cambiarDisco;
     
        return cambiarDisco;
    }
    
    public void swapingToDisco(){
    
        Proceso primerProceso = listaProcesos.get(0);
        //listaMemoria.size()<13 && listaMemoria.size()+primerProceso.getCantBloques()<13
        if (isPrioridadAlta(primerProceso) ) {
            
            if (isPrioridadBajaEnMemoria()) {
                soloPrioridadBaja();
                ordenarArraylistaPrioridadBaja();
                ArrayList<Proceso> toDisco = verificarEspacio(primerProceso);
                
                if (toDisco!=null) {
                    
                    Proceso procesoCorrecto = listaProcesos.remove(0);
                    
                    for (Proceso proceso: toDisco) {
                        //Funcion para borrar 
                        borrarElementos(toDisco, listaMemoriaUnica);
                        borrarElementosMemoria(toDisco, listaMemoria);
                        
                        listaDisco.add(proceso);
                        
                    }
                    System.out.println("tamaño");
                    System.out.println(listaMemoria.size());
                    
                    listaMemoriaUnica.add(procesoCorrecto);
                    
                    //Se crea el proceso ingresado al grid
                    for (int i = 0; i < procesoCorrecto.getCantBloques(); i++) {
                        Proceso aux = new Proceso();
                        aux.setIdentificador(procesoCorrecto.getIdentificador());
                        aux.setCantBloques(procesoCorrecto.getCantBloques());
                        aux.setTiempo(procesoCorrecto.getTiempo());
                        aux.setPrioridad(procesoCorrecto.getPrioridad());

                        listaMemoria.add(aux);
                    }

                    actualizarGrid(disco, listaDisco);
                    actualizarGrid(memoria, listaMemoria);
                    actualizarGrid(procesos, listaProcesos);
       
                }
                
                

            }
            else{
                System.out.println("No hay prioridad baja en la memoria.");
            }
            
            
            
        }
        else{
            
            //cambiasos desde el disco a la memoria
            
            if (listaDisco.size()>0) {
                
                
                
                
            }
            else{
                System.out.println("No hay procesos en espera.");
            }
            
            
            System.out.println("No es prioridad alta.");
        }
    
    }
    
    
    public void swapingDiscoToMemoria(){
    
        Proceso primerProcesoDisco = listaDisco.remove(0);
        
        listaMemoriaUnica.add(primerProcesoDisco);
        
        //Se crea el proceso ingresado al grid
        for (int i = 0; i < primerProcesoDisco.getCantBloques(); i++) {
            Proceso aux = new Proceso();
            aux.setIdentificador(primerProcesoDisco.getIdentificador());
            aux.setCantBloques(primerProcesoDisco.getCantBloques());
            aux.setTiempo(primerProcesoDisco.getTiempo());
            aux.setPrioridad(primerProcesoDisco.getPrioridad());

            listaMemoria.add(aux);
        }
        
        actualizarGrid(disco, listaDisco);
        actualizarGrid(memoria, listaMemoria);
        
    }
    
    public void avazarTiempoGrid(){
        
        if (listaMemoria.size()>=0) {

                ArrayList<Proceso> borrar = new ArrayList();
                ArrayList<Proceso> borrarUnica = new ArrayList();
                
                //Limpiesa de grid
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
                
                //Limpiesa de datos
                for (Proceso proceso: listaMemoriaUnica) {
                    if (proceso.getTiempo()==1) {
                        borrarUnica.add(proceso);
                    }
                }
                for (int i = 0; i < borrarUnica.size(); i++) {
                    listaMemoriaUnica.remove(borrarUnica.get(i));
                }
                for (Proceso proceso: listaMemoriaUnica) {
                    proceso.avanzarTiempo();
                }
                
                
                actualizarGrid(memoria,listaMemoria);
            }
    }
    int contadorProceso=0;
    @FXML
    private void crearProceso(ActionEvent event) {
        try {
            if (listaProcesos.size() <19) {

                String id = "000"+contadorProceso;
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
                contadorProceso++;
 
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
    
    public void borrarElementos(ArrayList<Proceso> borrar,ArrayList<Proceso> lista2){
    
        for (int i = 0; i < borrar.size(); i++) {

            lista2.remove(borrar.get(i)); 
        }

    }
    
    public void borrarElementosMemoria(ArrayList<Proceso> borrar,ArrayList<Proceso> lista2){
        
        ArrayList<Proceso> listaBorrar = new ArrayList();
        
        for (int i = 0; i < borrar.size(); i++) {
            for (int j = 0; j < lista2.size(); j++) {
                
                if (borrar.get(i).getIdentificador().equals(lista2.get(j).getIdentificador())) {
                    
                    System.out.println(borrar.get(i).getIdentificador()+"--"+lista2.get(j).getIdentificador());
                    
                    listaBorrar.add(lista2.get(j));
                }
            }
            
        }
        
        for (int i = 0; i < listaBorrar.size(); i++) {
            
           lista2.remove(listaBorrar.get(i));
            
        }
        
    
    }
}
