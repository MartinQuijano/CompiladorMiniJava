package main.analizadorSemantico.tablaDeSimbolos.variables;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

public abstract class Variable {

    protected Token tokenDeDatos;
    protected Tipo tipo;

    public Token getTokenDeDatos(){
        return tokenDeDatos;
    }

    public Tipo getTipo(){
        return tipo;
    }

    public abstract boolean esVisibile();
}
