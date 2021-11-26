//[Error:v1|7]
// El atributo v1 en 'a' existe pero no se tiene acceso a el.
class A{
    private int v1;
    dynamic void met1(){
        A a = new A();
        int x = a.v1;
    }
}

class Init{
    static void main()
    { }
}

