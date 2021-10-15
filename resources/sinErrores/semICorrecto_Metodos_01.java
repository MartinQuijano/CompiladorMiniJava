// Se prueba que se puede heredar el metodo main correctamente.
class A {
    dynamic void m3(A p1, B p2)
    {}

    static void main(){}
}
class B extends A {
    dynamic void m3(A p1, B p2)
    {}  
}

class Init{

}




