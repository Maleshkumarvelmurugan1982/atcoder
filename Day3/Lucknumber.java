import java.util.*;
public class main{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int n;
        System.out.println("Enter car number:");
        n=sc.nextInt();
        int length = String.valueOf(n).length();
        String Str = String.valueOf(n);
        if(n<=0 || length!=4 || Str.contains("0")){
            System.out.println(n+"is not a valid car number");
        }else{
            int sum=0;
            while(n>0){
                sum+=n%10;
                n/=10;
            }
            if(sum%3==0||sum%5==0||sum%7==0){
                System.out.println("Lucky number");
            }else{
                System.out.println("Not Lucky Number");
            }
        }
        
    }
}
