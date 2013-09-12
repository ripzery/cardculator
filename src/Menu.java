import javax.swing.*;
import javax.swing.UIManager.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame{
    private int width=800,height=600;
    private JPanel main,buttonGroup;
    private JLabel heading;
    private JButton bstart,bhigh,bexit,bhow;
    private GridBagConstraints c;
    private Level level;
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
        main.setLayout(new FlowLayout());
        while(player_name==null){System.out.println("");}
        heading = new JLabel("Hi "+player_name+", welcome to the Cardculator!");
        Font h = new Font("Arial",Font.BOLD,36);
        heading.setFont(h);
        heading.setForeground(new Color(0x8e,0x28,0x00));
        main.add(heading);
        main.setOpaque(false);
        
        buttonGroup = new JPanel();
        buttonGroup.setLayout(new BorderLayout(0,50));
        bstart = new myButton("Start game");
        bstart.setFont(new Font("Arial",Font.BOLD,25));
        Icon img = new ImageIcon("Minion-Happy-icon.png");
        bstart.setIcon(img);
        bstart.setBorder(BorderFactory.createEmptyBorder(0, -10, 2, 2));
        bstart.setIconTextGap(10);
        buttonGroup.add(bstart,BorderLayout.NORTH);
        
        bhigh = new myButton("High score");
        img = new ImageIcon("Minion-Reading-icon.png");
        bhigh.setIcon(img);
        bhigh.setIconTextGap(10);
        bhigh.setFont(new Font("Arial",Font.BOLD,25));
        bhigh.setBorder(BorderFactory.createEmptyBorder(0, -10, 2, 2));
        buttonGroup.add(bhigh,BorderLayout.CENTER);
        
        bexit = new myButton("Exit game");
        img = new ImageIcon("Minion-Hello-icon.png");
        bexit.setIcon(img);
        bexit.setIconTextGap(10);
        bexit.setFont(new Font("Arial",Font.BOLD,25));
        bexit.setBorder(BorderFactory.createEmptyBorder(0, -15, 2, 2));
        buttonGroup.add(bexit,BorderLayout.SOUTH);
        
        bhow = new myButton("How to play");
        img = new ImageIcon("Minion-Amazed-icon.png");
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
        c.insets = new Insets(80,0,0,0);
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
                Menu.this.dispose();
            }
        });
        /*
        bhigh.addActionListener(new ActionListener()){
        
    }
        bhow.addActionListener(new ActionListener()){
    
    }*/
    }
    
    public class myButton extends JButton{
        public myButton(String name){
            super(name);
            setPreferredSize(new Dimension(250,100));
            setForeground(new Color(0x19,0x34,0x41));
            setBackground(new Color(0xff,0xb0,0x3b));
        }
    }
    
    public void setPlayerName(String s){
        player_name = s; 
        frame_name.dispose();
    }
}