//[Error:x|8]
// No existe el metodo x en la clase B.

class A{

    static void met1(){
        B b = new B();
        b.x(2);
    }

}

class B{

}

class Init{
    static void main()
    { }
}


