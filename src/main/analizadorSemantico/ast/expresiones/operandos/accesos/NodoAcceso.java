package main.analizadorSemantico.ast.expresiones.operandos.accesos;

import main.analizadorSemantico.ast.expresiones.operandos.NodoOperando;

public abstract class NodoAcceso extends NodoOperando {
    protected boolean esLadoIzqAsignacion = false;

    public abstract boolean esLlamada();
    public abstract boolean esVariable();

    public abstract void generarCodigo();

    public void setEsLadoIzqAsignacion(boolean value){
        esLadoIzqAsignacion = value;
    }

}
