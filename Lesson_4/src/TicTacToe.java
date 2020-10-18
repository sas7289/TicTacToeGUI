import java.util.Random;
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
    final char[] DOT_XO = {'X','O'};
    //final char DOT_O = 'O';
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
            printMap();
        }
    }

    private void move(int first) {


        if (first%2 == step%2){
            humanMove(DOT_XO[step%2]);
        }
        else {
            aiMove(DOT_XO[step%2]);
        }
        step++;
    }

    private boolean checkEndGame(int r, int c, char XO){
        boolean result = false;
        checkHorizonLine(r, c, XO);
        //checkVerticalLine(r, c, XO);



        if (step == maxStep){
            System.out.println("Ничья!");
            result = true;
        }
        return result;
    }

    private boolean checkHorizonLine(int r, int c, char XO) {
        boolean result = false;
        int correctionFigure = checkEdgeColumn(c);
        for (int i = 0; i < countDotToWin - Math.abs(correctionFigure); i++){
            int countDot = 0;
            int tempCol = c - countDotToWin + 1;
            if (correctionFigure < 0){
                for (int j = 0; j < countDotToWin; j++){
                    if (map[r - 1][tempCol - correctionFigure - 1] == XO){
                        countDot++;
                    }
                }
            }
            else {
                for (int j = 0; j < countDotToWin; j++){
                    if (map[r - 1][tempCol - 1] == XO){
                        countDot++;
                    }
                }
            }
            if (countDot == countDotToWin){
                System.out.println("Победил " + XO);
                result = true;
            }
        }
        return result;
    }

    private int checkEdgeRow(int r){
        int countMarksOutOfMap = 0;
        if ((rows - r) >= (countDotToWin - 1)){
            if ((r - (countDotToWin - 1)) < 0){
                countMarksOutOfMap = r - countDotToWin;
            }
        }
        else {
            countMarksOutOfMap = (countDotToWin - 1)  - (rows - r);
        }
        return countMarksOutOfMap;
    }
    private int checkEdgeColumn(int c){
        int countMarksOutOfMap = 0;
        if ((columns - c) >= (countDotToWin - 1)){
            if ((c - (countDotToWin - 1)) < 0){
                countMarksOutOfMap = c - countDotToWin;
            }
        }
        else {
            countMarksOutOfMap = (countDotToWin - 1)  - (columns - c);
        }
        return countMarksOutOfMap;
    }

    private boolean checkVerticalLine(int r, int c, char XO) {
        boolean result = false;
        int correctionFigure = checkEdgeRow(r);
        for (int i = 0; i < countDotToWin - Math.abs(correctionFigure); i++){
            int countDot = 0;
            int tempRow = r - countDotToWin + 1;
                if (correctionFigure < 0){
                    for (int j = 0; j < countDotToWin; j++){
                        if (map[tempRow - correctionFigure - 1][c - 1] == XO){
                            countDot++;
                        }
                    }
                }
                else {
                    for (int j = 0; j < countDotToWin; j++){
                        if (map[tempRow][r - 1] == XO){
                            countDot++;
                        }
                    }
                }
            if (countDot == countDotToWin){
                System.out.println("Победил " + XO);
                result = true;
            }
        }
        return result;
    }
    private void checkMainDiagonale(int r, int c, char XO) {
        int correctionalVertical = checkEdgeRow(r);
        int correctionalHorizontal = checkEdgeColumn(c);
        int correctionalFigure;
        if (correctionalHorizontal < correctionalVertical) {
            correctionalFigure =correctionalHorizontal;
        }
        else {
            correctionalFigure = correctionalVertical;
        }
        for (int i = 0; i < countDotToWin - Math.abs(correctionalFigure); i++){
            int countDot = 0;
            int tempCol = c - countDotToWin + 1;
            int tempRow = r - countDotToWin + 1;
            if (correctionalFigure < 0){
                for (int j = 0; j < countDotToWin; j++){
                    if (map[tempRow - 1][tempRow - correctionalFigure - 1] == XO){
                        countDot++;
                    }
                }
            }
            else {
                for (int j = 0; j < countDotToWin; j++){
                    if (map[r][tempRow] == XO){
                        countDot++;
                    }
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
        while (map[r - 1][c - 1] != DOT_EMPTY){
            r = scanner.nextInt();
            c = scanner.nextInt();
        }
        map[r - 1][c - 1] = OX;
        checkEndGame(r, c, OX);
    }

    private void aiMove(char OX) {
        Random randomRows = new Random();
        Random randomCol = new Random();
        int r = randomRows.nextInt(rows);
        int c = randomCol.nextInt(columns);
        while (map[r][c] != DOT_EMPTY){
            r = randomRows.nextInt(rows);
            c = randomCol.nextInt(columns);
        }
        map[r][c] = OX;
        checkEndGame(r, c, OX);
    }
}
