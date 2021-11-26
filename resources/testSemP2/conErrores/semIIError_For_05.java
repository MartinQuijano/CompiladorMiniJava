//[Error:a|11]
// La variable 'a' creada por el for es accesible en el bloque de este pero fuera de ese bloque deja de existir.
class A {
    public int a1;
    
    dynamic void m1(int p1)
    {
        for(int a = 0; a < 20 ;a++){
            int x = a1 + a;
        }
        int x = a1 + a;
    }

    dynamic void getValue(){

    }

}



class Init{
    static void main()
    { }
}


