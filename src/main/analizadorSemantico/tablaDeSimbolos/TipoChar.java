package main.analizadorSemantico.tablaDeSimbolos;

import main.analizadorLexico.Token;

public class TipoChar extends TipoPrimitivo{

    public Token getTokenDeDatos() {
        return new Token("char", "char", 0);
    }
}
