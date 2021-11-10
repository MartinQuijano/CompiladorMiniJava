//[Error:a2|9]
// La clase B no tiene un atributo llamado a2.
class A {
    public int a1;
    
    static void m1(int p1)
    {
        B tipoB;
        tipoB.a2 = 5;
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


