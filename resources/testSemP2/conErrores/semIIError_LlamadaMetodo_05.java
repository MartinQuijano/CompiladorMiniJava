//[Error:met1DeB|9]
// Se hace una llamada a un metodo que no tiene la misma cantidad de parametros que un metodo de la clase de la izquierda.

class A {
    public B publicB;
    private B privateB;

    dynamic void met1DeA(){
        publicB.met1DeB(true);
    }
}

class B extends A{

    public A a;

    dynamic void met1DeB(int c, boolean b){

    }

    static int met2DeB(){
        return 6;
    }
}


class Init{
    static void main()
    { }
}

