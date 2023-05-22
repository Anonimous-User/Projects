import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SettingsBar extends JPanel{

    public ColourThemeSelection_Settings CTSelect;
    private JFrame frame;
    private int SizeWindowX = 300;
    private int SizeWindowY = 600;
    private int StartX = App.SizeWindowX/3*2-50;
    private int SizeX = 50;
    private int SizeY = 50;
    private Image SettingsButton;

    /**initializes settings bar with timezone selection and colour theme selection */
    public SettingsBar() throws IOException {
        frame = new JFrame();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        TimezoneSelection_Settings.load(frame);
        CTSelect = new ColourThemeSelection_Settings(frame);
        makeSettingsFile();
        readSettingsFile();
        CTSelect.addActionlisteners();
        frame.add(this);
        SettingsButton = ImageIO.read(new File(".\\src\\PNGs\\SettingsButton.png"));
    }

    /**reads data from settings.txt and saves to settings bar.
     * <p> first line is background, second line is foreground
     */
    public void readSettingsFile() throws FileNotFoundException{
        File myObj = new File("settings.txt");
        Scanner myReader = new Scanner(myObj);

        String back = myReader.nextLine();
        String fore = myReader.nextLine();
        CTSelect.setSelected(fore, back);

        myReader.close();
    }

    /**writes given string str into file, overwrites everything in file */
    public void writeSettingsFile(String str){
        try {
            FileWriter myWriter;
            myWriter = new FileWriter("settings.txt");
            myWriter.write(str);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**creates new Settings.txt at root directory with defaulted value BLACK for background and LIGHT_GRAY for foreground */
    public void makeSettingsFile() throws IOException{
        File myObj = new File("settings.txt");
        if (myObj.createNewFile()) {
            writeSettingsFile("LIGHT_GRAY" + "\n" + "BLACK");
        }
    }

    /**displays settings button at 2/3 length of screen width */
    public void Display(Graphics g){
        StartX = App.SizeWindowX/3*2-50;
        g.drawImage(SettingsButton, StartX, 0, null);
    }

    /**checks for collision of block with mouse */
    public boolean Collide(int MouseX, int MouseY){
        if(!(MouseX>=StartX&&MouseX<=StartX+SizeX)){
            return false;
        }
        if(!(MouseY>=0&&MouseY<=SizeY)){
            return false;
        }
        return true;
    }

    /**displays settings menu on seperate {@link JFrame} */
    public void main(){
        frame.setBounds(0, 0, SizeWindowX, SizeWindowY);
        frame.setBounds(0, 0, SizeWindowX, SizeWindowY);
        frame.setTitle("");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
    }

    /**paints background using {@link Graphics}*/
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
