package main.analizadorSemantico.ast.sentencias;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.expresiones.operandos.accesos.NodoAcceso;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

public class NodoLlamada extends NodoSentencia {
    private NodoAcceso nodoAcceso;

    //todo de abajo
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
        //TODO: ojo en encadenado que tiene que ser el ultimo el que tenga valor de retorno! Se podria guardar cuando hago el chequear mas arriba, guardo el tipo y ahi determino!
        //if(nodoAcceso.tieneValorDeRetorno())
        //    TablaDeSimbolos.insertarInstruccion("POP        ; descarto el valor del tope de la pila que no es requerido");

        //conformando el todo de arriba
        if (!tipoDeRetorno.getTokenDeDatos().getLexema().equals("void"))
            TablaDeSimbolos.insertarInstruccion("POP        ; descarto el valor del tope de la pila que no es requerido");
    }

    public void imprimir() {
        nodoAcceso.imprimir();
    }
}
