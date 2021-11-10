package main.analizadorSemantico.ast.expresiones.operandos.accesos;

import main.analizadorSemantico.ast.expresiones.NodoExpresion;

public interface NodoConArgs {
    void insertarArgActual(NodoExpresion ne);
}
