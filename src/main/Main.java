package main;

import main.analizadorLexico.AnalizadorLexico;
import main.analizadorLexico.Token;
import main.analizadorLexico.excepciones.ExcepcionLexica;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSintactico.AnalizadorSintactico;
import main.analizadorSintactico.excepciones.ExcepcionSintactica;
import main.manejadorDeArchivos.ProcesadorDeArchivo;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;

import java.io.File;

public class Main {

    public static void main(String args[]) {

        ProcesadorDeArchivo procesadorDeArchivo = new ProcesadorDeArchivo(args[0]);
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(procesadorDeArchivo);
        File archivoDeSalida = new File(args[1]);

        TablaDeSimbolos tablaDeSimbolos = TablaDeSimbolos.getInstance();

        try {
            TablaDeSimbolos.restablecer();
            AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(analizadorLexico);
            TablaDeSimbolos.chequearDeclaraciones();
            TablaDeSimbolos.consolidar();
            TablaDeSimbolos.chequearSentencias();
            TablaDeSimbolos.generarCodigo();
            TablaDeSimbolos.escribirCodigoEnArchivo(archivoDeSalida);
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
        }catch (ExcepcionSemantica e) {
            System.out.println(e.getMensajeError());
            System.out.println(e.getCodigoError());
        }

    }
}