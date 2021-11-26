package main.analizadorSemantico.ast.encadenados;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

public abstract class NodoEncadenado {

    protected Token tokenDeDatos;
    protected NodoEncadenado nodoEncadenado;
    protected boolean esLadoIzqAsignacion = false;

    public void setEncadenado(NodoEncadenado nodoEncadenado){
        this.nodoEncadenado = nodoEncadenado;
    }

    public abstract Tipo chequear(Tipo tipo) throws ExcepcionSemantica;

    public abstract boolean esLlamada();

    public abstract boolean esVariable();

    public abstract void generarCodigo();

    public void setEsLadoIzqAsignacion(boolean value){
        esLadoIzqAsignacion = value;
    }
}
