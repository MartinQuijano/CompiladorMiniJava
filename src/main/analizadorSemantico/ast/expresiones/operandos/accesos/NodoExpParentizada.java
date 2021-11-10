package main.analizadorSemantico.ast.expresiones.operandos.accesos;

import main.analizadorSemantico.ast.expresiones.NodoExpresion;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

public class NodoExpParentizada extends NodoPrimario{

    private NodoExpresion expresion;

    public void setExpresion(NodoExpresion expresion){
        this.expresion = expresion;
    }

    public Tipo chequear() throws ExcepcionSemantica {
        if(nodoEncadenado != null)
            return nodoEncadenado.chequear(expresion.chequear());
        return expresion.chequear();
    }

    public boolean esLlamada() {
        if(nodoEncadenado == null)
            return false;
        else
            return nodoEncadenado.esLlamada();
    }

    public boolean esVariable() {
        if(nodoEncadenado == null)
            return false;
        else
            return nodoEncadenado.esVariable();
    }

    public void imprimir(){
        System.out.print("(");
        expresion.imprimir();
        System.out.print(")");
        nodoEncadenado.imprimir();
    }


}
