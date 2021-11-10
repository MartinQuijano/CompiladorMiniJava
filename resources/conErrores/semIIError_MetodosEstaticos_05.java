//[Error:pb|8]
// No se puede hacer uso de un atributo no estatico en un ambiente estatico.
class A{
    public int pb;
    private int pv;

    static void metodoA(){
        pb = 5;
    }

    dynamic void metodoA2(){

    }
}

class Init{
    static void main()
    { }
}
