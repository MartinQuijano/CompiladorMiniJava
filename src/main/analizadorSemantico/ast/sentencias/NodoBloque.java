package main.analizadorSemantico.ast.sentencias;

import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.variables.VarLocal;

import java.util.ArrayList;

public class NodoBloque extends NodoSentencia {

    protected ArrayList<NodoSentencia> sentencias;
    protected ArrayList<VarLocal> varLocalesInsertadasABorrar;

    public NodoBloque(){
        sentencias = new ArrayList<>();
        varLocalesInsertadasABorrar = new ArrayList<>();
    }

    public void insertarSentencia(NodoSentencia nodoSentencia){
        sentencias.add(nodoSentencia);
    }

    public void chequear() throws ExcepcionSemantica {
        TablaDeSimbolos.getBloques().push(this);
        for(NodoSentencia sentencia : sentencias)
            sentencia.chequear();
        for(VarLocal varLocal : varLocalesInsertadasABorrar)
            TablaDeSimbolos.getUnidadActual().eliminarVarLocal(varLocal);
        TablaDeSimbolos.getBloques().pop();
    }

    public void insertarVarLocal(VarLocal varLocal){
        varLocalesInsertadasABorrar.add(varLocal);
    }

    public void imprimir(){
        System.out.println("{");
        for (NodoSentencia sentencia : sentencias) {
            sentencia.imprimir();
        }
        System.out.println();
        System.out.println("}");
        System.out.println();
    }
}
