package main.analizadorSemantico.ast.sentencias;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;

public abstract class NodoSentencia {

    protected Token tokenDeDatos;

    public abstract void chequear() throws ExcepcionSemantica;

    public abstract void generarCodigo();

    public abstract void imprimir();

}
