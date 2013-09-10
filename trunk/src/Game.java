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
    private int cardIndex=0,x,y,ans;
    private JLabel AnsMessage,Lives;
    private myCard card[];
    private int timerAction=0,lives = 3;
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
        card = new myCard[1000];
        addComponent();
        addListener();
        timer = new Timer(50,new TimerListener());
        timer.start();
    }
    
    private class TimerListener implements ActionListener{
        int count=0;
        @Override
        public void actionPerformed(ActionEvent e){
            timerAction++;
            JLabel card_pointer;
            for(int j=0;j<GameBox.getComponentCount();j++){
                card_pointer = (JLabel)GameBox.getComponent(j);
                if(card_pointer.getText().equals("Correct!")){card_pointer.setBounds(card_pointer.getX(), card_pointer.getY(), 100, 40);
                }
                else{
                    card_pointer.setBounds(card_pointer.getX(), card_pointer.getY()+1, 100, 40);
                    if(card_pointer.getY()==(GameBox.getHeight()-card_pointer.getHeight())){
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
        card[cardIndex] = new myCard(Integer.toString(x = (int)(Math.random()*10))+" + "+Integer.toString(y = (int)(Math.random()*10))+" = ?");
        card[cardIndex].setXY(x,y);
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
                myCard card_pointer;
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    ans = Integer.parseInt(answer.getText());
                    answer.setText(null);
                    for(int j=0;j<GameBox.getComponentCount();j++){
                        card_pointer = (myCard)GameBox.getComponent(j);
                        if(ans==card_pointer.getAnswer()){
                            card_pointer.setText("Correct!");
                            SoundEffect.SHOOT.play();
                            delayCardDisappear(card_pointer,2000);
                            break;
                        }
                    }
                }
            }    
        });
    }
    
    public void delayCardDisappear(final JLabel label,int delaytime){
        delay_card = new Timer(delaytime,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                GameBox.remove(label);
                GameBox.repaint();
                delay_card.setRepeats(false);
                
            }
       });
        delay_card.start();
    }
    
    private class myCard extends JLabel{
        private int x,y,ans;
        public myCard(){
            
        }
        public myCard(String name){
            super(name);
        }
        public void setXY(int x,int y){
            this.x = x;
            this.y = y;
            ans = x+y;
        }
        
        public int getXValue(){
            return x;
        }
        
        public int getYValue(){
            return y;
        }
        public int getAnswer(){
            return ans;
        }
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
