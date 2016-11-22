/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author jeather
 */
public class Codigo_class implements Serializable{

    String texto = "";
    
    public Codigo_class()
    {
        texto = Analiza.jTextAreaGuarda.getText();
    }

    public void get_Text()
    {
     Analiza.jTextAreaGuarda.setText(texto);
    }   
}//Fin del método Guardar archivo 
