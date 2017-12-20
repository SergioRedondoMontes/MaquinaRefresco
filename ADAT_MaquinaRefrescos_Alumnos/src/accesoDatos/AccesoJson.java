package accesoDatos;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import auxiliares.ApiRequests;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJson implements I_Acceso_Datos {
	ApiRequests encargadoPeticiones;
	private String SERVER_PATH, GET, SET_DISPENSADOR,SET_DEPOSITO;

	public AccesoJson() {

		encargadoPeticiones = new ApiRequests();

		SERVER_PATH = "http://localhost/PHPmaquinaRefrescos/";

	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		GET = "leerDepositos.php";
		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();
		Deposito auxDeposito = null;
		int clave;

		try {

			System.out.println("---------- Leemos datos de JSON --------------------");

			System.out.println("Lanzamos peticion JSON para alumnos");

			String url = SERVER_PATH + GET; // Sacadas de configuracion

			System.out.println("La url a la que lanzamos la peticion es " + url);

			String response = encargadoPeticiones.getRequest(url);

			System.out.println(response); // Traza para pruebas
			JSONObject respuesta = (JSONObject) JSONValue.parse(response);
			JSONArray depositos = (JSONArray) respuesta.get("Depositos");
			System.out.println("--------" + depositos);
			for (Object object : depositos) {
				JSONObject aux = (JSONObject) object;

				clave = Integer.parseInt(aux.get("valor").toString());
				auxDeposito = new Deposito(aux.get("nombre").toString(), Integer.parseInt(aux.get("valor").toString()),
						Integer.parseInt(aux.get("cantidad").toString()));

				depositosCreados.put(clave, auxDeposito);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		GET = "leerDispensadores.php";
		HashMap<String, Dispensador> dispensadoresCreado = new HashMap<String, Dispensador>();
		String clave;
		Dispensador auxDispensador;

		try {

			System.out.println("---------- Leemos datos de JSON --------------------");

			System.out.println("Lanzamos peticion JSON para alumnos");

			String url = SERVER_PATH + GET; // Sacadas de configuracion

			System.out.println("La url a la que lanzamos la peticion es " + url);

			String response = encargadoPeticiones.getRequest(url);

			System.out.println(response); // Traza para pruebas
			JSONObject respuesta = (JSONObject) JSONValue.parse(response);
			JSONArray dispensadores = (JSONArray) respuesta.get("Dispensadores");
			System.out.println("--------" + dispensadores);
			for (Object object : dispensadores) {
				JSONObject aux = (JSONObject) object;

				clave = aux.get("clave").toString();
				auxDispensador = new Dispensador(clave, aux.get("nombre").toString(),
						Integer.parseInt(aux.get("precio").toString()),
						Integer.parseInt(aux.get("cantidad").toString()));

				dispensadoresCreado.put(clave, auxDispensador);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return dispensadoresCreado;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		SET_DEPOSITO = "escribirDepositos.php";
		JSONObject objDeposito = new JSONObject();
		JSONObject objPeticion = new JSONObject();
		try {

			for (Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {
				objDeposito.put("valor", entry.getValue().getValor());
				objDeposito.put("cantidad", entry.getValue().getCantidad());
				// Tenemos el jugador como objeto JSON. Lo aÒadimos a una peticion
				// Lo transformamos a string y llamamos al
				// encargado de peticiones para que lo envie al PHP

				objPeticion.put("peticion", "add");
				objPeticion.put("depositoAdd", objDeposito);
				String json = objPeticion.toJSONString();

				System.out.println("Lanzamos peticion JSON para almacenar un jugador");

				String url = SERVER_PATH + SET_DEPOSITO;

				System.out.println("La url a la que lanzamos la peticiÛn es " + url);
				System.out.println("El json que enviamos es: ");
				System.out.println(json);
				// System.exit(-1);

				String response = encargadoPeticiones.postRequest(url, json);

				System.out.println("El json que recibimos es: ");

				System.out.println(response); // Traza para pruebas
				//System.exit(-1);

				// Parseamos la respuesta y la convertimos en un JSONObject

				JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

				if (respuesta == null) { // Si hay alg˙n error de parseo (json
											// incorrecto porque hay alg˙n caracter
											// raro, etc.) la respuesta ser· null
					System.out.println("El json recibido no es correcto. Finaliza la ejecuciÛn");
					System.exit(-1);
				} else { // El JSON recibido es correcto

					// Sera "ok" si todo ha ido bien o "error" si hay alg˙n problema
					String estado = (String) respuesta.get("estado");
					if (estado.equals("ok")) {

						System.out.println("Almacenado jugador enviado por JSON Remoto");

					} else { // Hemos recibido el json pero en el estado se nos
								// indica que ha habido alg˙n error

						System.out.println("Acceso JSON REMOTO - Error al almacenar los datos");
						System.out.println("Error: " + (String) respuesta.get("error"));
						System.out.println("Consulta: " + (String) respuesta.get("query"));

						System.exit(-1);

					}
				}
			}

			

			
		} catch (Exception e) {
			System.out.println(
					"Excepcion desconocida. Traza de error comentada en el mÈtodo 'annadirJugador' de la clase JSON REMOTO");
			// e.printStackTrace();
			System.out.println("Fin ejecuciÛn");
			System.exit(-1);
			return false;
		}

		return true;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		
		SET_DISPENSADOR = "escribirDispensadores.php";
		JSONObject objDispensador = new JSONObject();
		JSONObject objPeticion = new JSONObject();
		try {

			for (Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
				objDispensador.put("clave", entry.getValue().getClave());
				objDispensador.put("cantidad", entry.getValue().getCantidad());
				// Tenemos el jugador como objeto JSON. Lo aÒadimos a una peticion
				// Lo transformamos a string y llamamos al
				// encargado de peticiones para que lo envie al PHP

				objPeticion.put("peticion", "add");
				objPeticion.put("dispensadorAdd", objDispensador);
				String json = objPeticion.toJSONString();

				System.out.println("Lanzamos peticion JSON para almacenar un jugador");

				String url = SERVER_PATH + SET_DISPENSADOR;

				System.out.println("La url a la que lanzamos la peticiÛn es " + url);
				System.out.println("El json que enviamos es: ");
				System.out.println(json);
				// System.exit(-1);

				String response = encargadoPeticiones.postRequest(url, json);

				System.out.println("El json que recibimos es: ");

				System.out.println(response); // Traza para pruebas
				//System.exit(-1);

				// Parseamos la respuesta y la convertimos en un JSONObject

				JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

				if (respuesta == null) { // Si hay alg˙n error de parseo (json
											// incorrecto porque hay alg˙n caracter
											// raro, etc.) la respuesta ser· null
					System.out.println("El json recibido no es correcto. Finaliza la ejecuciÛn");
					System.exit(-1);
				} else { // El JSON recibido es correcto

					// Sera "ok" si todo ha ido bien o "error" si hay alg˙n problema
					String estado = (String) respuesta.get("estado");
					if (estado.equals("ok")) {

						System.out.println("Almacenado jugador enviado por JSON Remoto");

					} else { // Hemos recibido el json pero en el estado se nos
								// indica que ha habido alg˙n error

						System.out.println("Acceso JSON REMOTO - Error al almacenar los datos");
						System.out.println("Error: " + (String) respuesta.get("error"));
						System.out.println("Consulta: " + (String) respuesta.get("query"));

						System.exit(-1);

					}
				}
			}

			

			
		} catch (Exception e) {
			System.out.println(
					"Excepcion desconocida. Traza de error comentada en el mÈtodo 'annadirJugador' de la clase JSON REMOTO");
			// e.printStackTrace();
			System.out.println("Fin ejecuciÛn");
			System.exit(-1);
			return false;
		}

		return true;
	}

}
