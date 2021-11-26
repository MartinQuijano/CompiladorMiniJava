package main.analizadorSemantico.ast.sentencias;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.NodoAsignacion;
import main.analizadorSemantico.ast.expresiones.NodoExpresion;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.variables.VarLocal;

public class NodoFor extends NodoSentencia {

    protected NodoVarLocal varLocal;
    protected NodoExpresion expresion;
    protected NodoAsignacion asignacion;
    protected NodoBloque bloqueSentencia;

    public NodoFor(Token tokenDeDatos, NodoVarLocal nodoVarLocal, NodoExpresion nodoExpresion, NodoAsignacion nodoAsignacion){
        this.tokenDeDatos = tokenDeDatos;
        this.varLocal = nodoVarLocal;
        this.expresion = nodoExpresion;
        this.asignacion = nodoAsignacion;
    }

    public void setSentencia(NodoBloque bloqueSentencia){
        this.bloqueSentencia = bloqueSentencia;
    }

    public void chequear() throws ExcepcionSemantica {
        varLocal.chequear();
        if(!esExpresionBoolean())
            throw new ExcepcionSemantica(tokenDeDatos, "El tipo de la expresion tiene que ser booleano.");
        asignacion.chequear();
        bloqueSentencia.chequear();

        TablaDeSimbolos.getUnidadActual().eliminarVarLocal(new VarLocal(varLocal.getTokenDeDatos(), varLocal.getTipo()));
    }

    public void generarCodigo() {
        String etiquetaInicioCicloFor = "inicioFor_" + TablaDeSimbolos.getUnidadActual().getTokenDeDatos().getLexema() + "_" + TablaDeSimbolos.getUnidadActual().getForCounter();
        String etiquetaFinCicloFor = "finFor_" + TablaDeSimbolos.getUnidadActual().getTokenDeDatos().getLexema() + "_" + TablaDeSimbolos.getUnidadActual().getForCounter();
        TablaDeSimbolos.getUnidadActual().incrementarForCounter();
        varLocal.generarCodigo();
        TablaDeSimbolos.insertarInstruccion(etiquetaInicioCicloFor + ":        ; agrego la etiqueta de inicio del ciclo del for");
        expresion.generarCodigo();
        TablaDeSimbolos.insertarInstruccion("BF " + etiquetaFinCicloFor + "          ; agrego el salto condicional con la etiqueta del fin del ciclo for");
        bloqueSentencia.generarCodigo();
        asignacion.generarCodigo();
        TablaDeSimbolos.insertarInstruccion("JUMP " + etiquetaInicioCicloFor + "         ; instruccion JUMP para saltar de nuevo al inicio del ciclo");
        TablaDeSimbolos.insertarInstruccion(etiquetaFinCicloFor + ": NOP          ; agrego la etiqueta de fin del ciclo for");
        TablaDeSimbolos.insertarInstruccion("FMEM 1        ; libero la memoria reservada para la variable del for");
        TablaDeSimbolos.getUnidadActual().decrementarMemoriaReservada(1);
        TablaDeSimbolos.getBloques().peek().decrementarMemoriaALiberar(1);
    }

    private boolean esExpresionBoolean() throws ExcepcionSemantica {
        Tipo tipoExpresion = expresion.chequear();
        if(tipoExpresion.getTokenDeDatos().getLexema().equals("boolean"))
            return true;
        else return false;
    }
}
