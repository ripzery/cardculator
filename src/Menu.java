import javax.swing.*;
import javax.swing.UIManager.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import net.miginfocom.swing.MigLayout;

public class Menu extends JFrame{
    private int width=800,height=600;
    private JPanel main,buttonGroup;
    private JLabel heading;
    private JButton bstart,bhigh,bexit;
    private GridBagConstraints c;
    private Game g;
    public Menu(){
        addComponent();
        c = new GridBagConstraints();
        GridBagLayout frame_layout = new GridBagLayout();
        setLayout(frame_layout);
        
        c.gridy = 0;
        add(main,c);
        c.gridy = 1;
        c.insets = new Insets(120,0,0,0);
        add(buttonGroup,c);
        addListener();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(320,70);
        this.pack();
        setSize(width,height);
        getContentPane().setBackground(new Color(0x3e,0x60,0x6f));
        setVisible(true);
        setTitle("Cardculator");
    }
    public static void main(String args[]){
          try{
              //UIManager.put("nimbusBase", new Color(210, 0, 210));
              for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
                  if("Nimbus".equals(info.getName())){
                      UIManager.setLookAndFeel(info.getClassName());
                      break;
                  }
              }
              
          }
          catch(Exception e){}
          new Menu();
    }
    
    public void addComponent(){
        main = new JPanel();
        main.setLayout(new FlowLayout());
        heading = new JLabel("Welcome to Cardculator game!");
        Font h = new Font("Arial",Font.BOLD,36);
        heading.setFont(h);
        heading.setForeground(new Color(0x91,0xaa,0x9d));
        main.add(heading);
        main.setOpaque(false);
        buttonGroup = new JPanel();
        
        buttonGroup.setLayout(new BorderLayout(0,50));
        
        bstart = new myButton("Start game");
        bstart.setFont(new Font("Arial",Font.BOLD,20));
        Icon img = new ImageIcon("Minion-Happy-icon.png") ;  
       // Image newimg = img.getScaledInstance( 64, 64,  java.awt.Image.SCALE_SMOOTH ) ;  
        //ImageIcon icon = new ImageIcon( newimg );
        bstart.setIcon(img);
        bstart.setBorder(BorderFactory.createEmptyBorder(0, -10, 2, 2));
        bstart.setIconTextGap(10);
        
        buttonGroup.add(bstart,BorderLayout.NORTH);
       
        
        bhigh = new myButton("High score");
        img = new ImageIcon("Minion-Reading-icon.png");
        bhigh.setIcon(img);
        bhigh.setIconTextGap(10);
        bhigh.setFont(new Font("Arial",Font.BOLD,20));
        bhigh.setBorder(BorderFactory.createEmptyBorder(0, -10, 2, 2));
        buttonGroup.add(bhigh,BorderLayout.CENTER);
        
        bexit = new myButton("Exit game");
        img = new ImageIcon("Minion-Hello-icon.png");
        bexit.setIcon(img);
        bexit.setIconTextGap(10);
        bexit.setFont(new Font("Arial",Font.BOLD,20));
        bexit.setBorder(BorderFactory.createEmptyBorder(0, -20, 2, 2));
        buttonGroup.add(bexit,BorderLayout.SOUTH);
        
        buttonGroup.setOpaque(false);

    }
    public void addListener(){
        
      
    }
    
    public class myButton extends JButton{
        public myButton(String name){
            super(name);
            setPreferredSize(new Dimension(200,80));
            setForeground(new Color(0x19,0x34,0x41));
            setBackground(new Color(0x91,0xAA,0x9D));
            
            addButtonListener();
        }
        
        
        public void addButtonListener(){
            addMouseListener(new MouseAdapter(){
                public void mouseEntered(MouseEvent e){
                    
                }
                public void mouseExited(MouseEvent e){
                    
                }
            });
        }
    }
}
