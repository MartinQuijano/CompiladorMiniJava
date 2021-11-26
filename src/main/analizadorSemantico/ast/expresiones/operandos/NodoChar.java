package main.analizadorSemantico.ast.expresiones.operandos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoChar;

public class NodoChar extends NodoOperando {
    public NodoChar(Token tokenDeDatos) {
        this.tokenDeDatos = tokenDeDatos;
        this.tipo = new TipoChar();
    }

    public Tipo chequear() {
        return tipo;
    }

    public void generarCodigo() {
        TablaDeSimbolos.insertarInstruccion("PUSH '" + tokenDeDatos.getLexema() + "'        ; guardo en el tope de la pila el valor del literal char");
    }
}
