class A2{
    public int pb;
    private int pv;

    public static void metodoA(){
        C c = new C();
        c.metodoC2();

        c.a.metodoA2();
        c.metodoC3().metodoA2();
     //   metodoA2();
    }

    public void metodoA2(){

    }
}

class B2{

    public void metodoB(){
        A2.metodoA();
    }
}

class C{

    public A2 a = new A2();

    public static void metodoC(){

    }

    public void metodoC2(){

    }

    public A2 metodoC3(){
        return new A2();
    }
}