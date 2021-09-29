///[Error:Integer|7]
// Luego del cuarto 'Integer' en la linea 5 se espera ',' y se encuentra 'Integer'
class Clase<A<B, C>>extends ClaseDos<A>{
    Test<Integer, Integer, Integer> testUno;

    static void metodo(){
        Test<Integer, Integer, Integer> testDos = new Test<Integer Integer,Integer>();
    }
}

