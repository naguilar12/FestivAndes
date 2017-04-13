package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import dao.DAOTablaCategorias;
import dao.DAOTablaCliente;
import dao.DAOTablaCompa駃a;
import dao.DAOTablaEspectaculos;
import dao.DAOTablaFuncion;
import dao.DAOTablaSitio;
import oracle.sql.DATE;
import dao.DAOTablaEspectaculos;
import dao.DAOTablaEspectaculos;
import dao.DAOTablaEspectaculos;
import dao.DAOTablaEspectaculos;
import vos.Boleta;
import vos.Categoria;
import vos.Cliente;
import vos.Compa駃aTeatro;
import vos.Espectaculo;
import vos.Funcion;
import vos.ListaBoletas;
import vos.ListaCategorias;
import vos.ListaCompa駃as;
import vos.ListaEspectaculos;
import vos.ListaPreferencias;
import vos.ListaVideos;
import vos.Localidad;
import vos.Preferencia;
import vos.Resultado;
import vos.Video;

public class FestivAndesMaster {


	/**
	 * Atributo est谩tico que contiene el path relativo del archivo que tiene los datos de la conexi贸n
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo est谩tico que contiene el path absoluto del archivo que tiene los datos de la conexi贸n
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * Conexi贸n a la base de datos
	 */
	private Connection conn;


	/**
	 * M茅todo constructor de la clase VideoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logia de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesMaster, se inicializa el path absoluto de el archivo de conexi贸n y se
	 * inicializa los atributos que se usan par la conexi贸n a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public FestivAndesMaster(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/*
	 * M茅todo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexi贸n a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * M茅todo que  retorna la conexi贸n a la base de datos
	 * @return Connection - la conexi贸n a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexi贸n a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////TRANSACCIONES////

	//////////////////////////////////////////////////////////////////////////
	/////////////////////PREFERENCIA
	//////////////////////////////////////////////////////////////////////////


	public ListaPreferencias darPreferencias() throws Exception {
		ArrayList<Preferencia> preferencias;
		DAOTablaCliente daoUsuario = new DAOTablaCliente();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			preferencias = daoUsuario.darPreferencias();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaPreferencias(preferencias);
	}


	public ListaPreferencias buscarPreferenciasPorUsuario(int idUsuario) throws Exception {
		ArrayList<Preferencia> preferencias;
		DAOTablaCliente daoCliente = new DAOTablaCliente();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoCliente.setConn(conn);
			preferencias = daoCliente.buscarPreferenciasPorUsuario(idUsuario);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaPreferencias(preferencias);
	}

	public void addPreferencia(Preferencia preferencia, int idUsuario) throws Exception {
		DAOTablaCliente daoClientes = new DAOTablaCliente();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoClientes.setConn(conn);
			daoClientes.addPreferenciaCliente(preferencia, idUsuario);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deletePreferencia(Preferencia preferencia, int idUsuario) throws Exception {
		DAOTablaCliente daoCliente = new DAOTablaCliente();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoCliente.setConn(conn);
			daoCliente.deletePreferenciaCliente(preferencia, idUsuario);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	//	public ListaEspectaculos darEspectaculos() throws Exception {
	//		ArrayList<Espectaculo> espectaculos;
	//		DAOTablaEspectaculos daoEspectaculos = new DAOTablaEspectaculos();
	//		try 
	//		{
	//			//////Transacci贸n
	//			this.conn = darConexion();
	//			daoEspectaculos.setConn(conn);
	//			espectaculos = daoEspectaculos.darEspectaculos();
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoEspectaculos.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//		return new ListaEspectaculos(espectaculos);
	//	}







	public Funcion realizarFuncion(int idO, int idE,int idF) throws Exception {
		Funcion funcion;
		DAOTablaFuncion daoFuncion = new DAOTablaFuncion();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoFuncion.setConn(conn);

			funcion = daoFuncion.realizarFuncion(idO, idE, idF);
			funcion.setEspectaculo(buscarEspectaculoId(idE));

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFuncion.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return funcion;
	}

	public Espectaculo buscarEspectaculoId(int idE) throws Exception {
		Espectaculo espectaculo;
		DAOTablaEspectaculos daoEspectaculo = new DAOTablaEspectaculos();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoEspectaculo.setConn(conn);
			espectaculo = daoEspectaculo.darEspectaculo(idE);
			espectaculo.setCategorias(darCategoriasPorEspectaculoId(idE));

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspectaculo.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return espectaculo;
	}

	public Compa駃aTeatro darInfoCompaniasId(int idE) throws Exception {
		Compa駃aTeatro compa駃as;
		DAOTablaCompa駃a daoTablaCompa駃as = new DAOTablaCompa駃a();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoTablaCompa駃as.setConn(conn);
			compa駃as = daoTablaCompa駃as.darInfoCompaniasId(idE);
			List<Espectaculo> listaEsp = compa駃as.getEspectaculos().getEspectaculos();
			ArrayList<Funcion> funcionesEspect = listaEsp.get(1).getFunciones();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaCompa駃as.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return compa駃as;
	}

	public ListaCompa駃as darInfoCompanias() throws Exception {
		ArrayList<Compa駃aTeatro> companias;
		DAOTablaCompa駃a daoTablaCompa駃as = new DAOTablaCompa駃a();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoTablaCompa駃as.setConn(conn);
			companias = daoTablaCompa駃as.darInfoCompanias();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaCompa駃as.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaCompa駃as(companias);
	}
	
	public ListaCategorias darCategoriasPorEspectaculoId(int idE) throws Exception {
		ArrayList<Categoria> categorias;
		DAOTablaCategorias daoTablaCategoria = new DAOTablaCategorias();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoTablaCategoria.setConn(conn);
			categorias = daoTablaCategoria.darCategoriasEspectaculo(idE);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaCategoria.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaCategorias(categorias);
	}

	public ListaBoletas comprarMultiplesBoletas(ListaBoletas boletas, int id) throws Exception {
		ArrayList<Boleta> arregloBoletas;
		DAOTablaCliente daoTablaCliente = new DAOTablaCliente();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoTablaCliente.setConn(conn);

			boolean mismaLocalidad = true;
			int localidadID = boletas.getBoletas().get(0).getLocalidad().getId();
			for (Boleta boleta : boletas.getBoletas()) 
				if(boleta.getLocalidad().getId()!=localidadID)
					mismaLocalidad=false;	
			System.out.println(mismaLocalidad);
			System.out.println("LLEGUE 2222222222222222");

			boolean mismaFuncion = true;
			int funcionId = boletas.getBoletas().get(0).getFuncion().getId();
			for (Boleta boleta : boletas.getBoletas()) 
				if(boleta.getFuncion().getId()!=funcionId)
					mismaFuncion=false;	
			Funcion f = darFuncion(funcionId);
			System.out.println(mismaFuncion);
			System.out.println("LLEGUE 3333333333333333333");

			boolean numerada = false;
			Localidad loc = darLocalidad(localidadID);
			if(loc.getNumerada()==1)
				numerada = true;
			System.out.println(numerada);
			System.out.println("LLEGUE 4444444444444444444444444");

			Cliente cliente = darCliente(id);
			arregloBoletas = new ArrayList<Boleta>();

			if(numerada&&cliente!=null){
				System.out.println("LLEGUE 555555555555555555");
				boolean seguidas = true;
				for(int i = 0; i < boletas.getBoletas().size()-1 && seguidas && numerada; i++)
					if(boletas.getBoletas().get(i).getUbicacion() != boletas.getBoletas().get(i+1).getUbicacion()-1)
						seguidas = false;
				System.out.println(seguidas+"SEGUIDAS");

				boolean disponiblesNumeradas = true;
				for (int i = 0; i < boletas.getBoletas().size()&&numerada; i++) 
					disponiblesNumeradas = disponiblesNumeradas&&daoTablaCliente.boletaNumeradaDisponible(boletas.getBoletas().get(i));		
				System.out.println(disponiblesNumeradas+"Disponibles");

				if(seguidas&&disponiblesNumeradas&&mismaLocalidad&&mismaFuncion)
					for (Boleta boleta : boletas.getBoletas()){
						System.out.println("LLEGUE 666666666666666666666");
						Boleta agregada = daoTablaCliente.comprarBoletaNumerada(boleta, id,false);
						agregada.setLocalidad(loc);
						agregada.setFuncion(f);
						agregada.setCliente(cliente);
						arregloBoletas.add(agregada);
					}
			}
			else if(mismaLocalidad&&mismaFuncion&&!numerada&&cliente!=null){
				int ocupadas = daoTablaCliente.ocupadasBoletaNoNumeradas(boletas.getBoletas().get(0));
				int disponiblesNoNumeradas = loc.getCapacidad() - ocupadas;
				if(disponiblesNoNumeradas >= boletas.getBoletas().size())
				{
					arregloBoletas = daoTablaCliente.comprarBoletasNoNumeradas(boletas.getBoletas().get(0), id, boletas.getBoletas().size(), ocupadas, false);
					for (Boleta boleta : arregloBoletas) {
						boleta.setLocalidad(loc);
						boleta.setFuncion(f);
						boleta.setCliente(cliente);
					}
				}
			}

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaBoletas(arregloBoletas);
	}

	public ListaBoletas registrarCompraAbonamiento(ListaBoletas boletas, int id) throws Exception {
		ArrayList<Boleta> arregloBoletas;
		DAOTablaCliente daoTablaCliente = new DAOTablaCliente();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoTablaCliente.setConn(conn);

			boolean antesDeTresSem = true;

			boolean todasDisponibles = true;
			for (Boleta boleta : boletas.getBoletas()) {
				Localidad loc = darLocalidad(boleta.getLocalidad().getId());
				if(loc.getNumerada()==1)
					todasDisponibles = todasDisponibles&&daoTablaCliente.boletaNumeradaDisponible(boleta);
				else if(loc.getNumerada()==0)
				{
					int ocupadas = daoTablaCliente.ocupadasBoletaNoNumeradas(boleta);
					int disponibles = loc.getCapacidad() - ocupadas;
					todasDisponibles = todasDisponibles && (disponibles>0);
				}
			}
			Cliente c = darCliente(id);
			arregloBoletas = new ArrayList<>();
			if(antesDeTresSem&&todasDisponibles)
			{
				for (Boleta boleta : boletas.getBoletas()) {
					Localidad loc = darLocalidad(boleta.getLocalidad().getId());
					if(loc.getNumerada()==1){
						Boleta b = daoTablaCliente.comprarBoletaNumerada(boleta, id,true);
						b.setLocalidad(loc);
						b.setFuncion(darFuncion(boleta.getFuncion().getId()));
						b.setCliente(c);
						arregloBoletas.add(b);
					}
					else if(loc.getNumerada()==0)
					{
						int ocupadas = daoTablaCliente.ocupadasBoletaNoNumeradas(boleta);
						Boleta b = daoTablaCliente.comprarBoletasNoNumeradas(boleta, id, 1, ocupadas,true).get(0);
						b.setLocalidad(loc);
						b.setFuncion(darFuncion(boleta.getFuncion().getId()));
						b.setCliente(c);
						arregloBoletas.add(b);
					}
				}
			}

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaBoletas(arregloBoletas);
	}



	public Localidad darLocalidad(int id) throws Exception
	{
		Localidad localidad;
		DAOTablaSitio daoTablaSitio = new DAOTablaSitio();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoTablaSitio.setConn(conn);


			localidad = daoTablaSitio.darLocalidad(id);



		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaSitio.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return localidad;
	}

	public Funcion darFuncion(int id) throws Exception
	{
		Funcion funcion;
		DAOTablaFuncion daoTablaFuncion = new DAOTablaFuncion();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoTablaFuncion.setConn(conn);

			funcion = daoTablaFuncion.darFuncion(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaFuncion.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return funcion;
	}

	public Cliente darCliente(int id) throws Exception
	{
		Cliente cliente;
		DAOTablaCliente daoTablaCliente = new DAOTablaCliente();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoTablaCliente.setConn(conn);

			cliente = daoTablaCliente.darCliente(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cliente;
	}

	public Resultado consultarAsistenciaAlFestival(int idO, int idC) throws Exception
	{
		String resultado ="";
		DAOTablaCliente daoTablaCliente = new DAOTablaCliente();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoTablaCliente.setConn(conn);
			ListaBoletas bol = daoTablaCliente.darBoletasCliente(idC);
			
			ArrayList<Funcion> funciones = new ArrayList<>();
			for (Boleta b : bol.getBoletas()) {
				funciones.add(darFuncion(b.getFuncion().getId()));
			}
			
			for (Funcion funcion : funciones) {
				resultado += funcion.isRealizada();			
			}
			
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new Resultado(resultado);
	}

	

	public Boleta devolverBoleta(Boleta pBoleta, int id) throws Exception
	{
		System.out.println(1);
		DAOTablaCliente daoCliente = new DAOTablaCliente();
		Boleta devolver = null;
		try 
		{
			this.conn = darConexion();
			daoCliente.setConn(conn);
			System.out.println(pBoleta.getFuncion().getId() + " " + pBoleta.getLocalidad().getId() + " " + pBoleta.getUbicacion());
			devolver = daoCliente.darBoleta(pBoleta);
			Cliente cliente = darCliente(id);
			if(devolver!=null && cliente!=null)
			{
				System.out.println("entra");
				Funcion fun = devolver.getFuncion();
				Timestamp fechaA = fun.getFechaHora();
				Date fechafun = new Date(fechaA.getYear(),fechaA.getMonth(),fechaA.getDate());
				System.out.println(fechafun.toString());
				Calendar fechaB = Calendar.getInstance();
				fechaB.setTime(fechafun);
				fechaB.add(Calendar.DATE, -5);
				Date compararFecha = fechaB.getTime();
				System.out.println(fechaB.toString());
				Date actual = new Date();
				if(compararFecha.after(actual))
				{
					daoCliente.devolverBoleta(pBoleta);
				}
			}

		}
		catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return daoCliente.darBoleta(pBoleta);
	}

	public void devolverAbonamiento(Boleta pBoleta, int id) throws Exception
	{
		System.out.println(1);
		DAOTablaCliente daoCliente = new DAOTablaCliente();
		Boleta devolver = null;
		try 
		{
			this.conn = darConexion();
			daoCliente.setConn(conn);
			System.out.println(pBoleta.getFuncion().getId() + " " + pBoleta.getLocalidad().getId() + " " + pBoleta.getUbicacion());
			devolver = daoCliente.darBoleta(pBoleta);
			Cliente cliente = darCliente(id);
			if(devolver!=null && cliente!=null)
			{
				System.out.println("entra");
				Funcion fun = devolver.getFuncion();
				Timestamp fechaA = fun.getFechaHora();
				Date fechafun = new Date(fechaA.getYear(),fechaA.getMonth(),fechaA.getDate());
				System.out.println(fechafun.toString());
				Calendar fechaB = Calendar.getInstance();
				fechaB.setTime(fechafun);
				fechaB.add(Calendar.DATE, -21);
				Date compararFecha = fechaB.getTime();
				System.out.println(fechaB.toString());
				Date actual = new Date();
				if(compararFecha.after(actual))
				{
					daoCliente.devolverAbonamiento(pBoleta);
				}
			}

		}
		catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
	}


}
