package accesoDatos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class FicherosTexto implements I_Acceso_Datos {
	
	File fDis; // FicheroDispensadores
	File fDep; // FicheroDepositos

	public FicherosTexto(){
		System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
	}
	
	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		
		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();
		fDep = new File("Ficheros/datos/depositos.txt");

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(fDep));
			String text = null;
			Deposito deposito = null;
			int clave = 0;

			while ((text = reader.readLine()) != null) {

				String[] datosaux = text.split(";");
				clave = Integer.parseInt(datosaux[1].toString());
				deposito = new Deposito (datosaux[0].toString(),clave,Integer.parseInt(datosaux[2].toString()));
				
				depositosCreados.put(clave, deposito);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		return depositosCreados;
	}
	

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		
		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<String,Dispensador>();
		fDis = new File("Ficheros/datos/dispensadores.txt");

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(fDis));
			String text = null;
			Dispensador dispensador = null;
			String clave = null;

			while ((text = reader.readLine()) != null) {

				String[] datosaux = text.split(";");
				clave = datosaux[0].toString();
				dispensador = new Dispensador (clave,datosaux[1].toString(),Integer.parseInt(datosaux[2].toString()),Integer.parseInt(datosaux[3].toString()));
				
				dispensadoresCreados.put(clave, dispensador);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dispensadoresCreados;
		
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {

		boolean todoOK = true;
		PrintWriter pw = null;
		fDis = new File("Ficheros/datos/depositos.txt");
		try {
			pw = new PrintWriter(fDis);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			for(Integer key:depositos.keySet()){
				 Deposito value=depositos.get(key);
				 pw.println(value.getNombreMoneda()+";"+key+";"+value.getCantidad());
			}
		} catch (Exception e) {
			return false;
		}
		

		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		boolean todoOK = true;
		PrintWriter pw = null;
		fDis = new File("Ficheros/datos/dispensadores.txt");
		try {
			pw = new PrintWriter(fDis);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			for(String key:dispensadores.keySet()){
				Dispensador value=dispensadores.get(key);
				 pw.println(key+";"+value.getNombreProducto()+";"+value.getPrecio() +";"+value.getCantidad());
			}
		} catch (Exception e) {
			return false;
		}
		

		return todoOK;
	}

} // Fin de la clase