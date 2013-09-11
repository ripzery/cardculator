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
    private int cardIndex=0,x,y,ans,score=0,mode=0,operation=0,card_delay=100,speed=1; //operation : 0=plus,1=minus,2=multiply,3=divide
    private JLabel AnsMessage,Lives,Score,Score_point,Level,Level_point;
    private myCard card[];
    private int timerAction=0,lives = 3,level_value=1;
    private Font f;
    private Level level;
    private JTextField answer;
    private Timer timer,delay_card;
    private JButton pause;
    
    public Game(){
        SoundEffect.init();
        SoundEffect.volume = SoundEffect.Volume.LOW;  // un-mute
        SoundEffect.GAMEPLAY2.playSong();
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
        timer = new Timer(20,new TimerListener()); //around 40 fps
        timer.start();
    }
    
    private class TimerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            timerAction++;
            JLabel card_pointer;
            for(int j=0;j<GameBox.getComponentCount();j++){
                card_pointer = (JLabel)GameBox.getComponent(j);
                if(card_pointer.getText().equals("Correct!")){
                    if(timerAction%4==0){
                        card_pointer.setBackground(Color.yellow);
                    }
                    else{
                        card_pointer.setBackground(Color.red);
                    }
                        card_pointer.repaint();
                }
                else{
                    card_pointer.setBounds(card_pointer.getX(), card_pointer.getY()+speed, 100, 40);
                    
                    if(card_pointer.getY()>=(GameBox.getHeight()-card_pointer.getHeight())){
                        lives--;
                        GameBox.remove(card_pointer);
                        ScoreBox.remove(1);
                        ScoreBox.repaint();
                    }
                    if(lives==0){
                        SoundEffect.GAMEPLAY2.stop();
                        JOptionPane.showMessageDialog(null,"Your score is "+score);
                        System.exit(0);
                        /*
                         * end game here.
                         */
                    }
                    
                }
            }
            if(timerAction>=card_delay){
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
        /*FlowLayout fl = new FlowLayout();
        fl.setHgap(70);*/
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
        ScoreBox.add(new JLabel(new ImageIcon("propractice/CARD.jpg")),"wrap 20px");
        Score = new JLabel("Score : ");
        Score_point = new JLabel("0");
        Score.setFont(f);
        Score_point.setFont(f);
        ScoreBox.add(Score);
        ScoreBox.add(Score_point,"wrap 20px");
        Level = new JLabel("Level : ");
        Level_point = new JLabel("1");
        Level.setFont(f);
        Level_point.setFont(f);
        ScoreBox.add(Level);
        ScoreBox.add(Level_point,"wrap 20px");
        
        pause = new JButton("Pause");
        pause.setFont(f);
        ScoreBox.add(pause);
        
        addCard();
        add(GameBox,"pos 10px 10px 600px 450px");
        add(ScoreBox,"pos 610px 10px 770px 550px");
        add(AnswerBox,"pos 10px 460px 600px 550px");
        
    }
    
    public void addCard(){
        setOperation(mode*10);
        card[cardIndex].setXY(x,y,operation);
        card[cardIndex].setBounds((int)(Math.random()*(600-150)), 0, 150, 40);
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
                int j;
                myCard card_pointer;
                if(e.getKeyCode() == KeyEvent.VK_ENTER&&isNumber(answer.getText())){
                    ans = Integer.parseInt(answer.getText());
                    answer.setText(null);
                    for(j=0;j<GameBox.getComponentCount();j++){
                        card_pointer = (myCard)GameBox.getComponent(j);
                        if(ans==card_pointer.getAnswer()){
                            card_pointer.setText("Correct!");
                            updateScore(10);                            
                               
                            if(score%100==0){
                                /*
                                 * Now, increase the speed
                                 */
                                if(score%150==0)
                                speed++;
                                playSounds(score/100);
                                level_value++;
                                Level_point.setText(Integer.toString(level_value));
                                card_delay -=5;
                            }
                            SoundEffect.CORRECT.play();
                            delayCardDisappear(card_pointer,1000);
                            break;
                        }
                    }
                    // Wrong answer will lead to end
                    if(j==GameBox.getComponentCount()){
                        SoundEffect.WRONG.play();
                        for(int k = 0;k < GameBox.getComponentCount();k++){
                            card_pointer = (myCard)GameBox.getComponent(k);
                            card_pointer.setLocation(card_pointer.getX(),card_pointer.getY()+50);
                        }
                    }
                }
            }    
        });
        
        pause.addActionListener(new ActionListener(){
        int tempSong = 0;

        public void actionPerformed (ActionEvent e)
        {
            if(tempSong ==0){
                Game.this.timer.stop();
                SoundEffect.GAMEPLAY2.stop();
                tempSong=1;
                pause.setName("Resume");
            }
            else {
                Game.this.timer.start();
                SoundEffect.GAMEPLAY2.playSong();
                tempSong=0;
                pause.setName("Pause");
            }
        }
    });
        
    }
    
    public void updateScore(int amount){  
        score+=amount;
        Score_point.setText(Integer.toString(score));
        ScoreBox.repaint();
    }
    
    public void playSounds(int n){
        if(n==1){
            SoundEffect.HUNDRED.play();
        }else if(n==2){
            SoundEffect.HUNDRED2.play();
        }else if(n==3){
            SoundEffect.HUNDRED3.play();
        }else if(n==4){
            SoundEffect.HUNDRED4.play();
        }else if(n==5){
            SoundEffect.HUNDRED5.play();
            SoundEffect.OWNING.play();
        }else if(n==6){
            SoundEffect.HUNDRED6.play();
        }else if(n==7){
            SoundEffect.HUNDRED7.play();
        }else if(n==8){
            SoundEffect.HUNDRED8.play();
        }else if(n==9){
            SoundEffect.HUNDRED9.play();
        }else{
            SoundEffect.HUNDRED10.play();
        }
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
        public void setXY(int x,int y,int op){
            this.x = x;
            this.y = y;
            if(op==0){
                ans = x+y;
            }
            else if(op==1){
                ans = x-y;
            }
            else if(op==2){
                ans = x*y;
            }
            else{
                ans = x/y;
            }   
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
    
    public void setMode(int n){
        mode = n;
    }
    
    public void setOperation(int scale){
            operation = (int)(Math.random()*4);
            if(operation==0){
                boolean temp = true;
                
                x = (int)(Math.random()*10)+scale;
                y = (int)(Math.random()*10)+scale;
                    
                while(temp==true && mode==1){
                    x = (int)(Math.random()*50)+scale;
                    y = (int)(Math.random()*50)+scale;
                    temp = false;
                }
                while(temp==true && mode==2){
                    x = (int)(Math.random()*100);
                    y = (int)(Math.random()*100);
                    temp = false;
                }
                card[cardIndex] = new myCard(Integer.toString(x)+" + "+Integer.toString(y)+" = ?");
                temp = true;
            }
            else if(operation==1){
                card[cardIndex] = new myCard(Integer.toString(x = (int)(Math.random()*10)+scale)+" - "+Integer.toString(y = (int)(Math.random()*10)+scale)+" = ?");
            }
            else if(operation==2){
                boolean temp1 = true;
                x = (int)(Math.random()*(10+scale));
                y = (int)(Math.random()*(10+scale));
                
                //normal mode X=0-20, Y=0-9  
                while(temp1==true&&mode == 1){
                    x = (int)(Math.random()*(10+scale));
                    y = (int)(Math.random()*(scale));
                    temp1 = false;
                }
                //hard mode X=10-20 , Y = 5-15   
                while(temp1==true&&mode == 2){
                    x = (int)(Math.random()*(scale-10)+10);
                    y = (int)(Math.random()*(scale-10)+5);
                    temp1 = false;
                }
                
                card[cardIndex] = new myCard(Integer.toString(x)+" * "+Integer.toString(y)+" = ?");
                temp1=true;
            }
            else if(operation==3){
                
                boolean temp2 = true;                                    
                x = (int)(Math.random()*(10+scale));
                y = (int)(Math.random()*(10+scale))+1;
                
                //normal X= 0-100 Y= 1-51
                while (temp2==true && mode == 1){
                    x = (int)(Math.random()*(90+scale));
                    y = (int)(Math.random()*(10+scale))+1;
                    temp2=false;
                }
                //hard X=0-150 Y= 1-20
                while (temp2==true && mode == 2){
                    x = (int)(Math.random()*(130+scale));
                    y = (int)(Math.random()*(scale))+1;
                    temp2=false;
                }
                while((x%y!=0)){
                    x = (int)(Math.random()*(10+scale));
                    y = (int)(Math.random()*(10+scale))+1;
                }
                card[cardIndex] = new myCard(Integer.toString(x)+" / "+Integer.toString(y)+" = ?");
                temp2=true;
            }
    }
    
    public void setDifficulty(){
        
    }
    
    private class myWindowListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e){
            SoundEffect.GAMEPLAY2.stop();
            Game.this.dispose();
            Game.this.removeAll();
            Game.this.timer.stop();
            level.setVisible(true);
        }
    }
    
    public boolean isNumber(String s){
        try{
            int a = Integer.parseInt(s);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    
    public void setLevel(Object level){
        this.level = (Level)level;
    }
}
