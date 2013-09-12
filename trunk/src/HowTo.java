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
    private JLabel heading;
    private JPanel phowto,p2;
    private Menu menu;
    private Font arial ;
    
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
        add(p2,"gapleft 25%,width 50%,gapright 25%,wrap 430px");
        p2.setOpaque(false);
       
        next = new JButton(" Next");
        next.setFont(new Font("Arial",Font.BOLD,20));
        /* Insert image here
        Icon img = new ImageIcon("next.png");
        bnext.setIcon(img);*/
        next.setBorder(BorderFactory.createEmptyBorder(0, -10, 2, 2));

        back = new JButton(" Back");
        back.setFont(new Font("Arial",Font.BOLD,20));
        back.setBorder(BorderFactory.createEmptyBorder(0, -10, 2, 2));
        
        
        phowto = new JPanel();
        phowto.setLayout(new MigLayout());
        //phowto.setSize(400, 200);
        phowto.add(back,"pos 10 10 80 50");
        phowto.add(next,"pos 690 10 760 50");
        phowto.setOpaque(false);
        add(phowto);
    }
    
    private void addListener(){
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                 menu.setVisible(true);
                 dispose();
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