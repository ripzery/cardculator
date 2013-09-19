import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
   
/**
 * This enum encapsulates all the sound effects of a game, so as to separate the sound playing
 * codes from the game codes.
 * 1. Define all your sound effect names and the associated wave file.
 * 2. To play a specific sound, simply invoke SoundEffect.SOUND_NAME.play().
 * 3. You might optionally invoke the static method SoundEffect.init() to pre-load all the
 *    sound files, so that the play is not paused while loading the file for the first time.
 * 4. You can use the static variable SoundEffect.volume to mute the sound.
 */
public enum SoundEffect {         
   CORRECT("BEEP2.WAV"),       // correct answer
   WRONG("wrong.wav"),
   HUNDRED("Dota-Allstars Sounds/firstblood.wav"),
   HUNDRED2("Dota-Allstars Sounds/Combowhore.wav"),
   HUNDRED3("Dota-Allstars Sounds/Killing_Spree.wav"),
   HUNDRED4("Dota-Allstars Sounds/Dominating.wav"),
   HUNDRED5("Dota-Allstars Sounds/MegaKill.wav"),
   OWNING("Dota-Allstars Sounds/Ownage.wav"),
   HUNDRED6("Dota-Allstars Sounds/Unstoppable.wav"),
   HUNDRED7("Dota-Allstars Sounds/WhickedSick.wav"),
   HUNDRED8("Dota-Allstars Sounds/monster_kill.wav"),
   HUNDRED9("Dota-Allstars Sounds/GodLike.wav"),
   HUNDRED10("Dota-Allstars Sounds/HolyShit.wav"),
   GAMEPLAY3("megaman.wav");
   //GAMEPLAY3("The Reluctant Heroes.wav");
   
   //GAMEPLAY("taylor swift - lovestory.wav");
   
   // Nested class for specifying volume
   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }
   
   public static Volume volume = Volume.LOW;
   
   // Each sound effect has its own clip, loaded with its own sound file.
   private Clip clip;
   
   // Constructor to construct each element of the enum with its own sound file.
   SoundEffect(String soundFileName) {
      try {
         URL url = this.getClass().getClassLoader().getResource(soundFileName);
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
         clip = AudioSystem.getClip();
         clip.open(audioInputStream);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
   
   // Play or Re-play the sound effect from the beginning, by rewinding.
   public void play() {
      if (volume != Volume.MUTE) {
         if (clip.isRunning())
            clip.stop();   // Stop the player if it is still running
         clip.setFramePosition(0); // rewind to the beginning
         clip.start();     // Start playing
      }
   }
   public void playSong() {
      if (volume != Volume.MUTE) {
         if (clip.isRunning())
            clip.stop();   // Stop the player if it is still running
         clip.start();     // Start playing
         clip.loop(2);
      }
   }
   
   public void stop(){
       if(clip.isRunning())
       clip.stop();
   }
   
   public void reset(){
       clip.setFramePosition(0);
   }
   
   // Optional static method to pre-load all the sound files.
   static void init() {
      values(); // calls the constructor for all the elements
   }
}