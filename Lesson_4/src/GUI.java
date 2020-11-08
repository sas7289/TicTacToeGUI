import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI extends JFrame {
    JButton[] buttons;

    public GUI(TicTacToe game) {
        int rows = game.getRows();
        int columns = game.getColumns();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowHeight = screenSize.height/4;
        int windowWidth = screenSize.width/4;
        setBounds(screenSize.height / 4, screenSize.width / 4, windowWidth, windowHeight);
        setTitle("TicTacToe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        game.initNum();
        Panel changeMarkPanel = new Panel();
        Button changePlayerFirst = new Button("ЯЯЯЯЯ!!!");
        Button changeAiFirst = new Button("Железка!!!");
        TextField statusGame = new TextField();
        statusGame.setVisible(false);
        changePlayerFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setMarks(0);
                changePlayerFirst.setVisible(false);;
                changeAiFirst.setVisible(false);
                statusGame.setVisible(true);

            }
        });
        changeAiFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setMarks(1);
                changePlayerFirst.setVisible(false);;
                changeAiFirst.setVisible(false);
                statusGame.setVisible(true);
                int targetPositionAi = getPosition(game.aiMove());
                buttons[targetPositionAi].setText(String.valueOf(game.getMarkAi()));
            }
        });
        changeMarkPanel.add(changePlayerFirst);
        changeMarkPanel.add(changeAiFirst);
        changeMarkPanel.add(statusGame);




        buttons = new JButton[rows * columns];

        Panel gameField = new Panel(new GridLayout(rows, columns));
        for (int i = 0; i < rows * columns; i++) {
            buttons[i] = new JButton("");
            buttons[i].setPreferredSize(new Dimension(50,50));
            buttons[i].setName(String.valueOf(i));
            int position = i;
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (game.humanMove(Integer.parseInt(buttons[position].getName()))){
                        buttons[position].setText(String.valueOf(game.getMarkHuman()));
                        int[] dot = game.getDotXY(position);
                        if (game.checkEndGame(dot[0], dot[1], game.markHuman, game.countDotToWin)){
                            statusGame.setText("ЧЕЛОВЕК ПОБЕДИЛ!!!!");
                            return;
                        }
                    }
                    else {
                        return;
                    }
//                    int targetPositionAi = getPosition(game.aiMove());
                    int[] aiDot = game.aiMove();
                    buttons[game.getPosition(aiDot)].setText(String.valueOf(game.getMarkAi()));
                    if (game.checkEndGame(aiDot[0], aiDot[1], game.markAi, game.countDotToWin)){
                        statusGame.setText("ЖЕЛЕЗЯКА ПОБЕДИЛА!!!!");
                        return;
                    }
                }


            });
            gameField.add(buttons[i]);
        }
        add(changeMarkPanel);
        add(gameField);

        setVisible(true);
    }


    private int getPosition (int[] dot){
        return dot[0] * dot[1] + dot[1];
    }

    public void setButtonText (int i, char mark){
        buttons[i].setText(String.valueOf(mark));
    }
}
