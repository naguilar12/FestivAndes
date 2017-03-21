package vos;

import org.codehaus.jackson.annotate.JsonProperty;

import com.sun.jmx.snmp.Timestamp;

public class Sitio 
{
	
	@JsonProperty(value="id")
	private int id;


	@JsonProperty(value="nombre")
	private String nombre;


	@JsonProperty(value="direccion")
	private String direccion;
	
	
	@JsonProperty(value="abierto")
	private boolean abierto;
	
	@JsonProperty(value="capacidad")
	private int capacidad;


	@JsonProperty(value="aptoIncapacitados")
	private boolean aptoIncapacitados;


	@JsonProperty(value="inicioHorario")
	private Timestamp inicioHorario;
	
	public void ni()
	{
		inicioHorario = new Timestamp();
		System.out.println(inicioHorario.getDate());
	}
	
	public static void main(String[] args) {
		new Sitio().ni();
	}
	
	
	@JsonProperty(value="finHorario")
	private Timestamp finHorario;
	
}
