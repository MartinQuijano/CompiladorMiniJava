package main.analizadorSemantico.ast.expresiones.operandos.accesos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.unidades.Unidad;

import java.util.ArrayList;

public class NodoLlamadaMetodo extends NodoUnidad {

    public NodoLlamadaMetodo(Token tokenDeDatos) {
        argumentosActuales = new ArrayList<>();
        this.tokenDeDatos = tokenDeDatos;
    }

    public Tipo chequear() throws ExcepcionSemantica {
        Unidad metodoEnClaseActual = TablaDeSimbolos.getClaseActual().existeMetodo(tokenDeDatos.getLexema());
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
}
