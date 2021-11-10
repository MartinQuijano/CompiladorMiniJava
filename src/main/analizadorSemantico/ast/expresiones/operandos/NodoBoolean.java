package main.analizadorSemantico.ast.expresiones.operandos;

import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

public abstract class NodoBoolean extends NodoOperando {

    public Tipo chequear(){
        return tipo;
    }
}
