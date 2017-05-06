package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Fechas {
	
	@JsonProperty(value="fechaIncio")
	private Date fechaInicio;
	
	@JsonProperty(value="fechaFin")
	private Date fechaFin;

	public Fechas(@JsonProperty(value="fechaIncio") Date fechaInicio, @JsonProperty(value="fechaFin") Date fechaFin) {
		super();
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	

}
