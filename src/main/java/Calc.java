public class Calc {

    public static int fib(int n) {
        int s = 0;
        if (n <= 2) {
            return 1;
        } else {
            int f1 = 1;
            int f2 = 1;
            for (int i = 0; i < n; i++) {
                s = f1 + f2;
                f1 = f2;
                f2 = s;
            }
        }
        return s;
    }
}
