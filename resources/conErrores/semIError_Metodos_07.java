///[Error:metodo1|16]
// Si un metodo sobreescribe uno de una superclase, tiene que coincidir toda la signatura, en este caso no coinciden los parametros (por los nombres).
class A{
    A(){

    }

    static C metodo1(int a, String nom){

    }

}

class B extends A{

    static C metodo1(int a2, String nom2){

    }
}

class C{

}

class Init{
    static void main()
    { }
}
