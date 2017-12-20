package accesoDatos;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import auxiliares.ApiRequests;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJson implements I_Acceso_Datos{
	ApiRequests encargadoPeticiones;
	private String SERVER_PATH, GET;
	
	public AccesoJson(){
		
		encargadoPeticiones = new ApiRequests();
		

		SERVER_PATH = "http://localhost/PHPmaquinaRefrescos/";
	
		
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		GET = "leerDepositos.php";
		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();
		Deposito auxDeposito = null;
		int clave;
		

		try{
			
		    System.out.println("---------- Leemos datos de JSON --------------------");	
		    
			System.out.println("Lanzamos peticion JSON para alumnos");
			
			String url = SERVER_PATH + GET; // Sacadas de configuracion
			
			System.out.println("La url a la que lanzamos la peticion es " + url);
			
			String response = encargadoPeticiones.getRequest(url);
			
			System.out.println(response); // Traza para pruebas
			JSONObject respuesta = (JSONObject) JSONValue.parse(response);
			JSONArray depositos = (JSONArray) respuesta.get("Depositos");
			System.out.println("--------"+depositos);
			for (Object object : depositos) {
				JSONObject aux = (JSONObject) object;

				clave = Integer.parseInt(aux.get("valor").toString());
				auxDeposito = new Deposito(aux.get("nombre").toString(), Integer.parseInt(aux.get("id").toString()), Integer.parseInt(aux.get("cantidad").toString()));
				
				depositosCreados.put(clave, auxDeposito);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		GET = "leerDispensadores.php";
		HashMap<String,Dispensador> dispensadoresCreado = new HashMap<String,Dispensador>();
		String clave;
		Dispensador auxDispensador;
		
try{
			
		    System.out.println("---------- Leemos datos de JSON --------------------");	
		    
			System.out.println("Lanzamos peticion JSON para alumnos");
			
			String url = SERVER_PATH + GET; // Sacadas de configuracion
			
			System.out.println("La url a la que lanzamos la peticion es " + url);
			
			String response = encargadoPeticiones.getRequest(url);
			
			System.out.println(response); // Traza para pruebas
			JSONObject respuesta = (JSONObject) JSONValue.parse(response);
			JSONArray dispensadores = (JSONArray) respuesta.get("Dispensadores");
			System.out.println("--------"+dispensadores);
			for (Object object : dispensadores) {
				JSONObject aux = (JSONObject) object;

				clave = aux.get("clave").toString();
				auxDispensador = new Dispensador(clave, aux.get("nombre").toString(), Integer.parseInt(aux.get("precio").toString()), Integer.parseInt(aux.get("cantidad").toString()));
						
				dispensadoresCreado.put(clave, auxDispensador);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return dispensadoresCreado;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		// TODO Auto-generated method stub
		return false;
	}

}
