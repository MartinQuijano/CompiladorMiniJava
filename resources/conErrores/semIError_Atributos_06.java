///[Error:num1|10]
// Una variable no puede tener el mismo nombre que una declarada en una superclase.
class A{
    private int num1;
    public String num2;
    public C num3;
}

class C extends B{
    public int num1;
}

class B extends A{
    private int num4;
    public String num5;
}

class Init{
    static void main()
    { }
}
