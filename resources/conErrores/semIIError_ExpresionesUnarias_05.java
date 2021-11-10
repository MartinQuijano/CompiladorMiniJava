///[Error:!|8]
// El tipo de la expresion en una expresion unaria con el operador unario ! debe ser de tipo boolean.

class A{

    dynamic void met1(){
        boolean izquierda = true;
        boolean suma = !"cadena";
    }
}

class Init{
    static void main()
    { }
}