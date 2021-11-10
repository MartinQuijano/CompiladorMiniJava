//[Error:B|7]
// Se hace una llamada a un constructor que no tiene los mismos tipos de parametros que un constructor definido para la clase.

class A {

    dynamic void met1DeA(){
        B b = new B(new A(), 6);
    }
}

class B extends A{

    B(A a, boolean bool){

    }
}

class C{
    C(){

    }
}

class Init{
    static void main()
    { }
}

