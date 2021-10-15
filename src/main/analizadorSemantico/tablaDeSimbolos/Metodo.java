package main.analizadorSemantico.tablaDeSimbolos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;

import java.util.Arrays;

public class Metodo extends Unidad {

    private Tipo tipo;
    private String forma;

    public Metodo(Token tokenDeDatos, Tipo tipo, String forma) {
        this.tokenDeDatos = tokenDeDatos;
        this.tipo = tipo;
        this.forma = forma;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getForma() {
        return forma;
    }

    public void estaBienDeclarada() throws ExcepcionSemantica {
        if (!Arrays.asList("int", "char", "boolean", "String", "void").contains(tipo.getTokenDeDatos().getNombre())) {
            if (TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema()) == null) {
                throw new ExcepcionSemantica(tipo.getTokenDeDatos(), "El tipo '" + tipo.getTokenDeDatos().getLexema() + "' de retorno del metodo " + tokenDeDatos.getLexema() + " no es un tipo declarado.");
            }
        }
        super.estaBienDeclarada();
    }
}
