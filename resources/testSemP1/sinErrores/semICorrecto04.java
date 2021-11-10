// Multiples declaraciones validas, sobre-escritura de metodos de Object y System.

class A {
    dynamic void m3(A p1, B p2)
    {}  
}

class B extends A {
    dynamic void m3(A p1, B p2)
    {}  
}

class C extends B{
    dynamic void m3(A p1, B p2){}
}

class D extends System{
    static void printIln(int i){
    }

    static void printI(int i){
    }
}

class E {
    static void debugPrint(){
    }
}

class Init{
    static void main()
    { }
}




