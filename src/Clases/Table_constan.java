/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeather
 */
public class Table_constan {
    //Create table of symbols
		List<List<Object>> table_const;
		
	        //APuntador
	        int apuntador=0;
		public Table_constan(){	
			
	         table_const = new ArrayList<List<Object>>();   	     
	         //for add ArrayList on table
	         for(int i =0; i< 5; i++)
	   	 {
	            table_const.add(new ArrayList<Object>());
	         }
	    }
		
		public void set_Elements(int dir, String desc, String tipo, int ram, int apun )
		{
			 table_const.get(0).add(dir);
			 table_const.get(1).add(desc);
			 table_const.get(2).add(tipo);
			 table_const.get(3).add(ram);
			 table_const.get(4).add(apun);
	                 apuntador++;
		}
		
		
}
