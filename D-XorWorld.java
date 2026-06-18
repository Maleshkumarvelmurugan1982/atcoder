import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long a = sc.nextLong();
        long b = sc.nextLong();
        long xorB = 0;
        long remB = b % 4;
        if (remB == 0) xorB = b;
        else if (remB == 1) xorB = 1;
        else if (remB == 2) xorB = b + 1;
        else xorB = 0;
        long xorA1 = 0;
        long nA1 = a - 1;
        if (nA1 >= 0) {
            long remA = nA1 % 4;
            if (remA == 0) xorA1 = nA1;
            else if (remA == 1) xorA1 = 1;
            else if (remA == 2) xorA1 = nA1 + 1;
            else xorA1 = 0;
        }
        long result = xorB ^ xorA1;
        System.out.println(result);
    }
}
