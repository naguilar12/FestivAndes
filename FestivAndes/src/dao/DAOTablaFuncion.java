package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import vos.Espectaculo;
import vos.Funcion;

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
		
		public Funcion realizarFuncion(int idE, int idF) throws SQLException, Exception {

			String sql = "UPDATE FUNCION SET ";
			sql += "ya_se_realizo='" + "1";
			sql += " WHERE ID = " + idF;
			sql += " WHERE ID_ESPECTACULOS = " + idF;

			System.out.println("SQL stmt:" + sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			
			return darFuncion(idF);
		}
		
		public Funcion darFuncion(int idF) throws SQLException, Exception {
			Funcion funcion = null;

			String sql = "SELECT * FROM FUNCION WHERE ID=" + idF;

			System.out.println("SQL stmt:" + sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				
				int id = Integer.parseInt(rs.getString("id"));
				Date fechaHora =  rs.getDate("fecha_hora");
				double costo = Double.parseDouble(rs.getString("costo"));
				int sillasreservadas = Integer.parseInt(rs.getString("sillas_reservadas"));
				int  realizada = Integer.parseInt(rs.getString("ya_se_realizo"));
				Espectaculo espectaculo = null;
				funcion =  new Funcion(id, (java.sql.Date) fechaHora, costo, sillasreservadas, realizada, espectaculo);
				
			}
			return funcion;
		}
		

}
