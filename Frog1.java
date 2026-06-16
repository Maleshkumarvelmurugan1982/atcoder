import java.util.Scanner;
public class Main {
    public static int FrogJump(int count, int[] h) {
        int prev2 = 0; 
        int prev1 = Math.abs(h[1] - h[0]); 
        for (int i = 2; i < count; i++) {
            int option1 = prev1 + Math.abs(h[i] - h[i - 1]);
            int option2 = prev2 + Math.abs(h[i] - h[i - 2]);
            int curr = Math.min(option1, option2);
            prev2 = prev1; 
            prev1 = curr;
        }
        return prev1;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int[] h = new int[N];
        for (int i = 0; i < N; i++) {
            h[i] = scanner.nextInt();
        }
        int result = FrogJump(N, h);
        System.out.println(result);
    }
}
