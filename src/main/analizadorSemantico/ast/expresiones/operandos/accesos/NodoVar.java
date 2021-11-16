package main.analizadorSemantico.ast.expresiones.operandos.accesos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.variables.Atributo;
import main.analizadorSemantico.tablaDeSimbolos.variables.Parametro;
import main.analizadorSemantico.tablaDeSimbolos.variables.VarLocal;
import main.analizadorSemantico.tablaDeSimbolos.variables.Variable;

public class NodoVar extends NodoPrimario {

    private Variable entradaVariable;

    public NodoVar(Token tokenDeDatos) {
        this.tokenDeDatos = tokenDeDatos;
    }

    public Tipo chequear() throws ExcepcionSemantica {
        Variable variable = null;
        if (TablaDeSimbolos.getUnidadActual().existeParametro(tokenDeDatos.getLexema()) != null)
            variable = TablaDeSimbolos.getUnidadActual().existeParametro(tokenDeDatos.getLexema());
        else if (TablaDeSimbolos.getUnidadActual().existeVarLocal(tokenDeDatos.getLexema()) != null)
            variable = TablaDeSimbolos.getUnidadActual().existeVarLocal(tokenDeDatos.getLexema());
        else if (TablaDeSimbolos.getClaseActual().existeAtributo(tokenDeDatos.getLexema()) != null) {
            if (TablaDeSimbolos.getUnidadActual().esDinamica())
                variable = TablaDeSimbolos.getClaseActual().existeAtributo(tokenDeDatos.getLexema());
            else {
                throw new ExcepcionSemantica(tokenDeDatos, "No se puede referenciar a un campo no estatico en un contexto estatico.");
            }
        } else {
            throw new ExcepcionSemantica(tokenDeDatos, "No se encontro la variable.");
        }
        entradaVariable = variable;
        if (nodoEncadenado != null) {
            return nodoEncadenado.chequear(variable.getTipo());
        } else {
            return variable.getTipo();
        }
    }

    public boolean esLlamada() {
        if (nodoEncadenado == null)
            return false;
        else
            return nodoEncadenado.esLlamada();
    }

    public boolean esVariable() {
        if (nodoEncadenado == null)
            return true;
        else
            return nodoEncadenado.esVariable();
    }

    public void generarCodigo() {
        //TODO: por ahora sin encadenado
        if (entradaVariable instanceof Parametro) {
            if (!esLadoIzqAsignacion) {
                TablaDeSimbolos.insertarInstruccion("LOAD " + (3 + entradaVariable.getOffset()) + "        ; cargo el valor del parametro con el offset indicado en el tope de la pila");
            } else {
                TablaDeSimbolos.insertarInstruccion("STORE " + (3 + entradaVariable.getOffset()) + "        ; guardo en el parametro con el offset indicado el valor del tope de la pila");
            }
        } else if (entradaVariable instanceof VarLocal) {
            if (!esLadoIzqAsignacion) {
                TablaDeSimbolos.insertarInstruccion("LOAD " + entradaVariable.getOffset() + "        ; cargo el valor de la var local con el offset indicado en el tope de la pila");
            } else {
                TablaDeSimbolos.insertarInstruccion("STORE " + entradaVariable.getOffset() + "        ; guardo en la var local con el offset indicado el valor del tope de la pila");
            }
        } else if (entradaVariable instanceof Atributo){
            TablaDeSimbolos.insertarInstruccion("LOAD 3        ; guardo this en el tope de la pila");
            if (!esLadoIzqAsignacion) {
                TablaDeSimbolos.insertarInstruccion("LOADREF " + entradaVariable.getOffset() + "        ; cargo el valor del atributo con el offset indicado en el tope de la pila");
            } else{
                TablaDeSimbolos.insertarInstruccion("SWAP");
                TablaDeSimbolos.insertarInstruccion("STOREREF " + entradaVariable.getOffset() + "        ; guardo el valor en el tope de la pila en el atributo con el offset indicado");
            }
        }
        if(nodoEncadenado != null)
            nodoEncadenado.generarCodigo();
    }

    public boolean tieneValorDeRetorno() {
        return false;
    }

    public void imprimir() {
        System.out.print(tokenDeDatos.getLexema());
        if (nodoEncadenado != null) {
            System.out.print(".");
            nodoEncadenado.imprimir();
        }
    }

}
