///[Error:=|8]
// Los tipos de la asignacion no conforman.

class A{

    dynamic void met1(){
        int izquierda = 5;
        boolean suma = +izquierda;
    }
}

class Init{
    static void main()
    { }
}