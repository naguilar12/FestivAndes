package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Compa�iaTeatro;
import vos.Espectaculo;
import vos.Funcion;
import vos.ListaCategorias;
import vos.ListaCompa�ias;
import vos.ListaEspectaculos;
import vos.ListaFestivales;
import vos.ListaRequerimientos;
import vos.Preferencia;
import vos.Representante;

public class DAOTablaCompa�ia {

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
		public DAOTablaCompa�ia() {
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

		public ArrayList<Compa�iaTeatro> darCompa�ias(int idE) throws SQLException, Exception {
			ArrayList<Compa�iaTeatro> compa�ias = new ArrayList<Compa�iaTeatro>();

			String sql = "SELECT * FROM COMPA�IA_ESPECTACULO";
			sql += " WHERE ID_ESPECTACULO = " + idE;

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				String sql1 = "SELECT * FROM COMPA�IA_TEATRO";
				sql1 += " WHERE ID = " + rs.getString("ID_COMPA�IA");
				
				PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
				recursos.add(prepStmt1);
				ResultSet rs1 = prepStmt1.executeQuery();
				
				while(rs1.next())
				{
					int id = Integer.parseInt(rs1.getString("ID"));
					String nombre = rs1.getString("NOMBRE");
					Date fechaLlegada =  rs1.getDate("FECHA_LLEGADA");
					Date fechaSalida =  rs1.getDate("FECHA_SALIDA");
					ListaFestivales festivales = null;
					Representante representante = null;
					ListaEspectaculos espectaculos = null;
					compa�ias.add(new Compa�iaTeatro(id, nombre, fechaLlegada, fechaSalida, festivales, representante, espectaculos));
				}
			}
			return compa�ias;
		}
}
