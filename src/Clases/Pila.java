package Clases;

/**
 * Created by jeather on 1/06/16.
 */
public class Pila {
        
    //Atributos de la pila
    int inicioPila;
    int finalPila;
    int pila[];

    //Metodo constructor
    public Pila(int tamaño)
    {
        this.inicioPila=-1;
        this.finalPila=tamaño;
        pila =new int[tamaño];
    }//Fin del método constructor


    //Metodo para insertar en la pila
    public void PUSH(int dato)
    {
        try
        {
            if (PILALLENA()) {

            } else {
                inicioPila++;
                pila[inicioPila] = dato;
                System.out.println("Dato metido: " +dato);
            }
        }catch(Exception e)
        {
            
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
                dato = pila[inicioPila];
                System.out.println("Dato elimiminado " + dato);
                inicioPila--;
            }

        }catch (Exception e){
            
        }
        return dato;

    }//Fin del método pop

    //Método para checar si la pila está llena
    public boolean PILALLENA()
    {

        return inicioPila==finalPila-1;

    }//Fn del método de cheque de pila llena

    //Método para checar si la pila está vacia
    public boolean PILAVACIA()
    {

        return inicioPila==-1;
    }//Fn del método de cheque de pila llena

    //Método que muestra el contenido de la pila
    public void MOSTRARPILA()
    {
        System.out.println();
        for (int i=inicioPila; i>=0; i--)
        {
            System.out.print(pila[i] +" ");
        }

    }//Fin del método muestra pila

    //Regresa el último valor de la pila
    public int VALOR(){

        int valor = pila[inicioPila];
        return valor;
    }

}//Final de la clase