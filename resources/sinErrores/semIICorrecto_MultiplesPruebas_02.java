// Se prueban multiples sentencias validas.

class A {
    public B publicB;
    private B privateB;

    dynamic void met1DeA(){
        publicB = new B(new X(), new Y());
        privateB = new B(new Y(), new Y());

        publicB.met1DeB();
    }
}

class B extends A{

    private X privateX;
    private int num;
    public A a;

    B(X x, Y y){

    }

    dynamic void met1DeB(){
        ((B)a).met1DeB();
        ((B)a).met1DeA();

    }

    static int met2DeB(){
        return 6;
    }
}


class X{
    dynamic void met1DeX(){
        for(int a = 20; a >= 0; a--)
            for(int b = 20; b >= 0; b--){
                if(b > a){
                    return;
                }
            }
        return;
    }

    dynamic int met2DeX(){
        return ((6+11)*5);
    }
}

class Y extends X{

}

class Init{
    static void main()
    { }
}


