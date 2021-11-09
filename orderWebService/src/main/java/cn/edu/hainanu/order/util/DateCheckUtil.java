package cn.edu.hainanu.order.util;

public class DateCheckUtil {

    public static boolean judge(int y, int m, int d) {
        boolean p = false;
        if (m < 1 || m > 12) {
            System.out.print("月份不合法");
            p = false;
        } else if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
            if (d <= 31) {
                p = true;
            } else {
                p = false;
                System.out.print("日期不合法");
            }
        } else if (m == 2) {
            if (y % 400 == 0 || (y % 4 == 0 && y % 100 != 0)) {
                if (d <= 29) {
                    p = true;
                } else {
                    p = false;
                    System.out.print("日期不合法");
                }
            } else {
                if (d <= 28) {
                    p = true;
                } else {
                    p = false;
                    System.out.print("日期不合法");
                }
            }
        } else {
            if (d <= 30) {
                p = true;
            } else {
                p = false;
                System.out.print("日期不合法");
            }
        }
        return p;
    }
}
