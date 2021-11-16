package main.analizadorSemantico.ast.expresiones.operandos.accesos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.expresiones.NodoExpresion;
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

    public void generarCodigo() {
        //TODO: revisar
        TablaDeSimbolos.insertarInstruccion("RMEM 1        ; reservo memoria para el resultado del malloc (ref a CIR)");
        TablaDeSimbolos.insertarInstruccion("PUSH " + (1 + TablaDeSimbolos.existeClase(tokenDeDatos.getLexema()).getAtributosEnOrdenParaOffset().size()) + "        ; guardo en el tope de la pila la cantidad de variable de instancia del CIR +1 para la ref a la VT");
        TablaDeSimbolos.insertarInstruccion("PUSH simple_malloc        ; pongo la dir de la rutina para alojar memoria en el heap en el tope de la pila");
        TablaDeSimbolos.insertarInstruccion("CALL        ; invoco la rutina en el tope de la pila");
        TablaDeSimbolos.insertarInstruccion("DUP        ; para no perder la referencia al CIR al hacer STOREREF para asociar la VT");
        TablaDeSimbolos.insertarInstruccion("PUSH VT_" + tokenDeDatos.getLexema() + "        ; apilo la direccion de comienzo de la VT");
        TablaDeSimbolos.insertarInstruccion("STOREREF 0        ; guardo la referencia a la VT en el CIR creado");
        TablaDeSimbolos.insertarInstruccion("DUP        ; duplico para no perder la referencia y que quede en el tope de la pila cuando termine la ejecucion del constructor");
        for(NodoExpresion argumento : argumentosActuales){
            argumento.generarCodigo();
            TablaDeSimbolos.insertarInstruccion("SWAP        ; intercambio el argumento agregado con el this para que este quede en el lugar correcto");
        }
        TablaDeSimbolos.insertarInstruccion("PUSH l" + tokenDeDatos.getLexema() + "_" + tokenDeDatos.getLexema() + "        ; hago el push del label del constructor");
        TablaDeSimbolos.insertarInstruccion("CALL        ; hago el call del label del constructor que esta en el tope de la pila");
    }

    public boolean tieneValorDeRetorno() {
        return true;
    }
}