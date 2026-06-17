import java.util.Scanner;

public class SeatingArrangement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the No of test cases:");
        int t = sc.nextInt();
        
        while (t-- > 0) {
            int n = sc.nextInt();
            int x = sc.nextInt();
            int s = sc.nextInt();
            String queue = sc.next();
            
            int empty = x;
            int available = 0;
            int flexible = 0;
            int seated = 0;
            
            for (int i = 0; i < n; i++) {
                char person = queue.charAt(i);
                
                if (person == 'I') {
                    if (empty > 0) {
                        empty--;
                        available += (s - 1);
                        seated++;
                    }
                } 
                else if (person == 'A') {
                    if (available > 0) {
                        available--;
                        flexible++;
                        seated++;
                    } else if (empty > 0) {
                        empty--;
                        available += (s - 1);
                        seated++;
                    }
                } 
                else if (person == 'E') {
                    if (available > 0) {
                        available--;
                        seated++;
                    } else if (flexible > 0 && empty > 0) {
                        flexible--;
                        empty--;
                        available += (s - 1);
                        seated++;
                    }
                }
            }
            
            System.out.println(seated);
        }
    }
}
