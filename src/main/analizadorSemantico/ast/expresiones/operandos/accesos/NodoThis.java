package main.analizadorSemantico.ast.expresiones.operandos.accesos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

public class NodoThis extends NodoPrimario{

    public NodoThis(Token tokenDeDatos){
        this.tokenDeDatos = tokenDeDatos;
    }

    public Tipo chequear() throws ExcepcionSemantica {
        if(!TablaDeSimbolos.getUnidadActual().esDinamica())
        {
            throw new ExcepcionSemantica(tokenDeDatos, "No puede utilizarse this en un metodo estatico");
        }
        if(nodoEncadenado != null)
            return nodoEncadenado.chequear(TablaDeSimbolos.getClaseActual().getTipo());
        else
            return TablaDeSimbolos.getClaseActual().getTipo();
    }

    public boolean esLlamada() {
        if(nodoEncadenado == null)
            return false;
        else
            return nodoEncadenado.esLlamada();
    }

    public boolean esVariable() {
        if(nodoEncadenado == null)
            return true;
        else
            return nodoEncadenado.esVariable();
    }

    public void imprimir(){
        System.out.print("this");
        if(nodoEncadenado != null) {
            System.out.print(".");
            nodoEncadenado.imprimir();
        }
    }
}
