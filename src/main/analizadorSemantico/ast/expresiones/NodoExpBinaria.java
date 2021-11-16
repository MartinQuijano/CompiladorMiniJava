package main.analizadorSemantico.ast.expresiones;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoBoolean;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoEntero;

import java.util.Arrays;

public class NodoExpBinaria extends NodoExpresion {

    protected NodoExpresion ladoIzquierdo;
    protected NodoExpresion ladoDerecho;
    protected Token operador;

    public NodoExpBinaria(NodoExpresion ladoIzquierdo, NodoExpresion ladoDerecho, Token operador) {
        this.ladoIzquierdo = ladoIzquierdo;
        this.ladoDerecho = ladoDerecho;
        this.operador = operador;
    }

    public Tipo chequear() throws ExcepcionSemantica {
        Tipo tipoLadoIzquierdo = ladoIzquierdo.chequear();
        Tipo tipoLadoDerecho = ladoDerecho.chequear();
        if (Arrays.asList("+", "-", "*", "/", "%").contains(operador.getLexema())) {
            if (tipoLadoIzquierdo.conforma(new TipoEntero())) {
                if (!tipoLadoDerecho.conforma(new TipoEntero())) {
                    throw new ExcepcionSemantica(operador, "Ambas subexpresiones tienen que ser de tipo entero y la subexpresion de la derecha es de tipo " + tipoLadoDerecho.getTokenDeDatos().getLexema());
                }
            } else {
                throw new ExcepcionSemantica(operador, "Ambas subexpresiones tienen que ser de tipo entero y la subexpresion de la izquierda es de tipo " + tipoLadoIzquierdo.getTokenDeDatos().getLexema());
            }
            return new TipoEntero();
        } else if (Arrays.asList("&&", "||").contains(operador.getLexema())) {
            if (tipoLadoIzquierdo.conforma(new TipoBoolean())) {
                if (!tipoLadoDerecho.conforma(new TipoBoolean())) {
                    throw new ExcepcionSemantica(operador, "Ambas subexpresiones tienen que ser de tipo boolean y la subexpresion de la derecha es de tipo " + tipoLadoDerecho.getTokenDeDatos().getLexema());
                }
            } else {
                throw new ExcepcionSemantica(operador, "Ambas subexpresiones tienen que ser de tipo boolean y la subexpresion de la izquierda es de tipo " + tipoLadoIzquierdo.getTokenDeDatos().getLexema());
            }
            return new TipoBoolean();
        } else if (Arrays.asList("==", "!=").contains(operador.getLexema())) {
            if (!(tipoLadoIzquierdo.conforma(tipoLadoDerecho) || tipoLadoDerecho.conforma(tipoLadoIzquierdo))) {
                throw new ExcepcionSemantica(operador, "Los tipos de las expresiones tienen que conformar.");
            }
            return new TipoBoolean();
        } else { // <, <=, >=, >
            if (tipoLadoIzquierdo.conforma(new TipoEntero())) {
                if (!tipoLadoDerecho.conforma(new TipoEntero())) {
                    throw new ExcepcionSemantica(operador, "Ambas subexpresiones tienen que ser de tipo entero y la subexpresion de la derecha es de tipo " + tipoLadoDerecho.getTokenDeDatos().getLexema());
                }
            } else {
                throw new ExcepcionSemantica(operador, "Ambas subexpresiones tienen que ser de tipo entero y la subexpresion de la izquierda es de tipo " + tipoLadoIzquierdo.getTokenDeDatos().getLexema());
            }
            return new TipoBoolean();
        }
    }

    public void imprimir() {
        ladoIzquierdo.imprimir();
        System.out.print(" " + operador.getLexema() + " ");
        ladoDerecho.imprimir();
    }

    public void generarCodigo() {
        ladoIzquierdo.generarCodigo();
        ladoDerecho.generarCodigo();
        if (operador.getLexema().equals("+")) {
            TablaDeSimbolos.insertarInstruccion("ADD");
        } else if (operador.getLexema().equals("-")) {
            TablaDeSimbolos.insertarInstruccion("SUB");
        } else if (operador.getLexema().equals("*")) {
            TablaDeSimbolos.insertarInstruccion("MUL");
        } else if (operador.getLexema().equals("/")) {
            TablaDeSimbolos.insertarInstruccion("DIV");
        } else if (operador.getLexema().equals("%")) {
            TablaDeSimbolos.insertarInstruccion("MOD");
        } else if (operador.getLexema().equals("&&")) {
            TablaDeSimbolos.insertarInstruccion("AND");
        } else if (operador.getLexema().equals("||")) {
            TablaDeSimbolos.insertarInstruccion("OR");
        } else if (operador.getLexema().equals("==")) {
            TablaDeSimbolos.insertarInstruccion("EQ");
        } else if (operador.getLexema().equals("!=")) {
            TablaDeSimbolos.insertarInstruccion("NE");
        } else if (operador.getLexema().equals("<")) {
            TablaDeSimbolos.insertarInstruccion("LT");
        } else if (operador.getLexema().equals("<=")) {
            TablaDeSimbolos.insertarInstruccion("LE");
        } else if (operador.getLexema().equals(">")) {
            TablaDeSimbolos.insertarInstruccion("GT");
        } else if (operador.getLexema().equals(">=")) {
            TablaDeSimbolos.insertarInstruccion("GE");
        }
    }

}
