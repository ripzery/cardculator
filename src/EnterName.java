import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import net.miginfocom.swing.MigLayout;

public class EnterName extends JFrame{
    private JLabel message1,message2;
    private JTextField name;
    private JPanel p;
    private JButton b;
    private Object menu;
    
    public EnterName(){
        try{
              //UIManager.put("nimbusBase", new Color(210, 0, 210));
              for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
                  if("Nimbus".equals(info.getName())){
                      UIManager.setLookAndFeel(info.getClassName());
                      break;
                  }
              }
          }
          catch(Exception e){}
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
    public void addComponents(){
         message1 = new JLabel("Welcome to the Cardculator!");
         message2 = new JLabel("What's your name?");
         Font f = new Font("Arial",Font.BOLD,40);
         b = new JButton("Submit");
         b.setFont(f);
         message1.setFont(f);
         message2.setFont(f);
         name = new JTextField(30);
         name.setFont(f);
         p.add(message1,"gapleft 20%,gapright 20%,wrap 40px,width 60%");
         p.add(message2,"gapleft 30%,gapright 30%,wrap 40px,width 40%");
         p.add(name,"gapleft 25%,gapright 25%,wrap 40px,width 50%");
         p.add(b,"gapleft 40%,gapright 40%");
         validate();
    }
    
    public void addListener(){
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                throwPlayerName();
            }
        });
        name.addKeyListener(new KeyAdapter(){
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
}
