package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Espectaculo;
import vos.ListaCategorias;
import vos.ListaCompa�ias;
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

	public ArrayList<Espectaculo> darEspectaculos() throws SQLException, Exception {
		ArrayList<Espectaculo> espectaculos = new ArrayList<Espectaculo>();

		//String sql = "SELECT * FROM ISIS2304MO11620.VIDEOS";
		String sql = "SELECT * FROM ESPECTACULO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("nombre");
			int id = Integer.parseInt(rs.getString("id"));
			double duracion= Double.parseDouble(rs.getString("duracion"));
			boolean intermedio = rs.getBoolean("intermedio");
			String idioma = rs.getString("idioma");
			String clasificacion = rs.getString("clasificacion");
			double costoRealizacion= Double.parseDouble(rs.getString("costo_realizacion"));
			boolean publicoActivo = rs.getBoolean("publico_activo");
			boolean traduccionSubtitulos = rs.getBoolean("traduccion_subtitulos");
			boolean traduccionAudifonos = rs.getBoolean("audifonos");
			String descripcion= rs.getString("descripcion");
			String publicoObjetivo= rs.getString("publico_objetivo");
			ListaCompa�ias compa�ias = null;
			ListaCategorias categorias = null;
			ListaRequerimientos requerimientos = null;
			
			espectaculos.add(new Espectaculo(id, nombre, duracion, intermedio, idioma, clasificacion, costoRealizacion, publicoActivo, traduccionSubtitulos, traduccionAudifonos, descripcion, publicoObjetivo, compa�ias, categorias, requerimientos));
		}
		return espectaculos;
	}
}
