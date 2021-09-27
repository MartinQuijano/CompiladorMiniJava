///[Error:}|10]
// Luego del '{' de la linea 7 se espera que se cierre, se encuentra '}' en la linea 8 el cual corresponde al metodo pero se considera que cierra el bloque de la linea 7, por lo tanto el error esta en el primer '{'
class Clase{

    static void metodo(){

        {
    }

}
