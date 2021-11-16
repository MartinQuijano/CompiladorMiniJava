package main.analizadorSemantico.ast.sentencias;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.variables.VarLocal;

public class NodoVarLocal extends NodoSentencia {

    protected Tipo tipo;

    public NodoVarLocal(Token tokenDeDatos, Tipo tipo){
        this.tokenDeDatos = tokenDeDatos;
        this.tipo = tipo;
    }

    public void chequear() throws ExcepcionSemantica {
        if(TablaDeSimbolos.getUnidadActual().existeParametro(tokenDeDatos.getLexema()) != null){
            throw new ExcepcionSemantica(tokenDeDatos, "Ya existe un parametro en el metodo con el mismo nombre.");
        } else if(TablaDeSimbolos.getUnidadActual().existeVarLocal(tokenDeDatos.getLexema()) != null){
            throw new ExcepcionSemantica(tokenDeDatos, "Ya existe una variable local en el metodo con el mismo nombre.");
        } else if(tipo.esTipoClase() && TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema()) == null){
            throw new ExcepcionSemantica(tipo.getTokenDeDatos(), "El tipo " + tipo.getTokenDeDatos().getLexema() + " (tipoClase) no se corresponde con una clase declarada.");
        }
        VarLocal varLocal = new VarLocal(tokenDeDatos, tipo);
        TablaDeSimbolos.getUnidadActual().insertarVarLocal(varLocal);
        TablaDeSimbolos.getBloques().peek().insertarVarLocal(varLocal);
    }

    public void generarCodigo() {
        TablaDeSimbolos.insertarInstruccion("RMEM 1        ; reservo memoria para la variable local");
        TablaDeSimbolos.getBloques().peek().incrementarMemoriaALiberar();
    }

    public Token getTokenDeDatos(){
        return tokenDeDatos;
    }

    public Tipo getTipo(){
        return tipo;
    }

    public void imprimir() {
        System.out.println(tipo.getTokenDeDatos().getLexema() + " " + tokenDeDatos.getLexema());
    }
}
