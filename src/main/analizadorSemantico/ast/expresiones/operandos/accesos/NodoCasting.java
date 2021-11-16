package main.analizadorSemantico.ast.expresiones.operandos.accesos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoReferencia;

public class NodoCasting extends NodoAcceso{

    private NodoPrimario nodoPrimario;

    public NodoCasting(Token tokenDeDatos){
        this.tokenDeDatos = tokenDeDatos;
    }

    public void setPrimario(NodoPrimario nodoPrimario){
        this.nodoPrimario = nodoPrimario;
    }

    public Tipo chequear() throws ExcepcionSemantica {
        if(TablaDeSimbolos.existeClase(tokenDeDatos.getLexema()) == null){
            throw new ExcepcionSemantica(tokenDeDatos, "El tipo tipoClase del casting no corresponde a una clase declarada.");
        }
        Tipo tipoAcceso = nodoPrimario.chequear();
        Tipo tipoCasting = new TipoReferencia(tokenDeDatos);
        if(!tipoCasting.conforma(tipoAcceso)){
            throw new ExcepcionSemantica(tokenDeDatos, "El tipo tipoClase del casting no conforma con el tipo del acceso.");
        }
        return tipoCasting;
    }

    public void imprimir(){
        System.out.print("(" + tokenDeDatos.getLexema() + ")");
        nodoPrimario.imprimir();
    }

    public boolean esLlamada() {
        return nodoPrimario.esLlamada();
    }

    public boolean esVariable() {
        return nodoPrimario.esLlamada();
    }

    public void generarCodigo() {
        //TODO
    }

    public boolean tieneValorDeRetorno() {
        return false;
    }
}
