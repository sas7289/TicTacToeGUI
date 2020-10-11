public class Main {
    public static void main(String[] args) {
        byte bt = 1;
        char ch = 'c';
        short shrt = 12;
        long lng = 31;
        int a = 2;
        double dbl = 131.1;
        float flt = 235.4f;
        String s = "String";
        boolean bool = true;
    }

    //Задание 3
    static double calcExpression(double a, double b, double c, double d){
        return a*(b+(c/d));
    }


    //Задание 4
    static  boolean checkSum (double a, double b){
        boolean res = false;
        if ((a+b) >= 10 && (a+b) <= 20){
            res = true;
        }
        return res;
    }

    //Задание 5
    static void checkSign (int a){
        if (a >=0){
            System.out.println("Число " + a + " положительное");
        }
        else {
            System.out.println("Число " + a + " отрицательное");
        }
    }

    //Задание 6
    static boolean checkMinus (int a){
        boolean res = false;
        if (a < 0){
            res = true;
        }
        return res;
    }

    //Задание 7
    static  void showName (String s){
        System.out.println("Привет, " + s + "!");
    }


    //Задание 8
    static void checkLeapYear(int year){
        String res = "";
        if (year % 400 == 0){
            res = "високосный год";
        }
        else if (year % 100 == 0){
            res = "невисокосный год";
        } else if (year % 4 == 0) {
            res = "високосный год";
        }
        else {
            res = "невисокосный год";
        }
        System.out.println(year + " - " + res);
    }

}
