class Init {

    static void main() {
        A a = new A();
        A ab = new B();
        B b = new B();

        ab.printVars();
        b.printVars();
        a.printVars();

        ab.printA(); // se espera 11
        b.printA(); // se espera 11

        a.printA2(); // se espera 2
        ab.printA2(); // se espera 0
        b.printA2(); // se espera 0
    }


}

class A extends System{
    public int a1;
    public int a2;
    private int a3;
    public int a4;

    A(){
        a1 = 1;
        a2 = 2;
        a3 = 3;
        a4 = 4;
    }

    dynamic void printVars(){
        debugPrint(a1);
        debugPrint(a2);
        debugPrint(a3);
        debugPrint(a4);
    }

    dynamic void printA(){
        debugPrint(a1);
    }

    dynamic void printA2(){
        debugPrint(a2);
    }
}

class B extends A{
    public int a2;
    public boolean a3;
    private int a4;

    B(){
        a1 = 11;
        a2 = 22;
        a3 = true;
        a4 = 44;
    }

    dynamic void printVars(){
        debugPrint(a1);
        debugPrint(a2);
        printBln(a3);
        debugPrint(a4);
    }

}
