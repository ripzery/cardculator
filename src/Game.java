import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class Game extends JFrame{
    private Object m;
    private Menu mm ;
    private JPanel p;
    private int i=0;
    private JLabel a[];
    private int count=0,curY=0;
    private Font f;
    public Game(){
        try{for(LookAndFeelInfo info:UIManager.getInstalledLookAndFeels()){
            if(info.getName().equals("Nimbus")){
                UIManager.setLookAndFeel(info.getName());
                break;
            }
        }}
        catch(Exception e){}
        
        
        this.getContentPane().setBackground(new Color(0xff,0xf0,0xa5));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800,600);
        this.setVisible(true);
        this.setLocation(200, 100);
        this.setTitle("Cardculator game");
        this.setLayout(null);
        addComponent();
        
        p = new JPanel();
        p.setLayout(null);
        p.setOpaque(true);
        p.setSize(600,400);
        p.setLocation(0, 0);
        //p.setPreferredSize(new Dimension(800,400));
        
        f = new Font("Arial",Font.BOLD,18);
        
        a = new JLabel[1000];
        a[i] = new JLabel(Integer.toString((int)(Math.random()*10))+" + "+Integer.toString((int)(Math.random()*10))+" = ?");
        a[i].setBounds(400, curY, 100, 40);
        a[i].setFont(f);
        a[i].setBackground(Color.red);
        a[i].setOpaque(true);
        a[i].setHorizontalAlignment(SwingConstants.CENTER);
        p.add(a[i]);
        add(p);
        Timer timer = new Timer(50,new TimerListener());
        timer.start();
    }
    
    private class TimerListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            count++;
            curY += 1;
            for(int j=0;j<p.getComponentCount();j++){
            a[j].setBounds(a[j].getX(), curY-(60*j), 100, 40);
            }
            if(count==60){
                i++;
                count=0;
                a[i] = new JLabel(Integer.toString((int)(Math.random()*10))+" + "+Integer.toString((int)(Math.random()*10))+" = ?");
                a[i].setBounds((int)(Math.random()*(600-150)), 0, 100, 40);
                a[i].setFont(f);
                a[i].setBackground(Color.red);
                a[i].setOpaque(true);
                a[i].setHorizontalAlignment(SwingConstants.CENTER);
                p.add(a[i]);                 
                add(p);
                validate();
            }
        }
    }
    
    public void addComponent(){
            
    }
}
