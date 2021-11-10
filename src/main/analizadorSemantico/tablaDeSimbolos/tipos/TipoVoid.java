package main.analizadorSemantico.tablaDeSimbolos.tipos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.compatibilidadDeTipos.Compatibilidad;
import main.analizadorSemantico.tablaDeSimbolos.compatibilidadDeTipos.CompatibilidadTipoVoid;

public class TipoVoid extends TipoPrimitivo{

    public Token getTokenDeDatos() {
        return new Token("void", "void", 0);
    }

    public boolean conforma(Tipo tipo) {
        return tipo.compatibilidad(new CompatibilidadTipoVoid());
    }

    protected boolean compatibilidad(Compatibilidad compatibilidad){
        return compatibilidad.conforma(this);
    }
}
