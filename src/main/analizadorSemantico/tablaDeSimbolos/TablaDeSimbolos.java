package main.analizadorSemantico.tablaDeSimbolos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.ast.sentencias.NodoBloque;
import main.analizadorSemantico.ast.expresiones.operandos.accesos.NodoConArgs;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.tipos.*;
import main.analizadorSemantico.tablaDeSimbolos.unidades.Constructor;
import main.analizadorSemantico.tablaDeSimbolos.unidades.Metodo;
import main.analizadorSemantico.tablaDeSimbolos.unidades.Unidad;
import main.analizadorSemantico.tablaDeSimbolos.variables.Atributo;
import main.analizadorSemantico.tablaDeSimbolos.variables.Parametro;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public final class TablaDeSimbolos {

    private static TablaDeSimbolos tablaDeSimbolos = null;

    private static Clase claseActual;
    private static Unidad unidadActual;
    private static Stack<NodoConArgs> nodosConArgsActual;
    private static Stack<NodoBloque> bloques;
    private static HashMap<String, Clase> clases;

    private static boolean seEncontroMain;

    private static ArrayList<String> instrucciones;
    private static String nombreDeClaseConMain;

    private TablaDeSimbolos() {
        clases = new HashMap<String, Clase>();
        nodosConArgsActual = new Stack<>();
        inicializarObject();
        inicializarSystem();
        seEncontroMain = false;
        bloques = new Stack<>();
        instrucciones = new ArrayList<>();
    }

    private static void inicializarObject() {
        Clase object = new Clase(new Token("Object", "Object", 0));
        object.setConsolidacion(true);
        Metodo debugPrint = new Metodo(new Token("idMetVar", "debugPrint", 0), new TipoVoid(), "static", object.getTokenDeDatos().getLexema());
        Parametro i = new Parametro(new Token("idMetVar", "i", 0), new TipoEntero());
        debugPrint.insertarParametro(i);
        debugPrint.setBloque(new NodoBloque());
        debugPrint.setYaGeneroCodigo(true);
        object.insertarMetodo(debugPrint);

        Unidad constructorPorDefecto = new Constructor(new Token("idClase", object.getTokenDeDatos().getLexema(), 0), object.getTokenDeDatos().getLexema());
        constructorPorDefecto.setBloque(new NodoBloque());
        object.insertarConstructor(constructorPorDefecto);

        object.setCodigoGenerado(true);
        object.setDataGenerada(true);
        clases.put("Object", object);

    }

    private static void inicializarSystem() {
        Clase system = new Clase(new Token("System", "System", 0));
        system.heredaDe(new Token("Object", "Object", 0));

        Metodo read = new Metodo(new Token("idMetVar", "read", 0), new TipoEntero(), "static", system.getTokenDeDatos().getLexema());
        read.setBloque(new NodoBloque());
        read.setYaGeneroCodigo(true);
        system.insertarMetodo(read);

        Metodo printB = new Metodo(new Token("idMetVar", "printB", 0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        Parametro b = new Parametro(new Token("idMetVar", "b", 0), new TipoBoolean());
        printB.insertarParametro(b);
        printB.setBloque(new NodoBloque());
        printB.setYaGeneroCodigo(true);
        system.insertarMetodo(printB);

        Metodo printC = new Metodo(new Token("idMetVar", "printC", 0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        Parametro c = new Parametro(new Token("idMetVar", "c", 0), new TipoChar());
        printC.insertarParametro(c);
        printC.setBloque(new NodoBloque());
        printC.setYaGeneroCodigo(true);
        system.insertarMetodo(printC);

        Metodo printI = new Metodo(new Token("idMetVar", "printI", 0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        Parametro i = new Parametro(new Token("idMetVar", "i", 0), new TipoEntero());
        printI.insertarParametro(i);
        printI.setBloque(new NodoBloque());
        printI.setYaGeneroCodigo(true);
        system.insertarMetodo(printI);

        Metodo printS = new Metodo(new Token("idMetVar", "printS", 0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        Parametro s = new Parametro(new Token("idMetVar", "s", 0), new TipoString());
        printS.insertarParametro(s);
        printS.setBloque(new NodoBloque());
        printS.setYaGeneroCodigo(true);
        system.insertarMetodo(printS);

        Metodo println = new Metodo(new Token("idMetVar", "println", 0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        println.setBloque(new NodoBloque());
        println.setYaGeneroCodigo(true);
        system.insertarMetodo(println);

        Metodo printBln = new Metodo(new Token("idMetVar", "printBln", 0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        b = new Parametro(new Token("idMetVar", "b", 0), new TipoBoolean());
        printBln.insertarParametro(b);
        printBln.setBloque(new NodoBloque());
        printBln.setYaGeneroCodigo(true);
        system.insertarMetodo(printBln);

        Metodo printCln = new Metodo(new Token("idMetVar", "printCln", 0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        c = new Parametro(new Token("idMetVar", "c", 0), new TipoChar());
        printCln.insertarParametro(c);
        printCln.setBloque(new NodoBloque());
        printCln.setYaGeneroCodigo(true);
        system.insertarMetodo(printCln);

        Metodo printIln = new Metodo(new Token("idMetVar", "printIln", 0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        i = new Parametro(new Token("idMetVar", "i", 0), new TipoEntero());
        printIln.insertarParametro(i);
        printIln.setBloque(new NodoBloque());
        printIln.setYaGeneroCodigo(true);
        system.insertarMetodo(printIln);

        Metodo printSln = new Metodo(new Token("idMetVar", "printSln", 0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        s = new Parametro(new Token("idMetVar", "s", 0), new TipoString());
        printSln.insertarParametro(s);
        printSln.setBloque(new NodoBloque());
        printSln.setYaGeneroCodigo(true);
        system.insertarMetodo(printSln);

        Unidad constructorPorDefecto = new Constructor(new Token("idClase", system.getTokenDeDatos().getLexema(), 0), system.getTokenDeDatos().getLexema());
        constructorPorDefecto.setBloque(new NodoBloque());
        system.insertarConstructor(constructorPorDefecto);

        system.setCodigoGenerado(true);
        system.setDataGenerada(true);
        clases.put("System", system);

    }

    public static TablaDeSimbolos getInstance() {
        if (tablaDeSimbolos == null)
            tablaDeSimbolos = new TablaDeSimbolos();
        return tablaDeSimbolos;
    }

    public static void restablecer() {
        clases = new HashMap<String, Clase>();
        inicializarObject();
        inicializarSystem();
        seEncontroMain = false;
    }

    public static void setNombreDeClaseConMain(String nombre) {
        nombreDeClaseConMain = nombre;
    }

    public static void insertarInstruccion(String instruccion) {
        instrucciones.add(instruccion);
    }

    public static void generarCodigo() {
        generarCodigoDeInicializacion();
        generarCodigoDeInicializacionDelHeap();
        generarCodigoSimpleMAlloc();
        generarCodigoMetodosObject();
        generarCodigoMetodosSystem();

        TablaDeSimbolos.insertarInstruccion(".DATA");
        for (Clase clase : clases.values()) {
            if (!clase.yaGeneroData())
                clase.generarData();
        }

        for (Clase clase : clases.values()) {
            if (!clase.yaGeneroCodigo())
                clase.generarCodigo();
        }
    }

    public static void escribirCodigoEnArchivo(File archivoDeSalida) {
        try {
            FileWriter writer = new FileWriter(archivoDeSalida);
            for (String instruccion : instrucciones) {
                writer.write(instruccion);
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void insertarClase(Clase nuevaClase) {
        clases.put(nuevaClase.getTokenDeDatos().getLexema(), nuevaClase);
    }

    public static Clase existeClase(String nombreDeClase) {
        if (clases.containsKey(nombreDeClase))
            return clases.get(nombreDeClase);
        else return null;
    }

    public static Stack<NodoBloque> getBloques() {
        return bloques;
    }

    public static Clase getClaseActual() {
        return claseActual;
    }

    public static void setClaseActual(Clase nuevaClaseActual) {
        claseActual = nuevaClaseActual;
    }

    public static Unidad getUnidadActual() {
        return unidadActual;
    }

    public static void setUnidadActual(Unidad nuevaUnidadActual) {
        unidadActual = nuevaUnidadActual;
    }

    public static NodoConArgs getTopeNodosConArgsActual() {
        return nodosConArgsActual.peek();
    }

    public static void pushNodoConArgsActual(NodoConArgs nuevoNodoConArgsActual) {
        nodosConArgsActual.push(nuevoNodoConArgsActual);
    }

    public static void popNodoConArgsActual() {
        nodosConArgsActual.pop();
    }

    public static void chequearDeclaraciones() throws ExcepcionSemantica {
        if (!seEncontroMain)
            throw new ExcepcionSemantica(new Token(" ", " ", 0), "Alguna clase tiene que poseer un metodo llamado main estatico sin parametros.");
        for (Clase clase : clases.values()) {
            if (!clase.getTokenDeDatos().getLexema().equals("Object") && !clase.getTokenDeDatos().getLexema().equals("System"))
                clase.estaBienDeclarada();
        }
    }

    public static void consolidar() throws ExcepcionSemantica {
        for (Clase clase : clases.values())
            if (!clase.estaConsolidada())
                clase.consolidar();
    }

    public static void chequearSentencias() throws ExcepcionSemantica {
        for (Clase clase : clases.values()) {
            TablaDeSimbolos.setClaseActual(clase);
            clase.getConstructor().chequearSentencias();
            for (Metodo metodo : clase.getMetodos().values())
                if (metodo.getDeclaradoEnClase().equals(clase.getTokenDeDatos().getLexema()))
                    metodo.chequearSentencias();
        }
    }

    public static void setSeEncontroMain(boolean value) {
        seEncontroMain = value;
    }

    public static boolean getSeEncontroMain() {
        return seEncontroMain;
    }

    public static void generarCodigoDeInicializacion() {
        instrucciones.add(".CODE");
        instrucciones.add("PUSH simple_heap_init");
        instrucciones.add("CALL");
        instrucciones.add("PUSH lmain_" + nombreDeClaseConMain);
        instrucciones.add("CALL");
        instrucciones.add("HALT");
        instrucciones.add("");
    }

    public static void generarCodigoDeInicializacionDelHeap() {
        instrucciones.add("simple_heap_init:");
        instrucciones.add("RET 0");
        instrucciones.add("");
    }

    public static void generarCodigoSimpleMAlloc() {
        instrucciones.add("simple_malloc:");
        instrucciones.add("LOADFP");
        instrucciones.add("LOADSP");
        instrucciones.add("STOREFP");
        instrucciones.add("LOADHL");
        instrucciones.add("DUP");
        instrucciones.add("PUSH 1");
        instrucciones.add("ADD");
        instrucciones.add("STORE 4");
        instrucciones.add("LOAD 3");
        instrucciones.add("ADD");
        instrucciones.add("STOREHL");
        instrucciones.add("STOREFP");
        instrucciones.add("RET 1");
        instrucciones.add("");
    }

    private static void generarCodigoMetodosObject() {
        TablaDeSimbolos.insertarInstruccion("ldebugPrint_Object:");
        TablaDeSimbolos.insertarInstruccion("LOADFP");
        TablaDeSimbolos.insertarInstruccion("LOADSP");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("LOAD 3");
        TablaDeSimbolos.insertarInstruccion("IPRINT");
        TablaDeSimbolos.insertarInstruccion("PRNLN");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("RET 1");
        TablaDeSimbolos.insertarInstruccion("");
    }

    private static void generarCodigoMetodosSystem() {
        TablaDeSimbolos.insertarInstruccion("lread_System:");
        TablaDeSimbolos.insertarInstruccion("LOADFP");
        TablaDeSimbolos.insertarInstruccion("LOADSP");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("READ");
        TablaDeSimbolos.insertarInstruccion("STORE 3");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("RET 0");
        TablaDeSimbolos.insertarInstruccion("");

        TablaDeSimbolos.insertarInstruccion("lprintB_System:");
        TablaDeSimbolos.insertarInstruccion("LOADFP");
        TablaDeSimbolos.insertarInstruccion("LOADSP");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("LOAD 3");
        TablaDeSimbolos.insertarInstruccion("BPRINT");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("RET 1");
        TablaDeSimbolos.insertarInstruccion("");

        TablaDeSimbolos.insertarInstruccion("lprintC_System:");
        TablaDeSimbolos.insertarInstruccion("LOADFP");
        TablaDeSimbolos.insertarInstruccion("LOADSP");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("LOAD 3");
        TablaDeSimbolos.insertarInstruccion("CPRINT");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("RET 1");
        TablaDeSimbolos.insertarInstruccion("");

        TablaDeSimbolos.insertarInstruccion("lprintI_System:");
        TablaDeSimbolos.insertarInstruccion("LOADFP");
        TablaDeSimbolos.insertarInstruccion("LOADSP");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("LOAD 3");
        TablaDeSimbolos.insertarInstruccion("IPRINT");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("RET 1");
        TablaDeSimbolos.insertarInstruccion("");

        TablaDeSimbolos.insertarInstruccion("lprintS_System:");
        TablaDeSimbolos.insertarInstruccion("LOADFP");
        TablaDeSimbolos.insertarInstruccion("LOADSP");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("LOAD 3");
        TablaDeSimbolos.insertarInstruccion("SPRINT");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("RET 1");
        TablaDeSimbolos.insertarInstruccion("");

        TablaDeSimbolos.insertarInstruccion("lprintBln_System:");
        TablaDeSimbolos.insertarInstruccion("LOADFP");
        TablaDeSimbolos.insertarInstruccion("LOADSP");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("LOAD 3");
        TablaDeSimbolos.insertarInstruccion("BPRINT");
        TablaDeSimbolos.insertarInstruccion("PRNLN");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("RET 1");
        TablaDeSimbolos.insertarInstruccion("");

        TablaDeSimbolos.insertarInstruccion("lprintCln_System:");
        TablaDeSimbolos.insertarInstruccion("LOADFP");
        TablaDeSimbolos.insertarInstruccion("LOADSP");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("LOAD 3");
        TablaDeSimbolos.insertarInstruccion("CPRINT");
        TablaDeSimbolos.insertarInstruccion("PRNLN");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("RET 1");
        TablaDeSimbolos.insertarInstruccion("");

        TablaDeSimbolos.insertarInstruccion("lprintIln_System:");
        TablaDeSimbolos.insertarInstruccion("LOADFP");
        TablaDeSimbolos.insertarInstruccion("LOADSP");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("LOAD 3");
        TablaDeSimbolos.insertarInstruccion("IPRINT");
        TablaDeSimbolos.insertarInstruccion("PRNLN");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("RET 1");
        TablaDeSimbolos.insertarInstruccion("");

        TablaDeSimbolos.insertarInstruccion("lprintSln_System:");
        TablaDeSimbolos.insertarInstruccion("LOADFP");
        TablaDeSimbolos.insertarInstruccion("LOADSP");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("LOAD 3");
        TablaDeSimbolos.insertarInstruccion("SPRINT");
        TablaDeSimbolos.insertarInstruccion("PRNLN");
        TablaDeSimbolos.insertarInstruccion("STOREFP");
        TablaDeSimbolos.insertarInstruccion("RET 1");
        TablaDeSimbolos.insertarInstruccion("");
    }
}
