package main.analizadorSemantico.ast.sentencias;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;

public class NodoReturn extends NodoSentencia {

    public NodoReturn(Token tokenDeDatos){
        this.tokenDeDatos = tokenDeDatos;
    }

    public void chequear() throws ExcepcionSemantica {
        if(!TablaDeSimbolos.getUnidadActual().getTipo().getTokenDeDatos().getLexema().equals("void"))
            throw new ExcepcionSemantica(tokenDeDatos, "El tipo de retorno del metodo debe ser void.");
    }

    public void setTokenDeDatos(Token tokenDeDatos){
        this.tokenDeDatos = tokenDeDatos;
    }

    public void imprimir() {
        System.out.println("return");
    }
}
