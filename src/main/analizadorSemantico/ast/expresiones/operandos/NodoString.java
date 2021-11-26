package main.analizadorSemantico.ast.expresiones.operandos;

import main.analizadorLexico.Token;
import main.analizadorSemantico.tablaDeSimbolos.TablaDeSimbolos;
import main.analizadorSemantico.tablaDeSimbolos.tipos.Tipo;
import main.analizadorSemantico.tablaDeSimbolos.tipos.TipoString;

public class NodoString extends NodoOperando {
    public NodoString(Token tokenDeDatos) {
        this.tokenDeDatos = tokenDeDatos;
        this.tipo = new TipoString();
    }

    public Tipo chequear() {
        return tipo;
    }

    public void generarCodigo() {
        String cadena = tokenDeDatos.getLexema();
        int memoriaAReservarEnElHeap = cadena.length() + 1;
        TablaDeSimbolos.insertarInstruccion("RMEM 1        ; reservo memoria para la referencia al string");
        TablaDeSimbolos.insertarInstruccion("PUSH " + memoriaAReservarEnElHeap + "        ; guardo en el tope de la pila la cantidad de memoria que tiene que reservar el heap (longitud de  cadena + caracter finalizador)");
        TablaDeSimbolos.insertarInstruccion("PUSH simple_malloc          ; pongo en el tope de la pila el label de simple_malloc");
        TablaDeSimbolos.insertarInstruccion("CALL         ; llamo a simple_malloc para que me reserve memoria en el heap");
        for(int i = 0; i < cadena.length() ; i++){
            TablaDeSimbolos.insertarInstruccion("DUP        ; duplico la referencia ya que al hacer storeref con cada caracter se consume");
            TablaDeSimbolos.insertarInstruccion("PUSH '" + cadena.charAt(i) + "'        ; pongo en el tope de la pila el caracter");
            TablaDeSimbolos.insertarInstruccion("STOREREF " + i + "        ; almaceno el caracter con posicion igual a la posicion en la cadena");
        }
        TablaDeSimbolos.insertarInstruccion("DUP        ; duplico la referencia ya que al hacer storeref se consume");
        TablaDeSimbolos.insertarInstruccion("PUSH 0        ; pongo en el tope de la pila el caracter");
        TablaDeSimbolos.insertarInstruccion("STOREREF " + cadena.length() + "        ; almaceno el caracter terminador con posicion igual a la longitud de la cadena");
    }
}
