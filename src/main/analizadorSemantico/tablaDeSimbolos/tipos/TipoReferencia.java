package main.analizadorSemantico.tablaDeSimbolos.tipos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.Clase;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.compatibilidadDeTipos.Compatibilidad;
import main.analizadorSemantico.tablaDeSimbolos.compatibilidadDeTipos.CompatibilidadTipoReferencia;

public class TipoReferencia extends Tipo {

    protected Token tokenDeDatos;

    public TipoReferencia(Token tokenDeDatos) {
        this.tokenDeDatos = tokenDeDatos;
    }

    public Token getTokenDeDatos() {
        return tokenDeDatos;
    }

    public boolean esTipoClase() {
        return true;
    }


    protected boolean compatibilidad(Compatibilidad compatibilidad) {
        return compatibilidad.conforma(this);
    }

    public boolean conforma(Tipo tipo) {
        if (tipo.esTipoClase())
            if (tipo.getTokenDeDatos().getLexema().equals("Null"))
                return false;
            else
                return tieneComoAncestro(tipo, this);
        else
            return tipo.compatibilidad(new CompatibilidadTipoReferencia());
    }

    private boolean tieneComoAncestro(Tipo tipoDeAncestroABuscar, TipoReferencia tipoDeClaseAValidarComoSubtipo) {
        Clase claseAComprobarSubtipo = TablaDeSimbolos.existeClase(tipoDeClaseAValidarComoSubtipo.getTokenDeDatos().getLexema());

        Clase ancestro = claseAComprobarSubtipo;
        if (tipoDeAncestroABuscar.getTokenDeDatos().getLexema().equals("Object"))
            return true;
        else {
            while (!ancestro.getTokenDeDatos().getLexema().equals("Object")) {
                if (ancestro.getTokenDeDatos().getLexema().equals(tipoDeAncestroABuscar.getTokenDeDatos().getLexema()))
                    return true;
                ancestro = TablaDeSimbolos.existeClase(ancestro.getHeredaDe().getLexema());
            }
        }
        return false;
    }

}
