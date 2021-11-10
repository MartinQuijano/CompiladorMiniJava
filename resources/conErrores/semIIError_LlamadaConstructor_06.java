//[Error:B|8]
// Se hace una llamada a un constructor que no tiene los mismos tipos de parametros que un constructor definido para la clase.

class A {

    C c;
    dynamic void met1DeA(){
        B b = new B(c.met1DeC(new A()), false);
    }
}

class B extends A{

    B(C a, boolean bool){

    }
}

class C{
    C(){

    }

    dynamic A met1DeC(A a){}
}

class Init{
    static void main()
    { }
}

