package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import oracle.sql.DATE;
import vos.Cliente;
import vos.Festival;
import vos.FiltroConsultaCompraBoletas;
import vos.ListaClientes;
import vos.ListaCompanias;
import vos.ListaOrganizadores;
import vos.Organizador;
import vos.Requerimiento;
import vos.RespuestaConsultaCompraBoletas;

public class DAOTablaOrganizador {

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
	public DAOTablaOrganizador() 
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

	public Organizador darOrganizador(int id)  throws SQLException, Exception
	{
		Organizador resultado = null;

		String sql = "SELECT * FROM USUARIO WHERE ID ="+id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			String mail = rs.getString("MAIL");
			String rol = rs.getString("ROL");

			String sql1 = "SELECT * FROM ORGANIZADOR WHERE ID ="+id;

			PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
			recursos.add(prepStmt1);
			ResultSet rs1 = prepStmt1.executeQuery();
			if(rs1.next())
			{
				ListaCompanias listComp = null;
				ListaOrganizadores listOrg = null;
				ListaClientes listCli = null;
				Festival fest = new Festival(Integer.parseInt(rs1.getString("ID_FESTIVAL")), "", 0, "", new java.util.Date(), new java.util.Date(), listComp, listOrg, listCli);
				resultado = new Organizador(id, nombre, mail, rol, fest);
			}
		}
		return resultado;
	}

	public ArrayList<RespuestaConsultaCompraBoletas> consultarCompraBoletas(FiltroConsultaCompraBoletas filtro) throws Exception
	{
		ArrayList<RespuestaConsultaCompraBoletas> respuesta = new ArrayList<RespuestaConsultaCompraBoletas>();

		String sql = "WITH COMPRADAS AS (SELECT * FROM (BOLETA INNER JOIN FUNCION ON ID_FUNCION = ID) WHERE ESTADO IN (1,2)),"
				+ "FILTRO_ELEMENTOS AS (SELECT * FROM (COMPRADAS C INNER JOIN REQUERIMIENTOS_SITIO RS ON C.ID_SITIO = RS.ID_SITIO )WHERE " ;
		if(filtro.getRequerimiento()== null)
			sql+="1 = 1";
		else
			sql+= "REQUERIMIENTOS = "+"'" + filtro.getRequerimiento()+"'";
		sql += "), ";



		sql+= " FILTRO_LOCALIDAD AS (SELECT * FROM (FILTRO_ELEMENTOS FE INNER JOIN LOCALIDAD L ON FE.ID_LOCALIDAD = L.ID ) WHERE ";
		if(filtro.getTipoLocalidad()== null)
			sql+=" 1 = 1 ";
		else
			sql+= " NOMBRE = "+"'" + filtro.getTipoLocalidad()+"'";
		sql += "), ";



		sql += " FILTRO_RANGO_FECHA AS (SELECT * FROM FILTRO_LOCALIDAD ";
		if(filtro.getFechaInicio()!= null && filtro.getFechaFin()!=null)
			sql+= " WHERE FECHA_HORA BETWEEN  '" + filtro.getFechaInicio()+"' AND '" + filtro.getFechaFin()+"' " ;
		sql += "), ";


		sql+=" FILTRO_FRANJA_HORARIA AS (SELECT * FROM FILTRO_RANGO_FECHA ";
		if(filtro.getHoraInicio() < filtro.getHoraFin() &&  0 <=filtro.getHoraInicio() && 24>=filtro.getHoraFin())
			sql+=" where to_char(FECHA_HORA,'HH24') BETWEEN '"+filtro.getHoraInicio()+"' AND '"+filtro.getHoraFin()+"'";
		sql += "), ";


		sql +=" FILTRO_DIA AS (SELECT * FROM FILTRO_FRANJA_HORARIA";
		if(filtro.getDia()!=null)		
			sql +=" WHERE REPLACE(to_char(FECHA_HORA,'Day'), ' ', '') = '"+filtro.getDia()+"'";
		sql += "), ";


		sql+=" UNION_SITIO AS (SELECT * FROM (FILTRO_DIA INNER JOIN SITIO ON ID_SITIO = ID)), "
				+" NOMBRE_ESPECTACULO AS (SELECT * FROM(UNION_SITIO FD INNER JOIN (SELECT ID, NOMBRE AS NOMBRE_ESPECTACULO FROM ESPECTACULO) E ON  FD.ID_ESPECTACULO = E.ID)), "
				+" BOLETAS_CONTADAS AS (SELECT * FROM (NOMBRE_ESPECTACULO NE INNER JOIN (SELECT ID_FUNCION AS FUNCION_ID ,COUNT(ID_FUNCION) AS BOLETAS_VENDIDAS FROM NOMBRE_ESPECTACULO GROUP BY ID_FUNCION)  A ON NE.ID_FUNCION = A.FUNCION_ID )), "
				+" CLIENTES_CONTADOS AS (SELECT * FROM (BOLETAS_CONTADAS NE INNER JOIN (SELECT FUNCION_ID,COUNT(FUNCION_ID) AS CLIENTES_REGISTRADOS FROM BOLETAS_CONTADAS WHERE ID_CLIENTE != 99 GROUP BY FUNCION_ID) A ON NE.ID_FUNCION = A.FUNCION_ID ))";

		sql += "SELECT DISTINCT ID_FUNCION, NOMBRE_ESPECTACULO, NOMBRE_SITIO,CAST(FECHA_HORA AS DATE) AS FECHA_FUNCION, BOLETAS_VENDIDAS, CLIENTES_REGISTRADOS FROM CLIENTES_CONTADOS";

		System.out.println("SQL stmt:" + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next())
		{
			String espectaculo = rs.getString("NOMBRE_ESPECTACULO");
			String fecha = rs.getString("FECHA_FUNCION");
			int idFuncion = rs.getInt("ID_FUNCION");
			String sitio = rs.getString("NOMBRE_SITIO");
			int boletasVendidas = rs.getInt("BOLETAS_VENDIDAS");
			int usuariosRegistrados = rs.getInt("CLIENTES_REGISTRADOS");
			RespuestaConsultaCompraBoletas res = new RespuestaConsultaCompraBoletas(idFuncion,espectaculo, fecha, sitio, boletasVendidas, usuariosRegistrados);
			respuesta.add(res);
		}

		return respuesta;
	}

	public ArrayList<Cliente> consultarBuenosClientes(int n) throws Exception{
		ArrayList<Cliente> resultado = new ArrayList<>();

		String sql = " WITH BOLETA_LOCALIDAD AS (SELECT * FROM(BOLETA INNER JOIN LOCALIDAD ON ID_LOCALIDAD=ID)), "
				+" BOLETAS_VIP AS(SELECT ID_CLIENTE, COUNT(*) AS BOLETAS_COMPRADAS FROM BOLETA_LOCALIDAD WHERE NOMBRE = 'VIP' AND ESTADO IN (1,2) GROUP BY ID_CLIENTE), "
				+" BOLETAS_POR_CLIENTE AS (SELECT ID_CLIENTE, COUNT(*) AS BOLETAS_COMPRADAS FROM BOLETA GROUP BY ID_CLIENTE), "
				+" BUENOS_CLIENTES AS (SELECT * FROM (BOLETAS_VIP NATURAL JOIN BOLETAS_POR_CLIENTE) WHERE ID_CLIENTE != 99 AND BOLETAS_COMPRADAS >= "+n+"), "
				+" INFO_BUENOS_USUARIOS AS (SELECT* FROM(BUENOS_CLIENTES INNER JOIN USUARIO ON ID_CLIENTE = ID)), "
				+" INFO_BUENOS_CLIENTES AS (SELECT * FROM (INFO_BUENOS_USUARIOS INNER JOIN CLIENTE ON CLIENTE.ID = ID_CLIENTE)) ";

		sql += " SELECT * FROM INFO_BUENOS_CLIENTES ";

		System.out.println("SQL stmt:" + sql);
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
			Festival festival = null;

			Cliente cliente = new Cliente(id, nombre, mail, rol, contrasena, festival);
			resultado.add(cliente);
		}

		return resultado;
	}
}
