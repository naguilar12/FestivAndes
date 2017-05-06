package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultaAsistencia {
	
	@JsonProperty(value="fechaIncio")
	private Date fechaInicio;
	
	@JsonProperty(value="fechaFin")
	private Date fechaFin;
	
	@JsonProperty(value="criterio")
	private String criterio;

	public ConsultaAsistencia(@JsonProperty(value="fechaIncio") Date fechaInicio, @JsonProperty(value="fechaFin") Date fechaFin, @JsonProperty(value="criterio")String criterio) {
		super();
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.criterio = criterio;
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

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}
	
}
