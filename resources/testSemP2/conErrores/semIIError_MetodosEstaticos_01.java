//[Error:metodoA2|9]
// No se puede hacer uso de un metodo no estatico en un ambiente estatico.
class A{
    public int pb;
    private int pv;

    static void metodoA(){
        B b = new B();
        metodoA2();
    }

    dynamic void metodoA2(){

    }
}

class B{

}

class Init{
    static void main()
    { }
}




