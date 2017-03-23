package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Cliente;
import vos.Espectaculo;
import vos.Funcion;
import vos.ListaCategorias;
import vos.ListaCompa�ias;
import vos.ListaRequerimientos;
import vos.ListaSillas;
import vos.Preferencia;
import vos.Reserva;
import vos.Video;

public class DAOTablaUsuarios {

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
	public DAOTablaUsuarios() {
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

	////////////////////////////////////////RF7////////////////////////////////////////////////////////////////

	public void addPreferenciaCliente(Preferencia preferencia, int idUsuario) throws SQLException, Exception {
		String sql1 = "SELECT * FROM CLIENTE WHERE id ='" + idUsuario+ "'";

		System.out.println("SQL stmt:" + sql1);

		PreparedStatement prepStmt = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {

			String sql = "INSERT INTO ISIS2304A021720.PREFERENCIA VALUES (";
			sql += idUsuario + ",'";
			sql += preferencia.getTipo() + "',";
			sql += "'"+preferencia.getPreferencia() + "')";

			System.out.println("SQL stmt:" + sql);

			PreparedStatement prepStmta = conn.prepareStatement(sql);
			recursos.add(prepStmta);
			prepStmta.executeQuery();
		}
	}

	public void deletePreferenciaCliente(Preferencia preferencia, int idUsuario) throws SQLException, Exception {

		String sql = "DELETE FROM PREFERENCIA";
		sql += " WHERE id = " + idUsuario;
		sql += " AND preferencia = '" + preferencia.getPreferencia()+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public ArrayList<Preferencia> buscarPreferenciasPorUsuario(int idUsuario) throws SQLException, Exception {
		ArrayList<Preferencia> preferencias= new ArrayList<Preferencia>();

		String sql = "SELECT * FROM PREFERENCIA WHERE id ='" + idUsuario+ "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			String preferencia = rs.getString("preferencia");
			String tipo = rs.getString("tipo");
			preferencias.add(new Preferencia(tipo, preferencia));
		}

		return preferencias;
	}

	public ArrayList<Preferencia> darPreferencias() throws SQLException, Exception {
		ArrayList<Preferencia> preferencias= new ArrayList<Preferencia>();

		String sql = "SELECT * FROM ISIS2304A021720.PREFERENCIA";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String preferencia = rs.getString("preferencia");
			String tipo = rs.getString("tipo");
			preferencias.add(new Preferencia(tipo, preferencia));
		}
		return preferencias;
	}

	public boolean esCliente(int id) throws SQLException, Exception
	{
		boolean encontro = false;
		String sql = "SELECT * FROM ISIS2304A021720.CLIENTE WHERE ID = " + id ;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs==null)
			encontro = true;

		return encontro;
	}

	////////////////////////////////////////RF8////////////////////////////////////////////////////////////////

	//	public Reserva comprarBoletas(int id, int idFuncion, ListaSillas sillas) throws SQLException, Exception
	//	{
	//		if(esCliente(id) && esfuncion(idFuncion))
	//		{
	//
	//		}
	//	}	
}
