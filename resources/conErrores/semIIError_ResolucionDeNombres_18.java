//[Error:x|8]
// El tipo de la izquierda tiene que ser un tipo de tipoClase.

class A{

    static void met1(){
        B b = new B();
        getB().x(2);
    }

    static int getB(){

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


