package main.analizadorSemantico.tablaDeSimbolos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;

import java.util.HashMap;

public class Clase {

    private Token tokenDeDatos;
    private Token heredaDe;
    private Unidad constructor;
    private boolean consolidada;

    private HashMap<String, Atributo> atributos;
    private HashMap<String, Metodo> metodos;

    public Clase(Token tokenDeDatos){
        this.tokenDeDatos = tokenDeDatos;
        atributos = new HashMap<String, Atributo>();
        metodos = new HashMap<String, Metodo>();
        heredaDe = null;
        consolidada = false;
        constructor = null;
    }

    public void heredaDe(Token clasePadre){
        heredaDe = clasePadre;
    }

    public Token getHeredaDe(){
        return heredaDe;
    }

    public Token getTokenDeDatos(){
        return tokenDeDatos;
    }

    public Atributo existeAtributo(String nombreDeAtributo){
        if(atributos.containsKey(nombreDeAtributo))
            return atributos.get(nombreDeAtributo);
        else
            return null;
    }

    public Metodo existeMetodo(String nombreDeMetodo){
        if(metodos.containsKey(nombreDeMetodo))
            return metodos.get(nombreDeMetodo);
        else
            return null;
    }

    public void insertarAtributo(Atributo atributoAInsertar){
        atributos.put(atributoAInsertar.getTokenDeDatos().getLexema(), atributoAInsertar);
    }

    public void insertarAtributoDeAncestro(Atributo atributoAInsertar, String nombreAncestro){
        atributos.put(nombreAncestro + "." + atributoAInsertar.getTokenDeDatos().getLexema(), atributoAInsertar);
    }

    public void insertarConstructor(Unidad constructor){
        this.constructor = constructor;
    }

    public void insertarMetodo(Metodo metodoAInsertar){
        metodos.put(metodoAInsertar.getTokenDeDatos().getLexema(), metodoAInsertar);
    }

    public HashMap<String, Atributo> getAtributos(){
        return atributos;
    }

    public Unidad getConstructor(){
        return constructor;
    }

    public HashMap<String, Metodo> getMetodos(){
        return metodos;
    }

    public void estaBienDeclarada() throws ExcepcionSemantica {
        if(TablaDeSimbolos.existeClase(heredaDe.getLexema()) == null)
            throw new ExcepcionSemantica(heredaDe, "La clase '" + heredaDe.getLexema() + "' de la que intenta heredar '" + tokenDeDatos.getLexema() + "' no esta declarada.");
        comprobarHerenciaCircular();
        for(Atributo atributo : atributos.values()){
            atributo.estaBienDeclarado();
        }
        if(constructor != null)
            constructor.estaBienDeclarada();
        for(Metodo metodo : metodos.values()){
            metodo.estaBienDeclarada();
        }
    }

    public void comprobarHerenciaCircular() throws ExcepcionSemantica {
        Clase ancestro = TablaDeSimbolos.existeClase(heredaDe.getLexema());
        while (!ancestro.getTokenDeDatos().getLexema().equals("Object")){
            if(ancestro.getTokenDeDatos().getLexema().equals(tokenDeDatos.getLexema()))
                throw new ExcepcionSemantica(tokenDeDatos, "Un ancestro de la clase '" + tokenDeDatos.getLexema() + "' hereda de la misma, provocando una herencia circular.");
            ancestro = TablaDeSimbolos.existeClase(ancestro.getHeredaDe().getLexema());
        }
    }

    public boolean estaConsolidada(){
        return consolidada;
    }

    public void setConsolidacion(boolean value){
        consolidada = value;
    }

    public void consolidar() throws ExcepcionSemantica {
        Clase claseAncestro = TablaDeSimbolos.existeClase(heredaDe.getLexema());
        if(!claseAncestro.estaConsolidada())
            claseAncestro.consolidar();
        consolidarAtributos();
        consolidarMetodos();
        consolidada = true;
    }

    private void consolidarAtributos(){
        Clase claseAncestro = TablaDeSimbolos.existeClase(heredaDe.getLexema());
        for(Atributo atributo : claseAncestro.getAtributos().values()){
            Atributo atributoYaExistente = existeAtributo(atributo.getTokenDeDatos().getLexema());
            if(atributoYaExistente != null)
                insertarAtributoDeAncestro(atributo, claseAncestro.getTokenDeDatos().getLexema());
            else
                insertarAtributo(atributo);
        }
    }

    private void consolidarMetodos() throws ExcepcionSemantica {
        Clase claseAncestro = TablaDeSimbolos.existeClase(heredaDe.getLexema());
        for(Metodo metodo : claseAncestro.getMetodos().values()){
            Metodo metodoYaExistente = existeMetodo(metodo.getTokenDeDatos().getLexema());
            if(metodoYaExistente != null){
                if(!tienenMismaSignatura(metodoYaExistente, metodo)){
                    throw new ExcepcionSemantica(metodoYaExistente.getTokenDeDatos(), "El metodo '" + metodoYaExistente.getTokenDeDatos().getLexema() + "' ya esta declarado en una clase ancestro y las signaturas no coinciden.");
                }
            } else {
                insertarMetodo(metodo);
            }
        }
    }

    private boolean tienenMismaSignatura(Metodo metodoDeLaClase, Metodo metodoDeAncestro){
        if(!metodoDeLaClase.getForma().equals(metodoDeAncestro.getForma()))
            return false;
        if(!metodoDeLaClase.getTipo().getTokenDeDatos().getLexema().equals(metodoDeAncestro.getTipo().getTokenDeDatos().getLexema()))
            return false;
        if(metodoDeLaClase.getParametros().size() != metodoDeAncestro.getParametros().size())
            return false;
        int indiceParametros = 0;
        for(Parametro parametro : metodoDeLaClase.getParametros()){
            if(!coincidenLosParametros(parametro, metodoDeAncestro.getParametros().get(indiceParametros)))
                return false;
            indiceParametros++;
        }
        return true;
    }

    private boolean coincidenLosParametros(Parametro parametroClase, Parametro parametroAncestro){
        if(!parametroClase.getTipo().getTokenDeDatos().getLexema().equals(parametroAncestro.getTipo().getTokenDeDatos().getLexema()))
            return false;
        if(!parametroClase.getTokenDeDatos().getLexema().equals(parametroAncestro.getTokenDeDatos().getLexema()))
            return false;
        return true;
    }
}
