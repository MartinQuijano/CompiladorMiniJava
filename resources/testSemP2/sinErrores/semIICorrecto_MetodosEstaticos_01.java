// Llamadas válidas desde metodo estatico

class A{
    public int pb;
    private int pv;

     static void metodoA(){
        C c = new C();
        c.metodoC2();

        c.a.metodoA2();
        c.metodoC3().metodoA2();
    }

    dynamic void metodoA2(){

    }


}
class C{

    public A a;

    static void metodoC(){

    }

    dynamic void metodoC2(){

    }

    dynamic A metodoC3(){
        return new A();
    }

}

class Init{
    static void main()
    { }
}