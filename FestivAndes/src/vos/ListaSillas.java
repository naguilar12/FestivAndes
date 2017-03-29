package vos;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaSillas {

	
	@JsonProperty(value="sillas")
	private ArrayList<Silla> sillas;
	
	public ListaSillas(@JsonProperty(value="sillas")ArrayList<Silla> sillas) {
		super();
		this.sillas = sillas;
	}

	public ArrayList<Silla> getSillas() {
		return sillas;
	}

	public void setSillas(ArrayList<Silla> sillas) {
		this.sillas = sillas;
	}
	
	
}
