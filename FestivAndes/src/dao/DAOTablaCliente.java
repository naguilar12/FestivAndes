package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import com.sun.jmx.snmp.Timestamp;

import vos.Boleta;
import vos.Cliente;
import vos.Festival;
import vos.Funcion;
import vos.ListaBoletas;
import vos.ListaClientes;
import vos.ListaCompa�ias;
import vos.ListaOrganizadores;
import vos.Localidad;
import vos.Preferencia;
import vos.Sitio;
import vos.Video;

public class DAOTablaCliente {
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
	public DAOTablaCliente() 
	{
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

	public ArrayList<Preferencia> darPreferencias() throws SQLException, Exception {
		ArrayList<Preferencia> preferencias = new ArrayList<Preferencia>();

		String sql = "SELECT * FROM PREFERENCIA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("ID");
			String peferencia = rs.getString("PREFERENCIA");
			String tipo = rs.getString("TIPO");
			preferencias.add(new Preferencia(tipo, peferencia, id));
		}
		return preferencias;
	}

	public ArrayList<Preferencia> buscarPreferenciasPorUsuario(int idUsuario) throws SQLException, Exception {
		ArrayList<Preferencia> preferencias= new ArrayList<Preferencia>();

		String sql = "SELECT * FROM PREFERENCIA WHERE ID =" + idUsuario;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			String preferencia = rs.getString("PREFERENCIA");
			String tipo = rs.getString("TIPO");
			preferencias.add(new Preferencia(tipo, preferencia,id));
		}

		return preferencias;
	}

	public void addPreferenciaCliente(Preferencia preferencia, int idUsuario) throws SQLException, Exception {
		String sql1 = "SELECT * FROM CLIENTE WHERE ID =" + idUsuario;

		System.out.println("SQL stmt:" + sql1);

		PreparedStatement prepStmt = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if (rs.next()) {

			String sql = "INSERT INTO PREFERENCIA VALUES (";
			sql += idUsuario + ",'";
			sql += preferencia.getPreferencia() + "',";
			sql += "'"+preferencia.getTipo() + "')";

			System.out.println("SQL stmt:" + sql);

			PreparedStatement prepStmta = conn.prepareStatement(sql);
			recursos.add(prepStmta);
			prepStmta.executeQuery();
		}
	}

	public void deletePreferenciaCliente(Preferencia preferencia, int idUsuario) throws SQLException, Exception {

		String sql = "DELETE FROM PREFERENCIA";
		sql += " WHERE ID = " + idUsuario;
		sql += " AND PREFERENCIA = '" + preferencia.getPreferencia()+"'";
		sql += " AND TIPO = '" + preferencia.getTipo()+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public Boleta comprarBoletaNumerada(Boleta boleta, int idUsuario, boolean abonado) throws SQLException, Exception {

		int nuevoEstado = Boleta.NO_DISPONIBLE;

		if(abonado)
			nuevoEstado = Boleta.ABONADA;

		if(boletaNumeradaDisponible(boleta))
		{
			String sql = "UPDATE BOLETA ";
			sql += " SET ID_CLIENTE = " + idUsuario;
			sql += ", ESTADO = " + nuevoEstado;
			sql += " WHERE UBICACION ='" + boleta.getUbicacion()+"'";
			sql += " AND ID_LOCALIDAD = " + boleta.getLocalidad().getId();
			sql += " AND ID_FUNCION = " + boleta.getFuncion().getId();

			System.out.println("SQL stmt:" + sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();	
		}

		return darBoleta(boleta);
	}

	public ArrayList<Boleta> comprarBoletasNoNumeradas(Boleta boleta, int idCliente, int necesarias, int ocupadas, boolean abonado) throws SQLException, Exception {

		int nuevoEstado = Boleta.NO_DISPONIBLE;

		if(abonado)
			nuevoEstado = Boleta.ABONADA;
		ArrayList<Boleta> boletas = new ArrayList<>();

		for(int i=0; i < necesarias;i++)
		{
			String sql = "UPDATE BOLETA ";
			sql += " SET ID_CLIENTE = "+ idCliente;
			sql += ", ESTADO = " + nuevoEstado;
			sql += " WHERE UBICACION = '" + (ocupadas+1+i)+"'";
			sql += " AND ID_LOCALIDAD = " + boleta.getLocalidad().getId();
			sql += " AND ID_FUNCION = " + boleta.getFuncion().getId();

			System.out.println("SQL stmt:" + sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();	
			boleta.setUbicacion(ocupadas+1+i);
			boletas.add(darBoleta(boleta));
		}
		return boletas;
	}

	public boolean boletaNumeradaDisponible(Boleta boleta) throws SQLException, Exception 
	{
		boolean disponible = false;
		String sql1 = "SELECT * FROM BOLETA WHERE UBICACION ='" + boleta.getUbicacion()+"'";
		sql1	+= " AND ID_LOCALIDAD = " + boleta.getLocalidad().getId();
		sql1	+= " AND ID_FUNCION = " + boleta.getFuncion().getId();

		System.out.println("SQL stmt:" + sql1);

		PreparedStatement prepStmt = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if (rs.next()) 
			if(rs.getInt("ESTADO")==(Boleta.DISPONIBLE))
				disponible=true;
		return disponible;
	}

	public int ocupadasBoletaNoNumeradas(Boleta boleta) throws SQLException, Exception
	{
		int ocupadas = -1;

		String sql = "SELECT COUNT(*) FROM BOLETA WHERE ID_LOCALIDAD = " + boleta.getLocalidad().getId();
		sql +=" AND ID_FUNCION = " + boleta.getFuncion().getId();
		sql +=" AND (ESTADO = " + Boleta.NO_DISPONIBLE+" OR ESTADO = " + Boleta.ABONADA+")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if (rs.next()) 
			ocupadas=rs.getInt("COUNT(*)");

		return ocupadas;
	}

	public Boleta darBoleta (Boleta boleta)  throws SQLException, Exception
	{
		Boleta resultado = null;

		String sql = "SELECT * FROM BOLETA WHERE UBICACION =" + boleta.getUbicacion();
		sql	+= " AND ID_LOCALIDAD = " + boleta.getLocalidad().getId();
		sql	+= " AND ID_FUNCION = " + boleta.getFuncion().getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			int ubicacion = rs.getInt("UBICACION");
			int estado = rs.getInt("ESTADO");
			double costo = rs.getDouble("COSTO");

			String sql1 = "SELECT * FROM FUNCION WHERE ID =" + boleta.getFuncion().getId();


			PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
			recursos.add(prepStmt1);
			ResultSet rs1 = prepStmt1.executeQuery();
			if(rs1.next())
			{
				Funcion nueva = new Funcion(rs.getInt("ID_FUNCION"), rs1.getTimestamp("FECHA_HORA"), rs1.getDouble("COSTO"), rs1.getInt("SILLAS_OCUPADAS"), rs1.getInt("YA_SE_REALIZO"), null, null);
				Localidad localidad = null;
				Funcion funcion = nueva;
				Cliente cliente = null;
				resultado = new Boleta(ubicacion, estado, costo, localidad, funcion, cliente);
			}
		}
		return resultado;
	}


	public Cliente darCliente (int id)  throws SQLException, Exception
	{
		Cliente resultado = null;

		String sql = "SELECT * FROM USUARIO WHERE ID ="+id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			String mail = rs.getString("MAIL");
			String rol = rs.getString("ROL");

			String sql1 = "SELECT * FROM CLIENTE WHERE ID ="+id;

			PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
			recursos.add(prepStmt1);
			ResultSet rs1 = prepStmt1.executeQuery();
			if(rs1.next())
			{
				String contrasena = rs1.getString("CONTRASENA");
				int idFest = rs1.getInt("ID_FESTIVAL");
				Festival nuevoFest = null;
				String sql2 = "SELECT * FROM FESTIVAL WHERE ID ="+idFest;

				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				ResultSet rs2 = prepStmt2.executeQuery();
				
				if(rs2.next())
				{
					String nameFest = rs2.getString("NAME");
					int anioFest = rs2.getInt("ANIO");
					String paisFest = rs2.getString("PAIS");
					Date fechaIniFest = rs2.getDate("FECHAINICIO");
					Date fechaFinFest = rs2.getDate("FECHAFIN");
					nuevoFest = new Festival(idFest, nameFest, anioFest, paisFest, fechaIniFest, fechaFinFest, null, null, null);
				}
				
				resultado = new Cliente(id, nombre, mail, rol, contrasena, nuevoFest);
			}
		}
		return resultado;
	}
	
	public ArrayList<Cliente> darClientes()  throws SQLException, Exception
	{
		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		String sql = "SELECT * FROM CLIENTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Cliente resultado = null;
			int id = rs.getInt("ID");
			String contrasena = rs.getString("CONTRASENA");
			
			String sql1 = "SELECT * FROM USUARIO WHERE ID ="+id;

			PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
			recursos.add(prepStmt1);
			ResultSet rs1 = prepStmt1.executeQuery();
			if(rs1.next())
			{
				String nombre = rs1.getString("NOMBRE");
				String mail = rs1.getString("MAIL");
				String rol = rs1.getString("ROL");
				
				resultado = new Cliente(id, nombre, mail, rol, contrasena, null);
				listaClientes.add(resultado);
			}
		}
		return listaClientes;
	}

	public void devolverBoleta(Boleta pBoleta) throws SQLException, Exception
	{


		String sql = "UPDATE BOLETA SET ESTADO = 3";
		sql += " WHERE UBICACION ='" + pBoleta.getUbicacion()+"'";
		sql += " AND ID_LOCALIDAD = " + pBoleta.getLocalidad().getId();
		sql += " AND ID_FUNCION = " + pBoleta.getFuncion().getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();


	}

//	public void devolverAbonamiento(Boleta pBoleta) throws SQLException, Exception
//	{
//
//
//		String sql = "UPDATE BOLETA SET ESTADO = 3";
//		sql += " WHERE UBICACION ='" + pBoleta.getUbicacion()+"'";
//		sql += " AND ID_LOCALIDAD = " + pBoleta.getLocalidad().getId();
//		sql += " AND ID_FUNCION = " + pBoleta.getFuncion().getId();
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();
//
//
//	}

	public ListaBoletas darBoletasCliente (int id) throws SQLException, Exception
	{
		ArrayList<Boleta> boletas = new ArrayList<>();

		String sql = "SELECT * FROM BOLETA WHERE ID_CLIENTE =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int idLocalidad = rs.getInt("ID_LOCALIDAD");
			int idFuncion = rs.getInt("ID_FUNCION");
			int ubicacion = rs.getInt("UBICACION");
			int estado = rs.getInt("ESTADO");
			double costo = rs.getDouble("COSTO");

			Localidad localidad =  new Localidad(idLocalidad, 0, 0, "", null, null);
			Funcion funcion = new Funcion(idFuncion, null, 0, 0, 0, null, null);
			Boleta bol = new Boleta(ubicacion, estado, costo, localidad, funcion, null); 
			
			boletas.add(bol);
		}

		return new ListaBoletas(boletas);

	}
	
	public ArrayList<Boleta> darTodasBoletas() throws SQLException, Exception
	{
		ArrayList<Boleta> boletas = new ArrayList<>();

		String sql = "SELECT * FROM BOLETA ORDER BY ID_CLIENTE";

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
			Funcion funcion = new Funcion(idFuncion, null, 0, 0, 0, null, null);
			Boleta bol = new Boleta(ubicacion, estado, costo, localidad, funcion, darCliente(idCliente)); 
			
			boletas.add(bol);
		}
		return boletas;
	}
}
