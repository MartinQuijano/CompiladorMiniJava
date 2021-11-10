//[Error:x|7]
// El atributo 'x' en 'A' existe pero no se tiene acceso a el.
class A {
    private int x;
    dynamic void m1() {
        x = 10;
        this.x = 15; //ERROR en MINIJAVA!!!!
    }
}

class Init{
    static void main()
    { }
}


