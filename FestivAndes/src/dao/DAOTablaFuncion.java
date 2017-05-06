package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import vos.Boleta;
import vos.Cliente;
import vos.Espectaculo;
import vos.Festival;
import vos.Funcion;
import vos.ListaBoletas;
import vos.ListaCategorias;
import vos.ListaCompañias;
import vos.ListaRequerimientos;
import vos.Localidad;

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
			
			ListaCompañias compañias = null;
			ListaCategorias categorias = null;
			ListaRequerimientos requerimientos = null;
			
			Espectaculo espectaculo = new Espectaculo(idEspectaculo, "", 0, 0, "", "", 0, 0, 0, 0, "", "", compañias, categorias, requerimientos, null);
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



}
