package main.analizadorSemantico.tablaDeSimbolos.unidades;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoReferencia;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoVoid;

public class Constructor extends Unidad{

    public Constructor(Token tokenDeDatos, String declaradoEnClase) {
        super();
        this.tokenDeDatos = tokenDeDatos;
        this.declaradoEnClase = declaradoEnClase;
    }

    public boolean esDinamica() {
        return true;
    }

    public void generarCodigo() {
        TablaDeSimbolos.insertarInstruccion("l" + tokenDeDatos.getLexema() + "_" + declaradoEnClase + ":");
        TablaDeSimbolos.insertarInstruccion("LOADFP");
        TablaDeSimbolos.insertarInstruccion("LOADSP");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        bloque.generarCodigo();
        //TODO: tener cuidado con el return; (probar)
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        //TODO: preg. +1 por el this
        TablaDeSimbolos.insertarInstruccion("RET " + (parametros.size()+1));
        TablaDeSimbolos.insertarInstruccion("");
    }

    public boolean tieneThisElRADeLaUnidad() {
        return true;
    }

    public String getForma(){
        return "static";
    }

    public Tipo getTipo() {
        return new TipoVoid();
    }

    public Tipo getTipoRetorno() {
        return new TipoVoid();
    }
}
