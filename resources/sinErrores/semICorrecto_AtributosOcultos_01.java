//Control simple de declaracion de clases con nombres validos

class A{
    public int var1;
    private char var2;
    private String var3;
    public Init var4;
}

class B extends A{
    private int var1;
    public String var3;
}

class C extends B{
    private Init var4;
}

class Init{
    static void main()
    { }
}
