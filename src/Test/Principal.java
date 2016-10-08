package Test;

import Clases.Analiza;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by jeather on 25/04/16.
 */
public class Principal {
    public static void main(String[] args) {

        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Analiza analiza = new Analiza();

    }
}
