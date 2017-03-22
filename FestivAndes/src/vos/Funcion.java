package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Funcion {

	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="fechaHora")
	private Date fechaHora;
	
	@JsonProperty(value="costo")
	private double costo;
	
	@JsonProperty(value="sillasReservadas")
	private int sillasreservadas;
	
	@JsonProperty(value="realizada")
	private boolean realizada;
	
	@JsonProperty(value="espectaculo")
	private Espectaculo espectaculo;
	
	

	public Funcion(@JsonProperty(value="id")int id, @JsonProperty(value="fechaHora")Date fechaHora, @JsonProperty(value="costo")double costo, @JsonProperty(value="sillasReservadas")int sillasreservadas, @JsonProperty(value="realizada")boolean realizada,
			@JsonProperty(value="espectaculo")Espectaculo espectaculo) {
		super();
		this.id = id;
		this.fechaHora = fechaHora;
		this.costo = costo;
		this.sillasreservadas = sillasreservadas;
		this.realizada = realizada;
		this.espectaculo = espectaculo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public int getSillasreservadas() {
		return sillasreservadas;
	}

	public void setSillasreservadas(int sillasreservadas) {
		this.sillasreservadas = sillasreservadas;
	}

	public boolean isRealizada() {
		return realizada;
	}

	public void setRealizada(boolean realizada) {
		this.realizada = realizada;
	}

	public Espectaculo getEspectaculo() {
		return espectaculo;
	}

	public void setEspectaculo(Espectaculo espectaculo) {
		this.espectaculo = espectaculo;
	}
	
	
	
}
