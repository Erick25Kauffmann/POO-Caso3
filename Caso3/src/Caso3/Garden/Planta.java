package Caso3.Garden;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Caso3.Time.CicloTiempo;

public class Planta {
	public ConfiguracionPlanta infoPlanta;
	public int id = 0;
	public int edad;
	public String tamanoAct;
	public int salud;
	public int cantAguaAct;
	public int cantAbonoAct;
	
	public void setEdad(int edadDias) {
		this.edad = edadDias;
		String tamActual = "";
		int saludAct = 0;
		EstadoPlanta estadoAct;
		for(int i=0;i<infoPlanta.estados.size();i++) {
			estadoAct = infoPlanta.estados.get(i);
			if(edad < estadoAct.tiempoTamano) {
				tamActual = estadoAct.tamano;
				break;
			}
		}
		this.tamanoAct = tamActual;
	}
	
	public EstadoPlanta BuscarEstado() {
		EstadoPlanta estActual = null;
		EstadoPlanta estadoAct;
		for(int i=0;i<infoPlanta.estados.size();i++) {
			estadoAct = infoPlanta.estados.get(i);
			if(tamanoAct.compareTo(estadoAct.tamano) == 0) {
				estActual = estadoAct;
				break;
			}
		}
		return estActual;
	}
	
	
	public void Abonar() {
		cantAbonoAct += 5;
		EstadoPlanta estadoPlanta = BuscarEstado();
		if((cantAbonoAct < estadoPlanta.minimoAbono)||(cantAbonoAct > estadoPlanta.maximoAbono)) {
			salud -= 3;
		}
	}
	
	public void Regar() {
		cantAguaAct += 5;
		EstadoPlanta estadoPlanta = BuscarEstado();
		if((cantAguaAct < estadoPlanta.minimoAgua)||(cantAguaAct > estadoPlanta.maximoAgua)) {
			salud -= 3;
		}
	}
	
	public boolean EstaViva() {
		EstadoPlanta estadoPlanta = BuscarEstado();
		if(salud >= estadoPlanta.minimoVida) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public void Dibujar(int idPlanta, JPanel panelJardin) {
		EstadoPlanta estado = BuscarEstado();
		final Planta yo = this;
		
		JPanel panel = new JPanel();
		panelJardin.add(panel);
		
		GridLayout f1 = new GridLayout(2,2);
        panel.setLayout(f1);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        JPanel panelLabel = new JPanel();
		GridLayout pl = new GridLayout(2,3);
        panelLabel.setLayout(pl);
        
        
        
        panel.add(panelLabel);
        JLabel label = new JLabel();
        label.setText(infoPlanta.nombrePlanta + " " + id);
        panelLabel.add(label);
        label = new JLabel();
        label.setText("Edad: " + edad);
        panelLabel.add(label);
        label = new JLabel();
        label.setText("Tamano: " + tamanoAct);
        panelLabel.add(label);
        label = new JLabel();
        label.setText("Salud: " + salud);
        panelLabel.add(label);
        label = new JLabel();
        label.setText("Agua: " + cantAguaAct);
        panelLabel.add(label);
        label = new JLabel();
        label.setText("Abono: " + cantAbonoAct);
        panelLabel.add(label);
        
		this.Imprimir();

		try {
			BufferedImage myPicture = null;
			if (this.EstaViva()) {
				myPicture = ImageIO.read(new File(CicloTiempo.rutaImagenes + estado.fotoPlantaViva));
			}
			else {
				myPicture = ImageIO.read(new File(CicloTiempo.rutaImagenes + estado.fotoPlantaMuerta));
			}
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			panel.add(picLabel);
		}
		catch(Exception e)
		{
			
		}
		
		
		JButton button2 = new JButton();
		button2.setActionCommand(Integer.toString(idPlanta));
        button2.setText("Abonar " + this.infoPlanta.nombrePlanta + " " + idPlanta);  
        panel.add(button2);
        
        button2.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){
        		//int iPlanta = Integer.parseInt(e.getActionCommand());
        		//Planta planta = yo;//jardin.get(iPlanta);
        		if(yo.EstaViva() == true) {
	        		System.out.println("ABONANDO " + yo.infoPlanta.nombrePlanta + " " + yo.id);
	        		yo.Abonar();
        		}
        		else {
        			System.out.println("MURIO " + yo.infoPlanta.nombrePlanta + " " + yo.id);
        		}
        	}  
        	    });
        
        JButton button3 = new JButton();
		button3.setActionCommand(Integer.toString(idPlanta));
        button3.setText("Regar " + this.infoPlanta.nombrePlanta + " " + idPlanta);  
        panel.add(button3);
        
        button3.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){
        		//int iPlanta = Integer.parseInt(e.getActionCommand());
        		//Planta planta = yo;//jardin.get(iPlanta);
        		if(yo.EstaViva() == true) {
	        		System.out.println("REGANDO " + yo.infoPlanta.nombrePlanta + " " + yo.id);
	        		yo.Regar();
        		}
        		else {
        			System.out.println("MURIO " + yo.infoPlanta.nombrePlanta + " " + yo.id);
        		}
        	}  
        	    });
        
	}
	
	
	
	
	public void Imprimir() {
		System.out.println("______________________________________________");
		System.out.println(infoPlanta.nombrePlanta + " " + id);
		System.out.println("Edad en dias: " + edad);
		System.out.println("Tamano actual: " + tamanoAct);
		System.out.println("Salud: " + salud);
		System.out.println("Cantidad de agua actual: " + cantAguaAct);
		System.out.println("Cantidad de abono actual: " + cantAbonoAct);
		System.out.println("______________________________________________");
	}
}
