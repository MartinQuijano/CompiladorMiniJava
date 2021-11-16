package main.analizadorSemantico.tablaDeSimbolos.unidades;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

import java.util.Arrays;

public class Metodo extends Unidad {

    private Tipo tipo;

    private boolean seLeAsignoOffset;
    private boolean yaGeneroCodigo;

    private String forma;

    public Metodo(Token tokenDeDatos, Tipo tipo, String forma, String declaradoEnClase) {
        super();
        this.tokenDeDatos = tokenDeDatos;
        this.tipo = tipo;
        this.forma = forma;
        this.declaradoEnClase = declaradoEnClase;
        seLeAsignoOffset = false;
        yaGeneroCodigo = false;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public boolean yaGeneroCodigo() {
        return yaGeneroCodigo;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void generarCodigo() {
        //TODO arreglar. el if es para testear
        //
        TablaDeSimbolos.setUnidadActual(this);
        //
        if (!tokenDeDatos.getLexema().equals("debugPrint")) {
            TablaDeSimbolos.insertarInstruccion("l" + tokenDeDatos.getLexema() + "_" + declaradoEnClase + ":");
            TablaDeSimbolos.insertarInstruccion("LOADFP");
            TablaDeSimbolos.insertarInstruccion("LOADSP");
            TablaDeSimbolos.insertarInstruccion("STOREFP");
            bloque.generarCodigo();
            //TODO: tendria que ver si tiene retorno
            // Si lo tiene, va a tener return y el codigo necesario lo genera ese return. (dsp de lo que hablamos en clase se que estas instrucciones "muertas" si hay retorno tienen que quedar)
            // Si es void y aparece la sentencia de retorno vacia, genera lo mismo que genero a continuacion
            // Como el bloque es el que libera la memoria de las var locales, si hay un ret, va a tener que hacerse ahi la liberacion
            TablaDeSimbolos.insertarInstruccion("STOREFP");
            //TODO: hay que agregar este control pq los dinamicos tienen el this y hay que sumar 1 para el RET
            if (esDinamica())
                TablaDeSimbolos.insertarInstruccion("RET " + (parametros.size() + 1));
            else
                TablaDeSimbolos.insertarInstruccion("RET " + parametros.size());
            TablaDeSimbolos.insertarInstruccion("");
        }
        yaGeneroCodigo = true;
    }

    public void setSeLeAsignoOffset(boolean value) {
        this.seLeAsignoOffset = value;
    }

    public boolean seLeAsignoOffset() {
        return seLeAsignoOffset;
    }

    public String getForma() {
        return forma;
    }

    public boolean esDinamica() {
        if (forma.equals("dynamic"))
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

    public boolean tieneThisElRADeLaUnidad() {
        if (forma.equals("static"))
            return false;
        else return true;
    }
}
