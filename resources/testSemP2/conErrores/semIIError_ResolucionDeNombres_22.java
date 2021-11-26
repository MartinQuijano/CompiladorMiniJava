//[Error:B|9]
// No coinciden los tipos de los parametros del constructor.

class A{

    private B y;

    static void met1(){
        new B(5, 1);
    }

}

class B{
    private int b;

    B(int a, String b){

    }
}

class Init{
    static void main()
    { }
}


