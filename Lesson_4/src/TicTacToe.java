import java.util.Scanner;

public class TicTacToe {
    int size;
    int rows;
    int columns;
    char[][] map;
    int step;
    int countDotToWin;
    int maxStep;
    final int COUNT_WIN_LINE = 4;
    final char DOT_EMPTY = '•';
    final char DOT_X = 'X';
    final char DOT_O = 'O';
    final char FIRST_SYMBOL = 'Ϯ';
    final String EMPTY = " ";

    static Scanner scanner = new Scanner(System.in);


    public TicTacToe (){
        System.out.println("Введите размер поля");
        rows = scanner.nextInt();//Проверить на правильность ввода!!!
        columns = scanner.nextInt();
    }
    public TicTacToe (int row, int col){
        rows = row;
        columns = col;
    }

    public void turnGame() {
        initNum();
        initMap();
        printMap();
        playGame();
    }

    private void initNum() {
        step = 0;
        maxStep = rows * columns;
        if (rows >= 3 && rows < 6 && columns >= 3 && columns < 6){
            countDotToWin = 3;
        }
        if (rows >= 6 && rows < 11  && columns >= 6 && columns < 11){
            countDotToWin = 4;
        }
        if (rows >= 11 && columns >= 11){
            countDotToWin = 5;
        }
    }

    private void initMap() {
        map = new char[rows][columns];
        for (int i = 0; i < rows; i ++){
            for (int j = 0; j < columns; j++){
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    private void printMap() {
        printHead();
        printField();
    }

    private void printHead() {
        System.out.print(FIRST_SYMBOL + EMPTY);
        for (int i = 0; i < columns; i++){
            printMapNumber(i);
        }
        System.out.println();
    }

    private void printMapNumber(int i) {
        System.out.print(i + 1 + EMPTY);
    }

    private void printField() {
        for (int i = 0; i < rows; i++){
            printMapNumber(i);
            for (int j = 0; j < columns; j++){
                System.out.print(map[i][j] + EMPTY);
            }
            System.out.println();
        }
    }

    private void playGame() {
        System.out.println("Кто ходит первым?\n1. Конечно же я\n2. Дадим шанс железке");
        int first = scanner.nextInt() - 1;
        boolean endGame = false;
        while (!endGame) {
            move(first);
        }
    }

    private void move(int first) {
        if (first%2 == step%2){
            humanMove(DOT_X);
        }
        else {
            aiMove(DOT_O);
        }
        step++;
    }

    private boolean checkEndGame(int r, int c, char XO){
        checkHorizonLine(r, c, XO);


        if (step == maxStep){
            System.out.println("Ничья!");
        }
    }

    private void checkHorizonLine(int r, int c, char XO) {
        int correctionFigure = checkEdgeColumn(c);
        for (int i = 0; i < countDotToWin; i++){
            int countDot = 0;
            int tempCol = c - countDotToWin + 1;
            for (int j = 0 - correctionFigure; j < countDotToWin - correctionFigure; j++){
                if (map[r][tempCol + j] == XO){
                    countDot++;
                }
            }
            if (countDot == countDotToWin){
                System.out.println("Победил " + XO);
            }
        }
    }

    private int checkEdgeRow(int r){
        int returnableNum;
        if ((rows - 1 - r) >= countDotToWin - 1){
            if ((r - countDotToWin + 1) >=0){
                returnableNum = r;
            }
            else {
                returnableNum = r - countDotToWin + 1;
            }
        }
        else {
            returnableNum = rows - r;
        }
        return returnableNum;
    }
    private int checkEdgeColumn(int c){
        int returnableNum;
        if ((columns - 1 - c) >= countDotToWin - 1){
            if ((c - countDotToWin + 1) >=0){
                returnableNum = c;
            }
            else {
                returnableNum = c - countDotToWin + 1;
            }
        }
        else {
            returnableNum = columns - c;
        }
        return returnableNum;
    }

    private void checkVerticalLine(int r, int c, char XO) {
        int correctionFigure = checkEdgeRow(r);
        for (int i = 0; i < countDotToWin; i++){
            int countDot = 0;
            int tempRow = r - countDotToWin + 1;
            for (int j = 0 - correctionFigure; j < countDotToWin - correctionFigure; j++){
                if (map[r][tempRow + j] == XO){
                    countDot++;
                }
            }
            if (countDot == countDotToWin){
                System.out.println("Победил " + XO);
            }
        }
    }
    private void checkMainDiagonale(int r, int c, char XO) {
        for (int i = 0; i < countDotToWin; i++){
            int countDot = 0;
            int tempCol = c - countDotToWin + 1;
            int tempRow = r - countDotToWin + 1;
            for (int j = 0; j < countDotToWin; j++){
                if (map[tempRow][tempCol + j] == XO){
                    countDot++;
                }
            }
            if (countDot == countDotToWin){
                System.out.println("Победил " + XO);
            }
        }
    }
    private void checkSecondaryDiagonale(int r, int c, char XO) {
        for (int i = 0; i < countDotToWin; i++){
            int countDot = 0;
            int tempCol = c - countDotToWin + 1;
            int tempRow = r - countDotToWin + 1;
            for (int j = 0; j < countDotToWin; j++){
                if (map[tempRow][tempCol + j] == XO){
                    countDot++;
                }
            }
            if (countDot == countDotToWin){
                System.out.println("Победил " + XO);
            }
        }
    }


    private void humanMove(char OX) {
        System.out.println("Введите значения строки и столбца");
        int r = scanner.nextInt();
        int c = scanner.nextInt();
        map[r-1][c-1] = OX;
    }

    private void aiMove(char OX) {

        //map[r-1][c-1] = OX;
    }
}
