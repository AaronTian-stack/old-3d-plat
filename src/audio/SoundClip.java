package audio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundClip {
	
	private Clip clip = null;
	private FloatControl gainControl,panControl;
	
	//private SoundClip s; //put all sound effects into this play maybe?
	
	public SoundClip(String path) {
		
		
		try {
			InputStream audioSrc = SoundClip.class.getResourceAsStream(path);
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
			
			//URL url = this.getClass().getResource(path);
		    //AudioInputStream ais = AudioSystem.getAudioInputStream(url);
		   
			
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
														baseFormat.getSampleRate(),16,baseFormat.getChannels(),
														baseFormat.getChannels()*2,baseFormat.getSampleRate(),
														false);
			
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			
			this.clip = AudioSystem.getClip();
			this.clip.open(ais);
			
			this.gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN); //there is other stuff like pan in here
			
			//this.panControl = (FloatControl)clip.getControl(FloatControl.Type.PAN);
			
			
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}

		
	}
	
	public void play() {
		if(this.clip==null) {
			System.out.println("clip is null");
			return;
		}
		stop();
		this.clip.setFramePosition(0);
		while(!this.clip.isRunning()) {
			this.clip.start();
		}
		
		
		
	}
	
	public void stop() {
		
		if(this.clip.isRunning())
			this.clip.stop();
		
	}
	
	public void close() {
		
		stop();
		this.clip.drain();
		this.clip.close();
		
	}
	
	public void loop() {
		this.clip.loop(Clip.LOOP_CONTINUOUSLY);
		play();
	}
	
	public void setVolume(float value) {
		if(value>6.0206)
			value=6.0206f;
		else if(value<-80)
			value=-80f;
		gainControl.setValue(value);
	}
	
	
	//public void setPan(float value) {
	//	panControl.setValue(value);
	//}
	
	public boolean isRunning() {
		return this.clip.isRunning();
	}

}
