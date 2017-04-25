package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import vos.Boleta;
import vos.Compa�iaTeatro;
import vos.ConsultaCompania;
import vos.ListaCompa�ias;
import vos.ListaConsultaCompania;


	@Path("companias")
	public class FestivAndesCompaniaServices {

		
		/**
		 * Atributo que usa la anotación @Context para tener el ServletContext de la conexión actual.
		 */
		@Context
		private ServletContext context;

		/**
		 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
		 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
		 */
		private String getPath() {
			return context.getRealPath("WEB-INF/ConnectionData");
		}
		
		
		private String doErrorMessage(Exception e){
			return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
		}
		
		
		@GET
		@Produces({MediaType.APPLICATION_JSON})
		public Response darCompanias(){
			System.out.println("compa");
			FestivAndesMaster tm = new FestivAndesMaster(getPath());
			ListaConsultaCompania companias;
			try{
				companias = tm.darInfoCompanias();	
				

			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(companias).build();

		}
		
		@GET
		@Path ("{id}")
		@Produces({MediaType.APPLICATION_JSON})
		public Response darCompaniasId(@javax.ws.rs.PathParam("id") int id){
			
			FestivAndesMaster tm = new FestivAndesMaster(getPath());
			ConsultaCompania resultado = null;
			try{
				resultado = tm.darInfoCompaniasId(id);					

			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(resultado).build();

		}

}
