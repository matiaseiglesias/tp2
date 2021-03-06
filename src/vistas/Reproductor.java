package vistas;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;;

public class Reproductor {
	
	String path = "src/vistas/musica/musicaDeFondo.mp3";
	File archivo = new File(path);
	Media media = new Media(archivo.toURI().toString());
	MediaPlayer mediaPlayer = new MediaPlayer(media); 
	
	public void reproducionAutomitica(boolean estado){
		
		mediaPlayer.setAutoPlay(estado);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		
	}
	
	public void pausar(){
		
		mediaPlayer.pause();
	}
	
	
	public void play(){
	
		mediaPlayer.play();
	}

	
	public void muteOn(){
		
		mediaPlayer.setMute(true);
	}

	public void muteOff(){
		
		mediaPlayer.setMute(false);
	}
	
	public boolean estaMudo(){
		
		return mediaPlayer.isMute();
	}
}
