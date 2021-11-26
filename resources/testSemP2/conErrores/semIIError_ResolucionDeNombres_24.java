//[Error:B|9]
// No existe el constructor con los tipos de los parametros indicados

class A {
    public B publicB;
    private B privateB;

    dynamic void met1DeA(){
        publicB = new B();
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


