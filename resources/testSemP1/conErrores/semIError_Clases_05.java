///[Error:A|3]
// La clase C no puede heredar de A porque se produce una herencia circular
class A extends B{

}

class B extends C{

}

class C extends A{

}

class Init{
    static void main()
    { }
}
