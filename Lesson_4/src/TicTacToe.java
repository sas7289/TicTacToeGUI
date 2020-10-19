import java.util.*;

public class TicTacToe {
    char markHuman, markAi;
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
    final char DOT_X = 'X';
    final char DOT_O = 'O';
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
        setMarks(first);
        boolean endGame = false;
        while (!endGame) {
            move(first);
            printMap();
        }
    }

    private void setMarks(int first) {
        if (first == 0){
            markHuman = DOT_X;
            markAi = DOT_O;
        }
        else {
            markHuman = DOT_O;
            markAi = DOT_X;
        }
    }

    private void move(int first) {


        if (first%2 == step%2){
            humanMove();
        }
        else {
            aiMove();
        }
        step++;
    }

    private boolean checkEndGame(int r, int c, char XO){
        boolean result = false;
        if (checkHorizonLine(r, c, XO) || checkVerticalLine(r, c, XO) || checkMainDiagonal(r, c, XO) ||
                checkSecondaryDiagonal(r, c, XO)){
            result = true;
        }



        if (step == maxStep){
            System.out.println("Ничья!");
            result = true;
        }
        return result;
    }

    private boolean checkHorizonLine(int r, int c, char XO) {
        boolean result = false;
        int left, right, countWinDot;
        int countStepLeft = 0;
        int countStepRight = 0;
        left = right = c;
        while (left != 0 && countStepLeft != (countDotToWin -1)){
            left--;
            countStepLeft++;
        }
        while (right != columns- 1 && countStepRight != (countDotToWin -1) && !result){
            right++;
            countStepRight++;
        }
        do {
            countWinDot = 0;
            for (int i = 0; i < countDotToWin; i++){
                if (map[r][left + i] == markHuman){
                    countWinDot++;
                }
            }
            if (countWinDot == 4){
                result = true;
            }
            left++;
        } while ((left + countDotToWin - 1) <= countDotToWin - 1);
        return result;
    }


    private int checkEdgeRow(int r){
        int countMarksOutOfMap = 0;
        if ((rows - r) >= (countDotToWin - 1)){
            if ((r - countDotToWin) < 0){
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
            if ((c - countDotToWin) < 0){
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
        int up, down, countWinDot;
        int countStepLeft = 0;
        int countStepRight = 0;
        up = down = r;
        while (up != 0 && countStepLeft != (countDotToWin -1)){
            up--;
            countStepLeft++;
        }
        while (down != columns- 1 && countStepRight != (countDotToWin -1) && !result){
            down++;
            countStepRight++;
        }
        do {
            countWinDot = 0;
            for (int i = 0; i < countDotToWin; i++){
                if (map[up + i][c] == markHuman){
                    countWinDot++;
                }
            }
            if (countWinDot == 4){
                result = true;
            }
            up++;
        } while ((up + countDotToWin - 1) <= countDotToWin - 1);
        return result;
    }



    //Метод проверяет, пересекает ли выиграшная диагональ сразу обе грани
    private boolean checkDotEdges(int r, int c) {
        boolean result = false;
        if (((c - countDotToWin) < 0) || ((columns - c) <= (countDotToWin - 1))) {
            if (((r - countDotToWin) < 0) || ((rows - r) <= (countDotToWin - 1))){
                result = true;
            }
        }
        return result;
    }

    public boolean checkMainDiagonal(int r, int c, char XO) {
        boolean result = false;
        double lenght;
        int[] dotLeft = new int[2];
        int[] dotRight = new int[2];
        dotLeft[1] = dotRight[1] = r;// координаты Х точек
        dotLeft[0] = dotRight[0] = c;// координаты У точек
        findMainTermianlDots(dotLeft, dotRight);
        lenght = dotRight[0] - dotLeft[0] + 1;
        if (lenght >= countDotToWin) {
            result = checkCountMainWinDots(dotLeft, dotRight, XO);
        }
        return result;

    }


    private boolean checkCountMainWinDots(int[] dotLeft, int[] dotRight, char XO){
        boolean result =false;
        do {
            int countForWin = 0;
            for (int i = 0; i < countDotToWin; i++) {
                if (map[dotLeft[1]+i][dotLeft[0]+i] == XO) {
                    countForWin++;
                }
            }
            if (countForWin == countDotToWin) {
                System.out.println("ПОБЕДА по главной диагонали!!!");
                result = true;
            }
            dotLeft[0]++;
            dotLeft[1]++;
        }while (dotLeft[0]+3<=dotRight[0] ||dotLeft[1]+3<=dotRight[1]);
        return  result;
    }

    private void findMainTermianlDots(int[] dotLeft, int[] dotRight) {
        int countLeft = 0;
        int countRight = 0;
        while ((dotLeft[0] != 0 && dotLeft[1] != 0) && countLeft != (countDotToWin - 1)){
            dotLeft[0]--;
            dotLeft[1]--;
            countLeft++;
        }
        while ((dotRight[0] != columns - 1 && dotRight[1] != rows - 1) && countRight != (countDotToWin - 1)) {
            dotRight[0]++;
            dotRight[1]++;
            countRight++;
        }
    }

    public boolean checkSecondaryDiagonal(int r, int c, char XO) {
        boolean result = false;
        double lenght;
        int[] dotLeft = new int[2];
        int[] dotRight = new int[2];
        dotLeft[1] = dotRight[1] = r;// координаты Х точек
        dotLeft[0] = dotRight[0] = c;// координаты У точек
        findSecondaryTermianlDots(dotLeft, dotRight);
        lenght = dotRight[0] - dotLeft[0] + 1;
        if (lenght >= countDotToWin) {
            result = checkCountSecondaryWinDots(dotLeft, dotRight, XO);
        }
        return  result;

    }

    private void findSecondaryTermianlDots(int[] dotLeft, int[] dotRight) {
        int countLeft = 0;
        int countRight = 0;
        while ((dotLeft[0] != rows - 1  && dotLeft[1] != 0) && countLeft != (countDotToWin - 1)){
            dotLeft[0]++;
            dotLeft[1]--;
            countLeft++;
        }
        while ((dotRight[0] != 0 && dotRight[1] != columns - 1) && countRight != (countDotToWin - 1)) {
            dotRight[0]--;
            dotRight[1]++;
            countRight++;
        }
    }

    private boolean checkCountSecondaryWinDots(int[] dotLeft, int[] dotRight, char XO){
        boolean result = false;
        do {
            int countForWin = 0;
            for (int i = 0; i < countDotToWin; i++) {
                if (map[dotLeft[1]-i][dotLeft[0]+i] == XO) {
                    countForWin++;
                }
            }
            if (countForWin == countDotToWin) {
                System.out.println("ПОБЕДА по главной диагонали!!!");
                result = true;
            }
            dotLeft[0]++;
            dotLeft[1]--;
        }while (dotLeft[0]+3<=dotRight[0] ||dotLeft[1]-3>=dotRight[1]);
        return result;
    }




    private void humanMove() {
        System.out.println("Введите значения строки и столбца");
        int r = scanner.nextInt() - 1;
        int c = scanner.nextInt() - 1;
        while (map[r][c] != DOT_EMPTY){
            r = scanner.nextInt() - 1;
            c = scanner.nextInt() - 1;
        }
        map[r][c] = markHuman;
        checkEndGame(r, c, markHuman);
    }

    private void aiMove() {
        aiMind();
    }


    private void aiMind (){
        List<int[]> coastDots = new ArrayList<>();
        int[] dot = new int[4];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (map[i][j] == markHuman){
                    int[] temp = checkHumanWinLine(i, j);//0 - horizon; 1 - vertical; 2 - mainD; 3- secondaryD
                    dot[0] = temp[0];
                    dot[1] = temp[1];
                    dot[2] = temp[2];
                    dot[3] = getMaxCoast(i, j);//возвращает стоимость этой точки
                    coastDots.add(dot);
                }
            }
        }
        findTargetDot(coastDots);

   /*     Random randomRows = new Random();
        Random randomCol = new Random();
        dot[0] = randomRows.nextInt(rows) + 1;
        dot[1] = randomCol.nextInt(columns) + 1;
        while (map[dot[0]][dot[1]] != DOT_EMPTY){
            dot[0] = randomRows.nextInt(rows) + 1;
            dot[1] = randomCol.nextInt(columns) + 1;
        }*/

    }

    private void findTargetDot(List<int[]> coastDots) {
        int maxPos = 0;
        int maxCoast = 0;
        for (int i = 0; i < coastDots.size(); i++){
            if (coastDots.get(i)[3] > maxCoast){
                maxCoast = coastDots.get(i)[3];
                maxPos = i;
            }
        }
        setMarkAi(coastDots.get(maxPos));
    }

/*    private int[] findMaxCoastPosition(List<int[]> coastDots) {
        int[] max = new int[2];
        int[] dot = new int[2];
        for (int i = 0; i < coastDots.size(); i++){
            if (coastDots.get(i)[2] > max[1]){
                max[1] = coastDots.get(i)[2];
                max[0] = i;
            }
        }
        return coastDots.get(max[0]);
    }*/

    private int findMaxCoastPosition(int[][] arr) {
        int pos = 0;
        int max = 0;
        for (int i = 0; i < arr.length; i++){
            if (arr[i][2] > max){
                max = arr[i][2];
                pos = i;
            }
        }
        return pos;
    }

    private int[] checkHumanWinLine(int r, int c) {
        int[][] coastDot = new int[4][3];
        boolean result = false;
        int[] temp = new int[4];
        coastDot[0] = checkHorizonHuman(r, c);
        coastDot[1] = checkVerticalHuman(r, c);
        coastDot[2] = checkMainHumanDiagonal(r, c);
        coastDot[3] = checkSecondaryHumanDiagonal(r, c);
        int pos = findMaxCoastPosition(coastDot);
        temp[0] = coastDot[pos][0];
        temp[1] = coastDot[pos][1];
        temp[2] = pos;
        temp[3] = coastDot[pos][2];

        return temp;
    }
    private int getMaxCoast(int r, int c) {
        int[][] coastDot = new int[4][3];
        int maxCoast = 0;
        coastDot[0] = checkHorizonHuman(r, c);
        coastDot[1] = checkVerticalHuman(r, c);
        coastDot[2] = checkMainHumanDiagonal(r, c);
        coastDot[3] = checkSecondaryHumanDiagonal(r, c);
        maxCoast = findMaxCoast(coastDot);
        return maxCoast;
    }

    private int findMaxCoast(int[][] coastDot) {
        int pos = 0;
        int max = 0;
        for (int i = 0; i < coastDot.length; i++){
            if (coastDot[i][2] > max){
                max = coastDot[i][2];
                pos = i;
            }
        }
        return max;
    }


    private int[] checkHorizonHuman(int r, int c) {
        boolean result = false;
        int maxCountWin = 0;
        int[] maxDot= new int[3];
        int left, right, countWinDot;
        int countStepLeft = 0;
        int countStepRight = 0;
        left = right = c;
        while (left != 0 && countStepLeft != (countDotToWin -1)){
            left--;
            countStepLeft++;
        }
        while (left != columns && countStepRight != (countDotToWin -1) && !result){
            right++;
            countStepRight++;
        }
        do {
            countWinDot = 0;
            for (int i = 0; i < countDotToWin; i++){
                if (map[r][left + i] == markHuman){
                    countWinDot++;
                }
            }
            if (countWinDot > maxDot[2]){
                maxDot[2] = countWinDot;
                maxDot[0] = r;
                maxDot[1] = left;
            }
            left++;
        } while ((left + countDotToWin - 1) <= countDotToWin - 1);
        return maxDot;
    }

    private void findDotToStopHumanHorizon(int r, int c) {
        while (map[r][c] != DOT_EMPTY){
            c++;
        }
        map[r][c] = markAi;
    }

    private int[] checkVerticalHuman(int r, int c) {
        int maxCountWin = 0;
        int[] maxDot= new int[3];
        boolean result = false;
        int up, down, countWinDot;
        int countStepUp = 0;
        int countStepDown = 0;
        up = down = r;
        while (up != 0 && countStepUp != (countDotToWin -1)){
            up--;
            countStepUp++;
        }
        while (down != rows && countStepDown != (countDotToWin -1) && !result){
            down++;
            countStepDown++;
        }
        do {
            countWinDot = 0;
            for (int i = 0; i < countDotToWin; i++){
                if (map[up + i][c] == markHuman){
                    countWinDot++;
                }
            }
            if (countWinDot > maxDot[2]){
                maxDot[2] = countWinDot;
                maxDot[0] = up;
                maxDot[1] = c;
            }
            up++;
        } while ((up + countDotToWin - 1) <= countDotToWin);
        return maxDot;
    }

    private void findDotToStopHumanVertical(int up, int c) {
        while (map[up][c] != DOT_EMPTY){
            up++;
        }
        map[up][c] = markAi;
    }

    public int[] checkMainHumanDiagonal(int r, int c) {
        boolean result = false;
        double lenght;
        int[] dotLeft = new int[2];
        int[] dotRight = new int[2];
        dotLeft[0] = dotRight[0] = r;// координаты Х точек
        dotLeft[1] = dotRight[1] = c;// координаты У точек
        findMainTermianlDots(dotLeft, dotRight);
        lenght = Math.abs(dotRight[0] - dotLeft[0]) + 1;
        int[] targetDot = new int[2];
        if (lenght >= countDotToWin) {
            return checkCountHumanMainWinDots(dotLeft, dotRight, targetDot);
        }
        return new int[3];
    }

    private void findDotToStopHumanMainDiagonal(int[] targetDot) {
        while (map[targetDot[0]][targetDot[1]] != DOT_EMPTY){
            targetDot[0]++;
            targetDot[1]++;
        }
        map[targetDot[0]][targetDot[1]] = markAi;
    }

    private int[] checkCountHumanMainWinDots(int[] dotLeft, int[] dotRight, int[] targetDot){
        boolean result = false;
        int[] maxDot = new int[3];
        do {
            int countForWin = 0;
            for (int i = 0; i < countDotToWin; i++) {
                if (map[dotLeft[0]+i][dotLeft[1]+i] == markHuman) {
                    countForWin++;
                }
            }
            if (countForWin > maxDot[2]) {
                maxDot[2] = countForWin;
                maxDot[0] = dotLeft[0];
                maxDot[1] = dotLeft[1];
            }
            dotLeft[0]++;
            dotLeft[1]++;
        }while (dotLeft[0]+countDotToWin -1 <=dotRight[0] || dotLeft[1]+3<=dotRight[1]);
        return  maxDot;
    }

    public int[] checkSecondaryHumanDiagonal(int r, int c) {
        boolean result = false;
        double lenght;
        int[] dotLeft = new int[2];
        int[] dotRight = new int[2];
        dotLeft[0] = dotRight[0] = r;// координаты Х точек
        dotLeft[1] = dotRight[1] = c;// координаты У точек
        findSecondaryTermianlDots(dotLeft, dotRight);
        lenght = Math.abs(dotRight[0] - dotLeft[0]) + 1;
        if (lenght >= countDotToWin) {
            return checkCountHumanSecondaryWinDots(dotLeft, dotRight);
        }
        return new int[3];
    }

    private int[] checkCountHumanSecondaryWinDots(int[] dotLeft, int[] dotRight){
        int maxCoast = 0;
        int[] maxDot= new int[3];
        boolean result = false;
        do {
            int countForWin = 0;
            for (int i = 0; i < countDotToWin; i++) {
                if (map[dotLeft[0]-i][dotLeft[1]+i] == markHuman) {
                    countForWin++;
                }
            }
            if (countForWin > maxDot[2]) {
                maxDot[2] = countForWin;
                maxDot[0] = dotLeft[0];
                maxDot[1] = dotLeft[1];
            }
            dotLeft[0]--;
            dotLeft[1]++;
        }while (dotLeft[0]+countDotToWin - 1<=dotRight[0] ||dotLeft[1]-3>=dotRight[1]);
        return maxDot;
    }

    private void setMarkAi (int[] dot){
        switch (dot[2]){
            case 0: setMarkHorizon(dot);
                break;
            case 1: setMarkVertical(dot);
                break;
            case 2: setMarkMainDiagonal(dot);
                break;
            case 3: setMarkSecondaryDiagonal(dot);
                break;
        }
    }

    private void setMarkSecondaryDiagonal(int[] dot) {
        int r = dot[0];
        int c = dot[1];
        while (map[r][c] != DOT_EMPTY){
            r--;
            c++;
        }
        map[r][c] = markAi;
    }

    private void setMarkMainDiagonal(int[] dot) {
        int r = dot[0];
        int c = dot[1];
        while (map[r][c] != DOT_EMPTY){
            r++;
            c++;
        }
        map[r][c] = markAi;
    }

    private void setMarkVertical(int[] dot) {
        int r = dot[0];
        int c = dot[1];
        while (map[r][c] != DOT_EMPTY){
            r++;
        }
        map[r][c] = markAi;
    }

    private void setMarkHorizon(int[] dot) {
        int r = dot[0];
        int c = dot[1];
        while (map[r][c] != DOT_EMPTY){
            c++;
        }
        map[r][c] = markAi;
    }
}
