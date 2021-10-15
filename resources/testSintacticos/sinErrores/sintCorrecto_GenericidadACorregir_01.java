// Prueba la genericidad de las correcciones, incluye genericidad en el casting y genericidad en un for


class Test<T, G, F>{
    private T numero;
    private T numero2;
    private Genericidad numero3;
    private Gene numero4;

    Test(){

    }
}

class Obj<Test<T, G, F>, ArrayList<ArrayList<A>, B>>{

}

class Test2<T, Genericidad, Gene> extends Test<T, Genericidad, Gene>{
    Test<Integer, Integer, Integer> testUno;
    Test<Integer, Integer, Integer> testDos;



    static Test<Integer, Integer, Integer> metodo(Obj<Integer> obj){
        Test<Integer, Integer, Integer> testTres;
        Test<Integer, Integer, Integer> testCuatro = new Test<>();
        Test<Integer, Integer, Integer> testCinco = new Test<Integer, Integer, Integer>();
        int var = (Class<A<B<F,G,H>,E,T>, C<D>>) x;
        for(Class<A<B<F,E>>,C> i = 10; i < 10; i++);

        return null;
    }
}
