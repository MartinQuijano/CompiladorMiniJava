package main.analizadorSemantico.tablaDeSimbolos.tipos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.compatibilidadDeTipos.Compatibilidad;
import main.analizadorSemantico.tablaDeSimbolos.compatibilidadDeTipos.CompatibilidadTipoChar;

public class TipoChar extends TipoPrimitivo{

    public Token getTokenDeDatos() {
        return new Token("char", "char", 0);
    }

    public boolean conforma(Tipo tipo) {
        return tipo.compatibilidad(new CompatibilidadTipoChar());
    }

    protected boolean compatibilidad(Compatibilidad compatibilidad){
        return compatibilidad.conforma(this);
    }
}
