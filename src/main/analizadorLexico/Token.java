package main.analizadorLexico;

public class Token {

    private String nombre;
    private String lexema;
    private int linea;

    public Token(String nombre, String lexema, int linea){
        this.nombre = nombre;
        this.lexema = lexema;
        this.linea = linea;
    }

    public String getNombre(){
        return nombre;
    }

    public String getLexema(){
        return lexema;
    }

    public int getLinea(){
        return linea;
    }

}
