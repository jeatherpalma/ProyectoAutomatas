package Clases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;

public class Cargador {
	
	
	public void set_Write(Table_constan table_constan, table_cuadruples ta_c, Table_Symbols table_Symbols,Codigo_class codigo_class) throws IOException
	{
		//Creamos el archivo
                String name = JOptionPane.showInputDialog("Tclea el nombre del archivo:");
		File file = new File(name+".hclass");
		
		//Creamos el archivo FileOutputStream
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		
		//Creamos el objeto que se guearda
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		
		System.out.println("paso");
		//"Se guardan el objeto
                objectOutputStream.writeObject(table_Symbols);
		objectOutputStream.writeObject(table_constan);
                objectOutputStream.writeObject(ta_c);
                objectOutputStream.writeObject(codigo_class);
		//se cierra el archivo de objetos
		objectOutputStream.close();
	}
	
	public void get_Read(String name)
	{
		ObjectInputStream inputStream=null;
		try{
			File file = new File(name);
			FileInputStream fileInputStream = new FileInputStream(file);
			inputStream = new ObjectInputStream(fileInputStream);
			
			while(true)
			{
				Table_Symbols p=(Table_Symbols)inputStream.readObject();
				p.Carga();
                                
                                Table_constan c=(Table_constan)inputStream.readObject();
				c.Carga();
                                
                                table_cuadruples k=(table_cuadruples)inputStream.readObject();
				k.Carga();
                                
                                Codigo_class cc =(Codigo_class)inputStream.readObject();
                                cc.get_Text();

			}
			
			
		}catch (IOException ie) {
			System.out.println(ie);
		}
		catch (ClassNotFoundException e) {
			System.out.println(e);
		} {
			
		}
	}
}
