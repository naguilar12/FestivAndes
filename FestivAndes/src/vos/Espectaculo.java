package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Espectaculo {
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="duracion")
	private double duracion;	

	@JsonProperty(value="intermedio")
	private boolean intermedio;
	
	@JsonProperty(value="idioma")
	private String idioma;
	
	@JsonProperty(value="clasificacion")
	private String clasificacion;	
	
	@JsonProperty(value="costoRealizacion")
	private double costoRealizacion;
	
	@JsonProperty(value="publicoActivo")
	private boolean publicoActivo;
	
	@JsonProperty(value="traduccionSubtitulos")
	private boolean traduccionSubtitulos;
	
	@JsonProperty(value="traduccionAudifonos")
	private boolean traduccionAudifonos;
	
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	@JsonProperty(value="publicoObjetivo")
	private String publicoObjetivo;
	
	@JsonProperty(value="compañias")
	private ListaCompañias compañias;

	public Espectaculo(@JsonProperty(value="id")int id, @JsonProperty(value="nombre")String nombre, @JsonProperty(value="duracion")double duracion, @JsonProperty(value="intermedio")boolean intermedio, @JsonProperty(value="idioma")String idioma, @JsonProperty(value="clasificacion")String clasificacion,
			@JsonProperty(value="costoRealizacion")double costoRealizacion, @JsonProperty(value="publicoActivo")boolean publicoActivo, @JsonProperty(value="traduccionSubtitulos")boolean traduccionSubtitulos, @JsonProperty(value="traduccionAudifonos")boolean traduccionAudifonos,
			@JsonProperty(value="descripcion")String descripcion, @JsonProperty(value="publicoObjetivo")String publicoObjetivo, @JsonProperty(value="compañias")ListaCompañias compañias) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.duracion = duracion;
		this.intermedio = intermedio;
		this.idioma = idioma;
		this.clasificacion = clasificacion;
		this.costoRealizacion = costoRealizacion;
		this.publicoActivo = publicoActivo;
		this.traduccionSubtitulos = traduccionSubtitulos;
		this.traduccionAudifonos = traduccionAudifonos;
		this.descripcion = descripcion;
		this.publicoObjetivo = publicoObjetivo;
		this.compañias = compañias;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}

	public boolean isIntermedio() {
		return intermedio;
	}

	public void setIntermedio(boolean intermedio) {
		this.intermedio = intermedio;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public double getCostoRealizacion() {
		return costoRealizacion;
	}

	public void setCostoRealizacion(double costoRealizacion) {
		this.costoRealizacion = costoRealizacion;
	}

	public boolean isPublicoActivo() {
		return publicoActivo;
	}

	public void setPublicoActivo(boolean publicoActivo) {
		this.publicoActivo = publicoActivo;
	}

	public boolean isTraduccionSubtitulos() {
		return traduccionSubtitulos;
	}

	public void setTraduccionSubtitulos(boolean traduccionSubtitulos) {
		this.traduccionSubtitulos = traduccionSubtitulos;
	}

	public boolean isTraduccionAudifonos() {
		return traduccionAudifonos;
	}

	public void setTraduccionAudifonos(boolean traduccionAudifonos) {
		this.traduccionAudifonos = traduccionAudifonos;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPublicoObjetivo() {
		return publicoObjetivo;
	}

	public void setPublicoObjetivo(String publicoObjetivo) {
		this.publicoObjetivo = publicoObjetivo;
	}

	public ListaCompañias getCompañias() {
		return compañias;
	}

	public void setCompañias(ListaCompañias compañias) {
		this.compañias = compañias;
	}
	

}
