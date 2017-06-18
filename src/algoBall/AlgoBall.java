package algoBall;

import static algoBall.ConstantesDelJuego.CELL_FIL;
import static algoBall.ConstantesDelJuego.CELL_COL;
import static algoBall.ConstantesDelJuego.TAMANIO_TABLERO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static algoBall.ConstantesDelJuego.NOMBRE_ENEMIGOS;
import static algoBall.ConstantesDelJuego.NOMBRE_GUERREROS;
import static algoBall.ConstantesDelJuego.FREEZER_COL;
import static algoBall.ConstantesDelJuego.FREEZER_FIL;
import static algoBall.ConstantesDelJuego.GOHAN_COL;
import static algoBall.ConstantesDelJuego.GOHAN_FIL;
import static algoBall.ConstantesDelJuego.GOKU_COL;
import static algoBall.ConstantesDelJuego.GOKU_FIL;
import static algoBall.ConstantesDelJuego.MAJIN_BOO_COL;
import static algoBall.ConstantesDelJuego.MAJIN_BOO_FIL;
import static algoBall.ConstantesDelJuego.PICCOLO_COL;
import static algoBall.ConstantesDelJuego.PICCOLO_FIL;

import exceptions.EquipoInexistenteException;
import exceptions.PersonajeInexistenteException;
import personajes.Cell;
import personajes.Freezer;
import personajes.Gohan;
import personajes.Goku;
import personajes.MajinBoo;
import personajes.Personaje;
import personajes.Piccolo;
import tablero.Posicion;
import tablero.Tablero;

public class AlgoBall 
{
	private Turno turnoActual;
	private Tablero tablero;
	
	public AlgoBall(String nombre1, String nombre2)
	{
		this.tablero = new Tablero(TAMANIO_TABLERO);
		Jugador jugador1 = new Jugador(nombre1, NOMBRE_ENEMIGOS);
		Jugador jugador2 = new Jugador(nombre2, NOMBRE_GUERREROS);
		iniciarPersonajes(jugador1, jugador2);
		this.turnoActual = iniciarTurnos(jugador1, jugador2);
	}
	
	public Turno iniciarTurnos(Jugador jugador1, Jugador jugador2){
		Turno turnoJugador1 = new Turno(jugador1);
		Turno turnoJugador2 = new Turno(jugador2);
		turnoJugador1.setTurnoSiguiente(turnoJugador2);
		turnoJugador2.setTurnoSiguiente(turnoJugador1);
		List<Turno> lista = new ArrayList<Turno>();
		lista.add(turnoJugador1);
		lista.add(turnoJugador2);
		int randomNum = ThreadLocalRandom.current().nextInt(0, 1 + 1);
		return lista.get(randomNum);
	}
		
	public void finalizarTurno(){
		turnoActual.reestablecer();
		turnoActual = turnoActual.getTurnoSiguiente();
	}
	
	public Turno turnoSiguiente(){
		return turnoActual.getTurnoSiguiente();
	}
	
	
	public void terminarJuego(){
		@SuppressWarnings("unused")
		Jugador jugadorGanador = this.turnoActual.getJugador();
		/*mensaje final*/
	}
	
	public void iniciarPersonajes(Jugador jugador1, Jugador jugador2)
	{
		
		Personaje goku = new Goku();
		Personaje gohan = new Gohan();
		Personaje piccolo = new Piccolo();
		Personaje majinBoo = new MajinBoo();
		Personaje cell = new Cell();
		Personaje freezer = new Freezer();
		
		
		jugador1.agregarPersonaje(goku);
		jugador1.agregarPersonaje(gohan);
		jugador1.agregarPersonaje(piccolo);
		jugador2.agregarPersonaje(majinBoo);
		jugador2.agregarPersonaje(cell);
		jugador2.agregarPersonaje(freezer);
	
		tablero.agregarPosicionable(goku, new Posicion(GOKU_FIL,GOKU_COL));
		tablero.agregarPosicionable(gohan, new Posicion(GOHAN_FIL,GOHAN_COL));
		tablero.agregarPosicionable(piccolo, new Posicion(PICCOLO_FIL,PICCOLO_COL));
		tablero.agregarPosicionable(majinBoo,  new Posicion(MAJIN_BOO_FIL,MAJIN_BOO_COL));
		tablero.agregarPosicionable(cell, new Posicion(CELL_FIL,CELL_COL));
		tablero.agregarPosicionable(freezer, new Posicion(FREEZER_FIL,FREEZER_COL));
	}

	public int getCantidadDeAtaquesRestantes()
	{
		 return this.turnoActual.getCantidadDeAtaquesRestantes();
			
	}
	
	public int getDimensionTablero(){
		return tablero.getDimension();
	}

	public Personaje getPersonaje(String nombre) {
		Personaje personajeBuscado;
		try{
			personajeBuscado = this.turnoActual.getPersonaje(nombre);
		}
		catch (PersonajeInexistenteException error){
			personajeBuscado = this.turnoActual.getTurnoSiguiente().getPersonaje(nombre);
		}
		return personajeBuscado;
	}
	
	public Equipo getEquipoActual(){
		return this.turnoActual.getEquipo();
	}
	
	public Equipo getEquipoSiguiente(){
		return this.turnoSiguiente().getEquipo();
	}
	
	public Equipo getEquipo(String nombre) {
		Equipo equipoBuscado;
		try{
			equipoBuscado = this.turnoActual.getEquipo(nombre);
		}
		catch (EquipoInexistenteException error){
			equipoBuscado = this.turnoActual.getTurnoSiguiente().getEquipo(nombre);
		}
		return equipoBuscado;
	}
	
	public Equipo getGuerrerosZ(){
		return this.getEquipo(ConstantesDelJuego.NOMBRE_GUERREROS);
	}
	
	public Equipo getEnemigos(){
		return this.getEquipo(ConstantesDelJuego.NOMBRE_ENEMIGOS);
	}
	
	public Personaje getGoku(){
		return this.getPersonaje(ConstantesDelJuego.GOKU_NOMBRE);
	}
	
	public Personaje getGohan(){
		return this.getPersonaje(ConstantesDelJuego.GOHAN_NOMBRE);
	}

	public Personaje getPiccolo(){
		return this.getPersonaje(ConstantesDelJuego.PICCOLO_NOMBRE);
	}
	
	public Personaje getCell(){
		return this.getPersonaje(ConstantesDelJuego.CELL_NOMBRE);
	}

	public Personaje getFreezer(){
		return this.getPersonaje(ConstantesDelJuego.FREEZER_NOMBRE);
	}

	public Personaje getMajinBoo(){
		return this.getPersonaje(ConstantesDelJuego.MAJIN_BOO_NOMBRE);
	}

}