///[Error:>|7]
// Luego de 'Obj<' se espera un 'idClase' y se encuentra '>'
class Clase<A<B,C>> extends ClaseDos<A>{
    Test<Integer, Integer, Integer> testUno;
    Test<Integer, Integer, Integer> testDos;

        static Test<Integer, Integer, Integer> metodo(Obj<> obj){
            return null;
        }
}
