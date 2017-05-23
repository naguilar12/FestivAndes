package dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Categoria;
import vos.CompaniaTeatro;
import vos.Espectaculo;
import vos.Funcion;
import vos.ListaCategorias;
import vos.ListaCompanias;
import vos.ListaEspectaculos;
import vos.ListaFestivales;
import vos.ListaRequerimientos;
import vos.Preferencia;
import vos.Representante;

public class DAOTablaCategorias {


	


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
			public DAOTablaCategorias() {
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

			public ArrayList<Categoria> darCategoriasEspectaculo(int idE) throws SQLException, Exception {
				ArrayList<Categoria> categorias= new ArrayList<Categoria>();

				String sql = "SELECT * FROM CATEGORIA_ESPECTACULO";
				sql += " WHERE ID_ESPECTACULO = " + idE;

				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				ResultSet rs = prepStmt.executeQuery();

				while (rs.next()) {
					String categoria = rs.getString("CATEGORIA");
					categorias.add(new Categoria(categoria, null));
				}
				return categorias;
			}
	
			public void nuevo()
			{
				
			}
			
}
