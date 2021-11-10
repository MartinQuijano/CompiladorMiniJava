//[Error:;|8]
// El acceso en una llamada debe ser llamable.
class A {
    public int a1;
    
    dynamic void m1(int p1)
    {
        a1;
    }
}

class B{

}

class Init{
    static void main()
    { }
}


