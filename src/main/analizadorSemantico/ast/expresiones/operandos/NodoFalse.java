package main.analizadorSemantico.ast.expresiones.operandos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoBoolean;

public class NodoFalse extends NodoBoolean {
    public NodoFalse(Token tokenDeDatos) {
        this.tokenDeDatos = tokenDeDatos;
        this.tipo = new TipoBoolean();
    }
}
