package Clases;


import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;



public class Analiza extends JFrame {
	Scanner sc = new Scanner(System.in);
//Variable que guarda el token d eentrada
       int tokenEntrada;
        //Jframe de la ventana principal
	JFrame jFrame;
        /*Areas en donde se teclea el codigo y áres en donde aparece el código
        analizdo lexicamente y sintacticamente*/
	JTextArea jTextAreaGuarda,jTextAreaImprime,jTextAreaSintactico;
        //Etiquetas de la ventana
	JLabel jLabelTeclea,jLabelResultado,jLabelEtiquetaSintactico;
        //Scroles para las áreas
        JScrollPane jScrollPaneGuarda,jScrollPaneSintactico;
        //Color de fondo de la ventana
        Color color = new Color(220000);
        //Token terminal
    	int Terminal=0;
        //Sacara la ventana y su dimensión de la computadora
    	Toolkit t = Toolkit.getDefaultToolkit();
    	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        //Puntero que guarda la posición en el código
        int puntero_codigo =0;
        //Variables para el método sintáctico
        boolean bandera;
        //Objeto de la clase pila
        Pila pila;
        //Palabra
        String description;
        //Creating of stacks for analizer
        //Stacks of types
        ArrayList<Object> types = new ArrayList<Object>();
        //Stack of avails
        ArrayList<Object> avails = new ArrayList<Object>();
        //operators
        ArrayList<Object> operators = new ArrayList<Object>();
        //jumps
        ArrayList<Object> jumps = new ArrayList<Object>();
        //Operating
        ArrayList<Object> operands= new ArrayList<Object>();
        
        //table of symbol
        Object [][] symbols = new Object[100][5];
        
        //table cuadruplos
        Object [][] cuadruplos = new Object[100][4];
        
        //APuntador de tabla simbolos
        int apuntador_tabla_simbolos=0;
	public Analiza()
	{
		//Ventana principal
		jFrame = new JFrame();
		jFrame.setSize(dimension);
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jFrame.setLayout(null);
        	jFrame.getContentPane().setBackground(color);


		//Etiqeuta que indica el espacio en donde se teclea el código
		jLabelTeclea = new JLabel("Texto que será analizado");
		jLabelTeclea.setForeground(Color.white);
        	jLabelTeclea.setFont(new Font("Arial",Font.ITALIC,15));
		jLabelTeclea.setBounds(20,10,200,30);

        try {
		//Area en donde se teclea el código
            	jTextAreaGuarda = new JTextArea();
            	jTextAreaGuarda.setFont(new Font("Arial", Font.BOLD, 15));
            	jScrollPaneGuarda = new JScrollPane(jTextAreaGuarda);
            	jScrollPaneGuarda.setBounds(20, 50, 350, 600);
        }catch (Exception e)
        {
           
        }

		//Etiqueta que indica el áre en donde aparece los análisi del código
		jLabelResultado = new JLabel("Texto analizado");
		jLabelResultado.setForeground(Color.white);
		jLabelResultado.setBounds(450,10,200,30);
        	jLabelResultado.setFont(new Font("Arial",Font.ITALIC,15));


		//Area de impresión del análisis léxico		
		jTextAreaImprime = new JTextArea();
        	jTextAreaImprime.setEnabled(false);
        	jTextAreaImprime.setFont(new Font("Arial",Font.BOLD,15));
        	jTextAreaImprime.setForeground(Color.black);
		JScrollPane jScrollPaneIMprime = new JScrollPane(jTextAreaImprime);
		jScrollPaneIMprime.setBounds(430,50,350,600);

        	//Etiqueta del sintactico
        	jLabelEtiquetaSintactico = new JLabel("Análisis Sintáctico");
        	jLabelEtiquetaSintactico.setForeground(Color.white);
        	jLabelEtiquetaSintactico.setBounds(860,10,200,30);
        	jLabelEtiquetaSintactico.setFont(new Font("Arial",Font.ITALIC,15));
        	
		//TextArea para imprimir los resultados del sintactico
        	jTextAreaSintactico = new JTextArea();
        	jTextAreaSintactico.setEnabled(false);
        	jTextAreaSintactico.setFont(new Font("Arial",Font.BOLD,15));
        	jTextAreaSintactico.setForeground(Color.black);
        	jScrollPaneSintactico = new JScrollPane(jTextAreaSintactico);
        	jScrollPaneSintactico.setBounds(840,50,380,600);


        	//Etiqueta que nos permite meter un icono de abrir
        	JLabel jLabeAbrir = new JLabel();
        	jLabeAbrir.setBounds(1235,50,100,100);
        	ImageIcon imageIconNota = new ImageIcon(getClass().getResource("/recursos/Abrir.png"));
        	ImageIcon imageIconExtrad = new ImageIcon(imageIconNota.getImage().getScaledInstance(jLabeAbrir.getWidth(),jLabeAbrir.getHeight(),Image.SCALE_SMOOTH));
        	jLabeAbrir.setIcon(imageIconExtrad);
        	jLabeAbrir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        	jLabeAbrir.setToolTipText("Teclea para abrir un programa de h++");
        	
		//Evento del mouse para controlar el clik en la etiqueta abrir
		jLabeAbrir.addMouseListener(new MouseAdapter() {
            	@Override
            	public void mouseClicked(MouseEvent e) {
               		Abrir();
                        puntero_codigo = 0;
            	}});

        	//Etiqueta para borrar
        	JLabel jLabelBorra = new JLabel();
        	jLabelBorra.setBounds(1235,200,100,100);
		//Se carga el icono
        	ImageIcon imageIconBorra = new ImageIcon(getClass().getResource("/recursos/eliminar.png"));
                ImageIcon imageIconBorra2 = new ImageIcon(imageIconBorra.getImage().getScaledInstance(jLabelBorra.getWidth(),
                jLabelBorra.getHeight      (),Image.SCALE_SMOOTH));
		//Se agrega a la etiqueta        	
		jLabelBorra.setIcon(imageIconBorra2);
        	jLabelBorra.setCursor(new Cursor(Cursor.HAND_CURSOR));
        	jLabelBorra.setToolTipText("Teclea para borrar el contenido de las tres áreas");

		//Evento del mouse para controlar el clik en la etiqueta borrar todo     	
		jLabelBorra.addMouseListener(new MouseAdapter() {
            	@Override
            	public void mouseClicked(MouseEvent e) {
                	jTextAreaSintactico.setText("");
                	jTextAreaImprime.setText("");
                	jTextAreaGuarda.setText("");
                        puntero_codigo = 0;
            	}});

		//Etiqueta que sirve para añadir el icono de guardar
		JLabel jLabelGuarda = new JLabel();
		jLabelGuarda.setBounds(1235,350,100,100);
		//Se crea el icono
		ImageIcon imageGuarda = new ImageIcon(getClass().getResource("/recursos/guardar.png"));
		ImageIcon imageGuarda2 = new ImageIcon(imageGuarda.getImage().getScaledInstance(jLabelGuarda.getWidth(),jLabelGuarda.getHeight(),Image.SCALE_SMOOTH));
		//Se asigna la etiqueta		
		jLabelGuarda.setIcon(imageGuarda2);
		jLabelGuarda.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jLabelGuarda.setToolTipText("Teclea para guardar tu código");
		jLabelGuarda.addMouseListener(new MouseAdapter() {
		//Evento del mouse para controlar el clik en la etiqueta borrar todo     	
		@Override
		public void mouseClicked(MouseEvent e) {
		      	save();
                        puntero_codigo = 0;
		}});


		//Etiqueta que srive para añadir el icono de jecución
		JLabel jLabelEjecuta = new JLabel();
		jLabelEjecuta.setBounds(1235,500,100,100);
		//Se crea el icono de ejecucion
		ImageIcon imageEjecuta = new ImageIcon(getClass().getResource("/recursos/ejecuta.png"));
		ImageIcon imageEjecuta2 = new ImageIcon(imageEjecuta.getImage().getScaledInstance(jLabelEjecuta.getWidth(),
                jLabelEjecuta.getHeight(),Image.SCALE_SMOOTH));
		//Se le asigna el icono a la etiqueta		
		jLabelEjecuta.setIcon(imageEjecuta2);
		jLabelEjecuta.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jLabelEjecuta.setToolTipText("Teclea para que se analice tu código");
		//Evento del mouse para controlar el clik en la etiqueta de ejecución    	
		jLabelEjecuta.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
                        puntero_codigo = 0;
			String texto = jTextAreaGuarda.getText();
		        if (texto.matches("[[ ]*[\n]*[\t]]*")) 
			{
				//compara si en el JTextArea hay texto sino muestrtra un mensaje en pantalla
		        	JOptionPane.showMessageDialog(null,"No hay texto para ejecutar", "Error", JOptionPane.ERROR_MESSAGE);
		        }else {
				//Se limṕian las areas
		            	jTextAreaImprime.setText("");
		            	jTextAreaSintactico.setText("");
				//Se abre el archivo que vamos a jecutar
		            	File f = new File("Salida.txt");
		            	double d = 18.76353;
		        	try {
		                FileOutputStream fis = new FileOutputStream(f);
		                DataOutputStream dos = new DataOutputStream(fis);
		                dos.writeBytes(jTextAreaGuarda.getText());
		                dos.close();
		            	} catch (FileNotFoundException ex) {
		                System.out.println("No se encontro el archivo");
		            	} catch (IOException ex) {
		                System.out.println("Error al escribir");
		            }
		            
				
		            	Sintactico_version2();

		        }
		    }
		});

		//Cargamos componentes al JFRame
		jFrame.add(jLabelTeclea);
		jFrame.add(jScrollPaneGuarda);
		jFrame.add(jLabelResultado);
		jFrame.add(jScrollPaneIMprime);
		jFrame.add(jScrollPaneSintactico);
		jFrame.add(jLabelEtiquetaSintactico);
		jFrame.add(jLabeAbrir);
		jFrame.add(jLabelBorra);
		jFrame.add(jLabelGuarda);
		jFrame.add(jLabelEjecuta);

		//Se carga un icono        	
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/recursos/analisis.png"));
        	jFrame.setIconImage(icon);

        	//Porpiedades del jframe
		jFrame.setLocationRelativeTo(null);
 	       	jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		jFrame.setVisible(true);
        	jFrame.setResizable(true);
	}
    

    //Metodo que obtiene el código que será analizado
    public String  get_codigo()
     {
         
         String codigo_a_ejecutar="";

    	
        String nombreFichero ="Salida.txt";
	File file = new File(nombreFichero);
	FileReader fileReader=null;

	try {
            fileReader = new FileReader(file);
            int contador_while_palabra=0;

            //While que lee el archivo y lo guarda en toda la palabra                                    
            while (contador_while_palabra<=file.length()-1) {
              
                int caracter = fileReader.read();
                codigo_a_ejecutar += (char) caracter;
                contador_while_palabra++;

            }//Fin del while
            
            
        }
        catch (FileNotFoundException e)
	{
            //Fichero no encontrado
        }
        catch (IOException e)
        {

        }
        finally {
	       try {
                    if(fileReader !=null){
			   fileReader.close();
		    }
	        }catch (Exception e)
		{

		}
            return codigo_a_ejecutar;
}
     }//Fin del método analizador
    
    public int Lexico(String palbra)
    {
        int Terminal=0;
        int aux=puntero_codigo-1;
        int estados = 0;
        String analiza="";
        int columna;
        

        while (estados<=18) {
    
            int caracter = palbra.charAt(puntero_codigo);
            analiza += (char) caracter;
            columna = Relaciona((char) caracter);
            estados = Matriz(estados, columna);
            puntero_codigo++;
            aux++;
            if(puntero_codigo==palbra.length() && estados<=18 )
            {

            estados =Matriz(estados,27);

            }
        }//Fin del while del analisis lexico
        

                            if(estados==100)
                            {

                                if(puntero_codigo==palbra.length()&& (int)analiza.charAt(analiza.length()-1)>=(int)'A'&&(int)analiza.charAt(analiza.length()-1)<=(int)'Z')
                                {


                                    analiza+=" ";
                                }else{
                                      puntero_codigo=aux;
                                }
                                
                                String palabra = analiza.substring(0,analiza.length()-1);
                                if(palabra.trim().equals("CLASS")){

                                    Terminal = 100;
                                    description=analiza.substring(0,analiza.length()-1).trim();

                                }
                                if(palabra.trim().equals("BEGIN"))
                                {
                                    
                                    Terminal=101;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("ENDCLASS"))
                                {
                                    Terminal=102;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("INTEGER"))
                                {
                                    Terminal=103;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("FLOAT"))
                                {
                                    Terminal=104;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("CHAR"))
                                {
                                    Terminal=105;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("STRING"))
                                {
                                    Terminal=106;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("BOOLEAN"))
                                {
                                    Terminal=107;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("READ"))
                                {
                                    Terminal=108;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("PRINT"))
                                {
                                    Terminal=109;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("IF"))
                                {
                                    Terminal=110;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("ENDIF"))
                                {
                                    Terminal=111;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("ELSE"))
                                {
                                    Terminal=112;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("WHILE"))
                                {
                                    Terminal=113;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("ENDWHILE"))
                                {
                                    Terminal=114;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("FOR"))
                                {
                                    Terminal=115;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("ENDFOR"))
                                {
                                    Terminal=116;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("TRUE"))
                                {
                                    Terminal=141;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                if(palabra.trim().equals("FALSE"))
                                {
                                    Terminal=142;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }

                                if(Terminal==0)
                                {
                                    jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Identififcador\n");
                                    Terminal=130;
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                else{
                                    jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Palabra Recervada\n");
                                    description=analiza.substring(0,analiza.length()-1).trim();
                                }
                                

                              
                              

                            }
                            if(estados==101)
                            {


                                if(puntero_codigo==palbra.length()&& (int)analiza.charAt(analiza.length()-1)>=(int)'a'&&(int)analiza.charAt(analiza.length()-1)<=(int)'z'
                                        || (int)analiza.charAt(analiza.length()-1)>=(int)'A'&&(int)analiza.charAt(analiza.length()-1)<=(int)'Z'
                                        || (int)analiza.charAt(analiza.length()-1)>=(int)'0'&&(int)analiza.charAt(analiza.length()-1)<=(int)'9')
                                {
                                        analiza+=" ";
                                }else{
                                    puntero_codigo=aux;

                                }
                                
                                jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Identificador\n");
                                Terminal=130;
                                description=analiza.substring(0,analiza.length()-1).trim();
                                
                            }
                            if(estados==102)
                            {
                                if(puntero_codigo==palbra.length() && (int)analiza.charAt(analiza.length()-1)>=(int)'0'&&(int)analiza.charAt(analiza.length()-1)<=(int)'9')
                                {
                                    analiza+=" ";

                                }else{
                                    puntero_codigo=aux;
                                }
                                
                                jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Entero\n");
                                Terminal = 136;
                                description=analiza.substring(0,analiza.length()-1).trim();
                                
                            }
                            System.out.println(estados);

                            if(estados==103)
                            {


                                if(puntero_codigo==palbra.length() && (int)analiza.charAt(analiza.length()-1)>=(int)'0'&&(int)analiza.charAt(analiza.length()-1)<=(int)'9')
                                {


                                }else{
                                    puntero_codigo=aux;
                                }
                                
                                jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Número real\n");
                                Terminal = 137;
                                description=analiza.substring(0,analiza.length()-1).trim();
                            }
                            if(estados==104)
                            {
                                if(puntero_codigo==palbra.length()-1)
                                {

                                }else{
                                    puntero_codigo=aux;
                                }
                              
                                jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Número con notación cientifica\n");
                                Terminal = 138;
                                description=analiza.substring(0,analiza.length()-1).trim();
                            }
                            if(estados==105)
                            {

                                System.out.print(palbra);
                                System.out.println(">-----Operador aritmetico suma");
                                jTextAreaImprime.append(analiza.trim()+">----Operador aritmetico suma\n");
                                Terminal = 132;
                                description=analiza.trim();
                            }
                            if(estados==106)
                            {
                                jTextAreaImprime.append(analiza.trim()+">----Operador aritmetico resta\n");
                                Terminal = 133;
                                description=analiza.trim();
                            }
                            if(estados==107)
                            {
                                jTextAreaImprime.append(analiza.trim()+">----Operador aritmetico multiplicación\n");
                                Terminal = 134;
                                description=analiza.trim();
                                
                            }
                            if(estados==108)
                            {
                                
                                jTextAreaImprime.append(analiza.trim()+">----Operador aritmetico división\n");
                                Terminal = 135;
                                description=analiza.trim();
                                
                            }
                            if(estados==109)
                            {
                                
                                jTextAreaImprime.append(analiza.trim()+">----Operador aritmetico módulo\n");
                                Terminal=69;
                            }
                            if(estados==110)
                            {
                                
                                jTextAreaImprime.append(analiza.trim()+">----Operador lógico AND\n");
                                Terminal = 118;
                                description=analiza.trim();
                                
                            }
                            if(estados==111)
                            {
                                jTextAreaImprime.append(analiza.trim()+">----Operador lógico OR\n");
                                Terminal = 117;
                                description=analiza.trim();
                                
                            }
                            if(estados==112)
                            {
                                
                                jTextAreaImprime.append(analiza.trim()+">----Operador relacional !\n");
                                Terminal = 119;
                                description=analiza.trim();
                                
                            }
                            if(estados==113)
                            {
                                
                                jTextAreaImprime.append(analiza.trim()+">----Operador relacional diferente de !=\n");
                                Terminal = 120;
                                description=analiza.trim();
                                
                            }
                            if(estados==114)
                            {
                                if(puntero_codigo==palbra.length()-1)
                                {
                                    analiza+=" ";

                                }
                                
                                jTextAreaImprime.append(analiza.trim()+">----Operador relacional de igual a\n");
                                Terminal = 131;
                                description=analiza.trim();
                            }
                            if(estados==115)
                            {
                                if(puntero_codigo==palbra.length()-1)
                                {
                                    analiza+=" ";

                                }else{
                                    puntero_codigo=aux;
                                }
                                
                                jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Operador relacional de asignación\n");
                                Terminal = 126;
                                description=analiza.substring(0,analiza.length()-1).trim();

                            }
                            if(estados==116)
                            {
                                if(puntero_codigo==palbra.length()-1)
                                {
                                    analiza+=" ";

                                }else{
                                    puntero_codigo=aux;
                                }
                                
                                jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Operador relacional menor que\n");
                                Terminal=121;
                                description=analiza.substring(0,analiza.length()-1).trim();
                            }
                            if(estados==117)
                            {
                                
                                jTextAreaImprime.append(analiza.trim()+">----Operador relacional menor igual que \n");
                                Terminal=122;
                                description=analiza.trim();
                                
                            }
                            if(estados==119)
                            {
                                if(puntero_codigo==palbra.length()-1)
                                {

                                    analiza+=" ";
                                }else{
                                    puntero_codigo=aux;
                                }
                                
                                jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Operador relacional mayor que\n");
                                Terminal=123;
                                description=analiza.substring(0,analiza.length()-1).trim();
                            }
                            if(estados==118)
                            {

                                
                                jTextAreaImprime.append(analiza.trim()+">----Operador relacional mayor igual que\n");
                                Terminal=124;
                                description=analiza.trim();
                            }
                            if(estados==120)
                            {
                                
                                jTextAreaImprime.append(analiza.trim()+">----Constante caracter\n");
                                Terminal=139;
                                description=analiza.trim();
                            }

                            if(estados==121)
                            {
                                
                                jTextAreaImprime.append(analiza.trim()+">----Constante String\n");
                                Terminal=140;
                                description=analiza.trim();
                            }
                            if(estados==122)
                            {
                                
                                jTextAreaImprime.append(analiza.trim()+">----Signo de agrupación parentesis abre (\n");
                                Terminal=128;
                                description=analiza.trim();
                            }
                            if(estados==123)
                            {
                               
                                jTextAreaImprime.append(analiza.trim()+">----Signo de agrupación parentesis cierra )\n");
                                Terminal=129;
                                description=analiza.trim();
                                
                            }
                            if(estados==124)
                            {
                                
                                jTextAreaImprime.append(analiza.trim()+">----Signo de agrupación corchete abre [\n");
                                Terminal = 69;
                                
                            }
                            if(estados==125)
                            {
                                
                                jTextAreaImprime.append(analiza.trim()+">----Signo de agrupación corchete cierra ]\n");
                                Terminal = 69;
                            }
                            if(estados==126)
                            {
                                
                                jTextAreaImprime.append(analiza.trim()+">----Signo de puntuación punto y coma\n");
                                Terminal=127;
                                description=analiza.trim();
                                
                            }
                            if(estados==127)
                            {
                                jTextAreaImprime.append(analiza.trim()+">----Signo de puntuación coma\n");
                                description=analiza.trim();
                                Terminal=125;
                                
                            }
                            if(estados==128)
                            {
                               
                                jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Comentario en linea\n");
                                Terminal = 69;
                            }
                            if(estados==500)
                            {
                               
                                jTextAreaImprime.append(analiza+">----Error de inicio\n");
                            }

                            if(estados==501)
                            {
                                if(puntero_codigo==palbra.length()-1)
                                {

                                }else{
                                    puntero_codigo=aux;
                                }
                                
                                jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Error real se esperaba un número y se recibio algo diferente\n");
                            }
                            if(estados==502)
                            {
                                if(puntero_codigo==palbra.length()-1)
                                {

                                }else{
                                    puntero_codigo=aux;
                                }
                                
                                jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Error de notacion esperaba un + o - o digito  y se recibio algo diferente\n");
                            }
                            if(estados==503)
                            {
                                if(puntero_codigo==palbra.length()-1)
                                {

                                    analiza+=" ";
                                }else{
                                    puntero_codigo=aux;
                                }
                                
                                jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Error notación esperaba un número \n");


                            }
                            if(estados==504)
                            {
                                
                                if(puntero_codigo==palbra.length())
                                {
                                     analiza+=" ";
                                }else{
                                    puntero_codigo=aux;
                                }
                                
                                jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Error de operacion logico esperaba un & \n");

                            }
                            if(estados==505)
                            {
                                if(puntero_codigo==palbra.length())
                                {

                                    analiza+=" ";
                                }else{
                                    puntero_codigo=aux;
                                }
                               
                                jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Error de operacion locigo se esperaba un |\n");

                            }
                            if(estados==506)
                            {
                                if(puntero_codigo==palbra.length())
                                {
                                    analiza+=" ";

                                }else{
                                    puntero_codigo=aux;
                                }
                                
                                jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Error se esperaba algo diferente de comilla\n");

                            }
                            if(estados==507)
                            {
                                if(puntero_codigo==palbra.length())
                                {
                                   analiza+=" ";
                                }else{
                                    puntero_codigo=aux;
                                }
                                
                                jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Error se esperaba finalizar con comilla\n");
                            }
                            if(estados==1000)
                            {
                                
                                jTextAreaImprime.append(analiza.substring(0,analiza.length()-1).trim()+">----Error no se cerro el String\n");
                                
                                
                            }
                            
                            
        
        return Terminal;
    }
     
    //Metodo relaciona
    public int Relaciona(char c)
    {
    	int columna=27;
    	
    	//Letras minúsculas
    	if((int)c>=(int)'a' && (int)c<=(int)'z')
    	{
    		columna= 0;
    	}
    	//Letras mayúsculas
    	if((int)c>=(int)'A' && (int)c<=(int)'Z')
    	{
    		columna = 1;
    	}
    	//Numero
    	if((int)c>=(int)'0' && (int)c<=(int)'9')
    	{
    		columna =2;
    	}
    	
    	//Guión, punto tabulador salto de linea
    	
    	switch (c) {
			case '_':
				return 3;
		case '.':
			return 4;
		case '\t':
			return 29;
		case '\n':
			return 30;
		case '+':
			return 7;
		case '*':
			return 9;
		case '/':
			return 10;
		case '%':
			return 11;
		case '&':
			return 12;
		case '|':
			return 13;
		case '!':
			return 14;
		case '=':
			return 15;
		case '<':
			return 16;
		case '>':
			return 17;
		case '"':
			return 19;
		case '(':
			return 20;
		case ')':
			return 21;
		case '[':
			return 22;
		case ']':
			return 23;
		case ';':
			return 24;
		case ',':
			return 25;
		case '#':
			return 26;
		
		}
    	
    	if((int)c==(int)'E')
    	{
    		columna=5;
    	}
    	if((int)c==(int)'e')
    	{
    		columna=6;
    	}
    	//Resta 
    	if((int)c==45)
    	{
    		return 8;
    	}
		//comita
    	if((int)c==39)
    	{
    		return 18;
    	}
		//Blanco
		if((int)c==32) {
            return 28;
        }

    	
    	
    	
    	
    	return columna;
    }//Fin del método relaciona
    
    //Método Matriz para saber es edo
    public int Matriz(int estado, int columna)
    {
    	int valor=0;
    	if(estado==0 &&columna==0)
    	{
    		valor=2;
    	}
    	if(estado==0 &&columna==1)
    	{
    		valor=1;
    	}
    	if(estado==0 &&columna==2)
    	{
    		valor=3;
    	}
    	if(estado==0 &&columna==3)
    	{
    		valor=500;
    	}
    	if(estado==0 &&columna==4)
    	{
    		valor=500;
    	}
    	if(estado==0 &&columna==5)
    	{
    		valor=1;
    	}
    	if(estado==0 &&columna==6)
    	{
    		valor=2;
    	}
    	if(estado==0 &&columna==7)
    	{
    		valor=105;
    	}
    	if(estado==0 &&columna==8)
    	{
    		valor=106;
    	}
    	if(estado==0 &&columna==9)
    	{
    		valor=107;
    	}
    	if(estado==0 &&columna==10)
    	{
    		valor=108;
    	}
    	if(estado==0 &&columna==11)
    	{
    		valor=109;
    	}
    	if(estado==0 &&columna==12)
    	{
    		valor=9;
    	}
    	if(estado==0 &&columna==13)
    	{
    		valor=10;
    	}
    	if(estado==0 &&columna==14)
    	{
    		valor=11;
    	}
    	if(estado==0 &&columna==15)
    	{
    		valor=12;
    	}
    	if(estado==0 &&columna==16)
    	{
    		valor=13;
    	}
    	if(estado==0 &&columna==17)
    	{
    		valor=14;
    	}
    	if(estado==0 &&columna==18)
    	{
    		valor=15;
    	}
    	if(estado==0 &&columna==19)
    	{
    		valor=17;
    	}
    	if(estado==0 &&columna==20)
    	{
    		valor=122;
    	}
    	if(estado==0 &&columna==21)
    	{
    		valor=123;
    	}
    	if(estado==0 &&columna==22)
    	{
    		valor=124;
    	}
    	if(estado==0 &&columna==23)
    	{
    		valor=125;
    	}
    	if(estado==0 &&columna==24)
    	{
    		valor=126;
    	}
    	if(estado==0 &&columna==25)
    	{
    		valor=127;
    	}
    	if(estado==0 &&columna==26)
    	{
    		valor=18;
    	}
    	if(estado==0 &&columna==27)
    	{
    		valor=500;
    	}
    	
    	if(estado==1 &&columna==0)
    	{
    		valor=2;
    	}
    	if(estado==1 &&columna==1)
    	{
    		valor=1;
    	}
    	if(estado==1 &&columna==2)
    	{
    		valor=2;
    	}
    	if(estado==1 &&columna==3)
    	{
    		valor=2;
    	}
    	if(estado==1 &&columna==4)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==5)
    	{
    		valor=1;
    	}
    	if(estado==1 &&columna==6)
    	{
    		valor=2;
    	}
    	if(estado==1 &&columna==7)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==8)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==9)
    	{
    		valor=10;
    	}
    	if(estado==1 &&columna==10)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==11)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==12)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==13)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==14)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==15)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==16)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==17)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==18)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==19)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==20)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==21)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==22)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==23)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==24)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==25)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==26)
    	{
    		valor=100;
    	}
    	if(estado==1 &&columna==27)
    	{
    		valor=100;
    	}
    	if(estado==2 &&columna==0)
    	{
    		valor=2;
    	}
    	if(estado==2 &&columna==1)
    	{
    		valor=2;
    	}
    	if(estado==2 &&columna==2)
    	{
    		valor=2;
    	}
    	if(estado==2 &&columna==3)
    	{
    		valor=2;
    	}
    	if(estado==2 &&columna==4)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==5)
    	{
    		valor=2;
    	}
    	if(estado==2 &&columna==6)
    	{
    		valor=2;
    	}
    	if(estado==2 &&columna==7)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==8)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==9)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==10)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==11)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==12)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==13)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==14)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==15)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==16)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==17)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==18)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==19)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==20)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==21)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==22)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==23)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==24)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==25)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==26)
    	{
    		valor=101;
    	}
    	if(estado==2 &&columna==27)
    	{
    		valor=101;
    	}
    	if(estado==3 &&columna==0)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==1)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==2)
    	{
    		valor=3;
    	}
    	if(estado==3 &&columna==3)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==4)
    	{
    		valor=4;
    	}
    	if(estado==3 &&columna==5)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==6)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==7)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==8)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==9)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==10)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==11)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==12)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==13)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==14)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==15)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==16)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==17)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==18)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==19)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==20)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==21)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==22)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==23)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==24)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==25)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==26)
    	{
    		valor=102;
    	}
    	if(estado==3 &&columna==27)
    	{
    		valor=102;
    	}
    	if(estado==4 &&columna==0)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==1)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==2)
    	{
    		valor=5;
    	}
    
    	if(estado==4 &&columna==3)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==4)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==5)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==6)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==7)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==8)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==9)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==10)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==11)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==12)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==13)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==14)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==15)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==16)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==17)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==18)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==19)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==20)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==21)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==22)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==23)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==24)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==25)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==26)
    	{
    		valor=501;
    	}
    	if(estado==4 &&columna==27)
    	{
    		valor=501;
    	}
    	if(estado==5 &&columna==0)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==1)
    	{
    		valor=103;
    	}if(estado==5 &&columna==2)
    	{
    		valor=5;
    	}
    	if(estado==5 &&columna==3)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==4)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==5)
    	{
    		valor=6;
    	}
    	if(estado==5 &&columna==6)
    	{
    		valor=6;
    	}
    	if(estado==5 &&columna==8)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==7)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==9)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==10)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==11)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==12)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==13)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==14)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==15)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==16)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==17)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==18)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==19)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==20)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==21)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==22)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==23)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==24)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==25)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==26)
    	{
    		valor=103;
    	}
    	if(estado==5 &&columna==27)
    	{
    		valor=103;
    	}
    	if(estado==6 &&columna==0)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==1)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==2)
    	{
    		valor=8;
    	}
    	if(estado==6 &&columna==3)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==4)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==5)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==6)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==7)
    	{
    		valor=7;
    	}
    	if(estado==6 &&columna==8)
    	{
    		valor=7;
    	}
    	if(estado==6 &&columna==9)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==10)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==11)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==12)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==13)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==14)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==15)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==16)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==17)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==18)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==19)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==20)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==21)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==22)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==23)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==24)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==25)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==26)
    	{
    		valor=502;
    	}
    	if(estado==6 &&columna==27)
    	{
    		valor=502;
    	}
		if(estado==7 &&columna==0)
		{
			valor=503;
		}
		if(estado==7 &&columna==1)
		{
			valor=503;
		}
		if(estado==7 &&columna==2)
		{
			valor=8;
		}
        if(estado==7 &&columna==3)
        {
            valor=503;
        }
		if(estado==7 &&columna==4)
		{
			valor=503;
		}
		if(estado==7 &&columna==5)
		{
			valor=503;
		}
		if(estado==7 &&columna==6)
		{
			valor=503;
		}
		if(estado==7 &&columna==7)
		{
			valor=503;
		}
		if(estado==7 &&columna==8)
		{
			valor=503;
		}
		if(estado==7 &&columna==9)
		{
			valor=503;
		}
		if(estado==7 &&columna==10)
		{
			valor=503;
		}
		if(estado==7 &&columna==11)
		{
			valor=503;
		}
		if(estado==7 &&columna==12)
		{
			valor=503;
		}
		if(estado==7 &&columna==13)
		{
			valor=503;
		}
		if(estado==7 &&columna==14)
		{
			valor=503;
		}
		if(estado==7 &&columna==15)
		{
			valor=503;
		}
		if(estado==7 &&columna==16)
		{
			valor=503;
		}
		if(estado==7 &&columna==17)
		{
			valor=503;
		}
		if(estado==7 &&columna==18)
		{
			valor=503;
		}
		if(estado==7 &&columna==19)
		{
			valor=503;
		}
		if(estado==7 &&columna==20)
		{
			valor=503;
		}
		if(estado==7 &&columna==21)
		{
			valor=503;
		}
		if(estado==7 &&columna==22)
		{
			valor=503;
		}
		if(estado==7 &&columna==23)
		{
			valor=503;
		}
		if(estado==7 &&columna==24)
		{
			valor=503;
		}
		if(estado==7 &&columna==25)
		{
			valor=503;
		}
		if(estado==7 &&columna==26)
		{
			valor=503;
		}
		if(estado==7 &&columna==27)
		{
			valor=503;
		}

        if(estado==8 && columna==0)
        {
            valor=104;
        }
        if(estado==8 && columna==1)
        {
            valor=104;
        }
        if(estado==8 && columna==2)
        {
            valor=8;
        }
        if(estado==8 && columna==3)
        {
            valor=104;
        }
        if(estado==8 && columna==4)
        {
            valor=104;
        }
        if(estado==8 && columna==5)
        {
            valor=104;
        }
        if(estado==8 && columna==6)
        {
            valor=104;
        }
        if(estado==8 && columna==7)
        {
            valor=104;
        }
        if(estado==8 && columna==8)
        {
            valor=104;
        }
        if(estado==8 && columna==9)
        {
            valor=104;
        }
        if(estado==8 && columna==10)
        {
            valor=104;
        }
        if(estado==8 && columna==11)
        {
            valor=104;
        }
        if(estado==8 && columna==12)
        {
            valor=104;
        }
        if(estado==8 && columna==13)
        {
            valor=104;
        }
        if(estado==8 && columna==14)
        {
            valor=104;
        }
        if(estado==8 && columna==15)
        {
            valor=104;
        }
        if(estado==8 && columna==16)
        {
            valor=104;
        }
        if(estado==8 && columna==17)
        {
            valor=104;
        }
        if(estado==8 && columna==18)
        {
            valor=104;
        }
        if(estado==8 && columna==19)
        {
            valor=104;
        }
        if(estado==8 && columna==20)
        {
            valor=104;
        }
        if(estado==8 && columna==21)
        {
            valor=104;
        }
        if(estado==8 && columna==22)
        {
            valor=104;
        }
        if(estado==8 && columna==23)
        {
            valor=104;
        }
        if(estado==8 && columna==24)
        {
            valor=104;
        }
        if(estado==8 && columna==25)
        {
            valor=104;
        }
        if(estado==8 && columna==26)
        {
            valor=104;
        }
        if(estado==8 && columna==27)
        {
            valor=104;
        }
        if(estado==9 && columna==0)
        {
            valor=504;
        }
        if(estado==9 && columna==1)
        {
            valor=504;
        }
        if(estado==9 && columna==2)
        {
            valor=504;
        }
        if(estado==9 && columna==3)
        {
            valor=504;
        }
        if(estado==9 && columna==4)
        {
            valor=504;
        }
        if(estado==9 && columna==5)
        {
            valor=504;
        }
        if(estado==9 && columna==6)
        {
            valor=504;
        }
        if(estado==9 && columna==7)
        {
            valor=504;
        }
        if(estado==9 && columna==8)
        {
            valor=504;
        }
        if(estado==9 && columna==9)
        {
            valor=504;
        }
        if(estado==9 && columna==10)
        {
            valor=504;
        }
        if(estado==9 && columna==11)
        {
            valor=504;
        }
        if(estado==9 && columna==12)
        {
            valor=110;
        }
        if(estado==9 && columna==13)
        {
            valor=504;
        }
        if(estado==9 && columna==14)
        {
            valor=504;
        }
        if(estado==9 && columna==15)
        {
            valor=504;
        }
        if(estado==9 && columna==16)
        {
            valor=504;
        }
        if(estado==9 && columna==17)
        {
            valor=504;
        }
        if(estado==9 && columna==18)
        {
            valor=504;
        }
        if(estado==9 && columna==19)
        {
            valor=504;
        }
        if(estado==9 && columna==20)
        {
            valor=504;
        }
        if(estado==9 && columna==21)
        {
            valor=504;
        }
        if(estado==9 && columna==22)
        {
            valor=504;
        }
        if(estado==9 && columna==23)
        {
            valor=504;
        }
        if(estado==9 && columna==24)
        {
            valor=504;
        }
        if(estado==9 && columna==25)
        {
            valor=504;
        }
        if(estado==9 && columna==26)
        {
            valor=504;
        }
        if(estado==9 && columna==27)
        {
            valor=504;
        }
        if(estado==10 && columna==0)
        {
            valor=505;
        }
        if(estado==10 && columna==1)
        {
            valor=505;
        }
        if(estado==10 && columna==2)
        {
            valor=505;
        }
        if(estado==10 && columna==3)
        {
            valor=505;
        }
        if(estado==10 && columna==4)
        {
            valor=505;
        }
        if(estado==10 && columna==5)
        {
            valor=505;
        }
        if(estado==10 && columna==6)
        {
            valor=505;
        }
        if(estado==10 && columna==7)
        {
            valor=505;
        }
        if(estado==10 && columna==8)
        {
            valor=505;
        }
        if(estado==10 && columna==9)
        {
            valor=505;
        }
        if(estado==10 && columna==10)
        {
            valor=505;
        }
        if(estado==10 && columna==11)
        {
            valor=505;
        }
        if(estado==10 && columna==12)
        {
            valor=505;
        }
        if(estado==10 && columna==13)
        {
            valor=111;
        }
        if(estado==10 && columna==14)
        {
            valor=505;
        }
        if(estado==10 && columna==15)
        {
            valor=505;
        }
        if(estado==10 && columna==16)
        {
            valor=505;
        }
        if(estado==10 && columna==17)
        {
            valor=505;
        }
        if(estado==10 && columna==18)
        {
            valor=505;
        }
        if(estado==10 && columna==19)
        {
            valor=505;
        }
        if(estado==10 && columna==20)
        {
            valor=505;
        }
        if(estado==10 && columna==21)
        {
            valor=505;
        }
        if(estado==10 && columna==22)
        {
            valor=505;
        }
        if(estado==10 && columna==23)
        {
            valor=505;
        }
        if(estado==10 && columna==24)
        {
            valor=505;
        }
        if(estado==10 && columna==25)
        {
            valor=505;
        }
        if(estado==10 && columna==26)
        {
            valor=505;
        }
        if(estado==10 && columna==27)
        {
            valor=505;
        }
        if(estado==11 && columna==0)
        {
            valor=112;
        }
        if(estado==11 && columna==1)
        {
            valor=112;
        }
        if(estado==11 && columna==2)
        {
            valor=112;
        }
        if(estado==11 && columna==3)
        {
            valor=112;
        }
        if(estado==11 && columna==4)
        {
            valor=112;
        }
        if(estado==11 && columna==5)
        {
            valor=112;
        }
        if(estado==11 && columna==6)
        {
            valor=112;
        }
        if(estado==11 && columna==7)
        {
            valor=112;
        }
        if(estado==11 && columna==8)
        {
            valor=112;
        }
        if(estado==11 && columna==9)
        {
            valor=112;
        }
        if(estado==11 && columna==10)
        {
            valor=112;
        }
        if(estado==11 && columna==11)
        {
            valor=112;
        }
        if(estado==11 && columna==12)
        {
            valor=112;
        }
        if(estado==11 && columna==13)
        {
            valor=112;
        }
        if(estado==11 && columna==14)
        {
            valor=112;
        }
        if(estado==11 && columna==15)
        {
            valor=113;
        }
        if(estado==11 && columna==16)
        {
            valor=112;
        }
        if(estado==11 && columna==17)
        {
            valor=112;
        }
        if(estado==11 && columna==18)
        {
            valor=112;
        }
        if(estado==11 && columna==19)
        {
            valor=112;
        }
        if(estado==11 && columna==20)
        {
            valor=112;
        }
        if(estado==11 && columna==21)
        {
            valor=112;
        }
        if(estado==11 && columna==22)
        {
            valor=112;
        }
        if(estado==11 && columna==23)
        {
            valor=112;
        }
        if(estado==11 && columna==24)
        {
            valor=112;
        }
        if(estado==11 && columna==25)
        {
            valor=112;
        }
        if(estado==11 && columna==26)
        {
            valor=112;
        }
        if(estado==11 && columna==27)
        {
            valor=112;
        }

        if(estado==12 && columna==0)
        {
            valor=115;
        }
        if(estado==12 && columna==1)
        {
            valor=115;
        }
        if(estado==12 && columna==2)
        {
            valor=115;
        }
        if(estado==12 && columna==3)
        {
            valor=115;
        }
        if(estado==12 && columna==4)
        {
            valor=115;
        }
        if(estado==12 && columna==5)
        {
            valor=115;
        }
        if(estado==12 && columna==6)
        {
            valor=115;
        }
        if(estado==12 && columna==7)
        {
            valor=115;
        }
        if(estado==12 && columna==8)
        {
            valor=115;
        }
        if(estado==12 && columna==9)
        {
            valor=115;
        }
        if(estado==12 && columna==10)
        {
            valor=115;
        }
        if(estado==12 && columna==11)
        {
            valor=115;
        }
        if(estado==12 && columna==12)
        {
            valor=115;
        }
        if(estado==12 && columna==13)
        {
            valor=115;
        }
        if(estado==12 && columna==14)
        {
            valor=115;
        }
        if(estado==12 && columna==15)
        {
            valor=114;
        }
        if(estado==12 && columna==16)
        {
            valor=115;
        }
        if(estado==12 && columna==17)
        {
            valor=115;
        }
        if(estado==12 && columna==18)
        {
            valor=115;
        }
        if(estado==12 && columna==19)
        {
            valor=115;
        }
        if(estado==12 && columna==20)
        {
            valor=115;
        }
        if(estado==12 && columna==21)
        {
            valor=115;
        }
        if(estado==12 && columna==22)
        {
            valor=115;
        }
        if(estado==12 && columna==23)
        {
            valor=115;
        }
        if(estado==12 && columna==24)
        {
            valor=115;
        }
        if(estado==12 && columna==25)
        {
            valor=115;
        }
        if(estado==12 && columna==26)
        {
            valor=115;
        }
        if(estado==12 && columna==27)
        {
            valor=115;
        }
        if(estado==13 && columna==0)
        {
            valor=116;
        }
        if(estado==13 && columna==1)
        {
            valor=116;
        }
        if(estado==13 && columna==2)
        {
            valor=116;
        }
        if(estado==13 && columna==3)
        {
            valor=116;
        }
        if(estado==13 && columna==4)
        {
            valor=116;
        }
        if(estado==13 && columna==5)
        {
            valor=116;
        }
        if(estado==13 && columna==6)
        {
            valor=116;
        }
        if(estado==13 && columna==7)
        {
            valor=116;
        }
        if(estado==13 && columna==8)
        {
            valor=116;
        }
        if(estado==13 && columna==9)
        {
            valor=116;
        }
        if(estado==13 && columna==10)
        {
            valor=116;
        }
        if(estado==13 && columna==11)
        {
            valor=116;
        }
        if(estado==13 && columna==12)
        {
            valor=116;
        }
        if(estado==13 && columna==13)
        {
            valor=116;
        }
        if(estado==13 && columna==14)
        {
            valor=116;
        }
        if(estado==13 && columna==15)
        {
            valor=117;
        }
        if(estado==13 && columna==16)
        {
            valor=116;
        }
        if(estado==13 && columna==17)
        {
            valor=116;
        }
        if(estado==13 && columna==18)
        {
            valor=116;
        }
        if(estado==13 && columna==19)
        {
            valor=116;
        }
        if(estado==13 && columna==20)
        {
            valor=116;
        }
        if(estado==13 && columna==21)
        {
            valor=116;
        }
        if(estado==13 && columna==22)
        {
            valor=116;
        }
        if(estado==13 && columna==23)
        {
            valor=116;
        }
        if(estado==13 && columna==24)
        {
            valor=116;
        }
        if(estado==13 && columna==25)
        {
            valor=116;
        }
        if(estado==13 && columna==26)
        {
            valor=116;
        }
        if(estado==13 && columna==27)
        {
            valor=116;
        }
        if(estado==14 && columna==0)
        {
            valor=119;
        }
        if(estado==14 && columna==1)
        {
            valor=119;
        }
        if(estado==14 && columna==2)
        {
            valor=119;
        }
        if(estado==14 && columna==3)
        {
            valor=119;
        }
        if(estado==14 && columna==4)
        {
            valor=119;
        }
        if(estado==14 && columna==5)
        {
            valor=119;
        }
        if(estado==14 && columna==6)
        {
            valor=119;
        }
        if(estado==14 && columna==7)
        {
            valor=119;
        }
        if(estado==14 && columna==8)
        {
            valor=119;
        }
        if(estado==14 && columna==9)
        {
            valor=119;
        }
        if(estado==14 && columna==10)
        {
            valor=119;
        }
        if(estado==14 && columna==11)
        {
            valor=119;
        }
        if(estado==14 && columna==12)
        {
            valor=119;
        }
        if(estado==14 && columna==13)
        {
            valor=119;
        }
        if(estado==14 && columna==14)
        {
            valor=119;
        }
        if(estado==14 && columna==15)
        {
            valor=118;
        }
        if(estado==14 && columna==16)
        {
            valor=119;
        }
        if(estado==14 && columna==17)
        {
            valor=119;
        }
        if(estado==14 && columna==18)
        {
            valor=119;
        }
        if(estado==14 && columna==19)
        {
            valor=119;
        }
        if(estado==14 && columna==20)
        {
            valor=119;
        }
        if(estado==14 && columna==21)
        {
            valor=119;
        }
        if(estado==14 && columna==22)
        {
            valor=119;
        }
        if(estado==14 && columna==23)
        {
            valor=119;
        }
        if(estado==14 && columna==24)
        {
            valor=119;
        }
        if(estado==14 && columna==25)
        {
            valor=119;
        }
        if(estado==14 && columna==26)
        {
            valor=119;
        }
        if(estado==14 && columna==27)
        {
            valor=119;
        }
        if(estado==15 && columna==0)
        {
            valor=16;
        }
        if(estado==15 && columna==1)
        {
            valor=16;
        }
        if(estado==15 && columna==2)
        {
            valor=16;
        }
        if(estado==15 && columna==3)
        {
            valor=16;
        }
        if(estado==15 && columna==4)
        {
            valor=16;
        }
        if(estado==15 && columna==5)
        {
            valor=16;
        }
        if(estado==15 && columna==6)
        {
            valor=16;
        }
        if(estado==15 && columna==7)
        {
            valor=16;
        }
        if(estado==15 && columna==8)
        {
            valor=16;
        }
        if(estado==15 && columna==9)
        {
            valor=16;
        }
        if(estado==15 && columna==10)
        {
            valor=16;
        }
        if(estado==15 && columna==11)
        {
            valor=16;
        }
        if(estado==15 && columna==12)
        {
            valor=16;
        }
        if(estado==15 && columna==13)
        {
            valor=16;
        }
        if(estado==15 && columna==14)
        {
            valor=16;
        }
        if(estado==15 && columna==15)
        {
            valor=16;
        }
        if(estado==15 && columna==16)
        {
            valor=16;
        }
        if(estado==15 && columna==17)
        {
            valor=16;
        }
        if(estado==15 && columna==18)
        {
            valor=506;
        }
        if(estado==15 && columna==19)
        {
            valor=16;
        }
        if(estado==15 && columna==20)
        {
            valor=16;
        }
        if(estado==15 && columna==21)
        {
            valor=16;
        }
        if(estado==15 && columna==22)
        {
            valor=16;
        }
        if(estado==15 && columna==23)
        {
            valor=16;
        }
        if(estado==15 && columna==24)
        {
            valor=16;
        }
        if(estado==15 && columna==25)
        {
            valor=16;
        }
        if(estado==15 && columna==26)
        {
            valor=16;
        }
        if(estado==15 && columna==27)
        {
            valor=16;
        }
        if(estado==16 && columna==0)
        {
            valor=507;
        }
        if(estado==16 && columna==1)
        {
            valor=507;
        }
        if(estado==16 && columna==2)
        {
            valor=507;
        }
        if(estado==16 && columna==3)
        {
            valor=507;
        }
        if(estado==16 && columna==4)
        {
            valor=507;
        }
        if(estado==16 && columna==5)
        {
            valor=507;
        }
        if(estado==16 && columna==6)
        {
            valor=507;
        }
        if(estado==16 && columna==7)
        {
            valor=507;
        }
        if(estado==16 && columna==8)
        {
            valor=507;
        }
        if(estado==16 && columna==9)
        {
            valor=507;
        }
        if(estado==16 && columna==10)
        {
            valor=507;
        }
        if(estado==16 && columna==11)
        {
            valor=507;
        }
        if(estado==16 && columna==12)
        {
            valor=507;
        }
        if(estado==16 && columna==13)
        {
            valor=507;
        }
        if(estado==16 && columna==14)
        {
            valor=507;
        }
        if(estado==16 && columna==15)
        {
            valor=507;
        }
        if(estado==16 && columna==16)
        {
            valor=507;
        }
        if(estado==16 && columna==17)
        {
            valor=507;
        }
        if(estado==16 && columna==18)
        {
            valor=120;
        }
        if(estado==16 && columna==19)
        {
            valor=507;
        }
        if(estado==16 && columna==20)
        {
            valor=507;
        }
        if(estado==16 && columna==21)
        {
            valor=507;
        }
        if(estado==16 && columna==22)
        {
            valor=507;
        }
        if(estado==16 && columna==23)
        {
            valor=507;
        }
        if(estado==16 && columna==24)
        {
            valor=507;
        }
        if(estado==16 && columna==25)
        {
            valor=507;
        }
        if(estado==16 && columna==26)
        {
            valor=507;
        }
        if(estado==16 && columna==27)
        {
            valor=507;
        }
        if(estado==17 && columna==0)
        {
            valor=17;
        }
        if(estado==17 && columna==1)
        {
            valor=17;
        }
        if(estado==17 && columna==2)
        {
            valor=17;
        }
        if(estado==17 && columna==3)
        {
            valor=17;
        }
        if(estado==17 && columna==4)
        {
            valor=17;
        }
        if(estado==17 && columna==5)
        {
            valor=17;
        }
        if(estado==17 && columna==6)
        {
            valor=17;
        }
        if(estado==17 && columna==7)
        {
            valor=17;
        }
        if(estado==17 && columna==8)
        {
            valor=17;
        }
        if(estado==17 && columna==9)
        {
            valor=17;
        }
        if(estado==17 && columna==10)
        {
            valor=17;
        }
        if(estado==17 && columna==11)
        {
            valor=17;
        }
        if(estado==17 && columna==12)
        {
            valor=17;
        }
        if(estado==17 && columna==13)
        {
            valor=17;
        }
        if(estado==17 && columna==14)
        {
            valor=17;
        }
        if(estado==17 && columna==15)
        {
            valor=17;
        }
        if(estado==17 && columna==16)
        {
            valor=17;
        }
        if(estado==17 && columna==17)
        {
            valor=17;
        }
        if(estado==17 && columna==18)
        {
            valor=17;
        }
        if(estado==17 && columna==19)
        {
            valor=121;
        }
        if(estado==17 && columna==20)
        {
            valor=17;
        }
        if(estado==17 && columna==21)
        {
            valor=17;
        }
        if(estado==17 && columna==22)
        {
            valor=17;
        }
        if(estado==17 && columna==23)
        {
            valor=17;
        }
        if(estado==17 && columna==24)
        {
            valor=17;
        }
        if(estado==17 && columna==25)
        {
            valor=17;
        }
        if(estado==17 && columna==26)
        {
            valor=17;
        }
        if(estado==17 && columna==27)
        {
            valor=1000;
        }
        if(estado==18 && columna==0)
        {
            valor=18;
        }
        if(estado==18 && columna==1)
        {
            valor=18;
        }
        if(estado==18 && columna==2)
        {
            valor=18;
        }
        if(estado==18 && columna==3)
        {
            valor=18;
        }
        if(estado==18 && columna==4)
        {
            valor=18;
        }
        if(estado==18 && columna==5)
        {
            valor=18;
        }
        if(estado==18 && columna==6)
        {
            valor=18;
        }
        if(estado==18 && columna==7)
        {
            valor=18;
        }
        if(estado==18 && columna==8)
        {
            valor=18;
        }
        if(estado==18 && columna==9)
        {
            valor=18;
        }
        if(estado==18 && columna==10)
        {
            valor=18;
        }
        if(estado==18 && columna==11)
        {
            valor=18;
        }
        if(estado==18 && columna==12)
        {
            valor=18;
        }
        if(estado==18 && columna==13)
        {
            valor=18;
        }
        if(estado==18 && columna==14)
        {
            valor=18;
        }
        if(estado==18 && columna==15)
        {
            valor=18;
        }
        if(estado==18 && columna==16)
        {
            valor=18;
        }
        if(estado==18 && columna==17)
        {
            valor=18;
        }
        if(estado==18 && columna==18)
        {
            valor=18;
        }
        if(estado==18 && columna==19)
        {
            valor=18;
        }
        if(estado==18 && columna==20)
        {
            valor=18;
        }
        if(estado==18 && columna==21)
        {
            valor=18;
        }
        if(estado==18 && columna==22)
        {
            valor=18;
        }
        if(estado==18 && columna==23)
        {
            valor=18;
        }
        if(estado==18 && columna==24)
        {
            valor=18;
        }
        if(estado==18 && columna==25)
        {
            valor=18;
        }
        if(estado==18 && columna==26)
        {
            valor=18;
        }
        if(estado==18 && columna==27)
        {
            valor=18;
        }
		if(estado==0 && columna==28)
		{
			valor=0;
		}
		if(estado==1 && columna==28)
		{
			valor=100;
		}
		if(estado==2 && columna==28)
		{
			valor=101;
		}
		if(estado==3 && columna==28)
		{
			valor=102;
		}
		if(estado==4 && columna==28)
		{
			valor=501;
		}
		if(estado==5 && columna==28)
		{
			valor=103;
		}
		if(estado==6 && columna==28)
		{
			valor=502;
		}
		if(estado==7 && columna==28)
		{
			valor=503;
		}
		if(estado==8 && columna==28)
		{
			valor=104;
		}
		if(estado==9 && columna==28)
		{
			valor=504;
		}
		if(estado==10 && columna==28)
		{
			valor=505;
		}
		if(estado==11 && columna==28)
		{
			valor=112;
		}
		if(estado==12 && columna==28)
		{
			valor=115;
		}
		if(estado==13 && columna==28)
		{
			valor=116;
		}
		if(estado==14 && columna==28)
		{
			valor=119;
		}
		if(estado==15 && columna==28)
		{
			valor=16;
		}
		if(estado==16 && columna==28)
		{
			valor=507;
		}
		if(estado==17 && columna==28)
		{
			valor=17;
		}
		if(estado==18 && columna==28) {
			valor =18;
		}
        if(estado==0 && columna==30)
        {
            valor=0;
        }
        if(estado==1 && columna==30)
        {
            valor=100;
        }
        if(estado==2 && columna==30)
        {
            valor=101;
        }
        if(estado==3 && columna==30)
        {
            valor=102;
        }
        if(estado==4 && columna==30)
        {
            valor=501;
        }
        if(estado==5 && columna==30)
        {
            valor=103;
        }
        if(estado==6 && columna==30)
        {
            valor=502;
        }
        if(estado==7 && columna==30)
        {
            valor=503;
        }
        if(estado==8 && columna==30)
        {
            valor=104;
        }
        if(estado==9 && columna==30)
        {
            valor=504;
        }
        if(estado==10 && columna==30)
        {
            valor=505;
        }
        if(estado==11 && columna==30)
        {
            valor=112;
        }
        if(estado==12 && columna==30)
        {
            valor=115;
        }
        if(estado==13 && columna==30)
        {
            valor=117;
        }
        if(estado==14 && columna==30)
        {
            valor=119;
        }
        if(estado==15 && columna==30)
        {
            valor=16;
        }
        if(estado==16 && columna==30)
        {
            valor=507;
        }
        if(estado==17 && columna==30)
        {
            valor=17;
        }
        if(estado==18 && columna==30)
        {
            valor=128;
        }











        return valor;
    }//Fin del metodo de la matriz

    //Método de sintaxis
    public void Sintactico(){
         //Se carga el código que se va a analizar
         String codigo_enviar = get_codigo();
         //crea el tamaño de la pila
         pila = new Pila(32);
         //Se mete el 143 a la pila que identifica el final del código
         pila.PUSH(143);
         jTextAreaSintactico.append("Valor introducido a la pila: " + 143+"\n");
         //Se mete el 0 que indica la producción inicial
         pila.PUSH(0);
         jTextAreaSintactico.append("Valor introducido a la pila: " + 0+"\n");
        //Tabla de producciones
        int tablaProducciones[][]={{1,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		   {999,999,999,2,2,2,2,2,4,5,6,999,999,7,999,8,999,999,999,999,999,999,999,999,999,999,999,999,999,999,3,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		   {999,999,10,9,9,9,9,9,9,9,9,10,10,9,10,9,10,999,999,999,999,999,999,999,999,999,999,999,999,999,9,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		    {999,999,999,11,11,11,11,11,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		    {999,999,999,12,13,14,15,16,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		    {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,17,999,18,999,18,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,19,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,20,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,21,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,22,999,999,999,23,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,24,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,26,25,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,27,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,28,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,29,999,999,999,999,999,999,999,999,29,999,29,999,999,999,999,999,29,29,29,29,29,29,29},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,30,999,999,999,999,999,999,999,31,999,31,999,31,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,32,999,32,999,999,999,999,999,32,32,32,32,32,32,32},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,34,33,999,999,999,999,999,999,34,999,34,999,34,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,35,999,999,999,999,999,999,999,35,999,35,999,999,999,999,999,35,35,35,35,35,35,35},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,36,999,999,999,999,999,999,999,999,37,999,37,999,999,999,999,999,37,37,37,37,37,37,37},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,38,999,38,999,999,999,999,999,38,38,38,38,38,38,38},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,40,40,999,39,39,39,39,39,40,999,40,999,40,999,39,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,46,41,42,43,44,999,999,999,999,999,999,45,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,47,999,47,999,999,999,999,999,47,47,47,47,47,47,47},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,50,50,999,50,50,50,50,50,50,999,50,999,50,999,50,48,49,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,51,999,51,999,999,999,999,999,51,51,51,51,51,51,51,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,54,54,999,54,54,54,54,54,54,999,54,999,54,999,54,54,54,52,53,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,63,999,55,999,999,999,999,999,56,57,58,59,60,61,62}
	    		      };



        //Se carga la tabla Lr
       int tablaLr[][]={{100,130,101,1,102},
               {3,2},
               {6,2},
               {7,2},
               {8,2},
               {10,2},
               {12,2},
               {13,2},
               {1},
               {-1},
               {4,130,5,127},
               {103},
               {104},
               {105},
               {106},
               {107},
               {125,130,5},
               {-1},
               {130,126,14,127},
               {108,128,130,5,129,127},
               {109,128,14,9,129,127	},
               {125,14,9},
               {-1},
               {110,128,14,129,1,11,111},
               {112,1},
               {-1},
               {113,128,14,129,1,114},
               {115,128,130,126,14,127,14,129,1,116},
               {16	,15},
               {11,14},
               {-1},
               {18	,17},
               {118,16},
               {-1	},
               {19	,20},
               {119},
               {-1},
               {23	,21	},
               {22	,23},
               {-1},
               {121},
               {122},
               {123},
               {124},
               {131},
               {120},
               {25,24},
               {132,23},
               {133,23},
               {-1},
               {27,26},
               {134,25},
               {135,25},
               {-1},
               {130},
               {136},
               {137},
               {138},
               {139},
               {140},
               {141},
               {142},
               {128,14,139}	};
       //Variable que guarda el token d eentrada
       int tokenEntrada;
       //Bandera para el ciclo de análisis del código
       boolean bandera_while=true;
       //While del proceso de análisi del código que se analiza
        while(bandera_while)
        {
           //Metodo para el análisis de sintaxis
           //Pedimos un token de entrada al léxico
            tokenEntrada= Lexico(codigo_enviar.substring(0, codigo_enviar.length()-1));
            if(tokenEntrada==69)
            {
                tokenEntrada= Lexico(codigo_enviar.substring(0, codigo_enviar.length()-1));

            }
            //Resultado que se obtiene de la matriz predictiva si es 999 es error xD
            int resultado=0;
            
            try {
    
                //While que carga los datos de la tabla lr
                while(pila.VALOR()<100)
               {
            	   resultado =get_valor_of_table_lr(tablaProducciones, tablaLr, tokenEntrada);
               }
                //While que elimina los terminales de la pila
               while(pila.VALOR()>=100)
               {
                   //Compara si el valor de la cima de la pila es un elemento terminal y lo compara con el elemento terminal del vector de entrada
                if (pila.VALOR() == tokenEntrada) {
                    jTextAreaSintactico.append("Valor borrado de la pila: " + pila.POP() +"\n");
                    
                } else {
                    //Si el valor de la pila no es igual al terminal del vector de entrada pero es mayo de 100 quiere decir que hay error de sintaxis
                    if (pila.VALOR() >= 100) {
                        jTextAreaSintactico.append("ERROR 999 DE SINTAXIS CORRIJA SU CÓDIGO");
                        JOptionPane.showMessageDialog(null, "Error","Error sintactico",JOptionPane.ERROR_MESSAGE);
                        bandera_while=false;
                        break;
                        

                    } else {
                        

                        //Si el resultado es igual a 999 quiere decir que es error
                        if (resultado == 999) {
                            jTextAreaSintactico.append("ERROR 999 DE SINTAXIS CORRIJA SU CÓDIGO");
                            JOptionPane.showMessageDialog(null, "Error","Error sintactico",JOptionPane.ERROR_MESSAGE);
                            bandera_while=false;
                            break;
                        }
                       



                    }
                }
                //Compara si el valor de la cima de la pila es igual al 143 y si se llego al final de los tokens analizados para salir del ciclo
                if(pila.VALOR()==143)
                {
                    jTextAreaSintactico.append("Valor borrado de la pila: " + pila.POP() +"\n");
                    JOptionPane.showMessageDialog(null, "Codigo correcto");
                    bandera_while=false;
                    break;
                }
                   
               //Si el valo de la pila es mayor a 100 se pide otro token para seguir analizando
                if(pila.VALOR()>=100)
                {
                    tokenEntrada = Lexico(codigo_enviar);
                }
                

                }
            }
            //Está excepción la da la tabla lr si el token n es el que seguía según las producciones
            // devolverá un valor diferente y tirara un desbordamiento en la tabla lr
            catch (ArrayIndexOutOfBoundsException e)
            {
                
               jTextAreaSintactico.append("ERROR 999 DE SINTAXIS CORRIJA SU CÓDIGO");
               JOptionPane.showMessageDialog(null, "Error","Error sintactico",JOptionPane.ERROR_MESSAGE);
               break;
               
            }
        }

    }//Fin del método de sintaxis
    
   

    //Método para guardar archivo
    private void save(){
        String texto = jTextAreaGuarda.getText();//variable para comparacion

        if (texto.matches("[[ ]*[\n]*[\t]]*")) {//compara si en el JTextArea hay texto sino muestrtra un mensaje en pantalla
            JOptionPane.showMessageDialog(null,"No hay texto para guardar!", "Error", JOptionPane.ERROR_MESSAGE);
        }else{

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("todos los archivos *.TXT", "txt","TXT"));//filtro para ver solo archivos .edu
            int seleccion = fileChooser.showSaveDialog(null);
            try{
                if (seleccion == JFileChooser.APPROVE_OPTION){//comprueba si ha presionado el boton de aceptar
                    File JFC = fileChooser.getSelectedFile();
                    String PATH = JFC.getAbsolutePath();//obtenemos el path del archivo a guardar
                    PrintWriter printwriter = new PrintWriter(JFC);
                    printwriter.print(jTextAreaGuarda.getText());//escribe en el archivo todo lo que se encuentre en el JTextArea
                    printwriter.close();//cierra el archivo

                    //comprobamos si a la hora de guardar obtuvo la extension y si no se la asignamos
                    if(!(PATH.endsWith(".txt"))){
                        File temp = new File(PATH+".txt");
                        JFC.renameTo(temp);//renombramos el archivo
                    }

                    JOptionPane.showMessageDialog(null,"Guardado exitoso!", "Guardado exitoso!", JOptionPane.INFORMATION_MESSAGE);
                }
            }catch (Exception e){//por alguna excepcion salta un mensaje de error
                JOptionPane.showMessageDialog(null,"Error al guardar el archivo!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//Fin del método Guardar archivo

    //Metodo para abrir archivo
    public void Abrir()
    {

        File archivo = null;
        FileReader FR = null;
        BufferedReader BR =null;
        try {
            //Se crea un JFileChooser
            JFileChooser jFileChooser = new JFileChooser();
            //Se lanza un dialogo para ver si se preciono el boton de aceptar
            int abrir = jFileChooser.showDialog(null,"Abrir");
            if(abrir==jFileChooser.APPROVE_OPTION) {
                //El archivo que seleccione lo guardaremos en un File
                archivo = jFileChooser.getSelectedFile();
                //Se crea un FileReader a partir del archivo File
                FR = new FileReader(archivo);
                //Se crea un BuffereadReader para imprimirlo en el área de texto
                BR = new BufferedReader(FR);
                String linea="";
                //Se genera un ciclo while para escribir todos lo datos del ciclo
                while((linea=BR.readLine())!=null){
                    jTextAreaGuarda.append(linea+"\n");
                }

            }

        }catch (FileNotFoundException e)
        {
            e.printStackTrace();

        }
        catch (IOException e)
        {
            e.printStackTrace();

        }
    }//Fin del método abrir
    
    //Método que carga los datos que se encuentran en la posición d ela tabla LR
    public int get_valor_of_table_lr(int tablaProducciones[][], int tablaLr[][],int tokenEntrada)
    {
    	//Resultado es el valor de la tabla que se saca con el elemnto de entrada del vector y la posición de las producciones
        System.out.println("Dato de la pila: " +pila.VALOR());
        System.out.println("Token :"+ tokenEntrada);
        int resultado = tablaProducciones[pila.VALOR()][tokenEntrada - 100];
        System.out.println("Resultado: " +resultado);
        //Se elimina el primer elemnto de la pila y se sacan los nuevos de la tabla lr
        jTextAreaSintactico.append("Valor borrado de la pila: " + pila.POP() +"\n");
        for (int j = tablaLr[resultado - 1].length - 1; j >= 0; j--) 
        {
               if (tablaLr[resultado - 1][j] == -1) 
               {


               } else
                     {
                       jTextAreaSintactico.append("Valor introducido a la pila: " + tablaLr[resultado - 1][j]+"\n");
                       pila.PUSH(tablaLr[resultado - 1][j]);

                     }


        }
        return resultado;
    }
    
    
    //Método en construcción
    public void Actions(int action,String id){
        
        switch(action)
        {
            //Action 1001 save operating on stack of operands 
            case 1001:
                if(types.isEmpty()==false)
                {
                  operands.add(id);   
                }
               
                
                
            break;
            //Action 1002 save type on stack of types
            case 1002:
                types.add(id);
            break;
            case 1003:
            //Action 1003 create table of symbols     
                if(types.isEmpty()==false){
                for (Object operando : operands) {
                    symbols[apuntador_tabla_simbolos][0]=apuntador_tabla_simbolos;
                    symbols[apuntador_tabla_simbolos][1]=operando;
                    symbols[apuntador_tabla_simbolos][2]=types.get(types.size()-1);
                    symbols[apuntador_tabla_simbolos][3]=10;
                    symbols[apuntador_tabla_simbolos][4]=apuntador_tabla_simbolos;
                    apuntador_tabla_simbolos++;
                }
                operands.clear();
                types.clear();
                }
            break;
            
            //Action 1011 push stack of operands
            case 1011:
                operands.add(id);
                break;
            //Action 1012 save operator (+,-) on stack of operators
            case 1012:
                operators.add(id);
                break;
            //Action 1013 save operator (*,/) on stack of operators
            case 1013:
                operators.add(id);
                break;
            //create cuadruple     
            case 1014:
                if(operators.get(operators.size()-1).toString().equals("+") || operators.get(operators.size()-1).toString().equals("-")|| 
                        operators.get(operators.size()-1).toString().equals("||"))
                {
                    String operator2 = operators.remove(operators.size()-1).toString();
                    String op_1=operands.remove(operands.size()-1).toString();
                    String op_2=operands.remove(operands.size()-1).toString();
                    cuadruplos[0][0] =operator2;
                    cuadruplos[0][1] =op_1;
                    cuadruplos[0][2] =op_2;
                    cuadruplos[0][3] ="t1";
                    JOptionPane.showMessageDialog(null, "Opera: " +operator2+" op1: " + op_1+" op2: " + op_2+" op1: t1 ");
                    operands.add("t1");
                
                   break;
                }
            //create cuadruple    
            case 1015:
                
                if(operators.get(operators.size()-1).toString().equals("*") || operators.get(operators.size()-1).toString().equals("/"))
                {
                    String operator2 = operators.remove(operators.size()-1).toString();
                    String op_1=operands.remove(operands.size()-1).toString();
                    String op_2=operands.remove(operands.size()-1).toString();
                    cuadruplos[0][0] =operator2;
                    cuadruplos[0][1] =op_1;
                    cuadruplos[0][2] =op_2;
                    cuadruplos[0][3] ="t1";
                    JOptionPane.showMessageDialog(null, "Opera: " +operator2+" op1: " + op_1+" op2: " + op_2+" op1: t1 ");
                   operands.add("t1");
                   break;
                }
            case 1016:
                operators.add("(");
                break;
            case 1017:
                operators.remove(operators.size()-1);
                break;
            case 1018:
                operators.add(id);
                break;
            //push or     
            case 1020:
                operators.add(id);
                break;
            //push and    
            case 1021:
                operators.add(id);
                break;    
            case 1022:
                if(operators.get(operators.size()-1).toString().equals("||"))
                {
                    String operator2 = operators.remove(operators.size()-1).toString();
                    String op_1=operands.remove(operands.size()-1).toString();
                    String op_2=operands.remove(operands.size()-1).toString();
                    cuadruplos[0][0] =operator2;
                    cuadruplos[0][1] =op_1;
                    cuadruplos[0][2] =op_2;
                    cuadruplos[0][3] ="t1";
                    JOptionPane.showMessageDialog(null, "Opera: " +operator2+" op1: " + op_1+" op2: " + op_2+" op1: t1 ");
                   operands.add("t1");
                   break;
                }
            case 1023:
                if(operators.get(operators.size()-1).toString().equals("&&"))
                {
                    String operator2 = operators.remove(operators.size()-1).toString();
                    String op_1=operands.remove(operands.size()-1).toString();
                    String op_2=operands.remove(operands.size()-1).toString();
                    cuadruplos[0][0] =operator2;
                    cuadruplos[0][1] =op_1;
                    cuadruplos[0][2] =op_2;
                    cuadruplos[0][3] ="t1";
                    JOptionPane.showMessageDialog(null, "Opera: " +operator2+" op1: " + op_1+" op2: " + op_2+" op1: t1 ");
                   operands.add("t1");
                   break;
                }    
                
                    
                
                    
        }
        
        
    }
    //Método de sintaxis
    public void Sintactico_version2(){
         //Se carga el código que se va a analizar
         String codigo_enviar = get_codigo();
         //crea el tamaño de la pila
         pila = new Pila(32);
         //Se mete el 143 a la pila que identifica el final del código
         pila.PUSH(143);
         jTextAreaSintactico.append("Valor introducido a la pila: " + 143+"\n");
         //Se mete el 0 que indica la producción inicial
         pila.PUSH(0);
         jTextAreaSintactico.append("Valor introducido a la pila: " + 0+"\n");
        //Tabla de producciones
        int tablaProducciones[][]={{1,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		   {999,999,999,2,2,2,2,2,4,5,6,999,999,7,999,8,999,999,999,999,999,999,999,999,999,999,999,999,999,999,3,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		   {999,999,10,9,9,9,9,9,9,9,9,10,10,9,10,9,10,999,999,999,999,999,999,999,999,999,999,999,999,999,9,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		           {999,999,999,11,11,11,11,11,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,12,13,14,15,16,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,17,999,18,999,18,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,19,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,20,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,21,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,22,999,999,999,23,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,24,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,26,25,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,27,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,28,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,29,999,999,999,999,999,999,999,999,29,999,29,999,999,999,999,999,29,29,29,29,29,29,29},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,30,999,999,999,999,999,999,999,31,999,31,999,31,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,32,999,32,999,999,999,999,999,32,32,32,32,32,32,32},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,34,33,999,999,999,999,999,999,34,999,34,999,34,999,999,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,35,999,999,999,999,999,999,999,35,999,35,999,999,999,999,999,35,35,35,35,35,35,35},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,36,999,999,999,999,999,999,999,999,37,999,37,999,999,999,999,999,37,37,37,37,37,37,37},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,38,999,38,999,999,999,999,999,38,38,38,38,38,38,38},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,40,40,999,39,39,39,39,39,40,999,40,999,40,999,39,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,46,41,42,43,44,999,999,999,999,999,999,45,999,999,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,47,999,47,999,999,999,999,999,47,47,47,47,47,47,47},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,50,50,999,50,50,50,50,50,50,999,50,999,50,999,50,48,49,999,999,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,51,999,51,999,999,999,999,999,51,51,51,51,51,51,51,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,54,54,999,54,54,54,54,54,54,999,54,999,54,999,54,54,54,52,53,999,999,999,999,999,999,999},
	    		              {999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,999,63,999,55,999,999,999,999,999,56,57,58,59,60,61,62}
	    		      };



       //Se carga la tabla Lr
	       int tablaLr[][]={{100,130,101,1,102},
	               {3,2},
	               {6,2},
	               {7,2},
	               {8,2},
	               {10,2},
	               {12,2},
	               {13,2},
	               {1},
	               {-1},
	               {4,130,/*Action 1001*/1001,5,127,/*1003*/1003},
	               {103,/*Action 1002*/1002},
	               {104,/*Action 1002*/1002},
	               {105,/*Action 1002*/1002},
	               {106,/*Action 1002*/1002},
	               {107,/*Action 1002*/1002},
	               {125,130,/*Action 1001*/1001,5},
	               {-1},
	               {130,126,14,127,/*Action 1003*/1003},
	               {108,128,130,/*Action 1001*/10011,5,129,127,/*Action 1003*/1003},
	               {109,128,14,9,129,127,/*Action 1003*/1003},
	               {125,14,9},
	               {-1},
	               {110,128,14,129,1,11,111},
	               {112,1},
	               {-1},
	               {113,128,14,129,1,114},
	               {115,128,130,126,14,127,/*Action 1003*/1003,14,129,1,116},
	               {16,15},
	               {117,/*Action 1020*/1020,14,/*Action 1014*/1022},
	               {-1},
	               {18,17},
	               {118,/*Action 1021*/1021,16,/*Action 1015*/1023},
	               {-1},
	               {19	,20},
	               {119},
	               {-1},
	               {23	,21	},
	               {22,23},
	               {-1},
	               {121,/*Action 1018*/},
	               {122,/*Action 1018*/},
	               {123,/*Action 1018*/},
	               {124,/*Action 1018*/},
	               {131,/*Action 1018*/},
	               {120,/*Action 1018*/},
	               {25,24},
	               {132/*Action 1012*/,1012,23,/*Action 1014*/1014},
	               {133/*Action 1012*/,1012,23,/*Action 1014*/1014},
	               {-1},
	               {27,26},
	               {134,/*Action 1013*/1013,25,/*Action 1015*/1015},
	               {135,/*Action 1013*/1013,25,/*Action 1015*/1015},
	               {-1},
	               {130,/*Accion 10011*/1011},
	               {136,/*Action 1011*/1011},
	               {137,/*Action 1011*/1011},
	               {138,/*Action 1011*/1011},
	               {139,/*Action 1011*/1011},
	               {140,/*Action 1011*/1011},
	               {141,/*Action 1011*/1011},
	               {142,/*Action 1011*/1011},
	               {/*Action 1016*/1016,128,14,129,/*Action 1017*/1017}
               };
        
        

       //Bandera para el ciclo de análisis del código
       boolean bandera_while=true;
       //While del proceso de análisi del código que se analiza
        while(bandera_while)
        {
           
           //Pedimos un token de entrada al léxico
           tokenEntrada= Lexico(codigo_enviar.substring(0, codigo_enviar.length()-1)); 
           //Hay unos terminales que debemos omitir
           if(tokenEntrada==69)
            {
                tokenEntrada= Lexico(codigo_enviar.substring(0, codigo_enviar.length()-1));
            }
            //Resultado que se obtiene de la matriz predictiva si es 999 es error xD
            int resultado=0;
            
            try {
                //While que carga los datos de la tabla lr
                 while(pila.VALOR()<100)
                {
                   
                   //Mete los valores a la pila  
            	   resultado =get_valor_of_table_lr(tablaProducciones, tablaLr, tokenEntrada);
                   //Checa si el elemento que esta en la pila es una acción 
                   while(pila.VALOR()>1000)
                   {
                       Actions(pila.VALOR(), description);
                       jTextAreaSintactico.append("Valor borrado de la pila: " + pila.POP() +"\n");
                   }
                }
                 
               //While que elimina los terminales de la pila
               while(pila.VALOR()>=100)
               {
                   //Compara si el valor de la cima de la pila es un elemento terminal y lo compara con el elemento terminal del vector de entrada
                if (pila.VALOR() == tokenEntrada) 
                {
                    jTextAreaSintactico.append("Valor borrado de la pila: " + pila.POP() +"\n");
                    
                }
                else 
                {
                    //Si el valor de la pila no es igual al terminal del vector de entrada pero es mayo de 100 quiere decir que hay error de sintaxis
                    if (pila.VALOR() >= 100 && pila.VALOR()<1000) {
                        jTextAreaSintactico.append("ERROR 999 DE SINTAXIS CORRIJA SU CÓDIGO");
                        JOptionPane.showMessageDialog(null, "Error","Error sintactico",JOptionPane.ERROR_MESSAGE);
                        bandera_while=false;
                        break;
                    } 
                    else 
                    {
                        //Si el resultado es igual a 999 quiere decir que es error
                        if (resultado == 999) {
                            jTextAreaSintactico.append("ERROR 999 DE SINTAXIS CORRIJA SU CÓDIGO");
                            JOptionPane.showMessageDialog(null, "Error","Error sintactico",JOptionPane.ERROR_MESSAGE);
                            bandera_while=false;
                            break;
                        }
                    }
                }
                  //if active of actions and remove of stack 
                if(pila.VALOR()>1000)
                {
                    Actions(pila.VALOR(), description);
                    jTextAreaSintactico.append("Valor borrado de la pila: " + pila.POP() +"\n");
                }//end if actions
                
                
                
                //Compara si el valor de la cima de la pila es igual al 143 y si se llego al final de los tokens analizados para salir del ciclo
                if(pila.VALOR()==143)
                {
                    jTextAreaSintactico.append("Valor borrado de la pila: " + pila.POP() +"\n");
                    JOptionPane.showMessageDialog(null, "Codigo correcto");
                    
                    for(Object o : operands)
                    {
                        System.out.println("id: " +o);
                    }
                    for(Object o : operators)
                    {
                        System.out.println("operator: " +o);
                    }
                    
                    
                    
                    bandera_while=false;
                    break;
                }
              
               //Si el valo de la pila es mayor a 100 se pide otro token para seguir analizando
                if(pila.VALOR()>=100 && pila.VALOR()<1000)
                {
                    tokenEntrada = Lexico(codigo_enviar);
                }
 
                }//end while of terminals
               
            }//end try
            //Está excepción la da la tabla lr si el token n es el que seguía según las producciones
            //devolverá un valor diferente y tirara un desbordamiento en la tabla lr
            catch (NullPointerException e)
            {
              
               jTextAreaSintactico.append("ERROR 999 DE SINTAXIS CORRIJA SU CÓDIGO");
               JOptionPane.showMessageDialog(null, "Error","Error ",JOptionPane.ERROR_MESSAGE);
               break;
               
            }
        }//end while main

    }//Fin del método de sintaxis
}//Fin de la clase analiza
