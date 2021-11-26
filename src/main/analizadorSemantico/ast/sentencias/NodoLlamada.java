package main.analizadorSemantico.ast.sentencias;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.expresiones.operandos.accesos.NodoAcceso;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

public class NodoLlamada extends NodoSentencia {
    private NodoAcceso nodoAcceso;
    private Tipo tipoDeRetorno;

    public NodoLlamada(Token tokenDeDatos, NodoAcceso nodoAcceso) {
        this.tokenDeDatos = tokenDeDatos;
        this.nodoAcceso = nodoAcceso;
    }

    public void chequear() throws ExcepcionSemantica {
        tipoDeRetorno = nodoAcceso.chequear();
        if (!nodoAcceso.esLlamada())
            throw new ExcepcionSemantica(tokenDeDatos, "El acceso debe ser una llamada.");
    }

    public void generarCodigo() {
        nodoAcceso.generarCodigo();
        if (!tipoDeRetorno.getTokenDeDatos().getLexema().equals("void"))
            TablaDeSimbolos.insertarInstruccion("POP        ; descarto el valor del tope de la pila que no es requerido porque la unidad no devuelve nada");
    }

}
