package main.analizadorSemantico.ast;

import main.analizadorSemantico.ast.expresiones.operandos.accesos.NodoAcceso;
import main.analizadorSemantico.ast.sentencias.NodoSentencia;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoEntero;

public class NodoAsignacion extends NodoSentencia {
    private NodoAcceso nodoAcceso;
    private NodoTipoAsignacion nodoTipoAsignacion;

    public NodoAsignacion(NodoAcceso nodoAcceso, NodoTipoAsignacion nodoTipoAsignacion){
        this.nodoAcceso = nodoAcceso;
        this.nodoTipoAsignacion = nodoTipoAsignacion;
    }

    public void chequear() throws ExcepcionSemantica {
        Tipo tipoAcceso = nodoAcceso.chequear();
        nodoAcceso.setEsLadoIzqAsignacion(true);
        if(!nodoAcceso.esVariable()){
            throw new ExcepcionSemantica(nodoTipoAsignacion.getSimbolo(), "El lado izquierdo de la asignacion debe ser asignable. Debe ser una variable o un encadenado que termina en una variable.");
        }
        if(nodoTipoAsignacion.getSimbolo().getLexema().equals("=")){
            Tipo tipoExpresion = nodoTipoAsignacion.getExpresion().chequear();
            if(!tipoExpresion.conforma(tipoAcceso))
                throw new ExcepcionSemantica(nodoTipoAsignacion.getSimbolo(), "Los tipos de la asignacion no conforman.");
        } else if(nodoTipoAsignacion.getSimbolo().getLexema().equals("++") || nodoTipoAsignacion.getSimbolo().getLexema().equals("--")){
            tipoAcceso.conforma(new TipoEntero());
        }
    }

    public void generarCodigo() {
        nodoTipoAsignacion.getExpresion().generarCodigo();
        nodoAcceso.generarCodigo();
    }

    public void imprimir() {
        nodoAcceso.imprimir();
        nodoTipoAsignacion.imprimir();
    }
}
