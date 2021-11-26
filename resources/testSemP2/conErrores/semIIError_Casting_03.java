//[Error:x|10]
// No es un problema de casting en si, mas bien de visibilidad de atributos. El atributo x no se encuentra.
class A {
    public int a1;
    public B a2;

    dynamic void m1(int p1)
    {
        B c = new B();
        a1 = ((C)c).x;
    }
}

class B{
    private int x;
    dynamic void action(){

    }

    dynamic int enB(){return 4;}
}

class C extends B{

    dynamic int enC(){return 6;}
}


class Init{
    static void main()
    { }
}


