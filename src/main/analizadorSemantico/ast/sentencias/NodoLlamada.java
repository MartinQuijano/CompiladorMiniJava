package main.analizadorSemantico.ast.sentencias;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.expresiones.operandos.accesos.NodoAcceso;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;

public class NodoLlamada extends NodoSentencia {
    private NodoAcceso nodoAcceso;

    public NodoLlamada(Token tokenDeDatos, NodoAcceso nodoAcceso){
        this.tokenDeDatos = tokenDeDatos;
        this.nodoAcceso = nodoAcceso;
    }

    public void chequear() throws ExcepcionSemantica {
        nodoAcceso.chequear();
        if(!nodoAcceso.esLlamada())
            throw new ExcepcionSemantica(tokenDeDatos, "El acceso debe ser una llamada.");
    }

    public void imprimir() {
        nodoAcceso.imprimir();
    }
}
