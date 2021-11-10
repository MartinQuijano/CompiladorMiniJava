package main.analizadorSemantico.ast.sentencias;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.expresiones.NodoExpresion;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;

public class NodoIfElse extends NodoIf{

    protected NodoSentencia sentenciaElse;

    public NodoIfElse(Token tokenDeDatos, NodoExpresion nodoExpresion, NodoSentencia nodoSentenciaThen, NodoSentencia nodoSentenciaElse) {
        super(tokenDeDatos, nodoExpresion, nodoSentenciaThen);
        this.sentenciaElse = nodoSentenciaElse;
    }

    public void chequear() throws ExcepcionSemantica {
        super.chequear();
        sentenciaElse.chequear();
    }

    public void imprimir() {
        System.out.print("if(");
        condicion.imprimir();
        System.out.print(")");
        sentenciaThen.imprimir();
        System.out.println(" else ");
        sentenciaElse.imprimir();
    }
}
