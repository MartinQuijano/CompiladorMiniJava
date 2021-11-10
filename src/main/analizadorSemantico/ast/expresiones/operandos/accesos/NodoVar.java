package main.analizadorSemantico.ast.expresiones.operandos.accesos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.variables.Variable;

public class NodoVar extends NodoPrimario {

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

    public void imprimir() {
        System.out.print(tokenDeDatos.getLexema());
        if (nodoEncadenado != null) {
            System.out.print(".");
            nodoEncadenado.imprimir();
        }
    }

}
