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
        if (tokenEnError.getNombre().equals("EOF"))
            return "Error sintáctico en línea " + tokenEnError.getLinea() + ": se llego al final del archivo y se esperaba encontrar " + lexemasEsperados;
        else
            return "Error sintáctico en línea " + tokenEnError.getLinea() + ": se encontró el lexema " + tokenEnError.getLexema() + " (Token: " + tokenEnError.getNombre() + ") cuando se esperaba un " + lexemasEsperados;
    }

    public String getCodigoError() {
        return "[Error:" + tokenEnError.getLexema() + "|" + tokenEnError.getLinea() + "]";
    }
}
