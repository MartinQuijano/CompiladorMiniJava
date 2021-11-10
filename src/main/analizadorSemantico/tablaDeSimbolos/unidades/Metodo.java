package main.analizadorSemantico.tablaDeSimbolos.unidades;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

import java.util.Arrays;

public class Metodo extends Unidad {

    private Tipo tipo;
    private String forma;
    private String declaradoEnClase;

    public Metodo(Token tokenDeDatos, Tipo tipo, String forma, String declaradoEnClase) {
        super();
        this.tokenDeDatos = tokenDeDatos;
        this.tipo = tipo;
        this.forma = forma;
        this.declaradoEnClase = declaradoEnClase;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getForma() {
        return forma;
    }

    public String getDeclaradoEnClase(){
        return declaradoEnClase;
    }

    public boolean esDinamica() {
        if(forma.equals("dynamic"))
            return true;
        else {
            return false;
        }
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
