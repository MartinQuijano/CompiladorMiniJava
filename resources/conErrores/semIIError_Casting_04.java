//[Error:B|10]
// El tipo B no conforma con el tipo de c. No se contempla este tipo de casting.
class A {
    public int a1;
    public B a2;

    dynamic void m1(int p1)
    {
        C c = new C();
        A a = (B)c;
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


