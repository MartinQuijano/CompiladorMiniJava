//[Error:x|7]
// No existe el metodo x en el contexto actual.

class A{

    dynamic void met1(){
        x(2).y.z(3);
    }
}

class Init{
    static void main()
    { }
}


