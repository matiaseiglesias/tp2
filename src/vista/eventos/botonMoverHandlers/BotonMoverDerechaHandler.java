package vista.eventos.botonMoverHandlers;
import javafx.event.ActionEvent;
import model.algoBall.Equipo;
import model.exceptions.CasilleroOcupadoException;
import model.exceptions.EstePersonajeNoSePuedeMoverException;
import model.exceptions.FueraDelTableroException;
import model.exceptions.NoQuedanMovimientosException;
import vista.eventos.BotonModificableHandler;
import vistas.Consola;
import vistas.VistaTablero;

public class BotonMoverDerechaHandler extends BotonModificableHandler {

	private Equipo equipo;
	private VistaTablero vista;
	private Consola consola;
	
	
	public BotonMoverDerechaHandler(Equipo equipo, VistaTablero vista, Consola consola) {
		this.equipo = equipo;
		this.vista = vista;
		this.consola = consola;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try{
        	this.equipo.moverDerecha(personajeModificador);
        	this.vista.update();
        }
        catch(NoQuedanMovimientosException error){
     	   this.consola.agregarInformacion("No te quedan movimientos!");
        }
        catch(EstePersonajeNoSePuedeMoverException e){
     	   this.consola.agregarInformacion("No se puede mover este personaje");
         }
     	catch (CasilleroOcupadoException error){
     	   this.consola.agregarInformacion("La posicion a la que intentas moverte ya esta ocupada");
     	}
 		catch (FueraDelTableroException error){
 			this.consola.agregarInformacion("Estas intentando moverte fuera del tablero");
 		}
    }
}