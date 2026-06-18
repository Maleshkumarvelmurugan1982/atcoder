import java.util.*;

class Main {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
       
        String[] arr = {"dreamer", "dream", "erase", "eraser"};
        boolean matchFound = true;

        while (s.length() > 0 && matchFound) {
            matchFound = false;
           
            for (int i = 0; i < arr.length; i++) {
                if (s.endsWith(arr[i])) {
                    s = s.substring(0, s.length() - arr[i].length());
                    matchFound = true;
                    break;
                }
            }
        }

        if (s.length() == 0) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}

