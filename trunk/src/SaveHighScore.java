import java.awt.Font;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class SaveHighScore extends JFrame{
    private JLabel message1;
    private String score,player_name;
    public SaveHighScore(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(320,70,800,600);
        setVisible(true);
        setLayout(new MigLayout());
    }
    
    public void addComponents(){
        Font f = new Font("Arial",Font.BOLD,48);
        message1 = new JLabel("Your score is "+score+" point");
    }
    
    public void setScore(String score){
        this.score = score;
    }
    
    public void setPlayerName(String name){
        player_name = name;
    }
}
