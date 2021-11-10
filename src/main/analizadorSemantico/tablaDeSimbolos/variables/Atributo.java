package main.analizadorSemantico.tablaDeSimbolos.variables;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

import java.util.Arrays;

public class Atributo extends Variable{

    private String visibilidad;

    public Atributo(String visibilidad, Token tokenDeDatos, Tipo tipo){
        this.visibilidad = visibilidad;
        this.tokenDeDatos = tokenDeDatos;
        this.tipo = tipo;
    }

    public String getVisibilidad(){
        return visibilidad;
    }

    public void estaBienDeclarado() throws ExcepcionSemantica {
        if (!Arrays.asList("int", "char", "boolean", "String").contains(tipo.getTokenDeDatos().getNombre())){
            if(TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema()) == null){
                throw new ExcepcionSemantica(tipo.getTokenDeDatos(), "El tipo de dato '" + tipo.getTokenDeDatos().getLexema() + "' del atributo " + tokenDeDatos.getLexema() + " no es un tipo declarado.");
            }
        }
    }

    public boolean esVisibile() {
        if(visibilidad.equals("public"))
            return true;
        else return false;
    }
}
