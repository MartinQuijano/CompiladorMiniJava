package main.analizadorSemantico.tablaDeSimbolos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoReferencia;
import main.analizadorSemantico.tablaDeSimbolos.unidades.Metodo;
import main.analizadorSemantico.tablaDeSimbolos.unidades.Unidad;
import main.analizadorSemantico.tablaDeSimbolos.variables.Atributo;
import main.analizadorSemantico.tablaDeSimbolos.variables.Parametro;

import java.util.ArrayList;
import java.util.HashMap;

public class Clase {

    private Token tokenDeDatos;
    private Token heredaDe;
    private Unidad constructor;
    private boolean consolidada;
    private Tipo tipo;

    private HashMap<String, Atributo> atributos;
    private HashMap<String, Metodo> metodos;

    private boolean tieneMetodosDinamicos;
    private int ultimoOffsetVT;
    private int ultimoOffsetAtributo;
    private ArrayList<Metodo> metodosEnOrdenParaOffset;
    private ArrayList<Atributo> atributosEnOrdenParaOffset;
    private boolean codigoGenerado;
    private boolean dataGenerada;

    public Clase(Token tokenDeDatos) {
        this.tokenDeDatos = tokenDeDatos;
        atributos = new HashMap<String, Atributo>();
        metodos = new HashMap<String, Metodo>();
        heredaDe = null;
        consolidada = false;
        constructor = null;
        tipo = new TipoReferencia(tokenDeDatos);
        tieneMetodosDinamicos = false;
        ultimoOffsetVT = 0;
        ultimoOffsetAtributo = 1;
        metodosEnOrdenParaOffset = new ArrayList<>();
        atributosEnOrdenParaOffset = new ArrayList<>();
        codigoGenerado = false;
        dataGenerada = false;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void heredaDe(Token clasePadre) {
        heredaDe = clasePadre;
    }

    public Token getHeredaDe() {
        return heredaDe;
    }

    public Token getTokenDeDatos() {
        return tokenDeDatos;
    }

    public void setCodigoGenerado(boolean value) {
        codigoGenerado = value;
    }

    public void setDataGenerada(boolean value) {
        dataGenerada = value;
    }

    public void generarCodigo() {
        Clase claseAncestro = TablaDeSimbolos.existeClase(heredaDe.getLexema());
        if (claseAncestro != null)
            if (!claseAncestro.yaGeneroCodigo()) {
                claseAncestro.generarCodigo();
            }
        generarCode();

        codigoGenerado = true;
    }

    private void generarCode() {
        TablaDeSimbolos.insertarInstruccion(".CODE");

        constructor.generarCodigo();
        for (Metodo metodo : metodos.values()) {
            if (!metodo.yaGeneroCodigo()) {
                metodo.generarCodigo();
            }
        }

        TablaDeSimbolos.insertarInstruccion("");
    }

    public void generarData() {
        Clase claseAncestro = TablaDeSimbolos.existeClase(heredaDe.getLexema());
        if (claseAncestro != null)
            if (!claseAncestro.yaGeneroData()) {
                claseAncestro.generarData();
            }

        String virtualTableDW = "VT_" + tokenDeDatos.getLexema() + ":";
        if (tieneMetodosDinamicos) {
            virtualTableDW += " DW ";
            virtualTableDW = generarDWDeLosMetodosDeLosAncestros(virtualTableDW);
            for (Metodo metodo : metodos.values()) {
                if (metodo.getForma().equals("dynamic")) {
                    if (!metodo.seLeAsignoOffset()) {
                        metodo.setOffset(ultimoOffsetVT);
                        ultimoOffsetVT++;
                        metodosEnOrdenParaOffset.add(metodo);
                        virtualTableDW += "l" + metodo.getTokenDeDatos().getLexema() + "_" + tokenDeDatos.getLexema() + ", ";
                    }
                }
            }
            virtualTableDW = virtualTableDW.substring(0, virtualTableDW.length() - 2);
        } else {
            virtualTableDW += " NOP";
        }

        TablaDeSimbolos.insertarInstruccion(virtualTableDW);
        TablaDeSimbolos.insertarInstruccion("");

        dataGenerada = true;
    }

    public ArrayList<Metodo> getMetodosEnOrdenParaOffset() {
        return metodosEnOrdenParaOffset;
    }

    public ArrayList<Atributo> getAtributosEnOrdenParaOffset() {
        return atributosEnOrdenParaOffset;
    }

    private String generarDWDeLosMetodosDeLosAncestros(String virtualTableDW) {
        Clase claseAncestro = TablaDeSimbolos.existeClase(heredaDe.getLexema());

        for (Metodo metodo : claseAncestro.getMetodosEnOrdenParaOffset()) {
            Metodo metodoYaExistente = existeMetodo(metodo.getTokenDeDatos().getLexema());

            boolean noEsUnMetodoDeLaMismaClase = (!metodo.getDeclaradoEnClase().equals(metodoYaExistente.getDeclaradoEnClase()));

            if (noEsUnMetodoDeLaMismaClase) {
                metodoYaExistente.setOffset(ultimoOffsetVT);
                ultimoOffsetVT++;
                metodoYaExistente.setSeLeAsignoOffset(true);
                metodosEnOrdenParaOffset.add(metodoYaExistente);
                virtualTableDW += "l" + metodoYaExistente.getTokenDeDatos().getLexema() + "_" + metodoYaExistente.getDeclaradoEnClase() + ", ";
            } else {
                metodoYaExistente.setOffset(ultimoOffsetVT);
                ultimoOffsetVT++;
                metodoYaExistente.setSeLeAsignoOffset(true);
                metodosEnOrdenParaOffset.add(metodo);
                virtualTableDW += "l" + metodoYaExistente.getTokenDeDatos().getLexema() + "_" + metodo.getDeclaradoEnClase() + ", ";
            }

        }
        return virtualTableDW;
    }

    public boolean yaGeneroCodigo() {
        return codigoGenerado;
    }

    public boolean yaGeneroData() {
        return dataGenerada;
    }

    public Atributo existeAtributo(String nombreDeAtributo) {
        if (atributos.containsKey(nombreDeAtributo))
            return atributos.get(nombreDeAtributo);
        else
            return null;
    }

    public Metodo existeMetodo(String nombreDeMetodo) {
        if (metodos.containsKey(nombreDeMetodo))
            return metodos.get(nombreDeMetodo);
        else
            return null;
    }

    public void insertarAtributo(Atributo atributoAInsertar) {
        atributos.put(atributoAInsertar.getTokenDeDatos().getLexema(), atributoAInsertar);
    }

    public void insertarAtributoDeAncestro(Atributo atributoAInsertar, String nombreAncestro) {
        atributos.put(nombreAncestro + "." + atributoAInsertar.getTokenDeDatos().getLexema(), atributoAInsertar);
    }

    public void insertarConstructor(Unidad constructor) {
        this.constructor = constructor;
    }

    public void setTieneMetodosDinamicos(boolean value) {
        tieneMetodosDinamicos = value;
    }

    public void insertarMetodo(Metodo metodoAInsertar) {
        metodos.put(metodoAInsertar.getTokenDeDatos().getLexema(), metodoAInsertar);
    }

    public HashMap<String, Atributo> getAtributos() {
        return atributos;
    }

    public Unidad getConstructor() {
        return constructor;
    }

    public HashMap<String, Metodo> getMetodos() {
        return metodos;
    }

    public void estaBienDeclarada() throws ExcepcionSemantica {
        if (TablaDeSimbolos.existeClase(heredaDe.getLexema()) == null)
            throw new ExcepcionSemantica(heredaDe, "La clase '" + heredaDe.getLexema() + "' de la que intenta heredar '" + tokenDeDatos.getLexema() + "' no esta declarada.");
        comprobarHerenciaCircular();
        for (Atributo atributo : atributos.values()) {
            atributo.estaBienDeclarado();
        }
        if (constructor != null)
            constructor.estaBienDeclarada();
        for (Metodo metodo : metodos.values()) {
            metodo.estaBienDeclarada();
        }
    }

    public void comprobarHerenciaCircular() throws ExcepcionSemantica {
        Clase ancestro = TablaDeSimbolos.existeClase(heredaDe.getLexema());
        while (!ancestro.getTokenDeDatos().getLexema().equals("Object")) {
            if (ancestro.getTokenDeDatos().getLexema().equals(tokenDeDatos.getLexema()))
                throw new ExcepcionSemantica(tokenDeDatos, "Un ancestro de la clase '" + tokenDeDatos.getLexema() + "' hereda de la misma, provocando una herencia circular.");
            ancestro = TablaDeSimbolos.existeClase(ancestro.getHeredaDe().getLexema());
        }
    }

    public boolean estaConsolidada() {
        return consolidada;
    }

    public void setConsolidacion(boolean value) {
        consolidada = value;
    }

    public void consolidar() throws ExcepcionSemantica {
        Clase claseAncestro = TablaDeSimbolos.existeClase(heredaDe.getLexema());
        if (!claseAncestro.estaConsolidada())
            claseAncestro.consolidar();
        consolidarAtributos();
        consolidarMetodos();
        consolidada = true;
        establecerOffsetDeLosAtributos();

    }

    private void establecerOffsetDeLosAtributos() {
        Clase claseAncestro = TablaDeSimbolos.existeClase(heredaDe.getLexema());
        for (Atributo atributo : claseAncestro.atributosEnOrdenParaOffset) {
            ultimoOffsetAtributo++;
            atributosEnOrdenParaOffset.add(atributo);
        }
        for (Atributo atributo : atributos.values()) {
            if (!atributo.seLeAsignoOffset()) {
                atributo.setOffset(ultimoOffsetAtributo);
                ultimoOffsetAtributo++;
                atributosEnOrdenParaOffset.add(atributo);
                atributo.setSeLeAsignoOffset(true);
            }
        }
    }

    private void consolidarAtributos() {
        Clase claseAncestro = TablaDeSimbolos.existeClase(heredaDe.getLexema());
        for (Atributo atributo : claseAncestro.getAtributos().values()) {
            Atributo atributoYaExistente = existeAtributo(atributo.getTokenDeDatos().getLexema());
            if (atributoYaExistente != null)
                insertarAtributoDeAncestro(atributo, claseAncestro.getTokenDeDatos().getLexema());
            else {
                if (atributo.getVisibilidad().equals("private"))
                    insertarAtributoDeAncestroPrivado(atributo, claseAncestro.getTokenDeDatos().getLexema());
                else
                    insertarAtributo(atributo);
            }
        }
    }

    private void insertarAtributoDeAncestroPrivado(Atributo atributoAInsertar, String nombreAncestro) {
        atributos.put("-" + nombreAncestro + "." + atributoAInsertar.getTokenDeDatos().getLexema(), atributoAInsertar);
    }

    private void consolidarMetodos() throws ExcepcionSemantica {
        Clase claseAncestro = TablaDeSimbolos.existeClase(heredaDe.getLexema());
        for (Metodo metodo : claseAncestro.getMetodos().values()) {
            Metodo metodoYaExistente = existeMetodo(metodo.getTokenDeDatos().getLexema());
            if (metodoYaExistente != null) {
                if (!tienenMismaSignatura(metodoYaExistente, metodo)) {
                    throw new ExcepcionSemantica(metodoYaExistente.getTokenDeDatos(), "El metodo '" + metodoYaExistente.getTokenDeDatos().getLexema() + "' ya esta declarado en una clase ancestro y las signaturas no coinciden.");
                }
            } else {
                if (metodo.getForma().equals("dynamic")) {
                    tieneMetodosDinamicos = true;
                }
                insertarMetodo(metodo);
            }
        }
    }

    private boolean tienenMismaSignatura(Metodo metodoDeLaClase, Metodo metodoDeAncestro) {
        if (!metodoDeLaClase.getForma().equals(metodoDeAncestro.getForma()))
            return false;
        if (!metodoDeLaClase.getTipo().getTokenDeDatos().getLexema().equals(metodoDeAncestro.getTipo().getTokenDeDatos().getLexema()))
            return false;
        if (metodoDeLaClase.getParametros().size() != metodoDeAncestro.getParametros().size())
            return false;
        int indiceParametros = 0;
        for (Parametro parametro : metodoDeLaClase.getParametros()) {
            if (!coincidenLosParametros(parametro, metodoDeAncestro.getParametros().get(indiceParametros)))
                return false;
            indiceParametros++;
        }
        return true;
    }

    private boolean coincidenLosParametros(Parametro parametroClase, Parametro parametroAncestro) {
        if (!parametroClase.getTipo().getTokenDeDatos().getLexema().equals(parametroAncestro.getTipo().getTokenDeDatos().getLexema()))
            return false;
        return true;
    }
}
