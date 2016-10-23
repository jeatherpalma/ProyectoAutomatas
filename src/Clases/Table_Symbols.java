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
import java.util.ArrayList;
import java.util.List;

public class Table_Symbols {
    
	//Create table of symbols
		List<List<Object>> table_symbols;
		
	        //APuntador
	        int apuntador=0;
		public Table_Symbols(){	
			
	         table_symbols = new ArrayList<List<Object>>();   	     
	         //for add ArrayList on table
	         for(int i =0; i< 5; i++)
	   	 {
	            table_symbols.add(new ArrayList<Object>());
	         }
	    }
		
		public void set_Elements(int dir, String desc, String tipo, int ram, int apun )
		{
			 table_symbols.get(0).add(dir);
			 table_symbols.get(1).add(desc);
			 table_symbols.get(2).add(tipo);
			 table_symbols.get(3).add(ram);
			 table_symbols.get(4).add(apun);
	                 apuntador++;
		}
		
		public String declarado_identificador(String id)
		{
			String tipo=null;
			for(int i =0; i< apuntador; i++){
				if(table_symbols.get(1).get(i).toString().equals(id))
				{
					tipo = table_symbols.get(2).get(i).toString();
				}
		         
		     }
			return tipo;
			
		}
	}

