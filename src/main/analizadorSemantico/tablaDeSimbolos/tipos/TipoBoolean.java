package main.analizadorSemantico.tablaDeSimbolos.tipos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.compatibilidadDeTipos.Compatibilidad;
import main.analizadorSemantico.tablaDeSimbolos.compatibilidadDeTipos.CompatibilidadTipoBoolean;

public class TipoBoolean extends TipoPrimitivo{

    public Token getTokenDeDatos() {
        return new Token("boolean", "boolean", 0);
    }

    public boolean conforma(Tipo tipo) {
        return tipo.compatibilidad(new CompatibilidadTipoBoolean());
    }

    protected boolean compatibilidad(Compatibilidad compatibilidad){
        return compatibilidad.conforma(this);
    }
}
