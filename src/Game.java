import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import net.miginfocom.swing.MigLayout;
import sun.applet.Main;

public class Game extends JFrame{
    private JPanel GameBox,AnswerBox;
    private int i=0,x[],y[],ans;
    private JLabel a[];
    private int count=0,curY=0;
    private Font f;
    private Level level;
    private JTextField answer;
    private Timer timer,delay_card;
    
    public Game(){
        setLookAndFeel();
        SoundEffect.init();
        SoundEffect.volume = SoundEffect.Volume.LOW;  // un-mute
        this.getContentPane().setBackground(new Color(0xff,0xf0,0xa5));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800,600);
        this.setVisible(true);
        this.setLocation(200, 100);
        this.setTitle("Cardculator game");
        this.setLayout(new MigLayout());
        
        addWindowListener(new myWindowListener());
        a = new JLabel[1000];
        x = new int[1000];
        y = new int[1000];
        addComponent();
        addListener();
        timer = new Timer(50,new TimerListener());
        
        timer.start();
    }
    
    private class TimerListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            count++;
            curY += 1;
            for(int j=0;j<GameBox.getComponentCount();j++){
                a[j].setBounds(a[j].getX(), curY-(60*j), 100, 40);
            }
            if(count==60){
                i++;
                count=0;
                addCard();
                validate();
            }
        }
    }
    
    public void addComponent(){
        f = new Font("Arial",Font.BOLD,18);
        GameBox = new JPanel();
        GameBox.setLayout(null);
        GameBox.setOpaque(true);
        GameBox.setPreferredSize(new Dimension(650,450));
       // GameBox.setLocation(0, 0);;
        
        //AnswerBox.setBounds(0, 450, 650, 150);
        
        
        answer = new JTextField(10);
        //answer.setLocation(50,500);
        answer.setFont(f);
        
        addCard();
        add(GameBox,"wrap 40px");
        add(answer,"gapleft 10%");
    }
    
    public void addCard(){
        a[i] = new JLabel(Integer.toString(x[i] = (int)(Math.random()*10))+" + "+Integer.toString(y[i] = (int)(Math.random()*10))+" = ?");
        a[i].setBounds((int)(Math.random()*(600-150)), 0, 100, 40);
        a[i].setFont(f);
        a[i].setBackground(Color.red);
        a[i].setOpaque(true);
        a[i].setHorizontalAlignment(SwingConstants.CENTER);
        GameBox.add(a[i]);
        
    }
 
    public void addListener(){
        boolean isCorrect = false;
        answer.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    ans = Integer.parseInt(answer.getText());
                    answer.setText(null);
                    for(int j=0;j<GameBox.getComponentCount();j++){
                        if(x[j]+y[j]==ans){
                            System.out.println(x[j]+" + "+y[j]+" = "+ans);
                            a[j].setText("Correct!");
                            SoundEffect.SHOOT.play();
                            delayCardDisappear(a[j]);
                        }
                    }
                }
            }    
        });
    }
    
    public static synchronized void playSound(final String url) {
  new Thread(new Runnable() {
  // The wrapper thread is unnecessary, unless it blocks on the
  // Clip finishing; see comments.
        public void run() {
          try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
            Main.class.getResourceAsStream(url));
            clip.open(inputStream);
            clip.start(); 
          } catch (Exception e) {
            System.err.println(e.getMessage());
          }
        }
      }).start();
    }
    
    public void delayCardDisappear(final JLabel label){
        delay_card = new Timer(2000,new ActionListener(){
            public void actionPerformed(ActionEvent e){
                delay_card.setRepeats(false);
                label.setVisible(false);
            }
       });
        delay_card.start();
    }
    
    private class myWindowListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e){
            level.setVisible(true);
        }
    }
    
    public void setLevel(Object level){
        this.level = (Level)level;
    }
    
    public final void setLookAndFeel(){
        try{for(LookAndFeelInfo info:UIManager.getInstalledLookAndFeels()){
            if(info.getName().equals("Nimbus")){
                UIManager.setLookAndFeel(info.getName());
                break;
            }
        }}
        catch(Exception e){}
    }
}
