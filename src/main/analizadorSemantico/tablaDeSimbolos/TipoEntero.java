package main.analizadorSemantico.tablaDeSimbolos;

import main.analizadorLexico.Token;

public class TipoEntero extends TipoPrimitivo{

    public Token getTokenDeDatos(){
        return new Token("int", "int", 0);
    }
}
