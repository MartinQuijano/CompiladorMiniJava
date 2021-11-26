//[Error:=|10]
// Incompatibilidad de tipos al querer asignar un Int a una variable de tipo String.
class A {
    public String a1;
    
    static void m1(int p1)
    {
        B tipoB;
        String a1;
        a1 = tipoB.devuelveInt();
        p1 = 5;
    }
}

class B{
    public int a1;

    static int devuelveInt(){
        return 5;
    }
}

class Init{
    static void main()
    { }
}


