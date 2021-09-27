package main.analizadorSintactico;

import main.analizadorLexico.AnalizadorLexico;
import main.analizadorLexico.Token;
import main.analizadorLexico.excepciones.ExcepcionLexica;
import main.analizadorSintactico.excepciones.ExcepcionSintactica;

import java.util.Arrays;

public class AnalizadorSintactico {

    private Token tokenActual;
    private AnalizadorLexico analizadorLexico;

    public AnalizadorSintactico(AnalizadorLexico analizadorLexico) throws ExcepcionLexica, ExcepcionSintactica {
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

    private void inicial() throws ExcepcionLexica, ExcepcionSintactica {
        listaClases();
        match("EOF");
    }

    private void listaClases() throws ExcepcionLexica, ExcepcionSintactica {
        clase();
        listaClasesTransformado();
    }

    private void listaClasesTransformado() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("class")) {
            listaClases();
        } else {

        }
    }

    private void clase() throws ExcepcionLexica, ExcepcionSintactica {
        match("class");
        match("idClase");
        herencia();
        match("{");
        listaMiembros();
        match("}");
    }

    private void herencia() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("extends")) {
            match("extends");
            match("idClase");
        } else {

        }
    }

    private void listaMiembros() throws ExcepcionSintactica, ExcepcionLexica {
        if (Arrays.asList("public", "private", "idClase", "static", "dynamic").contains(tokenActual.getNombre())) {
            miembro();
            listaMiembros();
        } else {

        }
    }

    private void miembro() throws ExcepcionSintactica, ExcepcionLexica {
        if (Arrays.asList("public", "private").contains(tokenActual.getNombre())) {
            atributo();
        } else if (Arrays.asList("idClase").contains(tokenActual.getNombre())) {
            constructor();
        } else if (Arrays.asList("static", "dynamic").contains(tokenActual.getNombre())) {
            metodo();
        } else {
            throw new ExcepcionSintactica(tokenActual, "public, private, idClase, static, dynamic");
        }
    }

    private void atributo() throws ExcepcionLexica, ExcepcionSintactica {
        visibilidad();
        tipo();
        listaDecAtrs();
        match(";");
    }

    private void metodo() throws ExcepcionLexica, ExcepcionSintactica {
        formaMetodo();
        tipoMetodo();
        match("idMetVar");
        argsFormales();
        bloque();
    }

    private void constructor() throws ExcepcionLexica, ExcepcionSintactica {
        match("idClase");
        argsFormales();
        bloque();
    }

    private void visibilidad() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("public"))
            match("public");
        else if (tokenActual.getNombre().equals("private"))
            match("private");
    }

    private void tipo() throws ExcepcionLexica, ExcepcionSintactica {
        if (Arrays.asList("boolean", "char", "int", "String").contains(tokenActual.getNombre()))
            tipoPrimitivo();
        else if (tokenActual.getNombre().equals("idClase"))
            match("idClase");
        else
            throw new ExcepcionSintactica(tokenActual, "boolean, char, int, String, idClase");
    }

    private void tipoPrimitivo() throws ExcepcionLexica, ExcepcionSintactica {
        match(tokenActual.getNombre());
    }

    private void listaDecAtrs() throws ExcepcionLexica, ExcepcionSintactica {
        match("idMetVar");
        listaDecAtrsTransformado();
    }

    private void listaDecAtrsTransformado() throws ExcepcionLexica, ExcepcionSintactica {
        if(tokenActual.getNombre().equals(",")){
            match(",");
            listaDecAtrs();
        } else {

        }
    }

    private void formaMetodo() throws ExcepcionLexica, ExcepcionSintactica {
        match(tokenActual.getNombre());
    }

    private void tipoMetodo() throws ExcepcionLexica, ExcepcionSintactica {
        if(Arrays.asList("boolean", "char", "int", "String","idClase").contains(tokenActual.getNombre())){
            tipo();
        } else if(tokenActual.getNombre().equals("void")){
            match("void");
        } else {
            throw new ExcepcionSintactica(tokenActual, "boolean, char, int, String, idClase, void");
        }
    }

    private void argsFormales() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getNombre().equals("(")){
            match("(");
            listaArgsFormalesOVacio();
            match(")");
        } else {
            throw new ExcepcionSintactica(tokenActual, "(");
        }
    }

    private void listaArgsFormalesOVacio() throws ExcepcionLexica, ExcepcionSintactica {
        if(Arrays.asList("boolean", "char", "int", "String","idClase").contains(tokenActual.getNombre())){
            listaArgsFormales();
        } else {

        }
    }

    private void listaArgsFormales() throws ExcepcionSintactica, ExcepcionLexica {
        if(Arrays.asList("boolean", "char", "int", "String","idClase").contains(tokenActual.getNombre())){
            argFormal();
            listaArgsFormalesTransformado();
        } else {
            throw new ExcepcionSintactica(tokenActual, "boolean, char, int, String, idClase");
        }
    }

    private void listaArgsFormalesTransformado() throws ExcepcionLexica, ExcepcionSintactica {
        if(tokenActual.getNombre().equals(",")){
            match(",");
            argFormal();
        } else {

        }
    }

    private void argFormal() throws ExcepcionSintactica, ExcepcionLexica {
        if(Arrays.asList("boolean", "char","int","String","idClase").contains(tokenActual.getNombre())){
            tipo();
            match("idMetVar");
        } else {
            throw new ExcepcionSintactica(tokenActual, "boolean, char, int, String, idClase");
        }
    }

    private void bloque() throws ExcepcionLexica, ExcepcionSintactica {
        if(tokenActual.getNombre().equals("{")) {
            match("{");
            listaSentencias();
            match("}");
        } else {
            throw new ExcepcionSintactica(tokenActual, "{");
        }
    }

    private void listaSentencias() throws ExcepcionLexica, ExcepcionSintactica {
        if(Arrays.asList(";" , "this", "new", "idMetVar", "(", "boolean", "char", "int","String", "idClase", "return", "if", "for", "{").contains(tokenActual.getNombre())){
            sentencia();
            listaSentencias();
        } else {

        }
    }

    private void sentencia() throws ExcepcionLexica, ExcepcionSintactica {
        if(tokenActual.getNombre().equals(";")) {
            match(";");
        } else if(Arrays.asList("this", "new", "idMetVar", "(").contains(tokenActual.getNombre())){
            acceso();
            sentenciaTransformado();
            match(";");
        } else if(Arrays.asList("boolean", "char","int","String","idClase").contains(tokenActual.getNombre())){
            varLocal();
            match(";");
        } else if(tokenActual.getNombre().equals("return")){
            returnMetodo();
            match(";");
        } else if(tokenActual.getNombre().equals("if")){
            ifMetodo();
        } else if(tokenActual.getNombre().equals("for")){
            forMetodo();
        } else if(tokenActual.getNombre().equals("{")){
            bloque();
        } else {
            throw new ExcepcionSintactica(tokenActual, ";, this, new, idMetVar, (, boolean, char, int, String, idClase, return, if, for, {");
        }
    }

    private void sentenciaTransformado() throws ExcepcionLexica, ExcepcionSintactica {
        if(Arrays.asList("=", "++", "--").contains(tokenActual.getNombre())){
            tipoDeAsignacion();
        } else {

        }
    }

    private void asignacion() throws ExcepcionSintactica, ExcepcionLexica {
        if(Arrays.asList("this", "new", "idMetVar", "(").contains(tokenActual.getNombre())){
            acceso();
            tipoDeAsignacion();
        } else {
            throw new ExcepcionSintactica(tokenActual, "this, new, idMetVar, (");
        }
    }

    private void tipoDeAsignacion() throws ExcepcionLexica, ExcepcionSintactica {
        if(tokenActual.getNombre().equals("=")){
            match("=");
            expresion();
        } else if(tokenActual.getNombre().equals("++")){
            match("++");
        } else if(tokenActual.getNombre().equals("--")){
            match("--");
        } else {
            throw new ExcepcionSintactica(tokenActual, "=, ++, --");
        }
    }

    private void varLocal() throws ExcepcionLexica, ExcepcionSintactica {
        tipo();
        match("idMetVar");
        varLocalTransformado();
    }

    private void varLocalTransformado() throws ExcepcionLexica, ExcepcionSintactica {
        if(tokenActual.getNombre().equals("=")){
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
        if(Arrays.asList("+", "-", "!", "null", "true", "false", "intLiteral", "charLiteral", "stringLiteral", "this", "new", "idMetVar", "(").contains(tokenActual.getNombre())){
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
        if(tokenActual.getNombre().equals("else")){
            match("else");
            sentencia();
        } else {

        }
    }

    private void forMetodo() throws ExcepcionLexica, ExcepcionSintactica {
        match("for");
        match("(");
        varLocal();
        match(";");
        expresion();
        match(";");
        asignacion();
        match(")");
        sentencia();
    }

    private void expresion() throws ExcepcionSintactica, ExcepcionLexica {
        if(Arrays.asList("+", "-", "!", "null", "true", "false", "intLiteral", "charLiteral", "stringLiteral", "this", "new", "idMetVar", "(").contains(tokenActual.getNombre())) {
            expresionUnaria();
            expresionTransformado();
        } else {
            throw new ExcepcionSintactica(tokenActual, "+, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, new, idMetVar, (");
        }
    }

    private void expresionTransformado() throws ExcepcionLexica, ExcepcionSintactica {
        if(Arrays.asList("||", "&&", "==", "!=", "<", ">", "<=", ">=", "+", "-", "*", "/", "%").contains(tokenActual.getNombre())){
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
        if(Arrays.asList("+", "-", "!").contains(tokenActual.getNombre())){
            operadorUnario();
            operando();
        } else if(Arrays.asList("null", "true", "false", "intLiteral", "charLiteral", "stringLiteral", "this", "new", "idMetVar", "(").contains(tokenActual.getNombre())){
            operando();
        } else {
            throw new ExcepcionSintactica(tokenActual, "+, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, new, idMetVar, (");
        }
    }

    private void operadorUnario() throws ExcepcionLexica, ExcepcionSintactica {
        match(tokenActual.getNombre());
    }

    private void operando() throws ExcepcionSintactica, ExcepcionLexica {
        if(Arrays.asList("null", "true", "false", "intLiteral", "charLiteral", "stringLiteral").contains(tokenActual.getNombre())){
            literal();
        } else if(Arrays.asList("this", "new", "idMetVar", "(").contains(tokenActual.getNombre())){
            acceso();
        } else {
            throw new ExcepcionSintactica(tokenActual, "null, true, false, intLiteral, charLiteral, stringLiteral, this, new, idMetVar, (");
        }
    }

    private void literal() throws ExcepcionLexica, ExcepcionSintactica {
        match(tokenActual.getNombre());
    }

    private void acceso() throws ExcepcionLexica, ExcepcionSintactica {
        if(tokenActual.getNombre().equals("this")){
            thisEncadenado();
        } else if(tokenActual.getNombre().equals("new")){
            constructorEncadenado();
        } else if(tokenActual.getNombre().equals("idMetVar")){
            llamadaOVariableEncadenado();
        } else if(tokenActual.getNombre().equals("(")){
            match("(");
            expresionOCasting();
        } else {
            throw new ExcepcionSintactica(tokenActual, "this, new, idMetVar, (");
        }
    }

    private void expresionOCasting() throws ExcepcionLexica, ExcepcionSintactica {
        if(Arrays.asList("+", "-", "!", "null", "true", "false", "intLiteral", "charLiteral", "stringLiteral", "this", "new", "idMetVar", "(").contains(tokenActual.getNombre())){
            expresion();
            match(")");
            encadenado();
        } else if(tokenActual.getNombre().equals("idClase")){
            match("idClase");
            match(")");
            primario();
        } else {
            throw new ExcepcionSintactica(tokenActual, "+, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, new, idMetVar, (, idClase");
        }
    }

    private void primario() throws ExcepcionLexica, ExcepcionSintactica {
        if(tokenActual.getNombre().equals("this")){
            thisEncadenado();
        } else if(tokenActual.getNombre().equals("new")){
            constructorEncadenado();
        } else if(tokenActual.getNombre().equals("idMetVar")){
            llamadaOVariableEncadenado();
        } else if(tokenActual.getNombre().equals("(")){
            match("(");
            expresion();
            match(")");
            encadenado();
        } else {
            throw new ExcepcionSintactica(tokenActual, "this, new, idMetVar, (");
        }
    }

    private void argsActualesOVacioEncadenado() throws ExcepcionLexica, ExcepcionSintactica {
        if(tokenActual.getNombre().equals("(")){
            argsActuales();
            encadenado();
        } else if(tokenActual.getNombre().equals(".")){
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
        if(Arrays.asList("+", "-", "!", "null","true","false","intLiteral", "charLiteral", "stringLiteral", "this", "new", "idMetVar", "(").contains(tokenActual.getNombre())){
            listaExps();
        } else {

        }
    }

    private void listaExps() throws ExcepcionSintactica, ExcepcionLexica {
        expresion();
        listaExpsTransformado();
    }

    private void listaExpsTransformado() throws ExcepcionLexica, ExcepcionSintactica {
        if(tokenActual.getNombre().equals(",")){
            match(",");
            listaExps();
        } else {

        }
    }

    private void encadenado() throws ExcepcionLexica, ExcepcionSintactica {
        if(tokenActual.getNombre().equals(".")){
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
        if(tokenActual.getNombre().equals("(")){
            argsActuales();
        } else {

        }
    }
}