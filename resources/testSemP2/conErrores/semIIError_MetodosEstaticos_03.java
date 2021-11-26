//[Error:this|8]
// No se puede hacer uso de this en un ambiente estatico.
class A{
    public int pb;
    private int pv;

    static void metodoA(){
        this.pb;
    }

    dynamic void metodoA2(){

    }
}

class Init{
    static void main()
    { }
}



