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
    private JPanel phowto;
    private Menu menu;
    private Font arial ;
    
    public HowTo(){
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(320,70,800,600);
        setVisible(true);
        setTitle("How to play");
        addWindowListener(new HowTo.myWindowListener());
        
        phowto = (JPanel)getContentPane();
        phowto.setBackground(new Color(0xff,0xf0,0xa5));
        phowto.setLayout(new MigLayout());
        arial = new Font("Arial",Font.BOLD,36);
        addComponent();
        addListener();
        
    }
    
    private void addComponent(){
        
        heading = new JLabel("How to play this game!");
        heading.setFont(arial);
        heading.setForeground(new Color(0x8e,0x28,0x00));
        
        phowto.add(heading);
        phowto.setOpaque(false);
       
        next = new JButton(" Next");
        next.setFont(new Font("Arial",Font.BOLD,20));
        /* Insert image here
        Icon img = new ImageIcon("next.png");
        bnext.setIcon(img);*/
        next.setBorder(BorderFactory.createEmptyBorder(0, -10, 2, 2));

        back = new JButton(" Back");
        back.setFont(new Font("Arial",Font.BOLD,20));
        back.setBorder(BorderFactory.createEmptyBorder(0, -10, 2, 2));
        
        phowto.add(heading,"wrap 82%,width 30%,gapleft 25%,gapright 35%");
        phowto.add(back,BorderLayout.SOUTH);
        phowto.add(next,"width 10%,gapleft 80%,gapright 10%");
        //phowto.add(back,"width 10%,gapright 80%,gapleft 10%");
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