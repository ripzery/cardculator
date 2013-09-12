import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class Highscore extends JFrame{
    private JLabel message1,message2;
    private JPanel a,b,d;
    private JComboBox mode,list;
    private String line,argument[];
    private ArrayList<KeepScore> easy= new ArrayList<>();
    private ArrayList<KeepScore> normal= new ArrayList<>();
    private ArrayList<KeepScore> hard= new ArrayList<>();
    private Menu menu;
    private JButton back;
    private JSplitPane split;
    private JList jlist;
    private JScrollPane scroll; 
    
    public Highscore(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(320,70,800,600);
        setVisible(true);
        getContentPane().setBackground(new Color(0xff,0xf0,0xa5));
        getContentPane().setLayout(new MigLayout());
        try {
            Scanner read = new Scanner(new File("highscore.txt"));
            while(read.hasNext()){
                line = read.nextLine();
                argument = line.split(" ");
                switch (argument[1]) {
                    case "Easy":
                        easy.add(new KeepScore(argument[0],Integer.parseInt(argument[2])));
                        break;
                    case "Normal":
                        normal.add(new KeepScore(argument[0],Integer.parseInt(argument[2])));
                        break;
                    default:
                        hard.add(new KeepScore(argument[0],Integer.parseInt(argument[2])));
                        break;
                }
            }
        } catch (FileNotFoundException ex) {System.err.println(ex);}
        Collections.sort(easy);
        Collections.sort(normal);
        Collections.sort(hard);
        addComponents();
        addListener();
    }
    
    public void addComponents(){
        Font f = new Font("Arial",Font.BOLD,36);
        a = new JPanel();
        b = new JPanel(new MigLayout());
        d = new JPanel();
        d.setOpaque(false);
        
        message1 = new JLabel("High score");
        message1.setFont(f);
        a.add(message1);
        a.setOpaque(false);
        
        String s[] = {"Easy","Normal","Hard"};
        message2 = new JLabel("Select mode : ");
        message2.setFont(new Font("Arial",Font.BOLD,20));
        mode = new JComboBox(s);
        mode.setFont(new Font("Arial",Font.BOLD,15));
        b.add(message2);
        b.add(mode);
        
        jlist = new JList(Highscore.this.generateInfo(easy).toArray());
        jlist.setFont(new Font("Arial",Font.BOLD,15));
        jlist.setVisibleRowCount(10);
        jlist.setBackground(new Color(0xff,0xb0,0x3b));
        
        scroll = new JScrollPane(jlist);
        scroll.setBackground(new Color(0xff,0xb0,0x3b));
        scroll.setSize(400,300);
        d.add(scroll);
        
        mode.setPreferredSize(new Dimension(100,50));
        
        b.setOpaque(false);
        
        validate();
        
        add(a,"gapleft 40%,width 20%,gapright 40%,wrap 20px");
        add(b,"gapleft 10px,wrap 20px"); 
        add(d,"wrap 50px,gapleft 25%,width 50%,gapright 25%");

        back = new JButton("Back");
        back.setBackground(new Color(0xff,0xb0,0x3b).darker());
        back.setForeground(new Color(0x19,0x34,0x41));
        back.setPreferredSize(new Dimension(100,70));
        back.setFont(new Font("Arial",Font.BOLD,20));
        back.setHorizontalTextPosition(SwingConstants.CENTER);
        
        add(back,"gapleft 2%");
    }
    
    public void addListener(){
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                menu.setVisible(true);
                Highscore.this.dispose();
            }
        });
        
        mode.addItemListener(new ItemListener(){
           
            public void itemStateChanged(ItemEvent ef){
                if(mode.getSelectedIndex()==0){
                    d.remove(0);
                    jlist.setListData(Highscore.this.generateInfo(easy).toArray());
                    jlist.setFont(new Font("Arial",Font.BOLD,15));
                    jlist.setVisibleRowCount(10);
                    scroll = new JScrollPane(jlist);
                    d.add(scroll);
                    d.repaint();
                }else if(mode.getSelectedIndex()==1){
                    d.remove(0);
                    jlist.setListData(Highscore.this.generateInfo(normal).toArray());
                    jlist.setFont(new Font("Arial",Font.BOLD,15));
                    jlist.setVisibleRowCount(10);
                    scroll = new JScrollPane(jlist);
                    d.add(scroll);
                    d.repaint();
                }else{
                    d.remove(0);
                    jlist.setListData(Highscore.this.generateInfo(hard).toArray());
                    jlist.setFont(new Font("Arial",Font.BOLD,15));
                    jlist.setVisibleRowCount(10);
                    scroll = new JScrollPane(jlist);
                    d.add(scroll);
                    d.repaint();
                }
                validate();
            }
        });
    }
    
    public void setMenu(Menu m){
        menu = m;
    }
    
    public ArrayList<String> generateInfo(ArrayList<KeepScore> a){
        ArrayList<String> k = new ArrayList<>();
        for(int i=0;i<a.size();i++){
            String s = a.get(i).getName()+" gets "+a.get(i).getScore()+" points.";
            k.add(s);
        }
        return k;
    }
    
    public class KeepScore implements Comparable{
        private int score;
        private String name;
        public KeepScore(String name,int score){
            this.name = name;
            this.score = score;
        }
        public String getName(){
           return name;
        }
        
        public String getScore(){
            return Integer.toString(score);
        }
        
        @Override
        public int compareTo(Object o){
            KeepScore a = (KeepScore)o;
            if(this.score>a.score){
                return -1;
            }else if(this.score<a.score){
                return 1;
            }
            else{
                return 0;
            }
        }
    }
}
