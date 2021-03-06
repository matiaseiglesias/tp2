package model.tablero;

import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import model.algoBall.Posicionable;
import model.consumibles.Consumible;
import model.exceptions.FueraDelTableroException;

public class Tablero 
{
	private int dimension;
	private Casillero tableroDeCasilleros[][];
	private List<Posicionable> posiblesConsumibles;
	private Hashtable<Posicion,Consumible> consumiblesActuales = new Hashtable<Posicion,Consumible>();
	private static Tablero INSTANCE = null;
	
	public static Tablero createInstance(int dimension){
		INSTANCE = new Tablero(dimension);
		return INSTANCE;
	}
	
	public static Tablero getInstance(){
		return INSTANCE;
	}
	private Tablero(int dimension)
	{
		this.dimension = dimension;
	    tableroDeCasilleros = new Casillero[dimension][dimension];
	    
	    for(int i = 0; i < dimension; i++)
            for(int j=0; j < dimension; j++)
                tableroDeCasilleros[i][j] = new Casillero();
	}
	
	public void setConsumibles(List<Posicionable> consumibles){
		this.posiblesConsumibles = consumibles;
	}
	
	public void aparecerConsumible(){
		if (this.debeAparecerConsumibleEsteTurno()){
			int randomNum = ThreadLocalRandom.current().nextInt(0, posiblesConsumibles.size());
			Posicionable consumible = posiblesConsumibles.get(randomNum);
			int randomFila = ThreadLocalRandom.current().nextInt(0, dimension);
			int randomColumna = ThreadLocalRandom.current().nextInt(0, dimension);
			if (tableroDeCasilleros[randomFila][randomColumna].estaVacio()){
				tableroDeCasilleros[randomFila][randomColumna].agregarConsumible(consumible);
				consumiblesActuales.put(new Posicion(randomFila,randomColumna),(Consumible)consumible);
			}
		}
		
	}
	
	private boolean debeAparecerConsumibleEsteTurno(){
		int randomNum = ThreadLocalRandom.current().nextInt(0, 10);
		return (randomNum==0);//10% de posibilidad de que deba aparecer
	}
	public Casillero getCasillero(Posicion pos)
	{
		try{
			return (this.tableroDeCasilleros[pos.getFila()][pos.getColumna()]);
		}
		catch (IndexOutOfBoundsException error){
			throw new FueraDelTableroException();
		}
	}	

	public int getDimension()
	{
		return dimension;
	}
	
	public void agregarPosicionable(Posicionable personaje, Posicion posicion)
	{
		Casillero casillero = this.getCasillero(posicion);
		casillero.ocupar(personaje);
		posicion.setCasillero(casillero);
		personaje.setPosicion(posicion);
	}
	
	public Hashtable<Posicion,Consumible> getPosicionesYConsumibles(){
		Hashtable<Posicion,Consumible> consumibles= new Hashtable<Posicion,Consumible>();
		for(int i = 0; i < dimension; i++){
            for(int j=0; j < dimension; j++){
                Posicionable consumible = tableroDeCasilleros[i][j].getConsumible();
                if (consumible!=null){
                	consumibles.put(new Posicion(i,j), (Consumible) consumible);
                }
            }
		}
		return consumibles;
		
	}
	
}

