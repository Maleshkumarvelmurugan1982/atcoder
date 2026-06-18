import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        boolean[] staticcolor = new boolean[8];
        int wild = 0;
        for (int i = 0; i < n; i++) {
            int rate = sc.nextInt();
            if (rate >= 3200) {
                wild++;
            } else {
                staticcolor[rate/ 400] = true;
            }
        }
        int unique = 0;
        for (int i = 0; i < 8; i++) {
            if (staticcolor[i]) {
                unique++;
            }
        }
        int min = (unique == 0) ? 1 : unique;
        int max = unique + wild;
        System.out.println(min + " " + max);
    }
}
