import javax.swing.*;
import javax.swing.UIManager.*;
import java.awt.*;
import static java.awt.Component.CENTER_ALIGNMENT;
import net.miginfocom.swing.MigLayout;

public class Menu extends JFrame{
    private int width=800,height=600;
    private JPanel main,buttonGroup;
    private JLabel heading;
    private JButton bstart,bhigh,bexit;
    private GridBagConstraints c;
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
 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(320,70);
        setSize(width,height);
        getContentPane().setBackground(new Color(0x3e,0x60,0x6f));
        setVisible(true);
        setTitle("Cardculator");
    }
    public static void main(String args[]){
        /*
         * Using UIManager package make the program good look and feel "Nimbus" :)
         */    
        /*try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }*/
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
        
        buttonGroup.setLayout(new BorderLayout(50,50));
        bstart = new JButton("Start game");
        bstart.setPreferredSize(new Dimension(200,50));
        bstart.setForeground(new Color(0x19,0x34,0x41));
        bstart.setBackground(new Color(0x91,0xAA,0x9D));
        buttonGroup.add(bstart,BorderLayout.NORTH);
       
        
        bhigh = new JButton("High score");
        bhigh.setPreferredSize(new Dimension(200,50));
        bhigh.setForeground(new Color(0x19,0x34,0x41));
        bhigh.setBackground(new Color(0x91,0xAA,0x9D));
        buttonGroup.add(bhigh,BorderLayout.CENTER);
        
        bexit = new JButton("Exit");
        bexit.setPreferredSize(new Dimension(200,50));
        bexit.setForeground(new Color(0x19,0x34,0x41));
        bexit.setBackground(new Color(0x91,0xAA,0x9D));
        buttonGroup.add(bexit,BorderLayout.SOUTH);
        buttonGroup.setOpaque(false);

    }
    
    public void addListener(){
        
    }
}
