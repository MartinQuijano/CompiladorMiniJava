package main.analizadorSemantico.tablaDeSimbolos;

import main.analizadorLexico.Token;

public class TipoString extends TipoPrimitivo{

    public Token getTokenDeDatos() {
        return new Token("String", "String", 0);
    }
}
