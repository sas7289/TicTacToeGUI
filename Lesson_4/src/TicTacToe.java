import java.util.*;

public class TicTacToe {
    boolean endGame = false;
    Random random = new Random();
    char markHuman, markAi;
    int rows;
    int columns;
    char[][] map;
    int step;
    int countDotToWin;
    int maxStep; //максимальное количество ходов на всю партию
    final int COUNT_WIN_LINE = 4;
    final char DOT_EMPTY = '•';
    final char[] DOT_XO = {'X','O'};
    final char DOT_X = 'X';
    final char DOT_O = 'O';
    final char FIRST_SYMBOL = 'Ϯ';
    final String EMPTY = " ";

    int[] lastStepHuman = new int[2];

    static Scanner scanner = new Scanner(System.in);


    public TicTacToe (int row, int col){
        rows = row;
        columns = col;
    }

    public TicTacToe (){
        inputRow();
        inputColumn();
    }

    private void inputColumn() {
        int columnCount = 0;
        do {
            System.out.println("Количество столбцов: ");
            while (!scanner.hasNextInt()){
                System.out.print("Не по тем кнопкам попадаешь, прицелься получше\nКоличество столбцов:");
                scanner.next();
            }
            columnCount = scanner.nextInt();
        } while (columnCount < 0);
        columns = columnCount;
    }

    private void inputRow() {
        int rowsCount = 0;
        do {
            System.out.print("Количество строк: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Не по тем кнопкам попадаешь, пробуй ещё разок\nКоличество строк: ");
                scanner.next();
            }
            rowsCount = scanner.nextInt();
        } while (rowsCount < 0);
        rows = rowsCount;
    }

    public void turnGame() {
        while(true) {
            initNum();
            initMap();
            printMap();
            playGame();
        }
    }

    private void initNum() {
        //инициализируем количество ячеек, необходимых для выигрыша и максимальное количестов ходов на всю партию
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
        int firstStep = stepPriority();
        setMarks(firstStep);
        while (!endGame) {
            move(firstStep);
            printMap();
        }
        int yourChoose = 0;
        do {
            System.out.print("1. Начать сначала\n2. Выход из игры\nChoose your destiny: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Одно из двух, красная или синия...");
                scanner.next();
            }
            yourChoose = scanner.nextInt();
        } while (yourChoose != 1 && yourChoose != 2);
        switch (scanner.nextInt()){
            case 1:
                break;
            case 2:System.exit(0);
        }
    }

    private int stepPriority() {
        System.out.println("Кто ходит первым?\n1. Конечно же я\n2. Дадим шанс железке");
        while (!scanner.hasNext()){
            System.out.println("Выбор небольшой, попробуй ещё раз");
            scanner.next();
        }
        return scanner.nextInt() - 1;
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

    private void humanMove() {
        int r = humanRow();
        int c = humanColumn();
        while (map[r][c] != DOT_EMPTY){
            System.out.println("Выбранная ячейка занята, выбери другую");
            r = humanRow();
            c = humanColumn();
        }
        map[r][c] = markHuman;
        endGame = checkEndGame(r, c, markHuman, countDotToWin);
        lastStepHuman[0] = r;
        lastStepHuman[1] = c;
    }

    private int humanColumn() {
        System.out.print("Введите значения столбца\nСтолбец: ");
        while (!scanner.hasNext()){
            System.out.println("Выбор небольшой, попробуй ещё раз\nСтолбец: ");
            scanner.next();
        }
        return scanner.nextInt() - 1;
    }

    private int humanRow() {
        System.out.print("Введите значения строки\nСтрока: ");
        while (!scanner.hasNext()){
            System.out.println("Выбор небольшой, попробуй ещё раз\nСтрока: ");
            scanner.next();
        }
        return scanner.nextInt() - 1;
    }

    private void aiMove() {
        aiMind();
    }

    private void aiMind (){
        List<int[]> coastDots = new ArrayList<>();
        int[] dot = new int[4];//0 - строка 1 - столбец 2 - линия, по которой большая цена 3 - наибольшая цена для данной точки
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (map[i][j] == markAi){
                    int[] temp = findAiWinLine(i, j, markAi, countDotToWin);//0 - horizon; 1 - vertical; 2 - mainD; 3- secondaryD
                    dot[0] = temp[0];
                    dot[1] = temp[1];
                    dot[2] = temp[2];
                    dot[3] = temp[3];//возвращает стоимость этой точки
                    coastDots.add(dot);
                }
                else if (map[i][j] == markHuman){
                    if (stopHuman()){
                        return;
                    }
                }
            }
        }
        if (coastDots.size() == 0){
            map[random.nextInt(rows)][random.nextInt(columns)] = markAi;
            return;
        }
        findTargetDot(coastDots);
    }

    private boolean stopHuman() {
        boolean result = false;
        mark: for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (checkEndGame(i, j, markHuman, countDotToWin - 1)){
                    int[] dotHumanWin = findAiWinLine(lastStepHuman[0], lastStepHuman[1], markHuman, countDotToWin - 1);
                    setMarkAi(dotHumanWin);
                    result = true;
                    break mark;
                }
            }
        }
        return result;
    }


    private boolean checkEndGame(int r, int c, char XO, int countDotToWin){
        boolean result = false;
        if (checkHorizonLine(r, c, XO, countDotToWin) || checkVerticalLine(r, c, XO, countDotToWin)
                || checkMainDiagonal(r, c, XO, countDotToWin) ||
                checkSecondaryDiagonal(r, c, XO, countDotToWin)){
            result = true;
        }
        if (step == maxStep){
            System.out.println("Ничья!");
            result = true;
        }
        return result;
    }

    //Проверяет горизонтальную линию на наличие победной комбинации
    private boolean checkHorizonLine(int r, int c, char XO, int countDotToWin) {
        boolean result = false;
        int left, right, currentCountDot;
        int countStepLeft = 0;
        int countStepRight = 0;
        left = right = c;
        //отсчитываем необходимое для победы количество ячеек влево или чтобы оно не выходило за границы поля
        while (left != 0 && countStepLeft != (this.countDotToWin -1)){
            left--;
            countStepLeft++;
        }
        //отсчитываем необходимое для победы количество ячеек вправо или чтобы оно не выходило за границы поля
        while (right != columns- 1 && countStepRight != (this.countDotToWin -1)){
            right++;
            countStepRight++;
        }
        do {
            currentCountDot = 0;
            for (int i = 0; i < this.countDotToWin; i++){
                if (map[r][left + i] == markHuman){
                    currentCountDot++;
                }
            }
            if (currentCountDot == countDotToWin){
                result = true;
            }
            left++;
        } while ((left + this.countDotToWin - 1) <= right);
        return result;
    }



    //Проверяет вериткальую линию на наличие победной комбинации
    private boolean checkVerticalLine(int r, int c, char XO, int countDotToWin) {
        boolean result = false;
        int up, down, currentCountDot;
        int countStepLeft = 0;
        int countStepRight = 0;
        up = down = r;
        while (up != 0 && countStepLeft != (this.countDotToWin -1)){
            up--;
            countStepLeft++;
        }
        while (down != columns- 1 && countStepRight != (this.countDotToWin -1)){
            down++;
            countStepRight++;
        }
        do {
            currentCountDot = 0;
            for (int i = 0; i < this.countDotToWin; i++){
                if (map[up + i][c] == markHuman){
                    currentCountDot++;
                }
            }
            if (currentCountDot == countDotToWin){
                result = true;
            }
            up++;
        } while ((up + this.countDotToWin - 1) <= down);
        return result;
    }

    //Вызывает необходимые методы для проверки главной диагонали на наличие победной комбинации
    public boolean checkMainDiagonal(int r, int c, char XO, int countDotToWin) {
        boolean result = false;
        double lenght;
        int[] dotLeft = new int[2];
        int[] dotRight = new int[2];
        dotLeft[0] = dotRight[0] = r;
        dotLeft[1] = dotRight[1] = c;
        findMainTermianlDots(dotLeft, dotRight);
        lenght = dotRight[0] - dotLeft[0] + 1;
        if (lenght >= this.countDotToWin) {
            result = checkCountMainWinDots(dotLeft, dotRight, XO, countDotToWin);
        }
        return result;

    }

    //Проверяет, есть ли необходимое количество меток для победы
    private boolean checkCountMainWinDots(int[] dotLeft, int[] dotRight, char XO, int countDotToWin){
        boolean result = false;
        do {
            int countForWin = 0;
            for (int i = 0; i < this.countDotToWin; i++) {
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

    //Находит крайние точки для всех комбинаций
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

    //Проверяет побочную диагональ на наличие победной комбинации
    public boolean checkSecondaryDiagonal(int r, int c, char XO, int countDotToWin) {
        boolean result = false;
        double lenght;
        int[] dotLeft = new int[2];
        int[] dotRight = new int[2];
        dotLeft[0] = dotRight[0] = r;// координаты Х точек
        dotLeft[1] = dotRight[1] = c;// координаты У точек
        findSecondaryTermianlDots(dotLeft, dotRight);
        lenght = dotRight[0] - dotLeft[0] + 1;
        if (lenght >= this.countDotToWin) {
            result = checkCountSecondaryWinDots(dotLeft, dotRight, XO, countDotToWin);
        }
        return  result;

    }

    //Находит крайние точки для всех комбинаций
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

    //Проверяет, есть ли необходимое количество меток для победы
    private boolean checkCountSecondaryWinDots(int[] dotLeft, int[] dotRight, char XO, int countDotToWin){
        boolean result = false;
        do {
            int countForWin = 0;
            for (int i = 0; i < this.countDotToWin; i++) {
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





    //Находит в списке точку игра с наибольшей ценой
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


    //Находит по какой линии большая цена
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

    //Проверяет ходы игрока на предмет препятсвия его победе
    private int[] findAiWinLine(int r, int c, char mark, int countDotToWin) {
        int[][] coastDot = new int[4][3];
        boolean result = false;
        int[] temp = new int[4];
        coastDot[0] = checkHorizonAiWinLine(r, c, mark, countDotToWin);
        coastDot[1] = checkVerticalAiWinLine(r, c, mark, countDotToWin);
        coastDot[2] = checkAiWinMainDiagonal(r, c, mark, countDotToWin);
        coastDot[3] = checkAiWinSecondaryDiagonal(r, c, mark, countDotToWin);
        int pos = findMaxCoastPosition(coastDot);
        temp[0] = coastDot[pos][0];
        temp[1] = coastDot[pos][1];
        temp[2] = pos;
        temp[3] = coastDot[pos][2];

        return temp;
    }
/*    private int getMaxCoast(int r, int c) {
        //coastDot: каждая строка массива соответствует каждому направлению (горизонталь, вертикаль и две диагонали)
        //а каждому направлению соответствует массив из трёх чисел: строка и столбец точки от которой отсчитвается
        // победное количество ячеек и, максимальная стоимость этой линии
        int[][] coastDot = new int[4][3];
        int maxCoast = 0;
        coastDot[0] = checkHorizonAiWinLine(r, c);
        coastDot[1] = checkVerticalAiWinLine(r, c);
        coastDot[2] = checkAiWinMainDiagonal(r, c);
        coastDot[3] = checkAiWinSecondaryDiagonal(r, c);
        maxCoast = findMaxCoast(coastDot);
        return maxCoast;
    }*/

    //Находит максимальную стоимость для выигрышной линии
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


    //Проверяет метку игрока по горизонтольной линии
    private int[] checkHorizonAiWinLine(int r, int c, char mark, int countDotToWin) {
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
        while (left != columns && countStepRight != (countDotToWin -1)){
            right++;
            countStepRight++;
        }
        do {
            countWinDot = 0;
            for (int i = 0; i < countDotToWin; i++){
                if (map[r][left + i] == mark){
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

    //Проверяет метку игрока по вертикальной линии
    private int[] checkVerticalAiWinLine(int r, int c, char mark, int countDotToWin) {
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
        while (down != rows && countStepDown != (countDotToWin -1)){
            down++;
            countStepDown++;
        }
        do {
            countWinDot = 0;
            for (int i = 0; i < countDotToWin; i++){
                if (map[up + i][c] == mark){
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

    //Проверяет метку игрока по главной диагонали
    public int[] checkAiWinMainDiagonal(int r, int c, char mark, int countDotToWin) {
        boolean result = false;
        double lenght;
        int[] dotLeft = new int[2];
        int[] dotRight = new int[2];
        dotLeft[0] = dotRight[0] = r;
        dotLeft[1] = dotRight[1] = c;
        findMainTermianlDots(dotLeft, dotRight);
        lenght = Math.abs(dotRight[0] - dotLeft[0]) + 1;
        if (lenght >= this.countDotToWin) {
            return checkCountHumanMainWinDots(dotLeft, dotRight, mark, countDotToWin);
        }
        return new int[3];
    }


    //Возвращает точку игрока с наибольшой ценой
    private int[] checkCountHumanMainWinDots(int[] dotLeft, int[] dotRight, char mark, int countDotToWin){
        int[] maxDot = new int[3];
        do {
            int countForWin = 0;
            for (int i = 0; i < this.countDotToWin; i++) {
                if (map[dotLeft[0]+i][dotLeft[1]+i] == mark) {
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
        }while (dotLeft[0]+this.countDotToWin -1 <=dotRight[0] || dotLeft[1]+3<=dotRight[1]);
        return  maxDot;
    }

    //Проверяет метку игрока на побочной диагонали
    public int[] checkAiWinSecondaryDiagonal(int r, int c, char mark, int countDotToWin) {
        boolean result = false;
        double lenght;
        int[] dotLeft = new int[2];
        int[] dotRight = new int[2];
        dotLeft[0] = dotRight[0] = r;// координаты Х точек
        dotLeft[1] = dotRight[1] = c;// координаты У точек
        findSecondaryTermianlDots(dotLeft, dotRight);
        lenght = Math.abs(dotRight[0] - dotLeft[0]) + 1;
        if (lenght >= countDotToWin) {
            return checkCountHumanSecondaryWinDots(dotLeft, dotRight, mark, countDotToWin);
        }
        return new int[3];
    }

    //Возвращает точку игрока с наибольшой ценой
    private int[] checkCountHumanSecondaryWinDots(int[] dotLeft, int[] dotRight, char mark, int countDotToWin){
        int maxCoast = 0;
        int[] maxDot= new int[3];
        boolean result = false;
        do {
            int countForWin = 0;
            for (int i = 0; i < countDotToWin; i++) {
                if (map[dotLeft[0]-i][dotLeft[1]+i] == mark) {
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


    //Ставит метку АИ на необоходимой линии
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
        endGame = checkEndGame(r, c, markAi, countDotToWin);
    }

    private void setMarkMainDiagonal(int[] dot) {
        int r = dot[0];
        int c = dot[1];
        while (map[r][c] != DOT_EMPTY){
            r++;
            c++;
        }
        map[r][c] = markAi;
        endGame = checkEndGame(r, c, markAi, countDotToWin);
    }

    private void setMarkVertical(int[] dot) {
        int r = dot[0];
        int c = dot[1];
        while (map[r][c] != DOT_EMPTY){
            r++;
        }
        map[r][c] = markAi;
        endGame = checkEndGame(r, c, markAi, countDotToWin);
    }

    private void setMarkHorizon(int[] dot) {
        int r = dot[0];
        int c = dot[1];
        while (map[r][c] != DOT_EMPTY){
            c++;
        }
        map[r][c] = markAi;
        endGame = checkEndGame(r, c, markAi, countDotToWin);
    }
}
