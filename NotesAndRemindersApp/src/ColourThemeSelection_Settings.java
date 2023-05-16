import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.Color;

public class ColourThemeSelection_Settings {
    private static JComboBox<String> ColourBackground;
    private static JComboBox<String> ColourForeground;

    /**initializes colour theme selection options */
    public static void load(JFrame frame){
        //initializes colours hashmap
        HashMap<String, Color> Colours = new HashMap<>();
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
        //initializes allowed colours list, the list that will be displayed to user
        String[] AllowedColours = new String[]{
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
        //initializes combo boxes
        ColourBackground = new JComboBox<String>(AllowedColours);
        ColourBackground.setEditable(true);
        ColourBackground.setSelectedItem("LIGHT_GRAY");
        ColourBackground.setBounds(55, 150, 160, 30);
        frame.add(ColourBackground);

        ColourBackground.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                App.BackgroundColour = Colours.get(ColourBackground.getSelectedItem());
                App.settings.repaint();
            }
        });
        
        ColourForeground = new JComboBox<String>(AllowedColours);
        ColourForeground.setEditable(true);
        ColourForeground.setSelectedItem("BLACK");
        ColourForeground.setBounds(55, 200, 160, 30);
        frame.add(ColourForeground);

        ColourForeground.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                App.ForegroundColour = Colours.get(ColourForeground.getSelectedItem());
                App.settings.repaint();
            }
        });
    }
    
}
