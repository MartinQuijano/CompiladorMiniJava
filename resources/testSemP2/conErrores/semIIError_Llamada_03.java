//[Error:met1DeB|19]
// Se hace una llamada a un metodo que no pertenece a la clase de la izquierda.

class A {
    public B publicB;
    private B privateB;

    dynamic void met1DeA(){

    }
}

class B extends A{

    public A a;

    dynamic void met1DeB(){
        ((B)a).met1DeB();
        (B)a.met1DeB();
    }

    static int met2DeB(){
        return 6;
    }
}


class Init{
    static void main()
    { }
}

