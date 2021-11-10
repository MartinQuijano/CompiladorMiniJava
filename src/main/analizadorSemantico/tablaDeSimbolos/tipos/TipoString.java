package main.analizadorSemantico.tablaDeSimbolos.tipos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.compatibilidadDeTipos.Compatibilidad;
import main.analizadorSemantico.tablaDeSimbolos.compatibilidadDeTipos.CompatibilidadTipoString;

public class TipoString extends TipoPrimitivo{

    public Token getTokenDeDatos() {
        return new Token("String", "String", 0);
    }

    public boolean conforma(Tipo tipo) {
        return tipo.compatibilidad(new CompatibilidadTipoString());
    }

    protected boolean compatibilidad(Compatibilidad compatibilidad){
        return compatibilidad.conforma(this);
    }
}
