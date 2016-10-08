package Clases;

/**
 * Created by jeather on 1/06/16.
 */
public class Pila {

    int inicioPila;
    int finalPila;
    int Pila[];

    //Metodo constructor
    public Pila(int tamaño)
    {
        this.inicioPila=-1;
        this.finalPila=tamaño;
        Pila =new int[tamaño];
    }//Fin del método constructor


    //Metodo para insertar en la pila
    public void PUSH(int dato)
    {
        try
        {
            if (PILALLENA()) {

            } else {
                inicioPila++;
                Pila[inicioPila] = dato;
                System.out.println("Dato metido: " +dato);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }//fin método push

    //Método para sacar valores de la pila
    public Object POP()
    {
        Object dato=null;
        try {

            if(PILAVACIA())
            {

            }
            else
            {
                dato = Pila[inicioPila];
                System.out.println("Dato elimiminado " + dato);
                inicioPila--;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return dato;

    }//Fin del método pop

    //Método para checar si la pila está llena
    public boolean PILALLENA()
    {

        if(inicioPila==finalPila-1){
            return true;
        }
        else{
            return false;
        }

    }//Fn del método de cheque de pila llena

    //Método para checar si la pila está vacia
    public boolean PILAVACIA()
    {

        if(inicioPila==-1){
            return true;
        }
        else
        {
            return false;
        }
    }//Fn del método de cheque de pila llena

    //Método que muestra el contenido de la pila
    public void MOSTRARPILA()
    {
        System.out.println();
        for (int i=inicioPila; i>=0; i--)
        {
            System.out.print(Pila[i] +" ");
        }

    }//Fin del método muestra pila

    //Regresa el último valor de la pila
    public int VALOR(){

        int valor = Pila[inicioPila];
        return valor;
    }

}//Final de la clase