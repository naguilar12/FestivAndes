package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import vos.Cliente;
import vos.CompañiaTeatro;
import vos.Espectaculo;
import vos.Festival;
import vos.Funcion;
import vos.ListaCategorias;
import vos.ListaCompañias;
import vos.ListaEspectaculos;
import vos.ListaFestivales;
import vos.ListaRequerimientos;
import vos.Preferencia;
import vos.Rentabilidad;
import vos.Representante;
import vos.Sitio;

public class DAOTablaCompañia {

	/**
	 * Arraylits de recursos que se usan para la ejecuciÃƒÂ³n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexiÃƒÂ³n a la base de datos
	 */
	private Connection conn;

	/**
	 * MÃƒÂ©todo constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaCompañia() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * MÃƒÂ©todo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * MÃƒÂ©todo que inicializa la connection del DAO a la base de datos con la conexiÃƒÂ³n que entra como parÃƒÂ¡metro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}

	public CompañiaTeatro darInfoCompaniasId(int idC) throws SQLException, Exception {

		CompañiaTeatro compania = null;

		String sql = "SELECT * FROM COMPANIA_TEATRO";
		sql += " WHERE ID = " + idC;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {

			int idComp = rs.getInt("ID");
			String nombreCom = rs.getString("NOMBRE");
			Date fechaLLeg = rs.getDate("FECHA_LLEGADA");
			Date fechaSali = rs.getDate("FECHA_SALIDA");
			ArrayList<Espectaculo> listaEspec = new ArrayList<Espectaculo>();

			String sql1 = "SELECT * FROM COMPANIA_ESPECTACULO";
			sql1 += " WHERE ID_COMPANIA = " + idC;
			PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
			recursos.add(prepStmt1);
			ResultSet rs1 = prepStmt1.executeQuery();
			while(rs1.next())
			{
				int id = rs1.getInt("ID_ESPECTACULO");

				String sql2 = "SELECT * FROM ESPECTACULO";
				sql2 += " WHERE ID = " + id;
				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				ResultSet rs2 = prepStmt2.executeQuery();

				if(rs2.next())
				{
					int idEsp = rs2.getInt("ID");
					String nombre = rs2.getString("NOMBRE");
					double duracion = rs2.getDouble("DURACION");
					int intermedio = rs2.getInt("INTERMEDIO");
					String idioma = rs2.getString("IDIOMA");
					String clasificacion = rs2.getString("CLASIFICACION");
					double costoRealizacion = rs2.getDouble("COSTO_REALIZACION");
					int publicoActivo = rs2.getInt("PUBLICO_ACTIVO");
					int tradSubt = rs2.getInt("TRADUCCION_SUBTITULOS");
					int tradAud = rs2.getInt("TRADUCCION_AUDIFONOS");
					String descripcion = rs2.getString("DESCRIPCION");
					String pubObj = rs2.getString("PUBLICO_OBJETIVO");
					ArrayList<Funcion> funciones = new ArrayList<Funcion>();
					
					String sql3 = "SELECT * FROM FUNCION";
					sql3 += " WHERE ID_ESPECTACULO = " + idEsp;
					PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
					recursos.add(prepStmt3);
					ResultSet rs3 = prepStmt3.executeQuery();
					
					while(rs3.next())
					{
						
						int idFun = rs3.getInt("ID");
						int idSit = rs3.getInt("ID_SITIO");
						double costo = rs3.getDouble("COSTO");
						int sillasOcupadas = rs3.getInt("SILLAS_OCUPADAS");
						Timestamp fechaHora = rs3.getTimestamp("FECHA_HORA");
						int yaSeRealizo = rs3.getInt("YA_SE_REALIZO");
						
						String sql4 = "SELECT * FROM SITIO";
						sql4 += " WHERE ID = " + idSit;
						PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
						recursos.add(prepStmt4);
						ResultSet rs4 = prepStmt4.executeQuery();
						Sitio nuevosit = null;
						if(rs4.next())
						{
							String direccion = rs4.getString("DIRECCION");
							int capacidad = rs4.getInt("CAPACIDAD");
							Timestamp inicioHorario = rs4.getTimestamp("INICIO_HORARIO");
							Timestamp finHorario = rs4.getTimestamp("FIN_HORARIO");
							
							nuevosit = new Sitio(idSit, direccion, 0, capacidad, 0, inicioHorario, finHorario, 0, 0);
						}
						Funcion nuevaFun = new Funcion(idFun, fechaHora, costo, sillasOcupadas, yaSeRealizo, null, nuevosit);
						funciones.add(nuevaFun);
						
						
					}
					Espectaculo nuevoEspect = new Espectaculo(idEsp, nombre, duracion, intermedio, idioma, clasificacion, costoRealizacion, publicoActivo, tradSubt, tradAud, descripcion, pubObj, null, null, null, funciones);
					listaEspec.add(nuevoEspect);
				}

			}
			
			compania = new CompañiaTeatro(idComp, nombreCom, fechaLLeg, fechaSali, null , null, new ListaEspectaculos(listaEspec));
		}
		return compania;
	}

	public ArrayList<CompañiaTeatro> darInfoCompanias() throws SQLException, Exception
	{
		ArrayList<CompañiaTeatro> companiasLista = new ArrayList<CompañiaTeatro>();
		String sql = "SELECT * FROM COMPANIA_TEATRO";
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next())
		{
			System.out.println("Entroooooo");
			int idComp = rs.getInt("ID");
			String nombreCom = rs.getString("NOMBRE");
			Date fechaLLeg = rs.getDate("FECHA_LLEGADA");
			Date fechaSali = rs.getDate("FECHA_SALIDA");
			ArrayList<Espectaculo> listaEspec = new ArrayList<Espectaculo>();

			String sql1 = "SELECT * FROM COMPANIA_ESPECTACULO";
			sql1 += " WHERE ID_COMPANIA = " + idComp;
			System.out.println(sql1);
			PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
			recursos.add(prepStmt1);
			ResultSet rs1 = prepStmt1.executeQuery();
			while(rs1.next())
			{
				int id = rs1.getInt("ID_ESPECTACULO");

				String sql2 = "SELECT * FROM ESPECTACULO";
				sql2 += " WHERE ID = " + id;
				System.out.println(sql2);
				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				ResultSet rs2 = prepStmt2.executeQuery();

				if(rs2.next())
				{
					int idEsp = rs2.getInt("ID");
					String nombre = rs2.getString("NOMBRE");
					double duracion = rs2.getDouble("DURACION");
					int intermedio = rs2.getInt("INTERMEDIO");
					String idioma = rs2.getString("IDIOMA");
					String clasificacion = rs2.getString("CLASIFICACION");
					double costoRealizacion = rs2.getDouble("COSTO_REALIZACION");
					int publicoActivo = rs2.getInt("PUBLICO_ACTIVO");
					int tradSubt = rs2.getInt("TRADUCCION_SUBTITULOS");
					int tradAud = rs2.getInt("TRADUCCION_AUDIFONOS");
					String descripcion = rs2.getString("DESCRIPCION");
					String pubObj = rs2.getString("PUBLICO_OBJETIVO");
					ArrayList<Funcion> funciones = new ArrayList<Funcion>();
					
					String sql3 = "SELECT * FROM FUNCION";
					sql3 += " WHERE ID_ESPECTACULO = " + idEsp;
					System.out.println();
					PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
					recursos.add(prepStmt3);
					ResultSet rs3 = prepStmt3.executeQuery();
					
					while(rs3.next())
					{
						int idFun = rs3.getInt("ID");
						int idSit = rs3.getInt("ID_SITIO");
						double costo = rs3.getDouble("COSTO");
						int sillasOcupadas = rs3.getInt("SILLAS_OCUPADAS");
						Timestamp fechaHora = rs3.getTimestamp("FECHA_HORA");
						int yaSeRealizo = rs3.getInt("YA_SE_REALIZO");
						String sql4 = "SELECT * FROM SITIO";
						sql4 += " WHERE ID = " + idSit;
						System.out.println(sql4);
						PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
						recursos.add(prepStmt4);
						ResultSet rs4 = prepStmt4.executeQuery();
						Sitio nuevosit = null;
						if(rs4.next())
						{
							String direccion = rs4.getString("DIRECCION");
							int capacidad = rs4.getInt("CAPACIDAD");
							Timestamp inicioHorario = rs4.getTimestamp("INICIO_HORARIO");
							Timestamp finHorario = rs4.getTimestamp("FIN_HORARIO");
							
							nuevosit = new Sitio(idSit, direccion, 0, capacidad, 0, inicioHorario, finHorario, 0, 0);
						}
						Funcion nuevaFun = new Funcion(idFun, fechaHora, costo, sillasOcupadas, yaSeRealizo, null, nuevosit);
						funciones.add(nuevaFun);
						
						
					}
					Espectaculo nuevoEspect = new Espectaculo(idEsp, nombre, duracion, intermedio, idioma, clasificacion, costoRealizacion, publicoActivo, tradSubt, tradAud, descripcion, pubObj, null, null, null, funciones);
					listaEspec.add(nuevoEspect);
				}

			}
			CompañiaTeatro compania = new CompañiaTeatro(idComp, nombreCom, fechaLLeg, fechaSali, null , null, new ListaEspectaculos(listaEspec));
			companiasLista.add(compania);
		}
		
		
		return companiasLista;
	}
	
	public ArrayList<Cliente> asistUsuariosFest(int idC, String FechaIni, String fechaFin, String criterio, String agrupar) throws SQLException, Exception
	{
		ArrayList<Cliente> listaClientes = new ArrayList<>();
		String sql = "WITH LISTA_FUNCIONES AS (SELECT IDFUN FROM (SELECT ID AS IDFUN FROM FUNCION F INNER JOIN"
				+ "(SELECT ID AS IDESPECT FROM ESPECTACULO E inner join (" 
				+ "(SELECT CE.ID_ESPECTACULO FROM COMPANIA_ESPECTACULO CE WHERE CE.ID_COMPANIA =" + idC + " )) H ON E.ID=H.ID_ESPECTACULO)"
				+ "K ON F.ID_ESPECTACULO = K.IDESPECT) INNER JOIN FUNCION ON IDFUN=ID WHERE FECHA_HORA BETWEEN '"+ FechaIni + "' AND '" + fechaFin +"'),"
				+ "ID_CLIENTES_FUN AS (SELECT DISTINCT ID_CLIENTE AS ID FROM LISTA_FUNCIONES LF INNER JOIN BOLETA B ON LF.IDFUN = B.ID_FUNCION)"
				+ "SELECT * FROM (SELECT * FROM ID_CLIENTES_FUN NATURAL JOIN CLIENTE) CL NATURAL JOIN USUARIO U WHERE ID!=99 "+ agrupar +" ORDER BY " + criterio;
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("ID");
			String nombre = rs.getString("NOMBRE");
			String mail = rs.getString("MAIL");
			String rol = rs.getString("ROL");
			String contrasena = rs.getString("CONTRASENA");
			Cliente nuevo = new Cliente(id, nombre, mail, rol, contrasena, null);
			listaClientes.add(nuevo);
		}
		return listaClientes;
	}
	
	public ArrayList<Cliente> asistNoUsuariosFest(int idC, String FechaIni, String fechaFin, String criterio, String agrupar) throws SQLException, Exception
	{
		ArrayList<Cliente> listaClientes = new ArrayList<>();
		String sql = "WITH LISTA_FUNCIONES AS (SELECT IDFUN FROM (SELECT ID AS IDFUN FROM FUNCION F INNER JOIN"
				+ "(SELECT ID AS IDESPECT FROM ESPECTACULO E inner join ("
				+ "(SELECT CE.ID_ESPECTACULO FROM COMPANIA_ESPECTACULO CE WHERE CE.ID_COMPANIA =" + idC + " )) H ON E.ID=H.ID_ESPECTACULO)"
				+ "K ON F.ID_ESPECTACULO = K.IDESPECT) INNER JOIN FUNCION ON IDFUN=ID WHERE FECHA_HORA BETWEEN '"+ FechaIni + "' AND '" + fechaFin +"'),"
				+ "ID_CLIENTES_FUN AS (SELECT DISTINCT ID_CLIENTE AS ID FROM LISTA_FUNCIONES LF INNER JOIN BOLETA B ON LF.IDFUN = B.ID_FUNCION)"
				+ "SELECT * FROM (SELECT DISTINCT * FROM (SELECT ID FROM CLIENTE MINUS SELECT * FROM ID_CLIENTES_FUN)) NATURAL JOIN CLIENTE NATURAL JOIN USUARIO WHERE ID!=99 "+ agrupar +" ORDER BY "+ criterio;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next())
		{
			int id = rs.getInt("ID");
			String nombre = rs.getString("NOMBRE");
			String mail = rs.getString("MAIL");
			String rol = rs.getString("ROL");
			String contrasena = rs.getString("CONTRASENA");
			Cliente nuevo = new Cliente(id, nombre, mail, rol, contrasena, null);
			listaClientes.add(nuevo);
		}
		return listaClientes;
	}
	
	public void cancelarCompania(int idComp) throws SQLException, Exception
	{


		String sql = "UPDATE BOLETA SET ESTADO = 0";
		sql += " WHERE ID = " + idComp;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();


	}
	
		public ArrayList<Rentabilidad> darRentabilidadCompania(Rentabilidad rent, Long idCompania) throws SQLException
	{
		ArrayList<Rentabilidad> listaRentabilidad = new ArrayList<>();
		String sql = " WITH RANGO_FECHAS AS (SELECT ID, FECHA_HORA, ID_ESPECTACULO, ID_SITIO FROM FUNCION WHERE FECHA_HORA BETWEEN '"+ rent.getFechaInicial()+  "' AND '"+ rent.getFechaFinal() + "'), "
				+ " FUNCION_ESPECTACULO AS (SELECT R.ID, FECHA_HORA, ID_ESPECTACULO, NOMBRE, ID_SITIO FROM (RANGO_FECHAS R INNER JOIN ESPECTACULO E ON R.ID_ESPECTACULO = E.ID)), "
				+ " ESPECTACULO_COMPANIA AS (SELECT ID, FECHA_HORA, C.ID_ESPECTACULO, NOMBRE, ID_SITIO, ID_COMPANIA FROM (FUNCION_ESPECTACULO F INNER JOIN COMPANIA_ESPECTACULO C ON F.ID_ESPECTACULO = C.ID_ESPECTACULO AND C.ID_COMPANIA= "+idCompania+")), "
				+ " CON_SITIO AS (SELECT E.ID, FECHA_HORA, ID_ESPECTACULO, NOMBRE, ID_SITIO,NOMBRE_SITIO, LUGAR_ABIERTO, ID_COMPANIA, CAPACIDAD FROM (ESPECTACULO_COMPANIA E INNER JOIN SITIO S ON E.ID_SITIO = S.ID)), "
				+ " CON_CATEGORIA AS (SELECT ID AS ID_FUNCION, FECHA_HORA, C.ID_ESPECTACULO, NOMBRE AS NOMBRE_ESPECTACULO, CATEGORIA, ID_SITIO, NOMBRE_SITIO, LUGAR_ABIERTO, ID_COMPANIA, CAPACIDAD FROM(CON_SITIO C INNER JOIN CATEGORIA_ESPECTACULO CE ON C.ID_ESPECTACULO = CE.ID_ESPECTACULO)), "
				+ " VENDIDAS AS (SELECT C.ID_FUNCION, FECHA_HORA, ID_ESPECTACULO, NOMBRE_ESPECTACULO, CATEGORIA, ID_SITIO, NOMBRE_SITIO, LUGAR_ABIERTO, CAPACIDAD, ID_COMPANIA, COUNT(ID_CLIENTE) AS BOLETAS_VENDIDAS, COUNT(ID_CLIENTE) AS ASISTENTES, SUM(B.COSTO) AS TOTAL_FACTURADO FROM(CON_CATEGORIA C INNER JOIN BOLETA B ON C.ID_FUNCION = B.ID_FUNCION AND (B.ESTADO = 2 OR B.ESTADO = 1))  GROUP BY C.ID_FUNCION, FECHA_HORA, ID_ESPECTACULO, NOMBRE_ESPECTACULO, CATEGORIA, ID_SITIO, NOMBRE_SITIO, LUGAR_ABIERTO, ID_COMPANIA, CAPACIDAD ), "
				+ " PROPORCION AS (SELECT ID_FUNCION, FECHA_HORA, ID_ESPECTACULO, NOMBRE_ESPECTACULO, CATEGORIA, ID_SITIO, NOMBRE_SITIO, LUGAR_ABIERTO, ASISTENTES/CAPACIDAD AS PROPORCION, ID_COMPANIA, BOLETAS_VENDIDAS, ASISTENTES, TOTAL_FACTURADO FROM VENDIDAS), "
				+ " NOMBRE_COMPANIA AS (SELECT ID_FUNCION, FECHA_HORA, ID_ESPECTACULO, NOMBRE_ESPECTACULO, CATEGORIA, ID_SITIO, NOMBRE_SITIO, LUGAR_ABIERTO, PROPORCION, ID_COMPANIA, BOLETAS_VENDIDAS, ASISTENTES, TOTAL_FACTURADO, NOMBRE FROM (PROPORCION P INNER JOIN COMPANIA_TEATRO CT ON P.ID_COMPANIA = CT.ID)) "
				+ " SELECT * FROM NOMBRE_COMPANIA";

		System.out.println("SQL stmt:" + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next())
		{
			String fechaInicial = rent.getFechaInicial();
			String fechaFinal = rent.getFechaFinal();
			String nombreCompania = rs.getString("NOMBRE");
			Long idEspectaculo = Long.parseLong(rs.getString("ID_ESPECTACULO"));
			int totalClientes = Integer.parseInt(rs.getString("ASISTENTES"));
			int totalBoletas = Integer.parseInt(rs.getString("BOLETAS_VENDIDAS"));
			Long idSitio = Long.parseLong(rs.getString("ID_SITIO"));
			char tipo = (rs.getString("LUGAR_ABIERTO")).charAt(0);
			double proporcion = Double.parseDouble(rs.getString("PROPORCION"));
			int precio = Integer.parseInt(rs.getString("TOTAL_FACTURADO"));
			String idCat = rs.getString("CATEGORIA");
			String fecha = rs.getString("FECHA_HORA");
			listaRentabilidad.add(new Rentabilidad(fechaInicial, fechaFinal, idEspectaculo, totalClientes,
					totalBoletas, idSitio, tipo, proporcion, precio,idCat,fecha, nombreCompania));
		}

		return listaRentabilidad;
	}

	public ArrayList<Rentabilidad> darRentabilidad(Rentabilidad rent) throws SQLException
	{
		ArrayList<Rentabilidad> listaRentabilidad = new ArrayList<>();
		String sql = " WITH RANGO_FECHAS AS (SELECT ID, FECHA_HORA, ID_ESPECTACULO, ID_SITIO FROM FUNCION WHERE FECHA_HORA BETWEEN '"+ rent.getFechaInicial()+  "' AND '"+ rent.getFechaFinal() + "'), "
				+ " FUNCION_ESPECTACULO AS (SELECT R.ID, FECHA_HORA, ID_ESPECTACULO, NOMBRE, ID_SITIO FROM (RANGO_FECHAS R INNER JOIN ESPECTACULO E ON R.ID_ESPECTACULO = E.ID)), "
				+ " ESPECTACULO_COMPANIA AS (SELECT ID, FECHA_HORA, C.ID_ESPECTACULO, NOMBRE, ID_SITIO, ID_COMPANIA FROM (FUNCION_ESPECTACULO F INNER JOIN COMPANIA_ESPECTACULO C ON F.ID_ESPECTACULO = C.ID_ESPECTACULO )), "
				+ " CON_SITIO AS (SELECT E.ID, FECHA_HORA, ID_ESPECTACULO, NOMBRE, ID_SITIO,NOMBRE_SITIO, LUGAR_ABIERTO, ID_COMPANIA, CAPACIDAD FROM (ESPECTACULO_COMPANIA E INNER JOIN SITIO S ON E.ID_SITIO = S.ID)), "
				+ " CON_CATEGORIA AS (SELECT ID AS ID_FUNCION, FECHA_HORA, C.ID_ESPECTACULO, NOMBRE AS NOMBRE_ESPECTACULO, CATEGORIA, ID_SITIO, NOMBRE_SITIO, LUGAR_ABIERTO, ID_COMPANIA, CAPACIDAD FROM(CON_SITIO C INNER JOIN CATEGORIA_ESPECTACULO CE ON C.ID_ESPECTACULO = CE.ID_ESPECTACULO)), "
				+ " VENDIDAS AS (SELECT C.ID_FUNCION, FECHA_HORA, ID_ESPECTACULO, NOMBRE_ESPECTACULO, CATEGORIA, ID_SITIO, NOMBRE_SITIO, LUGAR_ABIERTO, CAPACIDAD, ID_COMPANIA, COUNT(ID_CLIENTE) AS BOLETAS_VENDIDAS, COUNT(ID_CLIENTE) AS ASISTENTES, SUM(B.COSTO) AS TOTAL_FACTURADO FROM(CON_CATEGORIA C INNER JOIN BOLETA B ON C.ID_FUNCION = B.ID_FUNCION AND (B.ESTADO = 2 OR B.ESTADO = 1))  GROUP BY C.ID_FUNCION, FECHA_HORA, ID_ESPECTACULO, NOMBRE_ESPECTACULO, CATEGORIA, ID_SITIO, NOMBRE_SITIO, LUGAR_ABIERTO, ID_COMPANIA, CAPACIDAD ), "
				+ " PROPORCION AS (SELECT ID_FUNCION, FECHA_HORA, ID_ESPECTACULO, NOMBRE_ESPECTACULO, CATEGORIA, ID_SITIO, NOMBRE_SITIO, LUGAR_ABIERTO, ASISTENTES/CAPACIDAD AS PROPORCION, ID_COMPANIA, BOLETAS_VENDIDAS, ASISTENTES, TOTAL_FACTURADO FROM VENDIDAS), "
				+ " NOMBRE_COMPANIA AS (SELECT ID_FUNCION, FECHA_HORA, ID_ESPECTACULO, NOMBRE_ESPECTACULO, CATEGORIA, ID_SITIO, NOMBRE_SITIO, LUGAR_ABIERTO, PROPORCION, ID_COMPANIA, BOLETAS_VENDIDAS, ASISTENTES, TOTAL_FACTURADO, NOMBRE FROM (PROPORCION P INNER JOIN COMPANIA_TEATRO CT ON P.ID_COMPANIA = CT.ID)) "
				+ " SELECT * FROM NOMBRE_COMPANIA";

		System.out.println("SQL stmt:" + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next())
		{
			String fechaInicial = rent.getFechaInicial();
			String fechaFinal = rent.getFechaFinal();
			String nombreCompania = rs.getString("NOMBRE");
			Long idEspectaculo = Long.parseLong(rs.getString("ID_ESPECTACULO"));
			int totalClientes = Integer.parseInt(rs.getString("ASISTENTES"));
			int totalBoletas = Integer.parseInt(rs.getString("BOLETAS_VENDIDAS"));
			Long idSitio = Long.parseLong(rs.getString("ID_SITIO"));
			char tipo = (rs.getString("LUGAR_ABIERTO")).charAt(0);
			double proporcion = Double.parseDouble(rs.getString("PROPORCION"));
			int precio = Integer.parseInt(rs.getString("TOTAL_FACTURADO"));
			String idCat = rs.getString("CATEGORIA");
			String fecha = rs.getString("FECHA_HORA");
			listaRentabilidad.add(new Rentabilidad(fechaInicial, fechaFinal, idEspectaculo, totalClientes,
					totalBoletas, idSitio, tipo, proporcion, precio,idCat,fecha, nombreCompania));
		}

		return listaRentabilidad;	
	}

}
