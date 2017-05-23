package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaCompanias {

	@JsonProperty(value="companias")
	private List<CompaniaTeatro> compañias;

	public ListaCompanias(@JsonProperty(value="companias")List<CompaniaTeatro> compañias) {
		super();
		this.compañias = compañias;
	}

	public List<CompaniaTeatro> getCompañias() {
		return compañias;
	}

	public void setCompañias(List<CompaniaTeatro> compañias) {
		this.compañias = compañias;
	}
}
