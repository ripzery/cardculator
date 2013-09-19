import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Logger;
import net.miginfocom.swing.MigLayout;

public class Level extends JFrame{
    private JPanel p;
    private JButton easy,normal,hard,back;
    private JLabel l;
    private Font f;
    private Menu menu;
    private Game game;
    private String player_name;
    
    public Level(){
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(320,70,800,600);
        setVisible(true);
        setTitle("Level");
        addWindowListener(new myWindowListener());
        p = (JPanel)getContentPane();
        p.setBackground(new Color(0xff,0xf0,0xa5));
        p.setLayout(new MigLayout());
        f = new Font("Arial",Font.BOLD,25);
        addComponents();
        addListener();
    }
    
    private void addComponents(){
        JPanel mode = new JPanel();
        mode.setLayout(new BorderLayout(0,40));
        easy = new myButton("Easy");
        normal = new myButton("Normal");
        hard = new myButton("Hard");
        back = new JButton("Back");
        
        easy.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Skidmark-Snail-icon.png"))));
        normal.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Burn-Snail-icon.png"))));
        hard.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Turbo-Snail-icon.png"))));
        
        
       
        back.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Arrow-Back-4-icon.png"))));
        back.setPreferredSize(new Dimension(170,80));
        back.setForeground(new Color(0x19,0x34,0x41));
        back.setBackground(new Color(0xff,0xb0,0x3b).darker());
        back.setIconTextGap(15);
        back.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 2));
        back.setFont(f);
        
        l = new JLabel("Select Mode");
        l.setFont(new Font("Arial",Font.BOLD,36));
        l.setForeground(new Color(0x8e,0x28,0x00));
        
        
        mode.add(easy,BorderLayout.NORTH);
        mode.add(normal,BorderLayout.CENTER);
        mode.add(hard,BorderLayout.SOUTH);
        
        
        mode.setOpaque(false);
        p.add(l,"center,wrap 20px");
        p.add(mode,"wrap 40px,width 40%,gapleft 30%,gapright 30%,id mode");
        p.add(back,"pos 10px (mode.y2+50px)");
        
    }
    
    private void addListener(){
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                 menu.setVisible(true);
                 dispose();
            }
        });
        
        easy.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    game = new Game(0);
                } catch (IOException ex) {
                    Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                game.setMenu(menu);
                game.setPlayerName(player_name);
                dispose();
            }
        });
        
        normal.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    game = new Game(1);
                } catch (IOException ex) {
                    Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                game.setMenu(menu);
                game.setPlayerName(player_name);
                dispose();
            }
        });
        
        hard.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    game = new Game(2);
                } catch (IOException ex) {
                    Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                game.setMenu(menu);
                game.setPlayerName(player_name);
                dispose();
            }
        });
        
    }
    
    public void setMenu(Object menu){
        this.menu = (Menu)menu;
    }
    
    private class myButton extends JButton{
        public myButton(String name){
            super(name);
            setPreferredSize(new Dimension(250,90));
            setForeground(new Color(0x19,0x34,0x41));
            setBackground(new Color(0xff,0xb0,0x3b));
            setIconTextGap(60);
            setBorder(BorderFactory.createEmptyBorder(0, -40, 2, 2));
            setFont(f);
        }
    }
    
    class myWindowListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e){
            System.exit(0);
        }
    }
    
    public void setPlayerName(String name){
        player_name = name;
    }
}
