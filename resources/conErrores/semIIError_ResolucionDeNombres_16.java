//[Error:c|9]
// No existe la variable c.

class A{

    private B b;

    dynamic void met1(){
        c.x(2) = 5;
    }

}

class B{

}
class Init{
    static void main()
    { }
}


