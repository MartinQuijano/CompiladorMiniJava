//[Error:a1|8]
// La variable v1 no fue declarada, el metodo no es dinamico por lo tanto no la ve.
class A {
    public int a1;
    
    static void m1(int p1)
    {
        a1 = 4;
    }
}

class Init{
    static void main()
    { }
}


