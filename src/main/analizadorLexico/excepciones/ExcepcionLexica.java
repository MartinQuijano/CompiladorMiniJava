package main.analizadorLexico.excepciones;

public class ExcepcionLexica extends Exception {

    private String lexema;
    private int nroLinea;
    private int nroColumna;
    private String lineaConError;
    private String mensajeError;

    public ExcepcionLexica(String lexema, int nroLinea, int nroColumna, String lineaConError, String mensajeError) {
        this.lexema = lexema;
        this.nroLinea = nroLinea;
        this.nroColumna = nroColumna;
        this.lineaConError = lineaConError;
        this.mensajeError = mensajeError;
    }

    public String getMensajeError(){
        return String.format("Error Léxico en linea %d columna %d: %s %s", nroLinea, nroColumna, lexema, mensajeError);
    }

    public String getDetalleErrorElegante(){
        return String.format("Detalle: " + lineaConError);
    }

    public String getMarcadorDeError(){
        String marcador = "";
        for(int i=0; i < (nroColumna + 8); i++)
            marcador = marcador + " ";
        return marcador + "^";
    }

    public String getCodigoError(){
        return String.format("[Error:%s|%d]", lexema, nroLinea);
    }
}
