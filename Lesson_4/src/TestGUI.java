import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestGUI extends JFrame {
    public TestGUI(){
        setBounds(500,500,500,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new Button(new Button().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
