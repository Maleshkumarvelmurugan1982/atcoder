import java.util.*;
public class main{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int n,s;
        System.out.println("Enter the number of test cases:");
        n=sc.nextInt();
        for(int i=0;i<n;i++){
            System.out.println("Enter the size of the array:");
            s=sc.nextInt();
            for(int j=0;j<s;j++){
                int value = 2 * j + 2;
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
