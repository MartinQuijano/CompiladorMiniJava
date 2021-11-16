package main.analizadorSemantico.ast.expresiones.operandos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoNull;

public class NodoNull extends NodoOperando {
    public NodoNull(Token tokenDeDatos) {
        this.tokenDeDatos = tokenDeDatos;
        this.tipo = new TipoNull(tokenDeDatos);
    }

    public Tipo chequear() {
        return tipo;
    }

    public void generarCodigo() {
        TablaDeSimbolos.insertarInstruccion("PUSH " + 0);
    }
}
