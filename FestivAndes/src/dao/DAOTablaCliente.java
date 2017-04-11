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

	public Boleta comprarBoletaNumerada(Boleta boleta, int idUsuario) throws SQLException, Exception {

		if(boletaNumeradaDisponible(boleta))
		{
			String sql = "UPDATE BOLETA ";
			sql += " SET ID_CLIENTE = " + idUsuario;
			sql += ", ESTADO = " + Boleta.NO_DISPONIBLE;
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

	public ArrayList<Boleta> comprarBoletasNoNumeradas(Boleta boleta, int idCliente, int necesarias, int ocupadas) throws SQLException, Exception {

		ArrayList<Boleta> boletas = new ArrayList<>();

		for(int i=0; i < necesarias;i++)
		{
			String sql = "UPDATE BOLETA ";
			sql += " SET ID_CLIENTE = "+ idCliente;
			sql += ", ESTADO = " + Boleta.NO_DISPONIBLE;
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
		sql +=" AND ESTADO = " + Boleta.NO_DISPONIBLE;

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

		String sql = "SELECT * FROM BOLETA WHERE UBICACION ='" + boleta.getUbicacion()+"'";
		sql	+= " AND ID_LOCALIDAD = " + boleta.getLocalidad().getId();
		sql	+= " AND ID_FUNCION = " + boleta.getFuncion().getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			int ubicacion = rs.getInt("UBICACION");
			int estado = rs.getInt("ESTADO");
			double costo = rs.getDouble("COSTO");
			Localidad localidad = null;
			Funcion funcion = null;
			Cliente cliente = null;
			resultado = new Boleta(ubicacion, estado, costo, localidad, funcion, cliente);
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
				Festival festival = null;
				resultado = new Cliente(festival, id, nombre, mail, rol, contrasena);
			}
		}
		return resultado;
	}
	////////////////////////////////////////RF8///////////////////////////////////////////
	//	public ArrayList<Reserva> darReservas() throws SQLException, Exception {
	//		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
	//
	//		String sql = "SELECT * FROM RESERVA";
	//
	//		PreparedStatement prepStmt = conn.prepareStatement(sql);
	//		recursos.add(prepStmt);
	//		ResultSet rs = prepStmt.executeQuery();
	//
	//		ArrayList<Silla> listaSillas;
	//		int idAntes = -1;
	//		while (rs.next()) {
	//			listaSillas = new ArrayList<Silla>();
	//			int id = Integer.parseInt(rs.getString("ID"));
	//			int idFun = Integer.parseInt(rs.getString("ID_FUNCION"));
	//			int idClien = Integer.parseInt(rs.getString("ID_CLIENTE"));
	//			int valor = Integer.parseInt(rs.getString("VALOR_A_PAGAR"));
	//			int cantidad = Integer.parseInt(rs.getString("CANTIDAD_PERSONAS"));
	//			int pagada = Integer.parseInt(rs.getString("PAGADA"));
	//
	//			String sql1 = "SELECT * FROM SILLA S WHERE S.ID_RESERVA = " + id;
	//
	//			PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
	//			recursos.add(prepStmt1);
	//			ResultSet rs1 = prepStmt1.executeQuery();
	//			while(rs1.next())
	//			{
	//				int idSilla = Integer.parseInt(rs1.getString("ID"));
	//				int idLocal = Integer.parseInt(rs1.getString("ID_LOCALIDAD"));
	//				int fila = Integer.parseInt(rs1.getString("FILA"));
	//				int columna = Integer.parseInt(rs1.getString("COLUMNA"));
	//
	//				String sql2 = "SELECT * FROM LOCALIDAD L WHERE L.ID = " + idLocal;
	//
	//				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
	//				recursos.add(prepStmt2);
	//				ResultSet rs2 = prepStmt2.executeQuery();
	//				Localidad localSilla = null;;
	//				while(rs2.next())
	//				{
	//					int idLocalidad = Integer.parseInt(rs2.getString("ID"));
	//					int idSitio = Integer.parseInt(rs2.getString("ID_SITIO"));
	//					String nombre = rs2.getString("NOMBRE");
	//					int LocalCapaci = Integer.parseInt(rs2.getString("CAPACIDAD"));
	//					int numerada = Integer.parseInt(rs2.getString("NUMERADA"));
	//
	//					String sql3 = "SELECT * FROM SITIO H WHERE H.ID = " + idSitio;
	//
	//					PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
	//					recursos.add(prepStmt3);
	//					ResultSet rs3 = prepStmt3.executeQuery();
	//					
	//					Sitio nuevoSitio = null;
	//					while(rs3.next())
	//					{
	//						int idSitio1 = Integer.parseInt(rs3.getString("ID"));
	//						String direccion = rs3.getString("DIRECCION");
	//						int capacidadSitio = Integer.parseInt(rs3.getString("CAPACIDAD"));
	//						int lugarAbierto = Integer.parseInt(rs3.getString("ID"));
	//						int AptoInca = Integer.parseInt(rs3.getString("CAPACIDAD"));
	//						java.sql.Timestamp iniHora = rs3.getTimestamp("INICIO_HORAIO");
	//						java.sql.Timestamp finHora = rs3.getTimestamp("FIN_HORAIO");
	//						int protec = Integer.parseInt(rs3.getString("PROTECCION_LLUVIA"));
	//						int efectAt = Integer.parseInt(rs3.getString("EFECTOS_ATMOSFERICOS"));
	//						nuevoSitio = new Sitio(idSitio1, direccion, lugarAbierto, capacidadSitio, AptoInca, iniHora, finHora, protec, efectAt);
	//					}
	//					localSilla =  new Localidad(idLocalidad, LocalCapaci, numerada, nombre, null, nuevoSitio);
	//				}
	//				listaSillas.add(new Silla(id, fila, columna, localSilla));
	//			}
	//			String sql4 = "SELECT * FROM FUNCION F WHERE F.ID = " + idFun;
	//
	//			PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
	//			recursos.add(prepStmt4);
	//			ResultSet rs4 = prepStmt4.executeQuery();
	//			
	//			while(rs4.next())
	//			{
	//				int idFuncion = Integer.parseInt(rs3.getString("ID"));
	//				String direccion = rs3.getString("DIRECCION");
	//				int capacidadSitio = Integer.parseInt(rs3.getString("CAPACIDAD"));
	//				int lugarAbierto = Integer.parseInt(rs3.getString("ID"));
	//				int AptoInca = Integer.parseInt(rs3.getString("CAPACIDAD"));
	//			}
	//			listaSillas nuevo = listaSillas;
	//			reservas.add(new Reserva(id, valor, cantidad, pagada, null, listaSillas));
	//		}
	//		return reservas;
	//	}
}
