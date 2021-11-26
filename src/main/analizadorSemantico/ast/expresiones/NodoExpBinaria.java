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

    public void generarCodigo() {
        ladoIzquierdo.generarCodigo();
        ladoDerecho.generarCodigo();
        if (operador.getLexema().equals("+")) {
            TablaDeSimbolos.insertarInstruccion("ADD        ; instruccion ADD para sumar los ultimos 2 valores de la pila");
        } else if (operador.getLexema().equals("-")) {
            TablaDeSimbolos.insertarInstruccion("SUB        ; instruccion SUB para restar los ultimos 2 valores de la pila");
        } else if (operador.getLexema().equals("*")) {
            TablaDeSimbolos.insertarInstruccion("MUL        ; instruccion MUL para multiplicar ultimos 2 valores de la pila");
        } else if (operador.getLexema().equals("/")) {
            TablaDeSimbolos.insertarInstruccion("DIV        ; instruccion DIV para dividir el anteultimo valor de la pila por el ultimo valor en la pila");
        } else if (operador.getLexema().equals("%")) {
            TablaDeSimbolos.insertarInstruccion("MOD        ; instruccion MOD para calcular el modulo entre el anteultimo valor de la pila por el ultimo valor en la pila");
        } else if (operador.getLexema().equals("&&")) {
            TablaDeSimbolos.insertarInstruccion("AND        ; instruccion AND para calcular el valor booleano entre los ultimos 2 valores de la pila");
        } else if (operador.getLexema().equals("||")) {
            TablaDeSimbolos.insertarInstruccion("OR        ; instruccion OR para calcular el valor booleano entre los ultimos 2 valores de la pila");
        } else if (operador.getLexema().equals("==")) {
            TablaDeSimbolos.insertarInstruccion("EQ        ; instruccion EQ para calcular si son equivalentes los ultimos 2 valores de la pila");
        } else if (operador.getLexema().equals("!=")) {
            TablaDeSimbolos.insertarInstruccion("NE        ; instruccion NE para calcular si son distintos los ultimos 2 valores de la pila");
        } else if (operador.getLexema().equals("<")) {
            TablaDeSimbolos.insertarInstruccion("LT        ; instruccion LT para calcular si el anteultimo valor de la pila es menor que el que esta ultimo");
        } else if (operador.getLexema().equals("<=")) {
            TablaDeSimbolos.insertarInstruccion("LE        ; instruccion LE para calcular si el anteultimo valor de la pila es menor-igual que el que esta ultimo");
        } else if (operador.getLexema().equals(">")) {
            TablaDeSimbolos.insertarInstruccion("GT        ; instruccion GT para calcular si el anteultimo valor de la pila es mayor que el que esta ultimo");
        } else if (operador.getLexema().equals(">=")) {
            TablaDeSimbolos.insertarInstruccion("GE        ; instruccion GE para calcular si el anteultimo valor de la pila es mayor-igual que el que esta ultimo");
        }
    }

}
