// Prueba dos clases, distintos tipos de metodos, variables y asignaciones

class ClaseA{

    public int valor;
    private String saludo;

    ClaseA(int valor, String saludo){
        this.valor = valor;
        this.saludo = saludo + " -ClaseA";
    }

    static void display(int cantidad){
        if(cantidad > valor){
            mostrarCantidadFueraDeRango();
            valor--;
        } else mostrarPorPantalla(cantidad + valor + 10, "texto1", "texto2");
    }

    dynamic void mostrarCantidadFueraDeRango(){

    }

    static void mostrarPorPantalla(int total, String texto1, String texto2){
        if((true && false) || ((3+3) < 7))
            system.out.println(total);
        else
            system.out.println((Integer) total);
    }
    
}

class ClaseB extends ClaseA{
    ClaseB(int valor, String saludo){
        super(valor, saludo);
    }

    dynamic void mostrarCantidadFueraDeRango(){
        ;
    }
}

