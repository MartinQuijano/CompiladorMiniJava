///[Error:>|9]
// La expresion binaria con el operador > espera 2 subexpresiones de tipo int

class A{

    dynamic void met1(){
        B izquierda = null;
        B derecha = new B();
        int suma = izquierda > derecha;
    }
}

class B{

}

class Init{
    static void main()
    { }
}