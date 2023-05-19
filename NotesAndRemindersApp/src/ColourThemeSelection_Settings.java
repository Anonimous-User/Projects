import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.Color;

public class ColourThemeSelection_Settings {
    public HashMap<String, Color> Colours;
    //initializes allowed colours list, the list that will be displayed to user
    private String[] AllowedColours = new String[]{
        "WHITE",
        "BLACK",
        "DARK_GRAY",
        "GRAY",
        "LIGHT_GRAY",
        "BLUE",
        "CYAN",
        "GREEN",
        "RED",
        "ORANGE",
        "YELLOW",
        "MAGENTA",
        "PINK"
    };
    public JComboBox<String> ColourBackground = new JComboBox<String>(AllowedColours);
    public JComboBox<String> ColourForeground = new JComboBox<String>(AllowedColours);

    /**initializes colour theme selection options */
    public ColourThemeSelection_Settings(JFrame frame){
        //initializes combo boxes
        ColourBackground.setEditable(true);
        ColourBackground.setBounds(55, 150, 160, 30);
        frame.add(ColourBackground);
        
        ColourForeground.setEditable(true);
        ColourForeground.setBounds(55, 200, 160, 30);
        frame.add(ColourForeground);

        //initializes colours hashmap
        Colours = new HashMap<>();
        Colours.put("BLACK", Color.BLACK);
        Colours.put("BLUE", Color.BLUE);
        Colours.put("CYAN", Color.CYAN);
        Colours.put("DARK_GRAY", Color.DARK_GRAY);
        Colours.put("GRAY", Color.GRAY);
        Colours.put("GREEN", Color.GREEN);
        Colours.put("LIGHT_GRAY", Color.LIGHT_GRAY);
        Colours.put("MAGENTA", Color.MAGENTA);
        Colours.put("ORANGE", Color.ORANGE);
        Colours.put("PINK", Color.PINK);
        Colours.put("RED", Color.RED);
        Colours.put("WHITE", Color.WHITE);
        Colours.put("YELLOW", Color.YELLOW);

        
    }
    
    public void setSelected(String foreground, String background){
        ColourBackground.setSelectedItem(background);
        ColourForeground.setSelectedItem(foreground);
        App.BackgroundColour = Colours.get(ColourBackground.getSelectedItem());
        App.ForegroundColour = Colours.get(ColourForeground.getSelectedItem());
    }

    public void addActionlisteners(){
        ColourBackground.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                App.BackgroundColour = Colours.get(ColourBackground.getSelectedItem());
                App.settings.repaint();
            }
        });

        ColourForeground.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                App.ForegroundColour = Colours.get(ColourForeground.getSelectedItem());
                App.settings.repaint();
            }
        });
    }
}
