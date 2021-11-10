package main.analizadorSemantico.ast.expresiones.operandos.accesos;

import main.analizadorSemantico.ast.expresiones.operandos.NodoOperando;

public abstract class NodoAcceso extends NodoOperando {

    public abstract boolean esLlamada();
    public abstract boolean esVariable();
}
