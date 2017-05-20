package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaBoletas {

	@JsonProperty(value="boletas")
	private List<Boleta> boletas;

	public ListaBoletas(@JsonProperty(value="boletas")List<Boleta> boletas) {
		super();
		this.boletas = boletas;
	}

	public List<Boleta> getBoletas() {
		return boletas;
	}

	public void setBoletas(List<Boleta> boletas) {
		this.boletas = boletas;
	}
}
