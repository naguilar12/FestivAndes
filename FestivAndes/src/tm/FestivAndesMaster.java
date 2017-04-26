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
import dao.DAOTablaOrganizador;
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
import vos.ConsultaCompania;
import vos.ConsultaFuncion;
import vos.Espectaculo;
import vos.Festival;
import vos.Funcion;
import vos.ListaBoletas;
import vos.ListaCategorias;
import vos.ListaCompa駃as;
import vos.ListaConsultaCompania;
import vos.ListaEspectaculos;
import vos.ListaNotasDebito;
import vos.ListaPreferencias;
import vos.ListaVideos;
import vos.Localidad;
import vos.NotaDebito;
import vos.Organizador;
import vos.Preferencia;
import vos.Resultado;
import vos.Sitio;
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

	public ConsultaCompania darInfoCompaniasId(int idE) throws Exception {
		Compa駃aTeatro compa駃as;
		DAOTablaCompa駃a daoTablaCompa駃as = new DAOTablaCompa駃a();
		ConsultaCompania entrega = null;
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoTablaCompa駃as.setConn(conn);
			compa駃as = daoTablaCompa駃as.darInfoCompaniasId(idE);
			ListaEspectaculos espectaculos = compa駃as.getEspectaculos();
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
					String porcentajeOcupacion1 = "Para la funci髇 con identificaci髇 " + funciones.get(i).getId() + " el porcentaje de ocupaci髇 en el sitio con identificaci髇 " + actual.getId() + " es de " + resultadoPorcentaje + "%";
					porcentajeOcupacion.add(porcentajeOcupacion1);
				}
				String asistenciaEspect1 = "Para el espectaculo " + espect.getNombre() + " la asistencia total fue de " + contador + "\n"; 
				asistenciaEspect.add(asistenciaEspect1);
			}
			dineroTaquilla = "El dinero total generado en la taquilla fue de " + dinero;
			entrega = new ConsultaCompania(compa駃as.getNombre(), asistenciaEspect, dineroTaquilla, porcentajeOcupacion);
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
		return entrega;
	}

	public ListaConsultaCompania darInfoCompanias() throws Exception {
		ArrayList<Compa駃aTeatro> companias;
		DAOTablaCompa駃a daoTablaCompa駃as = new DAOTablaCompa駃a();
		ArrayList<ConsultaCompania> resultado = new ArrayList<ConsultaCompania>();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoTablaCompa駃as.setConn(conn);
			companias = daoTablaCompa駃as.darInfoCompanias();
			for (Compa駃aTeatro compa駃aTeatro : companias) {
				
				ListaEspectaculos espectaculos = compa駃aTeatro.getEspectaculos();
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
						String porcentajeOcupacion1 = "Para la funci髇 con identificaci髇 " + funciones.get(i).getId() + " el porcentaje de ocupaci髇 en el sitio con identificaci髇 " + actual.getId() + " es de " + resultadoPorcentaje + "%";
						porcentajeOcupacion.add(porcentajeOcupacion1);
					}
					String asistenciaEspect1 = "Para el espectaculo " + espect.getNombre() + " la asistencia total fue de " + contador + "\n"; 
					asistenciaEspect.add(asistenciaEspect1);
				}
				dineroTaquilla = "El dinero total generado en la taquilla fue de " + dinero;
				ConsultaCompania entrega = new ConsultaCompania(compa駃aTeatro.getNombre(), asistenciaEspect, dineroTaquilla, porcentajeOcupacion);
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
				daoTablaCompa駃as.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
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
		DAOTablaFuncion daoTablaFuncion = new DAOTablaFuncion();
		DAOTablaSitio daoTablaSitio = new DAOTablaSitio();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			daoTablaCliente.setConn(conn);
			daoTablaFuncion.setConn(conn);
			daoTablaSitio.setConn(conn);
			conn.setSavepoint();
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
			conn.commit();
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
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
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			daoTablaCliente.setConn(conn);
			conn.setSavepoint();
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
			conn.commit();
			conn.setAutoCommit(true);
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

	public Espectaculo darEspectaculo(int id) throws Exception
	{
		Espectaculo espectaculo;
		DAOTablaEspectaculos daoTablaEspectaculo = new DAOTablaEspectaculos();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoTablaEspectaculo.setConn(conn);

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


	public Cliente darCliente(int id) throws Exception
	{
		Cliente cliente;
		DAOTablaCliente daoTablaCliente = new DAOTablaCliente();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoTablaCliente.setConn(conn);
			cliente = daoTablaCliente.darCliente(id);
			conn.commit();
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
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

	public Organizador darOrganizador(int id) throws Exception
	{
		Organizador organizador;
		DAOTablaOrganizador daoTablaOrganizador = new DAOTablaOrganizador();
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			daoTablaOrganizador.setConn(conn);

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
				if(this.conn!=null)
					this.conn.close();
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
			//////Transacci贸n
			this.conn = darConexion();
			daoTablaCliente.setConn(conn);

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
				if(this.conn!=null)
					this.conn.close();
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
			//////Transacci贸n
			this.conn = darConexion();
			daoTablaOrganizadores.setConn(conn);

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
				if(this.conn!=null)
					this.conn.close();
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
			//////Transacci贸n
			this.conn = darConexion();
			daoTablaCliente.setConn(conn);

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
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resultado;
	}



	public NotaDebito devolverBoleta(Boleta pBoleta, int id) throws Exception
	{
		System.out.println(1);
		DAOTablaCliente daoCliente = new DAOTablaCliente();
		Boleta devolver = null;
		Boleta nueva = null;
		NotaDebito resultado = null;
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			daoCliente.setConn(conn);
			conn.setSavepoint();
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
				resultado = new NotaDebito(cliente, nueva.getCosto(), nueva.getFuncion(), nueva);
			}
			conn.commit();
			conn.setAutoCommit(true);			
		}
		catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
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
		return resultado;
	}

	public ListaNotasDebito devolverAbonamiento(int id) throws Exception
	{
		System.out.println(1);
		DAOTablaCliente daoCliente = new DAOTablaCliente();
		ArrayList<Boleta> resultado = new ArrayList<Boleta>();
		List<NotaDebito> notas = new ArrayList<NotaDebito>();
		try
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			daoCliente.setConn(conn);
			conn.setSavepoint();
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
							daoCliente.devolverBoleta(boleta);
							resultado.add(boleta);
						}
					}
				}
			}
			for (Boleta result : resultado) 
			{
				NotaDebito nuevaNota = new NotaDebito(cliente, result.getCosto(), result.getFuncion(), result);
				notas.add(nuevaNota);
			}
			conn.commit();
			conn.setAutoCommit(true);	
		}
		
		catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
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
		return new ListaNotasDebito(notas);
	}

	public ListaNotasDebito cancelarFuncion(int idF) throws Exception
	{
		System.out.println("cancelar");
		DAOTablaCliente daoCliente = new DAOTablaCliente();
		DAOTablaFuncion daoFuncion = new DAOTablaFuncion();
		List<NotaDebito> resultado = new ArrayList<NotaDebito>();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			daoCliente.setConn(conn);
			daoFuncion.setConn(conn);
			conn.setSavepoint();
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
							resultado.add(new NotaDebito(boleta.getCliente(), boleta.getCosto(), boleta.getFuncion(), boleta));							
						}
					}
				}
			}
			conn.commit();
			conn.setAutoCommit(true);	
		}
		catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
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

		return new ListaNotasDebito(resultado);

	}


}
