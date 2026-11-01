

package com.ejemplo.gimnasio.model;

public enum Especie {
    PERRO("Perro"),
    GATO("Gato"),
    AVE("Ave"),
    CONEJO("Conejo"),
    OTRO("Otro"); 

    private final String etiqueta;

    Especie(String etiqueta){
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta(){
        return etiqueta;
    }
} 
 
