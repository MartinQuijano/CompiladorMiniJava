package main.analizadorSemantico.tablaDeSimbolos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Unidad {

    protected Token tokenDeDatos;
    protected HashMap<String, Parametro> parametros;
    protected ArrayList<Parametro> parametrosEnOrden;

    public Unidad(){
        parametros = new HashMap<String, Parametro>();
        parametrosEnOrden = new ArrayList<Parametro>();
    }

    public Token getTokenDeDatos() {
        return tokenDeDatos;
    }

    public Parametro existeParametro(String nombreParametro){
        if(parametros.containsKey(nombreParametro))
            return parametros.get(nombreParametro);
        else
            return null;
    }

    public void insertarParametro(Parametro parametroAInsertar){
        parametros.put(parametroAInsertar.getTokenDeDatos().getLexema(), parametroAInsertar);
        parametrosEnOrden.add(parametroAInsertar);
    }

    public List<Parametro> getParametros(){
        return parametrosEnOrden;
    }

    public void estaBienDeclarada() throws ExcepcionSemantica {
        for(Parametro parametro : parametros.values()){
            parametro.estaBienDeclarado();
        }
    }
}