package main.analizadorSemantico.excepciones;

import main.analizadorLexico.Token;

public class ExcepcionSemantica extends Exception {

    private Token tokenEnError;
    private String mensajeDeError;

    public ExcepcionSemantica(Token tokenEnError, String mensajeDeError) {
        this.tokenEnError = tokenEnError;
        this.mensajeDeError = mensajeDeError;
    }

    public String getMensajeError() {
        return "Error semántico en linea " + tokenEnError.getLinea() + " : " + mensajeDeError;
    }

    public String getCodigoError() {
        return "[Error:" + tokenEnError.getLexema() + "|" + tokenEnError.getLinea() + "]";
    }
}
