import javax.swing.*;
import javax.swing.UIManager.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Kunanont
 */
public class HowTo extends JFrame{
    private JButton next,back;
    private JLabel heading,page1,page2,page3;
    private JPanel phowto,p2,pinfo;
    private Menu menu;
    private Font arial ;
    protected int counter = 0;
    
    public HowTo(){
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(320,70,800,600);
        setVisible(true);
        setTitle("How to play");
        addWindowListener(new HowTo.myWindowListener());
        
        getContentPane().setBackground(new Color(0xff,0xf0,0xa5));
        setLayout(new MigLayout());
        arial = new Font("Arial",Font.BOLD,36);
        addComponent();
        addListener();
        
    }
    
    private void addComponent(){
        p2 = new JPanel();
        heading = new JLabel("How to play this game!");
        heading.setFont(arial);
        heading.setForeground(new Color(0x8e,0x28,0x00));

        p2.add(heading);
        add(p2,"pos 200 10 400 40");
        p2.setOpaque(false);
       
        next = new JButton(" Next");
        next.setFont(new Font("Arial",Font.BOLD,20));
        next.setBorder(BorderFactory.createEmptyBorder(0, -10, 2, 2));

        back = new JButton(" Back");
        back.setFont(new Font("Arial",Font.BOLD,20));
        back.setBorder(BorderFactory.createEmptyBorder(0, -10, 2, 2));
       
        Icon img = new ImageIcon("info.png");
        Icon img2 = new ImageIcon("info2.png");
        Icon img3 = new ImageIcon("info3.png");
        page1 = new JLabel(img);
        page2 = new JLabel(img2);
        page3 = new JLabel(img3);
        
        page1.setVisible(true);
        page2.setVisible(false);
        page3.setVisible(false);
                
        phowto = new JPanel();
        phowto.setLayout(new MigLayout());
        
        phowto.add(page1,"pos 120 80 554 396");
        phowto.add(page2,"pos 120 80 554 396");
        phowto.add(page3,"pos 120 80 554 396");
        phowto.add(back,"pos 10 500 80 100");
        phowto.add(next,"pos 690 500 760 100");
        phowto.setOpaque(false);
        add(phowto);
    }
    
    private void addListener(){
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                 if(counter == 0){
                 menu.setVisible(true);
                 dispose();
                 }else if (counter ==1){
                     page1.setVisible(true);
                     page2.setVisible(false);                     
                     counter = 0;
                 }else{
                     page2.setVisible(true);
                     page1.setVisible(false);
                     page3.setVisible(false);
                     counter = 1;
                 }
            }
        });
        
        next.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(counter == 0){ 
                    page2.setVisible(true);
                    page1.setVisible(false);                    
                    counter = 1;
                }else if (counter == 1){
                    page3.setVisible(true);
                    page2.setVisible(false);
                    page1.setVisible(false);
                    counter = 2;
                    next.setText(" Done");
                }else {
                    next.setText(" Next");
                    page3.setVisible(false);
                    page1.setVisible(true);
                    menu.setVisible(true);
                    counter = 0;
                    dispose();
                }              
            }
        });
    }

    
    public void setMenu(Object menu){
        this.menu = (Menu)menu;  
    }
    
    
    
    class myWindowListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e){
            System.exit(0);
        }
    }
}