package main.analizadorSemantico.tablaDeSimbolos;

import main.analizadorLexico.Token;

public class TipoVoid extends TipoPrimitivo{

    public Token getTokenDeDatos() {
        return new Token("void", "void", 0);
    }
}
