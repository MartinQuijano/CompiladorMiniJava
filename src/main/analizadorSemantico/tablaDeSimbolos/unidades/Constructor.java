package main.analizadorSemantico.tablaDeSimbolos.unidades;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoReferencia;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoVoid;

public class Constructor extends Unidad{

    public Constructor(Token tokenDeDatos) {
        super();
        this.tokenDeDatos = tokenDeDatos;
    }

    public boolean esDinamica() {
        return true;
    }

    public Tipo getTipo() {
        return new TipoVoid();
        //return new TipoReferencia(tokenDeDatos);
    }

    public Tipo getTipoRetorno() {
        return new TipoVoid();
    }
}
