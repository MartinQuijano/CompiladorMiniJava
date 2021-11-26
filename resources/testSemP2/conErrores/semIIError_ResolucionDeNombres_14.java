//[Error:x|8]
// Existe un metodo con el nombre indicado en la clase C pero los tipos de los argumentos nos coinciden.

class A{

    static void met1(){
        B b = new B();
        getB().x(2);
    }

    static C getB(){

    }

}

class B{
    dynamic void x(char a){

    }
}

class C extends B{

}

class Init{
    static void main()
    { }
}


