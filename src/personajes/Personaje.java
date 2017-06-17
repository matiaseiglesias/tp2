package personajes;

import algoBall.ConstantesDelJuego;
import algoBall.Equipo;
<<<<<<< HEAD
import algoBall.Posicionable;
import consumibles.Consumible;
import exceptions.CasilleroOcupadoException;
=======
>>>>>>> 725fe55233c63811d8188aa2745687d5fd216d19
import exceptions.FueraDeRangoException;
import exceptions.IntentandoAtacarAUnCompanieroException;
import exceptions.KiInsuficienteException;
import exceptions.PersonajeEnEstadoChocolate;
import exceptions.SeAcabaronTurnosDelEstadoException;
import exceptions.YaNoPuedeEvolucionarException;
import personajes.elementos.AtaqueEspecial;
import personajes.elementos.EstadoActividad;
import personajes.elementos.EstadoInactivoConChocolate;
import personajes.elementos.EstadoTransformacion;
import personajes.elementos.Ki;
import personajes.elementos.Salud;
import tablero.Posicion;


public abstract class Personaje implements Posicionable
{
	protected Posicion posicion;
	protected String nombre;
	protected Ki ki;
	protected Salud salud;
	protected EstadoActividad estadoTransformacionActual;
	protected Equipo equipo;
	protected AtaqueEspecial ataqueEspecial;
	protected String rutaImagen;
	
	public void setAtaqueEspecial(AtaqueEspecial ataqueEspecial)
	{
		this.ataqueEspecial = ataqueEspecial;
	}
	
	public void setPosicion(Posicion pos) 
	{
		this.posicion = pos;	
	}
	
	public void setEquipo(Equipo equipo){
		this.equipo = equipo;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public int getSalud() {
		return this.salud.getSalud();
	}
	
	public int getPorcentajeSalud(){
		return this.salud.getPorcentajeSalud();
	}
	
	public int getKiCantidad()
	{
		return ki.getKi();
	}
	
	public Ki getKi()
	{
		return this.ki;
	}
	
	public Posicion getPosicion()
	{
		return this.posicion;
	}
	
	public int getVelocidad()
	{
		return (this.estadoTransformacionActual.getVelocidad());
	}
	
	public int getPoderDePelea()
	{
		return this.estadoTransformacionActual.getPoderDePelea();
	}
	public int getDistanciaDeAtaque()
	{
		return this.estadoTransformacionActual.getDistanciaDeAtaque();
	}
	
	public void aumentarKi(int cantidad){
		this.estadoTransformacionActual.aplicarKi(this, cantidad);
	}
	
	public void transformar()
	{
		try{
			int velocidadAnterior = this.getVelocidad();
			this.estadoTransformacionActual.transformar(this);
			this.actualizarMovimientosRestantes(velocidadAnterior);
		}
		catch (YaNoPuedeEvolucionarException error){
			/*cancela evolucion (mas adelante agregar mensaje a usuario)*/
		}
		catch (KiInsuficienteException error){
			/*cancela evolucion (mas adelante agregar mensaje a usuario)*/
		}
	}
	
<<<<<<< HEAD
	private void mover(Posicion nuevaPosicion)
	{
		if (this.movimientosRestantes == 0){
			throw new NoQuedanMovimientosException();
		}
				
		try {
			Posicion posicion_anterior = this.posicion;
			nuevaPosicion.ponerEnTablero(this);
			posicion_anterior.vaciarTableroEnPos();
			this.movimientosRestantes = this.movimientosRestantes - 1;
			if (this.movimientosRestantes == 0){
				this.equipo.restarMovimientosRestantes();
			}
		}
		catch (CasilleroOcupadoException error){
			/*cancela movimiento (mas adelante agregar mensaje a usuario)*/
		}
		catch (FueraDelTableroException error){
			/*cancela movimiento (mas adelante agregar mensaje a usuario)*/
		}
	}

	public void moverIzquierda()
	{
		this.mover(this.posicion.darIzquierda());
	}
	
	public void moverDerecha()
	{
		this.mover(this.posicion.darDerecha());
	}
	
	public void moverArriba()
	{
		this.mover(this.posicion.darArriba());
	}
	
	public void moverAbajo()
	{
		this.mover(this.posicion.darAbajo());
=======
	public boolean estaConvertidoAChocolate()
	{
		return (this.estadoTransformacionActual.getNombre() == ConstantesDelJuego.CHOCOLATE);
>>>>>>> 725fe55233c63811d8188aa2745687d5fd216d19
	}
	
	protected void verificarAtaque(Personaje victima)
	{	
		if (!this.posicion.dentroDelRango(victima.getPosicion(), this.getDistanciaDeAtaque())){
			throw new FueraDeRangoException();
		}
		
		if (this.equipo.existePersonaje(victima.getNombre())){
			throw new IntentandoAtacarAUnCompanieroException();
		}
		
	}
	public void realizarAtaqueBasico(Personaje victima){
		this.verificarAtaque(victima);
		this.equipo.restarAtaqueRestates();
		this.estadoTransformacionActual.realizarAtaqueBasico(victima);
	}

	public void realizarAtaqueEspecial(Personaje victima)
	{
		this.verificarAtaque(victima);
		this.equipo.restarAtaqueRestates();
		try{
			this.estadoTransformacionActual.realizarAtaqueEspecial(victima,
					this.ataqueEspecial.getPorcentaje(this.ki));
		}
		catch( KiInsuficienteException error){
		}
				
	}
	

	public void recibirDanio(int danioARecibir, int poderDePeleaEnemigo){
		if (poderDePeleaEnemigo < this.getPoderDePelea()){
			danioARecibir = danioARecibir - (danioARecibir * ConstantesDelJuego.REDUCCION_DE_ATAQUE / 100);
		}
		this.salud.disminuir(danioARecibir);
		if (this.salud.esCero()){
			this.equipo.eliminar(this);
		}
	}
	
	public void reestablecer(){
		/*deja todo listo para el siguiente turno*/
		try{
			this.estadoTransformacionActual.reducirTurnos();
		}
		catch (SeAcabaronTurnosDelEstadoException error){
			//Falta volver al estado anterior.
		}
		this.aumentarKi(ConstantesDelJuego.KI_POR_TURNO);
	}	
	
	private void actualizarMovimientosRestantes(int velocidadAnterior){
		equipo.actualizarMovimientosRestantes(this, velocidadAnterior, this.getVelocidad());
		
	}

	public void convertirAChocolate(){
        EstadoActividad transformacionAChocolate = new EstadoInactivoConChocolate(); 
        transformacionAChocolate.setSiguienteEstado(this.estadoTransformacionActual, 0);
        this.estadoTransformacionActual = transformacionAChocolate;
    }
	
	public EstadoActividad getEstado() {
		return this.estadoTransformacionActual;
	}
<<<<<<< HEAD

	public void regenerarSalud(int plusVida) {
		this.salud.aumentar(plusVida);
	}
	
	public void agarrarConsumible(Consumible consumible)
	{
		consumible.aplicarAPersonaje(this);
	}

	public void setEstado(EstadoActividad estado) {
		this.estadoTransformacionActual = estado;
=======
	
	public String getImagen(){
		return rutaImagen;
>>>>>>> 725fe55233c63811d8188aa2745687d5fd216d19
	}
}
