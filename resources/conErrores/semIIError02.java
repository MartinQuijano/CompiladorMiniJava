///[Error:=|11]
// Tipos incompatibles incompatibles en la asignacion: A no conforma con B - ln: 11

class A {

    public A v1;   
    
    dynamic void m1(B p1)
    
    {
        p1 = v1;

    }
         
    

}

class B extends A {}


class Init{
    static void main()
    { }
}


