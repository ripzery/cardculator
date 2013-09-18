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
    private JLabel heading,page1,page2,page3,page4,page5,icon;
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
        heading.setIcon(new ImageIcon("Minion-Amazed-icon.png"));
        p2.add(heading);
        add(p2,"width 60%,gapleft 20%,gapright 20%,wrap,id main");
        p2.setOpaque(false);
       
        next = new JButton(" Next");
        next.setFont(new Font("Arial",Font.BOLD,20));
        next.setPreferredSize(new Dimension(70,50));
        next.setBorder(BorderFactory.createEmptyBorder(0, -10, 2, 2));

        back = new JButton(" Back");
        back.setFont(new Font("Arial",Font.BOLD,20));
        back.setPreferredSize(new Dimension(70,50));
        back.setBorder(BorderFactory.createEmptyBorder(0, -10, 2, 2));
       
        Icon img = new ImageIcon("info.png");
        Icon img2 = new ImageIcon("info2.png");
        Icon img3 = new ImageIcon("info3.png");
        Icon img4 = new ImageIcon("info4.png");
        Icon img5 = new ImageIcon("info5.png");
        page1 = new JLabel(img);
        page2 = new JLabel(img2);
        page3 = new JLabel(img3);
        page4 = new JLabel(img4);
        page5 = new JLabel(img5);
        
        page1.setVisible(true);
        page2.setVisible(false);
        page3.setVisible(false);
        page4.setVisible(false);
        page5.setVisible(false);
                
        phowto = new JPanel();
        phowto.setLayout(new FlowLayout());
        
        phowto.add(page1);
        phowto.add(page2);
        phowto.add(page3);
        phowto.add(page4);
        phowto.add(page5);
        add(back,"pos 10 500");
        add(next,"pos 700 500");
        phowto.setOpaque(false);
        add(phowto,"width 80%,gapleft 10%,gapright 10%");
    }
    
    private void addListener(){
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                 next.setText(" Next");
                 if(counter == 0){
                 menu.setVisible(true);
                 dispose();
                 }else if (counter == 1){
                     page1.setVisible(true);
                     page2.setVisible(false);                     
                     counter = 0;
                 }else if (counter == 2){
                     page2.setVisible(true);
                     page1.setVisible(false);
                     page3.setVisible(false);
                     counter = 1;
                 }else if (counter == 3){
                     page3.setVisible(true);
                     page1.setVisible(false);
                     page2.setVisible(false);
                     page4.setVisible(false);
                     counter = 2;
                 }else {
                     page4.setVisible(true);
                     page1.setVisible(false);
                     page2.setVisible(false);
                     page3.setVisible(false);
                     page5.setVisible(false);
                     counter = 3;
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
                }else if (counter == 2){
                    page4.setVisible(true);
                    page3.setVisible(false);
                    counter = 3;
                }else if (counter == 3){
                    next.setText(" Done");
                    page5.setVisible(true);
                    page4.setVisible(false);
                    counter = 4;
                }else{
                    next.setText(" Next");
                    page5.setVisible(false);
                    page1.setVisible(true);
                    menu.setVisible(true);
                    counter = 0;
                    dispose();
                }              
            }
        });
    }

    
    public void setMenu(Object menu){
        if(menu!=null){
            next.setText(" Next");
        }
        this.menu = (Menu)menu;  
    }
    
    
    
    class myWindowListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e){
            System.exit(0);
        }
    }
}