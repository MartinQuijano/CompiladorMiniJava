//[Error:B|9]
// No existe el constructor con los tipos de los parametros indicados, no conforman los tipos del segundo parametro.

class A {
    public B publicB;
    private B privateB;

    dynamic void met1DeA(){
        publicB = new B(new X(), new X());
    }
}

class B{
    B(X x, Y y){

    }
}

class X{

}

class Y extends X{

}

class Init{
    static void main()
    { }
}


