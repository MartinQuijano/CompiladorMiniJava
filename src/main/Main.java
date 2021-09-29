package main;

import main.analizadorLexico.AnalizadorLexico;
import main.analizadorLexico.Token;
import main.analizadorLexico.excepciones.ExcepcionLexica;
import main.analizadorSintactico.AnalizadorSintactico;
import main.analizadorSintactico.excepciones.ExcepcionSintactica;
import main.manejadorDeArchivos.ProcesadorDeArchivo;

public class Main {

    private static void print_token(Token token) {
        System.out.print("(" + token.getNombre());
        System.out.print(", " + token.getLexema());
        System.out.println(", " + token.getLinea() + ")");
    }

    public static void main(String args[]) {

        ProcesadorDeArchivo procesadorDeArchivo = new ProcesadorDeArchivo(args[0]);
        //ProcesadorDeArchivo procesadorDeArchivo = new ProcesadorDeArchivo("C:\\Users\\Kottler\\Desktop\\Compiladores e Interpretes\\AnalizadorLexico\\resources\\test.java");
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(procesadorDeArchivo);

        try {
            AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(analizadorLexico);
            System.out.println("Compilación exitosa");
            System.out.println();
            System.out.println("[SinErrores]");
        } catch (ExcepcionLexica e) {
            System.out.println();
            System.out.println(e.getMensajeError());
            System.out.println(e.getDetalleErrorElegante());
            System.out.println(e.getMarcadorDeError());
            System.out.println(e.getCodigoError());
            System.out.println();
        } catch (ExcepcionSintactica e) {
            System.out.println(e.getMensajeError());
            System.out.println(e.getCodigoError());
        }

    }
}
