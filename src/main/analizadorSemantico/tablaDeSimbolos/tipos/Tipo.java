package main.analizadorSemantico.tablaDeSimbolos.tipos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.compatibilidadDeTipos.Compatibilidad;

public abstract class Tipo {

    public abstract Token getTokenDeDatos();

    public abstract boolean esTipoClase();

    public abstract boolean conforma(Tipo tipo);

    protected abstract boolean compatibilidad(Compatibilidad compatibilidad);

}
