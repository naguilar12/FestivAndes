package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class CompañiaTeatro {
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="nombre")
	private int nombre;
	
	@JsonProperty(value="fechaLlegada")
	private Date fechaLlegada;
	
	@JsonProperty(value="fechaSalida")
	private Date fechaSalida;

	@JsonProperty(value="festivales")
	private ListaFestivales festivales;

	public CompañiaTeatro(@JsonProperty(value="id")int id, @JsonProperty(value="nombre")int nombre, @JsonProperty(value="fechaLlegada")Date fechaLlegada, @JsonProperty(value="fechaSalida")Date fechaSalida) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaLlegada = fechaLlegada;
		this.fechaSalida = fechaSalida;
	}

	public CompañiaTeatro(@JsonProperty(value="id")int id, @JsonProperty(value="nombre")int nombre, @JsonProperty(value="fechaLlegada")Date fechaLlegada, @JsonProperty(value="fechaSalida")Date fechaSalida,@JsonProperty(value="festivales") ListaFestivales festivales) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaLlegada = fechaLlegada;
		this.fechaSalida = fechaSalida;
		this.festivales = festivales;
	}

	public ListaFestivales getFestivales() {
		return festivales;
	}

	public void setFestivales(ListaFestivales festivales) {
		this.festivales = festivales;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNombre() {
		return nombre;
	}

	public void setNombre(int nombre) {
		this.nombre = nombre;
	}

	public Date getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(Date fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	
	

}
