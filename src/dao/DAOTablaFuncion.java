package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import vos.Abonamiento;
import vos.Boleta;
import vos.Cliente;
import vos.Compa�iaTeatro;
import vos.Espectaculo;
import vos.Festival;
import vos.Funcion;
import vos.ListaBoletas;
import vos.ListaCategorias;
import vos.ListaCompa�ias;
import vos.ListaRequerimientos;
import vos.Localidad;
import vos.Sitio;

public class DAOTablaFuncion {


	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaFuncion() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra todos los recursos que estan enel arreglo de recursos
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
	 * Método que inicializa la connection del DAO a la base de datos con la conexión que entra como parámetro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	////////////////////////////////////////RF9////////////////////////////////////////////////////////////////

	public Funcion realizarFuncion(int idO, int idE, int idF) throws SQLException, Exception {

		String sql1 = "SELECT * FROM ORGANIZADOR WHERE ID =" + idO;

		System.out.println("SQL stmt:" + sql1);

		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);
		ResultSet rs = prepStmt1.executeQuery();
		if (rs.next()) 
		{
			String sql = "UPDATE FUNCION SET ";
			sql += "ya_se_realizo=" + "1";
			sql += " WHERE ID = " + idF;
			sql += " AND ID_ESPECTACULO = " + idE;

			System.out.println("SQL stmt:" + sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
		return darFuncion(idF);
	}

	public Funcion darFuncion(int idF) throws SQLException, Exception {
		Funcion funcion = null;

		String sql = "SELECT * FROM FUNCION WHERE ID = " + idF;
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {

			int id = Integer.parseInt(rs.getString("id"));
			int idEspectaculo = Integer.parseInt(rs.getString("id_espectaculo"));
			Timestamp fechaHora = rs.getTimestamp("fecha_hora");
			double costo = Double.parseDouble(rs.getString("costo"));
			int sillasreservadas = Integer.parseInt(rs.getString("sillas_ocupadas"));
			int  realizada = Integer.parseInt(rs.getString("ya_se_realizo"));
			
			ListaCompa�ias compa�ias = null;
			ListaCategorias categorias = null;
			ListaRequerimientos requerimientos = null;
			
			Espectaculo espectaculo = new Espectaculo(idEspectaculo, "", 0, 0, "", "", 0, 0, 0, 0, "", "", compa�ias, categorias, requerimientos, null);
			funcion =  new Funcion(id, fechaHora, costo, sillasreservadas, realizada, espectaculo, null);

		}
		return funcion;
	}
	
	public ListaBoletas darBoletasFuncion (int idF) throws SQLException, Exception
	{
		ArrayList<Boleta> boletas = new ArrayList<>();

		String sql = "SELECT * FROM BOLETA WHERE ID_FUNCION =" + idF;
		
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int idLocalidad = rs.getInt("ID_LOCALIDAD");
			int idFuncion = rs.getInt("ID_FUNCION");
			int idCliente = rs.getInt("ID_CLIENTE");
			
			int ubicacion = rs.getInt("UBICACION");
			int estado = rs.getInt("ESTADO");
			double costo = rs.getDouble("COSTO");

			Localidad localidad =  new Localidad(idLocalidad, 0, 0, "", null, null);
			Funcion funcion = new Funcion(idFuncion, null, 0, 0, 0, null,null);
			
			Cliente resultado = null;

			String sql1 = "SELECT * FROM USUARIO WHERE ID ="+idCliente;
			System.out.println(sql1);
			PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
			recursos.add(prepStmt1);
			ResultSet rs1 = prepStmt1.executeQuery();

			if (rs1.next()) {
				String nombre = rs1.getString("NOMBRE");
				String mail = rs1.getString("MAIL");
				String rol = rs1.getString("ROL");

				String sql2 = "SELECT * FROM CLIENTE WHERE ID ="+idCliente;
				System.out.println(sql2);
				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				ResultSet rs2 = prepStmt2.executeQuery();
				if(rs2.next())
				{
					String contrasena = rs2.getString("CONTRASENA");
					Festival nuevoFest = null;
					resultado = new Cliente(idCliente, nombre, mail, rol, contrasena, nuevoFest);
				}
			}
			Boleta bol = new Boleta(ubicacion, estado, costo, localidad, funcion, resultado); 
			
			boletas.add(bol);
		}
		

		return new ListaBoletas(boletas);

	}
	
	public ArrayList<Funcion> darFunciones(String fechas, String compa, String idioma, String traduccion) throws SQLException, Exception {
		
		ArrayList<Funcion> listafuncion = new ArrayList<>();
		String sql = "WITH LISTA_FUNCION AS (SELECT F.ID AS IDFUN, ID_ESPECTACULO, ID_SITIO, COSTO, SILLAS_OCUPADAS, FECHA_HORA, YA_SE_REALIZO, ESTADO FROM FUNCION F)"
				+ "SELECT * FROM (LISTA_FUNCION D INNER JOIN ESPECTACULO E ON D.ID_ESPECTACULO = E.ID) NATURAL JOIN COMPANIA_ESPECTACULO C WHERE 1=1 ";
		if(fechas.isEmpty())
		{
			sql += "AND 1=1 ";
		}
		else
		{
			sql+=fechas;
		}
		
		if(compa.isEmpty())
		{
			sql += "AND 1=1 ";
		}
		else
		{
			sql+=compa;
		}
		
		if(idioma.isEmpty())
		{
			sql += "AND 1=1 ";
		}
		else
		{
			sql+=idioma;
		}
		
		if(traduccion.isEmpty())
		{
			sql += "AND 1=1 ";
		}
		else
		{
			sql+=traduccion;
		}
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			int idFun = Integer.parseInt(rs.getString("IDFUN"));
			int idEspectaculo = Integer.parseInt(rs.getString("id_espectaculo"));
			int idCompania = Integer.parseInt(rs.getString("id_compania"));
			Timestamp fechaHora = rs.getTimestamp("fecha_hora");
			double costo = Double.parseDouble(rs.getString("costo"));
			int sillasreservadas = Integer.parseInt(rs.getString("sillas_ocupadas"));
			int  realizada = Integer.parseInt(rs.getString("ya_se_realizo"));
			String nombre = rs.getString("NOMBRE");
			double duracion = Double.parseDouble(rs.getString("DURACION"));
			int  intermedio= Integer.parseInt(rs.getString("INTERMEDIO"));
			String pidioma= rs.getString("IDIOMA");
			String clasificacion= rs.getString("CLASIFICACION");
			double costoRealizacion = Double.parseDouble(rs.getString("COSTO_REALIZACION"));
			int  publicoActivo= Integer.parseInt(rs.getString("PUBLICO_ACTIVO"));
			int  traduccionSubtitulos = Integer.parseInt(rs.getString("TRADUCCION_SUBTITULOS"));
			int  traduccionAudifonos = Integer.parseInt(rs.getString("TRADUCCION_AUDIFONOS"));
			String descripcion= rs.getString("DESCRIPCION");
			String publicoObjetivo= rs.getString("PUBLICO_OBJETIVO");
			
			
			List<Compa�iaTeatro> lista = new ArrayList<>();
			lista.add(new Compa�iaTeatro(idCompania, "", null, null, null, null, null));
			ListaCompa�ias compa�ias = new ListaCompa�ias(lista);
			ListaCategorias categorias = null;
			ListaRequerimientos requerimientos = null;
			
			Espectaculo espectaculo = new Espectaculo(idEspectaculo, nombre, duracion, intermedio, pidioma, clasificacion, costoRealizacion, publicoActivo, traduccionSubtitulos, traduccionAudifonos, descripcion, publicoObjetivo, compa�ias, categorias, requerimientos, null);
			listafuncion.add(new Funcion(idFun, fechaHora, costo, sillasreservadas, realizada, espectaculo, null));

		}
		return listafuncion;
	}

	public void cancelarFuncion(int idFun) throws SQLException, Exception
	{


		String sql = "UPDATE BOLETA SET ESTADO = 0";
		sql += " WHERE ID = " + idFun;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public boolean boletaDisponible(Long idFuncion, String localidad) throws SQLException, Exception
	{
		boolean boletaDisponible = false;
		
		String sql = " WITH NUMERO_BOLETAS_LOCALIDAD AS (SELECT ID_FUNCION, ID_SITIO, ID_LOCALIDAD, NOMBRE, CAPACIDAD, COUNT(*) AS OCUPADAS FROM (BOLETA B INNER JOIN LOCALIDAD L ON B.ID_LOCALIDAD = L.ID AND (B.ESTADO = 0 OR B.ESTADO = 1) AND B.ID_FUNCION ="+ idFuncion +" AND L.NOMBRE = '"+localidad+"') GROUP BY ID_FUNCION, ID_SITIO, ID_LOCALIDAD, NOMBRE, CAPACIDAD), "
				   + " DISPONIBLES AS (SELECT ID_FUNCION, ID_SITIO, ID_LOCALIDAD, NOMBRE, CAPACIDAD, OCUPADAS, CAPACIDAD-OCUPADAS AS DISPONIBLES FROM NUMERO_BOLETAS_LOCALIDAD) "
			       + " SELECT * FROM DISPONIBLES";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt1 = conn.prepareStatement(sql);
		recursos.add(prepStmt1);
		ResultSet rs = prepStmt1.executeQuery();
		if (rs.next()) 
		{
			if(rs.getInt("DISPONIBLES")>0)
				boletaDisponible = true;
		}
		
		return boletaDisponible;
	}
	
	public Boleta darBoletaDisponible(Long idFuncion, String localidad) throws SQLException, Exception
	{
		Boleta b = null;
		
		String sql = " WITH RESPUESTA AS (SELECT * FROM (BOLETA B INNER JOIN LOCALIDAD L ON B.ID_LOCALIDAD = L.ID AND (B.ESTADO = 0 OR B.ESTADO = 3) AND NOMBRE = '"+localidad+"' AND B.ID_FUNCION = "+idFuncion+")) "
				   + " SELECT * FROM RESPUESTA ";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt1 = conn.prepareStatement(sql);
		recursos.add(prepStmt1);
		ResultSet rs = prepStmt1.executeQuery();
		if (rs.next()) 
		{
			ListaBoletas list = null;
			Sitio s = null;
			Espectaculo e = null;
			Localidad l = new Localidad(Integer.parseInt(rs.getString("ID_LOCALIDAD")), 0, 0, localidad, list, s);
			Funcion f = new Funcion(rs.getInt("ID_FUNCION"), new Timestamp(0), 0, 0, 0, e, s);
			Cliente c =null;
			b = new Boleta(rs.getInt("UBICACION"), rs.getInt("ESTADO"), rs.getDouble("COSTO"), l , f, c);
		}
		
		return b;
		
	}
}
