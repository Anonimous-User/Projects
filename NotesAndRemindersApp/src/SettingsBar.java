import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

//TODO: save current setting to text file?

public class SettingsBar extends JPanel{

    private JFrame frame;
    private int SizeWindowX = 300;
    private int SizeWindowY = 600;
    private int StartX = App.SizeWindowX/3*2-50;
    private int SizeX = 50;
    private int SizeY = 50;

    public SettingsBar() {
        frame = new JFrame();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        TimezoneSelection_Settings.load(frame);
        ColourThemeSelection_Settings.load(frame);
        frame.add(this);
    }


    public void Display(Graphics g){
        g.drawRect(StartX, 0, SizeX, SizeY);
    }

    public boolean Collide(int MouseX, int MouseY){
        if(!(MouseX>=StartX&&MouseX<=StartX+SizeX)){
            return false;
        }
        if(!(MouseY>=0&&MouseY<=SizeY)){
            return false;
        }
        return true;
    }

    public void main(){
        frame.setBounds(0, 0, SizeWindowX, SizeWindowY);
        frame.setBounds(0, 0, SizeWindowX, SizeWindowY);
        frame.setTitle("");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
    }

    
    public void paint(Graphics g){
        SizeWindowX = frame.getWidth();
        SizeWindowY = frame.getHeight();
        g.setColor(App.BackgroundColour);
        g.fillRect(0, 0, SizeWindowX, SizeWindowY);
        g.setFont(App.Helvetica);
        g.setColor(App.ForegroundColour);
        g.drawString("Settings", App.rightShift, App.headerFontSize);
    }
}
