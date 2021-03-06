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
import dao.DAOTablaCompania;
import dao.DAOTablaEspectaculos;
import dao.DAOTablaFuncion;
import dao.DAOTablaOrganizador;
import dao.DAOTablaSitio;
import dao.DAOTablaEspectaculos;
import dao.DAOTablaEspectaculos;
import dao.DAOTablaEspectaculos;
import dao.DAOTablaEspectaculos;
import vos.Abonamiento;
import vos.Boleta;
import vos.Categoria;
import vos.Cliente;
import vos.CompaniaTeatro;
import vos.ConsultaCompania;
import vos.ConsultaFuncion;
import vos.Espectaculo;
import vos.Festival;
import vos.FiltroConsultaCompraBoletas;
import vos.Funcion;
import vos.ListaBoletas;
import vos.ListaCategorias;
import vos.ListaClientes;
import vos.ListaCompanias;
import vos.ListaConsultaCompania;
import vos.ListaEspectaculos;
import vos.ListaNotasDebito;
import vos.ListaPreferencias;
import vos.ListaVideos;
import vos.Localidad;
import vos.NotaDebito;
import vos.NotaDebitoJM;
import vos.Organizador;
import vos.Preferencia;
import vos.Rentabilidad;
import vos.RespuestaConsultaCompraBoletas;
import vos.Resultado;
import vos.Sitio;
import vos.Video;

public class FestivAndesMaster {


	/**
	 * Atributo estÃƒÂ¡tico que contiene el path relativo del archivo que tiene los datos de la conexiÃƒÂ³n
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion1.properties";
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE_2 = "/conexion2.properties";
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE_3 = "/conexion3.properties";

	/**
	 * Atributo estÃƒÂ¡tico que contiene el path absoluto del archivo que tiene los datos de la conexiÃƒÂ³n
	 */
	private  String connectionDataPath1;
	private  String connectionDataPath2;
	private  String connectionDataPath3;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user1;
	private String user2;
	private String user3;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password1;
	private String password2;
	private String password3;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url1;
	private String url2;
	private String url3;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver1;
	private String driver2;
	private String driver3;

	/**
	 * ConexiÃƒÂ³n a la base de datos
	 */
	private Connection conn1;
	private Connection conn2;
	private Connection conn3;


	/**
	 * MÃƒÂ©todo constructor de la clase VideoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logia de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesMaster, se inicializa el path absoluto de el archivo de conexiÃƒÂ³n y se
	 * inicializa los atributos que se usan par la conexiÃƒÂ³n a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public FestivAndesMaster(String contextPathP) {
		connectionDataPath1 = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		connectionDataPath2 = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE_2;
		initConnectionData();
	}

	/*
	 * MÃƒÂ©todo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexiÃƒÂ³n a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath1);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url1 = prop.getProperty("url");
			this.user1 = prop.getProperty("usuario");
			this.password1 = prop.getProperty("clave");
			this.driver1 = prop.getProperty("driver");
			Class.forName(driver1);
			
			File arch2 = new File(this.connectionDataPath2);
			Properties prop2 = new Properties();
			FileInputStream in2 = new FileInputStream(arch2);
			prop2.load(in2);
			in2.close();
			this.url2 = prop.getProperty("url");
			this.user2 = prop.getProperty("usuario");
			this.password2 = prop.getProperty("clave");
			this.driver2 = prop.getProperty("driver");
			Class.forName(driver2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * MÃƒÂ©todo que  retorna la conexiÃƒÂ³n a la base de datos
	 * @return Connection - la conexiÃƒÂ³n a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexiÃƒÂ³n a la base de datos
	 */
	private Connection darConexion1() throws SQLException {
		System.out.println("Connecting to: " + url1 + " With user: " + user1);
		return DriverManager.getConnection(url1, user1, password1);
	}
	
	private Connection darConexion2() throws SQLException {
		System.out.println("Connecting to: " + url2 + " With user: " + user2);
		return DriverManager.getConnection(url2, user2, password2);
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
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoUsuario.setConn(conn2);
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
				if(this.conn2!=null)
					this.conn2.close();
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
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoCliente.setConn(conn2);
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
				if(this.conn2!=null)
					this.conn2.close();
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
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoClientes.setConn(conn2);
			daoClientes.addPreferenciaCliente(preferencia, idUsuario);
			conn2.commit();

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
				if(this.conn2!=null)
					this.conn2.close();
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
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoCliente.setConn(conn2);
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
				if(this.conn2!=null)
					this.conn2.close();
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
	//			//////TransacciÃƒÂ³n
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
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoFuncion.setConn(conn2);

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
				if(this.conn2!=null)
					this.conn2.close();
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
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoEspectaculo.setConn(conn2);
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
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return espectaculo;
	}

	public ConsultaCompania darInfoCompaniasId(int idE) throws Exception {
		CompaniaTeatro companias;
		DAOTablaCompania daoTablaCompanias = new DAOTablaCompania();
		ConsultaCompania entrega = null;
		try 
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoTablaCompanias.setConn(conn2);
			companias = daoTablaCompanias.darInfoCompaniasId(idE);
			ListaEspectaculos espectaculos = companias.getEspectaculos();
			ArrayList<String> asistenciaEspect = new ArrayList<>();
			String dineroTaquilla = "";
			ArrayList<String> porcentajeOcupacion = new ArrayList<>();
			double dinero = 0;
			for (Espectaculo espect : espectaculos.getEspectaculos()) {
				ArrayList<Funcion> funciones = espect.getFunciones();
				int contador = 0;
				for (int i = 0; i < funciones.size(); i++) 
				{
					dinero += funciones.get(i).getCosto();
					contador += funciones.get(i).getSillasOcupadas();
					Sitio actual = funciones.get(i).getSitio();
					List<Boleta> boletasFun = darBoletasFuncion(funciones.get(i).getId()).getBoletas();
					ArrayList<Boleta> numerador = new ArrayList<Boleta>();
					for (Boleta boleta : boletasFun) 
					{
						if(boleta.getEstado()==1 || boleta.getEstado()==2)
						{
							numerador.add(boleta);
						}
					}
					System.out.println("size "+numerador.size());
					System.out.println("size "+boletasFun.size());
					double resultadoPorcentaje = (numerador.size()/(double)boletasFun.size())*100;
					String porcentajeOcupacion1 = "Para la funciÃ³n con identificaciÃ³n " + funciones.get(i).getId() + " el porcentaje de ocupaciÃ³n en el sitio con identificaciÃ³n " + actual.getId() + " es de " + resultadoPorcentaje + "%";
					porcentajeOcupacion.add(porcentajeOcupacion1);
				}
				String asistenciaEspect1 = "Para el espectaculo " + espect.getNombre() + " la asistencia total fue de " + contador + "\n"; 
				asistenciaEspect.add(asistenciaEspect1);
			}
			dineroTaquilla = "El dinero total generado en la taquilla fue de " + dinero;
			entrega = new ConsultaCompania(companias.getNombre(), asistenciaEspect, dineroTaquilla, porcentajeOcupacion);
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
				daoTablaCompanias.cerrarRecursos();
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return entrega;
	}

	public ListaConsultaCompania darInfoCompanias() throws Exception {
		ArrayList<CompaniaTeatro> companias;
		DAOTablaCompania daoTablaCompanias = new DAOTablaCompania();
		ArrayList<ConsultaCompania> resultado = new ArrayList<ConsultaCompania>();
		try 
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoTablaCompanias.setConn(conn2);
			companias = daoTablaCompanias.darInfoCompanias();
			for (CompaniaTeatro companiaTeatro : companias) {

				ListaEspectaculos espectaculos = companiaTeatro.getEspectaculos();
				ArrayList<String> asistenciaEspect = new ArrayList<>();
				String dineroTaquilla = "";
				ArrayList<String> porcentajeOcupacion = new ArrayList<>();
				double dinero = 0;

				for (Espectaculo espect : espectaculos.getEspectaculos()) {
					ArrayList<Funcion> funciones = espect.getFunciones();
					int contador = 0;
					for (int i = 0; i < funciones.size(); i++) 
					{
						dinero += funciones.get(i).getCosto();
						contador += funciones.get(i).getSillasOcupadas();
						Sitio actual = funciones.get(i).getSitio();
						List<Boleta> boletasFun = darBoletasFuncion(funciones.get(i).getId()).getBoletas();
						ArrayList<Boleta> numerador = new ArrayList<Boleta>();
						for (Boleta boleta : boletasFun) 
						{
							if(boleta.getEstado()==1 || boleta.getEstado()==2)
							{
								numerador.add(boleta);
							}
						}
						System.out.println("size "+numerador.size());
						System.out.println("size "+boletasFun.size());
						double resultadoPorcentaje = (numerador.size()/(double)boletasFun.size())*100;
						String porcentajeOcupacion1 = "Para la funciÃ³n con identificaciÃ³n " + funciones.get(i).getId() + " el porcentaje de ocupaciÃ³n en el sitio con identificaciÃ³n " + actual.getId() + " es de " + resultadoPorcentaje + "%";
						porcentajeOcupacion.add(porcentajeOcupacion1);
					}
					String asistenciaEspect1 = "Para el espectaculo " + espect.getNombre() + " la asistencia total fue de " + contador + "\n"; 
					asistenciaEspect.add(asistenciaEspect1);
				}
				dineroTaquilla = "El dinero total generado en la taquilla fue de " + dinero;
				ConsultaCompania entrega = new ConsultaCompania(companiaTeatro.getNombre(), asistenciaEspect, dineroTaquilla, porcentajeOcupacion);
				resultado.add(entrega);
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
				daoTablaCompanias.cerrarRecursos();
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaConsultaCompania(resultado);
	}

	public ListaCategorias darCategoriasPorEspectaculoId(int idE) throws Exception {
		ArrayList<Categoria> categorias;
		DAOTablaCategorias daoTablaCategoria = new DAOTablaCategorias();
		try 
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoTablaCategoria.setConn(conn2);
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
				if(this.conn2!=null)
					this.conn2.close();
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
		DAOTablaFuncion daoTablaFuncion = new DAOTablaFuncion();
		DAOTablaSitio daoTablaSitio = new DAOTablaSitio();
		try 
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			daoTablaCliente.setConn(conn2);
			daoTablaFuncion.setConn(conn2);
			daoTablaSitio.setConn(conn2);
			conn2.setSavepoint();
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
			Funcion f = daoTablaFuncion.darFuncion(funcionId);
			System.out.println(mismaFuncion);
			System.out.println("LLEGUE 3333333333333333333");

			boolean numerada = false;
			Localidad loc = daoTablaSitio.darLocalidad(localidadID);
			if(loc.getNumerada()==1)
				numerada = true;
			System.out.println(numerada);
			System.out.println("LLEGUE 4444444444444444444444444");

			Cliente cliente = daoTablaCliente.darCliente(id);
			arregloBoletas = new ArrayList<Boleta>();

			if(numerada&&cliente!=null){
				System.out.println("LLEGUE 555555555555555555");
				boolean seguidas = true;
				for(int i = 0; i < boletas.getBoletas().size()-1 && seguidas && numerada ; i++)
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
			conn2.commit();
			conn2.setAutoCommit(true);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn2.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn2.rollback();
			throw e;
		} finally {
			try {
				daoTablaCliente.cerrarRecursos();
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaBoletas(arregloBoletas);
	}

	public ListaBoletas registrarCompraAbonamientoOriginal(ListaBoletas boletas, int id) throws Exception {
		ArrayList<Boleta> arregloBoletas;
		DAOTablaCliente daoTablaCliente = new DAOTablaCliente();
		DAOTablaSitio daoTablaSitio = new DAOTablaSitio();
		DAOTablaFuncion daoTablaFuncion = new DAOTablaFuncion();
		try 
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			daoTablaCliente.setConn(conn2);
			daoTablaFuncion.setConn(conn2);
			daoTablaSitio.setConn(conn2);
			conn2.setSavepoint();
			boolean antesDeTresSem = true;

			boolean todasDisponibles = true;
			for (Boleta boleta : boletas.getBoletas()) {
				Localidad loc = daoTablaSitio.darLocalidad(boleta.getLocalidad().getId());
				if(loc.getNumerada()==1)
					todasDisponibles = todasDisponibles&&daoTablaCliente.boletaNumeradaDisponible(boleta);
				else if(loc.getNumerada()==0)
				{
					System.out.println("AQUI");
					int ocupadas = daoTablaCliente.ocupadasBoletaNoNumeradas(boleta);
					int disponibles = loc.getCapacidad() - ocupadas;
					todasDisponibles = todasDisponibles && (disponibles>0);
				}
				System.out.println(todasDisponibles);
			}
			Cliente c = daoTablaCliente.darCliente(id);
			arregloBoletas = new ArrayList<>();
			if(antesDeTresSem&&todasDisponibles)
			{
				System.out.println("EL FAROLEO");
				for (Boleta boleta : boletas.getBoletas()) {
					Localidad loc = daoTablaSitio.darLocalidad(boleta.getLocalidad().getId());
					if(loc.getNumerada()==1){
						System.out.println("NENENE");
						Boleta b = daoTablaCliente.comprarBoletaNumerada(boleta, id,true);
						b.setLocalidad(loc);
						b.setFuncion(daoTablaFuncion.darFuncion(boleta.getFuncion().getId()));
						b.setCliente(c);
						arregloBoletas.add(b);
					}
					else if(loc.getNumerada()==0)
					{
						int ocupadas = daoTablaCliente.ocupadasBoletaNoNumeradas(boleta);
						Boleta b = daoTablaCliente.comprarBoletasNoNumeradas(boleta, id, 1, ocupadas,true).get(0);
						b.setLocalidad(loc);
						b.setFuncion(daoTablaFuncion.darFuncion(boleta.getFuncion().getId()));
						b.setCliente(c);
						arregloBoletas.add(b);
						System.out.println("DASDASDA");
					}
				}
			}
			System.out.println("ALA");
			conn2.commit();
			conn2.setAutoCommit(true);
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
				if(this.conn2!=null)
					this.conn2.close();
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
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoTablaSitio.setConn(conn2);


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
				if(this.conn2!=null)
					this.conn2.close();
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
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoTablaFuncion.setConn(conn2);

			funcion = daoTablaFuncion.darFuncion(id);
			funcion.setEspectaculo(darEspectaculo(funcion.getEspectaculo().getId()));

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
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return funcion;
	}

	public Espectaculo darEspectaculo(int id) throws Exception
	{
		Espectaculo espectaculo;
		DAOTablaEspectaculos daoTablaEspectaculo = new DAOTablaEspectaculos();
		try 
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoTablaEspectaculo.setConn(conn2);

			espectaculo = daoTablaEspectaculo.darEspectaculo(id);

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
				daoTablaEspectaculo.cerrarRecursos();
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return espectaculo;
	}


	public Cliente darCliente(int id) throws Exception
	{
		Cliente cliente;
		DAOTablaCliente daoTablaCliente = new DAOTablaCliente();
		try 
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoTablaCliente.setConn(conn2);
			cliente = daoTablaCliente.darCliente(id);
			conn2.commit();
			conn2.setAutoCommit(true);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn2.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn2.rollback();
			throw e;
		} finally {
			try {
				daoTablaCliente.cerrarRecursos();
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cliente;
	}

	public Organizador darOrganizador(int id) throws Exception
	{
		Organizador organizador;
		DAOTablaOrganizador daoTablaOrganizador = new DAOTablaOrganizador();
		try 
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoTablaOrganizador.setConn(conn2);

			organizador = daoTablaOrganizador.darOrganizador(id);

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
				daoTablaOrganizador.cerrarRecursos();
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return organizador;
	}

	public ListaBoletas darBoletasCliente(int id) throws Exception
	{
		ListaBoletas listaBoletas;
		DAOTablaCliente daoTablaCliente = new DAOTablaCliente();
		try 
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoTablaCliente.setConn(conn2);

			listaBoletas = daoTablaCliente.darBoletasCliente(id);

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
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return listaBoletas;
	}

	public ListaBoletas darBoletasFuncion(int idF) throws Exception
	{
		ListaBoletas listaBoletas;
		DAOTablaFuncion daoTablaOrganizadores = new DAOTablaFuncion();
		try 
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoTablaOrganizadores.setConn(conn2);

			listaBoletas = daoTablaOrganizadores.darBoletasFuncion(idF);

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
				daoTablaOrganizadores.cerrarRecursos();
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return listaBoletas;
	}




	public ArrayList<ConsultaFuncion> consultarAsistenciaAlFestival(int idC) throws Exception
	{
		ArrayList<ConsultaFuncion> resultado =new ArrayList<>();
		DAOTablaCliente daoTablaCliente = new DAOTablaCliente();
		try 
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoTablaCliente.setConn(conn2);

			ListaBoletas boletas  = darBoletasCliente(idC);

			for (Boleta boleta: boletas.getBoletas()) {
				Funcion actual = darFuncion(boleta.getFuncion().getId());
				Espectaculo esp = darEspectaculo(actual.getEspectaculo().getId());
				String estado = "";
				String devuelta = "La boleta no se devolvio";
				if(actual.isRealizada()==1)
					estado+="La funcion ya se realizo. \n";
				else
					estado+="La funcion esta prevista por realizarse.\n";
				if(boleta.getEstado() == Boleta.DEVUELTA)
					devuelta="La boleta se devolvio";
				resultado.add(new ConsultaFuncion(actual.getId(),esp.getNombre() , actual.getFechaHora().toString(), estado, devuelta));
			}

			System.out.println(resultado);


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
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resultado;
	}



	public NotaDebitoJM devolverBoleta(Boleta pBoleta, int id, boolean cerrar) throws Exception
	{
		System.out.println(1);
		DAOTablaCliente daoCliente = new DAOTablaCliente();
		Boleta devolver = null;
		Boleta nueva = null;
		NotaDebitoJM resultado = null;
		try 
		{
			this.conn2 = darConexion2();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			daoCliente.setConn(conn2);
			conn2.setSavepoint();
			//System.out.println(pBoleta.getFuncion().getId() + " " + pBoleta.getLocalidad().getId() + " " + pBoleta.getUbicacion());
			devolver = daoCliente.darBoleta(pBoleta);
			Cliente cliente = daoCliente.darCliente(id);
			System.out.println(cliente);
			if(devolver!=null && cliente!=null && (devolver.getEstado()==1 || devolver.getEstado()==2) )
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
				nueva = daoCliente.darBoleta(pBoleta);
				resultado = new NotaDebitoJM(cliente, nueva.getCosto(), nueva.getFuncion(), nueva);
			}
			conn2.commit();
			conn2.setAutoCommit(true);			
		}
		catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn2.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn2.rollback();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn2!=null && cerrar)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resultado;
	}

	public ListaNotasDebito devolverAbonamiento(int id) throws Exception
	{
		System.out.println(1);
		DAOTablaCliente daoCliente = new DAOTablaCliente();
		ArrayList<Boleta> resultado = new ArrayList<Boleta>();
		List<NotaDebitoJM> notas = new ArrayList<NotaDebitoJM>();
		try
		{
			this.conn2 = darConexion2();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			daoCliente.setConn(conn2);
			conn2.setSavepoint();
			ListaBoletas revisar = daoCliente.darBoletasCliente(id);
			List<Boleta> aBuscar = revisar.getBoletas();
			Cliente cliente = daoCliente.darCliente(id);
			if(cliente!=null)
			{
				System.out.println("entra");
				Festival fest = cliente.getFestival();
				Date fechafest = fest.getFechaInicio();
				System.out.println(fechafest.toString());
				Calendar fechaB = Calendar.getInstance();
				fechaB.setTime(fechafest);
				fechaB.add(Calendar.DATE, -21);
				Date compararFecha = fechaB.getTime();
				System.out.println(fechaB.toString());
				Date actual = new Date();
				if(compararFecha.after(actual))
				{
					for (Boleta boleta : aBuscar) 
					{
						if(boleta.getEstado()==2)
						{
							NotaDebitoJM nota =devolverBoleta(boleta, cliente.getId(), false);
							notas.add(nota);
						}
					}
				}
			}
			conn2.commit();
			conn2.setAutoCommit(true);	
		}

		catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn2.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn2.rollback();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaNotasDebito(notas);
	}

	public ListaNotasDebito cancelarFuncion(int idF, boolean cerrar) throws Exception
	{
		DAOTablaCliente daoCliente = new DAOTablaCliente();
		DAOTablaFuncion daoFuncion = new DAOTablaFuncion();
		List<NotaDebitoJM> resultado = new ArrayList<NotaDebitoJM>();
		try 
		{
			this.conn2 = darConexion2();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			daoCliente.setConn(conn2);
			daoFuncion.setConn(conn2);
			conn2.setSavepoint();
			Funcion fun =daoFuncion.darFuncion(idF);
			if(fun!=null)
			{
				ListaBoletas boletas = daoFuncion.darBoletasFuncion(idF);
				if(fun.isRealizada()==0)
				{
					for (Boleta boleta : boletas.getBoletas()) {
						if(boleta.getEstado()==1 || boleta.getEstado()==2)
						{
							daoCliente.devolverBoleta(boleta);
							resultado.add(new NotaDebitoJM(boleta.getCliente(), boleta.getCosto(), boleta.getFuncion(), boleta));							
						}
					}
				}
			}
			conn2.commit();
			conn2.setAutoCommit(true);	
		}
		catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn2.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn2.rollback();
			throw e;
		} finally {
			try {
				daoFuncion.cerrarRecursos();
				daoCliente.cerrarRecursos();
				if(this.conn2!=null && cerrar)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return new ListaNotasDebito(resultado);
	}

	public boolean esGerenteGeneral(int id) throws Exception
	{
		boolean esGerenteGeneral = false;
		DAOTablaOrganizador daoTablaOrganizador = new DAOTablaOrganizador();
		try 
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoTablaOrganizador.setConn(conn2);

			Organizador org = daoTablaOrganizador.darOrganizador(id);
			esGerenteGeneral = org.getRol().equals("Gerente general");

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
				daoTablaOrganizador.cerrarRecursos();
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return esGerenteGeneral;
	}

	public ArrayList<RespuestaConsultaCompraBoletas> consultarCompraBoletas(FiltroConsultaCompraBoletas filtro) throws Exception{
		ArrayList<RespuestaConsultaCompraBoletas> respuesta = new ArrayList<>();
		DAOTablaOrganizador daoTablaOrganizador = new DAOTablaOrganizador();
		try
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoTablaOrganizador.setConn(conn2);		
			respuesta = daoTablaOrganizador.consultarCompraBoletas(filtro);

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
				daoTablaOrganizador.cerrarRecursos();
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuesta;
	}

	public ListaClientes consultarBuenosClientes(int n) throws Exception{
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		DAOTablaOrganizador daoTablaOrganizador = new DAOTablaOrganizador();
		try
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoTablaOrganizador.setConn(conn2);		
			clientes = daoTablaOrganizador.consultarBuenosClientes(n);

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
				daoTablaOrganizador.cerrarRecursos();
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaClientes(clientes);
	}


	public ArrayList<Cliente> asistUsuariosFest(int idComp, Date fechaIni, Date fechaFinal,String pCriterio, String pAgrupamiento) throws Exception
	{
		DAOTablaCompania daoCompania = new DAOTablaCompania();
		ArrayList<Cliente> clientesSinCriterio = new ArrayList<>();
		try
		{

			this.conn2 = darConexion2();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			daoCompania.setConn(conn2);
			String fechaInicial = fechaIni.getDate() + "/" + (fechaIni.getMonth()+1) + "/" + (fechaIni.getYear()+1900);
			String fechaFin = fechaFinal.getDate() + "/" + (fechaFinal.getMonth()+1) + "/" + (fechaFinal.getYear()+1900);
			String criterio = "' '";
			if(pCriterio.equalsIgnoreCase("nombre")||pCriterio.equalsIgnoreCase("rol")||pCriterio.equalsIgnoreCase("id")||pCriterio.equalsIgnoreCase("mail"))
			{
				criterio = pCriterio;
			}
			String agrupamiento = "";
			if(pAgrupamiento.contains("-"))
			{
				String[] ids = pAgrupamiento.split("-");
				agrupamiento = "AND ID >" + ids[0] + " AND ID < " + ids[1];
			}
			else
				agrupamiento= "WHERE 1=1";

			clientesSinCriterio = daoCompania.asistUsuariosFest(idComp, fechaInicial, fechaFin, criterio, agrupamiento);

			conn2.commit();
			conn2.setAutoCommit(true);
		} 
		catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn2.rollback();
			throw e;
		} finally {
			try {

				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return clientesSinCriterio;
	}

	public ArrayList<Cliente> asistNoUsuariosFest(int idComp, Date fechaIni, Date fechaFinal, String pCriterio, String pAgrupamiento) throws Exception
	{
		DAOTablaCompania daoCompania = new DAOTablaCompania();
		ArrayList<Cliente> clientesSinCriterio = new ArrayList<>();
		try
		{

			this.conn2 = darConexion2();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			daoCompania.setConn(conn2);
			String fechaInicial = fechaIni.getDate() + "/" + (fechaIni.getMonth()+1) + "/" + (fechaIni.getYear()+1900);
			String fechaFin = fechaFinal.getDate() + "/" + (fechaFinal.getMonth()+1) + "/" + (fechaFinal.getYear()+1900);
			String criterio = "' '";
			if(pCriterio.equalsIgnoreCase("nombre")||pCriterio.equalsIgnoreCase("rol")||pCriterio.equalsIgnoreCase("id")||pCriterio.equalsIgnoreCase("mail"))
			{
				criterio = pCriterio;
			}
			String agrupamiento = "";
			if(pAgrupamiento.contains("-"))
			{
				String[] ids = pAgrupamiento.split("-");
				System.out.println(ids[0]);
				System.out.println(ids[1]);
				agrupamiento = "AND ID >" + ids[0] + " AND ID < " + ids[1];
			}
			else
				agrupamiento= "WHERE 1=1";
			clientesSinCriterio = daoCompania.asistNoUsuariosFest(idComp, fechaInicial, fechaFin, criterio, agrupamiento);

			conn2.commit();
			conn2.setAutoCommit(true);
		} 
		catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn2.rollback();
			throw e;
		} finally {
			try {

				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return clientesSinCriterio;
	}

	public ArrayList<Funcion> darFunciones(int idComp, Date fechaIni, Date fechaFinal, String pAgrupamiento, String idioma, int traduccion) throws Exception
	{
		ArrayList<Funcion> listaFunciones = new ArrayList<>();
		DAOTablaFuncion daoFuncion = new DAOTablaFuncion();
		try{
			this.conn2 = darConexion2();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			daoFuncion.setConn(conn2);
			String sqlFecha = "";
			String sqlComp = "";
			String sqlIdi = "";
			String sqlTrad = "";
			String agrupamiento = "";
			if(fechaIni!=null && fechaFinal!=null)
			{
				String fechaInicial = fechaIni.getDate() + "/" + (fechaIni.getMonth()+1) + "/" + (fechaIni.getYear()+1900);
				String fechaFin = fechaFinal.getDate() + "/" + (fechaFinal.getMonth()+1) + "/" + (fechaFinal.getYear()+1900);
				sqlFecha = "AND FECHA_HORA BETWEEN '" + fechaInicial + "' AND '" + fechaFin + "' "; 
			}
			if(idComp!=0)
			{
				sqlComp = " AND ID_COMPANIA = " + idComp + " ";
			}
			if(!idioma.isEmpty())
			{
				sqlIdi = " AND IDIOMA = '" + idioma + "' ";
			}
			if(traduccion!=0)
			{
				sqlTrad = " AND TRADUCCION_SUBTITULOS = " + traduccion + " "; 
			}
			if(pAgrupamiento.contains("-"))
			{
				String[] ids = pAgrupamiento.split("-");
				agrupamiento = "AND ID >" + ids[0] + " AND ID < " + ids[1];
			}
			listaFunciones = daoFuncion.darFunciones(sqlFecha, sqlComp, sqlIdi, sqlTrad);

		}
		catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn2.rollback();
			throw e;
		} finally {
			try {

				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return listaFunciones;
	}

	public List<NotaDebito> retirarCompania(int idC) throws Exception
	{
		DAOTablaCliente daoCliente = new DAOTablaCliente();
		DAOTablaFuncion daoFuncion = new DAOTablaFuncion();
		DAOTablaCompania daoTablaCompania = new DAOTablaCompania();
		List<NotaDebito> listaFin = new ArrayList<NotaDebito>();
		try 
		{
			this.conn2 = darConexion2();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			daoFuncion.setConn(conn2);
			daoTablaCompania.setConn(conn2);
			daoCliente.setConn(conn2);
			conn2.setSavepoint();

			daoTablaCompania.cancelarCompania(idC);
			listaFin = daoFuncion.cancelarFuncion(idC);
			conn2.commit();
			conn2.setAutoCommit(true);	
		}
		catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn2.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn2.rollback();
			throw e;
		} finally {
			try {
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return listaFin;
	}

	public List<Rentabilidad> darRentabilidadCompania( Rentabilidad rent, Long idCompania) throws SQLException
	{
		ArrayList<Rentabilidad> rentabilidad;
		DAOTablaCompania daoCompania = new DAOTablaCompania();
		try 
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoCompania.setConn(conn2);
			rentabilidad = daoCompania.darRentabilidadCompania(rent,idCompania);
			conn2.commit();

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
				daoCompania.cerrarRecursos();
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rentabilidad;

	}

	public List<Rentabilidad> darRentabilidad( Rentabilidad rent) throws SQLException
	{
		ArrayList<Rentabilidad> rentabilidad;
		DAOTablaCompania daoFestival = new DAOTablaCompania();
		try 
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			daoFestival.setConn(conn2);
			rentabilidad = daoFestival.darRentabilidad(rent);
			conn2.commit();

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
				daoFestival.cerrarRecursos();
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rentabilidad;
	}

	public ListaBoletas registrarCompraAbonamiento(Abonamiento abonamiento, int id) throws Exception {
		ArrayList<Boleta> arregloBoletas;
		DAOTablaCliente daoTablaCliente = new DAOTablaCliente();
		DAOTablaSitio daoTablaSitio = new DAOTablaSitio();
		DAOTablaFuncion daoTablaFuncion = new DAOTablaFuncion();
		try 
		{
			//////TransacciÃƒÂ³n
			this.conn2 = darConexion2();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			daoTablaCliente.setConn(conn2);
			daoTablaFuncion.setConn(conn2);
			daoTablaSitio.setConn(conn2);
			conn2.setSavepoint();
			boolean antesDeTresSem = true;

			boolean todasDisponibles = true;
			for(int i = 0; i < abonamiento.getIdsFunciones().size(); i++)
				todasDisponibles = todasDisponibles && daoTablaFuncion.boletaDisponible(abonamiento.getIdsFunciones().get(i), abonamiento.getIdsLocalidades().get(i));

			Cliente c = daoTablaCliente.darCliente(id);
			arregloBoletas = new ArrayList<>();
			if(antesDeTresSem&&todasDisponibles)
			{
				for(int i = 0; i < abonamiento.getIdsFunciones().size(); i++){
					Boleta aComprar = daoTablaFuncion.darBoletaDisponible(abonamiento.getIdsFunciones().get(i), abonamiento.getIdsLocalidades().get(i));
					Boleta b = daoTablaCliente.comprarBoletaNumerada(aComprar, id,true);
					Localidad loc = daoTablaSitio.darLocalidad(aComprar.getLocalidad().getId());
					b.setLocalidad(loc);
					b.setFuncion(daoTablaFuncion.darFuncion(aComprar.getFuncion().getId()));
					b.setCliente(c);
					arregloBoletas.add(b);
				}
			}
			conn2.commit();
			conn2.setAutoCommit(true);
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
				if(this.conn2!=null)
					this.conn2.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaBoletas(arregloBoletas);
	}


}
