package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaCompañias {

	@JsonProperty(value="compañias")
	private List<CompañiaTeatro> compañias;

	public ListaCompañias(@JsonProperty(value="compañias")List<CompañiaTeatro> compañias) {
		super();
		this.compañias = compañias;
	}

	public List<CompañiaTeatro> getCompañias() {
		return compañias;
	}

	public void setCompañias(List<CompañiaTeatro> compañias) {
		this.compañias = compañias;
	}
}
