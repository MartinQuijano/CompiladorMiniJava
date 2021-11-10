// Se prueban multiples sentencias validas.

class A {
    public B publicB;
    public C publicC;
    public D publicD;
    private B privateB;
    private C privateC;
    private D privateD;

    dynamic void met1DeA(){
        publicB = new B(new X(), new Y());
        privateB = new B(new Y(), new Y());
        publicC = (D) new C();
        privateC = new D();
        publicD = new D();
        privateD = new D();

        publicB.met1DeB();
    }
}

class B{

    private X privateX;

    B(X x, Y y){

    }

    dynamic void met1DeB(){
        int a = 0;
        for(C c = new C(); a < 10; a++){
            int b = c.met1DeC() + a;
            {
                int d;
                b++;
                if(d > 5){
                    X claseX = new X();
                    claseX = (Y) new X();
                    claseX.met1DeX();
                }
                d = b;
                b = d;
            }
            int d = a;
            b = b + d;
        }
    }

    dynamic int met2DeB(){
        return privateX.met2DeX();
    }
}

class C extends B{
    dynamic int met1DeC(){

    }
}

class D extends C{

    D(){
        return;
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


