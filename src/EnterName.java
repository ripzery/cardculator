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
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class EnterName extends JFrame{
    private JLabel message1,message2,bg;
    public JTextField name;
    private JPanel p;
    private JButton b;
    private Object menu;
    
    public EnterName(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocation(320,70);
        setSize(860,600);  
        
        p = (JPanel)getContentPane();
        p.setLayout(new MigLayout("insets 100 0 200 100"));
        p.setBackground(Color.white);
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
         message1.setLayout(null);
         message2.setFont(f);
         Border border = new RoundedCornerBorder();
         name = new JTextField(30);
         name.setFont(f);
         name.setBorder(border);
         
         ImageIcon img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("bg.png")));
         bg = new JLabel();
         bg.setIcon(img);
         bg.setHorizontalAlignment(JLabel.CENTER);
         bg.setOpaque(false);   
         
         p.add(message1,"gapleft 10%,gapright 20%,wrap 40px,width 60%");
         p.add(message2,"gapleft 20%,gapright 30%,wrap 40px,width 40%");
         p.add(name,"gapleft 15%,gapright 25%,wrap 40px,width 50%");
         p.add(b,"gapleft 30%,gapright 40%");
         p.add(bg,"pos 1px 1px");
         validate();
    }
    
    private void addListener(){
        b.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(name.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Please enter name at least 1 character.");
                }
                else{
                    throwPlayerName();
                }
            }
        });
        name.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(name.getText().equals("")&&e.getKeyCode()==KeyEvent.VK_ENTER){
                    JOptionPane.showMessageDialog(null, "Please enter name at least 1 character.");
                }
                else if(e.getKeyCode()==KeyEvent.VK_ENTER&&!name.getText().equals("")){
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
