package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Cliente;
import vos.Festival;
import vos.Organizador;

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
					resultado = new Organizador(id, nombre, mail, rol);
				}
			}
			return resultado;
		}
}
