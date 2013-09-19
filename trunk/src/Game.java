import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class Game extends JFrame{
    private JPanel GameBox, AnswerBox, ScoreBox , Lives, NotLives;
    private JLabel AnsMessage, lives, Score, Score_point, Level, Level_point, waitImg,songBird;
    private JButton pause,quit;
    private JTextField answer;
    private Font f;
    private Timer timer, delay_card;
    private myCard card[];
    private int cardIndex=0, x, y, score=0, mode=0, operation=0, card_delay=100, speed=1, //operation : 0=plus,1=minus,2=multiply,3=divide
    timerAction=0, livesCount = 3, level_value=1, numberOfOperation;
    private String player_name,textmode;
    private FileWriter write;
    private Menu menu;
        
    public Game(int mode) throws IOException{
        setMode(mode);
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
                            card_pointer.setBackground(new Color(0xff,0x2d,0x00));
                        }
                        else{
                            card_pointer.setBackground(new Color(0xd9,0x00,0x00));
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
                        if(mode<2)
                            card_pointer.setBounds((int)(card_pointer.getPositionPortion()*(GameBox.getWidth()-(GameBox.getWidth()*0.2))), card_pointer.getY()+speed, (int)(GameBox.getWidth()*0.2), (int)(GameBox.getHeight()*0.1));
                        else
                            card_pointer.setBounds((int)(card_pointer.getPositionPortion()*(GameBox.getWidth()-(GameBox.getWidth()*0.3))), card_pointer.getY()+speed, (int)(GameBox.getWidth()*0.3), (int)(GameBox.getHeight()*0.1));
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
                            Lives.repaint();
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
                            Game.this.dispose();
                            Game.this.removeAll();
                            menu.setVisible(true);
                            j = 99999;//for out of loop
                            JOptionPane.showMessageDialog(null,"Your name is "+player_name+" playing mode "+textmode+" and score is "+score+" has been recorded!");
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
    
    private void addComponent() throws IOException{
      
        f = new Font("Arial",Font.BOLD,18);
        GameBox = new JPanel();
        GameBox.setLayout(null);
        GameBox.setBackground(new Color(0x04,0x75,0x6f));
        GameBox.setOpaque(true);
        
        AnswerBox = new JPanel();
        AnswerBox.setLayout(new FlowLayout(FlowLayout.CENTER,20,30));
        answer = new JTextField(10);
        answer.setFont(f);
        AnsMessage = new JLabel("Enter answer : ");
        AnsMessage.setFont(f);
        AnswerBox.add(AnsMessage);
        AnswerBox.add(answer);
        AnswerBox.setBackground(new Color(0xff,0x8c,0x00));
        
        ScoreBox = new JPanel();
        ScoreBox.setLayout(new MigLayout());
        lives = new JLabel("Lives : ");
        lives.setFont(f);
        ScoreBox.add(lives,"id lives");
        ScoreBox.add(new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("heart-icon.png")))),"pos (lives.x2+10px) (lives.y),id firstlive");
        Lives = new JPanel(new MigLayout());
        
        Lives.add(new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("heart-icon.png")))));
        Lives.add(new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("heart-icon.png")))),"gapx 12px");
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
        NotLives.add(pause,"pos 5px (level.y2+20px) ,id pause");
        
        quit = new JButton("Main menu");
        quit.setFont(f);
        NotLives.add(quit,"pos 5px (pause.y2+20px),id quit");
        
        songBird = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("songbird-icon.png"))));
        NotLives.add(songBird,"pos 5px (quit.y2+20px)");
        
        ScoreBox.add(NotLives,"pos 0px (LiveCard.y2+20px)");
        
        Icon img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("waiting_small.png")));
        waitImg = new JLabel(img);
        waitImg.setVisible(false);              
        
        addCard();
        add(waitImg,"pos 15px 2px,width 70%,height 100%");
        add(GameBox,"pos 10px 10px,width 80%,height 80%,grow,push,id GameBox");
        add(ScoreBox,"pos (GameBox.x2+10px) (GameBox.y) (GameBox.x2+(GameBox.x2*0.2)) (GameBox.y2+100px)");
        add(AnswerBox,"pos (GameBox.x) (GameBox.y2+10px) (GameBox.x2) ((GameBox.y2)+GameBox.y2*0.1)");
    }
    
    private void addCard(){
        double position_portion;
        card[cardIndex] = new myCard();
        setOperation();
        position_portion = Math.random();
        if(mode<2)
            card[cardIndex].setBounds((int)(position_portion*(GameBox.getWidth()-(GameBox.getWidth()*0.2))), 0, (int)(GameBox.getWidth()*0.2), (int)(GameBox.getHeight()*0.1));
        else
            card[cardIndex].setBounds((int)(position_portion*(GameBox.getWidth()-(GameBox.getWidth()*0.2))), 0, (int)(GameBox.getWidth()*0.3), (int)(GameBox.getHeight()*0.1));
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
                            if(level_value >= 3) updateScore(20);
                            else updateScore(10);                            
                            if(score%100==0){
                                if(score%200==0){speed++;card_delay-=7; }  
                                playSounds(score/100);
                                level_value++;
                                Level_point.setText(Integer.toString(level_value));
                                card_delay -=2;
                  /* POSiTION OF CARD IS NOT FIXED */              
                                if (score%200==0){
                                    ScoreBox.remove(NotLives);
                                    if(Lives.getComponentCount()<=3){
                                        livesCount++;
                                        if(Lives.getComponentCount()%2==0){
                                            Lives.add(new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("heart-icon.png")))),"newline 10px");
                                        }
                                        else{
                                            Lives.add(new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("heart-icon.png")))),"gapx 12px");
                                        }
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
                
                waitImg.setVisible(true);
                GameBox.setVisible(false);
                AnswerBox.setVisible(false);
            }
            else {
                Game.this.timer.start();
                SoundEffect.GAMEPLAY3.playSong();
                pause.setText("Pause");
                
                waitImg.setVisible(false);
                GameBox.setVisible(true);
                AnswerBox.setVisible(true);
                answer.requestFocus();
            }
        }
        });  
        
        quit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                SoundEffect.GAMEPLAY3.stop();
                SoundEffect.GAMEPLAY3.reset();
                Game.this.dispose();
                Game.this.removeAll();
                Game.this.timer.stop();
                menu.setVisible(true);
            }
        });
    }

    private void setOperation(){
            String expression = null;
            operation = -1;
            ArrayList<Integer> PastOperate = new ArrayList<>();
            PastOperate.add(operation);
            for(int i = 0;i<numberOfOperation;i++){
                while(PastOperate.contains(operation)){
                    operation = (int)(Math.random()*4);
                }
                PastOperate.add(operation);
                if(i==0){
                    x = (int)(Math.random()*10);
                }
                else{
                    x = card[cardIndex].getAnswer();
                }
            if(operation==0){
                y = (int)(Math.random()*10);
            }
            else if(operation==1){
                y = (int)(Math.random()*10);
            }
            else if(operation==2){
                y = (int)(Math.random()*10);
            }
            else{
                y = (int)(Math.random()*(Math.abs(x)))+1;
                while(x%y!=0){
                    if(i==0){
                        x = (int)(Math.random()*10);
                        y = (int)(Math.random()*(Math.abs(x)))+1;
                    }
                    else{
                        y = (int)(Math.random()*(Math.abs(x)))+1;
                    }
                }
            }
                if(i==0){
                    switch(operation){
                        case 0:
                            expression = Integer.toString(x)+" + "+Integer.toString(y);
                            card[cardIndex].setAnswer(x+y);
                            break;
                        case 1:
                            expression = Integer.toString(x)+" - "+Integer.toString(y);
                            card[cardIndex].setAnswer(x-y);
                            break;
                        case 2:
                            expression = Integer.toString(x)+" * "+Integer.toString(y);
                            card[cardIndex].setAnswer(x*y);
                            break;
                        default:
                            expression = Integer.toString(x)+" / "+Integer.toString(y);
                            card[cardIndex].setAnswer(x/y);
                            break;
                    }
                    if(numberOfOperation==2){
                        expression = "("+expression+") ";
                    }
                    else if(numberOfOperation == 3){
                        expression = "(("+expression+") ";
                    }
                }else{
                    if(i==2){
                        expression += ") ";
                    }
                    switch(operation){
                        case 0:
                            expression += "+ "+Integer.toString(y)+" ";
                            card[cardIndex].setAnswer(x+y);
                            break;
                        case 1:
                            expression += "- "+Integer.toString(y)+" ";
                            card[cardIndex].setAnswer(x-y);
                            break;
                        case 2:
                            expression += "* "+Integer.toString(y)+" ";
                            card[cardIndex].setAnswer(x*y);
                            break;
                        default:
                            expression += "/ "+Integer.toString(y)+" ";
                            card[cardIndex].setAnswer(x/y);
                            break;
                    }      
                }
         }
            expression += " = ?";
            card[cardIndex].setExpression(expression);
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
    
    private void setMode(int n){
        mode = n;
        if(mode==0){
            textmode = "Easy";
            numberOfOperation = 1;
        }
        else if(mode==1){
            textmode = "Normal";
            numberOfOperation = 2;
        }else{
            textmode = "Hard";
            numberOfOperation = 3;
        }
    }
    
    public void setMenu(Object menu){
        this.menu = (Menu)menu;
    }
    
    private class myCard extends JLabel{
        private int ans;
        private double position_portion;
        private boolean swap = false;
        public myCard(){
            
        }
        public myCard(String name){
            super(name);
        }
        
        public void setExpression(String exp){
            super.setText(exp);
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
