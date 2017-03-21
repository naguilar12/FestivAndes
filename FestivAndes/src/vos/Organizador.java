package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Organizador extends Usuario
{

	public Organizador(@JsonProperty(value="id")int id, @JsonProperty(value="nombre")String nombre,@JsonProperty(value="mail") String mail,@JsonProperty(value="rol") String rol) {
		super(id, nombre, mail, rol);
		// TODO Auto-generated constructor stub
	}

}
