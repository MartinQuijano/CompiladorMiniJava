//[Error:=|9]
// Es de asignacion el error: no conforman los tipos.

class A{

    private C y;

    dynamic void met1(){
        y = new B(5, "cadena");
    }

}

class B{
    private int b;

    B(int a, String b){

    }
}

class C{

}

class Init{
    static void main()
    { }
}


