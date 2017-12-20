package accesoDatos;

import java.util.HashMap;

import auxiliares.ApiRequests;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJson implements I_Acceso_Datos{
	ApiRequests encargadoPeticiones;
	private String SERVER_PATH, GET;
	
	public AccesoJson(){
		
		encargadoPeticiones = new ApiRequests();
		

		SERVER_PATH = "http://localhost/adat_alumnos/";
	
		
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		// TODO Auto-generated method stub
		return null;
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
