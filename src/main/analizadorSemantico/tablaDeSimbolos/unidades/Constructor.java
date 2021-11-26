package main.analizadorSemantico.tablaDeSimbolos.unidades;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
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
        TablaDeSimbolos.insertarInstruccion("l" + tokenDeDatos.getLexema() + "_" + declaradoEnClase + ":         ; agrego el label del constructor");
        TablaDeSimbolos.insertarInstruccion("LOADFP         ; apilo el valor del registro fp");
        TablaDeSimbolos.insertarInstruccion("LOADSP         ; apilo el valor del registro sp");
        TablaDeSimbolos.insertarInstruccion("STOREFP         ; almaceno lo que tengo en el tope de la pila en el registro fp (con estas 3 ultimas instrucciones queda el enlace dinamico para el retorno armado)");
        bloque.generarCodigo();
        TablaDeSimbolos.insertarInstruccion("STOREFP          ; almaceno lo que tengo en el tope de la pila en el registro fp");
        TablaDeSimbolos.insertarInstruccion("RET " + (parametros.size()+1) + "          ; retorno del llamado liberando memoria equivalente a la cantidad de parametros + 1");
        TablaDeSimbolos.insertarInstruccion("");
    }

    public boolean tieneThisElRADeLaUnidad() {
        return true;
    }

    public Tipo getTipo() {
        return new TipoVoid();
    }

}
