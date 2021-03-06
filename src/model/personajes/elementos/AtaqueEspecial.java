package model.personajes.elementos;

public class AtaqueEspecial {
	protected Ki kiNecesario;
	private String nombre;
	private int porcentaje = 0;
	
	public AtaqueEspecial(String nombre,int ki){
		this.kiNecesario = new Ki(ki);
		this.nombre = nombre;
	}
	
	public void setPorcentaje(int porcentaje){
		this.porcentaje = porcentaje;
	}
	
	public int getPorcentaje( Ki kiPersonaje){
		kiPersonaje.restar(kiNecesario);
		return porcentaje;
	}
	
	public Ki getCosto(){
		return kiNecesario;
	}
	
	public String getNombre(){
		return nombre;
	}
	
}
