package controladores.eventos;

import java.util.Hashtable;

import javafx.event.ActionEvent;
import model.algoBall.Equipo;
import model.exceptions.KiInsuficienteException;
import model.exceptions.NoCumpleCondicionesDeTransformacionException;
import model.exceptions.YaNoPuedeEvolucionarException;
import vistas.CajaDeInformacionPersonaje;
import vistas.Consola;
import vistas.LabelModificable;
import vistas.VistaTablero;

public class BotonTransformarEventHandler extends BotonModificableHandler {
	
	private Equipo equipo;
	private VistaTablero vista;
	private Hashtable<String,CajaDeInformacionPersonaje> cajas;
	private Consola consola;
	private LabelModificable danioBasico;
	private LabelModificable danioEspecial;
	
	public BotonTransformarEventHandler(Equipo equipo,VistaTablero vista, Consola consola, Hashtable<String,CajaDeInformacionPersonaje> cajas, LabelModificable danioBasico, LabelModificable danioEspecial){
		this.equipo = equipo;
		this. vista = vista;
		this.consola = consola;
		this.cajas = cajas;
		this.danioBasico = danioBasico;
		this.danioEspecial = danioEspecial;
	}

	@Override
	public void handle(ActionEvent arg0) {
		try{
			equipo.transformar(this.personajeModificador);
			vista.update();
			cajas.get(personajeModificador.getNombre()).actualizar();
			danioBasico.modificar("danio(PdP)", personajeModificador.getPoderDePelea());
			danioEspecial.modificar("danio", personajeModificador.getDanioAtaqueEspecial());
			consola.reiniciar();
		}
		catch (YaNoPuedeEvolucionarException error){
			this.consola.agregarInformacion("Este personaje ya no posee una proxima evolucion");
		}
		catch (KiInsuficienteException error){
			this.consola.agregarInformacion("El ki del personaje no es suficiente para evolucionar");
		}
		catch (NoCumpleCondicionesDeTransformacionException error){
			this.consola.agregarInformacion("Este personaje aun no cumple todas las condiciones para tranformarse");
		}
	}
	
	

}
