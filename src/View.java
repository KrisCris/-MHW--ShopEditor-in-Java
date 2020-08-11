import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class View extends JFrame implements Observer {
    private JMenuBar menuBar;

    private JButton add;
    private JButton remove;



    @Override
    public void update(Observable o, Object arg) {

    }
}
