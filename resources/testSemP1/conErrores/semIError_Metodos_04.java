///[Error:metodo1|16]
// Si un metodo sobreescribe uno de una superclase, tiene que coincidir toda la signatura, en este caso no coincide la forma.
class A{
    A(){

    }

    static B metodo1(){

    }

}

class B extends A{

    dynamic B metodo1(){

    }
}

class Init{
    static void main()
    { }
}
