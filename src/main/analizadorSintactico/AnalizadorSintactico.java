package main.analizadorSintactico;

import main.analizadorLexico.AnalizadorLexico;
import main.analizadorLexico.Token;
import main.analizadorLexico.excepciones.ExcepcionLexica;
import main.analizadorSemantico.excepciones.ExcepcionSemantica;
import main.analizadorSemantico.tablaDeSimbolos.*;
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
        Metodo metodo = new Metodo(tokenDeDatos, tipo, forma);
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
        bloque();
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
        bloque();
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

    private void bloque() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("{")) {
            match("{");
            listaSentencias();
            match("}");
        } else {
            throw new ExcepcionSintactica(tokenActual, "{");
        }
    }

    private void listaSentencias() throws ExcepcionLexica, ExcepcionSintactica {
        if (Arrays.asList(";", "this", "new", "idMetVar", "(", "boolean", "char", "int", "String", "idClase", "return", "if", "for", "{").contains(tokenActual.getNombre())) {
            sentencia();
            listaSentencias();
        } else {

        }
    }

    private void sentencia() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals(";")) {
            match(";");
        } else if (Arrays.asList("this", "new", "idMetVar", "(").contains(tokenActual.getNombre())) {
            acceso();
            sentenciaTransformado();
            match(";");
        } else if (Arrays.asList("boolean", "char", "int", "String", "idClase").contains(tokenActual.getNombre())) {
            varLocal();
            match(";");
        } else if (tokenActual.getNombre().equals("return")) {
            returnMetodo();
            match(";");
        } else if (tokenActual.getNombre().equals("if")) {
            ifMetodo();
        } else if (tokenActual.getNombre().equals("for")) {
            forMetodo();
        } else if (tokenActual.getNombre().equals("{")) {
            bloque();
        } else {
            throw new ExcepcionSintactica(tokenActual, ";, this, new, idMetVar, (, boolean, char, int, String, idClase, return, if, for o {");
        }
    }

    private void sentenciaTransformado() throws ExcepcionLexica, ExcepcionSintactica {
        if (Arrays.asList("=", "++", "--").contains(tokenActual.getNombre())) {
            tipoDeAsignacion();
        } else {

        }
    }

    private void asignacion() throws ExcepcionSintactica, ExcepcionLexica {
        if (Arrays.asList("this", "new", "idMetVar", "(").contains(tokenActual.getNombre())) {
            acceso();
            tipoDeAsignacion();
        } else {
            throw new ExcepcionSintactica(tokenActual, "this, new, idMetVar o (");
        }
    }

    private void tipoDeAsignacion() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("=")) {
            match("=");
            expresion();
        } else if (tokenActual.getNombre().equals("++")) {
            match("++");
        } else if (tokenActual.getNombre().equals("--")) {
            match("--");
        } else {
            throw new ExcepcionSintactica(tokenActual, "=, ++ o --");
        }
    }

    private void varLocal() throws ExcepcionLexica, ExcepcionSintactica {
        tipoClaseGenericidadOPrimitivo();
        match("idMetVar");
        varLocalTransformado();
    }

    private void varLocalTransformado() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("=")) {
            match("=");
            expresion();
        } else {

        }
    }

    private void returnMetodo() throws ExcepcionLexica, ExcepcionSintactica {
        match("return");
        expresionOVacio();
    }

    private void expresionOVacio() throws ExcepcionLexica, ExcepcionSintactica {
        if (Arrays.asList("+", "-", "!", "null", "true", "false", "intLiteral", "charLiteral", "stringLiteral", "this", "new", "idMetVar", "(").contains(tokenActual.getNombre())) {
            expresion();
        } else {

        }
    }

    private void ifMetodo() throws ExcepcionLexica, ExcepcionSintactica {
        match("if");
        match("(");
        expresion();
        match(")");
        sentencia();
        ifMetodoTransformado();
    }

    private void ifMetodoTransformado() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("else")) {
            match("else");
            sentencia();
        } else {

        }
    }

    private void forMetodo() throws ExcepcionLexica, ExcepcionSintactica {
        match("for");
        match("(");
        tipoClaseGenericidadOPrimitivo();
        match("idMetVar");
        forOForEach();
        match(")");
        sentencia();
    }

    private void forOForEach() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals(":")) {
            match(":");
            acceso();
        } else {
            varLocalTransformado();
            match(";");
            expresion();
            match(";");
            asignacion();
        }
    }

    private void expresion() throws ExcepcionSintactica, ExcepcionLexica {
        if (Arrays.asList("+", "-", "!", "null", "true", "false", "intLiteral", "charLiteral", "stringLiteral", "this", "new", "idMetVar", "(").contains(tokenActual.getNombre())) {
            expresionUnaria();
            expresionTransformado();
        } else {
            throw new ExcepcionSintactica(tokenActual, "+, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, new, idMetVar o (");
        }
    }

    private void expresionTransformado() throws ExcepcionLexica, ExcepcionSintactica {
        if (Arrays.asList("||", "&&", "==", "!=", "<", ">", "<=", ">=", "+", "-", "*", "/", "%").contains(tokenActual.getNombre())) {
            operadorBinario();
            expresionUnaria();
            expresionTransformado();
        } else {

        }
    }

    private void operadorBinario() throws ExcepcionLexica, ExcepcionSintactica {
        match(tokenActual.getNombre());
    }

    private void expresionUnaria() throws ExcepcionSintactica, ExcepcionLexica {
        if (Arrays.asList("+", "-", "!").contains(tokenActual.getNombre())) {
            operadorUnario();
            operando();
        } else if (Arrays.asList("null", "true", "false", "intLiteral", "charLiteral", "stringLiteral", "this", "new", "idMetVar", "(").contains(tokenActual.getNombre())) {
            operando();
        } else {
            throw new ExcepcionSintactica(tokenActual, "+, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, new, idMetVar o (");
        }
    }

    private void operadorUnario() throws ExcepcionLexica, ExcepcionSintactica {
        match(tokenActual.getNombre());
    }

    private void operando() throws ExcepcionSintactica, ExcepcionLexica {
        if (Arrays.asList("null", "true", "false", "intLiteral", "charLiteral", "stringLiteral").contains(tokenActual.getNombre())) {
            literal();
        } else if (Arrays.asList("this", "new", "idMetVar", "(").contains(tokenActual.getNombre())) {
            acceso();
        } else {
            throw new ExcepcionSintactica(tokenActual, "null, true, false, intLiteral, charLiteral, stringLiteral, this, new, idMetVar o (");
        }
    }

    private void literal() throws ExcepcionLexica, ExcepcionSintactica {
        match(tokenActual.getNombre());
    }

    private void acceso() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("this")) {
            thisEncadenado();
        } else if (tokenActual.getNombre().equals("new")) {
            constructorEncadenado();
        } else if (tokenActual.getNombre().equals("idMetVar")) {
            llamadaOVariableEncadenado();
        } else if (tokenActual.getNombre().equals("(")) {
            match("(");
            expresionOCasting();
        } else {
            throw new ExcepcionSintactica(tokenActual, "this, new, idMetVar o (");
        }
    }

    private void expresionOCasting() throws ExcepcionLexica, ExcepcionSintactica {
        if (Arrays.asList("+", "-", "!", "null", "true", "false", "intLiteral", "charLiteral", "stringLiteral", "this", "new", "idMetVar", "(").contains(tokenActual.getNombre())) {
            expresion();
            match(")");
            encadenado();
        } else if (tokenActual.getNombre().equals("idClase")) {
            tipoClaseGenericidad();
            match(")");
            primario();
        } else {
            throw new ExcepcionSintactica(tokenActual, "+, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, new, idMetVar, ( o idClase");
        }
    }

    private void primario() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("this")) {
            thisEncadenado();
        } else if (tokenActual.getNombre().equals("new")) {
            constructorEncadenado();
        } else if (tokenActual.getNombre().equals("idMetVar")) {
            llamadaOVariableEncadenado();
        } else if (tokenActual.getNombre().equals("(")) {
            match("(");
            expresion();
            match(")");
            encadenado();
        } else {
            throw new ExcepcionSintactica(tokenActual, "this, new, idMetVar o (");
        }
    }

    private void argsActualesOVacioEncadenado() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("(")) {
            argsActuales();
            encadenado();
        } else if (tokenActual.getNombre().equals(".")) {
            encadenado();
        } else {

        }
    }

    private void thisEncadenado() throws ExcepcionLexica, ExcepcionSintactica {
        match("this");
        encadenado();
    }

    private void constructorEncadenado() throws ExcepcionLexica, ExcepcionSintactica {
        match("new");
        match("idClase");
        genericidadONotacionDiamante();
        argsActuales();
        encadenado();
    }

    private void llamadaOVariableEncadenado() throws ExcepcionLexica, ExcepcionSintactica {
        match("idMetVar");
        argsActualesOVacioEncadenado();
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
        expresion();
        listaExpsTransformado();
    }

    private void listaExpsTransformado() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals(",")) {
            match(",");
            listaExps();
        } else {

        }
    }

    private void encadenado() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals(".")) {
            varOMetodoEncadenado();
            encadenado();
        } else {

        }
    }

    private void varOMetodoEncadenado() throws ExcepcionLexica, ExcepcionSintactica {
        match(".");
        match("idMetVar");
        varOMetodoEncadenadoTransformado();
    }

    private void varOMetodoEncadenadoTransformado() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("(")) {
            argsActuales();
        } else {

        }
    }
}