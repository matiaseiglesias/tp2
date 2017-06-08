package algoBall;

public class Jugador 
{
	private String nombre;
	private Agrupacion agrupacion;
	
	public Jugador(String Nombre, String equipo)
	{
		this.nombre = Nombre;
		this.agrupacion = new Agrupacion(equipo);
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public Agrupacion getEquipo()
	{
		return this.agrupacion;
	}
	
	public void agregarPersonaje(Personaje personaje){
		this.agrupacion.agregarPersonaje(personaje);
	}
	
	public boolean existePersonaje(String personaje){
		return (this.agrupacion.existePersonaje(personaje));
	}
	
	public Agrupacion getAgrupacion(){
		return this.agrupacion;
	}
	
	public void moverIzquierda(String nombrePersonaje){
		this.agrupacion.moverIzquierda(nombrePersonaje);
	}
	public void moverAbajo(String nombrePersonaje){
		this.agrupacion.moverAbajo(nombrePersonaje);
	}
	public void moverDerecha(String nombrePersonaje){
		this.agrupacion.moverDerecha(nombrePersonaje);
	}
	public void moverArriba(String nombrePersonaje){
		this.agrupacion.moverArriba(nombrePersonaje);
	}
}