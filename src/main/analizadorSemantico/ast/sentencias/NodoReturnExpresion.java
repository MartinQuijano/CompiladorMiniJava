package main.analizadorSemantico.ast.sentencias;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.expresiones.NodoExpresion;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

public class NodoReturnExpresion extends NodoReturn{

    protected NodoExpresion expresion;

    public NodoReturnExpresion(Token tokenDeDatos, NodoExpresion nodoExpresion){
        super(tokenDeDatos);
        this.expresion = nodoExpresion;
    }

    public void chequear() throws ExcepcionSemantica {
        Tipo tipoExpresion = expresion.chequear();
        Tipo tipoRetornoMetodo = TablaDeSimbolos.getUnidadActual().getTipo();
        if(!tipoExpresion.conforma(tipoRetornoMetodo))
            throw new ExcepcionSemantica(tokenDeDatos, "El tipo de retorno no conforma con el tipo de retorno del metodo");
    }

    public void imprimir(){
        System.out.print("return ");
        expresion.imprimir();
    }
}
