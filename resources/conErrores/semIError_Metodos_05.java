///[Error:metodo1|16]
// Si un metodo sobreescribe uno de una superclase, tiene que coincidir toda la signatura, en este caso no coincide el tipo de retorno.
class A{
    A(){

    }

    static B metodo1(){

    }

}

class B extends A{

    static C metodo1(){

    }
}

class C{

}

class Init{
    static void main()
    { }
}
