package rest;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import vos.Cliente;
import vos.ConsultaCompania;
import vos.ConsultaAsistencia;
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

		@PUT
		@Path("{idC}/consultarAsistenciaAlFestival")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response consultarAsistenciaAlFestival(ConsultaAsistencia info, @javax.ws.rs.PathParam("idC") int idC) {
			FestivAndesMaster tm = new FestivAndesMaster(getPath());
			ArrayList<Cliente> resultado;
			try {
				resultado = tm.asistUsuariosFest(idC, info.getFechaInicio(), info.getFechaFin(), info.getCriterio(), info.getAgrupamiento());
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(resultado).build();
		}
		
		@PUT
		@Path("{idC}/consultarNoAsistenciaAlFestival")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response consultarNoAsistenciaAlFestival(ConsultaAsistencia info, @javax.ws.rs.PathParam("idC") int idC) {
			FestivAndesMaster tm = new FestivAndesMaster(getPath());
			ArrayList<Cliente> resultado;
			System.out.println(info.getAgrupamiento());
			try {
				resultado = tm.asistNoUsuariosFest(idC, info.getFechaInicio(), info.getFechaFin(), info.getCriterio(), info.getAgrupamiento());
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(resultado).build();
		}
		
		@POST
		@Path("/rentabilidad/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response getRentabilidad(Rentabilidad rent, @javax.ws.rs.PathParam("id")Long idCompania ) {
			FestivAndesMaster tm = new FestivAndesMaster(getPath());
			ListaRentabilidad rentabilidades;
			try {
				//rentabilidades = tm.darRentabilidad(rent);
				rentabilidades = new ListaRentabilidad(tm.darRentabilidadCompania(rent, idCompania));
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(rentabilidades).build();
		}
}
