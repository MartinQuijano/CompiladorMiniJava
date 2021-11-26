//[Error:return|6]
// El tipo de la expresion de retorno tiene que conformar con el tipo de retorno del metodo.
class A {

    dynamic B met1(){
        return new C();
    }
}

class B extends C{

}

class C{

}

class Init{
    static void main()
    { }
}


