package main.analizadorSemantico.tablaDeSimbolos.tipos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.compatibilidadDeTipos.Compatibilidad;
import main.analizadorSemantico.tablaDeSimbolos.compatibilidadDeTipos.CompatibilidadTipoEntero;

public class TipoEntero extends TipoPrimitivo{

    public Token getTokenDeDatos(){
        return new Token("int", "int", 0);
    }

    public boolean conforma(Tipo tipo) {
        return tipo.compatibilidad(new CompatibilidadTipoEntero());
    }

    protected boolean compatibilidad(Compatibilidad compatibilidad){
        return compatibilidad.conforma(this);
    }
}
