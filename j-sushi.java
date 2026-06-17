import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int N = sc.nextInt();
        int c1 = 0, c2 = 0, c3 = 0;
        for (int i = 0; i < N; i++) {
            int x = sc.nextInt();
            if (x == 1) c1++;
            if (x == 2) c2++;
            if (x == 3) c3++;
        }
        double[][][] dp = new double[N + 1][N + 1][N + 1];
        for (int k = 0; k <= N; k++) {
            for (int j = 0; j <= N - k; j++) {
                for (int i = 0; i <= N - j - k; i++) {
                    if (i == 0 && j == 0 && k == 0) continue;
                    double total = i + j + k;
                    double sum = N;
                    if (i > 0) sum += i * dp[i - 1][j][k];
                    if (j > 0) sum += j * dp[i + 1][j - 1][k];
                    if (k > 0) sum += k * dp[i][j + 1][k - 1];
                    
                    dp[i][j][k] = sum / total;
                }
            }
        }
        System.out.printf("%.14f\n", dp[c1][c2][c3]);
    }
}
