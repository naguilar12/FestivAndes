package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Espectaculo;
import vos.Funcion;
import vos.ListaCategorias;
import vos.ListaCompanias;
import vos.ListaRequerimientos;

public class DAOTablaEspectaculos {	

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
	public DAOTablaEspectaculos() {
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

//	public ArrayList<Espectaculo> darEspectaculos() throws SQLException, Exception {
//		ArrayList<Espectaculo> espectaculos = new ArrayList<Espectaculo>();
//
//		//String sql = "SELECT * FROM ISIS2304MO11620.VIDEOS";
//		String sql = "SELECT * FROM ISIS2304A231720.ESPECTACULO";
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		while (rs.next()) {
//			String nombre = rs.getString("nombre");
//			int id = Integer.parseInt(rs.getString("id"));
//			double duracion= Double.parseDouble(rs.getString("duracion"));
//			boolean intermedio = rs.getBoolean("intermedio");
//			String idioma = rs.getString("idioma");
//			String clasificacion = rs.getString("clasificacion");
//			double costoRealizacion= Double.parseDouble(rs.getString("costo_realizacion"));
//			boolean publicoActivo = rs.getBoolean("publico_activo");
//			boolean traduccionSubtitulos = rs.getBoolean("traduccion_subtitulos");
//			boolean traduccionAudifonos = rs.getBoolean("audifonos");
//			String descripcion= rs.getString("descripcion");
//			String publicoObjetivo= rs.getString("publico_objetivo");
//			ListaCompa�ias compa�ias = null;
//			ListaCategorias categorias = null;
//			ListaRequerimientos requerimientos = null;
//			
//			espectaculos.add(new Espectaculo(id, nombre, duracion, intermedio, idioma, clasificacion, costoRealizacion, publicoActivo, traduccionSubtitulos, traduccionAudifonos, descripcion, publicoObjetivo, compa�ias, categorias, requerimientos));
//		}
//		return espectaculos;
//	}
	
	public Espectaculo darEspectaculo(int idE) throws SQLException, Exception {
		Espectaculo espectaculo = null;

		String sql = "SELECT * FROM ESPECTACULO WHERE ID=" + idE;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			int id = Integer.parseInt(rs.getString("id"));
			String nombre = rs.getString("NOMBRE");
			double duracion = Double.parseDouble(rs.getString("DURACION"));
			int  intermedio= Integer.parseInt(rs.getString("INTERMEDIO"));
			String idioma= rs.getString("IDIOMA");
			String clasificacion= rs.getString("CLASIFICACION");
			double costoRealizacion = Double.parseDouble(rs.getString("COSTO_REALIZACION"));
			int  publicoActivo= Integer.parseInt(rs.getString("PUBLICO_ACTIVO"));
			int  traduccionSubtitulos = Integer.parseInt(rs.getString("TRADUCCION_SUBTITULOS"));
			int  traduccionAudifonos = Integer.parseInt(rs.getString("TRADUCCION_AUDIFONOS"));
			String descripcion= rs.getString("DESCRIPCION");
			String publicoObjetivo= rs.getString("PUBLICO_OBJETIVO");
			
			ListaCompanias compa�ias = null;
			ListaCategorias categorias = null;
			ListaRequerimientos requerimientos = null;
			espectaculo = new Espectaculo(id, nombre, duracion, intermedio, idioma, clasificacion, costoRealizacion, publicoActivo, traduccionSubtitulos, traduccionAudifonos, descripcion, publicoObjetivo, compa�ias, categorias, requerimientos,null);

		}
		return espectaculo;
	}

	
	////////////////////////////////////////RF9////////////////////////////////////////////////////////////////
	
//	public Funcion realizarFuncion(int idE, int idF) throws SQLException, Exception {
//
//		String sql = "UPDATE FUNCION SET ";
//		sql += "ya_se_realizo='" + "1";
//		sql += " WHERE ID = " + idF;
//		sql += " WHERE ID_ESPECTACULOS = " + idF;
//
//		System.out.println("SQL stmt:" + sql);
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();
//		
//		return darFuncion(idF);
//	}
//	
//	public Funcion darFuncion(int idF) throws SQLException, Exception {
//		Funcion funcion = null;
//
//		String sql = "SELECT * FROM FUNCION WHERE ID=" + idF;
//
//		System.out.println("SQL stmt:" + sql);
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		while (rs.next()) {
//			
//			int id = Integer.parseInt(rs.getString("id"));
//			Date fechaHora =  rs.getDate("fecha_hora");
//			double costo = Double.parseDouble(rs.getString("costo"));
//			int sillasreservadas = Integer.parseInt(rs.getString("sillas_reservadas"));
//			int  realizada = Integer.parseInt(rs.getString("ya_se_realizo"));
//			Espectaculo espectaculo = null;
//			funcion = new Funcion(id, fechaHora, costo, sillasreservadas, realizada, espectaculo);
//		}
//		return funcion;
//	}
	
	////////////////////////////////////////RFC1////////////////////////////////////////////////////////////////
	
//	public ArrayList<Espectaculo>darEspectaculosIdioma(String idioma) throws SQLException, Exception {
//		ArrayList<Espectaculo> espectaculos = new ArrayList<Espectaculo>();
//
//		//String sql = "SELECT * FROM ISIS2304MO11620.VIDEOS";
//		String sql = "SELECT * FROM ISIS2304A021720.ESPECTACULO";
//			   sql+= "WHERE idioma='"+idioma+"'";
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		while (rs.next()) {
//			String nombre = rs.getString("nombre");
//			int id = Integer.parseInt(rs.getString("id"));
//			double duracion= Double.parseDouble(rs.getString("duracion"));
//			boolean intermedio = rs.getBoolean("intermedio");
//			String clasificacion = rs.getString("clasificacion");
//			double costoRealizacion= Double.parseDouble(rs.getString("costo_realizacion"));
//			boolean publicoActivo = rs.getBoolean("publico_activo");
//			boolean traduccionSubtitulos = rs.getBoolean("traduccion_subtitulos");
//			boolean traduccionAudifonos = rs.getBoolean("audifonos");
//			String descripcion= rs.getString("descripcion");
//			String publicoObjetivo= rs.getString("publico_objetivo");
//			ListaCompa�ias compa�ias = null;
//			ListaCategorias categorias = null;
//			ListaRequerimientos requerimientos = null;
//			
//			espectaculos.add(new Espectaculo(id, nombre, duracion, intermedio, idioma, clasificacion, costoRealizacion, publicoActivo, traduccionSubtitulos, traduccionAudifonos, descripcion, publicoObjetivo, compa�ias, categorias, requerimientos));
//		}
//		return espectaculos;
//	}
	

	
	
	
}
