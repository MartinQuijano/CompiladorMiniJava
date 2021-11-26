// se prueban multiples combinaciones de sentencias
class Init extends System {

    static void main() {
        B b = new B();
        A a = new A();

        for (int i = 0; i < a.getX(); i++)
            debugPrint((i * 2) % 3);

        C c = new A().c;
        if (a.x > 3) {
            D d = new D();
            debugPrint(d.sumarDosNumPorEntrada());
        } else {
            E e = new E();
        }
        {
            c.printSeparador();
            printS("El valor x de b es: ");
            {
                debugPrint(b.getX());
            }
            printS("Imprime el valor tapado de x en A, por eso el 0");
            b.c.printSeparador();
        }

        A ab = new B();
        printS("El valor x de ab es: ");
        debugPrint(ab.getX());
        printS("Imprime el valor tapado de x en A, por eso el 0");
        b.c.printSeparador();

        Y y = new Y();
        Z zy = new Y();

        c.printSeparador();
        printS("El valor x de z es: ");
        debugPrint(y.getX());
        printS("Imprime el valor de x en Z porque en este caso no lo tapa");
        b.c.printSeparador();

        printS("El valor x de zy es: ");
        debugPrint(zy.getX());
        printS("Imprime el valor de x en Z porque en este caso no lo tapa");
        b.c.printSeparador();

        b.c.getD().finCadena();
    }


}

class A extends System {
    public int x;
    public C c;

    A() {
        x = 5;
        c = new C();
    }

    dynamic int getX() {
        {
            return x;
        }
        int x = 777;
    }
}

class B extends A {
    private int x;

    B() {
        x = 55;
        c = new C();
    }

}

class Z extends System {
    public int x;

    Z() {
        x = 5;
    }

    dynamic int getX() {
        return x;
    }
}

class Y extends Z {

    Y() {
        x = 55;
    }

}

class C extends System {
    static void printSeparador() {
        printSln("");
        for (int i = 0; i < 20; i++) {
            if ((i != 0) && ((i % 5) == 0))
                printS("*");
            printS("-");
        }
        printSln("");
    }

    dynamic D getD() {
        return new D();
    }
}

class D extends C {

    public E e;

    dynamic int sumarDosNumPorEntrada() {
        String cadenaInfo = "--- Ingrese dos numeros de un digito seguidos, sin espacio (ej: 39, 3 y 9) | Ingrese q para salir ---";
        String nros = "Numeros: ";
        printSln(cadenaInfo);
        printS(nros);
        int num1 = read();
        int num2 = read();

        if (num1 == 113)
            return 0;

        if ((num1 < 48) || (num1 > 57) || (num2 < 48) || (num2 > 57)) {
            printSln("Error en la entrada de numeros");
            return sumarDosNumPorEntrada();
        }

        num1 = num1 - 48;
        num2 = num2 - 48;

        printS("Resultado: ");
        return num1 + num2;
    }

    dynamic void finCadena() {
        e = new E();
        e.finCadena();
        printSln("programa!");
    }
}

class E extends C {
    dynamic void finCadena() {
        printS("Fin ");
    }
}