package main.analizadorSemantico.ast.expresiones;

import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

public abstract class NodoExpresion {

    protected Tipo tipo;

    public abstract Tipo chequear() throws ExcepcionSemantica;

    public abstract void imprimir();
}
