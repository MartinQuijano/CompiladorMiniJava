///[Error:metodo|7]
// Luego de 'Test<Integer, Integer, Integer' se espera '>' y se encuentra 'metodo'
class Clase<A<B,C>> extends ClaseDos<A>{
    Test<Integer, Integer, Integer> testUno;
    Test<Integer, Integer, Integer> testDos;

        dynamic Test<Integer, Integer, Integer metodo(Obj<Integer> obj){
            return null;
        }
}
