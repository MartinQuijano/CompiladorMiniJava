//[Error:x|8]
// No existe el metodo x en la clase B con los parametros indicados.

class A{

    static void met1(){
        B b = new B();
        b.x(2);
    }

}

class B{
    dynamic void x(char a){

    }
}

class Init{
    static void main()
    { }
}


