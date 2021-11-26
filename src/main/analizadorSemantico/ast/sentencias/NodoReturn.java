package main.analizadorSemantico.ast.sentencias;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;

import java.util.Stack;

public class NodoReturn extends NodoSentencia {

    public NodoReturn(Token tokenDeDatos){
        this.tokenDeDatos = tokenDeDatos;
    }

    public void chequear() throws ExcepcionSemantica {
        if(!TablaDeSimbolos.getUnidadActual().getTipo().getTokenDeDatos().getLexema().equals("void"))
            throw new ExcepcionSemantica(tokenDeDatos, "El tipo de retorno del metodo debe ser void.");
    }

    public void generarCodigo() {
        TablaDeSimbolos.insertarInstruccion("FMEM " + TablaDeSimbolos.getUnidadActual().getMemoriaReservada() + "        ; libero la memoria reservada por la unidad hasta el momento");
        TablaDeSimbolos.insertarInstruccion("STOREFP        ; restauro la posicion correspondiente del fp que es la apuntada por el enlace");

        if (TablaDeSimbolos.getUnidadActual().esDinamica())
            TablaDeSimbolos.insertarInstruccion("RET " + (TablaDeSimbolos.getUnidadActual().getParametros().size() + 1) + "        ; retorno de la unidad y libero memoria correspondiente a la cantidad de parametros + 1 por el this al ser una unidad dinamica");
        else
            TablaDeSimbolos.insertarInstruccion("RET " + TablaDeSimbolos.getUnidadActual().getParametros().size() + "        ; retorno de la unidad y libero memoria correspondiente a la cantidad de parametros");
    }

    public void setTokenDeDatos(Token tokenDeDatos){
        this.tokenDeDatos = tokenDeDatos;
    }
}
