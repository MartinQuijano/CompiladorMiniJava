class Init {

    static void main() {
        A a = new A();
        testInt();
        B b = new B();
        testBoolean();
        C c = new C();
        testString();
        D d = new D();
        testChar();
        C cd = new D();
        A ab = new B();
        b.printIln(10); // 10
        a.printCln('a'); // a
        cd.imprimirDeC(); // 3
        c.imprimirDeC(); // 1
        b.printIln(10); // 10
        ab.imprimirDeAB(); // 22
        c.staticImprimirDeC(); // 2
        cd.staticImprimirDeC(); // 2
        d.staticImprimirDeC(); // 4
    }

    static int testInt(){
        return 5;
    }
    static boolean testBoolean(){
        return true;
    }
    static String testString(){
        return "testString";
    }
    static char testChar(){
        return 'c';
    }

}

class A extends System{
    dynamic void imprimirDeAB(){
        debugPrint(11);
    }
}

class B extends A{
    dynamic void imprimirDeAB(){
        debugPrint(22);
    }
}

class C{
    public int c;

    dynamic void imprimirDeC(){
        c = 1;
        debugPrint(c);
    }

    static void staticImprimirDeC(){
        int c = 2;
        debugPrint(c);
    }
}

class D extends C{
    public int c;

    dynamic void imprimirDeC(){
        c = 3;
        debugPrint(c);
    }

    static void staticImprimirDeC(){
        int c = 4;
        debugPrint(c);
    }
}