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

    private TablaDeSimbolos(){
        clases = new HashMap<String, Clase>();
        nodosConArgsActual = new Stack<>();
        inicializarObject();
        inicializarSystem();
        seEncontroMain = false;
        bloques = new Stack<>();
    }

    private static void inicializarObject(){
        Clase object = new Clase(new Token("Object", "Object", 0));
        object.setConsolidacion(true);
        Metodo debugPrint = new Metodo(new Token("idMetVar", "debugPrint",0), new TipoVoid(), "static", object.getTokenDeDatos().getLexema());
        debugPrint.setBloque(new NodoBloque());
        object.insertarMetodo(debugPrint);

        Unidad constructorPorDefecto = new Constructor(new Token("idClase", object.getTokenDeDatos().getLexema(),0));
        constructorPorDefecto.setBloque(new NodoBloque());
        object.insertarConstructor(constructorPorDefecto);

        clases.put("Object", object);
    }

    private static void inicializarSystem(){
        Clase system = new Clase(new Token("System", "System", 0));
        system.heredaDe(new Token("Object", "Object", 0));

        Metodo read = new Metodo(new Token("idMetVar", "read",0), new TipoEntero(), "static", system.getTokenDeDatos().getLexema());
        read.setBloque(new NodoBloque());
        system.insertarMetodo(read);

        Metodo printB = new Metodo(new Token("idMetVar", "printB",0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        Parametro b = new Parametro(new Token("idMetVar", "b", 0), new TipoBoolean());
        printB.insertarParametro(b);
        printB.setBloque(new NodoBloque());
        system.insertarMetodo(printB);

        Metodo printC = new Metodo(new Token("idMetVar", "printC",0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        Parametro c = new Parametro(new Token("idMetVar", "c", 0), new TipoChar());
        printC.insertarParametro(c);
        printC.setBloque(new NodoBloque());
        system.insertarMetodo(printC);

        Metodo printI = new Metodo(new Token("idMetVar", "printI",0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        Parametro i = new Parametro(new Token("idMetVar", "i", 0), new TipoEntero());
        printI.insertarParametro(i);
        printI.setBloque(new NodoBloque());
        system.insertarMetodo(printI);

        Metodo printS = new Metodo(new Token("idMetVar", "printS",0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        Parametro s = new Parametro(new Token("idMetVar", "s", 0), new TipoString());
        printS.insertarParametro(s);
        printS.setBloque(new NodoBloque());
        system.insertarMetodo(printS);

        Metodo println = new Metodo(new Token("idMetVar", "println",0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        println.setBloque(new NodoBloque());
        system.insertarMetodo(println);

        Metodo printBln = new Metodo(new Token("idMetVar", "printBln",0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        b = new Parametro(new Token("idMetVar", "b", 0), new TipoBoolean());
        printBln.insertarParametro(b);
        printBln.setBloque(new NodoBloque());
        system.insertarMetodo(printBln);

        Metodo printCln = new Metodo(new Token("idMetVar", "printCln",0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        c = new Parametro(new Token("idMetVar", "c", 0), new TipoChar());
        printCln.insertarParametro(c);
        printCln.setBloque(new NodoBloque());
        system.insertarMetodo(printCln);

        Metodo printIln = new Metodo(new Token("idMetVar", "printIln",0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        i = new Parametro(new Token("idMetVar", "i", 0), new TipoEntero());
        printIln.insertarParametro(i);
        printIln.setBloque(new NodoBloque());
        system.insertarMetodo(printIln);

        Metodo printSln = new Metodo(new Token("idMetVar", "printSln",0), new TipoVoid(), "static", system.getTokenDeDatos().getLexema());
        s = new Parametro(new Token("idMetVar", "s", 0), new TipoString());
        printSln.insertarParametro(s);
        printSln.setBloque(new NodoBloque());
        system.insertarMetodo(printSln);

        Unidad constructorPorDefecto = new Constructor(new Token("idClase", system.getTokenDeDatos().getLexema(),0));
        constructorPorDefecto.setBloque(new NodoBloque());
        system.insertarConstructor(constructorPorDefecto);

        clases.put("System", system);

    }

    public static TablaDeSimbolos getInstance(){
        if(tablaDeSimbolos == null)
            tablaDeSimbolos = new TablaDeSimbolos();
        return tablaDeSimbolos;
    }

    public static void  restablecer(){
        clases = new HashMap<String, Clase>();
        inicializarObject();
        inicializarSystem();
        seEncontroMain = false;
    }

    public static void insertarClase(Clase nuevaClase){
        clases.put(nuevaClase.getTokenDeDatos().getLexema(), nuevaClase);
    }

    public static Clase existeClase(String nombreDeClase){
        if(clases.containsKey(nombreDeClase))
            return clases.get(nombreDeClase);
        else return null;
    }

    public static Stack<NodoBloque> getBloques(){
        return bloques;
    }

    public static Clase getClaseActual(){
        return claseActual;
    }

    public static void setClaseActual(Clase nuevaClaseActual){
        claseActual = nuevaClaseActual;
    }

    public static Unidad getUnidadActual(){
        return unidadActual;
    }

    public static void setUnidadActual(Unidad nuevaUnidadActual){
        unidadActual = nuevaUnidadActual;
    }

    public static NodoConArgs getTopeNodosConArgsActual(){
        return nodosConArgsActual.peek();
    }

    public static void pushNodoConArgsActual(NodoConArgs nuevoNodoConArgsActual){
        nodosConArgsActual.push(nuevoNodoConArgsActual);
    }

    public static void popNodoConArgsActual(){
        nodosConArgsActual.pop();
    }

    public static void chequearDeclaraciones() throws ExcepcionSemantica {
        if(!seEncontroMain)
            throw new ExcepcionSemantica(new Token(" ", " ", 0), "Alguna clase tiene que poseer un metodo llamado main estatico sin parametros.");
        for(Clase clase : clases.values()) {
            if (!clase.getTokenDeDatos().getLexema().equals("Object") && !clase.getTokenDeDatos().getLexema().equals("System"))
                clase.estaBienDeclarada();
        }
    }

    public static void consolidar() throws ExcepcionSemantica {
        for(Clase clase : clases.values())
            if(!clase.estaConsolidada())
                clase.consolidar();
    }

    public static void chequearSentencias() throws ExcepcionSemantica {
        for(Clase clase : clases.values()){
            TablaDeSimbolos.setClaseActual(clase);
            clase.getConstructor().chequearSentencias();
            for(Metodo metodo : clase.getMetodos().values())
                if(metodo.getDeclaradoEnClase().equals(clase.getTokenDeDatos().getLexema()))
                    metodo.chequearSentencias();
        }
    }

    public static void setSeEncontroMain(boolean value){
        seEncontroMain = value;
    }

    public static boolean getSeEncontroMain(){
        return seEncontroMain;
    }

    public static void mostrarTabla(){
        for(Clase clase : clases.values()){
            System.out.println();
            System.out.println(clase.getTokenDeDatos().getLexema());
            for(Atributo atributo : clase.getAtributos().values()){
                Token tokenDeAtributo = atributo.getTokenDeDatos();
                System.out.println("    " + atributo.getVisibilidad() + " " + atributo.getTipo().getTokenDeDatos().getLexema() + " " + tokenDeAtributo.getLexema() + ";");
            }
            System.out.println();
            System.out.print("    " + clase.getConstructor().getTokenDeDatos().getLexema() + "(");
            int cantidadParametros = clase.getConstructor().getParametros().size();
            int contadorParametrosProcesados = 0;
            for(Parametro parametro : clase.getConstructor().getParametros()){
                contadorParametrosProcesados++;
                System.out.print(parametro.getTipo().getTokenDeDatos().getLexema() + " " + parametro.getTokenDeDatos().getLexema());
                if(contadorParametrosProcesados < cantidadParametros)
                    System.out.print(", ");
            }
            System.out.println(")");
            clase.getConstructor().getBloque().imprimir();
            for(Metodo metodo : clase.getMetodos().values()){
                System.out.print("    " + metodo.getForma() + " " + metodo.getTipo().getTokenDeDatos().getLexema() + " " + metodo.getTokenDeDatos().getLexema() + "(");
                cantidadParametros = metodo.getParametros().size();
                contadorParametrosProcesados = 0;
                for(Parametro parametro : metodo.getParametros()){
                    contadorParametrosProcesados++;
                    System.out.print(parametro.getTipo().getTokenDeDatos().getLexema() + " " + parametro.getTokenDeDatos().getLexema());
                    if(contadorParametrosProcesados < cantidadParametros)
                        System.out.print(", ");
                }
                System.out.println(")");
                metodo.getBloque().imprimir();
            }
        }
    }

}
