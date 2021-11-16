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

    protected String declaradoEnClase;
    private int offsetParaVarLocales;
    protected int memoriaReservada;

    protected int offset;

    public Unidad() {
        parametros = new HashMap<String, Parametro>();
        variablesLocales = new HashMap<String, VarLocal>();
        parametrosEnOrden = new ArrayList<Parametro>();

        offsetParaVarLocales = 0;
        memoriaReservada = 0;
    }

    public void incrementarMemoriaReservada(){
        memoriaReservada++;
    }

    public void decrementarMemoriaReservada(int cantidadLiberada){
        memoriaReservada -= cantidadLiberada;
    }

    public String getDeclaradoEnClase() {
        return declaradoEnClase;
    }

    public Token getTokenDeDatos() {
        return tokenDeDatos;
    }

    public int getOffset() {
        return offset;
    }

    public Parametro existeParametro(String nombreParametro) {
        if (parametros.containsKey(nombreParametro))
            return parametros.get(nombreParametro);
        else
            return null;
    }

    public void insertarParametro(Parametro parametroAInsertar) {
        parametros.put(parametroAInsertar.getTokenDeDatos().getLexema(), parametroAInsertar);
        parametrosEnOrden.add(parametroAInsertar);
    }

    public VarLocal existeVarLocal(String nombreVarLocal) {
        if (variablesLocales.containsKey(nombreVarLocal))
            return variablesLocales.get(nombreVarLocal);
        else
            return null;
    }

    public void insertarVarLocal(VarLocal varLocal) {
        varLocal.setOffset(offsetParaVarLocales);
        offsetParaVarLocales--;
        variablesLocales.put(varLocal.getTokenDeDatos().getLexema(), varLocal);
    }

    public void eliminarVarLocal(VarLocal varLocal) {
        offsetParaVarLocales++;
        variablesLocales.remove(varLocal.getTokenDeDatos().getLexema());
    }

    public void setBloque(NodoBloque nodoBloque) {
        this.bloque = nodoBloque;
    }

    public NodoBloque getBloque() {
        return bloque;
    }

    public List<Parametro> getParametros() {
        return parametrosEnOrden;
    }

    public abstract boolean esDinamica();

    public abstract void generarCodigo();

    public void estaBienDeclarada() throws ExcepcionSemantica {
        for (Parametro parametro : parametros.values()) {
            parametro.estaBienDeclarado();
        }
    }

    public int getMemoriaReservada(){
        return memoriaReservada;
    }

    public abstract boolean tieneThisElRADeLaUnidad();

    public void chequearSentencias() throws ExcepcionSemantica {
        TablaDeSimbolos.setUnidadActual(this);
        bloque.chequear();

        //TODO: prueba para offset de parametros - aca lo cambie
        // el offset va a depender de si el RA va a tener this
        // 0 en adelante para los que no
        // 1 en adelante para los que si
        // (pq cuando lo llamo hago 3 + offset)
        int cantidadDeParametros = parametrosEnOrden.size();
        int offsetDeParametro;
        if (tieneThisElRADeLaUnidad())
            offsetDeParametro = cantidadDeParametros;
        else
            offsetDeParametro = cantidadDeParametros - 1;
        for (Parametro parametro : parametrosEnOrden) {
            parametro.setOffset(offsetDeParametro);
            offsetDeParametro--;
        }
    }

    public abstract Tipo getTipo();

    public int getOffsetDeLugarDeRetorno(){
        int offset;
        if(tieneThisElRADeLaUnidad())
            offset = 4;
        else
            offset = 3;
        offset = offset + parametrosEnOrden.size();
        return offset;
    }
}