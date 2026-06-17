import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            int[] a = new int[n];
            Map<Integer, Integer> F = new HashMap<>();
            Map<Integer, Integer> L = new HashMap<>();
            for (int i = 0; i < n; i++) {
                a[i] = sc.nextInt();
                if (!F.containsKey(a[i])) F.put(a[i], i);
                L.put(a[i], i);
            }
            List<Integer> bad = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((i > F.get(a[i]) && a[i] != a[i-1]) || (i < L.get(a[i]) && a[i] != a[i+1])) {
                    bad.add(i);
                }
            }
            if (bad.isEmpty()) {
                System.out.println("YES");
                continue;
            }
            if (bad.size() > 12) {
                System.out.println("NO");
                continue;
            }
            String ans = "NO";
            for (int i : bad) {
                for (int j : bad) {
                    int temp = a[i]; a[i] = a[j]; a[j] = temp;
                    List<Integer> c = new ArrayList<>();
                    for (int k = 0; k < n; k++) {
                        if (c.isEmpty() || a[k] != c.get(c.size() - 1)) c.add(a[k]);
                    }
                    if (c.size() == new HashSet<>(c).size()) ans = "YES";
                    temp = a[i]; a[i] = a[j]; a[j] = temp;
                    if (ans.equals("YES")) break;
                }
                if (ans.equals("YES")) break;
            }
            System.out.println(ans);
        }
    }
}
