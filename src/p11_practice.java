import java.util.Scanner;

public class p11_practice {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入第一个长方形的长和宽：");
        int length1,width1;
        length1=scanner.nextInt();
        width1=scanner.nextInt();
        System.out.println("请输入第二个长方形的长和宽：");
        int length2,width2;
        length2=scanner.nextInt();
        width2=scanner.nextInt();
        if(length1+width1>length2+width2){
            System.out.println("第一个长方形的周长更长");
        }else if(length1+width1<length2+width2){
            System.out.println("第二个长方形的周长更长");
        }else{
            System.out.println("两个长方形的周长一样长");
        }
    }
}
