package accesoDatos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;
import auxiliares.HibernateUtil;

public class AccesoHibernate implements I_Acceso_Datos {
	
Session session;
	
	public AccesoHibernate() {
		
		HibernateUtil util = new HibernateUtil(); 
		
		session = util.getSessionFactory().openSession();
		
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> MapDeposito = new HashMap <Integer, Deposito>();
        Query q= session.createQuery("select dep from Deposito dep");
        List results = q.list();
        
        Iterator depositosIterator= results.iterator();

        while (depositosIterator.hasNext()){
            Deposito deposito = (Deposito)depositosIterator.next();
            MapDeposito.put(deposito.getValor(), deposito);
        }

    	
		return MapDeposito;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> MapDispensador = new HashMap <String, Dispensador>();
        Query q= session.createQuery("select dep from Dispensador dep");
        List results = q.list();
        
        Iterator dispensadorIterator= results.iterator();

        while (dispensadorIterator.hasNext()){
            Dispensador dispensador = (Dispensador)dispensadorIterator.next();
            MapDispensador.put(dispensador.getClave(), dispensador);
        }

    	
		return MapDispensador;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;
		
        
        	try {
        		session.beginTransaction();
                for (int key : depositos.keySet()) {
                session.save(depositos.get(key));
                }
                session.getTransaction().commit();
                
			} catch (Exception e) {
				todoOK=false;
			}
		
        
		

		return todoOK;
		
		
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
boolean todoOK = true;
		
       
        	try {
        		
        		session.beginTransaction();
                for (String key : dispensadores.keySet()) {
        		
                	session.save(dispensadores.get(key));
                }

                session.getTransaction().commit();
                
			} catch (Exception e) {
				todoOK=false;
			}
		
        
		
		return todoOK;
	}

}
