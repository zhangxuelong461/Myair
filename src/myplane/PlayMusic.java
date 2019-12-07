package myplane;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URL;


public class PlayMusic {
	private File path=null;
	private AudioClip ac=null;
	private URL url=null;
	public PlayMusic(String path) {
		url=World.class.getResource(path);
		ac=Applet.newAudioClip(url);		
	}
	public void playStart(){
		ac.play();
	}
	public void playSloop(){
		ac.loop();
	}
	public void playStop(){
		ac.stop();
	}

}
