package model.consumibles;

import static model.algoBall.ConstantesDelJuego.NUBE_VOLADORA_MULTIPLICADOR_VELOCIDAD;
import static model.algoBall.ConstantesDelJuego.NUBE_VOLADORA_TURNOS;

import model.personajes.elementos.ContadorDeTurnos;
import model.personajes.elementos.Danio;
import model.personajes.elementos.EstadoActividad;

public class EstadoBuffVelocidad extends EstadoTemporal{

	private int multiplicadorVelocidad = NUBE_VOLADORA_MULTIPLICADOR_VELOCIDAD;
	
	public EstadoBuffVelocidad(EstadoActividad actual) {
		turnos = new ContadorDeTurnos(NUBE_VOLADORA_TURNOS);
		this.rutaImagen = actual.getImagen();
		this.nombre = actual.getNombre();
		if (actual.esTemporal()){
			EstadoTemporal actualTemporal = (EstadoTemporal) actual;
			this.siguienteEstado = actual;
			actualTemporal.setVelocidad(calcularVelocidad(actual.getVelocidad()));
			this.anteriorEstado = actual.getEstadoAnterior();
			actualTemporal.setEstadoAnterior(this);
			this.distanciaDeAtaque = anteriorEstado.getDistanciaDeAtaque();
			this.poderDePelea = new Danio(anteriorEstado.getPoderDePelea());
			this.velocidad = calcularVelocidad(anteriorEstado.getVelocidad());
		}
		else{
			this.siguienteEstado = actual.getEstadoSiguiente();
			this.anteriorEstado = actual.getEstadoAnterior();
			this.distanciaDeAtaque = actual.getDistanciaDeAtaque();
			this.poderDePelea = new Danio(actual.getPoderDePelea());
			this.velocidad = calcularVelocidad(actual.getVelocidad());
		}
	}

	private int calcularVelocidad(int velocidad)
	{
		return velocidad * multiplicadorVelocidad;
	}

	public EstadoActividad getEstadoAnterior()
	{
		return this.anteriorEstado;
	}
}
