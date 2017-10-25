package accesoDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import auxiliares.LeeProperties;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJDBC implements I_Acceso_Datos {

	private String driver, urlbd, user, password; // Datos de la conexion
	private Connection conn1;
	

	public AccesoJDBC() {
		System.out.println("ACCESO A DATOS - Acceso JDBC");
		
		try {
			HashMap<String,String> datosConexion;
			
			LeeProperties properties = new LeeProperties("Ficheros/config/accesoJDBC.properties");
			datosConexion = properties.getHash();		
			
			driver = datosConexion.get("driver");
			urlbd = datosConexion.get("urlbd");
			user = datosConexion.get("user");
			password = datosConexion.get("password");
			conn1 = null;
			
			Class.forName(driver);
			conn1 = DriverManager.getConnection(urlbd, user, password);
			if (conn1 != null) {
				System.out.println("Conectado a la base de datos");
			} 

		} catch (ClassNotFoundException e1) {
			System.out.println("ERROR: No Conectado a la base de datos. No se ha encontrado el driver de conexion");
			//e1.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("ERROR: No se ha podido conectar con la base de datos");
			System.out.println(e.getMessage());
			//e.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		}
	}

	public int cerrarConexion() {
		try {
			conn1.close();
			System.out.println("Cerrada conexion");
			return 0;
		} catch (Exception e) {
			System.out.println("ERROR: No se ha cerrado corretamente");
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();
		Deposito auxDeposito;
		int clave;
		String query = "SELECT * from depositos";
		Statement st;
		ResultSet rs;
		
		try {
			st = conn1.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				clave=rs.getInt("valor");
				auxDeposito = new Deposito(rs.getString("nombre"),clave,rs.getInt("cantidad"));
				depositosCreados.put(clave, auxDeposito);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		return depositosCreados;

	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String,Dispensador> dispensadoresCreado = new HashMap<String,Dispensador>();
		String clave;
		Dispensador dispensador;
		String query = "SELECT * from dispensadores";
		Statement st;
		ResultSet rs;
		
		try {
			st = conn1.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				clave=rs.getString("clave");
				dispensador = new Dispensador(clave,rs.getString("nombre"),rs.getInt("precio"),rs.getInt("cantidad"));
				dispensadoresCreado.put(clave, dispensador);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return dispensadoresCreado;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;
		PreparedStatement ps;
		String query;
		
		 for(Integer key:depositos.keySet()){
			 Deposito value=depositos.get(key);
			 query = "UPDATE depositos SET cantidad=? WHERE valor ="+key;
			 try {
				ps = conn1.prepareStatement(query);
				ps.setInt(1, value.getCantidad());
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			 
		 }
		/*
		boolean todoOK = true;
		Deposito auxDeposito = null;
		Integer clave = null;
		PreparedStatement ps;
		String query = "UPDATE depositos SET cantidad=? WHERE valor ="+clave;
		try {
			Iterator iterador = depositos.entrySet().iterator();
		    //Iterator<Map.Entry<String, Float>> iterador = listaProductos.entrySet().iterator();
		    HashMap.Entry producto;
		    while (iterador.hasNext()) {
		        producto = (HashMap.Entry) iterador.next();
		        clave = (int) producto.getKey();
		        auxDeposito = (Deposito) producto.getValue();
		        //System.out.println(producto.getKey() + " - " + producto.getValue());
		    } 
		    
		    ps = conn1.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		    ps.setInt(1, auxDeposito.getCantidad());
		    System.out.println(clave);
		    System.out.println(auxDeposito.getCantidad());

		    try {
		    	ps.executeUpdate();
			} catch (Exception e) {
				return false;
			}
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			return false;
		}*/
		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = true;
		PreparedStatement ps;
		String query;
		
		 for(String key:dispensadores.keySet()){
			 Dispensador value=dispensadores.get(key);
			 query = "UPDATE `dispensadores` SET `cantidad`=? WHERE dispensadores.clave='"+key+"'";
			 try {
				ps = conn1.prepareStatement(query);
				ps.setInt(1, value.getCantidad());
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			 
		 }
		
		
		
		/*
		Dispensador auxDispensador = null;
		String clave = null;
		String query = "UPDATE `dispensadores` SET `cantidad`=? WHERE dispensadores.clave='"+clave+"'";

		try {
			Iterator iterador = dispensadores.entrySet().iterator();
		    //Iterator<Map.Entry<String, Float>> iterador = listaProductos.entrySet().iterator();
			HashMap.Entry disp;
		    while (iterador.hasNext()) {
		    	disp = (HashMap.Entry) iterador.next();
		        clave = (String) disp.getKey();
		        auxDispensador = (Dispensador) disp.getValue();
		    } 
		    
			ps = conn1.prepareStatement(query);
		    ps.setInt(1, auxDispensador.getCantidad());
		    System.out.println(clave);
		    System.out.println(auxDispensador.getCantidad());
		    try {
		    	ps.executeUpdate();
			} catch (Exception e) {
				return false;
			}
		    
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			return false;
		}
		*/
		return todoOK;
	}

} // Fin de la clase