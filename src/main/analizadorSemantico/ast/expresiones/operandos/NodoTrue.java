package main.analizadorSemantico.ast.expresiones.operandos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoBoolean;

public class NodoTrue extends NodoBoolean {
    public NodoTrue(Token tokenDeDatos) {
        this.tokenDeDatos = tokenDeDatos;
        this.tipo = new TipoBoolean();
    }
}
