import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Logger;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class Game extends JFrame{
    private Level level;
    private JPanel GameBox, AnswerBox, ScoreBox , Lives, NotLives;
    private JLabel AnsMessage, lives, Score, Score_point, Level, Level_point;
    private JButton pause;
    private JTextField answer;
    private Font f;
    private Timer timer, delay_card;
    private myCard card[];
    private int cardIndex=0, x, y, score=0, mode=0, operation=0, card_delay=100, speed=1, //operation : 0=plus,1=minus,2=multiply,3=divide
    timerAction=0, livesCount = 3, level_value=1;
    private String player_name,textmode;
    private FileWriter write;
    private Menu menu;
    
    public Game(){
        SoundEffect.init();
        SoundEffect.volume = SoundEffect.Volume.LOW;  // un-mute
        SoundEffect.GAMEPLAY3.playSong();
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
        
        timer = new Timer(30,new TimerListener());
        timer.start();
    }
    
    private class TimerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            timerAction++;
            myCard card_pointer;
            myCard target_swap;
            int a,b;
            if(timerAction%500==0&&GameBox.getComponentCount()>=3){
                a = (int)(Math.random()*(GameBox.getComponentCount()-2))+1;
                b = (int)(Math.random()*(GameBox.getComponentCount()-1));
                while(a==b){
                    b =(int)(Math.random()*(GameBox.getComponentCount()-1));
                }
                target_swap = (myCard)GameBox.getComponent(a);
                card_pointer = (myCard)GameBox.getComponent(b);
                if(!target_swap.getText().equals("Correct!")&&!card_pointer.getText().equals("Correct!")){
                delayCardSwap(card_pointer,target_swap,card_pointer.getText(),target_swap.getText(),3000);
                card_pointer.setText("SWAP!");
                target_swap.setText("SWAP!");
                card_pointer.setSwap(true);
                target_swap.setSwap(true);
                }
            }
            for(int j=0;j<GameBox.getComponentCount();j++){
                card_pointer = (myCard)GameBox.getComponent(j);
                switch (card_pointer.getText()) {
                    case "Correct!":
                        if(timerAction%4==0){
                            card_pointer.setBackground(Color.yellow);
                        }
                        else{
                            card_pointer.setBackground(Color.red);
                        }
                        card_pointer.repaint();
                        break;
                    case "SWAP!":
                        if(timerAction%5==0){
                            card_pointer.setBackground(Color.yellow);
                        }
                        else{
                            card_pointer.setBackground(Color.red);
                        }
                        card_pointer.repaint();
                        break;
                    default:
                        card_pointer.setBounds((int)(card_pointer.getPositionPortion()*(GameBox.getWidth()-(GameBox.getWidth()*0.2))), card_pointer.getY()+speed, (int)(GameBox.getWidth()*0.2), (int)(GameBox.getWidth()*0.07));
                        if(card_pointer.getY()>=(GameBox.getHeight()-card_pointer.getHeight())){
                            livesCount--;
                            GameBox.remove(card_pointer);
                            if(Lives.getComponentCount()>0)
                                Lives.remove(Lives.getComponentCount()-1);
                            else{
                                ScoreBox.getComponent(1).setVisible(false);
                                ScoreBox.remove(1);
                                ScoreBox.remove(0);
                            }
                            ScoreBox.validate();
                            Lives.validate();
                        }
                        if(livesCount==0){
                            SoundEffect.GAMEPLAY3.stop();
                            try {
                                write = new FileWriter("highscore.txt",true);
                                write.write(player_name+" "+textmode+" "+score);
                                write.append("\r\n");
                                write.close();
                            } catch (FileNotFoundException ex) {} catch (IOException ex) {}
                            SoundEffect.GAMEPLAY3.stop();
                            SoundEffect.GAMEPLAY3.reset();
                            Game.this.timer.stop();
                            JOptionPane.showMessageDialog(null,"Your name is "+player_name+" playing mode "+textmode+" and score is "+score+" has been recorded!");
                            Game.this.dispose();
                            Game.this.removeAll();
                            menu.setVisible(true);
                            /*
                             * end game here.
                             */
                        }
                        break; 
                }
            }
            if(timerAction%card_delay==0){
                cardIndex++;
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
        AnswerBox.setLayout(new FlowLayout(FlowLayout.CENTER,20,30));
        answer = new JTextField(10);
        answer.setFont(f);
        AnsMessage = new JLabel("Enter answer : ");
        AnsMessage.setFont(f);
        AnswerBox.add(AnsMessage);
        AnswerBox.add(answer);
        
        ScoreBox = new JPanel();
        ScoreBox.setLayout(new MigLayout());
        lives = new JLabel("Lives : ");
        lives.setFont(f);
        ScoreBox.add(lives,"id lives");
        ScoreBox.add(new JLabel(new ImageIcon("propractice/CARD.jpg")),"pos (lives.x2+10px) (lives.y),id firstlive");
        Lives = new JPanel(new MigLayout());
        Lives.add(new JLabel(new ImageIcon("propractice/CARD.jpg")));
        Lives.add(new JLabel(new ImageIcon("propractice/CARD.jpg")),"gapx 12px");
        ScoreBox.add(Lives,"pos 10px (firstlive.y2),id LiveCard");
        
        NotLives = new JPanel(new MigLayout());
        
        Score = new JLabel("Score : ");
        Score_point = new JLabel("0");
        Score.setFont(f);
        Score_point.setFont(f);
        NotLives.add(Score);
        NotLives.add(Score_point,"wrap 20px");
        
        Level = new JLabel("Level : ");
        Level_point = new JLabel("1");
        Level.setFont(f);
        Level_point.setFont(f);
        NotLives.add(Level,"id level");
        NotLives.add(Level_point,"wrap 20px");
        
        pause = new JButton("Pause");
        pause.setFont(f);
        NotLives.add(pause,"pos 5px (level.y2+20px)");
        
        
        ScoreBox.add(NotLives,"pos 0px (LiveCard.y2+20px)");
        
        addCard();
        add(GameBox,"pos 10px 10px,width 80%,height 80%,grow,push,id GameBox");
        add(ScoreBox,"pos (GameBox.x2+10px) (GameBox.y) (GameBox.x2+(GameBox.x2*0.2)) (GameBox.y2+100px)");
        add(AnswerBox,"pos (GameBox.x) (GameBox.y2+10px) (GameBox.x2) ((GameBox.y2)+GameBox.y2*0.1)");
    }
    
    private void addCard(){
        double position_portion;
        setOperation(mode*10);
        position_portion = Math.random();
        card[cardIndex].setXY(x,y,operation);
        card[cardIndex].setBounds((int)(position_portion*(GameBox.getWidth()-(GameBox.getWidth()*0.2))), 0, (int)(GameBox.getWidth()*0.2), (int)(GameBox.getWidth()*0.07));
        card[cardIndex].setPositionPortion(position_portion);
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
                    int a = Integer.parseInt(answer.getText());
                    answer.setText(null);
                    for(j=0;j<GameBox.getComponentCount();j++){                        
                        card_pointer = (myCard)GameBox.getComponent(j);
                        if(a==card_pointer.getAnswer()&&!card_pointer.isSwap()){                      
                            card_pointer.setText("Correct!");
                            SoundEffect.CORRECT.play();
                            delayCardDisappear(card_pointer,1000);
                            card_pointer.setSwap(true);
                            if(level_value >= 4) updateScore(20);
                            else updateScore(10);                            
                            if(score%100==0){
                                if(score%200==0){speed++;card_delay-=5; }  
                                playSounds(score/100);
                                level_value++;
                                Level_point.setText(Integer.toString(level_value));
                                card_delay -=10;
                  /* POSiTION OF CARD IS NOT FIXED */              
                                if (score%200==0){
                                    livesCount++;
                                    ScoreBox.remove(NotLives);
                                    if(Lives.getComponentCount()%2==0){
                                        Lives.add(new JLabel(new ImageIcon("propractice/CARD.jpg")),"newline 10px");
                                    }
                                    else{
                                        Lives.add(new JLabel(new ImageIcon("propractice/CARD.jpg")),"gapx 12px");
                                    }
                                    ScoreBox.add(NotLives,"pos 0px (LiveCard.y2+20px)");
                                    ScoreBox.validate();
                                }
                            }
                            break;
                        }     
                    }
                    //Wrong answer will cause the card fall faster.
                    if(j==GameBox.getComponentCount()){
                        SoundEffect.WRONG.play();
                        for(int k = 0;k < GameBox.getComponentCount();k++){
                            card_pointer = (myCard)GameBox.getComponent(k);
                            card_pointer.setLocation(card_pointer.getX(),card_pointer.getY()+40);
                        }
                    }                 
                }
            }    
        });
        
        pause.addActionListener(new ActionListener(){
        public void actionPerformed (ActionEvent e)
        {
            if(pause.getText().equals("Pause")){
                Game.this.timer.stop();
                SoundEffect.GAMEPLAY3.stop();
                pause.setText("Resume");
                
                GameBox.setVisible(false);
                AnswerBox.setVisible(false);
            }
            else {
                Game.this.timer.start();
                SoundEffect.GAMEPLAY3.playSong();
                pause.setText("Pause");
                
                GameBox.setVisible(true);
                AnswerBox.setVisible(true);
                answer.requestFocus();
            }
        }
        });  
    }

    private void setOperation(int scale){
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
            }
    }
    
    private void swapCard(myCard source,myCard target,String text1,String text2){
        int a;
        a = source.getAnswer();
        source.setText(text2);
        source.setAnswer(target.getAnswer());
        target.setText(text1);
        target.setAnswer(a);
        source.setBackground(Color.red);
        target.setBackground(Color.red);
        source.setSwap(false);
        target.setSwap(false);
    }
    
    private void delayCardDisappear(final myCard label,int delaytime){
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
    
    private void delayCardSwap(final myCard label1,final myCard label2,final String text1,final String text2,int delaytime){
        delay_card = new Timer(delaytime,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //add code here
                swapCard(label1,label2,text1,text2);
                GameBox.repaint();
                delay_card.setRepeats(false);
            }
       });
        delay_card.start();
    }
    
    private void updateScore(int amount){  
        score+=amount;
        Score_point.setText(Integer.toString(score));
        ScoreBox.repaint();
    }
    
    private class myWindowListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e){
            SoundEffect.GAMEPLAY3.stop();
            SoundEffect.GAMEPLAY3.reset();
            Game.this.dispose();
            Game.this.removeAll();
            Game.this.timer.stop();
            menu.setVisible(true);
        }
    }
    
    private void playSounds(int n){
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
    
    private boolean isNumber(String s){
        try{
            int a = Integer.parseInt(s);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    
    public void setMode(int n){
        mode = n;
        if(mode==0){
            textmode = "Easy";
        }
        else if(mode==1){
            textmode = "Normal";
        }else{
            textmode = "Hard";
        }
    }
    
    public void setMenu(Object menu){
        this.menu = (Menu)menu;
    }
    
    private class myCard extends JLabel{
        private int x,y,ans;
        private double position_portion;
        private boolean swap = false;
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
        
        public void setAnswer(int a){
            ans = a;
        }
        
        public void setSwap(boolean x){
            swap = x;
        }
        
        public boolean isSwap(){
            return swap;
        }
        
        public void setPositionPortion(double pos){
            position_portion = pos;
        }
        
        public double getPositionPortion(){
            return position_portion;
        }
    }
    
    public void setPlayerName(String name){
        player_name = name;
    }
}
