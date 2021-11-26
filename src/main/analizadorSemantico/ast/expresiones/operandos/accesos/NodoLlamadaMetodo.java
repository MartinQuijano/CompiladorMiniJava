package main.analizadorSemantico.ast.expresiones.operandos.accesos;

import jdk.swing.interop.SwingInterOpUtils;
import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.expresiones.NodoExpresion;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.unidades.Unidad;

import java.util.ArrayList;

public class NodoLlamadaMetodo extends NodoUnidad {

    private Unidad entradaMetodo;

    public NodoLlamadaMetodo(Token tokenDeDatos) {
        argumentosActuales = new ArrayList<>();
        this.tokenDeDatos = tokenDeDatos;
    }

    public Tipo chequear() throws ExcepcionSemantica {
        Unidad metodoEnClaseActual = TablaDeSimbolos.getClaseActual().existeMetodo(tokenDeDatos.getLexema());
        entradaMetodo = metodoEnClaseActual;
        if (metodoEnClaseActual == null) {
            throw new ExcepcionSemantica(tokenDeDatos, "No existe el metodo en la clase actual.");
        }
        chequearSiCoincidenLosTiposDeLosArgumentos(metodoEnClaseActual);
        if (!TablaDeSimbolos.getUnidadActual().esDinamica()) {
            if (TablaDeSimbolos.getClaseActual().existeMetodo(tokenDeDatos.getLexema()).esDinamica()) {
                throw new ExcepcionSemantica(tokenDeDatos, "No se puede llamar a un metodo dinamico desde un contexto estatico");
            }
        }

        Tipo tipoMetodoLlamado = TablaDeSimbolos.getClaseActual().existeMetodo(tokenDeDatos.getLexema()).getTipo();
        if (nodoEncadenado != null) {
            return nodoEncadenado.chequear(tipoMetodoLlamado);
        } else {
            return tipoMetodoLlamado;
        }
    }

    public void generarCodigo() {
        if(entradaMetodo.esDinamica()) {
            TablaDeSimbolos.insertarInstruccion("LOAD 3        ; cargo lo que será el this");
            if(!entradaMetodo.getTipo().getTokenDeDatos().getLexema().equals("void")){
                TablaDeSimbolos.insertarInstruccion("RMEM 1        ; reservo lugar para el retorno");
                TablaDeSimbolos.insertarInstruccion("SWAP        ; hago un swap para dejar el this en la posicion que corresponde");
            }
            for (NodoExpresion expresion : argumentosActuales) {
                expresion.generarCodigo();
                TablaDeSimbolos.insertarInstruccion("SWAP        ; por cada argumento que agrego al tope de la pila hago un swap para dejar el this en la posicion correcta");
            }
            TablaDeSimbolos.insertarInstruccion("DUP        ; duplico el this para no perderlo");
            TablaDeSimbolos.insertarInstruccion("LOADREF 0        ; cargo la VT");
            TablaDeSimbolos.insertarInstruccion("LOADREF " + entradaMetodo.getOffset() + "        ; cargo la direccion del metodo");
            TablaDeSimbolos.insertarInstruccion("CALL        ; llamo al metodo");
        } else {
            if(!entradaMetodo.getTipo().getTokenDeDatos().getLexema().equals("void"))
                TablaDeSimbolos.insertarInstruccion("RMEM 1        ; reservo lugar para el retorno");
            for (NodoExpresion expresion : argumentosActuales) {
                expresion.generarCodigo();
            }
            TablaDeSimbolos.insertarInstruccion("PUSH l" + tokenDeDatos.getLexema() + "_" + entradaMetodo.getDeclaradoEnClase() + "         ; cargo en el tope de la pila el label del metodo a llamar");
            TablaDeSimbolos.insertarInstruccion("CALL        ; llamo al metodo cuyo label esta en el tope de la pila");
        }

        if (nodoEncadenado != null){
            nodoEncadenado.setEsLadoIzqAsignacion(esLadoIzqAsignacion);
            nodoEncadenado.generarCodigo();
        }
    }

}
