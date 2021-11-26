package main.analizadorSemantico.ast.sentencias;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.expresiones.NodoExpresion;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

public class NodoIf extends NodoSentencia {

    protected NodoBloque bloqueThen;
    protected NodoExpresion condicion;

    public NodoIf(Token tokenDeDatos, NodoExpresion nodoExpresion, NodoBloque bloqueThen){
        this.tokenDeDatos = tokenDeDatos;
        this.condicion = nodoExpresion;
        this.bloqueThen = bloqueThen;
    }

    public void chequear() throws ExcepcionSemantica {
        if(!esCondicionBoolean())
            throw new ExcepcionSemantica(tokenDeDatos, "La expresion de condicion debe ser de tipo boolean");
        else
            bloqueThen.chequear();
    }

    public void generarCodigo() {
        condicion.generarCodigo();
        TablaDeSimbolos.getUnidadActual().incrementarIfCounter();
        String etiquetaFinIf = "finif_" + TablaDeSimbolos.getUnidadActual().getTokenDeDatos().getLexema() + "_" + TablaDeSimbolos.getUnidadActual().getIfCounter();
        TablaDeSimbolos.insertarInstruccion("BF " + etiquetaFinIf + "        ; agrego el salto condicional con la etiqueta de fin del if");
        bloqueThen.generarCodigo();
        TablaDeSimbolos.insertarInstruccion(etiquetaFinIf + ": NOP        ; agrego la etiqueta donde termina el fin.");
    }

    private boolean esCondicionBoolean() throws ExcepcionSemantica {
        Tipo tipoCondicion = condicion.chequear();
        if(tipoCondicion.getTokenDeDatos().getLexema().equals("boolean"))
            return true;
        else return false;
    }

}
