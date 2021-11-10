//[Error:x|7]
// El atributo 'x' en 'A' existe pero no se tiene acceso a el haciendo uso del acceso this.
class A {
    private int x;
    dynamic void m1() {
        x = 10;
        this.x = 15;
    }
}

class Init{
    static void main()
    { }
}

