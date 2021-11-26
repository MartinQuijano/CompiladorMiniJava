package main.analizadorSemantico.ast;

import main.analizadorSemantico.ast.expresiones.operandos.accesos.NodoAcceso;
import main.analizadorSemantico.ast.sentencias.NodoSentencia;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoEntero;

public class NodoAsignacion extends NodoSentencia {
    private NodoAcceso nodoAcceso;
    private NodoTipoAsignacion nodoTipoAsignacion;

    public NodoAsignacion(NodoAcceso nodoAcceso, NodoTipoAsignacion nodoTipoAsignacion) {
        this.nodoAcceso = nodoAcceso;
        this.nodoTipoAsignacion = nodoTipoAsignacion;
    }

    public void chequear() throws ExcepcionSemantica {
        Tipo tipoAcceso = nodoAcceso.chequear();
        nodoAcceso.setEsLadoIzqAsignacion(true);
        if (!nodoAcceso.esVariable()) {
            throw new ExcepcionSemantica(nodoTipoAsignacion.getSimbolo(), "El lado izquierdo de la asignacion debe ser asignable. Debe ser una variable o un encadenado que termina en una variable.");
        }
        if (nodoTipoAsignacion.getSimbolo().getLexema().equals("=")) {
            Tipo tipoExpresion = nodoTipoAsignacion.getExpresion().chequear();
            if (!tipoExpresion.conforma(tipoAcceso))
                throw new ExcepcionSemantica(nodoTipoAsignacion.getSimbolo(), "Los tipos de la asignacion no conforman.");
        } else if (nodoTipoAsignacion.getSimbolo().getLexema().equals("++") || nodoTipoAsignacion.getSimbolo().getLexema().equals("--")) {
            if(!tipoAcceso.conforma(new TipoEntero())){
                throw new ExcepcionSemantica(nodoTipoAsignacion.getSimbolo(), "El acceso debe ser de tipo entero y se encontro un tipo " + tipoAcceso.getTokenDeDatos().getLexema() + ".");
            }
        }
    }

    public void generarCodigo() {
        if (nodoTipoAsignacion.getSimbolo().getLexema().equals("++")) {
            nodoAcceso.setEsLadoIzqAsignacion(false);
            nodoAcceso.generarCodigo();
            TablaDeSimbolos.insertarInstruccion("PUSH 1       ; agrego un 1 en el tope de la pila para hacer el incremento");
            TablaDeSimbolos.insertarInstruccion("ADD        ; se hace el incremento de la variable en 1 (++)");
        } else if (nodoTipoAsignacion.getSimbolo().getLexema().equals("--")) {
            nodoAcceso.setEsLadoIzqAsignacion(false);
            nodoAcceso.generarCodigo();
            TablaDeSimbolos.insertarInstruccion("PUSH 1       ; agrego un 1 en el tope de la pila para hacer el decremento");
            TablaDeSimbolos.insertarInstruccion("SUB        ; se hace el decremento de la variable en 1 (--)");
        } else {
            nodoTipoAsignacion.getExpresion().generarCodigo();
        }
        nodoAcceso.setEsLadoIzqAsignacion(true);
        nodoAcceso.generarCodigo();
    }

}
