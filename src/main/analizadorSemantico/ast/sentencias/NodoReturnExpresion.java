package main.analizadorSemantico.ast.sentencias;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.expresiones.NodoExpresion;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.unidades.Unidad;

import java.util.Stack;

public class NodoReturnExpresion extends NodoReturn{

    protected NodoExpresion expresion;
    protected Unidad entradaUnidad;

    public NodoReturnExpresion(Token tokenDeDatos, NodoExpresion nodoExpresion){
        super(tokenDeDatos);
        this.expresion = nodoExpresion;
    }

    public void chequear() throws ExcepcionSemantica {
        Tipo tipoExpresion = expresion.chequear();
        Tipo tipoRetornoMetodo = TablaDeSimbolos.getUnidadActual().getTipo();
        entradaUnidad = TablaDeSimbolos.getUnidadActual();
        if(!tipoExpresion.conforma(tipoRetornoMetodo))
            throw new ExcepcionSemantica(tokenDeDatos, "El tipo de retorno no conforma con el tipo de retorno del metodo");
    }

    public void generarCodigo(){
        expresion.generarCodigo();
        TablaDeSimbolos.insertarInstruccion("STORE " + entradaUnidad.getOffsetDeLugarDeRetorno() + "        ; guardo el valor del tope de la pila en el lugar del valor de retorno");
        TablaDeSimbolos.insertarInstruccion("FMEM " + TablaDeSimbolos.getUnidadActual().getMemoriaReservada());
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        //TODO: refactor en servicio de unidad.
        if (TablaDeSimbolos.getUnidadActual().esDinamica())
            TablaDeSimbolos.insertarInstruccion("RET " + (TablaDeSimbolos.getUnidadActual().getParametros().size() + 1));
        else
            TablaDeSimbolos.insertarInstruccion("RET " + TablaDeSimbolos.getUnidadActual().getParametros().size());
    }

    public void imprimir(){
        System.out.print("return ");
        expresion.imprimir();
    }
}
