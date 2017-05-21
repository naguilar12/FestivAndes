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

import vos.Boleta;
import vos.Cliente;
import vos.CompañiaTeatro;
import vos.Espectaculo;
import vos.Festival;
import vos.Funcion;
import vos.ListaBoletas;
import vos.ListaCategorias;
import vos.ListaCompañias;
import vos.ListaRequerimientos;
import vos.Localidad;
import vos.NotaDebito;

public class DAOTablaFuncion {


	/**
	 * Arraylits de recursos que se usan para la ejecuciÃ³n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexiÃ³n a la base de datos
	 */
	private Connection conn;

	/**
	 * MÃ©todo constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaFuncion() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * MÃ©todo que cierra todos los recursos que estan enel arreglo de recursos
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
	 * MÃ©todo que inicializa la connection del DAO a la base de datos con la conexiÃ³n que entra como parÃ¡metro.
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
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		try{
			if (rs.next()) {

				int id = Integer.parseInt(rs.getString("id"));
				int idEspectaculo = Integer.parseInt(rs.getString("id_espectaculo"));
				Timestamp fechaHora = rs.getTimestamp("fecha_hora");
				double costo = Double.parseDouble(rs.getString("costo"));
				int sillasreservadas = Integer.parseInt(rs.getString("sillas_ocupadas"));
				int  realizada = Integer.parseInt(rs.getString("ya_se_realizo"));

				Espectaculo espectaculo = null;

				String sql2 = "SELECT * FROM ESPECTACULO WHERE ID=" + idEspectaculo;


				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				ResultSet rs2 = prepStmt2.executeQuery();
				if (rs2.next()) {

					String nombre = rs2.getString("NOMBRE");
					double duracion = Double.parseDouble(rs2.getString("DURACION"));
					int  intermedio= Integer.parseInt(rs2.getString("INTERMEDIO"));
					String idioma= rs2.getString("IDIOMA");
					String clasificacion= rs2.getString("CLASIFICACION");
					double costoRealizacion = Double.parseDouble(rs2.getString("COSTO_REALIZACION"));
					int  publicoActivo= Integer.parseInt(rs2.getString("PUBLICO_ACTIVO"));
					int  traduccionSubtitulos = Integer.parseInt(rs2.getString("TRADUCCION_SUBTITULOS"));
					int  traduccionAudifonos = Integer.parseInt(rs2.getString("TRADUCCION_AUDIFONOS"));
					String descripcion= rs2.getString("DESCRIPCION");
					String publicoObjetivo= rs2.getString("PUBLICO_OBJETIVO");

					ListaCompañias compañias = null;
					ListaCategorias categorias = null;
					ListaRequerimientos requerimientos = null;
					espectaculo = new Espectaculo(idEspectaculo, nombre, duracion, intermedio, idioma, clasificacion, costoRealizacion, publicoActivo, traduccionSubtitulos, traduccionAudifonos, descripcion, publicoObjetivo, compañias, categorias, requerimientos,null);

				}
				funcion =  new Funcion(id, fechaHora, costo, sillasreservadas, realizada, espectaculo, null);
				rs2.close();
				prepStmt2.close();
			}
		}
		finally {
			rs.close();
			prepStmt.close();
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
			Funcion funcion = darFuncion(idFuncion);

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


			List<CompañiaTeatro> lista = new ArrayList<>();
			lista.add(new CompañiaTeatro(idCompania, "", null, null, null, null, null));
			ListaCompañias compañias = new ListaCompañias(lista);
			ListaCategorias categorias = null;
			ListaRequerimientos requerimientos = null;

			Espectaculo espectaculo = new Espectaculo(idEspectaculo, nombre, duracion, intermedio, pidioma, clasificacion, costoRealizacion, publicoActivo, traduccionSubtitulos, traduccionAudifonos, descripcion, publicoObjetivo, compañias, categorias, requerimientos, null);
			listafuncion.add(new Funcion(idFun, fechaHora, costo, sillasreservadas, realizada, espectaculo, null));

		}
		return listafuncion;
	}

	public List<NotaDebito> cancelarFuncion(int idC) throws SQLException, Exception
	{
		List<NotaDebito> notas = new ArrayList<>();

		String sql = "UPDATE FUNCION FUN SET ESTADO = 0 WHERE EXISTS ";
		sql += " (SELECT * FROM (SELECT ID AS IDFUN  FROM(SELECT ID_ESPECTACULO FROM COMPANIA_ESPECTACULO E WHERE E.ID_COMPANIA = " + idC + " ) ";
		sql += "NATURAL JOIN FUNCION E )IDFUN WHERE IDFUN.IDFUN = FUN.ID AND FUN.YA_SE_REALIZO=0)";
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		String sql2 = "SELECT * FROM BOLETA BOL INNER JOIN (SELECT * FROM (SELECT ID AS IDFUN  FROM(SELECT ID_ESPECTACULO FROM COMPANIA_ESPECTACULO E WHERE E.ID_COMPANIA = 1) "
				+ "NATURAL JOIN FUNCION E)IDFUN ) ON BOL.ID_FUNCION= IDFUN";
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs =prepStmt2.executeQuery();
		System.out.println(sql2);
		try{
			while (rs.next()) {
				long codigo = 1;
				long idLocalidad = rs.getLong("ID_LOCALIDAD");
				long idUbicacion = rs.getLong("UBICACION");
				long idFuncion = rs.getLong("ID_FUNCION");
				long idCliente = rs.getLong("ID_CLIENTE");

				Funcion fun = darFuncion((int)idFuncion);

				String sql3 = "UPDATE BOLETA SET ESTADO = 3";
				sql3 += " WHERE UBICACION ='" + idUbicacion+"'";
				sql3 += " AND ID_LOCALIDAD = " + idLocalidad;
				sql3 += " AND ID_FUNCION = " + idFuncion;

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				prepStmt3.executeQuery();
				prepStmt3.close();
				NotaDebito nuevaNota = new NotaDebito(codigo, codigo, new Date(), fun.getFechaHora(), idCliente, idFuncion, fun);
				notas.add(nuevaNota);
			}
		}
		finally {
			rs.close();
			prepStmt.close();
		}
		return notas;
	}

}
