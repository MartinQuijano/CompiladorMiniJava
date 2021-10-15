///[Error:num1|14]
// Una variable no puede tener el mismo nombre que una declarada en una superclase.
class A{
    private int num1;
    public String num2;
    public C num3;
}

class C{

}

class B extends A{
    private int num1;
}

class Init{
    static void main()
    { }
}
