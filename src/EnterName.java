import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;

public class EnterName extends JFrame{
    private JLabel message1,message2;
    private JTextField name;
    private JPanel p;
    private JButton b;
    private Object menu;
    
    public EnterName(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocation(320,70);
        setSize(860,600);
        
        p = (JPanel)getContentPane();
        p.setBackground(new Color(0xff,0xf0,0xa5));
        p.setLayout(new MigLayout("insets 100 0 200 100"));
        addComponents();
        addListener();
        setTitle("Enter your name");
        
    }
    private void addComponents(){
         message1 = new JLabel("Welcome to the Cardculator!");
         message2 = new JLabel("What's your name?");
         Font f = new Font("Arial",Font.BOLD,40);
         b = new JButton("Submit");
         b.setFont(f);
         message1.setFont(f);
         message2.setFont(f);
         Border border = new RoundedCornerBorder();
         name = new JTextField(30);
         name.setFont(f);
         name.setBorder(border);
         p.add(message1,"gapleft 20%,gapright 20%,wrap 40px,width 60%");
         p.add(message2,"gapleft 30%,gapright 30%,wrap 40px,width 40%");
         p.add(name,"gapleft 25%,gapright 25%,wrap 40px,width 50%");
         p.add(b,"gapleft 40%,gapright 40%");
         validate();
    }
    
    private void addListener(){
        b.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                throwPlayerName();
            }
        });
        name.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    throwPlayerName();
                }
            }
        });
    }
    
    public void throwPlayerName(){     
        Menu a = (Menu)menu;
        a.setVisible(true);
        a.setPlayerName(name.getText());
   }
    
    public void setMenu(Object menu){
        this.menu = menu;
    }
    
    class RoundedCornerBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int r = height-1;
            RoundRectangle2D round = new RoundRectangle2D.Float(x, y, width-1, height-1, r, r);
            Container parent = c.getParent();
            if(parent!=null) {
                g2.setColor(parent.getBackground());
                Area corner = new Area(new Rectangle2D.Float(x, y, width, height));
                corner.subtract(new Area(round));
                g2.fill(corner);
            }
            g2.setColor(Color.GRAY);
            g2.draw(round);
            g2.dispose();
        }
        @Override public Insets getBorderInsets(Component c) {
            return new Insets(4, 8, 4, 8);
        }
        @Override public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = 8;
            insets.top = insets.bottom = 4;
            return insets;
        }
    }
}
