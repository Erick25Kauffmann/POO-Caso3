package Caso3.Garden;

//import Caso3.Time.CicloTiempo;

import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.*;
import java.util.ArrayList;

//import java.lang.reflect.Field;
import org.json.simple.*;
import org.json.simple.parser.*;

import Caso3.Time.CicloTiempo;



public class Garden {
	public static void main(String[] args) {		
		JSONParser parser = new JSONParser();
		//CicloTiempo adminTread = new CicloTiempo();
		//adminTread.run();
		try {
			
			Object obj = parser.parse(new FileReader("C:\\Users\\Erick Kauffmann\\eclipse-workspace\\Caso3\\src\\Caso3\\Garden\\garden.json"));
			JSONObject jsonObject = (JSONObject)obj;
			
			ArrayList<ConfiguracionPlanta> configuracionesPlantas = new ArrayList<ConfiguracionPlanta>();
			
			//String xxx = (String)jsonObject.get("xxx");	
	        
			System.out.println("_______________Plantas______________________");
			System.out.println("--------------------------------------------");
			
	        JSONArray arrayPlantas=(JSONArray)jsonObject.get("Planta");
	        
	        for (int i=0 ; i<arrayPlantas.size();i++)
	        {
	        	
	        	JSONObject oPlanta=(JSONObject) arrayPlantas.get(i);
	                
	        	String nombrePlanta=(String) oPlanta.get("Nombre");
	        	
	        	ConfiguracionPlanta configPlanta = new ConfiguracionPlanta();
        		configPlanta.nombrePlanta = nombrePlanta;
        		configPlanta.estados = new ArrayList<EstadoPlanta>();
        		
        		configuracionesPlantas.add(configPlanta);
        		
   		        	
	        	System.out.println("Nombre de planta: "+nombrePlanta);	
	        	System.out.println("--------------------------------------------");

	        	
	        	JSONArray arrayEstados=(JSONArray)oPlanta.get("Estado");
	        	for (int j=0 ; j<arrayEstados.size();j++)
		        {
	        		JSONObject estado=(JSONObject) arrayEstados.get(j);
	        		
	        		String tamano=(String) estado.get("tamaño");
	        		String tiempoTamano=(String) estado.get("tiempoTam");
	        		String minimoVida=(String) estado.get("minVida");
	        		String maximoVida=(String) estado.get("maxVida");
	        		String minimoAgua=(String) estado.get("minAgua");
	        		String maximoAgua=(String) estado.get("maxAgua");
	        		String minimoAbono=(String) estado.get("minAbono");
	        		String maximoAbono=(String) estado.get("maxAbono");
	        		String fotoPlantaViva=(String) estado.get("fotoViva");
	        		String fotoPlantaMuerta=(String) estado.get("fotoMuerta");
	        		
	        		EstadoPlanta estadoPlanta = new EstadoPlanta();
	        		estadoPlanta.tamano = tamano;
	        		estadoPlanta.tiempoTamano = Integer.parseInt(tiempoTamano);
	        		estadoPlanta.minimoVida = Integer.parseInt(minimoVida);
	        		estadoPlanta.maximoVida = Integer.parseInt(maximoVida);
	        		estadoPlanta.minimoAgua = Integer.parseInt(minimoAgua);
	        		estadoPlanta.maximoAgua = Integer.parseInt(maximoAgua);
	        		estadoPlanta.minimoAbono = Integer.parseInt(minimoAbono);
	        		estadoPlanta.maximoAbono = Integer.parseInt(maximoAbono);
	        		estadoPlanta.fotoPlantaViva = fotoPlantaViva;
	        		estadoPlanta.fotoPlantaMuerta = fotoPlantaMuerta;
	        		
	        		
	        		configPlanta.estados.add(estadoPlanta);
	        		
	        		
	        		
	        		
	        		System.out.println("tamano: "+tamano);
	        		System.out.println("tiempoTamano: "+tiempoTamano);
	        		System.out.println("minimoVida: "+minimoVida);
	        		System.out.println("maximoVida: "+maximoVida);
	        		System.out.println("minimoAgua: "+minimoAgua);
	        		System.out.println("maximoAgua: "+maximoAgua);
	        		System.out.println("minimoAbono: "+minimoAbono);
	        		System.out.println("maximoAbono: "+maximoAbono);
	        		System.out.println("fotoPlantaViva: "+fotoPlantaViva);
	        		System.out.println("fotoPlantaMuerta: "+fotoPlantaMuerta);  
	        		System.out.println("--------------------------------------------");
	        		
	        		
	        		
	        		
	        		
		        }
	        	
	        	
	        }
	       System.out.println("_______________Estaciones______________________");
	       System.out.println("--------------------------------------------");
	       
	       ArrayList<Estacion> estaciones = new ArrayList<Estacion>();
	      	       
	       JSONArray arrayEstaciones=(JSONArray)jsonObject.get("Estacion");
	       
	       for (int i=0 ; i<arrayEstaciones.size();i++)
	       {
	        	JSONObject estacion=(JSONObject) arrayEstaciones.get(i);
	        		        		        		
	        		String nombreEstacion=(String) estacion.get("nomEstacion");
	        		String inicioEstacion=(String) estacion.get("startDate");
	        		String finEstacion=(String) estacion.get("endDate");
	        		String minimoAgua=(String) estacion.get("minAgua");
	        		String maximoAgua=(String) estacion.get("maxAgua");
	        		String dano=(String) estacion.get("daño");
	        		
	        		Estacion estacionAct = new Estacion();
	        		estacionAct.nombreEstacion = nombreEstacion;
	        		estacionAct.inicioEstacion = Integer.parseInt(inicioEstacion);
	        		estacionAct.finEstacion = Integer.parseInt(finEstacion);
	        		estacionAct.minimoAgua = Integer.parseInt(minimoAgua);
	        		estacionAct.maximoAgua = Integer.parseInt(maximoAgua);
	        		estacionAct.dano = Integer.parseInt(dano);
	        		
	        		estaciones.add(estacionAct);
	        	

	        		System.out.println("nombreEstacion: "+nombreEstacion);
	        		System.out.println("inicioEstacion: "+inicioEstacion);
	        		System.out.println("finEstacion: "+finEstacion);
	        		System.out.println("minimoAgua: "+minimoAgua);
	        		System.out.println("maximoAgua: "+maximoAgua);
	        		System.out.println("dano: "+dano);
	        		System.out.println("--------------------------------------------");	        		
	        }
	       
	       
	       CicloTiempo simulacionTiempo = new CicloTiempo(configuracionesPlantas);
	       simulacionTiempo.Simular(estaciones);
	       
	       

		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
}
