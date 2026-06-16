import java.util.Scanner;
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int s = sc.nextInt(); 
        int n = sc.nextInt(); 
        int[] shar = new int[s];
        int[] neto = new int[n];
        for (int i = 0; i < s; i++) {
            shar[i] = sc.nextInt();
        }
        for (int j = 0; j < n; j++) {
            neto[j] = sc.nextInt();
        }
        Arrays.sort(shar);
        Arrays.sort(neto);
        int count = 0;
        int nIndex = 0; 
        for (int i = 0; i < s; i++) {
            if (nIndex >= n) {
                break;
            }
            if (neto[nIndex] <= 2 * shar[i]) {
                count++;      
                nIndex++; 
            }
        }
        System.out.println(count);
    }
}
