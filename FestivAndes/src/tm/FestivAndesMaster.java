package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import dao.DAOTablaCategorias;
import dao.DAOTablaCliente;
import dao.DAOTablaCompa�ia;
import dao.DAOTablaEspectaculos;
import dao.DAOTablaFuncion;
import dao.DAOTablaSitio;
import dao.DAOTablaEspectaculos;
import dao.DAOTablaEspectaculos;
import dao.DAOTablaEspectaculos;
import dao.DAOTablaEspectaculos;
import vos.Boleta;
import vos.Categoria;
import vos.Cliente;
import vos.Compa�iaTeatro;
import vos.Espectaculo;
import vos.Funcion;
import vos.ListaBoletas;
import vos.ListaCategorias;
import vos.ListaCompa�ias;
import vos.ListaEspectaculos;
import vos.ListaPreferencias;
import vos.ListaVideos;
import vos.Localidad;
import vos.Preferencia;
import vos.Resultado;
import vos.Video;

public class FestivAndesMaster {


	/**
	 * Atributo estático que contiene el path relativo del archivo que tiene los datos de la conexión
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estático que contiene el path absoluto del archivo que tiene los datos de la conexión
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
	 * Conexión a la base de datos
	 */
	private Connection conn;


	/**
	 * Método constructor de la clase VideoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logia de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesMaster, se inicializa el path absoluto de el archivo de conexión y se
	 * inicializa los atributos que se usan par la conexión a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public FestivAndesMaster(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/*
	 * Método que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexión a la base de datos.
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
	 * Método que  retorna la conexión a la base de datos
	 * @return Connection - la conexión a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexión a la base de datos
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
			//////Transacción
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
			//////Transacción
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
			//////Transacción
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
			//////Transacción
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
	//			//////Transacción
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
			//////Transacción
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
			//////Transacción
			this.conn = darConexion();
			daoEspectaculo.setConn(conn);
			espectaculo = daoEspectaculo.darEspectaculo(idE);
			espectaculo.setCompa�ias(buscarCompa�iasEspectaculoId(idE));
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

	public ListaCompa�ias buscarCompa�iasEspectaculoId(int idE) throws Exception {
		ArrayList<Compa�iaTeatro> compa�ias;
		DAOTablaCompa�ia daoTablaCompa�ias = new DAOTablaCompa�ia();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoTablaCompa�ias.setConn(conn);
			compa�ias = daoTablaCompa�ias.darCompa�ias(idE);
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
				daoTablaCompa�ias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaCompa�ias(compa�ias);
	}

	public ListaCategorias darCategoriasPorEspectaculoId(int idE) throws Exception {
		ArrayList<Categoria> categorias;
		DAOTablaCategorias daoTablaCategoria = new DAOTablaCategorias();
		try 
		{
			//////Transacción
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
			//////Transacción
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
			//////Transacción
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
			//////Transacción
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
			//////Transacción
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
			//////Transacción
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
			//////Transacción
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

	

	//	public ListaEspectaculos darEspectaculosIdioma(String idioma) throws Exception
	//	{
	//		ArrayList<Espectaculo> espectaculos;
	//		DAOTablaEspectaculos daoEspectaculos = new DAOTablaEspectaculos();
	//		try 
	//		{
	//			//////Transacción
	//			this.conn = darConexion();
	//			daoEspectaculos.setConn(conn);
	//			espectaculos = daoEspectaculos.darEspectaculosIdioma(idioma);
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

}
