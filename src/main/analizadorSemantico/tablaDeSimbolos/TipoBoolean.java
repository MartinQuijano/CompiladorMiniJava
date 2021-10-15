package main.analizadorSemantico.tablaDeSimbolos;

import main.analizadorLexico.Token;

public class TipoBoolean extends TipoPrimitivo{

    public Token getTokenDeDatos() {
        return new Token("boolean", "boolean", 0);
    }
}
