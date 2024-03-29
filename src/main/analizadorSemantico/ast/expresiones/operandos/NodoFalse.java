package main.analizadorSemantico.ast.expresiones.operandos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoBoolean;

public class NodoFalse extends NodoBoolean {
    public NodoFalse(Token tokenDeDatos) {
        this.tokenDeDatos = tokenDeDatos;
        this.tipo = new TipoBoolean();
    }

    public void generarCodigo() {
        TablaDeSimbolos.insertarInstruccion("PUSH " + 0 + "        ; guardo en el tope de la pila el valor del literal boolean false que es 0");
    }
}
