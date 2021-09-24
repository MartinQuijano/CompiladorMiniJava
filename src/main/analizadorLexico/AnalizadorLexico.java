package main.analizadorLexico;

import main.analizadorLexico.excepciones.ExcepcionLexica;
import main.manejadorDeArchivos.ProcesadorDeArchivo;

import java.util.HashMap;

public class AnalizadorLexico {
    private String lexema;
    private char caracterActual;
    private int contadorDigitos;

    private ProcesadorDeArchivo procesadorDeArchivo;
    private HashMap<String, String> hashMap;

    private int nroLineaInicioComentarioMultilinea;
    private int nroLineaInicioBloqueDeTexto;
    private int nroColumnaDondeIniciaElLexema;

    public AnalizadorLexico(ProcesadorDeArchivo procesador) {
        procesadorDeArchivo = procesador;
        construirHash();
        actualizarCaracterActual();
    }

    private void construirHash() {
        hashMap = new HashMap<String, String>();
        hashMap.put("class", "class");
        hashMap.put("extends", "extends");
        hashMap.put("static", "static");
        hashMap.put("dynamic", "dynamic");
        hashMap.put("void", "void");
        hashMap.put("boolean", "boolean");
        hashMap.put("char", "char");
        hashMap.put("int", "int");
        hashMap.put("String", "String");
        hashMap.put("public", "public");
        hashMap.put("private", "private");
        hashMap.put("if", "if");
        hashMap.put("else", "else");
        hashMap.put("for", "for");
        hashMap.put("return", "return");
        hashMap.put("this", "this");
        hashMap.put("new", "new");
        hashMap.put("null", "null");
        hashMap.put("true", "true");
        hashMap.put("false", "false");
    }

    public void actualizarLexema() {
        lexema = lexema + caracterActual;
    }

    private void actualizarCaracterActual() {
        caracterActual = procesadorDeArchivo.proximoCaracter();
    }

    public Token proximoToken() throws ExcepcionLexica {
        lexema = "";
        return e0();
    }

    private Token e0() throws ExcepcionLexica {
        nroColumnaDondeIniciaElLexema = procesadorDeArchivo.getNroColumna();
        if (Character.isUpperCase(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e1();
        } else if (Character.isLowerCase(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e2();
        } else if (Character.isDigit(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            contadorDigitos = 0;
            return e3();
        } else if (caracterActual == '\'') {
            actualizarCaracterActual();
            return e4();
        } else if (caracterActual == '"') {
            actualizarCaracterActual();
            return e8();
        } else if (caracterActual == '(') {
            actualizarLexema();
            actualizarCaracterActual();
            return e11();
        } else if (caracterActual == ')') {
            actualizarLexema();
            actualizarCaracterActual();
            return e12();
        } else if (caracterActual == '{') {
            actualizarLexema();
            actualizarCaracterActual();
            return e13();
        } else if (caracterActual == '}') {
            actualizarLexema();
            actualizarCaracterActual();
            return e14();
        } else if (caracterActual == ';') {
            actualizarLexema();
            actualizarCaracterActual();
            return e15();
        } else if (caracterActual == ',') {
            actualizarLexema();
            actualizarCaracterActual();
            return e16();
        } else if (caracterActual == '.') {
            actualizarLexema();
            actualizarCaracterActual();
            return e17();
        } else if (caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return e18();
        } else if (caracterActual == '+') {
            actualizarLexema();
            actualizarCaracterActual();
            return e20();
        } else if (caracterActual == '-') {
            actualizarLexema();
            actualizarCaracterActual();
            return e22();
        } else if (caracterActual == '*') {
            actualizarLexema();
            actualizarCaracterActual();
            return e24();
        } else if (caracterActual == '/') {
            actualizarLexema();
            actualizarCaracterActual();
            return e25();
        } else if (caracterActual == '%') {
            actualizarLexema();
            actualizarCaracterActual();
            return e30();
        } else if (caracterActual == '&') {
            actualizarLexema();
            actualizarCaracterActual();
            return e26();
        } else if (caracterActual == '|') {
            actualizarLexema();
            actualizarCaracterActual();
            return e28();
        } else if (caracterActual == '!') {
            actualizarLexema();
            actualizarCaracterActual();
            return e31();
        } else if (caracterActual == '>') {
            actualizarLexema();
            actualizarCaracterActual();
            return e33();
        } else if (caracterActual == '<') {
            actualizarLexema();
            actualizarCaracterActual();
            return e35();
        } else if (Character.isWhitespace(caracterActual) || procesadorDeArchivo.esEOL(caracterActual) || procesadorDeArchivo.esCR(caracterActual)) {
            actualizarCaracterActual();
            return e0();
        } else if (procesadorDeArchivo.esEOF(caracterActual)) {
            return e42();
        } else {
            actualizarLexema();
            actualizarCaracterActual();
            throw new ExcepcionLexica(lexema, procesadorDeArchivo.getNroLinea(), nroColumnaDondeIniciaElLexema, procesadorDeArchivo.getLineaActual(), "no es un símbolo válido");
        }
    }

    private Token e1() {
        if (Character.isLetter(caracterActual) || Character.isDigit(caracterActual) || caracterActual == '_') {
            actualizarLexema();
            actualizarCaracterActual();
            return e1();
        } else {
            Token token;
            if (hashMap.containsKey(lexema))
                token = new Token(hashMap.get(lexema), lexema, procesadorDeArchivo.getNroLinea());
            else
                token = new Token("idClase", lexema, procesadorDeArchivo.getNroLinea());
            return token;
        }
    }

    private Token e2() {
        if (Character.isLetter(caracterActual) || Character.isDigit(caracterActual) || caracterActual == '_') {
            actualizarLexema();
            actualizarCaracterActual();
            return e2();
        } else {
            Token token;
            if (hashMap.containsKey(lexema))
                token = new Token(hashMap.get(lexema), lexema, procesadorDeArchivo.getNroLinea());
            else
                token = new Token("idMetVar", lexema, procesadorDeArchivo.getNroLinea());
            return token;
        }
    }

    private Token e3() throws ExcepcionLexica {
        contadorDigitos++;
        if (Character.isDigit(caracterActual) && contadorDigitos < 10) {
            actualizarLexema();
            actualizarCaracterActual();
            return e3();
        } else if (contadorDigitos > 9) {
            throw new ExcepcionLexica(lexema, procesadorDeArchivo.getNroLinea(), nroColumnaDondeIniciaElLexema, procesadorDeArchivo.getLineaActual(), "los números enteros no pueden contener más de 9 digitos");
        } else {
            return new Token("intLiteral", lexema, procesadorDeArchivo.getNroLinea());
        }
    }

    private Token e4() throws ExcepcionLexica {
        if (caracterActual != '\\' && caracterActual != '\'' && !procesadorDeArchivo.esEOL(caracterActual) && !procesadorDeArchivo.esCR(caracterActual) && !procesadorDeArchivo.esEOF(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e5();
        } else if (caracterActual == '\\') {
            actualizarCaracterActual();
            return e6();
        } else {
                throw new ExcepcionLexica(lexema, procesadorDeArchivo.getNroLinea(), nroColumnaDondeIniciaElLexema, procesadorDeArchivo.getLineaActual(), "el literal caracter no se cerró");
        }
    }

    private Token e5() throws ExcepcionLexica {
        if (caracterActual == '\'') {
            actualizarCaracterActual();
            return e7();
        } else {
                throw new ExcepcionLexica(lexema, procesadorDeArchivo.getNroLinea(), nroColumnaDondeIniciaElLexema, procesadorDeArchivo.getLineaActual(), "el literal caracter no se cerró");
        }
    }

    private Token e6() throws ExcepcionLexica {
        if (!procesadorDeArchivo.esEOL(caracterActual) && !procesadorDeArchivo.esCR(caracterActual) && !procesadorDeArchivo.esEOF(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e5();
        } else {
            throw new ExcepcionLexica(lexema, procesadorDeArchivo.getNroLinea(), nroColumnaDondeIniciaElLexema, procesadorDeArchivo.getLineaActual(), "el literal caracter no se cerró");
        }
    }

    private Token e7() {
        return new Token("charLiteral", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e8() throws ExcepcionLexica {
        if (caracterActual == '"') {
            actualizarCaracterActual();
            return e10();
        } else if (caracterActual != '\\' && !procesadorDeArchivo.esEOL(caracterActual) && !procesadorDeArchivo.esCR(caracterActual) && !procesadorDeArchivo.esEOF(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e43();
        } else if (caracterActual == '\\') {
            actualizarLexema();
            actualizarCaracterActual();
            return e9();
        } else {
            throw new ExcepcionLexica(lexema, procesadorDeArchivo.getNroLinea(), nroColumnaDondeIniciaElLexema, procesadorDeArchivo.getLineaActual(), "el literal String no se cerró");
        }

    }

    private Token e9() throws ExcepcionLexica {
        if (caracterActual == '\\') {
            actualizarLexema();
            actualizarCaracterActual();
            return e9();
        } else if (!procesadorDeArchivo.esEOL(caracterActual) && !procesadorDeArchivo.esEOF(caracterActual) && !procesadorDeArchivo.esCR(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e43();
        } else {
            throw new ExcepcionLexica(lexema, procesadorDeArchivo.getNroLinea(), nroColumnaDondeIniciaElLexema, procesadorDeArchivo.getLineaActual(), "el literal String no se cerró");
        }
    }

    private Token e10() throws ExcepcionLexica {
        if (caracterActual == '"') {
            actualizarCaracterActual();
            nroLineaInicioBloqueDeTexto = procesadorDeArchivo.getNroLinea();
            return e44();
        } else {
            return new Token("stringLiteral", lexema, procesadorDeArchivo.getNroLinea());
        }
    }

    private Token e11() {
        return new Token("(", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e12() {
        return new Token(")", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e13() {
        return new Token("{", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e14() {
        return new Token("}", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e15() {
        return new Token(";", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e16() {
        return new Token(",", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e17() {
        return new Token(".", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e18() {
        if (caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return e19();
        } else {
            return new Token("=", lexema, procesadorDeArchivo.getNroLinea());
        }
    }

    private Token e19() {
        return new Token("==", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e20() {
        if (caracterActual == '+') {
            actualizarLexema();
            actualizarCaracterActual();
            return e21();
        } else {
            return new Token("+", lexema, procesadorDeArchivo.getNroLinea());
        }
    }

    private Token e21() {
        return new Token("++", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e22() {
        if (caracterActual == '+') {
            actualizarLexema();
            actualizarCaracterActual();
            return e23();
        } else {
            return new Token("-", lexema, procesadorDeArchivo.getNroLinea());
        }
    }

    private Token e23() {
        return new Token("--", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e24() {
        return new Token("*", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e25() throws ExcepcionLexica {
        if (caracterActual == '/') {
            actualizarLexema();
            actualizarCaracterActual();
            return e37();
        } else if (caracterActual == '*') {
            nroLineaInicioComentarioMultilinea = procesadorDeArchivo.getNroLinea();
            actualizarLexema();
            actualizarCaracterActual();
            return e38();
        } else {
            return new Token("/", lexema, procesadorDeArchivo.getNroLinea());
        }
    }

    private Token e37() throws ExcepcionLexica {
        if (!procesadorDeArchivo.esEOL(caracterActual) && !procesadorDeArchivo.esEOF(caracterActual) && !procesadorDeArchivo.esCR(caracterActual)) {
            actualizarCaracterActual();
            return e37();
        } else {
            return proximoToken();
        }
    }

    private Token e38() throws ExcepcionLexica {
        if (caracterActual != '*' && !procesadorDeArchivo.esEOF(caracterActual)) {
            actualizarCaracterActual();
            return e38();
        } else if (caracterActual == '*') {
            actualizarCaracterActual();
            return e39();
        } else {
            throw new ExcepcionLexica(lexema, nroLineaInicioComentarioMultilinea, nroColumnaDondeIniciaElLexema, "/*", "el comentario multilinea no se cerró");
        }
    }

    private Token e39() throws ExcepcionLexica {
        if (caracterActual == '*') {
            actualizarCaracterActual();
            return e39();
        } else if (caracterActual == '/') {
            actualizarCaracterActual();
            return proximoToken();
        } else if (!procesadorDeArchivo.esEOF(caracterActual)) {
            actualizarCaracterActual();
            return e38();
        } else {
            throw new ExcepcionLexica(lexema, nroLineaInicioComentarioMultilinea, nroColumnaDondeIniciaElLexema, "/*", "el comentario multilinea no se cerró");
        }
    }

    private Token e30() {
        return new Token("%", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e26() throws ExcepcionLexica {
        if (caracterActual == '&') {
            actualizarLexema();
            actualizarCaracterActual();
            return e27();
        } else {
            throw new ExcepcionLexica(lexema, procesadorDeArchivo.getNroLinea(), nroColumnaDondeIniciaElLexema, procesadorDeArchivo.getLineaActual(), "no es un símbolo válido");
        }
    }

    private Token e27() {
        return new Token("&&", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e28() throws ExcepcionLexica {
        if (caracterActual == '|') {
            actualizarLexema();
            actualizarCaracterActual();
            return e29();
        } else {
            throw new ExcepcionLexica(lexema, procesadorDeArchivo.getNroLinea(), nroColumnaDondeIniciaElLexema, procesadorDeArchivo.getLineaActual(), "no es un símbolo válido");
        }
    }

    private Token e29() {
        return new Token("||", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e31() {
        if (caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return e32();
        } else {
            actualizarLexema();
            actualizarCaracterActual();
            return new Token("!", lexema, procesadorDeArchivo.getNroLinea());
        }
    }

    private Token e32() {
        return new Token("!=", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e33() {
        if (caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return e34();
        } else {
            actualizarLexema();
            actualizarCaracterActual();
            return new Token(">", lexema, procesadorDeArchivo.getNroLinea());
        }
    }

    private Token e34() {
        return new Token(">=", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e35() {
        if (caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return e36();
        } else {
            actualizarLexema();
            actualizarCaracterActual();
            return new Token("<", lexema, procesadorDeArchivo.getNroLinea());
        }
    }

    private Token e36() {
        return new Token("<=", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e42() {
        return new Token("EOF", lexema, procesadorDeArchivo.getNroLinea());
    }

    private Token e43() throws ExcepcionLexica {
        if (caracterActual != '\\' && caracterActual != '"' && !procesadorDeArchivo.esEOL(caracterActual) && !procesadorDeArchivo.esCR(caracterActual) && !procesadorDeArchivo.esEOF(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e43();
        } else if (caracterActual == '\\') {
            actualizarLexema();
            actualizarCaracterActual();
            return e9();
        } else if (caracterActual == '"') {
            actualizarCaracterActual();
            return e49();
        } else {
            throw new ExcepcionLexica(lexema, procesadorDeArchivo.getNroLinea(), nroColumnaDondeIniciaElLexema, procesadorDeArchivo.getLineaActual(), "el literal String no se cerró");
        }
    }

    private Token e44() throws ExcepcionLexica {
        if (procesadorDeArchivo.esCR(caracterActual)) {
            actualizarCaracterActual();
            return e44();
        } else if (!procesadorDeArchivo.esEOL(caracterActual) && caracterActual != '\\' && caracterActual != '"' && !procesadorDeArchivo.esEOF(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e44();
        } else if (procesadorDeArchivo.esEOL(caracterActual)) {
            lexema = lexema + "\\n";
            actualizarCaracterActual();
            return e44();
        } else if (caracterActual == '\\') {
            actualizarLexema();
            actualizarCaracterActual();
            return e45();
        } else if (caracterActual == '"') {
            actualizarCaracterActual();
            return e46();
        } else {
            throw new ExcepcionLexica(lexema, nroLineaInicioBloqueDeTexto, nroColumnaDondeIniciaElLexema, procesadorDeArchivo.getLineaActual(), "el literal String como bloque de texto no se cerró");
        }
    }

    private Token e45() throws ExcepcionLexica {
        if (caracterActual == '\\') {
            actualizarLexema();
            actualizarCaracterActual();
            return e45();
        } else if (procesadorDeArchivo.esCR(caracterActual)) {
            actualizarCaracterActual();
            return e44();
        } else if (procesadorDeArchivo.esEOL(caracterActual)) {
            lexema = lexema + "\\n";
            actualizarCaracterActual();
            return e44();
        } else if (!procesadorDeArchivo.esEOF(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e44();
        } else {
            throw new ExcepcionLexica(lexema, nroLineaInicioBloqueDeTexto, nroColumnaDondeIniciaElLexema, procesadorDeArchivo.getLineaActual(), "el literal String como bloque de texto no se cerró");
        }
    }

    private Token e46() throws ExcepcionLexica {
        if (caracterActual == '"') {
            actualizarCaracterActual();
            return e47();
        } else {
            throw new ExcepcionLexica(lexema, nroLineaInicioBloqueDeTexto, nroColumnaDondeIniciaElLexema, procesadorDeArchivo.getLineaActual(), "el literal String como bloque de texto no se cerró");
        }
    }

    private Token e47() throws ExcepcionLexica {
        if (caracterActual == '"') {
            actualizarCaracterActual();
            return e48();
        } else {
            throw new ExcepcionLexica(lexema, nroLineaInicioBloqueDeTexto, nroColumnaDondeIniciaElLexema, procesadorDeArchivo.getLineaActual(), "el literal String como bloque de texto no se cerró");
        }
    }

    private Token e48() {
        return new Token("stringLiteral", lexema, nroLineaInicioBloqueDeTexto);
    }

    private Token e49() {
        return new Token("stringLiteral", lexema, procesadorDeArchivo.getNroLinea());
    }
}
