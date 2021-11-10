//[Error:=|8]
// Error de compatibilidad de tipos int - String.
class A {
    public int a1;
    
    static void m1(int p1)
    {
        p1 = "String";
    }
}

class Init{
    static void main()
    { }
}


