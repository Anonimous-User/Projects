import java.awt.event.WindowListener;
import javax.swing.JFrame;

public class Start {
    
    public static JFrame Mainframe = new JFrame();
    
    public static void main (String[] args)
    {
        Mainframe.setBounds(0, 0, 900, 900);
        Mainframe.setTitle("Project");
        Mainframe.setResizable(true);
        Mainframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //sets action of program for when its window is interacted with
        Mainframe.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {}
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                Mainframe.setState(JFrame.ICONIFIED);
            }
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {}
            @Override
            public void windowIconified(java.awt.event.WindowEvent e) {}
            @Override
            public void windowDeiconified(java.awt.event.WindowEvent e) {}
            @Override
            public void windowActivated(java.awt.event.WindowEvent e) {}
            @Override
            public void windowDeactivated(java.awt.event.WindowEvent e) {}
        });
        App r = new App();
        Mainframe.add(r);
        Mainframe.setVisible(true);
    }
}
