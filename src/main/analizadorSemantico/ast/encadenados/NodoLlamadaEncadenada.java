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

    protected Unidad entradaMetodo;
    protected ArrayList<NodoExpresion> argumentosActuales;

    public NodoLlamadaEncadenada(Token tokenDeDatos) {
        this.argumentosActuales = new ArrayList<>();
        this.tokenDeDatos = tokenDeDatos;
    }

    public void insertarArgActual(NodoExpresion nodoExpresion) {
        argumentosActuales.add(nodoExpresion);
    }

    public Tipo chequear(Tipo tipo) throws ExcepcionSemantica {
        if (!tipo.esTipoClase()) {
            throw new ExcepcionSemantica(tokenDeDatos, "El tipo de la izquierda no es un tipoClase, en cambio es un tipo " + tipo.getTokenDeDatos().getLexema() + ".");
        }
        if (TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema()) == null) {
            throw new ExcepcionSemantica(tipo.getTokenDeDatos(), "No existe la clase indicada por el tipo de la izquierda.");
        }
        Unidad metodoEnClaseActual = TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema()).existeMetodo(tokenDeDatos.getLexema());
        if (metodoEnClaseActual == null) {
            throw new ExcepcionSemantica(tokenDeDatos, "No existe el metodo en la clase indicada.");
        }
        chequearSiCoincidenLosTiposDeLosArgumentos(metodoEnClaseActual);
        Tipo tipoMetodoLlamado = TablaDeSimbolos.existeClase(tipo.getTokenDeDatos().getLexema()).existeMetodo(tokenDeDatos.getLexema()).getTipo();
        entradaMetodo = metodoEnClaseActual;
        if (nodoEncadenado != null)
            return nodoEncadenado.chequear(tipoMetodoLlamado);
        else
            return tipoMetodoLlamado;
    }

    public boolean esLlamada() {
        if (nodoEncadenado == null)
            return true;
        else
            return nodoEncadenado.esLlamada();
    }

    public boolean esVariable() {
        if (nodoEncadenado == null)
            return false;
        else
            return nodoEncadenado.esVariable();
    }

    public void generarCodigo() {
        if (entradaMetodo.esDinamica()) {
            if (!entradaMetodo.getTipo().getTokenDeDatos().getLexema().equals("void")) {
                TablaDeSimbolos.insertarInstruccion("RMEM 1        ; reservo lugar para el retorno");
                TablaDeSimbolos.insertarInstruccion("SWAP        ; hago swap para dejar el this en la posicion correcta de la pila");
            }
            for (NodoExpresion expresion : argumentosActuales) {
                expresion.generarCodigo();
                TablaDeSimbolos.insertarInstruccion("SWAP        ; cada vez que inserto un argumento en la pila lo intercambios con el this para que este quede en la posicion correcta");
            }
            TablaDeSimbolos.insertarInstruccion("DUP        ; duplico el this para no perderlo");
            TablaDeSimbolos.insertarInstruccion("LOADREF 0        ; cargo la VT");
            TablaDeSimbolos.insertarInstruccion("LOADREF " + entradaMetodo.getOffset() + "        ; cargo la direccion del metodo");
            TablaDeSimbolos.insertarInstruccion("CALL        ; llamo al metodo");
        } else {
            TablaDeSimbolos.insertarInstruccion("POP        ; como el metodo es estatico tengo que sacar el this que habia cargado");
            if (!entradaMetodo.getTipo().getTokenDeDatos().getLexema().equals("void"))
                TablaDeSimbolos.insertarInstruccion("RMEM 1        ; reservo lugar para el retorno");
            for (NodoExpresion expresion : argumentosActuales) {
                expresion.generarCodigo();
            }
            TablaDeSimbolos.insertarInstruccion("PUSH l" + tokenDeDatos.getLexema() + "_" + entradaMetodo.getDeclaradoEnClase() + "        ; hago push del label correspondiente al metodo encadenado a llamar");
            TablaDeSimbolos.insertarInstruccion("CALL        ; llamo al metodo encadenado");
        }

        if (nodoEncadenado != null){
            nodoEncadenado.setEsLadoIzqAsignacion(esLadoIzqAsignacion);
            nodoEncadenado.generarCodigo();
        }
    }

    protected void chequearSiCoincidenLosTiposDeLosArgumentos(Unidad unidadEnClaseActual) throws ExcepcionSemantica {
        if (unidadEnClaseActual.getParametros().size() != argumentosActuales.size())
            throw new ExcepcionSemantica(tokenDeDatos, "No existe unidad con la cantidad de parametros indicada. No se puede resolver.");
        int indiceParametros = 0;
        for (NodoExpresion nodoExpresion : argumentosActuales) {
            chequearSiCoincidenLosTipos(nodoExpresion.chequear(), unidadEnClaseActual.getParametros().get(indiceParametros).getTipo());
            indiceParametros++;
        }
    }

    protected void chequearSiCoincidenLosTipos(Tipo tipoDeArgActual, Tipo tipoDeArgFormal) throws ExcepcionSemantica {
        if (!tipoDeArgActual.conforma(tipoDeArgFormal))
            throw new ExcepcionSemantica(tokenDeDatos, "Se esperaba un argumento que conforme con el tipo " + tipoDeArgFormal.getTokenDeDatos().getLexema() + " y se encontro uno de tipo " + tipoDeArgActual.getTokenDeDatos().getLexema() + ".");
    }
}
