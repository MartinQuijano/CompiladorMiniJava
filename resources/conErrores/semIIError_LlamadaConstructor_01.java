//[Error:B|7]
// Se hace una llamada a un constructor que no tiene la misma cantidad parametros que un constructor definido para la clase.

class A {

    dynamic void met1DeA(){
        B b = new B(5);
    }
}

class B extends A{

    B(){

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

