///[Error:A|3]
// La clase B no puede heredar de A porque se produce una herencia circular
class A extends B{

}

class B extends A{

}

class Init{
    static void main()
    { }
}
