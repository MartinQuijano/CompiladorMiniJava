package main.analizadorSemantico.tablaDeSimbolos;

import main.analizadorLexico.Token;

public class TipoReferencia extends Tipo{

    protected Token tokenDeDatos;

    public TipoReferencia(Token tokenDeDatos){
        this.tokenDeDatos = tokenDeDatos;
    }

    public Token getTokenDeDatos(){
        return tokenDeDatos;
    }
}
