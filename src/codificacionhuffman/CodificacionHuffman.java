/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codificacionhuffman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Alejo
 */
public class CodificacionHuffman {
    public static int tamanioArreglo = 0;
    public static float entropia = 0;
    public static ArrayList<Float> probabilidadesCaracteresOrd = new ArrayList<>();
    public static ArrayList<String> caracteresUnicosOrd = new ArrayList<>();
    public static objetoHuff[] listaObjetosHuff;
    public static ArrayList<String> caracteresUnicos = new ArrayList<>();
    public static ArrayList<String> caracteresDoc = new ArrayList<>();
    public static ArrayList<Integer> cantidadCaracteres = new ArrayList<>();
    public static ArrayList<Float> probabilidadesCaracteres = new ArrayList<>();
    public static float [][] probabilidades;
    public static int [][] codificaciones;
    public static int filas, columnas;
        
    public static void leerArchivo() throws FileNotFoundException, IOException{
        String cadena;
        File file = new File("/home/alejo/Escritorio/caracteres.txt");
        FileReader f = new FileReader(file.getAbsoluteFile());
        try (BufferedReader b = new BufferedReader(f)) {
            while((cadena = b.readLine())!=null) {
                String[] caracteres = cadena.split("");
                Set<String> hs = new HashSet<>(Arrays.asList(caracteres));
                hs.addAll(caracteresUnicos);
                caracteresUnicos.clear();
                caracteresUnicos.addAll(hs);
                caracteresDoc.addAll(Arrays.asList(caracteres));
            }
        }        
    }
    
    public static void obtenerValoreUnicos(){
        for (int i = 0; i < caracteresUnicos.size(); i++) {
            cantidadCaracteres.add(i, 0);
        }
        for(int i = 0; i < caracteresDoc.size(); i++){
            for (int j = 0; j < caracteresUnicos.size(); j++) {
                if (caracteresDoc.get(i).equals(caracteresUnicos.get(j))) {
                    cantidadCaracteres.set(j, cantidadCaracteres.get(j) + 1);
                }
            }
        }
        tamanioArreglo = caracteresUnicos.size();
    }
    
    public static void calcularPromedios(){
        for (int i = 0; i < cantidadCaracteres.size(); i++) {
            float cantidad = cantidadCaracteres.get(i);
            float tamanio = caracteresDoc.size();
            float operacion = cantidad / tamanio;
            probabilidadesCaracteres.add(operacion);
        }
    }
    
    public static void calcularEntropia(){
        for (int i = 0; i < probabilidadesCaracteres.size(); i++) {
            float prob = (float) Math.log(1 / probabilidadesCaracteres.get(i));
            float log2 = (float) Math.log(2);
            entropia += probabilidadesCaracteres.get(i) * (prob / log2);
        }
        
        System.out.println("Entropía: " + entropia);
    }
    
    public static void imprimirResultado(){
        for (int i = 0; i < caracteresUnicos.size(); i++) {
            System.out.println("Caracter: " + caracteresUnicos.get(i) + " - cantidad: " + cantidadCaracteres.get(i) + " - proba: " 
                    + probabilidadesCaracteres.get(i));
        }
    }
    
    public static void imprimirArreglo(objetoHuff[] obj){
        for (int i = obj.length-1; i >= 0; i--) {
            System.out.println("Caracter: " + obj[i].getCarater() + " - cantidad: " + obj[i].getCantidad() + " - proba: " 
                    + obj[i].getProbabilidad() + " - codi: " + obj[i].getCodificacion() + " - nivel: " + obj[i].getNivel());
        }
    }
    
    public static void imprimirProbabilidades(){
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.println("Fila : " + i + " Columna: " + j + " Valor: " + probabilidades[i][j]);
            }
        }
    }
    
    public static void llenarListaObjetos(){
        listaObjetosHuff = new objetoHuff[tamanioArreglo];
        System.out.println("Tam CU: " + caracteresUnicos.size() + " - tam lista: " + tamanioArreglo);
        for (int i = 0; i < caracteresUnicos.size(); i++) {
            listaObjetosHuff[i] = new objetoHuff(caracteresUnicos.get(i), cantidadCaracteres.get(i), probabilidadesCaracteres.get(i), "", 0);
        }
        Arrays.sort(listaObjetosHuff);
        filas = listaObjetosHuff.length-1;
        columnas = filas-1;
        probabilidades = new float[filas][columnas];
        for (int i = listaObjetosHuff.length-1; i >= 1; i--) {
            for (int j = 0; j < listaObjetosHuff.length-1; j++) {
                probabilidades[j][0] = listaObjetosHuff[i].getProbabilidad();
            }
        }
    }
    
    public static objetoHuff[] codificacionHuffman(objetoHuff[] obj, int nivel){
        objetoHuff[] aux = new objetoHuff[obj.length-1];
        if (obj.length == 2) {
            obj[1].setCodificacion("0");
            obj[0].setCodificacion("1");
            return obj;
        } else {
            for (int i = obj.length-1; i >= 1; i--) {
                for (int j = 0; j < obj.length-1; j++) {
                    probabilidades[j][nivel] = obj[i].getProbabilidad();
                }
            }
            for (int i = obj.length-1; i >= 1; i--) {
                if (i == 1) {
                    float prob = obj[i].getProbabilidad() + obj[i-1].getProbabilidad();
                    aux[i-1] = new objetoHuff(obj[i].getCarater(), obj[i].getCantidad(), prob, "", nivel);
                } else {
                    aux[i-1] = new objetoHuff(obj[i].getCarater(), obj[i].getCantidad(), obj[i].getProbabilidad(), "", -1);
                }
            }
            Arrays.sort(aux);
            nivel += 1;
            aux = codificacionHuffman(aux, nivel);
            int ite = obj.length-1;
            for (int i = aux.length-1; i >= 0; i--) {
                if (aux[i].getNivel() != -1) {
                    obj[0].setCodificacion(aux[i].getCodificacion() + "0");
                    obj[1].setCodificacion(aux[i].getCodificacion() + "1");
                } else {
                    obj[ite].setCodificacion(aux[i].getCodificacion());
                    ite--;
                }
            }
        }
        return obj;
    }
            
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        leerArchivo();
        obtenerValoreUnicos();
        calcularPromedios();
        calcularEntropia();
        llenarListaObjetos();
//        imprimirResultado();
//        imprimirArreglo(listaObjetosHuff);
        objetoHuff[] carajo = codificacionHuffman(listaObjetosHuff, 0);
        imprimirArreglo(carajo);
        imprimirProbabilidades();
    }    
}