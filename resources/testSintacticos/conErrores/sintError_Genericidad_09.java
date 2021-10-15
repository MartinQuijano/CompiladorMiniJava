///[Error:>|4]
// Luego del primero 'Test<' se espera un 'idClase' y se encuentra '>'
class Clase<A<B,C>> extends ClaseDos<A>{
    Test<> testUno = new Test<>();

}
