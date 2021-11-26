///[Error:!=|9]
// La expresion binaria con el operador != espera 2 subexpresiones de tipos conformantes

class A{

    dynamic void met1(){
        char izquierda = 'c';
        boolean derecha = true;
        int suma = izquierda != derecha;
    }
}


class Init{
    static void main()
    { }
}