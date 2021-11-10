package main.analizadorSemantico.ast.expresiones.operandos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.expresiones.NodoExpresion;

public abstract class NodoOperando extends NodoExpresion {
    protected Token tokenDeDatos;

    public void imprimir() {
        System.out.println(tokenDeDatos.getLexema());
    }

    public Token getTokenDeDatos(){
        return tokenDeDatos;
    }
}
