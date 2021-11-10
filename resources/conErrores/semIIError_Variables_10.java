//[Error:=|10]
// Incompatibilidad de tipos al querer asignar un valor de tipo tipoClase C a una variable de tipo String.
class A {
    public String a1;
    
    static void m1(int p1)
    {
        B tipoB;
        String a1;
        a1 = tipoB.devuelveC();
        p1 = 5;
    }
}

class B{
    public int a1;

    dynamic C devuelveC(){
        return new C();
    }
}

class C{

}

class Init{
    static void main()
    { }
}


