package main.analizadorSemantico.tablaDeSimbolos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;

import java.util.Arrays;

public class Atributo {

    private String visibilidad;
    private Token tokenDeDatos;
    private Tipo tipo;

    public Atributo(String visibilidad, Token tokenDeDatos, Tipo tipo){
        this.visibilidad = visibilidad;
        this.tokenDeDatos = tokenDeDatos;
        this.tipo = tipo;
    }

    public Token getTokenDeDatos(){
        return tokenDeDatos;
    }

    public String getVisibilidad(){
        return visibilidad;
    }

    public Tipo getTipo(){
        return tipo;
    }

    public void estaBienDeclarado() throws ExcepcionSemantica {
        if (!Arrays.asList("int", "char", "boolean", "String").contains(tipo.getTokenDeDatos().getNombre())){
            if(TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema()) == null){
                throw new ExcepcionSemantica(tipo.getTokenDeDatos(), "El tipo de dato '" + tipo.getTokenDeDatos().getLexema() + "' del atributo " + tokenDeDatos.getLexema() + " no es un tipo declarado.");
            }
        }
    }
}
