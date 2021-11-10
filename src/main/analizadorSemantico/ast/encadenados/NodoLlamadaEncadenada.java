package main.analizadorSemantico.ast.encadenados;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.expresiones.NodoExpresion;
import main.analizadorSemantico.ast.expresiones.operandos.accesos.NodoConArgs;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.unidades.Unidad;

import java.util.ArrayList;

public class NodoLlamadaEncadenada extends NodoEncadenado implements NodoConArgs {
    protected ArrayList<NodoExpresion> argumentosActuales;

    public NodoLlamadaEncadenada(Token tokenDeDatos){
        this.argumentosActuales = new ArrayList<>();
        this.tokenDeDatos = tokenDeDatos;
    }

    public void insertarArgActual(NodoExpresion nodoExpresion) {
        argumentosActuales.add(nodoExpresion);
    }

    public Tipo chequear(Tipo tipo) throws ExcepcionSemantica {
        if(!tipo.esTipoClase())
        {
            throw new ExcepcionSemantica(tokenDeDatos, "El tipo de la izquierda no es un tipoClase, en cambio es un tipo " + tipo.getTokenDeDatos().getLexema()+".");
        }
        if (TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema()) == null){
            throw new ExcepcionSemantica(tipo.getTokenDeDatos(), "No existe la clase indicada por el tipo de la izquierda.");
        }
        Unidad metodoEnClaseActual = TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema()).existeMetodo(tokenDeDatos.getLexema());
        if(metodoEnClaseActual == null){
            throw new ExcepcionSemantica(tokenDeDatos, "No existe el metodo en la clase indicada.");
        }
        chequearSiCoincidenLosTiposDeLosArgumentos(metodoEnClaseActual);
        Tipo tipoMetodoLlamado = TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema()).existeMetodo(tokenDeDatos.getLexema()).getTipo();
        if(nodoEncadenado != null)
            return nodoEncadenado.chequear(tipoMetodoLlamado);
        else
            return tipoMetodoLlamado;
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

    public void imprimir() {
        System.out.print(tokenDeDatos.getLexema() + "(");
        for(NodoExpresion nodoExpresion : argumentosActuales){
            nodoExpresion.imprimir();
            System.out.print(", ");
        }
        nodoEncadenado.imprimir();
    }
}
