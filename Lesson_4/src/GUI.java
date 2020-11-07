import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    public GUI(TicTacToe game) {
        int rows = game.getRows();
        int columns = game.getColumns();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.height / 4, screenSize.width / 4, screenSize.height / 2, screenSize.height / 2);
        setTitle("TicTacToe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



        JButton[] buttons = new JButton[rows * columns];
        setLayout(new GridLayout(rows, columns));
        for (int i = 0; i < rows * columns; i++) {
            buttons[i] = new JButton("");
            int position = i;
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (game.toGo(position)){
                        buttons[position].setText("" + game.getMarkHuman());
                        game.move(position);
                        buttons[game.aiStep()].setText("" + game.getMarkAi());
                    }
                    game.aiMove();
                }
            });
            add(buttons[i]);
        }

        setVisible(true);
    }
}
