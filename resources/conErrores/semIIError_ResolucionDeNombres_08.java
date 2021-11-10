//[Error:x|7]
// No existe el metodo x con los parametros indicados en el contexto actual.

class A{

    dynamic void met1(){
        x(true).y.z(3);
    }

    dynamic void x(){

    }
}

class Init{
    static void main()
    { }
}


