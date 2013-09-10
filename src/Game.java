import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class Game extends JFrame{
    private JPanel GameBox,AnswerBox,ScoreBox;
    private int cardIndex=0,x[],y[],ans;
    private JLabel card[],AnsMessage,Lives;
    private int timerAction=0,curY=0,lives = 3;
    private Font f;
    private Level level;
    private JTextField answer;
    private Timer timer,delay_card;
    
    public Game(){
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
        card = new JLabel[1000];
        x = new int[1000];
        y = new int[1000];
        addComponent();
        addListener();
        timer = new Timer(50,new TimerListener());
        timer.start();
    }
    
    private class TimerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            timerAction++;
            curY += 1;
            for(int j=0;j<GameBox.getComponentCount();j++){
                card[j].setBounds(card[j].getX(), curY-(60*j), 100, 40);
                if(card[j].getY()==(GameBox.getHeight()-card[j].getHeight())){
                    lives--;
                    ScoreBox.remove(ScoreBox.getComponentCount()-1);
                    ScoreBox.repaint();
                }
                if(lives==0){
                    System.exit(0);
                    /*
                     * end game here.
                     */
                }
            }
            if(timerAction==60){
                cardIndex++;
                timerAction=0;
                addCard();
                validate();
            }
        }
    }
    
    private void addComponent(){
        f = new Font("Arial",Font.BOLD,18);
        GameBox = new JPanel();
        GameBox.setLayout(null);
        GameBox.setOpaque(true);
        
        AnswerBox = new JPanel();
        AnswerBox.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
        answer = new JTextField(10);
        answer.setFont(f);
        AnsMessage = new JLabel("Enter answer : ");
        AnsMessage.setFont(f);
        AnswerBox.add(AnsMessage);
        AnswerBox.add(answer);
        
        ScoreBox = new JPanel();
        ScoreBox.setLayout(new MigLayout());
        Lives = new JLabel("Lives : ");
        Lives.setFont(f);
        ScoreBox.add(Lives);
        ScoreBox.add(new JLabel(new ImageIcon("propractice/CARD.jpg")),"wrap");
        ScoreBox.add(new JLabel(new ImageIcon("propractice/CARD.jpg")));
        ScoreBox.add(new JLabel(new ImageIcon("propractice/CARD.jpg")));
        
        addCard();
        add(GameBox,"pos 10px 10px 600px 450px");
        add(ScoreBox,"pos 610px 10px 770px 550px");
        add(AnswerBox,"pos 10px 460px 600px 550px");
        
    }
    
    public void addCard(){
        card[cardIndex] = new JLabel(Integer.toString(x[cardIndex] = (int)(Math.random()*10))+" + "+Integer.toString(y[cardIndex] = (int)(Math.random()*10))+" = ?");
        card[cardIndex].setBounds((int)(Math.random()*(600-150)), 0, 100, 40);
        card[cardIndex].setFont(f);
        card[cardIndex].setBackground(Color.red);
        card[cardIndex].setOpaque(true);
        card[cardIndex].setHorizontalAlignment(SwingConstants.CENTER);
        GameBox.add(card[cardIndex]);
    }
 
    private void addListener(){
        answer.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    ans = Integer.parseInt(answer.getText());
                    for(int j=0;j<GameBox.getComponentCount();j++){
                        if(x[j]+y[j]==ans){
                            card[j].setText("Correct!");
                            SoundEffect.SHOOT.play();
                            delayCardDisappear(card[j]);
                        }
                    }
                }
            }    
        });
    }
    
    public void delayCardDisappear(final JLabel label){
        delay_card = new Timer(2000,new ActionListener(){
            @Override
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
    
}
