//[Error:tipoB|10]
// Ya existe una variable con el mismo nombre.
class A {
    public int a1;
    
    static void m1(int p1)
    {
        B tipoB;
        {
            int tipoB;
        }
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


