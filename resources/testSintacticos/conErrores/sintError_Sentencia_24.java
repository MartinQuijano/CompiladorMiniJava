///[Error:)|7]
// Como luego de 'cb' se encuentra ';' se considera que es un for comun (no foreach) pero no es correcto ya que se espera luego de la expresion 'a' un ';' y se encuentra ')'
class Clase{

    static void metodo(){

        for(ClaseB cb; a){

        }
    }

}
