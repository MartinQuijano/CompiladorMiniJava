package main.analizadorSemantico.tablaDeSimbolos.variables;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

import java.util.Arrays;

public class Parametro extends Variable{

    public Parametro(Token tokenDeDatos, Tipo tipo){
        this.tokenDeDatos = tokenDeDatos;
        this.tipo = tipo;
    }

    public void estaBienDeclarado() throws ExcepcionSemantica {
        if (!Arrays.asList("int", "char", "boolean", "String").contains(tipo.getTokenDeDatos().getNombre())){
            if(TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema()) == null){
                throw new ExcepcionSemantica(tipo.getTokenDeDatos(), "El tipo de dato '" + tipo.getTokenDeDatos().getLexema() + "' del parametro " + tokenDeDatos.getLexema() + " no es un tipo declarado.");
            }
        }
    }

    public boolean esVisibile() {
        return true;
    }
}
