///[Error:!|8]
// La expresion en la expresion unaria debe ser de tipo boolean.

class A{

    dynamic void met1(){
        boolean izquierda = true;
        int suma = !"cadena";
    }
}

class Init{
    static void main()
    { }
}