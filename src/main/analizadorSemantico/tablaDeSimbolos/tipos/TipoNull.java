package main.analizadorSemantico.tablaDeSimbolos.tipos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.compatibilidadDeTipos.CompatibilidadTipoNull;

public class TipoNull extends TipoReferencia{
    public TipoNull(Token tokenDeDatos) {
        super(tokenDeDatos);
    }

    public Token getTokenDeDatos() {
        return tokenDeDatos;
    }

    public boolean conforma(Tipo tipo) {
        if (tipo.esTipoClase())
                return true;
        else
            return tipo.compatibilidad(new CompatibilidadTipoNull());
    }
}
