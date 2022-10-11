package Caso3.Time;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Caso3.Garden.ConfiguracionPlanta;
import Caso3.Garden.Estacion;
import Caso3.Garden.EstadoPlanta;
import Caso3.Garden.Planta;

public class CicloTiempo {

	public static String rutaImagenes = "C:\\Users\\Erick Kauffmann\\eclipse-workspace\\Caso3\\src\\Caso3\\Garden\\Imagenes\\";
	final ArrayList<Planta> jardin = new ArrayList<Planta>();
	final ArrayList<ConfiguracionPlanta> configuracionesPlantas;
	
	public CicloTiempo(ArrayList<ConfiguracionPlanta> plantas) {
		configuracionesPlantas = plantas;
	}
	
	public void Simular(ArrayList<Estacion> estaciones) {
		JFrame frame = new JFrame("Jardin");  
	    JPanel panel1 = new JPanel();
	    JPanel panel2 = new JPanel();
	    JPanel panel3 = new JPanel();
		Interfaz(frame, panel1, panel2, panel3);
		boolean simulacion = true;
		int dia = 0;
		Estacion estacion;
		Estacion estacionAct;
		
		while(simulacion == true) {
			dia++;
			IncrementarEdad();
			DecrementarAbono();
			DecrementarAgua();
			if(dia > 365) {
				dia = 1;
			}
			System.out.println("--------------------------------");
			System.out.println("Dia: "+dia);
			try {
				panel3.removeAll();
				dibujarJardin(frame, panel3);
				estacionAct = null;
				for(int contEstacion=0; contEstacion<estaciones.size(); contEstacion++) {
					estacion = estaciones.get(contEstacion);
					if((dia >= estacion.inicioEstacion)&&(dia <= estacion.finEstacion)) {
						estacionAct = estacion;
						break;
					}
				}
				
				DecrementarSalud(estacionAct);
				dibujarEstacionActual(estacionAct, dia, panel2);
				System.out.println("--------------------------------");
				Thread.sleep(4000);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}
	}
	
	
	public void IncrementarEdad() {
		Planta plantaAct;
		for(int i=0; i<jardin.size(); i++) {
			plantaAct = jardin.get(i); 
			plantaAct.setEdad(plantaAct.edad + 1);
		}
	}
	
	public void DecrementarAbono() {
		Planta plantaAct;
		for(int i=0; i<jardin.size(); i++) {
			plantaAct = jardin.get(i); 
			plantaAct.cantAbonoAct -= 1;
		}
	}
	
	public void DecrementarAgua() {
		Planta plantaAct;
		for(int i=0; i<jardin.size(); i++) {
			plantaAct = jardin.get(i); 
			plantaAct.cantAguaAct -= 1;
		}
	}
	
	public void DecrementarSalud(Estacion estacionAct) {
		Planta plantaAct;
		for(int i=0; i<jardin.size(); i++) {
			plantaAct = jardin.get(i); 
			plantaAct.salud -= estacionAct.dano;
		}
	}
	

	
	public void Interfaz(JFrame frame, JPanel panel1, JPanel panel2, JPanel panel3) {
		
        //JLabel label = new JLabel("Motor");  
        
        /*for(int cont9=0; cont9<labelNivelBaterias.size(); cont9++) {
        	panel2.add(labelNivelBaterias.get(cont9));
        }
        for(int cont8=0; cont8<labelCapacidadPaneles.size(); cont8++) {
        	panel2.add(labelCapacidadPaneles.get(cont8));
        }*/
		
		JPanel mainPanel = new JPanel();
		BorderLayout mainLayout = new BorderLayout();
		mainPanel.setLayout(mainLayout);

		
		
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//    HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(800,800));//.setBounds(50, 30, 300, 50);		
		
		
		FlowLayout f1 = new FlowLayout();
        panel1.setLayout(f1);
		
		FlowLayout f2 = new FlowLayout();
        panel2.setLayout(f2);
        
        //FlowLayout f3 = new FlowLayout();
        BoxLayout f3 = new BoxLayout(panel3, BoxLayout.Y_AXIS);
        panel3.setLayout(f3);
        
        mainPanel.add(panel1, BorderLayout.NORTH);
        panel1.add(panel2);
        mainPanel.add(panel3, BorderLayout.CENTER);
        
        //frame.add(mainPanel);
        frame.add(scrollPane);
          
		dibujarTipoPlantas(panel1);

        frame.setSize(1800, 1600);  
        frame.setLocationRelativeTo(null);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setVisible(true); 
        
	}
	
	public void dibujarJardin(JFrame frame, JPanel panel) {

		Planta plantaImprimir;
		for(int j=0;j<jardin.size();j++) {
			plantaImprimir = jardin.get(j);
	        
			EstadoPlanta estadoPlanta = plantaImprimir.BuscarEstado();
	        
			plantaImprimir.Dibujar(j, panel);
	        
	        if(plantaImprimir.EstaViva() == true) {
	    	    if((plantaImprimir.cantAbonoAct < estadoPlanta.minimoAbono)||(plantaImprimir.cantAbonoAct > estadoPlanta.maximoAbono)) {
	    	    	plantaImprimir.salud -= 1;
	    		}
	    	    if((plantaImprimir.cantAguaAct < estadoPlanta.minimoAgua)||(plantaImprimir.cantAguaAct > estadoPlanta.maximoAgua)) {
	    	    	plantaImprimir.salud -= 1;
	    		}
	    	    if(((plantaImprimir.cantAbonoAct > estadoPlanta.minimoAbono)&&(plantaImprimir.cantAbonoAct < estadoPlanta.maximoAbono))&&((plantaImprimir.cantAguaAct > estadoPlanta.minimoAgua)||(plantaImprimir.cantAguaAct < estadoPlanta.maximoAgua))) {
	    	    	if(plantaImprimir.salud < estadoPlanta.maximoVida) {
	    	    		plantaImprimir.salud += 10;
	    	    	}
	    	    }
	        }
    	    
		}
		//panel.revalidate();
		//panel.repaint();
	}
	
	public void dibujarTipoPlantas(JPanel panel) {
		//frame.removeAll();
        //fl.setHgap(1);

		ConfiguracionPlanta tipoPlanta;
		for(int j=0;j<configuracionesPlantas.size();j++) {
			tipoPlanta = configuracionesPlantas.get(j);
			JButton button = new JButton();
			button.setActionCommand(Integer.toString(j));
	        button.setText("Agregar " + tipoPlanta.nombrePlanta);  
	        panel.add(button);
	        
	        button.addActionListener(new ActionListener(){  
	        	public void actionPerformed(ActionEvent e){
	        		int iTipoPlanta = Integer.parseInt(e.getActionCommand());
	        		ConfiguracionPlanta config = configuracionesPlantas.get(iTipoPlanta);

	        		Planta plantaInsertada = new Planta();
	        		plantaInsertada.infoPlanta = config;
	        		plantaInsertada.id = jardin.size();
	        		plantaInsertada.setEdad(0);
	        		EstadoPlanta estPlantaAct = plantaInsertada.BuscarEstado();
	        		plantaInsertada.salud = estPlantaAct.maximoVida;
	        		plantaInsertada.cantAguaAct = estPlantaAct.minimoAgua;
	        		plantaInsertada.cantAbonoAct = estPlantaAct.minimoAbono;
	        		
	        	    jardin.add(plantaInsertada);
	        	    //dibujarJardin();
	        	}  
       	    });
		}
	}

	public void dibujarEstacionActual(Estacion estacion, int dia, JPanel panel) {
		panel.removeAll();
		
		try {
			BufferedImage myPicture = null;
			if (estacion.nombreEstacion.compareTo("Verano") == 0) {
				myPicture = ImageIO.read(new File(rutaImagenes + "verano.png"));
			}
			else if (estacion.nombreEstacion.compareTo("Invierno") == 0){
				myPicture = ImageIO.read(new File(rutaImagenes + "invierno.png"));
			}
			else if (estacion.nombreEstacion.compareTo("Primavera") == 0) {
				myPicture = ImageIO.read(new File(rutaImagenes + "primavera.png"));
			}
			else if (estacion.nombreEstacion.compareTo("Otoño") == 0){
				myPicture = ImageIO.read(new File(rutaImagenes + "otono.png"));
			}
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			panel.add(picLabel);
		}
		catch(Exception exc)
		{
			System.out.println("Error cargando imagen " + exc.getMessage());
		}		
		
		JButton button = new JButton();
        button.setText("Estacion:" + estacion.nombreEstacion);  
        panel.add(button);
        
		button = new JButton();
        button.setText("Dia: " + dia);  
        panel.add(button);

        panel.revalidate();
		panel.repaint();
        
	}
	
}


/*
plantaImprimir.Imprimir();
JButton button2 = new JButton();
button2.setActionCommand(Integer.toString(j));
button2.setText("Abonar " + plantaImprimir.infoPlanta.nombrePlanta + " " + j);  
panel.add(button2);

button2.addActionListener(new ActionListener(){  
	public void actionPerformed(ActionEvent e){
		int iPlanta = Integer.parseInt(e.getActionCommand());
		Planta planta = jardin.get(iPlanta);
		if(planta.EstaViva() == true) {
    		System.out.println("ABONANDO " + planta.infoPlanta.nombrePlanta + " " + iPlanta);
    		planta.Abonar();
		}
		else {
			System.out.println("MURIO " + planta.infoPlanta.nombrePlanta + " " + iPlanta);
		}
	}  
	    });

JButton button3 = new JButton();
button3.setActionCommand(Integer.toString(j));
button3.setText("Regar " + plantaImprimir.infoPlanta.nombrePlanta + " " + j);  
panel.add(button3);

button3.addActionListener(new ActionListener(){  
	public void actionPerformed(ActionEvent e){
		int iPlanta = Integer.parseInt(e.getActionCommand());
		Planta planta = jardin.get(iPlanta);
		if(planta.EstaViva() == true) {
    		System.out.println("REGANDO " + planta.infoPlanta.nombrePlanta + " " + iPlanta);
    		planta.Regar();
		}
		else {
			System.out.println("MURIO " + planta.infoPlanta.nombrePlanta + " " + iPlanta);
		}
	}  
	    });
*/	        

/*public void DibujarPlanta(Planta planta, EstadoPlanta estado, int idPlanta, JPanel panelJardin) {
JPanel panel = new JPanel();
panelJardin.add(panel);

FlowLayout f1 = new FlowLayout();
panel.setLayout(f1);
panel.setBorder(BorderFactory.createLineBorder(Color.black));

planta.Imprimir();

try {
	BufferedImage myPicture = null;
	if (planta.EstaViva()) {
		myPicture = ImageIO.read(new File(rutaImagenes + estado.fotoPlantaViva));
	}
	else {
		myPicture = ImageIO.read(new File(rutaImagenes + estado.fotoPlantaMuerta));
	}
	JLabel picLabel = new JLabel(new ImageIcon(myPicture));
	panel.add(picLabel);
}
catch(Exception e)
{
	
}


JButton button2 = new JButton();
button2.setActionCommand(Integer.toString(idPlanta));
button2.setText("Abonar " + planta.infoPlanta.nombrePlanta + " " + idPlanta);  
panel.add(button2);

button2.addActionListener(new ActionListener(){  
	public void actionPerformed(ActionEvent e){
		int iPlanta = Integer.parseInt(e.getActionCommand());
		Planta planta = jardin.get(iPlanta);
		if(planta.EstaViva() == true) {
    		System.out.println("ABONANDO " + planta.infoPlanta.nombrePlanta + " " + iPlanta);
    		planta.Abonar();
		}
		else {
			System.out.println("MURIO " + planta.infoPlanta.nombrePlanta + " " + iPlanta);
		}
	}  
	    });

JButton button3 = new JButton();
button3.setActionCommand(Integer.toString(idPlanta));
button3.setText("Regar " + planta.infoPlanta.nombrePlanta + " " + idPlanta);  
panel.add(button3);

button3.addActionListener(new ActionListener(){  
	public void actionPerformed(ActionEvent e){
		int iPlanta = Integer.parseInt(e.getActionCommand());
		Planta planta = jardin.get(iPlanta);
		if(planta.EstaViva() == true) {
    		System.out.println("REGANDO " + planta.infoPlanta.nombrePlanta + " " + iPlanta);
    		planta.Regar();
		}
		else {
			System.out.println("MURIO " + planta.infoPlanta.nombrePlanta + " " + iPlanta);
		}
	}  
	    });

}*/
