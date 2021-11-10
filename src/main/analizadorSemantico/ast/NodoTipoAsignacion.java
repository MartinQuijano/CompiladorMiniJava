package main.analizadorSemantico.ast;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.expresiones.NodoExpresion;

public class NodoTipoAsignacion{

    private Token simbolo;
    private NodoExpresion expresion;

    public NodoTipoAsignacion(Token tokenDeDatos){
        this.simbolo = tokenDeDatos;
    }

    public void setExpresion(NodoExpresion nodoExpresion){
        this.expresion = nodoExpresion;
    }

    public Token getSimbolo(){
        return simbolo;
    }

    public NodoExpresion getExpresion(){
        return expresion;
    }

    public void imprimir(){
        System.out.print(" " + simbolo.getLexema() + " ");
        expresion.imprimir();
    }
}
