///[Error:!=|9]
// La expresion binaria con el operador != espera 2 subexpresiones cuyos tipos conformen

class A{

    dynamic void met1(){
        B izquierda = null;
        C derecha = new C();
        int suma = izquierda != derecha;
    }
}

class B{

}

class C{

}

class Init{
    static void main()
    { }
}