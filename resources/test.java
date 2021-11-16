class Init{

    static void main() {
        A a = new A();
        int x;
        int y;
        x = a.met1();
        y = x + a.met1();
        debugPrint(y);
    }

}

class A{

    dynamic int met1(){
        return 9;
    }
}
