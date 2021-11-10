package main.analizadorSemantico.ast.encadenados;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.Clase;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.variables.Variable;

public class NodoVarEncadenada extends NodoEncadenado {

    public NodoVarEncadenada(Token tokenDeDatos) {
        this.tokenDeDatos = tokenDeDatos;
    }

    public void imprimir() {
        System.out.print(tokenDeDatos.getLexema());
        if (nodoEncadenado != null) {
            System.out.print(".");
            nodoEncadenado.imprimir();
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

    public Tipo chequear(Tipo tipo) throws ExcepcionSemantica {
        Variable variable = null;
        if (tipo.esTipoClase())
            if (TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema()) != null)
                if (TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema()).existeAtributo(tokenDeDatos.getLexema()) != null) {
                    Clase claseEnLaQueSeBuscaElAtributo = TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema());
                    variable = claseEnLaQueSeBuscaElAtributo.existeAtributo(tokenDeDatos.getLexema());
                    if (!variable.esVisibile()) {
                        throw new ExcepcionSemantica(tokenDeDatos, "El atributo existe pero no se tiene acceso a el.");
                    }
                } else {
                    throw new ExcepcionSemantica(tokenDeDatos, "No existe el atributo en la clase " + TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema()).getTokenDeDatos().getLexema() + ".");
                }
            else {
                throw new ExcepcionSemantica(tipo.getTokenDeDatos(), "No existe la clase indicada por el tipo de la izquierda.");
            }
        else {
            throw new ExcepcionSemantica(tipo.getTokenDeDatos(), "El tipo de la izquierda no es un tipo tipoClase.");
        }

        if (nodoEncadenado != null)
            return nodoEncadenado.chequear(variable.getTipo());
        else
            return variable.getTipo();
    }
}
