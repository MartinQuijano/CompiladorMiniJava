package main.analizadorSemantico.ast.expresiones.operandos.accesos;

import main.analizadorSemantico.ast.encadenados.NodoEncadenado;

public abstract class NodoPrimario extends NodoAcceso{

    protected NodoEncadenado nodoEncadenado;

    public void setEncadenado(NodoEncadenado nodoEncadenado){
        this.nodoEncadenado = nodoEncadenado;
    }

}
