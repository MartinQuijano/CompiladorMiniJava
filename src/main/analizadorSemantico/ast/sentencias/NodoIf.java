package main.analizadorSemantico.ast.sentencias;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.expresiones.NodoExpresion;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

public class NodoIf extends NodoSentencia {

    protected NodoSentencia sentenciaThen;
    protected NodoExpresion condicion;

    public NodoIf(Token tokenDeDatos, NodoExpresion nodoExpresion, NodoSentencia nodoSentencia){
        this.tokenDeDatos = tokenDeDatos;
        this.condicion = nodoExpresion;
        this.sentenciaThen = nodoSentencia;
    }

    public void chequear() throws ExcepcionSemantica {
        if(!esCondicionBoolean())
            throw new ExcepcionSemantica(tokenDeDatos, "La expresion de condicion debe ser de tipo boolean");
        else
            sentenciaThen.chequear();
    }

    public void generarCodigo() {
        //TODO
    }

    private boolean esCondicionBoolean() throws ExcepcionSemantica {
        Tipo tipoCondicion = condicion.chequear();
        if(tipoCondicion.getTokenDeDatos().getLexema().equals("boolean"))
            return true;
        else return false;
    }

    public void imprimir() {
        System.out.print("if(");
        condicion.imprimir();
        System.out.print(")");
        sentenciaThen.imprimir();
    }
}
