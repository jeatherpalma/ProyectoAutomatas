/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author jeather
 */
public class table_cuadruples implements Serializable {
    List<List<Object>> table_cuadruple;
		
//APuntador of the table
    int apuntador=0;
    public table_cuadruples()
    {			
	 table_cuadruple = new ArrayList<List<Object>>();   	     
	 //for add ArrayList on table
	  for(int i =0; i< 5; i++)
	  {
	     table_cuadruple.add(new ArrayList<Object>());
	  }
    }		
    public void set_Elements(int contador, String cod_operacion, String op1, String op2, String respuesta)
    {
	 table_cuadruple.get(0).add(contador);
	 table_cuadruple.get(1).add(cod_operacion);
         table_cuadruple.get(2).add(op1);
	 table_cuadruple.get(3).add(op2);
	 table_cuadruple.get(4).add(respuesta);
         apuntador++;
    }
    public int get_apuntador()
    {
        return apuntador;
    }
    
    public void Carga()
    {
        for(int i =0; i< apuntador; i++){
		
            String Dir = table_cuadruple.get(0).get(i).toString();
            String Desc =table_cuadruple.get(1).get(i).toString();
            String tipo = table_cuadruple.get(2).get(i).toString();
            String Ram = table_cuadruple.get(3).get(i).toString();
            String Apunt = table_cuadruple.get(4).get(i).toString();
            Object [] newRow ={Dir,Desc, tipo, Ram, Apunt};
            Analiza.modelo_de_tabla_cuadruplos.addRow(newRow);
                      
        }
   }
}
