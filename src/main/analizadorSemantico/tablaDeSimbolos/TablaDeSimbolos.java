package main.analizadorSemantico.tablaDeSimbolos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;

import java.util.HashMap;

public final class TablaDeSimbolos {

    private static TablaDeSimbolos tablaDeSimbolos = null;

    private static Clase claseActual;
    private static Unidad unidadActual;
    private static HashMap<String, Clase> clases;

    private static boolean seEncontroMain;

    private TablaDeSimbolos(){
        clases = new HashMap<String, Clase>();
        inicializarObject();
        inicializarSystem();
        seEncontroMain = false;
    }

    private static void inicializarObject(){
        Clase object = new Clase(new Token("Object", "Object", 0));
        object.setConsolidacion(true);
        Metodo debugPrint = new Metodo(new Token("idMetVar", "debugPrint",0), new TipoVoid(), "static");
        object.insertarMetodo(debugPrint);
        clases.put("Object", object);
    }

    private static void inicializarSystem(){
        Clase system = new Clase(new Token("System", "System", 0));
        system.heredaDe(new Token("Object", "Object", 0));
        system.setConsolidacion(true);

        Metodo read = new Metodo(new Token("idMetVar", "read",0), new TipoEntero(), "static");
        system.insertarMetodo(read);

        Metodo printB = new Metodo(new Token("idMetVar", "printB",0), new TipoVoid(), "static");
        Parametro b = new Parametro(new Token("idMetVar", "b", 0), new TipoBoolean());
        printB.insertarParametro(b);
        system.insertarMetodo(printB);

        Metodo printC = new Metodo(new Token("idMetVar", "printC",0), new TipoVoid(), "static");
        Parametro c = new Parametro(new Token("idMetVar", "c", 0), new TipoChar());
        printC.insertarParametro(c);
        system.insertarMetodo(printC);

        Metodo printI = new Metodo(new Token("idMetVar", "printI",0), new TipoVoid(), "static");
        Parametro i = new Parametro(new Token("idMetVar", "i", 0), new TipoEntero());
        printI.insertarParametro(i);
        system.insertarMetodo(printI);

        Metodo printS = new Metodo(new Token("idMetVar", "printS",0), new TipoVoid(), "static");
        Parametro s = new Parametro(new Token("idMetVar", "s", 0), new TipoString());
        printS.insertarParametro(s);
        system.insertarMetodo(printS);

        Metodo println = new Metodo(new Token("idMetVar", "println",0), new TipoVoid(), "static");
        system.insertarMetodo(println);

        Metodo printBln = new Metodo(new Token("idMetVar", "printBln",0), new TipoVoid(), "static");
        b = new Parametro(new Token("idMetVar", "b", 0), new TipoBoolean());
        printBln.insertarParametro(b);
        system.insertarMetodo(printBln);

        Metodo printCln = new Metodo(new Token("idMetVar", "printCln",0), new TipoVoid(), "static");
        c = new Parametro(new Token("idMetVar", "c", 0), new TipoChar());
        printCln.insertarParametro(c);
        system.insertarMetodo(printCln);

        Metodo printIln = new Metodo(new Token("idMetVar", "printIln",0), new TipoVoid(), "static");
        i = new Parametro(new Token("idMetVar", "i", 0), new TipoEntero());
        printIln.insertarParametro(i);
        system.insertarMetodo(printIln);

        Metodo printSln = new Metodo(new Token("idMetVar", "printSln",0), new TipoVoid(), "static");
        s = new Parametro(new Token("idMetVar", "s", 0), new TipoString());
        printSln.insertarParametro(s);
        system.insertarMetodo(printSln);

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

    public static void comprobarDeclaraciones() throws ExcepcionSemantica {
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
            for(Metodo metodo : clase.getMetodos().values()){
                System.out.print("    " + metodo.getForma() + " " + metodo.getTipo().getTokenDeDatos().getLexema() + " " + metodo.getTokenDeDatos().getLexema() + "(");
                int cantidadParametros = metodo.getParametros().size();
                int contadorParametrosProcesados = 0;
                for(Parametro parametro : metodo.getParametros()){
                    contadorParametrosProcesados++;
                    System.out.print(parametro.getTipo().getTokenDeDatos().getLexema() + " " + parametro.getTokenDeDatos().getLexema());
                    if(contadorParametrosProcesados < cantidadParametros)
                        System.out.print(", ");
                }
                System.out.println(");");
            }
        }
    }

}
