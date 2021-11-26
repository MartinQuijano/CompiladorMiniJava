//[Error:a1|9]
// La clase B no tiene un metodo llamado a1.
class A {
    public int a1;
    
    static void m1(int p1)
    {
        B tipoB;
        tipoB.a1();
        p1 = 5;
    }
}

class B{
    public int a1;
}

class Init{
    static void main()
    { }
}


