package main.manejadorDeArchivos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ProcesadorDeArchivo {

    private BufferedReader bufferedReader;
    private int nroLinea;
    private int nroColumna;
    private String lineaActual;

    private static char EOF = (char) -1;
    private static char CR = (char) 13;
    private static char EOL = (char) 10;

    public ProcesadorDeArchivo(String filepath) {
        setupFileReader(filepath);
        nroLinea = 1;
        nroColumna = 0;
    }

    private void setupFileReader(String filepath){
        try {
            bufferedReader = new BufferedReader(new FileReader(filepath));
            guardarLineaActual();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void guardarLineaActual(){
        try {
            bufferedReader.mark(128);
            lineaActual = bufferedReader.readLine();
            bufferedReader.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void finalizarUso(){
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public char proximoCaracter() {
        int c = -1;
        try {
            if((c = bufferedReader.read()) != -1) {
                nroColumna++;
                if(c == 10) {
                    nroLinea++;
                    guardarLineaActual();
                    nroColumna=0;
                }
                return (char) c;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (char) c;
    }

    public boolean esEOF(char caracter){
        if(caracter == EOF){
            return true;
        }
        return false;
    }

    public boolean esEOL(char caracter){
        if(caracter == EOL){
            return true;
        }
        return false;
    }

    public boolean esCR(char caracter){
        if(caracter == CR){
            return true;
        }
        return false;
    }

    public int getNroLinea(){
        return nroLinea;
    }

    public int getNroColumna(){
        return nroColumna;
    }

    public String getLineaActual(){
        return lineaActual;
    }
}
