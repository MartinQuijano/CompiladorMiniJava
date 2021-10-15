package main.analizadorSemantico.tablaDeSimbolos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;

import java.util.Arrays;

public class Parametro {

    private Token tokenDeDatos;
    private Tipo tipo;

    public Parametro(Token tokenDeDatos, Tipo tipo){
        this.tokenDeDatos = tokenDeDatos;
        this.tipo = tipo;
    }

    public Token getTokenDeDatos(){
        return tokenDeDatos;
    }

    public Tipo getTipo(){
        return tipo;
    }

    public void estaBienDeclarado() throws ExcepcionSemantica {
        if (!Arrays.asList("int", "char", "boolean", "String").contains(tipo.getTokenDeDatos().getNombre())){
            if(TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema()) == null){
                throw new ExcepcionSemantica(tipo.getTokenDeDatos(), "El tipo de dato '" + tipo.getTokenDeDatos().getLexema() + "' del parametro " + tokenDeDatos.getLexema() + " no es un tipo declarado.");
            }
        }
    }
}
