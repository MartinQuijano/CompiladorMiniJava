package main.analizadorSemantico.tablaDeSimbolos.compatibilidadDeTipos;

import main.analizadorSemantico.tablaDeSimbolos.tipos.*;

public class CompatibilidadTipoNull implements Compatibilidad{
    public boolean conforma(TipoEntero tipoEntero) {
        return false;
    }

    public boolean conforma(TipoString tipoString) {
        return false;
    }

    public boolean conforma(TipoChar tipoChar) {
        return false;
    }

    public boolean conforma(TipoVoid tipoVoid) {
        return false;
    }

    public boolean conforma(TipoNull tipoNull) {
        return false;
    }

    public boolean conforma(TipoBoolean tipoBoolean) {
        return false;
    }

    public boolean conforma(TipoReferencia tipoReferencia) {
        return true;
    }
}
