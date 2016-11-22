package Test;

import Clases.Analiza;
import Clases.Cargador;

import javax.swing.*;

/**
 * Created by jeather on 25/04/16.
 */
public class Principal {
    public static void main(String[] args) {

        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            
        }
        Analiza analiza = new Analiza();
        

    }
}
