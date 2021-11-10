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
    protected NodoSentencia sentencia;

    public NodoFor(Token tokenDeDatos, NodoVarLocal nodoVarLocal, NodoExpresion nodoExpresion, NodoAsignacion nodoAsignacion){
        this.tokenDeDatos = tokenDeDatos;
        this.varLocal = nodoVarLocal;
        this.expresion = nodoExpresion;
        this.asignacion = nodoAsignacion;
    }

    public void setSentencia(NodoSentencia nodoSentencia){
        this.sentencia = nodoSentencia;
    }

    public void chequear() throws ExcepcionSemantica {
        varLocal.chequear();
        if(!esExpresionBoolean())
            throw new ExcepcionSemantica(tokenDeDatos, "El tipo de la expresion tiene que ser booleano.");
        asignacion.chequear();
        sentencia.chequear();

        TablaDeSimbolos.getUnidadActual().eliminarVarLocal(new VarLocal(varLocal.getTokenDeDatos(), varLocal.getTipo()));
    }

    private boolean esExpresionBoolean() throws ExcepcionSemantica {
        Tipo tipoExpresion = expresion.chequear();
        if(tipoExpresion.getTokenDeDatos().getLexema().equals("boolean"))
            return true;
        else return false;
    }

    public void imprimir() {
        System.out.print("for( ");
        varLocal.imprimir();
        System.out.print("; ");
        expresion.imprimir();
        System.out.print(";");
        asignacion.imprimir();
        System.out.print(")");
        sentencia.imprimir();
    }
}
