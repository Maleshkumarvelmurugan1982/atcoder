import java.util.Scanner;
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        int[] h = new int[N];
        for (int i = 0; i < N; i++) {
            h[i] = sc.nextInt();
        }
        int[] dp = new int[N];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 1; j <= K; j++) {
                if (i + j < N) {
                    int cost = dp[i] + Math.abs(h[i] - h[i + j]);
                    if (cost < dp[i + j]) {
                        dp[i + j] = cost;
                    }
                }
            }
        }
        
        System.out.println(dp[N - 1]);
    }
}
