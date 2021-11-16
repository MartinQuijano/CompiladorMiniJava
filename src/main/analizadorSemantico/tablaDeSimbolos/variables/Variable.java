package main.analizadorSemantico.tablaDeSimbolos.variables;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

public abstract class Variable {

    protected Token tokenDeDatos;
    protected Tipo tipo;

    protected int offset;
    protected boolean seLeAsignoOffset;

    public Token getTokenDeDatos(){
        return tokenDeDatos;
    }

    public Tipo getTipo(){
        return tipo;
    }

    public abstract boolean esVisibile();

    public void setOffset(int offset){
        this.offset = offset;
    }

    public int getOffset(){
        return offset;
    }

    public void setSeLeAsignoOffset(boolean value){
        seLeAsignoOffset = value;
    }

    public boolean seLeAsignoOffset(){
        return seLeAsignoOffset;
    }
}
