package main.analizadorSintactico.excepciones;

import main.analizadorLexico.Token;

public class ExcepcionSintactica extends Exception {

    private Token tokenEnError;
    private String lexemasEsperados;

    public ExcepcionSintactica(Token tokenEnError, String lexemasEsperados) {
        this.tokenEnError = tokenEnError;
        this.lexemasEsperados = lexemasEsperados;
    }

    public String getMensajeError() {
        return "Error sintáctico en línea " + tokenEnError.getLinea() + ": se encontró " + tokenEnError.getNombre() + " cuando se esperaba un " + lexemasEsperados;
    }
}
