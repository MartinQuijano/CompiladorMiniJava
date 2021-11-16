package main.analizadorSemantico.ast.expresiones;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.expresiones.operandos.NodoOperando;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoBoolean;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoEntero;

public class NodoExpUnaria extends NodoExpresion{

    private Token operador;
    private NodoOperando operando;

    public NodoExpUnaria(Token tokenDeDatos){
        this.operador = tokenDeDatos;
    }

    public void setOperando(NodoOperando nodoOperando){
        this.operando = nodoOperando;
    }

    public Tipo chequear() throws ExcepcionSemantica {
        Tipo tipoSubExpresion = operando.chequear();
        if(operador.getLexema().equals("+") || operador.getLexema().equals("-")){
            if(!tipoSubExpresion.conforma(new TipoEntero())){
                throw new ExcepcionSemantica(operador, "La expresion debe ser de tipo entero y es de tipo " + tipoSubExpresion.getTokenDeDatos().getLexema());
            }
            return tipoSubExpresion;
        } else { // el operador es "!"
            if(!tipoSubExpresion.conforma(new TipoBoolean())){
                throw new ExcepcionSemantica(operador, "La expresion debe ser de tipo boolean y es de tipo " + tipoSubExpresion.getTokenDeDatos().getLexema());
            }
            return tipoSubExpresion;
        }
    }

    public void imprimir(){
        System.out.print(operador.getLexema());
        operando.imprimir();
    }

    public void generarCodigo() {
        operando.generarCodigo();
        if(operador.getLexema().equals("-")) {
            TablaDeSimbolos.insertarInstruccion("NEG");
        } else if (operador.getLexema().equals("!")){
            TablaDeSimbolos.insertarInstruccion("NOT");
        }
    }

}
