package main.analizadorSemantico.ast.expresiones.operandos.accesos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.*;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoReferencia;
import main.analizadorSemantico.tablaDeSimbolos.unidades.Unidad;

import java.util.ArrayList;

public class NodoConstructor extends NodoUnidad{

    public NodoConstructor(Token tokenDeDatos){
        argumentosActuales = new ArrayList<>();
        this.tokenDeDatos = tokenDeDatos;
    }

    public Tipo chequear() throws ExcepcionSemantica {
        Unidad constructorEnClaseActual = TablaDeSimbolos.existeClase(tokenDeDatos.getLexema()).getConstructor();
        chequearSiCoincidenLosTiposDeLosArgumentos(constructorEnClaseActual);

        Tipo tipoConstructorLlamado = new TipoReferencia(TablaDeSimbolos.existeClase(tokenDeDatos.getLexema()).getTokenDeDatos());
        if (nodoEncadenado != null) {
            return nodoEncadenado.chequear(tipoConstructorLlamado);
        } else {
            return tipoConstructorLlamado;
        }
    }
}