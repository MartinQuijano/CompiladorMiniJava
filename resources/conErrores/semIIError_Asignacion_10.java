//[Error:=|7]
// La expresion debe conformar con el tipo de la variable.
class A{
    private String cadena;

    dynamic void met1(){
        cadena = new B();
    }
}

class B{

}

class Init{
    static void main()
    { }
}