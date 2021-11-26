//[Error:=|7]
// La expresion debe conformar con el tipo de la variable.
class A{
    private int entero;

    A(){
        entero = new B();
    }
}

class B{

}

class Init{
    static void main()
    { }
}