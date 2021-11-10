package main.analizadorSemantico.tablaDeSimbolos.unidades;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.sentencias.NodoBloque;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.variables.Parametro;
import main.analizadorSemantico.tablaDeSimbolos.variables.VarLocal;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Unidad {

    protected Token tokenDeDatos;
    protected HashMap<String, Parametro> parametros;
    protected ArrayList<Parametro> parametrosEnOrden;
    protected HashMap<String, VarLocal> variablesLocales;
    protected NodoBloque bloque;

    public Unidad(){
        parametros = new HashMap<String, Parametro>();
        variablesLocales = new HashMap<String, VarLocal>();
        parametrosEnOrden = new ArrayList<Parametro>();
    }

    public Token getTokenDeDatos() {
        return tokenDeDatos;
    }

    public Parametro existeParametro(String nombreParametro){
        if(parametros.containsKey(nombreParametro))
            return parametros.get(nombreParametro);
        else
            return null;
    }

    public void insertarParametro(Parametro parametroAInsertar){
        parametros.put(parametroAInsertar.getTokenDeDatos().getLexema(), parametroAInsertar);
        parametrosEnOrden.add(parametroAInsertar);
    }

    public VarLocal existeVarLocal(String nombreVarLocal){
        if(variablesLocales.containsKey(nombreVarLocal))
            return variablesLocales.get(nombreVarLocal);
        else
            return null;
    }

    public void insertarVarLocal(VarLocal varLocal){
        variablesLocales.put(varLocal.getTokenDeDatos().getLexema(), varLocal);
    }

    public void eliminarVarLocal(VarLocal varLocal){
        variablesLocales.remove(varLocal.getTokenDeDatos().getLexema());
    }

    public void setBloque(NodoBloque nodoBloque){
        this.bloque = nodoBloque;
    }

    public NodoBloque getBloque(){
        return bloque;
    }

    public List<Parametro> getParametros(){
        return parametrosEnOrden;
    }

    public abstract boolean esDinamica();

    public void estaBienDeclarada() throws ExcepcionSemantica {
        for(Parametro parametro : parametros.values()){
            parametro.estaBienDeclarado();
        }
    }

    public void chequearSentencias() throws ExcepcionSemantica {
        TablaDeSimbolos.setUnidadActual(this);
        bloque.chequear();
    }

    public abstract Tipo getTipo();
}