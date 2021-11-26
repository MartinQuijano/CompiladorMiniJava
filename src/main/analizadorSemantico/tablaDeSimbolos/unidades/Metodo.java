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

    public void setYaGeneroCodigo(boolean value) {
        yaGeneroCodigo = value;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void generarCodigo() {
        TablaDeSimbolos.setUnidadActual(this);

        TablaDeSimbolos.insertarInstruccion("l" + tokenDeDatos.getLexema() + "_" + declaradoEnClase + ":         ; agrego el label del metodo");
        TablaDeSimbolos.insertarInstruccion("LOADFP         ; apilo el valor del registro fp");
        TablaDeSimbolos.insertarInstruccion("LOADSP         ; apilo el valor del registro sp");
        TablaDeSimbolos.insertarInstruccion("STOREFP         ; almaceno lo que tengo en el tope de la pila en el registro fp (con estas 3 ultimas instrucciones queda el enlace dinamico para el retorno armado)");
        bloque.generarCodigo();
        TablaDeSimbolos.insertarInstruccion("STOREFP          ; almaceno lo que tengo en el tope de la pila en el registro fp");
        if (esDinamica())
            TablaDeSimbolos.insertarInstruccion("RET " + (parametros.size() + 1) + "          ; retorno del llamado liberando memoria equivalente a la cantidad de parametros + 1 del this por ser un metodo dinamico");
        else
            TablaDeSimbolos.insertarInstruccion("RET " + parametros.size() + "         ; retorno del llamado liberando memoria equivalente a la cantidad de parametros");
        TablaDeSimbolos.insertarInstruccion("");

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
