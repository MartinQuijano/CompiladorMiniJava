package main.analizadorSintactico;

import main.analizadorLexico.AnalizadorLexico;
import main.analizadorLexico.Token;
import main.analizadorLexico.excepciones.ExcepcionLexica;
import main.analizadorSemantico.ast.*;
import main.analizadorSemantico.ast.encadenados.NodoEncadenado;
import main.analizadorSemantico.ast.encadenados.NodoLlamadaEncadenada;
import main.analizadorSemantico.ast.encadenados.NodoVarEncadenada;
import main.analizadorSemantico.ast.expresiones.NodoExpBinaria;
import main.analizadorSemantico.ast.expresiones.NodoExpUnaria;
import main.analizadorSemantico.ast.expresiones.NodoExpresion;
import main.analizadorSemantico.ast.expresiones.operandos.*;
import main.analizadorSemantico.ast.expresiones.operandos.accesos.*;
import main.analizadorSemantico.ast.sentencias.*;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.*;
import main.analizadorSemantico.tablaDeSimbolos.tipos.*;
import main.analizadorSemantico.tablaDeSimbolos.unidades.Constructor;
import main.analizadorSemantico.tablaDeSimbolos.unidades.Metodo;
import main.analizadorSemantico.tablaDeSimbolos.unidades.Unidad;
import main.analizadorSemantico.tablaDeSimbolos.variables.Atributo;
import main.analizadorSemantico.tablaDeSimbolos.variables.Parametro;
import main.analizadorSintactico.excepciones.ExcepcionSintactica;

import java.util.Arrays;

public class AnalizadorSintactico {

    private Token tokenActual;
    private AnalizadorLexico analizadorLexico;

    public AnalizadorSintactico(AnalizadorLexico analizadorLexico) throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        this.analizadorLexico = analizadorLexico;
        tokenActual = analizadorLexico.proximoToken();
        inicial();
    }

    private void match(String nombreToken) throws ExcepcionLexica, ExcepcionSintactica {
        if (nombreToken.equals(tokenActual.getNombre())) {
            tokenActual = analizadorLexico.proximoToken();
        } else {
            throw new ExcepcionSintactica(tokenActual, nombreToken);
        }
    }

    private void inicial() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        listaClases();
        match("EOF");
    }

    private void listaClases() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        clase();
        listaClasesTransformado();
    }

    private void listaClasesTransformado() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        if (tokenActual.getNombre().equals("class")) {
            listaClases();
        } else {

        }
    }

    private void clase() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        match("class");
        Token tokenDeClase = tokenActual;
        match("idClase");
        Clase clase = new Clase(tokenDeClase);
        genericidad();
        TablaDeSimbolos.setClaseActual(clase);
        Token tokenHerencia = herencia();
        TablaDeSimbolos.getClaseActual().heredaDe(tokenHerencia);
        match("{");
        listaMiembros();
        match("}");
        if(TablaDeSimbolos.existeClase(tokenDeClase.getLexema()) == null) {
            if(clase.getConstructor() == null){
                Unidad constructorPorDefecto = new Constructor(new Token("idClase", clase.getTokenDeDatos().getLexema(),0));
                constructorPorDefecto.setBloque(new NodoBloque());
                clase.insertarConstructor(constructorPorDefecto);
            }
            TablaDeSimbolos.insertarClase(clase);
        }
        else
            throw new ExcepcionSemantica(tokenDeClase, "Ya existe una clase con el nombre '" + tokenDeClase.getLexema() + "'.");
    }

    private Token herencia() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("extends")) {
            match("extends");
            Token tokenDeClase = tokenActual;
            match("idClase");
            genericidad();
            return tokenDeClase;
        } else {
            return new Token("Object", "Object", 0);
        }
    }

    private void listaMiembros() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        if (Arrays.asList("public", "private", "boolean", "char", "int", "String", "idClase", "static", "dynamic").contains(tokenActual.getNombre())) {
            miembro();
            listaMiembros();
        } else {

        }
    }

    private void miembro() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        if (Arrays.asList("public", "private").contains(tokenActual.getNombre())) {
            atributo();
        } else if (Arrays.asList("idClase", "boolean", "char", "int", "String").contains(tokenActual.getNombre())) {
            constructorOAtributoSinVisibilidad();
        } else if (Arrays.asList("static", "dynamic").contains(tokenActual.getNombre())) {
            metodo();
        } else {
            throw new ExcepcionSintactica(tokenActual, "public, private, boolean, char, int, String, idClase, static o dynamic");
        }
    }

    private void constructorOAtributoSinVisibilidad() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        if (tokenActual.getNombre().equals("idClase")) {
            Tipo tipo = new TipoReferencia(tokenActual);
            match("idClase");
            constructorOAtributo("public", tipo);
        } else {
            Tipo tipo = tipoPrimitivo();
            listaDecAtrs("public", tipo);
            match(";");
        }
    }

    private void constructorOAtributo(String visibilidad, Tipo tipo) throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        if (tokenActual.getNombre().equals("(")) {
            constructor(tipo);
        } else if (Arrays.asList("idMetVar", "<").contains(tokenActual.getNombre())) {
            genericidad();
            listaDecAtrs(visibilidad, tipo);
            match(";");
        } else {
            throw new ExcepcionSintactica(tokenActual, "(, idMetVar o <");
        }
    }

    private void atributo() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        String visibilidad = visibilidad();
        Tipo tipo = tipoClaseGenericidadOPrimitivo();
        listaDecAtrs(visibilidad, tipo);
        match(";");
    }

    private void metodo() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        String forma = formaMetodo();
        Tipo tipo = tipoMetodo();
        Token tokenDeDatos = tokenActual;
        match("idMetVar");
        Metodo metodo = new Metodo(tokenDeDatos, tipo, forma, TablaDeSimbolos.getClaseActual().getTokenDeDatos().getLexema());
        TablaDeSimbolos.setUnidadActual(metodo);
        argsFormales();

        if(TablaDeSimbolos.getClaseActual().existeMetodo(tokenDeDatos.getLexema()) == null) {
            if(tokenDeDatos.getLexema().equals("main") && metodo.getForma() == "static" && (metodo.getParametros().size() == 0) && metodo.getTipo().getTokenDeDatos().getLexema().equals("void")){
                if(TablaDeSimbolos.getSeEncontroMain())
                    throw new ExcepcionSemantica(tokenDeDatos, "Ya existe una clase que tiene el metodo main");
                else
                    TablaDeSimbolos.setSeEncontroMain(true);
            }
            TablaDeSimbolos.getClaseActual().insertarMetodo(metodo);
        }
        else
            throw new ExcepcionSemantica(tokenDeDatos, "Ya existe un metodo con el nombre " + tokenDeDatos.getLexema() + " en la clase '" + TablaDeSimbolos.getClaseActual().getTokenDeDatos().getLexema() + "'.");
        TablaDeSimbolos.getUnidadActual().setBloque(bloque());
    }

    private void constructor(Tipo tipo) throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        if(!TablaDeSimbolos.getClaseActual().getTokenDeDatos().getLexema().equals(tipo.getTokenDeDatos().getLexema()))
            throw new ExcepcionSemantica(tipo.getTokenDeDatos(), "El nombre del constructor debe coincidir con el de la clase en la cual es declarado.");
        Unidad constructor = new Constructor(tipo.getTokenDeDatos());
        if(TablaDeSimbolos.getClaseActual().getConstructor() != null)
            throw new ExcepcionSemantica(tipo.getTokenDeDatos(), "Ya existe un constructor para esta clase.");
        TablaDeSimbolos.getClaseActual().insertarConstructor(constructor);
        TablaDeSimbolos.setUnidadActual(constructor);
        argsFormales();
        TablaDeSimbolos.getUnidadActual().setBloque(bloque());
    }

    private String visibilidad() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("public")) {
            match("public");
            return "public";
        }
        else {
            match("private");
            return "private";
        }
    }

    private Tipo tipo() throws ExcepcionLexica, ExcepcionSintactica {
        if (Arrays.asList("boolean", "char", "int", "String").contains(tokenActual.getNombre()))
            return tipoPrimitivo();
        else if (tokenActual.getNombre().equals("idClase")) {
            Token tokenTipo = tokenActual;
            match("idClase");
            return new TipoReferencia(tokenTipo);
        }
        else
            throw new ExcepcionSintactica(tokenActual, "boolean, char, int, String o idClase");
    }

    private Tipo tipoPrimitivo() throws ExcepcionLexica, ExcepcionSintactica {
        Token tokenDeTipo = tokenActual;
        match(tokenActual.getNombre());
        if(tokenDeTipo.getNombre().equals("boolean"))
            return new TipoBoolean();
        else if(tokenDeTipo.getNombre().equals("char"))
            return new TipoChar();
        else if(tokenDeTipo.getNombre().equals("int"))
            return new TipoEntero();
        else {
            return new TipoString();
        }
    }

    private void genericidadONotacionDiamante() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("<")) {
            match("<");
            genericidadONotacionDiamanteTransformado();
        } else {

        }
    }

    private void genericidadONotacionDiamanteTransformado() throws ExcepcionLexica, ExcepcionSintactica {
        if(tokenActual.getNombre().equals("idClase")){
            listaGenericidad();
            match(">");
        } else if(tokenActual.getNombre().equals(">")){
            match(">");
        } else {
            throw new ExcepcionSintactica(tokenActual, "idClase o >");
        }
    }

    private Tipo tipoClaseGenericidad() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("idClase")) {
            Token tokenTipo = tokenActual;
            match("idClase");
            genericidad();
            return new TipoReferencia(tokenTipo);
        }  else {
            throw new ExcepcionSintactica(tokenActual, "idClase");
        }
    }

    private Tipo tipoClaseGenericidadOPrimitivo() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("idClase")) {
            return tipoClaseGenericidad();
        } else if(Arrays.asList("boolean", "char", "int", "String").contains(tokenActual.getNombre())){
            return tipoPrimitivo();
        } else {
            throw new ExcepcionSintactica(tokenActual, "boolean, char, int, String o idClase");
        }
    }

    private void genericidad() throws ExcepcionLexica, ExcepcionSintactica {
        if(tokenActual.getNombre().equals("<")){
            match("<");
            listaGenericidad();
            match(">");
        } else {

        }
    }

    private void listaGenericidad() throws ExcepcionLexica, ExcepcionSintactica {
        match("idClase");
        genericidad();
        listaGenericidadEncadenado();
    }

    private void listaGenericidadEncadenado() throws ExcepcionLexica, ExcepcionSintactica {
        if(tokenActual.getNombre().equals(",")){
            match(",");
            listaGenericidad();
        } else {

        }

    }

    private void listaDecAtrs(String visibilidad, Tipo tipo) throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        Atributo atributo = new Atributo(visibilidad, tokenActual, tipo);
        if(TablaDeSimbolos.getClaseActual().existeAtributo(tokenActual.getLexema()) == null){
            TablaDeSimbolos.getClaseActual().insertarAtributo(atributo);
        } else {
            throw new ExcepcionSemantica(tokenActual, "El atributo " + tokenActual.getLexema() + " ya existe en la clase " + TablaDeSimbolos.getClaseActual().getTokenDeDatos().getLexema() + ".");
        }
        match("idMetVar");
        listaDecAtrsTransformado(visibilidad, tipo);
    }

    private void listaDecAtrsTransformado(String visibilidad, Tipo tipo) throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        if (tokenActual.getNombre().equals(",")) {
            match(",");
            listaDecAtrs(visibilidad, tipo);
        } else {

        }
    }

    private String formaMetodo() throws ExcepcionLexica, ExcepcionSintactica {
        String formaMetodo = tokenActual.getNombre();
        match(tokenActual.getNombre());
        return formaMetodo;
    }

    private Tipo tipoMetodo() throws ExcepcionLexica, ExcepcionSintactica {
        if (Arrays.asList("boolean", "char", "int", "String", "idClase").contains(tokenActual.getNombre())) {
            return tipoClaseGenericidadOPrimitivo();
        } else if (tokenActual.getNombre().equals("void")) {
            match("void");
            return new TipoVoid();
        } else {
            throw new ExcepcionSintactica(tokenActual, "boolean, char, int, String, idClase o void");
        }
    }

    private void argsFormales() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        if (tokenActual.getNombre().equals("(")) {
            match("(");
            listaArgsFormalesOVacio();
            match(")");
        } else {
            throw new ExcepcionSintactica(tokenActual, "(");
        }
    }

    private void listaArgsFormalesOVacio() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        if (Arrays.asList("boolean", "char", "int", "String", "idClase").contains(tokenActual.getNombre())) {
            listaArgsFormales();
        } else {

        }
    }

    private void listaArgsFormales() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        if (Arrays.asList("boolean", "char", "int", "String", "idClase").contains(tokenActual.getNombre())) {
            argFormal();
            listaArgsFormalesTransformado();
        } else {
            throw new ExcepcionSintactica(tokenActual, "boolean, char, int, String o idClase");
        }
    }

    private void listaArgsFormalesTransformado() throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        if (tokenActual.getNombre().equals(",")) {
            match(",");
            listaArgsFormales();
        } else {

        }
    }

    private void argFormal() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        if (Arrays.asList("boolean", "char", "int", "String", "idClase").contains(tokenActual.getNombre())) {
            Tipo tipo = tipoClaseGenericidadOPrimitivo();
            Token tokenDeDatos = tokenActual;
            match("idMetVar");
            Parametro parametro = new Parametro(tokenDeDatos, tipo);
            if(TablaDeSimbolos.getUnidadActual().existeParametro(tokenDeDatos.getLexema()) != null){
                throw new ExcepcionSemantica(tokenDeDatos, "Ya existe un parametro con el nombre '" + tokenDeDatos.getLexema() + "' en el metodo '" + TablaDeSimbolos.getUnidadActual().getTokenDeDatos().getLexema() + "'.");
            }
            TablaDeSimbolos.getUnidadActual().insertarParametro(parametro);
        } else {
            throw new ExcepcionSintactica(tokenActual, "boolean, char, int, String o idClase");
        }
    }

    private NodoBloque bloque() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("{")) {
            match("{");
            NodoBloque nodoBloque = new NodoBloque();
            TablaDeSimbolos.getBloques().push(nodoBloque);
            listaSentencias();
            match("}");
            TablaDeSimbolos.getBloques().pop();
            return nodoBloque;
        } else {
            throw new ExcepcionSintactica(tokenActual, "{");
        }
    }

    private void listaSentencias() throws ExcepcionLexica, ExcepcionSintactica {
        if (Arrays.asList(";", "this", "new", "idMetVar", "(", "boolean", "char", "int", "String", "idClase", "return", "if", "for", "{").contains(tokenActual.getNombre())) {
            NodoSentencia nodoSentencia = sentencia();
            TablaDeSimbolos.getBloques().peek().insertarSentencia(nodoSentencia);
            listaSentencias();
        } else {

        }
    }

    private NodoSentencia sentencia() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals(";")) {
            match(";");
            NodoSentenciaVacia nodoSentenciaVacia = new NodoSentenciaVacia();
            return nodoSentenciaVacia;
        } else if (Arrays.asList("this", "new", "idMetVar", "(").contains(tokenActual.getNombre())) {
            NodoAcceso nodoAcceso = acceso();
            NodoTipoAsignacion nodoTipoAsignacion = sentenciaTransformado();
            Token tokenDeDatosDeLLamada = tokenActual;
            match(";");
            if(nodoTipoAsignacion == null)
                return new NodoLlamada(tokenDeDatosDeLLamada, nodoAcceso);
            else {
                return new NodoAsignacion(nodoAcceso, nodoTipoAsignacion);
            }
        } else if (Arrays.asList("boolean", "char", "int", "String", "idClase").contains(tokenActual.getNombre())) {
            NodoVarLocal nodoVarLocal = varLocal();
            match(";");
            return nodoVarLocal;
        } else if (tokenActual.getNombre().equals("return")) {
            Token tokenDeDatosReturn = tokenActual;
            NodoReturn nodoReturn = returnMetodo();
            nodoReturn.setTokenDeDatos(tokenDeDatosReturn);
            match(";");
            return nodoReturn;
        } else if (tokenActual.getNombre().equals("if")) {
            NodoIf nodoIf = ifMetodo();
            return nodoIf;
        } else if (tokenActual.getNombre().equals("for")) {
            NodoFor nodoFor = forMetodo();
            return nodoFor;
        } else if (tokenActual.getNombre().equals("{")) {
            NodoBloque nodoBloque = bloque();
            return nodoBloque;
        } else {
            throw new ExcepcionSintactica(tokenActual, ";, this, new, idMetVar, (, boolean, char, int, String, idClase, return, if, for o {");
        }
    }

    private NodoTipoAsignacion sentenciaTransformado() throws ExcepcionLexica, ExcepcionSintactica {
        if (Arrays.asList("=", "++", "--").contains(tokenActual.getNombre())) {
            return tipoDeAsignacion();
        } else {
            return null;
        }
    }

    private NodoAsignacion asignacion() throws ExcepcionSintactica, ExcepcionLexica {
        if (Arrays.asList("this", "new", "idMetVar", "(").contains(tokenActual.getNombre())) {
            NodoAcceso nodoAcceso = acceso();
            NodoTipoAsignacion nodoTipoAsignacion = tipoDeAsignacion();
            NodoAsignacion nodoAsignacion = new NodoAsignacion(nodoAcceso, nodoTipoAsignacion);
            return nodoAsignacion;
        } else {
            throw new ExcepcionSintactica(tokenActual, "this, new, idMetVar o (");
        }
    }

    private NodoTipoAsignacion tipoDeAsignacion() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("=")) {
            Token tokenDeDatos = tokenActual;
            match("=");
            NodoTipoAsignacion nodoTipoAsignacion = new NodoTipoAsignacion(tokenDeDatos);
            NodoExpresion nodoExpresion = expresion();
            nodoTipoAsignacion.setExpresion(nodoExpresion);
            return nodoTipoAsignacion;
        } else if (tokenActual.getNombre().equals("++")) {
            Token tokenDeDatos = tokenActual;
            match("++");
            NodoTipoAsignacion nodoTipoAsignacion = new NodoTipoAsignacion(tokenDeDatos);
            return nodoTipoAsignacion;
        } else if (tokenActual.getNombre().equals("--")) {
            Token tokenDeDatos = tokenActual;
            match("--");
            NodoTipoAsignacion nodoTipoAsignacion = new NodoTipoAsignacion(tokenDeDatos);
            return nodoTipoAsignacion;
        } else {
            throw new ExcepcionSintactica(tokenActual, "=, ++ o --");
        }
    }

    private NodoVarLocal varLocal() throws ExcepcionLexica, ExcepcionSintactica {
        Tipo tipo = tipoClaseGenericidadOPrimitivo();
        Token tokenDeDatos = tokenActual;
        match("idMetVar");
        return varLocalTransformado(tokenDeDatos, tipo);
    }

    private NodoVarLocal varLocalTransformado(Token tokenDeDatos, Tipo tipo) throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("=")) {
            Token tokenDeOperador = tokenActual;
            match("=");
            NodoExpresion nodoExpresion = expresion();
            NodoVarLocalAsignacion nodoVarLocalAsignacion = new NodoVarLocalAsignacion(tokenDeDatos, tokenDeOperador, tipo, nodoExpresion);
            return nodoVarLocalAsignacion;
        } else {
            NodoVarLocal nodoVarLocal = new NodoVarLocal(tokenDeDatos, tipo);
            return nodoVarLocal;
        }
    }

    private NodoReturn returnMetodo() throws ExcepcionLexica, ExcepcionSintactica {
        Token tokenDeDatosReturn = tokenActual;
        match("return");
        return expresionOVacio(tokenDeDatosReturn);
    }

    private NodoReturn expresionOVacio(Token tokenDeDatosReturn) throws ExcepcionLexica, ExcepcionSintactica {
        if (Arrays.asList("+", "-", "!", "null", "true", "false", "intLiteral", "charLiteral", "stringLiteral", "this", "new", "idMetVar", "(").contains(tokenActual.getNombre())) {
            NodoExpresion nodoExpresion = expresion();
            NodoReturnExpresion nodoReturnExpresion = new NodoReturnExpresion(tokenDeDatosReturn,nodoExpresion);
            return nodoReturnExpresion;
        } else {
            NodoReturn nodoReturn = new NodoReturn(tokenDeDatosReturn);
            return nodoReturn;
        }
    }

    private NodoIf ifMetodo() throws ExcepcionLexica, ExcepcionSintactica {
        Token tokenDeDatosIf = tokenActual;
        match("if");
        match("(");
        NodoExpresion nodoExpresion = expresion();
        match(")");
        NodoSentencia nodoSentencia = sentencia();
        return ifMetodoTransformado(tokenDeDatosIf, nodoExpresion, nodoSentencia);
    }

    private NodoIf ifMetodoTransformado(Token tokenDeDatosIf, NodoExpresion nodoExpresion, NodoSentencia nodoSentenciaThen) throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("else")) {
            match("else");
            NodoSentencia nodoSentenciaElse = sentencia();
            NodoIfElse nodoIfElse = new NodoIfElse(tokenDeDatosIf, nodoExpresion, nodoSentenciaThen, nodoSentenciaElse);
            return nodoIfElse;
        } else {
            NodoIf nodoIf = new NodoIf(tokenDeDatosIf, nodoExpresion, nodoSentenciaThen);
            return nodoIf;
        }
    }

    private NodoFor forMetodo() throws ExcepcionLexica, ExcepcionSintactica {
        Token tokenDeDatosFor = tokenActual;
        match("for");
        match("(");
        Tipo tipo = tipoClaseGenericidadOPrimitivo();
        Token tokenDeDatos = tokenActual;
        match("idMetVar");
        NodoFor nodoFor = forOForEach(tokenDeDatosFor, tokenDeDatos, tipo);
        match(")");
        NodoSentencia nodoSentencia = sentencia();
        nodoFor.setSentencia(nodoSentencia);
        return nodoFor;
    }

    private NodoFor forOForEach(Token tokenDeDatosFor, Token tokenDeDatos, Tipo tipo) throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals(":")) {
            match(":");
            acceso();
            return null;
        } else {
            NodoVarLocal nodoVarLocal = varLocalTransformado(tokenDeDatos, tipo);
            match(";");
            NodoExpresion nodoExpresion = expresion();
            match(";");
            NodoAsignacion nodoAsignacion = asignacion();
            return new NodoFor(tokenDeDatosFor, nodoVarLocal, nodoExpresion, nodoAsignacion);
        }
    }

    private NodoExpresion expresion() throws ExcepcionSintactica, ExcepcionLexica {
        if (Arrays.asList("+", "-", "!", "null", "true", "false", "intLiteral", "charLiteral", "stringLiteral", "this", "new", "idMetVar", "(").contains(tokenActual.getNombre())) {
            NodoExpresion nodoExpresion = expresionUnaria();
            return expresionTransformado(nodoExpresion);
        } else {
            throw new ExcepcionSintactica(tokenActual, "+, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, new, idMetVar o (");
        }
    }

    private NodoExpresion expresionTransformado(NodoExpresion nodoExpresionIzquierda) throws ExcepcionLexica, ExcepcionSintactica {
        if (Arrays.asList("||", "&&", "==", "!=", "<", ">", "<=", ">=", "+", "-", "*", "/", "%").contains(tokenActual.getNombre())) {
            Token operador = operadorBinario();
            NodoExpresion nodoExpresionDerecha = expresionUnaria();
            NodoExpBinaria nodoExpBinaria = new NodoExpBinaria(nodoExpresionIzquierda, nodoExpresionDerecha, operador);
            return expresionTransformado(nodoExpBinaria);
        } else {
            return nodoExpresionIzquierda;
        }
    }

    private Token operadorBinario() throws ExcepcionLexica, ExcepcionSintactica {
        Token tokenDeDatos = tokenActual;
        match(tokenActual.getNombre());
        return tokenDeDatos;
    }

    private NodoExpresion expresionUnaria() throws ExcepcionSintactica, ExcepcionLexica {
        if (Arrays.asList("+", "-", "!").contains(tokenActual.getNombre())) {
            Token operador = operadorUnario();
            NodoExpUnaria nodoExpUnaria = new NodoExpUnaria(operador);
            NodoOperando nodoOperando = operando();
            nodoExpUnaria.setOperando(nodoOperando);
            return nodoExpUnaria;
        } else if (Arrays.asList("null", "true", "false", "intLiteral", "charLiteral", "stringLiteral", "this", "new", "idMetVar", "(").contains(tokenActual.getNombre())) {
            NodoOperando nodoOperando = operando();
            return nodoOperando;
        } else {
            throw new ExcepcionSintactica(tokenActual, "+, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, new, idMetVar o (");
        }
    }

    private Token operadorUnario() throws ExcepcionLexica, ExcepcionSintactica {
        Token tokenDeDatos = tokenActual;
        match(tokenActual.getNombre());
        return tokenDeDatos;
    }

    private NodoOperando operando() throws ExcepcionSintactica, ExcepcionLexica {
        if (Arrays.asList("null", "true", "false", "intLiteral", "charLiteral", "stringLiteral").contains(tokenActual.getNombre())) {
            return literal();
        } else if (Arrays.asList("this", "new", "idMetVar", "(").contains(tokenActual.getNombre())) {
            NodoAcceso nodoAcceso = acceso();
            return nodoAcceso;
        } else {
            throw new ExcepcionSintactica(tokenActual, "null, true, false, intLiteral, charLiteral, stringLiteral, this, new, idMetVar o (");
        }
    }

    private NodoOperando literal() throws ExcepcionLexica, ExcepcionSintactica {
        Token tokenDeDatos = tokenActual;
        match(tokenActual.getNombre());
        if(tokenDeDatos.getNombre().equals("null")){
            return new NodoNull(tokenDeDatos);
        } else if(tokenDeDatos.getNombre().equals("true")){
            return new NodoTrue(tokenDeDatos);
        } else if(tokenDeDatos.getNombre().equals("false")){
            return new NodoFalse(tokenDeDatos);
        } else if(tokenDeDatos.getNombre().equals("intLiteral")){
            return new NodoInt(tokenDeDatos);
        } else if(tokenDeDatos.getNombre().equals("charLiteral")){
            return new NodoChar(tokenDeDatos);
        } else {
            return new NodoString(tokenDeDatos);
        }
    }

    private NodoAcceso acceso() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("this")) {
            return thisEncadenado();
        } else if (tokenActual.getNombre().equals("new")) {
            return constructorEncadenado();
        } else if (tokenActual.getNombre().equals("idMetVar")) {
            return llamadaOVariableEncadenado();
        } else if (tokenActual.getNombre().equals("(")) {
            match("(");
            return expresionOCasting();
        } else {
            throw new ExcepcionSintactica(tokenActual, "this, new, idMetVar o (");
        }
    }

    private NodoAcceso expresionOCasting() throws ExcepcionLexica, ExcepcionSintactica {
        if (Arrays.asList("+", "-", "!", "null", "true", "false", "intLiteral", "charLiteral", "stringLiteral", "this", "new", "idMetVar", "(").contains(tokenActual.getNombre())) {
            NodoExpresion nodoExpresion = expresion();
            match(")");
            NodoEncadenado nodoEncadenado = encadenado();
            NodoExpParentizada nodoExpParentizada = new NodoExpParentizada();
            nodoExpParentizada.setExpresion(nodoExpresion);
            nodoExpParentizada.setEncadenado(nodoEncadenado);
            return nodoExpParentizada;
        } else if (tokenActual.getNombre().equals("idClase")) {
            NodoCasting nodoCasting = new NodoCasting(tokenActual);
            tipoClaseGenericidad();
            match(")");
            NodoPrimario nodoPrimario = primario();
            nodoCasting.setPrimario(nodoPrimario);
            return nodoCasting;
        } else {
            throw new ExcepcionSintactica(tokenActual, "+, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, new, idMetVar, ( o idClase");
        }
    }

    private NodoPrimario primario() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("this")) {
            return thisEncadenado();
        } else if (tokenActual.getNombre().equals("new")) {
            return constructorEncadenado();
        } else if (tokenActual.getNombre().equals("idMetVar")) {
            return llamadaOVariableEncadenado();
        } else if (tokenActual.getNombre().equals("(")) {
            match("(");
            NodoExpresion nodoExpresion = expresion();
            match(")");
            NodoEncadenado nodoEncadenado = encadenado();
            NodoExpParentizada nodoExpParentizada = new NodoExpParentizada();
            nodoExpParentizada.setExpresion(nodoExpresion);
            nodoExpParentizada.setEncadenado(nodoEncadenado);
            return nodoExpParentizada;
        } else {
            throw new ExcepcionSintactica(tokenActual, "this, new, idMetVar o (");
        }
    }

    private NodoPrimario argsActualesOVacioEncadenado(Token tokenDeDatos) throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("(")) {
            NodoLlamadaMetodo nodoLlamadaMetodo = new NodoLlamadaMetodo(tokenDeDatos);
            TablaDeSimbolos.pushNodoConArgsActual(nodoLlamadaMetodo);
            argsActuales();
            NodoEncadenado nodoEncadenado = encadenado();
            nodoLlamadaMetodo.setEncadenado(nodoEncadenado);
            return nodoLlamadaMetodo;
        } else if (tokenActual.getNombre().equals(".")) {
            NodoVar nodoVar = new NodoVar(tokenDeDatos);
            NodoEncadenado nodoEncadenado = encadenado();
            nodoVar.setEncadenado(nodoEncadenado);
            return nodoVar;
        } else {
            NodoVar nodoVar = new NodoVar(tokenDeDatos);
            return nodoVar;
        }
    }

    private NodoThis thisEncadenado() throws ExcepcionLexica, ExcepcionSintactica {
        Token tokenDeDatos = tokenActual;
        match("this");
        NodoThis nodoThis = new NodoThis(tokenDeDatos);
        NodoEncadenado nodoEncadenado = encadenado();
        nodoThis.setEncadenado(nodoEncadenado);
        return nodoThis;
    }

    private NodoConstructor constructorEncadenado() throws ExcepcionLexica, ExcepcionSintactica {
        match("new");
        Token tokenDeDatos = tokenActual;
        match("idClase");
        NodoConstructor nodoConstructor = new NodoConstructor(tokenDeDatos);
        TablaDeSimbolos.pushNodoConArgsActual(nodoConstructor);
        genericidadONotacionDiamante();
        argsActuales();
        NodoEncadenado nodoEncadenado = encadenado();
        nodoConstructor.setEncadenado(nodoEncadenado);
        TablaDeSimbolos.popNodoConArgsActual();
        return nodoConstructor;
    }

    private NodoPrimario llamadaOVariableEncadenado() throws ExcepcionLexica, ExcepcionSintactica {
        Token tokenDeDatos = tokenActual;
        match("idMetVar");
        return argsActualesOVacioEncadenado(tokenDeDatos);
    }

    private void argsActuales() throws ExcepcionLexica, ExcepcionSintactica {
        match("(");
        listaExpsOVacio();
        match(")");
    }

    private void listaExpsOVacio() throws ExcepcionSintactica, ExcepcionLexica {
        if (Arrays.asList("+", "-", "!", "null", "true", "false", "intLiteral", "charLiteral", "stringLiteral", "this", "new", "idMetVar", "(").contains(tokenActual.getNombre())) {
            listaExps();
        } else {

        }
    }

    private void listaExps() throws ExcepcionSintactica, ExcepcionLexica {
        NodoExpresion nodoExpresion = expresion();
        TablaDeSimbolos.getTopeNodosConArgsActual().insertarArgActual(nodoExpresion);
        listaExpsTransformado();
    }

    private void listaExpsTransformado() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals(",")) {
            match(",");
            listaExps();
        } else {

        }
    }

    private NodoEncadenado encadenado() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals(".")) {
            NodoEncadenado nodoEncadenado = varOMetodoEncadenado();
            nodoEncadenado.setEncadenado(encadenado());
            return nodoEncadenado;
        } else {
            return null;
        }
    }

    private NodoEncadenado varOMetodoEncadenado() throws ExcepcionLexica, ExcepcionSintactica {
        match(".");
        Token tokenDeDatos = tokenActual;
        match("idMetVar");
        return varOMetodoEncadenadoTransformado(tokenDeDatos);
    }

    private NodoEncadenado varOMetodoEncadenadoTransformado(Token tokenDeDatos) throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("(")) {
            NodoLlamadaEncadenada nodoLlamadaEncadenada = new NodoLlamadaEncadenada(tokenDeDatos);
            TablaDeSimbolos.pushNodoConArgsActual(nodoLlamadaEncadenada);
            argsActuales();
            TablaDeSimbolos.popNodoConArgsActual();
            return nodoLlamadaEncadenada;
        } else {
            NodoVarEncadenada nodoVarEncadenada = new NodoVarEncadenada(tokenDeDatos);
            return nodoVarEncadenada;
        }
    }
}