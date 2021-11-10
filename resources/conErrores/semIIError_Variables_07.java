//[Error:=|9]
// Incompatibilidad de tipos al asignar un string a una variable de tipo int.
class A {
    public int a1;
    
    static void m1(int p1)
    {
        B tipoB;
        tipoB.a1 = "String";
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


