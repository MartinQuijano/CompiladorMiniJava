//[Error:b|9]
// La variable 'b' de 'y' no visibilidad publica.

class A{

    private B y;

    dynamic void met1(){
        y.b = 5;
    }

}

class B{
    private int b;
}

class Init{
    static void main()
    { }
}


