// Codigo valido que define una clase.
/*
    Java class
 */
public class Test extends Object{
    private int param1;
    private int param2;

    public Test(p1, p2){
        this.param1 = p1;
        this.param2 = p2;
    }

    /**
     * Setter de param1
     * @param newP1
     */
    public void setParam1(int newP1){
        param1 = newP1;
    }

    // Getter param1
    public int getParam1(){
        return param1;
    }

    // Getter param2
    public int getParam2(){
        return param2;
    }
}