import javax.swing.*;
import javax.swing.UIManager.*;
import java.awt.*;

public class Menu extends JFrame{
    private int width=800,height=600;
    private JPanel main,buttonGroup;
    private JLabel heading;
    private JButton b1;
    public Menu(){
        addComponent();
        setLayout(null);
        add(main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width,height);
        setVisible(true);
        setTitle("Cardculator");
    }
    public static void main(String args[]){
        /*
         * Using UIManager package make the program good look and feel "Nimbus" :)
         */    
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
          new Menu(); 
    }
    
    public void addComponent(){
        main = new JPanel();
        main.setLayout(new FlowLayout());
        main.setBounds(0, 25, width, height);
        
        //main.setSize(400,400);
        /*
         * Example of create button in nimbus size configuration
         * b1 = new JButton("HELLOSDADSAD");
         * b1.setPreferredSize(new Dimension(200,100));
         * b1.putClientProperty("JComponent.sizeVariant", "regular");
         */
 
        heading = new JLabel("<html><p>Welcome to Cardculator game!</p></html>");
        Font h = new Font("Arial",Font.BOLD,20);
        heading.setFont(h);
        heading.setForeground(Color.black);
        main.add(heading);
    }
    
    public void addListener(){
        
    }
}
