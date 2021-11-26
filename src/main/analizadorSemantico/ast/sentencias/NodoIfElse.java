package main.analizadorSemantico.ast.sentencias;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.expresiones.NodoExpresion;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;

public class NodoIfElse extends NodoIf{

    protected NodoBloque bloqueElse;

    public NodoIfElse(Token tokenDeDatos, NodoExpresion nodoExpresion, NodoBloque bloqueThen, NodoBloque bloqueElse) {
        super(tokenDeDatos, nodoExpresion, bloqueThen);
        this.bloqueElse = bloqueElse;
    }

    public void chequear() throws ExcepcionSemantica {
        super.chequear();
        bloqueElse.chequear();
    }

    public void generarCodigo(){
        condicion.generarCodigo();
        TablaDeSimbolos.getUnidadActual().incrementarIfCounter();
        String etiquetaElseIf = "elseif_" + TablaDeSimbolos.getUnidadActual().getTokenDeDatos().getLexema() + "_" + TablaDeSimbolos.getUnidadActual().getIfCounter();
        TablaDeSimbolos.insertarInstruccion("BF " + etiquetaElseIf + "        ; agrego el salto condicional con la etiqueta de la sentencia else del if");
        bloqueThen.generarCodigo();
        String etiquetaFinIf = "finIf_" + TablaDeSimbolos.getUnidadActual().getTokenDeDatos().getLexema() + "_" + TablaDeSimbolos.getUnidadActual().getIfCounter();
        TablaDeSimbolos.insertarInstruccion("JUMP "+ etiquetaFinIf + "           ; inserto JUMP para que haga el salto al fin del fin, para saltear el codigo del bloque else");
        TablaDeSimbolos.insertarInstruccion(etiquetaElseIf + ": NOP        ; agrego la etiqueta del else del if");
        bloqueElse.generarCodigo();
        TablaDeSimbolos.insertarInstruccion(etiquetaFinIf + ": NOP        ; agrego la etiqueta del fin del if");

    }

}
