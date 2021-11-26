package main.analizadorSemantico.ast.expresiones.operandos.accesos;

import main.analizadorSemantico.ast.expresiones.NodoExpresion;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.unidades.Unidad;

import java.util.ArrayList;

public abstract class NodoUnidad extends NodoPrimario implements NodoConArgs{

    protected ArrayList<NodoExpresion> argumentosActuales;

    public void insertarArgActual(NodoExpresion nodoExpresion) {
        argumentosActuales.add(nodoExpresion);
    }

    protected void chequearSiCoincidenLosTiposDeLosArgumentos(Unidad unidadEnClaseActual) throws ExcepcionSemantica {
        if(unidadEnClaseActual.getParametros().size() != argumentosActuales.size())
            throw new ExcepcionSemantica(tokenDeDatos, "No existe unidad con la cantidad de parametros indicada. No se puede resolver.");
        int indiceParametros = 0;
        for(NodoExpresion nodoExpresion : argumentosActuales){
            chequearSiCoincidenLosTipos(nodoExpresion.chequear(), unidadEnClaseActual.getParametros().get(indiceParametros).getTipo());
            indiceParametros++;
        }
    }

    protected void chequearSiCoincidenLosTipos(Tipo tipoDeArgActual, Tipo tipoDeArgFormal) throws ExcepcionSemantica {
        if(!tipoDeArgActual.conforma(tipoDeArgFormal))
            throw new ExcepcionSemantica(tokenDeDatos, "Se esperaba un argumento que conforme con el tipo " + tipoDeArgFormal.getTokenDeDatos().getLexema() + " y se encontro uno de tipo " + tipoDeArgActual.getTokenDeDatos().getLexema() + ".");
    }

    public boolean esLlamada() {
        if(nodoEncadenado == null)
            return true;
        else
            return nodoEncadenado.esLlamada();
    }

    public boolean esVariable() {
        if(nodoEncadenado == null)
            return false;
        else
            return nodoEncadenado.esVariable();
    }

}
