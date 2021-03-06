package model.personajes;

import static model.algoBall.ConstantesDelJuego.KI_ATQ_ESPECIAL_MAJIN_BOO;
import static model.algoBall.ConstantesDelJuego.KI_INICIAL;
import static model.algoBall.ConstantesDelJuego.MAJIN_BOO_NOMBRE;
import static model.algoBall.ConstantesDelJuego.NOMBRE_ATQ_ESPECIAL_MAJIN_BOO;
import static model.algoBall.ConstantesDelJuego.PUNTOS_VIDA_MAJIN_BOO;

import model.personajes.elementos.AtaqueEspecial;
import model.personajes.elementos.Ki;
import model.personajes.elementos.Salud;
import model.personajes.majinBoo.EstadoMajinBooNormal;

public class MajinBoo extends Personaje 
{	
	
	public MajinBoo()
	{
		this.nombre = MAJIN_BOO_NOMBRE;
		this.ki = new Ki(KI_INICIAL);
		this.salud = new Salud(PUNTOS_VIDA_MAJIN_BOO);
		this.ataqueEspecial = new AtaqueEspecial(NOMBRE_ATQ_ESPECIAL_MAJIN_BOO, KI_ATQ_ESPECIAL_MAJIN_BOO);
		this.estadoTransformacionActual = new EstadoMajinBooNormal();
		this.rutaImagen = "file:src/vistas/imagenes/majinboo.png";
	}
}