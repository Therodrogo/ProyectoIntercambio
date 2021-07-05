/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intercambioproyecto;

import javafx.scene.control.Label;

/**
 *
 * @author Rodrigo - Sabastian
 */


public class Proceso {
    private int numeroPantalla; 

    public int getNumeroPantalla() {
        return numeroPantalla;
    }

    public void setNumeroPantalla(int numeroPantalla) {
        this.numeroPantalla = numeroPantalla;
    }
    private String identificador;
    private String prioridad;
    
    private int cantBloques;
    private int tiempo;
    private Label item = new Label();

    public Proceso(){
        item.setPrefWidth(255);
    }
    
    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        
        if (prioridad=="Baja") {
            
            item.setStyle("-fx-background-color: #DAFA9E;");
            
        }
        else{
            item.setStyle("-fx-background-color: #FAA09B;");
        }
        this.prioridad = prioridad;
    }

    public int getCantBloques() {
        return cantBloques;
    }

    public void setCantBloques(int cantBloques) {
        this.cantBloques = cantBloques;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
    
    public void avanzarTiempo(){
        tiempo--;
    }
   
    public Label getItem(){
        
        item.setText(numeroPantalla+". "+"ID: "+identificador+" Tiempo: "+tiempo+" Bloques: "+cantBloques);
        
        return item;
    }
    
}
