import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] points = new int[n][3];
        points[0][0] = sc.nextInt();
        points[0][1] = sc.nextInt();
        points[0][2] = sc.nextInt();
        for (int i = 1; i < n; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();
            points[i][0] = a + Math.max(points[i-1][1], points[i-1][2]);
            points[i][1] = b + Math.max(points[i-1][0], points[i-1][2]);
            points[i][2] = c + Math.max(points[i-1][0], points[i-1][1]);
        }
        int ans = Math.max(points[n-1][0], Math.max(points[n-1][1], points[n-1][2]));
        System.out.println(ans);
    }
}
