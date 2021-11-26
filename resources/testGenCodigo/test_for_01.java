class Init{

    static void main() {
        int x = 5;
        for(int y = 0; y < x; y++)
            A a = new A();
    }

}

class A{
    A(){
        for(B b = new B(); b.cond(); b.z++)
            debugPrint(b.z);
    }
}

class B{
    public int z;

    B(){
        z = 0;
    }

    dynamic boolean cond(){
        if(z < 3)
            return true;
        else return false;
    }

}