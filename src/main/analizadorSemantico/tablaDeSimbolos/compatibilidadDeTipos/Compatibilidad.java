package main.analizadorSemantico.tablaDeSimbolos.compatibilidadDeTipos;

import main.analizadorSemantico.tablaDeSimbolos.tipos.*;

public interface Compatibilidad {

    boolean conforma(TipoEntero tipoEntero);
    boolean conforma(TipoString tipoString);
    boolean conforma(TipoChar tipoChar);
    boolean conforma(TipoVoid tipoVoid);
    boolean conforma(TipoNull tipoNull);
    boolean conforma(TipoBoolean tipoBoolean);
    boolean conforma(TipoReferencia tipoReferencia);
}
