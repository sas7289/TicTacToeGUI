import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    JButton[] buttons;
    TicTacToe currentGame;
    TextField statusGame;
    PopupFactory popupFactory;
    Popup popup;
    int tempRow;
    int tempCol;
    Panel gameField;

    public GUI(TicTacToe currentGame) {
        this.currentGame = currentGame;
        int rows = currentGame.getRows();
        int columns = currentGame.getColumns();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowHeight = screenSize.height/4;
        int windowWidth = screenSize.width/4;
        setBounds(screenSize.height / 4, screenSize.width / 4, windowWidth, windowHeight);
        setTitle("TicTacToe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        currentGame.initNum();


        gameField = new Panel(new GridLayout(rows, columns));



        TextField countRows = new TextField(2);
        TextField countCol = new TextField(2);
        JPanel preference = new JPanel();
        JPanel showPreference = new JPanel();
        Scrollbar scrollbarRow = new Scrollbar(Scrollbar.VERTICAL, 5, 1, 0, 100);
        scrollbarRow.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                tempRow = scrollbarRow.getValue();
                countRows.setText(String.valueOf(tempRow));
            }
        });
        Scrollbar scrollbarCol = new Scrollbar(JScrollBar.VERTICAL, 5, 1, 1, 100);
        scrollbarCol.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                tempCol = scrollbarCol.getValue();
                countCol.setText(String.valueOf(tempCol));
            }
        });
        Button setPreference = new Button("Начать игру");
        setPreference.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reboot();
            }
        });


        preference.add(scrollbarRow);
        preference.add(countRows);
        showPreference.add(scrollbarCol);
        showPreference.add(countCol);
        showPreference.add(setPreference);

        //Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Игра");
        JMenuItem newGame = new JMenuItem("Новая игра");
        JMenuItem changeSizeField = new JMenuItem("Изменить размер игрового поля");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reboot();
            }
        });
        menu.add(newGame);
        menu.add(changeSizeField);
        menuBar.add(menu);

        Panel changeMarkPanel = new Panel();
        Button changePlayerFirst = new Button("ЯЯЯЯЯ!!!");
        Button changeAiFirst = new Button("Железка!!!");
        statusGame = new TextField();
        statusGame.setVisible(false);
        changePlayerFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentGame.setMarks(0);
                changePlayerFirst.setVisible(false);;
                changeAiFirst.setVisible(false);
                statusGame.setVisible(true);

            }
        });
        changeAiFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentGame.setMarks(1);
                changePlayerFirst.setVisible(false);;
                changeAiFirst.setVisible(false);
                statusGame.setVisible(true);
                int targetPositionAi = getPosition(currentGame.aiMove());
                buttons[targetPositionAi].setText(String.valueOf(currentGame.getMarkAi()));
            }
        });
        changeMarkPanel.add(changePlayerFirst);
        changeMarkPanel.add(changeAiFirst);
        changeMarkPanel.add(statusGame);





        Panel gameField = createGameField(rows, columns);

        setJMenuBar(menuBar);
        add(preference);
        add(showPreference);
        add(changeMarkPanel);
        add(gameField);

        setVisible(true);
    }


    private Panel createGameField(int rows, int columns) {
        gameField.removeAll();
        buttons = new JButton[rows * columns];
        for (int i = 0; i < rows * columns; i++) {
            buttons[i] = new JButton("");
            buttons[i].setPreferredSize(new Dimension(50,50));
            buttons[i].setName(String.valueOf(i));
            int position = i;
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentGame.humanMove(Integer.parseInt(buttons[position].getName()))){
                        buttons[position].setText(String.valueOf(currentGame.getMarkHuman()));
                        int[] dot = currentGame.getDotXY(position);
                        if (currentGame.checkEndGame(dot[0], dot[1], currentGame.markHuman, currentGame.countDotToWin)){
                            statusGame.setText("ЧЕЛОВЕК ПОБЕДИЛ!!!!");
                            return;
                        }
                    }
                    else {
                        return;
                    }
//                    int targetPositionAi = getPosition(currentGame.aiMove());
                    int[] aiDot = currentGame.aiMove();
                    buttons[currentGame.getPosition(aiDot)].setText(String.valueOf(currentGame.getMarkAi()));
                    if (currentGame.checkEndGame(aiDot[0], aiDot[1], currentGame.markAi, currentGame.countDotToWin)){
                        statusGame.setText("ЖЕЛЕЗЯКА ПОБЕДИЛА!!!!");
                        return;
                    }
                }


            });
            gameField.add(buttons[i]);
        }
        return gameField;
    }


    private void reboot (){
        currentGame.reboot(tempRow, tempCol);
        gameField = createGameField(tempRow, tempCol);
        gameField.revalidate();
    }

    private int getPosition (int[] dot){
        return dot[0] * dot[1] + dot[1];
    }

    public void setButtonText (int i, char mark){
        buttons[i].setText(String.valueOf(mark));
    }
}
