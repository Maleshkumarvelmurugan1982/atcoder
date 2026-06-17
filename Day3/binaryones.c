#include <stdio.h>
int main() {
    int decimal;
    printf("Enter the decimal no:");
    scanf("%d",&decimal);
    int bin[32];
    int i=0,count=0;
    if(decimal==0){
        printf("Binary is 0\n");
    }
    while(decimal>0){
        bin[i]=decimal%2;
        if(bin[i]==1){
            count++;
        }
        decimal=decimal/2;
        i++;
    }
    if(count==0){
        printf("Invalid Input");
    }else{
        for(int j=i-1;j>=0;j--){
            printf("%d",bin[j]);
        }
        printf("\n");
        printf("No of ones:%d\n",count);   
        }
}
