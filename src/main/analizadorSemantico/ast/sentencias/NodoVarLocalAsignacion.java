package main.analizadorSemantico.ast.sentencias;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.expresiones.NodoExpresion;
import main.analizadorSemantico.ast.sentencias.NodoVarLocal;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.variables.VarLocal;

public class NodoVarLocalAsignacion extends NodoVarLocal {

    protected NodoExpresion nodoExpresion;
    protected Token tokenDeOperador;

    public NodoVarLocalAsignacion(Token tokenDeDatos, Token tokenDeOperador, Tipo tipo, NodoExpresion nodoExpresion) {
        super(tokenDeDatos, tipo);
        this.nodoExpresion = nodoExpresion;
        this.tokenDeOperador = tokenDeOperador;
    }

    public void chequear() throws ExcepcionSemantica {
        if(TablaDeSimbolos.getUnidadActual().existeParametro(tokenDeDatos.getLexema()) != null){
            throw new ExcepcionSemantica(tokenDeDatos, "Ya existe un parametro en el metodo con el mismo nombre.");
        } else if(TablaDeSimbolos.getUnidadActual().existeVarLocal(tokenDeDatos.getLexema()) != null){
            throw new ExcepcionSemantica(tokenDeDatos, "Ya existe una variable local en el metodo con el mismo nombre.");
        } else if(tipo.esTipoClase() && TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema()) == null){
            throw new ExcepcionSemantica(tipo.getTokenDeDatos(), "El tipo " + tipo.getTokenDeDatos().getLexema() + " (tipoClase) no se corresponde con una clase declarada.");
        }
        Tipo tipoNodoExpresion = nodoExpresion.chequear();
        if(!tipoNodoExpresion.conforma(tipo)){
            throw new ExcepcionSemantica(tokenDeOperador, "El tipo de la expresion no conforma con el tipo del acceso.");
        }
        VarLocal varLocal = new VarLocal(tokenDeDatos, tipo);
        TablaDeSimbolos.getUnidadActual().insertarVarLocal(varLocal);
        TablaDeSimbolos.getBloques().peek().insertarVarLocal(varLocal);
    }

    public void imprimir(){
        System.out.print(tipo.getTokenDeDatos().getLexema() + " " + tokenDeDatos.getLexema() + " = ");
        nodoExpresion.imprimir();
    }
}
