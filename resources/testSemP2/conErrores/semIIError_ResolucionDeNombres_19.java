//[Error:a|12]
// No existe la variable a, no es visible en este contexto.
class X{
    private B a;
}

class A{

    private B b;

    static void met1(){
        a.x(2) = 5;
    }

}

class B{
    dynamic void x(int num){

    }
}

class Init{
    static void main()
    { }
}


