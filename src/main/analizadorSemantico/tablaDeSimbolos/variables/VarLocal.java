package main.analizadorSemantico.tablaDeSimbolos.variables;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;

public class VarLocal extends Variable{

    public VarLocal(Token tokenDeDatos, Tipo tipo){
        this.tokenDeDatos = tokenDeDatos;
        this.tipo = tipo;
    }

    public boolean esVisibile() {
        return true;
    }
}
