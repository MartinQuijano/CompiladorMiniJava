///[Error:+|8]
// El tipo de la expresion en una expresion unaria con el operador unario + debe ser de tipo entero.

class A{

    dynamic void met1(){
        boolean izquierda = true;
        int suma = +izquierda;
    }
}

class Init{
    static void main()
    { }
}