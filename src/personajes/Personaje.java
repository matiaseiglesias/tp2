package personajes;

import algoBall.ConstantesDelJuego;
import algoBall.Equipo;
import exceptions.CasilleroOcupadoException;
import exceptions.FueraDeRangoException;
import exceptions.FueraDelTableroException;
import exceptions.IntentandoAtacarAUnCompanieroException;
import exceptions.KiInsuficienteException;
import exceptions.NoEstaEnEstadoChocolateException;
import exceptions.NoQuedanMovimientosException;
import exceptions.PersonajeEnEstadoChocolate;
import exceptions.SeAcabaronTurnosDelEstadoException;
import exceptions.YaNoPuedeEvolucionarException;
import funcionamientoPersonaje.elementos.AtaqueEspecial;
import funcionamientoPersonaje.elementos.EstadoActividad;
import funcionamientoPersonaje.elementos.EstadoInactivoConChocolate;
import funcionamientoPersonaje.elementos.EstadoTransformacion;
import funcionamientoPersonaje.elementos.Ki;
import funcionamientoPersonaje.elementos.Salud;
import tablero.Posicion;


public abstract class Personaje 
{
	protected Posicion posicion;
	protected String nombre;
	protected int movimientosRestantes;
	protected Ki ki;
	protected Salud salud;
	protected EstadoActividad estadoTransformacionActual;
	protected Equipo equipo;
	protected AtaqueEspecial ataqueEspecial;
	
	abstract EstadoActividad setEstadoNormal();
	abstract EstadoTransformacion setPrimerEstadoTransformacion();
	abstract EstadoTransformacion setSegundoEstadoTransformacion();
	
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
	
	public int getKi()
	{
		return ki.getKi();
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
		return (this.estadoTransformacionActual.getPoderDePelea());
	}
	public int getDistanciaDeAtaque()
	{
		return (this.estadoTransformacionActual.getDistanciaDeAtaque());
	}
	

	
	public void aumentarKi(int cantidad){
		this.estadoTransformacionActual.aplicarKi(this, cantidad);
	}
	
	public void ejecutarAumentoDeKi(int cantidad){
		this.ki.sumar(cantidad);
	}
	
	public void transformar()
	{
		try{
			int velocidadAnterior = this.getVelocidad();
			this.estadoTransformacionActual = this.estadoTransformacionActual.transformar(this.ki);
			this.actualizarMovimientosRestantes(velocidadAnterior);
		}
		catch (YaNoPuedeEvolucionarException error){
			/*cancela evolucion (mas adelante agregar mensaje a usuario)*/
		}
		catch (KiInsuficienteException error){
			/*cancela evolucion (mas adelante agregar mensaje a usuario)*/
		}
	}
	
	private boolean estaConvertidoAChocolate()
	{
		if (this.estadoTransformacionActual.getNombre() == ConstantesDelJuego.CHOCOLATE){
			return true;
		}
		return false;
	}
	private void mover(Posicion nuevaPosicion)
	{
		if (this.estaConvertidoAChocolate()){
			throw new PersonajeEnEstadoChocolate();
		}
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
	}
	
	protected void verificarAtaque(Personaje victima)
	{
		if(this.estaConvertidoAChocolate()){
			throw new PersonajeEnEstadoChocolate();
		}
		
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
		this.estadoTransformacionActual.realizarAtaqueEspecial(victima,
				this.ataqueEspecial.getPorcentaje(this.ki));
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
			this.estadoTransformacionActual = this.estadoTransformacionActual.transformar(ki); 
		}
		catch( NoEstaEnEstadoChocolateException error){
		 // si le mandas reducir turnos a un estado estandar tipo super sayajin
		}
		movimientosRestantes = this.getVelocidad();
		this.aumentarKi(ConstantesDelJuego.KI_POR_TURNO);
	}
	
	public void prohibirMovimientos(){
		movimientosRestantes = 0;
	}
	
	
	private void actualizarMovimientosRestantes(int velocidadAnterior){
		if (movimientosRestantes == 0){
			return;
		}
		else{
			int movimientosRealizados = velocidadAnterior - movimientosRestantes;
			movimientosRestantes = (this.getVelocidad() - movimientosRealizados);
		}
		
	}

	public void convertirAChocolate(){
	    
        EstadoActividad transformacionAChocolate = new EstadoInactivoConChocolate(); 
        transformacionAChocolate.setSiguienteEstado(this.estadoTransformacionActual, 0);
        this.estadoTransformacionActual = transformacionAChocolate;
    }
	
	public EstadoActividad getEstadoActividad() {
		return this.estadoTransformacionActual;
	}
}
