//[Error:D|11]
// El tipo 'D' en el casting no conforma con el tipo de la variable c.
class A {
    public B a1;
    public B a2;

    dynamic void m1(int p1)
    {
        B c = new B();
        a1 = (C) c;
        a2 = (D) c;
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
class D{

}

class Init{
    static void main()
    { }
}


