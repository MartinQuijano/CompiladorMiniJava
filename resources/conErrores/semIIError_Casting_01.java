//[Error:F|11]
// El tipo 'F' tipoClase no pertenece a una clase declarada
class A {
    public B a1;
    public B a2;

    dynamic void m1(int p1)
    {
        B c = new B();
        a1 = (C) c;
        a2 = (F) c;
    }
}

class B{
    dynamic void action(){

    }
}

class C extends B{
    dynamic void action(){

    }
}

class Init{
    static void main()
    { }
}


