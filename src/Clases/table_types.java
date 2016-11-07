/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author jeather
 */
public class table_types {
    /*
    103----INTEGER 
    104----FLOAT
    105----CHAR
    106----STRING
    107----BOOLEAN
    132----+
    133--- -
    134-- *
    118----AND
    117----OR
    119----!
    120----!=
    131----==
    126----=
    121----<
    122----<=
    123---->
    124---->=
    */
                
    int table [][]={
   //   *,+-   /   MOD  rela   && ||
        {103, 104, 103, 107,   500},
        {104, 104, 500, 107,   500},
        {104, 104, 500, 107,   500},
        {104, 104, 500, 107,   500},
        {500, 500, 500, 107,   500},
        {500, 500, 500, 107,   500},
        {500, 500, 500, 107,   500},
        {500, 500, 500, 107,   107}};
    public table_types()
    {
        
    }
    
    public int get_type(int type_one,int type_two, int operador)
    {
        System.out.println("Typo 1: " + type_one);
        System.out.println("Typo 2: " + type_two);
        System.out.println("Operador" +operador);
        int type=table[get_tupla(type_one, type_two)][get_column(operador)];
        return type;
    }
    
    public int get_tupla(int type_one, int type_two)
    {
        int column=10;
        //integer and integer 
        if(type_one==103 && type_two==103)
        {
            column=0;
        }
        //integer and flota
        if(type_one==103 && type_two==104)
        {
            column=1;
        }
        //float and integer 
        if(type_one==104 && type_two==103)
        {
            column=2;
        }
        //float and float
        if(type_one==104 && type_two==104)
        {
            column=3;
        }
        //char and charr
        if(type_one==105 && type_two==105)
        {
            column=4;
        }
        //char and string
        if(type_one==105 && type_two==106)
        {
            column=5;
        }
         //string and char
        if(type_one==106 && type_two==105)
        {
            column=6;
        }
         //string and string
        if(type_one==106 && type_two==106)
        {
            column=7;
        }
        //boolean and boolean
        if(type_one==107 && type_two==107)
        {
            column=7;
        }
        System.out.println("columna:" +column);
        return column;
    }
    
    public int get_column(int operator){
        
        int column=100;
        
        switch(operator)
        {
            case 132:
                column=0;
                break;
            case 133:
                column=0;
                break;
            case 134:
                column=0;
                break;
            case 135:
                column=1;
                break;
            case 118:
                column=4;
                break;
            case 117:
                column=4;
                break;
            case 131:
                column=3;
                break;
            default:
                column=3;
                break;
                
        }
        System.out.println("Tupla:" +column);
        return column;
    }
}
