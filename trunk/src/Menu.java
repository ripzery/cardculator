import javax.swing.*;
import javax.swing.UIManager.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.miginfocom.swing.MigLayout;

public class Menu extends JFrame{
    private int width=800,height=600;
    private JPanel main,buttonGroup,subButtonGroup;
    private JLabel heading,subheading;
    private JButton bstart,bhigh,bexit,bhow,bname;
    private GridBagConstraints c;
    private Level level;
    private HowTo howto;
    private Highscore high;
    private String player_name;
    private EnterName frame_name;
    
    public Menu(){        
        frame_name = new EnterName();
        frame_name.setMenu(this);
        //this.setContentPane(new JLabel(new ImageIcon("propractice/BackGround Menu.jpg")));
        
        addComponent();
        addListener();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(320,70,width,height);
        getContentPane().setBackground(new Color(0xff,0xf0,0xa5));
        setTitle("Cardculator");
        
        
    }
    public static void main(String args[]){
        
          try{
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
    
    private void addComponent(){
        main = new JPanel();
        main.setLayout(new MigLayout());
        while(player_name==null){System.out.println("");}
        heading = new JLabel("Hi "+player_name+", welcome to");
        subheading = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("logo.png"))));
        Font h = new Font("Arial",Font.BOLD,36);
        heading.setFont(h);
        heading.setForeground(new Color(0x8e,0x28,0x00));
        main.add(heading,"wrap");
        main.add(subheading);
        main.setOpaque(false);
        
        buttonGroup = new JPanel();
        buttonGroup.setLayout(new BorderLayout(10,30));
        bstart = new myButton("Start game");
        bstart.setFont(new Font("Arial",Font.BOLD,25));
        Icon img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Minion-Happy-icon.png")));
        bstart.setIcon(img);
        bstart.setBorder(BorderFactory.createEmptyBorder(0, -10, 2, 2));
        bstart.setIconTextGap(10);
        buttonGroup.add(bstart,BorderLayout.NORTH);
        
        bhigh = new myButton("High score");
        img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Minion-Reading-icon.png")));
        bhigh.setIcon(img);
        bhigh.setIconTextGap(10);
        bhigh.setFont(new Font("Arial",Font.BOLD,25));
        bhigh.setBorder(BorderFactory.createEmptyBorder(0, -10, 2, 2));
        buttonGroup.add(bhigh,BorderLayout.CENTER);
        
        subButtonGroup = new JPanel(new BorderLayout(10,0));
        bexit = new myButton("Exit game");
        img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Minion-Hello-icon.png")));
        bexit.setIcon(img);
        bexit.setIconTextGap(10);
        bexit.setFont(new Font("Arial",Font.BOLD,25));
        bexit.setBorder(BorderFactory.createEmptyBorder(0, -15, 2, 2));
        subButtonGroup.add(bexit,BorderLayout.CENTER);
        
        bname = new myButton("Change name");
        img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Minion-Curious-icon.png")));
        bname.setIcon(img);
        bname.setIconTextGap(10);
        bname.setFont(new Font("Arial",Font.BOLD,25));
        bname.setBorder(BorderFactory.createEmptyBorder(0, -15, 2, 2));
        subButtonGroup.add(bname,BorderLayout.WEST);
        subButtonGroup.setOpaque(false);
        
        buttonGroup.add(subButtonGroup,BorderLayout.SOUTH);
        
        bhow = new myButton("How to play");
        img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Minion-Amazed-icon.png")));
        bhow.setIcon(img);
        bhow.setIconTextGap(10);
        bhow.setFont(new Font("Arial",Font.BOLD,25));
        bhow.setBorder(BorderFactory.createEmptyBorder(0, -10, 2, 2));
        buttonGroup.add(bhow,BorderLayout.EAST);
        buttonGroup.setOpaque(false);
        c = new GridBagConstraints();
        GridBagLayout frame_layout = new GridBagLayout();
        setLayout(frame_layout);
        c.gridy = 0;
        add(main,c);
        c.gridy = 1;
        c.insets = new Insets(10,0,0,0);        
        add(buttonGroup,c);
    }
    private void addListener(){
        
        bexit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(null, "Bye bye "+player_name+"!");
                System.exit(0); 
            }
        });
        
        bstart.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(level==null){
                    level = new Level();
                }else{
                    level.setVisible(true);
                }
                level.setMenu(Menu.this);
                level.setPlayerName(player_name);
                Menu.this.dispose();
            }
        });

        bhow.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){               
                
                if(howto==null){
                    howto = new HowTo();
                }else{
                    howto.setVisible(true);
                }
                howto.setMenu(Menu.this);
                Menu.this.dispose();
            }        
    
    });
        bhigh.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            if(high==null){
                high = new Highscore();
            }else{
                high.setVisible(true);
                high.readFile();
            }
            high.setMenu(Menu.this);
            Menu.this.dispose();
        }
        });
        
        bname.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            if(frame_name==null){
                frame_name = new EnterName();
            }else{
                frame_name.setVisible(true);
            }
            frame_name.name.setText("");
            Menu.this.dispose();
        }
        });
        
    }
    
    public class myButton extends JButton{
        public myButton(String name){
            super(name);
            setPreferredSize(new Dimension(250,80));
            setForeground(new Color(0x19,0x34,0x41));
            setBackground(new Color(0xff,0xb0,0x3b));
        }
    }
    
    public void setPlayerName(String s){
        player_name = s;
        if(heading!=null){
            heading.setText("Hi "+player_name+", welcome to");
        }
        frame_name.dispose();
    }
}