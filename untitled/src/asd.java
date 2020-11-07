import javax.swing.*;

public class asd extends JFrame {
    public asd(){
        setBounds(500,500,500,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new JButton("•"));
        setVisible(true);
    }

    public static void main(String[] args) {
        new asd();
    }
}


