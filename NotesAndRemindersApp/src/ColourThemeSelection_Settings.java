import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.Color;

public class ColourThemeSelection_Settings {
    
    Color BackgroundColour = Color.WHITE;
    Color TextColour = Color.BLACK;
    private JComboBox<Color> ColourBackground;
    private JComboBox<Color> ColourForeground;
    public ColourThemeSelection_Settings(JFrame frame){
        //Color not showing String but rgb values
        //settings background doesn't update immediately after colour change
        Color[] AllowedColours = new Color[]{Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
        ColourBackground = new JComboBox<Color>(AllowedColours);
        ColourForeground = new JComboBox<Color>(AllowedColours);
        ColourBackground.setEditable(true);
        ColourBackground.setSelectedItem(Color.WHITE);
        ColourBackground.setBounds(55, 150, 160, 30);
        frame.add(ColourBackground);

        ColourBackground.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                App.BackgroundColour = (Color)ColourBackground.getSelectedItem();
                App.settings.repaint();
            }
        });
        
        ColourForeground.setEditable(true);
        ColourForeground.setSelectedItem(Color.WHITE);
        ColourForeground.setBounds(100, 150, 160, 30);
        frame.add(ColourForeground);

        ColourForeground.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                App.ForegroundColour = (Color)ColourForeground.getSelectedItem();
                App.settings.repaint();
            }
        });
    }
    
    public JComboBox<Color> getJComboBox_BackGround(){
        return ColourBackground;
    }

    public JComboBox<Color> getJComboBox_Foreground(){
        return ColourForeground;
    }
    
}
