/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codificacionhuffman;

/**
 *
 * @author Alejo
 */
public class objetoHuff implements Comparable<objetoHuff>{
    String carater;
    Integer cantidad;
    Float probabilidad;
    String codificacion;
    Integer nivel;

    public objetoHuff(String carater, Integer cantidad, Float probabilidad, String codificacion, Integer nivel) {
        this.carater = carater;
        this.cantidad = cantidad;
        this.probabilidad = probabilidad;
        this.codificacion = codificacion;
        this.nivel = nivel;
    }   

    public String getCarater() {
        return carater;
    }

    public void setCarater(String carater) {
        this.carater = carater;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Float getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(Float probabilidad) {
        this.probabilidad = probabilidad;
    }

    public String getCodificacion() {
        return codificacion;
    }

    public void setCodificacion(String codificacion) {
        this.codificacion = codificacion;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }
    
    @Override
    public int compareTo(objetoHuff o) {
        if (probabilidad < o.probabilidad) {
            return -1;
        }
        if (probabilidad > o.probabilidad) {
            return 1;
        }
        return 0;
    }
}
